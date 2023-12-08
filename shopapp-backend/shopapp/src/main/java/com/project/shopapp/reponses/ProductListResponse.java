package com.project.shopapp.reponses;

import com.project.shopapp.models.Product;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}
