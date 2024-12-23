package iuh.fit.fashionecommercewebsitebackend.api.controllers.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.ChangePasswordDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserUpdateDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.*;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.UserService;
import iuh.fit.fashionecommercewebsitebackend.utils.S3Upload;
import iuh.fit.fashionecommercewebsitebackend.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class UserController {

    private final UserService userService;
    private final ValidToken validToken;
    private final S3Upload s3Upload;

    @FindAllResponse
    @GetMapping
    public Response getAllUsers() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all users successfully",
                userService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{email}")
    public Response getUserByEmail(@PathVariable String email) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get user by email successfully",
                userService.getUserByEmail(email)
        );
    }

    @FullUpdateResponse
    @PutMapping("/{email}")
    public Response updateUser(@PathVariable String email, @RequestBody @Valid UserUpdateDto userUpdateDto, HttpServletRequest request) throws DataNotFoundException {
        validToken.validToken(email, request);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "User updated successfully",
                userService.updateUser(email, userUpdateDto)
        );
    }

    @FullUpdateResponse
    @PutMapping("/upload")
    public Response updateAvatar(@RequestParam("avatar") MultipartFile file) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "User updated avatar successfully",
                s3Upload.uploadFile(file)
        );
    }


    @UpdateOptionsResponse
    @PatchMapping("/{id}")
    public Response patchUser(@PathVariable Long id, @RequestBody Map<String, ?> data, HttpServletRequest request) throws DataNotFoundException {
        validToken.validToken(id, request);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "User updated successfully",
                userService.updatePatch(id, data)
        );
    }

    @Operation(summary = "Đổi mật khẩu", description = "Đổi mật khẩu khi đã đăng nhập")
    @GeneralResponse
    @PostMapping("/change-password")
    public Response changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws Exception {
        userService.changePassword(changePasswordDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Change password successfully",
                null
        );
    }

    @GetMapping("/page-users")
    public Response pageComments(@RequestParam(defaultValue = "1") int pageNo,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(required = false) String[] search,
                                 @RequestParam(required = false) String[] sort) throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get user successfully",
                userService.getDataWithPage(pageNo, pageSize, search, sort)
        );
    }



}
