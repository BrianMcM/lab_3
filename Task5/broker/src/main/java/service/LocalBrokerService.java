package service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import service.core.*;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceListener;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

@WebService(name = "BrokerService",
        targetNamespace = "http://core.service/",
        serviceName = "BrokerService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class LocalBrokerService implements BrokerService {
    private final List<String> discoveredServices = new CopyOnWriteArrayList<>();
    public LocalBrokerService() {
        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            System.out.print("lbs"+InetAddress.getLocalHost());
            // Add a service listener
            jmdns.addServiceListener("_http._tcp.local.", new JmdnsClient(discoveredServices));
            // Wait a bit
            Thread.sleep(30000);
            System.out.print(discoveredServices);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @WebMethod
    public LinkedList<Quotation> getQuotations(ClientInfo info) {
        LinkedList<Quotation> quotations = new LinkedList<>();

        // Loop through discovered services (no hardcoded URLs)
        for (String serviceUrl : discoveredServices) {
            System.out.println("Trying service: " + serviceUrl);
            try {
                System.out.print("a");
                URL wsdlUrl = new URL(serviceUrl); // Fetch the WSDL URL
                System.out.print("a");
                QName serviceName = new QName("http://core.service/", "QuotationService");
                System.out.print("b");
                Service service = Service.create(wsdlUrl, serviceName);
                System.out.print("c");
                QName portName = new QName("http://core.service/", "QuotationServicePort");
                System.out.print("d");
                QuotationService quotationService = service.getPort(portName, QuotationService.class);
                System.out.print("e");

                // Generate a quotation and add it to the list
                Quotation quotation = quotationService.generateQuotation(info);
                System.out.print("f");
                if (quotation != null) {
                    quotations.add(quotation);
                }
            } catch (MalformedURLException e) {
                System.err.println("Malformed URL: " + serviceUrl);
            } catch (WebServiceException e) {
                System.err.println("WebServiceException: Unable to access service at " + serviceUrl);
            } catch (Exception e) {
                System.err.println("Error accessing service at " + serviceUrl + ": " + e.getMessage());
            }
        }

        System.out.println("Quotations retrieved: " + quotations.size());
        return quotations;
    }
}