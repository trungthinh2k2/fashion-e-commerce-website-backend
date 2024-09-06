package iuh.fit.fashionecommercewebsitebackend.repositories.customizations;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class BaseCustomizationRepository<T> {

    protected final Class<T> entityClass;

    protected PageResponse<T> getDataWithPage(int pageNo, int pageSize, String[] search, String[] sortBy) {
        return null;
    }
}
