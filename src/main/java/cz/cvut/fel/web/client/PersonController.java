package cz.cvut.fel.web.client;

import cz.cvut.fel.service.AccountService;
import cz.cvut.fel.web.client.dto.ClientAccountDto;
import cz.cvut.fel.web.client.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final AccountService accountService;
    
    @Autowired
    public PersonController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @GetMapping("/registration")
    public ClientAccountDto getRegistrationData() {
        return accountService.getRegistrationData();
    }
    
    @PostMapping("/sign-up")
    public LoginDto signUp(@RequestBody ClientAccountDto accountDto) {
        accountService.signUp(accountDto);
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(accountDto.getClient().getEmail());
        loginDto.setPassword(accountDto.getClient().getPassword());
        return loginDto;
    }
    
    @PutMapping("/change_registration_data")
    public void changeRegistrationData(@RequestBody ClientAccountDto accountDto) {
        accountService.signUp(accountDto);
    }
}
