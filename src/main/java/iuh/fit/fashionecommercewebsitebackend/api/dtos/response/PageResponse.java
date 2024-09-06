package iuh.fit.fashionecommercewebsitebackend.api.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private int pageNo;
    private int totalPage;
    private long totalElements;
    private T data;
}
