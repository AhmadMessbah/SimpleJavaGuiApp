package mft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import mft.model.Gender;
import mft.model.Person;
import mft.model.PersonDataAccess;
import mft.model.Role;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class PersonController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, familyTxt, nameSearchTxt, familySearchTxt;

    @FXML
    private DatePicker birthDate;

    @FXML
    private ComboBox<Role> roleCmb;

    @FXML
    private CheckBox algoChk, javaChk;

    @FXML
    private ToggleGroup genderToggleGroup;

    @FXML
    private RadioButton maleRdo, femaleRdo;

    @FXML
    private Button saveBtn, editBtn, removeBtn, clearBtn;

    @FXML
    private TableView<Person> personTbl;

    @FXML
    private TableColumn<Person, Integer> idCol;

    @FXML
    private TableColumn<Person, String> nameCol, familyCol;

    @FXML
    private TableColumn<Person, Role> roleCol;

    private PersonDataAccess personDataAccess = new PersonDataAccess();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Role role : Role.values()) {
            roleCmb.getItems().add(role);
        }
        resetForm();

        saveBtn.setOnAction((event) -> {
           try{
               RadioButton selectedGenderRadio = (RadioButton) genderToggleGroup.getSelectedToggle();
               Person person =
                       Person
                               .builder()
                               .id(Integer.parseInt(idTxt.getText()))
                               .name(nameTxt.getText())
                               .family(familyTxt.getText())
                               .birthDate(birthDate.getValue())
                               .role(roleCmb.getSelectionModel().getSelectedItem())
                               .algorithmSkill(algoChk.isSelected())
                               .javaSkill(javaChk.isSelected())
                               .gender(Gender.valueOf(selectedGenderRadio.getText()))
                               .build();
               personDataAccess.savePerson(person);
               Alert alert = new Alert(Alert.AlertType.INFORMATION, "Person Created Successfully", ButtonType.OK);
               alert.show();
               resetForm();
               log.info("Person Created Successfully " + person);
           }catch(Exception e){
               e.printStackTrace();
               log.error(e.getMessage());
           }
        });

        clearBtn.setOnAction((event) -> {resetForm();});

        nameSearchTxt.setOnKeyReleased((event) -> {
           try{
               showPersonsOnTable(personDataAccess.getPersonsByNameAndFamily(nameSearchTxt.getText(), familySearchTxt.getText()));
           }catch(Exception e){
               log.error(e.getMessage());
           }
        });

        familySearchTxt.setOnKeyReleased((event) -> {
            try{
                showPersonsOnTable(personDataAccess.getPersonsByNameAndFamily(nameSearchTxt.getText(), familySearchTxt.getText()));
            }catch(Exception e){
                log.error(e.getMessage());
            }
        });

        EventHandler<Event> tableChangeEvent = (mouseEvent) -> {
            Person selectedPerson = personTbl.getSelectionModel().getSelectedItem();
            idTxt.setText(String.valueOf(selectedPerson.getId()));
            nameTxt.setText(selectedPerson.getName());
            familyTxt.setText(selectedPerson.getFamily());
            birthDate.setValue(selectedPerson.getBirthDate());
            roleCmb.getSelectionModel().select(selectedPerson.getRole());
            algoChk.setSelected(selectedPerson.isAlgorithmSkill());
            javaChk.setSelected(selectedPerson.isJavaSkill());
            maleRdo.setSelected(selectedPerson.getGender().equals(Gender.Male));
            femaleRdo.setSelected(selectedPerson.getGender().equals(Gender.Female));
        };

        personTbl.setOnMouseReleased(tableChangeEvent);
        personTbl.setOnKeyReleased(tableChangeEvent);
    }

    public void resetForm() {
        idTxt.setText(String.valueOf(personDataAccess.getNextId()));
        nameTxt.clear();
        familyTxt.clear();
        nameSearchTxt.clear();
        familySearchTxt.clear();

        roleCmb.getSelectionModel().select(Role.Employee);

        birthDate.setValue(LocalDate.now());

        algoChk.setSelected(true);
        javaChk.setSelected(false);

        maleRdo.setSelected(true);
        try {
            showPersonsOnTable(personDataAccess.getAllPersons());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void showPersonsOnTable(List<Person> personList) {
        ObservableList<Person> personObservableList = FXCollections.observableArrayList(personList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        personTbl.setItems(personObservableList);
    }
}
