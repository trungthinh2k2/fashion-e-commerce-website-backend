package iuh.fit.fashionecommercewebsitebackend.repositories.customizations;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.models.enums.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public abstract class BaseCustomizationRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<T> entityClass;

    // Mẫu regex cho filter và sort
    protected static final Pattern FILTER_PATTERN = Pattern.compile("(.*?)([<>]=?|:|-|!)([\\d\\w\\s:-]*)-?(or)?");

    protected static final Pattern FILTER_PATTERN1 = Pattern.compile(
            "(.*?)([<>]=?|:|-|!)([\\d\\w\\sáàảãạâấầẩẫậăắằẳẵặÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶéèẻẽẹêếềểễệÉÈẺẼẸÊẾỀỂỄỆíìỉĩịÍÌỈĨỊóòỏõọôốồổỗộơớờởỡợÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢúùủũụưứừửữựÚÙỦŨỤƯỨỪỬỮỰýỳỷỹỵÝỲỶỸỴđĐ]*)-?(or)?"
    );
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
        System.out.println(countQuery.toString());
        setValueParams(search, countQuery);

        var data = query.getResultList();

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil((long) countQuery.getSingleResult() * 1.0 / pageSize))
                .pageNo(pageNo)
                .totalElements(data.toArray().length)
                .build();
    }


    protected void createQueryBuilder(String[] search, StringBuilder queryBuilder, String queryFormat) {
        if (search != null) {
            for (String s : search) {
                Matcher matcher = FILTER_PATTERN.matcher(s);
                if (matcher.find()) {
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
                    if (!operator.isEmpty()) {
                        var value = matcher.group(3);
                        if (matcher.group(1).equals("status")) {
                            try {
                                OrderStatus orderStatus = OrderStatus.valueOf(value.toUpperCase());
                                queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, orderStatus);
                            } catch (IllegalArgumentException e) {
                                throw new IllegalArgumentException("Invalid orderStatus value: " + value);
                            }
                        }
                        else if (operator.equals(">=") || operator.equals("<=")) {
                            if (value.length() == 10) {
                                value = value + " 00:00:00";
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime dateTimeValue = LocalDateTime.parse(value, formatter);
                                queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, dateTimeValue);
                            }
                            else {
                                queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
                            }
                        }
                        else if (operator.equals("like")) {
                            value = "%" + value + "%";
                            queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
                        }
                        else {
                            queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
                        }
                    }
                }
            }
        }
    }

//    protected void setValueParams(String[] search, Query queryCount) {
//        if (search != null) {
//            for (String s : search) {
//                Matcher matcher1 = FILTER_PATTERN.matcher(s);
//                Matcher matcher2 = FILTER_PATTERN1.matcher(s);
//
//                if (matcher1.find()) {
//                    handleLogicForPattern(matcher1, queryCount, search, s);
//                }
//                else if (matcher2.find()) {
//                    handleLogicForPattern(matcher2, queryCount, search, s);
//                }
//            }
//        }
//    }
//    private void handleLogicForPattern(Matcher matcher, Query queryCount, String[] search, String s) {
//        String operator = OperatorQuery.getOperator(matcher.group(2));
//        String value = matcher.group(3);
//
//        if (!operator.isEmpty()) {
//            switch (operator) {
//                case ">=":
//                case "<=":
//                    handleComparisonLogic(value, operator, queryCount, search, s);
//                    break;
//                case "like":
//                    value = "%" + value + "%";
//                    queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
//                    break;
//                default:
//                    queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
//            }
//        }
//    }
//
//    private void handleComparisonLogic(String value, String operator, Query queryCount, String[] search, String s) {
//        if (value.length() == 10) { // Kiểm tra nếu là định dạng ngày như yyyy-MM-dd
//            value = value + " 00:00:00";
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime dateTimeValue = LocalDateTime.parse(value, formatter);
//            queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, dateTimeValue);
//        } else {
//            queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
//        }
//    }


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
