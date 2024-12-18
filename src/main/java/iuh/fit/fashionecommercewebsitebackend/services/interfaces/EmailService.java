package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.models.Invoice;
import iuh.fit.fashionecommercewebsitebackend.utils.EmailDetails;

public interface EmailService {
    void sendHtmlMail(EmailDetails details) throws Exception;
    void sendHtmlMailInvoice(Invoice invoice) throws Exception;
}
