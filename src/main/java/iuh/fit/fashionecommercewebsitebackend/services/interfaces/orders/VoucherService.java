package iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountShipDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.VoucherDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface VoucherService extends BaseService<Voucher, Long> {
    double applyDiscountOrder(ApplyDiscountOrderDto applyDiscountOrderDto) throws Exception;
    double applyDiscountShip(ApplyDiscountShipDto applyDiscountShipDto) throws Exception;

    Voucher save(VoucherDto voucherDto);
    void deactivateVoucher(Long id) throws DataExistsException, DataNotFoundException;
}
