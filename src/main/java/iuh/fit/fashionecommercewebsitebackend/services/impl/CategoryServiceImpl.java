package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Category;
import iuh.fit.fashionecommercewebsitebackend.repositories.CategoryRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Integer> implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(JpaRepository<Category, Integer> repository) {
        super(repository, Category.class);
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category checkExistsCategoryName(String categoryName) throws DataExistsException {
        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new DataExistsException("Category name already exists");
        }
        return null;
    }
}
