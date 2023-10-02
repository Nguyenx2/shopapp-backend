package com.example.shopapp.Controllers;

import com.example.shopapp.DTOS.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page")   int page,
            @RequestParam("limit")  int limit
    ){
        return ResponseEntity.ok(String.format("getAllCategories, page = %d, limit = %d", page, limit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is update category");
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result){
        if (result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("This is insertCategory" + categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("deleteCategory with id = " + id);
    }
}
