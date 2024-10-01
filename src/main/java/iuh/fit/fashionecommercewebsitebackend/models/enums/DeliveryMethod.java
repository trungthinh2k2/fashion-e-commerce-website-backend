package iuh.fit.fashionecommercewebsitebackend.models.enums;

public enum DeliveryMethod {
    ECONOMY("road"),
    EXPRESS("fly");

    private final String transportType; // Kiểu vận chuyển tương ứng

    // Constructor để khởi tạo transportType
    DeliveryMethod(String transportType) {
        this.transportType = transportType;
    }

    // Phương thức để lấy kiểu vận chuyển
    public String getTransportType() {
        return transportType;
    }

    // Tùy chọn phương thức cho việc chuyển đổi tên enum thành chuỗi
    @Override
    public String toString() {
        return this.name().toLowerCase(); // Chuyển đổi tên thành chữ thường
    }
}
