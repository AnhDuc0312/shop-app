package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.reponses.UpdateCategoryResponse;
import com.project.shopapp.services.CategoryService;
import com.project.shopapp.utils.LocalizationUtils;
import com.project.shopapp.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final LocaleResolver localeResolver;
    private final MessageSource messageSource;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<String> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ){
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList()
                    ;
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Insert category successfully");
    }
    //Hiển thị tất cả các categories
    @GetMapping("") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }



    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryResponse> updateCategories(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO,
            HttpServletRequest request){
        Locale locale = localeResolver.resolveLocale(request);
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(UpdateCategoryResponse.builder()
                        .message(messageSource.getMessage("category.update_category.update_successfully", null , locale))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_CATEGORY_SUCCESSFULLY));
    }
}
