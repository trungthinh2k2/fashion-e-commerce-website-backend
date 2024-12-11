package iuh.fit.fashionecommercewebsitebackend.services.interfaces;


import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<T, ID> {
    T save(T t);
    List<T> findAll();
    Optional<T> findById(ID id);
    T update(ID id, T t);
    T updatePatch(ID id, Map<String, ?> data) throws DataNotFoundException;
    void deleteById(ID id) throws DataNotFoundException;
    PageResponse<T> getDataWithPage(int pageNo, int pageSize, String[] search, String[] sortBy);
}
