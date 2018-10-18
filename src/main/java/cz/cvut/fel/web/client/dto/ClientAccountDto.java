package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.BaseAccountDto;

public class ClientAccountDto extends BaseAccountDto {
    private ClientDto client;
    
    public ClientDto getClient() {
        return client;
    }
    
    public void setClient(ClientDto client) {
        this.client = client;
    }
}
