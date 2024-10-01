package iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders;

public interface ShippingService {
    double calculateShippingFee(String pickProvince, String pickDistrict, String province, String district, double weight, String deliveryMethod) throws Exception;
}
