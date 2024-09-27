package iuh.fit.fashionecommercewebsitebackend.api.controllers.orders;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.UserVoucherDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.orders.UserVoucherMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.models.UserVoucher;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.UserVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userVouchers")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class UserVoucherController {

    private final UserVoucherService userVoucherService;
    private final UserVoucherMapper userVoucherMapper;

    @CreateResponse
    @PostMapping
    public Response createUserVoucher(UserVoucherDto userVoucherDto) throws DataNotFoundException {
        UserVoucher userVoucher = userVoucherMapper.userVoucherDtoToUserVoucher(userVoucherDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Create user voucher successfully",
                userVoucherService.save(userVoucher)
        );
    }
}
