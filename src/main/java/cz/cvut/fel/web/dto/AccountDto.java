package cz.cvut.fel.web.dto;

public class AccountDto extends BaseAccountDto {

    private PersonDto personDto;
    
    public AccountDto() {
        this.personDto = new PersonDto();
    }
    
    public PersonDto getPersonDto() {
        return personDto;
    }

    public void setPersonDto(PersonDto personDto) {
        this.personDto = personDto;
    }
}
