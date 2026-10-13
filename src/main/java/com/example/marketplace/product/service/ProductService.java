package com.example.marketplace.product.service;

import com.example.marketplace.product.constant.DeliveryTypeEnum;
import com.example.marketplace.product.controller.dto.GetProductsRequest;
import com.example.marketplace.product.controller.dto.SimpleProduct;
import com.example.marketplace.product.domain.Product;
import com.example.marketplace.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<SimpleProduct> getProducts(GetProductsRequest dto){
        DeliveryTypeEnum deliveryType = dto.getDeliveryType();
        boolean isExpress = dto.isExpress();
        int listSize = dto.getListSize();
        int startId = dto.getStart();

        List<Product> products = getProductsByDeliveryType(deliveryType, isExpress, listSize, startId);

        return SimpleProduct.toList(products);
    }

    private List<Product> getProductsByDeliveryType (DeliveryTypeEnum deliveryType, boolean isExpress, int listSize, int startId) {
        List<Product> products = new ArrayList<>();

        switch (deliveryType) {
            case EXPRESS:
                products = productRepository.getProductsByIsExpress(true, startId, listSize);
                break;
            case EXPRESS_FRESH:
                if (isExpress) {
                    products = productRepository.getProductsByIsExpressAndIsExpressFresh(true, true, startId, listSize);
                } else {
                    products = productRepository.getProductsByIsExpressFresh(true, startId, listSize);
                }
                break;
            case EXPRESS_GLOBAL:
                if (isExpress) {
                    products = productRepository.getProductsByIsExpressAndIsExpressGlobal(true, true, startId, listSize);
                } else {
                    products = productRepository.getProductsByIsExpressGlobal(true, startId, listSize);
                }
                break;
        }
        return products;
    }

    public List<SimpleProduct> searchProductsByKeyword(String keyword) {
        List<Product> products = productRepository.getProductsByKeyword(keyword);
        return SimpleProduct.toList(products);
    }

    public boolean checkIsProductExist(long id){
        return productRepository.findByProductId(id).isPresent();
    }
}
