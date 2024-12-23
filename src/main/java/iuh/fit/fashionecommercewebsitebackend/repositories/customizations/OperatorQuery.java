package iuh.fit.fashionecommercewebsitebackend.repositories.customizations;

public class OperatorQuery {
    public static String getOperator(String operator) {
        return switch (operator) {
            case ">" -> ">";
            case "<" -> "<";
            case ">=" -> ">=";
            case "<=" -> "<=";
            case "-" -> "=";
            case "!" -> "!=";  // Thêm xử lý cho '!'
            default -> "like";
        };
    }
}
