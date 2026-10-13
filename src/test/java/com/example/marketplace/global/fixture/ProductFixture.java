package com.example.marketplace.global.fixture;

import com.example.marketplace.product.domain.Product;

import java.math.BigInteger;

public class ProductFixture {

    public static class Product1 {
        public static final long ID = 1L;
        public static final BigInteger CATEGORY_ID  = new BigInteger("1");
        public static final String NAME =  "Product 1";
        public static final long PRICE = 10000L;
        public static final String MAIN_IMG = "/img/main/1.png";
        public static final String DETAIL_IMG = "/img/detail/1.png";
        public static final BigInteger STOCK = new BigInteger("1000");
        public static final float SCORE = 5.0f;
        public static final BigInteger DELIVERY_FEE = new BigInteger("1000");
        public static final boolean EXPRESS = true;
        public static final boolean EXPRESS_FRESH = false;
        public static final boolean EXPRESS_GLOBAL = false;

        public static final Product PRODUCT = Product.builder()
                .id(ID)
                .categoryId(CATEGORY_ID)
                .name(NAME)
                .price(PRICE)
                .mainImg(MAIN_IMG)
                .detailImg(DETAIL_IMG)
                .stock(STOCK)
                .score(SCORE)
                .deliveryFee(DELIVERY_FEE)
                .express(EXPRESS)
                .expressFresh(EXPRESS_FRESH)
                .expressGlobal(EXPRESS_GLOBAL)
                .build();
    }

    public static class Product2 {
        public static final long ID = 2L;
        public static final BigInteger CATEGORY_ID  = new BigInteger("2");
        public static final String NAME =  "Product 2";
        public static final long PRICE = 20000L;
        public static final String MAIN_IMG = "/img/main/2.png";
        public static final String DETAIL_IMG = "/img/detail/2.png";
        public static final BigInteger STOCK = new BigInteger("2000");
        public static final float SCORE = 4.0f;
        public static final BigInteger DELIVERY_FEE = new BigInteger("2000");
        public static final boolean EXPRESS = false;
        public static final boolean EXPRESS_FRESH = true;
        public static final boolean EXPRESS_GLOBAL = false;

        public static final Product PRODUCT = Product.builder()
                .id(ID)
                .categoryId(CATEGORY_ID)
                .name(NAME)
                .price(PRICE)
                .mainImg(MAIN_IMG)
                .detailImg(DETAIL_IMG)
                .stock(STOCK)
                .score(SCORE)
                .deliveryFee(DELIVERY_FEE)
                .express(EXPRESS)
                .expressFresh(EXPRESS_FRESH)
                .expressGlobal(EXPRESS_GLOBAL)
                .build();
    }

    public static class Product3 {
        public static final long ID = 3L;
        public static final BigInteger CATEGORY_ID  = new BigInteger("3");
        public static final String NAME =  "Product 3";
        public static final long PRICE = 30000L;
        public static final String MAIN_IMG = "/img/main/3.png";
        public static final String DETAIL_IMG = "/img/detail/3.png";
        public static final BigInteger STOCK = new BigInteger("3000");
        public static final float SCORE = 3.0f;
        public static final BigInteger DELIVERY_FEE = new BigInteger("3000");
        public static final boolean EXPRESS = false;
        public static final boolean EXPRESS_FRESH = false;
        public static final boolean EXPRESS_GLOBAL = true;

        public static final Product PRODUCT = Product.builder()
                .id(ID)
                .categoryId(CATEGORY_ID)
                .name(NAME)
                .price(PRICE)
                .mainImg(MAIN_IMG)
                .detailImg(DETAIL_IMG)
                .stock(STOCK)
                .score(SCORE)
                .deliveryFee(DELIVERY_FEE)
                .express(EXPRESS)
                .expressFresh(EXPRESS_FRESH)
                .expressGlobal(EXPRESS_GLOBAL)
                .build();
    }

    public static class Product4 {
        public static final long ID = 4L;
        public static final BigInteger CATEGORY_ID  = new BigInteger("4");
        public static final String NAME =  "Product 4";
        public static final long PRICE = 0L;
        public static final String MAIN_IMG = "/img/main/4.png";
        public static final String DETAIL_IMG = "/img/detail/4.png";
        public static final BigInteger STOCK = new BigInteger("0");
        public static final float SCORE = 0f;
        public static final BigInteger DELIVERY_FEE = new BigInteger("0");
        public static final boolean EXPRESS = false;
        public static final boolean EXPRESS_FRESH = false;
        public static final boolean EXPRESS_GLOBAL = false;

        public static final Product PRODUCT = Product.builder()
                .id(ID)
                .categoryId(CATEGORY_ID)
                .name(NAME)
                .price(PRICE)
                .mainImg(MAIN_IMG)
                .detailImg(DETAIL_IMG)
                .stock(STOCK)
                .score(SCORE)
                .deliveryFee(DELIVERY_FEE)
                .express(EXPRESS)
                .expressFresh(EXPRESS_FRESH)
                .expressGlobal(EXPRESS_GLOBAL)
                .build();
    }
}
