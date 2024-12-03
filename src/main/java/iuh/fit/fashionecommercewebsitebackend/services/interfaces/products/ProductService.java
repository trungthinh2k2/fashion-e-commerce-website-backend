package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ProductResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

import java.util.List;

public interface ProductService extends BaseService<Product, String> {
    Product save(ProductDto productDto) throws DataExistsException, DataNotFoundException;
    void deactivateProduct(String id) throws DataExistsException, DataNotFoundException;
    List<Product> findAllByStatus(Status status);
    ProductResponse findProductById(String id) throws DataNotFoundException;
    List<Product> findTop3ByOrderByCreatedAtDesc();
    List<Product> findProductsDiscount();
    PageResponse<?> getProductsForUserRole(int pageNo, int pageSize, String[] search, String[] sort);
    PageResponse<?> getProductsDiscount(int pageNo, int pageSize, String[] search, String[] sort);
    PageResponse<?> getProductsNewCreatedDate(int pageNo, int pageSize, String[] search, String[] sort);
    PageResponse<?> getProductsSold(int pageNo, int pageSize, String[] search, String[] sort);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}
