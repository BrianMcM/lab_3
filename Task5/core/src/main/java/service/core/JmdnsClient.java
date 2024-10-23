package service.core;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JmdnsClient implements ServiceListener {

    private final List<String> discoveredServices;
    private final Set<String> addedServices; // Set to track added services

    // Constructor that takes the list of discovered services
    public JmdnsClient(List<String> discoveredServices) {
        this.discoveredServices = discoveredServices;
        this.addedServices = new HashSet<>(); // Initialize the set
    }

    @Override
    public void serviceAdded(ServiceEvent event) {
        System.out.println("Service added: " + event.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        System.out.println("Service removed: " + event.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        System.out.println("Service resolved: " + event.getInfo());
        String path = event.getInfo().getPropertyString("path");

        // Add the discovered service's URL to the list only if it hasn't been added
        if (path != null && !addedServices.contains(path)) {
            discoveredServices.add(path);
            addedServices.add(path); // Mark this service as added
            System.out.println("Service discovered and added: " + path);
        } else {
            System.out.println("Service already added: " + path);
        }
    }
}
