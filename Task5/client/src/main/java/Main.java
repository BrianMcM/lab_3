import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.LinkedList;

public class Main {

    public static void displayProfile (ClientInfo info){
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("| Name: " + String.format("%1$-29s", info.name) + " | Gender: " + String.format("%1$-27s", info.gender == 'M' ? "Male" : "Female") + " | Age: " + String.format("%1$-30s", info.age) + " |");
        System.out.println("| Weight/Height: " + String.format("%1$-20s", info.weight + "kg/" + info.height + "m") + " | Smoker: " + String.format("%1$-27s", info.smoker ? "YES" : "NO") + " | Medical Problems: " + String.format("%1$-17s", info.medicalIssues ? "YES" : "NO") + " |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    public static void displayQuotation(Quotation quotation1){
        System.out.println("| Company: " + String.format("%1$-26s", quotation1.company) + " | Reference: " + String.format("%1$-24s", quotation1.reference) + " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation1.price)) + " |");
        System.out.println("|=================================================================================================================|");
    }

    public static void main(String[] args) {

        URL wsdlUrl = null;
        try {
            wsdlUrl = new URL("http://localhost:9000/quotation?wsdl");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        QName serviceName = new QName("http://core.service/", "BrokerService");

        Service service = Service.create(wsdlUrl, serviceName);

        QName portName = new QName("http://core.service/", "BrokerServicePort");

        BrokerService brokerService = service.getPort(portName, BrokerService.class);

        ClientInfo clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false);

        LinkedList<Quotation> quotations = null;

        try {
            quotations = (LinkedList<Quotation>) brokerService.getQuotations(clientInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.print(quotations);



        displayProfile(clientInfo);
        for (Quotation q : quotations){
            displayQuotation(q);
        }
    }

}