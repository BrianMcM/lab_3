package service;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.LinkedList;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

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
    public LocalBrokerService() {
    }

    @WebMethod
    public LinkedList<Quotation> getQuotations(ClientInfo info) {
        LinkedList<Quotation> quotations = new LinkedList<>();
        String[] listServices = new String[3];
        listServices[0] = "http://auldfellas:9001/quotation?wsdl";
        listServices[1] = "http://dodgygeezers:9002/quotation?wsdl";
        listServices[2] = "http://girlsallowed:9003/quotation?wsdl";

        // Loop through the list of service URLs
        for (String serviceUrl : listServices) {
            System.out.print("loop"+serviceUrl);
            URL wsdlUrl = null;
            try {
                wsdlUrl = new URL(serviceUrl); // Fetch the WSDL URL
            } catch (MalformedURLException e) {
                System.err.println("Malformed URL: " + serviceUrl);
                continue; // Skip this service if the URL is malformed
            }

            QName serviceName = new QName("http://core.service/", "QuotationService");
            try {
                Service service = Service.create(wsdlUrl, serviceName);
                QName portName = new QName("http://core.service/", "QuotationServicePort");
                QuotationService quotationService = service.getPort(portName, QuotationService.class);

                // Generate a quotation and add it to the list
                Quotation quotation = quotationService.generateQuotation(info);
                if (quotation != null) { // Ensure the quotation is not null
                    quotations.add(quotation);
                }
            } catch (WebServiceException e) {
                System.err.println("WebServiceException: Unable to access service at " + serviceUrl);
            } catch (Exception e) {
                System.err.println("Exception occurred while accessing service at " + serviceUrl + ": " + e.getMessage());
            }
        }

        System.out.print("Quotations retrieved: " + quotations.size());
        return quotations; // Return the list of quotations
    }


}