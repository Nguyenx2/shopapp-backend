package com.example.shopapp.Services;

import com.example.shopapp.DTOS.ProductDTO;
import com.example.shopapp.DTOS.ProductImageDTO;
import com.example.shopapp.Exceptions.DataNotFoundException;
import com.example.shopapp.Exceptions.InvalidParamException;
import com.example.shopapp.Models.Product;
import com.example.shopapp.Models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
    Product getProductById(Long id) throws DataNotFoundException;
    Page<Product> getAllProducts(PageRequest pageRequest);
    Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;

    void deleteProduct(Long id);
    boolean existByName(String name);
    ProductImage createProductImage (Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException;
}
