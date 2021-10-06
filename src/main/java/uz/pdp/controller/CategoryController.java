package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dto.CategoryDto;
import uz.pdp.entity.Category;
import uz.pdp.service.CategoryService;
import uz.pdp.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    @PreAuthorize("hasAuthority('GET_ALL')")
    @GetMapping("/getAllInfo")
    public ResponseEntity<ApiResponse<List<Category>>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "5") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.getAllInfo(pageable);
    }

    @PreAuthorize("hasAuthority('GET')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getId(@PathVariable Long id){
        return categoryService.getById(id);
    }


    @PreAuthorize("hasAuthority('ADD')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Category>> add(@RequestBody CategoryDto dto){
        return categoryService.add(dto);
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<Category>> edit(@PathVariable Long id, @RequestBody CategoryDto dto){
        return categoryService.edit(id, dto);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Category>> delete(@PathVariable Long id){
        return categoryService.delete(id);
    }

}
