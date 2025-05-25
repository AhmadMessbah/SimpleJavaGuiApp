package mft.model;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class PersonDataAccess {
    private final String FILE_NAME = "src/main/java/mft/model/datafile/person.dat";
    private List<Person> personList = new ArrayList<>();
    File file = new File(FILE_NAME);

    public void saveToFile() throws IOException {
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(personList);

        objectOutputStream.close();
        fileOutputStream.close();
        log.info("Data Saved To File");
    }

    public List<Person> readFromFile() throws IOException, ClassNotFoundException {
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            personList = (ArrayList<Person>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
            log.info("Data Read From File");
        }
        return personList;
    }

    public int getNextId() {
        int nextId = personList.size() + 1;
        log.info("New Id Generated");
        return nextId;
    }

    public void savePerson(Person person) throws IOException {
        personList.add(person);
        saveToFile();
        log.info("Person Saved");
    }

    public void editPerson(Person person) {
    }

    public void removePerson(Person person) {
    }

    public List<Person> getAllPersons() throws IOException, ClassNotFoundException {
        personList = readFromFile();
        log.info("Get All Persons");
        return personList;
    }

    public List<Person> getPersonsById(int id) throws IOException, ClassNotFoundException {
        personList = readFromFile();
        List<Person> persons = personList.stream().filter(person -> person.getId() == id).collect(Collectors.toList());
        log.info("Get All Persons By Id " + id);
        return persons;
    }

    public List<Person> getPersonsByNameAndFamily(String name, String family) throws IOException, ClassNotFoundException {
        personList = readFromFile();
        List<Person> persons = personList.stream().filter(person -> (person.getName().startsWith(name) && person.getFamily().startsWith(family))).collect(Collectors.toList());
        log.info("Get All Persons By Name and Family " + name + " " + family);
        return persons;
    }

}
