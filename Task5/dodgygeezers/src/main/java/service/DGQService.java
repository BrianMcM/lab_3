
package service;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.net.InetAddress;

@WebService(name="QuotationService",
        targetNamespace="http://core.service/",
        serviceName="QuotationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class DGQService extends AbstractQuotationService {
    public static final String PREFIX = "DG";
    public static final String COMPANY = "Dodgy Geezers Corp.";

    public DGQService() {
    }
    @WebMethod
    public Quotation generateQuotation(ClientInfo info) {
        double price = this.generatePrice(800.0, 200);
        int discount = this.bmiDiscount(info);
        if (info.smoker) {
            discount += 10;
        }

        return new Quotation(COMPANY, this.generateReference(PREFIX), price * (double)(100 - discount) / 100.0);
    }

    public int bmiDiscount(ClientInfo info) {
        double bmi = this.bmi(info.weight, info.height);
        if (bmi < 18.5) {
            return 0;
        } else if (bmi < 24.5) {
            return 5;
        } else if (bmi < 30.0) {
            return 10;
        } else {
            return bmi < 40.0 ? 15 : 20;
        }
    }
    public static void jmdnsAdvertise(String host) {
        try {
            String config = "path=http://"+host+":9002/quotation?wsdl";
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "ws-service", 9002, config);
            jmdns.registerService(serviceInfo);
            System.out.println("Registering dodgygeezers");
            System.out.println("Advertising service at: " + InetAddress.getLocalHost().getHostAddress());
            Thread.sleep(100000);
            jmdns.unregisterAllServices();
        } catch (Exception e) {
            System.out.println("Problem Advertising Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
