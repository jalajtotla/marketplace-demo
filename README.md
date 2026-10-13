# Online Marketplace

A Java / Spring Boot backend for an e-commerce marketplace: users, products, carts, orders, payments, coupons and reviews.

## Tech stack
- Java 11, Spring Boot 2.5
- MyBatis + MySQL
- Redis (session store and cache)
- S3-compatible object storage for review images
- Gradle, JUnit 5, Mockito, Checkstyle

## Modules
`user` · `auth` · `product` · `cart` · `order` · `payment` · `coupon` · `review` · `address` · `global` (config, errors, utils)

## Running locally
1. Create a MySQL database named `marketplace` and apply `src/main/resources/db/schema.sql`.
2. Fill in the datasource, Redis and storage values in `src/main/resources/application-local.yml`.
3. `./gradlew bootRun`

## Tests
`./gradlew test`
