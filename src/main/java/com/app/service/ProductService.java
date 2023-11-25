package com.app.service;

import com.app.entity.OrderDetail;
import com.app.entity.Product;
import com.app.payload.request.ProductQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    APIResponse filterProduct (ProductQueryParam productQueryParam);
    APIResponse create(Product product, MultipartFile image);
    APIResponse update(Product product, MultipartFile image);
    APIResponse delete(Integer id);
    APIResponse getMax();
    APIResponse switchStatus(boolean isAvailable, Integer id);
    void updateProductStockAfterCheckout(List<OrderDetail> orderDetails);
}
