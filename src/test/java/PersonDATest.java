import mft.model.entity.Person;
import mft.model.entity.enums.Gender;
import mft.model.entity.enums.Role;
import mft.model.repository.PersonDA;

import java.time.LocalDate;

public class PersonDATest {
    public static void main(String[] args) throws Exception {
        Person person =
                Person
                        .builder()
                        .name("mohsen")
                        .family("Akbari")
                        .username("Admin11")
                        .password("Admin123")
                        .birthDate(LocalDate.now())
                        .role(Role.Admin)
                        .gender(Gender.Male)
                        .build();
        try (PersonDA personDA = new PersonDA()) {
            personDA.save(person);
//            personDA.edit(person);
//            personDA.delete(0);

//            for (Person person1 : personDA.findAll()) {
//                System.out.println(person1);
//            }

            System.out.println(personDA.findById(1));

//            System.out.println(personDA.findByNameAndFamily("a", ""));

//            System.out.println(personDA.login("ahmad1111", "ahmad123"));
        }
    }
}
