package mft.model.repository;

import lombok.extern.log4j.Log4j2;
import mft.controller.exception.UserNotFoundException;
import mft.model.entity.Person;
import mft.model.entity.enums.Gender;
import mft.model.entity.enums.Role;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PersonDA implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public PersonDA() throws SQLException {
        log.info("Connect");
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    public void save(Person person) throws SQLException {
        preparedStatement = connection.prepareStatement("select person_seq.nextval from dual");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        person.setId(resultSet.getInt("nextval"));

        preparedStatement = connection.prepareStatement("insert into persons values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, person.getId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getFamily());
        preparedStatement.setString(4, person.getUsername());
        preparedStatement.setString(5, person.getPassword());
        preparedStatement.setDate(6, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(7, person.getRole().name());
        preparedStatement.setBoolean(8, person.isAlgorithmSkill());
        preparedStatement.setBoolean(9, person.isJavaSkill());
        preparedStatement.setString(10, person.getGender().name());
        preparedStatement.execute();
        log.info("Person Saved " + person);
    }

    public void edit(Person person) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "update persons set name=?, family=?, username=?, password=?, birth_date=?, role=?, algorithm_skill=?, java_skill=?, gender=? where id=?"
        );
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setString(3, person.getUsername());
        preparedStatement.setString(4, person.getPassword());
        preparedStatement.setDate(5, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(6, person.getRole().name());
        preparedStatement.setBoolean(7, person.isAlgorithmSkill());
        preparedStatement.setBoolean(8, person.isJavaSkill());
        preparedStatement.setString(9, person.getGender().name());
        preparedStatement.setInt(10, person.getId());
        preparedStatement.execute();
        log.info("Person Edited " + person);
    }

    public void delete(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "delete from persons where id=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        log.info("Person Removed " + id);
    }

    public List<Person> findAll() throws SQLException {
        List<Person> personList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from persons");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Person person =
                    Person
                            .builder()
                            .id(resultSet.getInt("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .username(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                            .role(Role.valueOf(resultSet.getString("ROLE")))
                            .algorithmSkill(resultSet.getBoolean("ALGORITHM_SKILL"))
                            .javaSkill(resultSet.getBoolean("JAVA_SKILL"))
                            .gender(Gender.valueOf(resultSet.getString("GENDER")))
                            .build();
            personList.add(person);
        }
        log.info("Find All Persons");
        return personList;
    }

    public Person findById(int id) throws SQLException, UserNotFoundException {
        Person person = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from persons where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .role(Role.valueOf(resultSet.getString("ROLE")))
                    .algorithmSkill(resultSet.getBoolean("ALGORITHM_SKILL"))
                    .javaSkill(resultSet.getBoolean("JAVA_SKILL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .build();
        }
        if (person == null) {
            log.error("No User With Id " + id);
            throw new UserNotFoundException("No User With Id " + id);
        }
        log.info("Find Person By Id " + id);
        return person;
    }

    public List<Person> findByNameAndFamily(String name, String family) throws SQLException, UserNotFoundException {
        List<Person> personList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from persons where name like ? and family like ?");
        preparedStatement.setString(1, name + "%");
        preparedStatement.setString(2, family + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Person person =
                    Person
                            .builder()
                            .id(resultSet.getInt("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .username(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                            .role(Role.valueOf(resultSet.getString("ROLE")))
                            .algorithmSkill(resultSet.getBoolean("ALGORITHM_SKILL"))
                            .javaSkill(resultSet.getBoolean("JAVA_SKILL"))
                            .gender(Gender.valueOf(resultSet.getString("GENDER")))
                            .build();
            personList.add(person);
        }
        if (personList.isEmpty()) {
            log.error("No User With name and family (starts with) : " + name + " " + family);
            throw new UserNotFoundException("No User With name and family (starts with) : " + name + " " + family);
        }
        log.info("Find All Persons By Name and Family " + name + " " + family);
        return personList;
    }

    public Person login(String username, String password) throws UserNotFoundException, SQLException {
        Person person = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from persons where username=? and password=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .role(Role.valueOf(resultSet.getString("ROLE")))
                    .algorithmSkill(resultSet.getBoolean("ALGORITHM_SKILL"))
                    .javaSkill(resultSet.getBoolean("JAVA_SKILL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .build();
        }
        if (person == null) {
            log.error("Login - No User With username/password " + username + ":" + password);
            throw new UserNotFoundException("No User With username/password");
        }
        log.info("Login " + person);
        return person;
    }

    @Override
    public void close() throws Exception {
        log.info("Disconnect");
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}
