package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.models.Invoice;
import iuh.fit.fashionecommercewebsitebackend.models.OrderDetail;
import iuh.fit.fashionecommercewebsitebackend.repositories.OrderDetailRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.EmailService;
import iuh.fit.fashionecommercewebsitebackend.utils.EmailDetails;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final OrderDetailRepository orderDetailRepository;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendHtmlMail(EmailDetails details) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(details.getRecipient());
        mimeMessageHelper.setSubject(details.getSubject());

        Context context = new Context();
        Map<String, Object> variables = new HashMap<>();
        variables.put("msgBody", details.getMsgBody());
        context.setVariables(variables);
        String htmlContext = templateEngine.process("email-html", context);

        mimeMessageHelper.setText(htmlContext, true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendHtmlMailInvoice(Invoice invoice) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(invoice.getOrder().getUser().getEmail());
        mimeMessageHelper.setSubject("Hóa đơn thanh toán - đơn hàng " + invoice.getOrder().getId());

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(invoice.getOrder().getId());

        Context context = new Context();
        context.setVariable("id", invoice.getId());
        context.setVariable("buyerName", invoice.getBuyerName());
        context.setVariable("phoneNumber", invoice.getPhoneNumber());
        context.setVariable("addressDetail", invoice.getAddressDetail());
        context.setVariable("orderDetails", orderDetails);
        context.setVariable("originalAmount", invoice.getOriginalAmount());
        context.setVariable("deliveryFee", invoice.getDeliveryFee());
        context.setVariable("discountAmount", invoice.getDiscountAmount());
        context.setVariable("discountPrice", invoice.getDiscountPrice());
        context.setVariable("invoiceDate", invoice.getInvoiceDate());
        context.setVariable("paymentMethod", invoice.getPaymentMethod());
        context.setVariable("deliveryMethod", invoice.getOrder().getDeliveryMethod());


        String htmlContext = templateEngine.process("invoice-email-html", context);

        mimeMessageHelper.setText(htmlContext, true);
        javaMailSender.send(mimeMessage);
    }
}
