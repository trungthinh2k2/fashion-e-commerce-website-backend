package iuh.fit.fashionecommercewebsitebackend.api.controllers.orders;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountShipDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.VoucherDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProviderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.orders.VoucherMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.DeleteResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FullUpdateResponse;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherMapper voucherMapper;

    @CreateResponse
    @PostMapping
    public Response create(@RequestBody @Valid VoucherDto voucherDto) {
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Voucher created successfully",
                voucherService.save(voucherDto)
                );
    }

    @FindAllResponse
    @GetMapping
    @Operation(summary = "Get all vouchers", description = "Lấy danh sách khuyến mãi dành cho Quản lý")
    public Response getAll() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all vouchers successfully",
                voucherService.findAll()
        );
    }

    @DeleteResponse
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) throws DataNotFoundException {
        voucherService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Voucher deleted successfully",
                null
        );
    }
    @FullUpdateResponse
    @PutMapping("/{id}")
    public Response updateVoucher(@PathVariable long id, @Valid @RequestBody VoucherDto voucherDto) throws DataNotFoundException {
        Voucher voucher = voucherMapper.voucherDtoToVoucher(voucherDto);
        voucher.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Voucher updated successfully",
                voucherService.update(id, voucher)
        );
    }

    @PostMapping("/apply/for-order")
    public Response applyVoucherOrder(@RequestBody ApplyDiscountOrderDto applyDiscountOrderDto) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Voucher applied successfully",
                voucherService.applyDiscountOrder(applyDiscountOrderDto)
        );
    }

    @PostMapping("/apply/for-delivery")
    public Response applyVoucherShip(@RequestBody ApplyDiscountShipDto applyDiscountShipDto) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Voucher applied successfully",
                voucherService.applyDiscountShip(applyDiscountShipDto)
        );
    }
    @PutMapping("/delete/{id}")
    @Operation(summary = "Deactivate voucher", description = "Deactivate voucher by id")
    public Response deactivateVoucher(@PathVariable Long id) throws DataExistsException, DataNotFoundException {
        voucherService.deactivateVoucher(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Voucher deactivate successfully",
                null
        );
    }
}
