package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.dto.CategoryDto;
import uz.pdp.entity.Category;
import uz.pdp.repository.CategoryRepository;
import uz.pdp.utils.ApiResponse;
import uz.pdp.utils.Utils;

import java.util.List;
import java.util.Optional;

import static uz.pdp.utils.ApiResponse.response;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<ApiResponse<List<Category>>> getAllInfo(Pageable pageable) {
        Page<Category> page = categoryRepository.findAll(pageable);
        List<Category> categoryList = page.getContent();
        if (categoryList.isEmpty()){
            return response(HttpStatus.NOT_FOUND);
        }
        Long totalCount = categoryRepository.count();
        return response(categoryList, totalCount);
    }


    public ResponseEntity<ApiResponse<Category>> getById(Long id) {
        Optional<Category> optionalCategory = Optional.of(categoryRepository.getById(id));
        return optionalCategory.map(ApiResponse::response).orElseGet(()->response(HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<ApiResponse<Category>> add(CategoryDto dto) {
        if (Utils.isEmpty(dto.getName())){
            return response("Category name should not be null!", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> categoryOptional = categoryRepository.findByName(dto.getName());
        if (categoryOptional.isPresent()){
            return response("This name is already exists!", HttpStatus.FORBIDDEN);
        }
        categoryRepository.save(Category.builder().name(dto.getName()).build());
        return ResponseEntity.ok(new ApiResponse<>("Category added"));
//        return response(categoryRepository.save(category), HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<Category>> edit(Long id, CategoryDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return response(HttpStatus.NOT_FOUND);
        }

        Category category = optionalCategory.get();
        category.setName(dto.getName());

        categoryRepository.save(category);
        return ResponseEntity.ok(new ApiResponse<>("Category edited"));
    }

    public ResponseEntity<ApiResponse<Category>> delete(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()){
            return response(HttpStatus.NOT_FOUND);
        }
        Category category = optionalCategory.get();
        categoryRepository.delete(category);
        return ResponseEntity.ok(new ApiResponse<>("Category deleted"));
    }
}





