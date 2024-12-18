package iuh.fit.fashionecommercewebsitebackend.services.impl.invoice;

import iuh.fit.fashionecommercewebsitebackend.models.Invoice;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.invoice.InvoiceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl extends BaseServiceImpl<Invoice, String> implements InvoiceService {
    public InvoiceServiceImpl(JpaRepository<Invoice, String> repository) {
        super(repository, Invoice.class);
    }
}
