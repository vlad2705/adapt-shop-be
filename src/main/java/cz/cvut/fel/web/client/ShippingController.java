package cz.cvut.fel.web.client;

import cz.cvut.fel.service.ShippingService;
import cz.cvut.fel.web.client.dto.ClientShippingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShippingController {
    
    private final ShippingService shippingService;
    
    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    
    @GetMapping(value = "/shipping")
    public List<ClientShippingDto> shipping() {
        return shippingService.getAll();
    }
}
