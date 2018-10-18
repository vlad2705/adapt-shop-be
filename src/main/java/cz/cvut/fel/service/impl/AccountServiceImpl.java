package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Account;
import cz.cvut.fel.model.Person;
import cz.cvut.fel.repository.AccountRepository;
import cz.cvut.fel.service.AccountService;
import cz.cvut.fel.service.AddressService;
import cz.cvut.fel.service.PersonService;
import cz.cvut.fel.web.client.dto.ClientAccountDto;
import cz.cvut.fel.web.client.dto.ClientDto;
import cz.cvut.fel.web.data.AccountData;
import cz.cvut.fel.web.dto.AccountDto;
import cz.cvut.fel.web.dto.BaseAccountDto;
import cz.cvut.fel.web.filter.AccountFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    
    private final PersonService personService;
    private final AddressService addressService;
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountServiceImpl(PersonService personService, AddressService addressService, AccountRepository accountRepository) {
        this.personService = personService;
        this.addressService = addressService;
        this.accountRepository = accountRepository;
    }
    
    @Override
    public AccountDto convertToDto(Account account) {
        if (account != null) {
            AccountDto accountDto = new AccountDto();
            accountDto.setId(account.getId());
            accountDto.setFirstName(account.getFirstName());
            accountDto.setLastName(account.getLastName());
            accountDto.setPhone(account.getPhone());
            accountDto.setPersonDto(personService.convertToDto(account.getPerson()));
            accountDto.setAddress(addressService.convertToDto(account.getAddress()));
            return accountDto;
        }
        return null;
    }
    
    @Override
    public Account convertToModel(AccountDto accountDto) {
        if (accountDto != null) {
            return accountDto.getId() != null
                    ? convertToModel(accountRepository.findById(accountDto.getId()), accountDto)
                    : convertToModel(new Account(), accountDto);
        }
        return null;
    }
    
    @Override
    public Account convertClientToModel(ClientAccountDto accountDto) {
        if (accountDto != null) {
            return accountDto.getId() != null
                    ? convertClientToModel(accountRepository.findById(accountDto.getId()), accountDto)
                    : convertClientToModel(new Account(), accountDto);
        }
        return null;
    }
    
    @Override
    public Account convertToModel(Account account, AccountDto accountDto) {
        if (accountDto != null) {
            account.setFirstName(accountDto.getFirstName());
            account.setLastName(accountDto.getLastName());
            account.setPhone(accountDto.getPhone());
            account.setPerson(personService.save(accountDto.getPersonDto()));
            account.setAddress(addressService.save(accountDto.getAddress()));
            return account;
        }
        return null;
    }
    
    @Override
    public Account convertClientToModel(Account account, ClientAccountDto accountDto) {
        if (accountDto != null) {
            account.setFirstName(accountDto.getFirstName());
            account.setLastName(accountDto.getLastName());
            account.setPhone(accountDto.getPhone());
            account.setPerson(personService.saveClient(accountDto.getClient()));
            account.setAddress(addressService.save(accountDto.getAddress()));
            return account;
        }
        return null;
    }
    
    @Override
    public AccountDto save(AccountDto accountDto) {
        if (accountDto != null) {
            Account account = convertToModel(accountDto);
            accountRepository.save(account);
            return convertToDto(account);
        }
        return null;
    }
    
    @Override
    public ClientDto signUp(ClientAccountDto clientAccountDto) {
        if (clientAccountDto != null) {
            Account account = convertClientToModel(clientAccountDto);
            accountRepository.save(account);
            return clientAccountDto.getClient();
        }
        return null;
    }
    
    @Override
    public void delete(long id) {
        this.accountRepository.delete(this.accountRepository.findById(id));
    }
    
    @Override
    public AccountDto getById(long id) {
        return convertToDto(accountRepository.findById(id));
    }
    
    @Override
    public AccountDto getById(String id) {
        return id == null || id.isEmpty() ? new AccountDto() : getById(Long.valueOf(id));
    }
    
    @Override
    public long getRowCount(AccountFilter filter) {
        return accountRepository.findRowCount(filter);
    }
    
    @Override
    public List<AccountData> getByFilter(AccountFilter filter) {
        return accountRepository.findByFilter(filter);
    }
    
    @Override
    public AccountData getDataById(String id) {
        return id == null || id.isEmpty() ? new AccountData() : getDataById(Long.valueOf(id));
    }
    
    @Override
    public AccountData getDataById(long id) {
        return convertToData(accountRepository.findById(id));
    }
    
    @Override
    public AccountData convertToData(Account account) {
        if (account != null) {
            AccountData accountData = new AccountData();
            accountData.setId(account.getId());
            accountData.setFirstName(account.getFirstName());
            accountData.setLastName(account.getLastName());
            accountData.setEmail(account.getPerson().getEmail());
            return accountData;
        }
        return null;
    }
    
    @Override
    public BaseAccountDto getBaseDtoByPersonId(long personId) {
        return convertToBaseDto(accountRepository.findByPersonId(personId));
    }
    
    @Override
    public BaseAccountDto convertToBaseDto(Account account) {
        if (account != null) {
            BaseAccountDto accountDto = new BaseAccountDto();
            accountDto.setId(account.getId());
            accountDto.setFirstName(account.getFirstName());
            accountDto.setLastName(account.getLastName());
            accountDto.setPhone(account.getPhone());
            accountDto.setAddress(addressService.convertToDto(account.getAddress()));
            return accountDto;
        }
        return null;
    }
    
    @Override
    public ClientAccountDto getRegistrationData() {
        Person person = personService.getAuthorizationPerson();
        if (person != null) {
            Account account = accountRepository.findByPersonId(person.getId());
            return convertToClientDto(account, person);
        }
        return null;
    }
    
    public ClientAccountDto convertToClientDto(Account account, Person person) {
        if (account != null) {
            ClientAccountDto accountDto = new ClientAccountDto();
            accountDto.setId(account.getId());
            accountDto.setFirstName(account.getFirstName());
            accountDto.setLastName(account.getLastName());
            accountDto.setPhone(account.getPhone());
            accountDto.setClient(personService.convertToClientDto(person));
            accountDto.setAddress(addressService.convertToDto(account.getAddress()));
            return accountDto;
        }
        return null;
    }
}
