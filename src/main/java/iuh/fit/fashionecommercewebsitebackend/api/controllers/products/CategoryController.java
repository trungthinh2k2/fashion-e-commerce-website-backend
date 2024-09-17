package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.CategoryDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.CategoryMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.*;
import iuh.fit.fashionecommercewebsitebackend.models.Category;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @CreateResponse
    @PostMapping
    public Response createCategory(@Valid @RequestBody CategoryDto categoryDto) throws DataExistsException {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Category created successfully",
                categoryService.save(category)
        );
    }

    @FindAllResponse
    @GetMapping
    public Response getAllCategories() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all categories successfully",
                categoryService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{id}")
    public Response getCategoryById(@PathVariable int id) throws DataExistsException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get category by id successfully",
                categoryService.findById(id).orElseThrow(() -> new DataExistsException("Category not found"))
        );
    }

    @FullUpdateResponse
    @PutMapping("/{id}")
    public Response updateCategory(@PathVariable int id, @Valid @RequestBody CategoryDto categoryDto) throws DataExistsException {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        category.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Category updated successfully",
                categoryService.update(id, category)
        );
    }

    @DeleteResponse
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable int id) {
        categoryService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Category deleted successfully with id: " + id
        );
    }
}