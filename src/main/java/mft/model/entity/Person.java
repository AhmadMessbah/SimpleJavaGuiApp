package mft.model.entity;

import mft.controller.exception.InvalidPersonDataException;
import mft.model.entity.enums.Gender;
import mft.model.entity.enums.Role;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.regex.Pattern;

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

//    public Person() {
//    }

    public Person(int id, String name, String family, String username, String password, LocalDate birthDate, Role role, boolean algorithmSkill, boolean javaSkill, Gender gender) throws InvalidPersonDataException {
        setName(name);
        setFamily( family);
        setUsername(username);
        setPassword(password);
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
        this.algorithmSkill = algorithmSkill;
        this.javaSkill = javaSkill;
        this.gender = gender;
    }

    public Person(String name, String family, String username, String password, LocalDate birthDate, Role role, boolean algorithmSkill, boolean javaSkill, Gender gender) throws InvalidPersonDataException {
        setName(name);
        setFamily( family);
        setUsername(username);
        setPassword(password);
        this.birthDate = birthDate;
        this.role = role;
        this.algorithmSkill = algorithmSkill;
        this.javaSkill = javaSkill;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

        public void setName(String name) throws InvalidPersonDataException {
        if(Pattern.matches("^[a-zA-Z\\s]{3,30}$", name)) {
            this.name = name;
        }else{
            throw new InvalidPersonDataException("Invalid Name !!!");
        }
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) throws InvalidPersonDataException {
        if(Pattern.matches("^[a-zA-Z\\s]{3,30}$", family)) {
            this.family = family;
        }else{
            throw new InvalidPersonDataException("Invalid Family !!!");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws InvalidPersonDataException {
        if(Pattern.matches("^[a-zA-Z\\d_]{6,20}$", username)) {
            this.username = username;
        }else{
            throw new InvalidPersonDataException("Invalid Username !!!");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidPersonDataException {
        if(Pattern.matches("^(?=.*[a-zA-Z]{2})(?=.*[0-9]{2})(?=.*[@#$&]{2}).{8,20}$", password)) {
            this.password = password;
        }else{
            throw new InvalidPersonDataException("Invalid Password !!!");
        }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAlgorithmSkill() {
        return algorithmSkill;
    }

    public void setAlgorithmSkill(boolean algorithmSkill) {
        this.algorithmSkill = algorithmSkill;
    }

    public boolean isJavaSkill() {
        return javaSkill;
    }

    public void setJavaSkill(boolean javaSkill) {
        this.javaSkill = javaSkill;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
