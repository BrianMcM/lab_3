//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package service;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

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
}
