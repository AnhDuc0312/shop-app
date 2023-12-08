package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.reponses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


public interface IProductService {
     Product createProduct(ProductDTO productDTO) throws Exception;

    Product getProductById(long productID) throws Exception;
    Page<ProductResponse> getAllProducts(String keyword, Long categoryId,PageRequest pageRequest);
    Product updateProduct(long id , ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;
}
