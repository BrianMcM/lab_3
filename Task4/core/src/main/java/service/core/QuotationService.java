package service.core;
import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface QuotationService  {
	@WebMethod
	public Quotation generateQuotation(ClientInfo info);
}
