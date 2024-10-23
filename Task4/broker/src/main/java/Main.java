import service.LocalBrokerService;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {

        Endpoint.publish("http://0.0.0.0:9000/quotation", new LocalBrokerService());
    }
}