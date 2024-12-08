package iuh.fit.fashionecommercewebsitebackend.crawldata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetail {
    @JsonProperty("short_description")
    private String shortDescription;
    private List<ProductImage> images;
    @JsonProperty("configurable_options")
    private List<Option> configurableOptions;
}
