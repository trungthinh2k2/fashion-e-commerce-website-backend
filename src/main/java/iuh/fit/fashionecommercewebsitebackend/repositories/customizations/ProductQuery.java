package iuh.fit.fashionecommercewebsitebackend.repositories.customizations;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ProductUserResponse;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class ProductQuery extends BaseCustomizationRepository<Product> {
    protected ProductQuery() {
        super(Product.class);
    }

    private String getQuery(String query) {
        return String.format("select distinct %s from Product p left join ProductPrice pp on pp.product = p " +
                "where (pp.discountedPrice = (select max(pp2.discountedPrice) from ProductPrice pp2 where pp2.product = p) " +
                "or pp.discountedPrice is null) and p.productStatus = 'ACTIVE' ", query);
    }

    // Lấy dữ liệu sản phẩm theo trang
    @Override
    public PageResponse<?> getDataWithPage(int pageNo, int pageSize, String[] search, String[] sort) {

        StringBuilder queryBuilder = new StringBuilder(getQuery(
                "new iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ProductUserResponse(" +
                        "p, pp.discount, pp.discountedPrice, pp.discountedAmount, pp.issueDate, pp.expiredDate, " +
                        "(case " +
                        "WHEN pp.issueDate <= CURRENT_DATE AND pp.expiredDate >= CURRENT_DATE THEN pp.discountedAmount " +
                        "ELSE p.price " +
                        "end), " +
                        "(case " +
                        "WHEN pp.issueDate - CURRENT_DATE <=0 AND pp.expiredDate - CURRENT_DATE > 0 THEN 'Đang khuyến mãi' " +
                        "ELSE 'Hết khuyến mãi, chưa tới khuyến mãi' " +
                        "end))"
                ));

        createQueryBuilder(search, queryBuilder, " %s p.%s %s ?%s");
        sortBy(queryBuilder, " order by p.%s %s", sort);
        TypedQuery<ProductUserResponse> query = entityManager.createQuery(queryBuilder.toString(), ProductUserResponse.class);
//        EntityGraph<?> entityGraph = entityManager.getEntityGraph("product-entity-graph");
//        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, query);

        var data = query.getResultList();

        StringBuilder countQueryBuilder = new StringBuilder(getQuery("count(*)"));
        createQueryBuilder(search, countQueryBuilder, " %s p.%s %s ?%s");

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElements(data.size())
                .build();
    }

    // Chỉ lấy sản phẩm đang khuyến mãi
    public PageResponse<?> getDataDiscount(int pageNo, int pageSize, String[] search, String[] sort) {
        StringBuilder queryBuilder1 = new StringBuilder(getQuery(
                "new iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ProductUserResponse(" +
                        "p, pp.discount, pp.discountedPrice, pp.discountedAmount, pp.issueDate, pp.expiredDate, " +
                        "(case WHEN pp.expiredDate >= CURRENT_DATE AND pp.issueDate <= CURRENT_DATE THEN pp.discountedAmount " +
                        "ELSE p.price end), " +
                        "(case WHEN  pp.issueDate - CURRENT_DATE <=0 AND pp.expiredDate - CURRENT_DATE > 0 THEN 'Đang khuyến mãi' " +
                        "ELSE 'Hết khuyến mãi, chưa tới khuyến mãi' end))"
        ));

        queryBuilder1.append(" AND pp.issueDate <= CURRENT_DATE AND pp.expiredDate >= CURRENT_DATE");

        createQueryBuilder(search, queryBuilder1, " %s p.%s %s ?%s");
        sortBy(queryBuilder1, " order by p.%s %s", sort);
        TypedQuery<ProductUserResponse> query = entityManager.createQuery(queryBuilder1.toString(), ProductUserResponse.class);
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("product-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, query);

        var data = query.getResultList();
        StringBuilder countQueryBuilder = new StringBuilder(getQuery("count(*)"));
        countQueryBuilder.append(" AND pp.issueDate <= CURRENT_DATE AND pp.expiredDate >= CURRENT_DATE");
        createQueryBuilder(search, countQueryBuilder, " %s p.%s %s ?%s");

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElements(data.size())
                .build();
    }

    // Lấy sản phẩm top 20 và sắp xếp theo createdDate
    public PageResponse<?> getDataProductTop20(int pageNo, int pageSize, String[] search, String[] sort) {
        StringBuilder queryBuilder1 = new StringBuilder(getQuery(
                "new iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ProductUserResponse(" +
                        "p, pp.discount, pp.discountedPrice, pp.discountedAmount, pp.issueDate, pp.expiredDate, " +
                        "(case WHEN pp.expiredDate >= CURRENT_DATE AND pp.issueDate <= CURRENT_DATE THEN pp.discountedAmount " +
                        "ELSE p.price end), " +
                        "(case WHEN  pp.issueDate - CURRENT_DATE <= 0 AND pp.expiredDate - CURRENT_DATE > 0 THEN 'Đang khuyến mãi' " +
                        "ELSE 'Hết khuyến mãi, chưa tới khuyến mãi' end))"
        ));

        queryBuilder1.append(" ORDER BY p.createdAt DESC");

        createQueryBuilder(search, queryBuilder1, " %s p.%s %s ?%s");
        sortBy(queryBuilder1, " order by p.%s %s",sort);

        TypedQuery<ProductUserResponse> query = entityManager.createQuery(queryBuilder1.toString(), ProductUserResponse.class);
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("product-entity-graph");
        query.setHint("jakata.persistence.fetchgraph", entityGraph);

        query.setMaxResults(20);
        setValueParams(search, query);

        var data = query.getResultList();

        StringBuilder countQueryBuilder = new StringBuilder(getQuery("count(*)"));
        createQueryBuilder(search, countQueryBuilder, " %s p.%s %s ?%s");

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElements(data.size())
                .build();
    }

    // Lấy sản phẩm top 20 và sắp xếp theo Sold
    public PageResponse<?> getDataProductsSold(int pageNo, int pageSize, String[] search, String[] sort) {
        StringBuilder queryBuilder1 = new StringBuilder(getQuery(
                "new iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ProductUserResponse(" +
                        "p, pp.discount, pp.discountedPrice, pp.discountedAmount, pp.issueDate, pp.expiredDate, " +
                        "(case WHEN pp.expiredDate >= CURRENT_DATE AND pp.issueDate <= CURRENT_DATE THEN pp.discountedAmount " +
                        "ELSE p.price end), " +
                        "(case WHEN  pp.issueDate - CURRENT_DATE <= 0 AND pp.expiredDate - CURRENT_DATE > 0 THEN 'Đang khuyến mãi' " +
                        "ELSE 'Hết khuyến mãi, chưa tới khuyến mãi' end))"
        ));

        queryBuilder1.append(" AND p.buyQuantity IS NOT NULL");
        queryBuilder1.append(" ORDER BY p.buyQuantity DESC");

        createQueryBuilder(search, queryBuilder1, " %s p.%s %s ?%s");
        sortBy(queryBuilder1, " order by p.%s %s", sort);

        TypedQuery<ProductUserResponse> query = entityManager.createQuery(queryBuilder1.toString(), ProductUserResponse.class);
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("product-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        query.setMaxResults(20);
        setValueParams(search, query);

        var data = query.getResultList();
        // Đếm tổng số sản phẩm
        StringBuilder countQueryBuilder = new StringBuilder(getQuery("count(*)"));
        countQueryBuilder.append(" AND p.buyQuantity IS NOT NULL");
        createQueryBuilder(search, countQueryBuilder, " %s p.%s %s ?%s");

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);
        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElements(data.size())
                .build();
    }

}
