package com.example.marketplace.product.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class Product {

    private long id;
    private BigInteger categoryId;
    private String name;
    private long price;
    private String mainImg;
    private String detailImg;
    private BigInteger stock;
    private Float score;
    private BigInteger deliveryFee;
    private Boolean express;
    private Boolean expressFresh;
    private Boolean expressGlobal;

    @Builder
    public Product(long id, BigInteger categoryId, String name, long price, String mainImg, String detailImg, BigInteger stock, Float score, BigInteger deliveryFee, Boolean express, Boolean expressFresh, Boolean expressGlobal) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.mainImg = mainImg;
        this.detailImg = detailImg;
        this.stock = stock;
        this.score = score;
        this.deliveryFee = deliveryFee;
        this.express = express;
        this.expressFresh = expressFresh;
        this.expressGlobal = expressGlobal;
    }
}
