package mft.model.repository;

import lombok.extern.log4j.Log4j2;
import mft.model.entity.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class PersonDataAccess {
    public void savePerson(Person person) throws IOException {
        PersonDataFileManager.getManager().addPerson(person);
        PersonDataFileManager.getManager().saveToFile();
        log.info("Person Saved");
    }

    public void editPerson(Person person) throws IOException {
        List<Person> personList = PersonDataFileManager.getManager().getPersonList();
        for (int i=0; i<personList.size(); i++) {
            if (personList.get(i).getId() == person.getId()) {
                personList.set(i, person);
                break;
            }
        }
        PersonDataFileManager.getManager().saveToFile();
        log.info("Person Edited");
    }

    public void removePerson(int id) throws IOException {
        List<Person> personList = PersonDataFileManager.getManager().getPersonList();
        for (int i=0; i<personList.size(); i++) {
            if (personList.get(i).getId() == id) {
                personList.remove(i);
                break;
            }
        }
        PersonDataFileManager.getManager().saveToFile();
        log.info("Person Removed");
    }

    public List<Person> getAllPersons() throws IOException, ClassNotFoundException {
        List<Person> personList = PersonDataFileManager.getManager().readFromFile();
        log.info("Get All Persons");
        return personList;
    }

    public List<Person> getPersonsById(int id) throws IOException, ClassNotFoundException {
        List<Person> personList = PersonDataFileManager.getManager().readFromFile();
        List<Person> persons = personList.stream().filter(person -> person.getId() == id).collect(Collectors.toList());
        log.info("Get All Persons By Id " + id);
        return persons;
    }

    public List<Person> getPersonsByNameAndFamily(String name, String family) throws
            IOException, ClassNotFoundException {
        List<Person> personList = PersonDataFileManager.getManager().readFromFile();
        List<Person> persons = personList.stream().filter(person -> (person.getName().startsWith(name) && person.getFamily().startsWith(family))).collect(Collectors.toList());
        log.info("Get All Persons By Name and Family " + name + " " + family);
        return persons;
    }

}
