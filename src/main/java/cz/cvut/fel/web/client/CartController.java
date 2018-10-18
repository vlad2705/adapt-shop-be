package cz.cvut.fel.web.client;

import cz.cvut.fel.service.ProductOrderService;
import cz.cvut.fel.web.client.dto.CartProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    private final ProductOrderService productOrderService;
    
    @Autowired
    public CartController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }
    
    @GetMapping
    public List<CartProductDto> products() {
        return productOrderService.getCart();
    }
    
    @PostMapping(value = "/product/{productId}")
    public long products(@PathVariable Long productId) {
        return productOrderService.save(productId);
    }
    
    @PatchMapping(value = "/product/{productId}/quantity/{quantity}")
    public void changeQuantity(@PathVariable Long productId, @PathVariable Integer quantity) {
        productOrderService.changeQuantity(productId, quantity);
    }
    
    @DeleteMapping(value = "/product/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productOrderService.deleteProduct(productId);
    }
}
