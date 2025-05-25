package mft.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@SuperBuilder

public class Person implements Serializable {
    private int id;
    private String name;
    private String family;
    private LocalDate birthDate;
    private Role role;
    private boolean algorithmSkill;
    private boolean javaSkill;
    private Gender gender;
}
