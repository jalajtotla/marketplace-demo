package com.example.marketplace.product.repository;

import com.example.marketplace.product.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface ProductRepository {

    List<Product> getProductsByIsExpress(boolean isExpress, int startId, int listSize);

    List<Product> getProductsByIsExpressFresh(boolean isExpressFresh, int startId, int listSize);

    List<Product> getProductsByIsExpressGlobal(boolean isExpressGlobal, int startId, int listSize);

    List<Product> getProductsByIsExpressAndIsExpressFresh(boolean isExpress, boolean isExpressFresh, int startId, int listSize);

    List<Product> getProductsByIsExpressAndIsExpressGlobal(boolean isExpress, boolean isExpressGlobal, int startId, int listSize);

    List<Product> getProductsByKeyword(String keyword);

    Optional<Product> findByProductId(long id);
}
