package iuh.fit.fashionecommercewebsitebackend.crawldata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private long id;
    private String name;
    private double price;
    @JsonProperty("original_price")
    private long originalPrice;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
}
