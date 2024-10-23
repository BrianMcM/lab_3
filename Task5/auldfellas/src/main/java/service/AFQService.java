package service;


import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.net.InetAddress;


@WebService(name="QuotationService",
        targetNamespace="http://core.service/",
        serviceName="QuotationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class AFQService extends AbstractQuotationService {
    public static final String PREFIX = "AF";
    public static final String COMPANY = "Auld Fellas Ltd.";

    public AFQService() {
    }
    @WebMethod
    public Quotation generateQuotation(ClientInfo info) {
        double price = this.generatePrice(600.0, 600);
//        boolean discount = false;
        int discount;
        if (info.gender == 'M') {
            discount = 30;
            if (info.age > 50) {
                discount += 10;
            }

            if (info.age > 60) {
                discount += 10;
            }
        } else {
            discount = -20;
        }

        return new Quotation(COMPANY, this.generateReference(PREFIX), price * (double)(100 - discount) / 100.0);
    }
    public static void jmdnsAdvertise(String host) {
        try {
            String config = "path=http://"+host+":9001/quotation?wsdl";
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "ws-service", 9001, config);
            jmdns.registerService(serviceInfo);
            System.out.println("Registering Auldfellas");
            System.out.println("Advertising service at: " + InetAddress.getLocalHost().getHostAddress());
            Thread.sleep(100000);
            jmdns.unregisterAllServices();
        } catch (Exception e) {
            System.out.println("Problem Advertising Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
