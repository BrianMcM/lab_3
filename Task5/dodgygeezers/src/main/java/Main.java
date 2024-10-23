
import service.DGQService;

import javax.xml.ws.Endpoint;


import static service.DGQService.jmdnsAdvertise;

public class Main {
    public static void main(String[] args) {
        String host = "dodgygeezers"; // Default value for host

        // If there are command line arguments, use the first one as the host
        if (args.length > 0) {
            host = args[0];
        }

        // Publish the web service at the specified URL
        Endpoint.publish("http://0.0.0.0:9002/quotation", new DGQService());
        System.out.print("host_main"+host);
        // Advertise the service using JmDNS with the specified host
        jmdnsAdvertise(host);
    }
}