package cz.cvut.fel.service;

import cz.cvut.fel.model.Account;
import cz.cvut.fel.web.client.dto.ClientAccountDto;
import cz.cvut.fel.web.client.dto.ClientDto;
import cz.cvut.fel.web.data.AccountData;
import cz.cvut.fel.web.dto.AccountDto;
import cz.cvut.fel.web.dto.BaseAccountDto;
import cz.cvut.fel.web.filter.AccountFilter;

public interface AccountService extends BaseService<AccountData, AccountFilter> {
    
    AccountDto convertToDto(Account account);
    
    Account convertToModel(AccountDto accountDto);
    
    Account convertClientToModel(ClientAccountDto accountDto);
    
    Account convertToModel(Account account, AccountDto accountDto);
    
    Account convertClientToModel(Account account, ClientAccountDto accountDto);
    
    AccountDto save(AccountDto accountDto);
    
    ClientDto signUp(ClientAccountDto clientAccountDto);
    
    void delete(long id);
    
    AccountDto getById(long id);
    
    AccountDto getById(String id);
    
    AccountData getDataById(long id);
    
    AccountData convertToData(Account account);
    
    BaseAccountDto getBaseDtoByPersonId(long personId);
    
    BaseAccountDto convertToBaseDto(Account account);
    
    ClientAccountDto getRegistrationData();
}
