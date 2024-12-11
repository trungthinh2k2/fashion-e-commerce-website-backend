package iuh.fit.fashionecommercewebsitebackend.models.ids;

import iuh.fit.fashionecommercewebsitebackend.models.Order;
import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetailId implements Serializable {
    private Order order;
    private ProductDetail productDetail;
}
