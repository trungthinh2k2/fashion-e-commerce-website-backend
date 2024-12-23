package iuh.fit.fashionecommercewebsitebackend.models.enums;

public enum OrderStatus {
    NOT_PROCESSED_YET("Chưa được xử lý"),
    PENDING("Đang chờ xử lý"),
    PROCESSING("Đã xác nhận đơn hàng"),
    SHIPPING("Đang vận chuyển"),
    DELIVERED("Đã được giao"),
    RECEIVED("Đã nhận"),
    CANCELLED("Đã hủy");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatusInVietnamese() {
        return this.status;
    }

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Unknown order status: " + status);
    }
}
