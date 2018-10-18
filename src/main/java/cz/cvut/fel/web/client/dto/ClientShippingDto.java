package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.NamedDto;

import java.math.BigDecimal;

public class ClientShippingDto extends NamedDto<Long> {
    private BigDecimal cost;
    
    public BigDecimal getCost() {
        return cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
