package service.core;
import javax.jws.WebService;
import javax.jws.WebMethod;
import java.rmi.RemoteException;
import java.util.LinkedList;
@WebService(name = "BrokerService",
		targetNamespace = "http://core.service/",
		serviceName = "BrokerService")
public interface BrokerService  {
	@WebMethod
	public LinkedList<Quotation> getQuotations(ClientInfo info) throws RemoteException;
}
