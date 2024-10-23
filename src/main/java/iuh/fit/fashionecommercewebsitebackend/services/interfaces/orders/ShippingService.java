package iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ShippingDto;

public interface ShippingService {
    double calculateShippingFee(String pickProvince, String pickDistrict, String province, String district, double weight, String deliveryMethod) throws Exception;
    double calculateShippingFee(ShippingDto shippingDto) throws Exception;
}
