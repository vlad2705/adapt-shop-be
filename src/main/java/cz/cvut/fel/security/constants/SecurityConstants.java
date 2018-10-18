package cz.cvut.fel.security.constants;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 86_400_000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/sign-up";
    public static final String PRODUCTS_URL = "/api/products";
    public static final String CATEGORIES_URL = "/api/categories";
    public static final String PRODUCT_URL = "/api/product/**";
}