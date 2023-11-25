package com.app.service.serviceImpl;

import com.app.entity.OrderDetail;
import com.app.entity.Product;
import com.app.exception.EntityNotFountException;
import com.app.payload.request.ProductQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.CloudinaryResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.ProductRepository;
import com.app.service.ProductService;
import com.app.speficication.ProductSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    public static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    ProductSpecification productSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public APIResponse filterProduct(ProductQueryParam productQueryParam) {
        Specification<Product> spec = productSpecification.getProductSpecification(productQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(productQueryParam);
        Page<Product> response = productRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse create(Product product, MultipartFile image) {
        if (image != null) {
            CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "product");
            product.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
            product.setImage(cloudinaryResponse.getUrl());
        }
        product = productRepository.save(product);
        return new SuccessAPIResponse(product);
    }

    @Override
    public APIResponse update(Product product, MultipartFile image) {
        if(product == null){
            return  new FailureAPIResponse("Product id is required!");
        }
        Product exists = productRepository.findById(product.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find product with id: "+product.getId());
        }
        if (image != null) {
            cloudinaryService.deleteFile(product.getCloudinaryId());
            CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "product");
            product.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
            product.setImage(cloudinaryResponse.getUrl());
        }
        product = productRepository.save(product);
        return new SuccessAPIResponse(product);
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            productRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse getMax() {
        return new APIResponse(productRepository.findMaxPrice());
    }



    @Override
    public APIResponse switchStatus(boolean isAvailable, Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFountException("Product not found with id: " + id);
        });
        product.setIsAvailable(isAvailable);
        productRepository.save(product);
        return new SuccessAPIResponse();
    }

    @Async("taskExecutor")
    @Override
    public void updateProductStockAfterCheckout(List<OrderDetail> orderDetails) {
        try {
            for (OrderDetail orderDetail : orderDetails) {
                Product product = orderDetail.getProduct();
                productRepository.updateProductStock(product.getStock() - orderDetail.getQuantity(),
                        product.getId());
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage() + ": " + "Cannot update product stock after checkout");
        }
    }
}
