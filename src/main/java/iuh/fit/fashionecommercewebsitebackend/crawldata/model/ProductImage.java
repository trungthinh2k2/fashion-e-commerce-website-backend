package iuh.fit.fashionecommercewebsitebackend.crawldata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {
    @JsonProperty("base_url")
    private String baseUrl;
}