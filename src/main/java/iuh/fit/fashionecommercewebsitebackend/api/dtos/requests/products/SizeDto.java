package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import iuh.fit.fashionecommercewebsitebackend.models.enums.SizeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SizeDto {
    private SizeType sizeType;
    private Integer numberSize;
    private String textSize;
}