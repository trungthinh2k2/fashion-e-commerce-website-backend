package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ProductResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;

import java.util.List;

public interface ProductService extends BaseService<Product, String> {
    Product save(ProductDto productDto) throws DataExistsException;
    void deactivateProduct(String id) throws DataExistsException;
    List<Product> findAllByStatus(Status status);
    ProductResponse findProductById(String id);
    List<Product> findTop3ByOrderByCreatedAtDesc();
    List<Product> findProductsDiscount();
    PageResponse<?> getProductsForUserRole(int pageNo, int pageSize, String[] search, String[] sort);
    PageResponse<?> getProductsDiscount(int pageNo, int pageSize, String[] search, String[] sort);
}
