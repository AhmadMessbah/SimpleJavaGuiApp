package mft.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import mft.controller.exception.UserNotFoundException;
import mft.model.entity.Person;
import mft.model.repository.PersonDA;

import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class LoginController implements Initializable {
    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button loginBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginBtn.setOnAction(event -> {
            try (PersonDA personDA = new PersonDA()) {
                Person person = personDA.login(usernameTxt.getText(), passwordTxt.getText());

                loginBtn.getScene().getWindow().hide();

                Stage stage = new Stage();
                stage.setTitle("Person Information");

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/PersonView.fxml")));
                stage.setScene(scene);
                stage.show();

                log.info("Person Logged In Successfully " + usernameTxt.getText() + ":" + passwordTxt.getText());
            } catch (UserNotFoundException e) {
                log.error("Person Login Error : " + usernameTxt.getText() + ":" + passwordTxt.getText() + " " + e.getMessage());
            }catch (Exception e ){
                log.error("Person Login Error : " + e.getMessage());
            }
        });
    }


}
