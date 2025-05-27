package mft.model.repository;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import mft.model.entity.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PersonDataFileManager {
    private final String FILE_NAME = "src/main/java/mft/model/repository/datafile/person.dat";
    File file = new File(FILE_NAME);
    private static int nextId;

    private static List<Person> personList;

    @Getter
    private static final PersonDataFileManager manager = new PersonDataFileManager();

    public List<Person> getPersonList() {
        return personList;
    }

    public void addPerson(Person person) {
        if(personList == null) {
            personList = new ArrayList<>();
        }
        personList.add(person);
    }

    public int getNextId() {
        nextId++;
        return nextId;
    }

    private PersonDataFileManager() {
    }

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
}
