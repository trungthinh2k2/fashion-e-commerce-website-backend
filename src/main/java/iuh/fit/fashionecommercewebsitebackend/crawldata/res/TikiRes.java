package iuh.fit.fashionecommercewebsitebackend.crawldata.res;

import iuh.fit.fashionecommercewebsitebackend.crawldata.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TikiRes {
    private List<Product> data;
}
