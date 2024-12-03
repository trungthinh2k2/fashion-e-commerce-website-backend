package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>  {
    boolean existsByProductName(String productName);
    List<Product> findAllByProductStatus(Status status);
    @EntityGraph(value = "product-entity-graph")
    List<Product> findTop3ByOrderByCreatedAtDesc();

    @EntityGraph(value = "product-entity-graph")
    @Query("select p, pp.discountedPrice, pp.discount, coalesce(case WHEN pp.expiredDate >= CURRENT_DATE AND pp.issueDate > CURRENT_DATE THEN p.price " +
            "                   WHEN pp.expiredDate >= CURRENT_DATE AND pp.issueDate <= CURRENT_DATE THEN pp.discountedAmount " +
            "                   ELSE NULL end, p.price) from Product p left join ProductPrice pp on pp.product = p where (pp.discountedPrice = (select max(pp2.discountedPrice) from ProductPrice pp2 where pp2.product = p) or pp.discountedPrice is null) and p.productStatus = 'ACTIVE'")
    List<Product> findProductsDiscount();

    List<Product> findTop5ByProductNameContainingIgnoreCaseOrderByBuyQuantityDesc(String productName);

}