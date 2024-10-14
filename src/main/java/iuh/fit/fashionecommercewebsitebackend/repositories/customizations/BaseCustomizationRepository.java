//package iuh.fit.fashionecommercewebsitebackend.repositories.customizations;
//
//import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.Arrays;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Repository
//public abstract class BaseCustomizationRepository<T> {
//
//    @PersistenceContext
//    protected EntityManager entityManager;
//
//    protected final Class<T> entityClass;
//
//    protected static final Pattern FILTER_PATTERN = Pattern.compile("(.*?)([<>]=?|:|-|!)([^-]*)-?(or)?");
//
//    protected static final Pattern SORT_PATTERN = Pattern.compile("(\\w+?)(:)(asc|desc)");
//    protected BaseCustomizationRepository(Class<T> entityClass) {
//        this.entityClass = entityClass;
//    }
//
//    protected PageResponse<?> getDataWithPage(int pageNo, int pageSize, String[] search, String[] sort) {
//
//        String sql = String.format("select o from %s o where 1=1", entityClass.getName());
//        StringBuilder queryBuilder = new StringBuilder(sql);
//
//        createQueryBuilder(search, queryBuilder);
//        sortBy(queryBuilder, sort);
//
//        Query query = entityManager.createQuery(queryBuilder.toString());
//        query.setFirstResult((pageNo - 1) * pageSize);
//        query.setMaxResults(pageSize);
//
//        setValueParams(search, query);
//
//        String sqlCount = String.format("select count(*) from %s o where 1=1", entityClass.getName());
//        StringBuilder countQueryBuilder = new StringBuilder(sqlCount);
//        createQueryBuilder(search, countQueryBuilder);
//        Query queryCount = entityManager.createQuery(countQueryBuilder.toString());
//        setValueParams(search, queryCount);
//
//        return PageResponse.builder()
//                .data(query.getResultList())
//                .totalPage((long)Math.ceil((long)queryCount.getSingleResult() * 1.0/pageSize))
//                .pageNo(pageNo)
//                .totalElements(query.getResultList().toArray().length)
//                .build();
//    }
//
//    protected void createQueryBuilder(String[] search, StringBuilder queryBuilder) {
//        if (search != null) {
//            for (String s : search) {
//                Matcher matcher = FILTER_PATTERN.matcher(s);
//                if (matcher.find()) {
//                    String operator = matcher.group(2);
//                    String format = String.format(" %s o.%s %s ?%s",
//                            matcher.group(4) != null ? "or" : "and",
//                            matcher.group(1),
//                            operator,
//                            Arrays.stream(search).toList().indexOf(s)+1);
//                    queryBuilder.append(format);
//                }
//            }
//        }
//    }
//
//    protected void sortBy(StringBuilder queryBuilder, String[] sort) {
//        if (sort != null) {
//            for (String s : sort) {
//                Matcher matcher = SORT_PATTERN.matcher(s);
//                if (matcher.find()) {
//                    String format = String.format(" order by o.%s %s", matcher.group(1), matcher.group(3));
//                    queryBuilder.append(format);
//                }
//            }
//        }
//    }
//
//    protected void setValueParams(String[] search, Query queryCount) {
//        if (search != null) {
//            for (String s : search) {
//                Matcher matcher = FILTER_PATTERN.matcher(s);
//                if (matcher.find()) {
//                    String operator = OperatorQuery.getOperator(matcher.group(2));
//                    if (!operator.isEmpty()) {
//                        var value = matcher.group(3);
//                        if(operator.equals("like")) {
//                            value = String.format("%%%s%%", value);
//                        }
//                        queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
//                    }
//                }
//            }
//        }
//    }
//}

package iuh.fit.fashionecommercewebsitebackend.repositories.customizations;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public abstract class BaseCustomizationRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<T> entityClass;

    // Mẫu regex cho filter và sort
    protected static final Pattern FILTER_PATTERN = Pattern.compile("(.*?)([<>]=?|:|-|!)([^-]*)-?(or)?");
    protected static final Pattern SORT_PATTERN = Pattern.compile("(\\w+?)(:)(asc|desc)");

    protected BaseCustomizationRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected PageResponse<?> getPageData(int pageNo, int pageSize, String[] search, String[] sort) {
        String sql = String.format("select o from %s o where 1=1", entityClass.getName());
        StringBuilder queryBuilder = new StringBuilder(sql);

        createQueryBuilder(search, queryBuilder, " %s o.%s %s ?%s");
        sortBy(queryBuilder, " order by o.%s %s", sort);

        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);

        setValueParams(search, query);

        String sqlCount = String.format("select count(*) from %s o where 1=1", entityClass.getName());
        StringBuilder countQueryBuilder = new StringBuilder(sqlCount);
        createQueryBuilder(search, countQueryBuilder, " %s o.%s %s ?%s");
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);

        var data = query.getResultList();

        return PageResponse.builder()
                .data(data)
                .totalPage((long)Math.ceil((long)countQuery.getSingleResult() * 1.0/pageSize))
                .pageNo(pageNo)
                .totalElements(data.toArray().length)
                .build();
    }


    protected void createQueryBuilder(String[] search, StringBuilder queryBuilder, String queryFormat) {
        if(search != null) {
            for(String s : search) {
                Matcher matcher = FILTER_PATTERN.matcher(s);
                if(matcher.find()) {
                    String operator = OperatorQuery.getOperator(matcher.group(2));
//                    String operator = matcher.group(2);
                    String format = String.format(queryFormat, matcher.group(4) != null ? "or" : "and",
                            matcher.group(1), operator,
                            Arrays.stream(search).toList().indexOf(s) + 1);
                    queryBuilder.append(format);
                }
            }
        }
    }

    protected void setValueParams(String[] search, Query queryCount) {
        if (search != null) {
            for (String s : search) {
                Matcher matcher = FILTER_PATTERN.matcher(s);
                if (matcher.find()) {
                    String operator = OperatorQuery.getOperator(matcher.group(2));
//                    String operator = matcher.group(2);
                    if (!operator.isEmpty()) {
                        var value = matcher.group(3);
                        if(operator.equals("like")) {
                            value = String.format("%%%s%%", value);
                        }
                        queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
                    }
                }
            }
        }
    }
    protected void sortBy(StringBuilder queryBuilder, String queryFormat, String... sort) {
        if (sort != null) {
            for (String s : sort) {
                Matcher matcher = SORT_PATTERN.matcher(s);
                if (matcher.find()) {
                    String sortBy = String.format(queryFormat, matcher.group(1), matcher.group(3));
                    queryBuilder.append(sortBy);
                }
            }
        }
    }

    // Lấy dữ liệu sản phẩm theo trang
    public abstract PageResponse<?> getDataWithPage(int pageNo, int pageSize, String[] search, String[] sort);
}
