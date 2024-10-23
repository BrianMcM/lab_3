//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
public class GAQService extends AbstractQuotationService {
    public static final String PREFIX = "GA";
    public static final String COMPANY = "Girls Allowed Inc.";

    public GAQService() {
    }
    @WebMethod
    public Quotation generateQuotation(ClientInfo info) {
        double price = this.generatePrice(600.0, 400);
        int discount = info.gender == 'F' ? 50 : -30;
        discount += this.bmiDiscount(info);
        discount += this.medicalWeighting(info);
        return new Quotation(COMPANY, this.generateReference(PREFIX), price * (double)(100 - discount) / 100.0);
    }

    public int bmiDiscount(ClientInfo info) {
        double bmi = this.bmi(info.weight, info.height);
        if (bmi < 18.5) {
            return 20;
        } else if (bmi < 24.5) {
            return 10;
        } else if (bmi < 30.0) {
            return 0;
        } else {
            return bmi < 40.0 ? -20 : 40;
        }
    }

    public int medicalWeighting(ClientInfo info) {
        int weighting = 0;
        if (info.medicalIssues) {
            weighting -= 20;
        }

        if (info.smoker) {
            weighting -= 40;
        }

        return weighting;
    }
    public static void jmdnsAdvertise(String host) {
        try {
            String config = "path=http://"+host+":9003/quotation?wsdl";
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "ws-service", 9003, config);
            jmdns.registerService(serviceInfo);
            System.out.println("Registering girlsallowed");
            System.out.println("Advertising service at: " + InetAddress.getLocalHost().getHostAddress());
            Thread.sleep(100000);
            jmdns.unregisterAllServices();
        } catch (Exception e) {
            System.out.println("Problem Advertising Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
