package mft.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mft.controller.exception.InvalidPersonDataException;
import mft.model.entity.enums.Gender;
import mft.model.entity.enums.Role;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.regex.Pattern;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter

public class Person implements Serializable {
    private int id;
    private String name;
    private String family;
    private String username;
    private String password;
    private LocalDate birthDate;
    private Role role;
    private boolean algorithmSkill;
    private boolean javaSkill;
    private Gender gender;

    public void setName(String name) throws InvalidPersonDataException {
        if(Pattern.matches("^[a-zA-Z\\s]{3,30}$", name)) {
            this.name = name;
        }else{
            throw new InvalidPersonDataException("Invalid Name !!!");
        }
    }

    public void setFamily(String family) throws InvalidPersonDataException {
        if(Pattern.matches("^[a-zA-Z\\s]{3,30}$", family)) {
            this.family = family;
        }else{
            throw new InvalidPersonDataException("Invalid Family !!!");
        }
    }

    public void setUsername(String username) throws InvalidPersonDataException {
        if(Pattern.matches("^[a-zA-Z\\d_]{6,20}$", username)) {
            this.username = username;
        }else{
            throw new InvalidPersonDataException("Invalid Username !!!");
        }
    }


    public void setPassword(String password) throws InvalidPersonDataException {
        if(Pattern.matches("^(?=.*[a-zA-Z]{2})(?=.*[0-9]{2})(?=.*[@#$&]{2}).{8,20}$", password)) {
            this.password = password;
        }else{
            throw new InvalidPersonDataException("Invalid Password !!!");
        }
    }
}
