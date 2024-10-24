package iuh.fit.fashionecommercewebsitebackend.api.controllers.orders;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountShipDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.VoucherDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.orders.VoucherMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.DeleteResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
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
        Voucher voucher = voucherMapper.voucherDtoToVoucher(voucherDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Voucher created successfully",
                voucherService.save(voucher)
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

    @PatchMapping("/{id}")
    public Response updatePatch(@PathVariable Long id, @RequestBody Map<String, ?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Voucher updated patch successfully",
                voucherService.updatePatch(id, data)
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
}
