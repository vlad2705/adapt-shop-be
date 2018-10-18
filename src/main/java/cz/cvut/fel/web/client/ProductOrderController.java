package cz.cvut.fel.web.client;

import cz.cvut.fel.service.ProductOrderService;
import cz.cvut.fel.web.client.dto.ClientProductOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductOrderController {
    
    private final ProductOrderService productOrderService;
    
    @Autowired
    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }
    
    @GetMapping(value = "/product_order")
    public ClientProductOrderDto getOrder() {
        return productOrderService.getOrder();
    }
    
    @PatchMapping(value = "/change_shipping/{shippingId}")
    public void changeShipping(@PathVariable Long shippingId) {
        this.productOrderService.changeShipping(shippingId);
    }
    
    @PatchMapping(value = "/product_order/accept")
    public void accessOrder() {
        this.productOrderService.acceptOrder();
    }
}
