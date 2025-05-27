package mft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import mft.model.entity.Person;
import mft.model.entity.enums.Gender;
import mft.model.entity.enums.Role;
import mft.model.repository.PersonDataAccess;

import java.io.IOException;

@Log4j2
public class App extends Application {
    public void saveAdmin() throws IOException {
        Person person =
                Person
                        .builder()
                        .name("Admin")
                        .family("Admin")
                        .username("Admin")
                        .password("Admin")
                        .role(Role.Admin)
                        .gender(Gender.Male)
                        .build();
         PersonDataAccess personDataAccess = new PersonDataAccess();
         personDataAccess.savePerson(person);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        saveAdmin();
       try{
           Scene scene = new Scene(
                   FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"))
           );
           primaryStage.setScene(scene);
           primaryStage.setTitle("Login");
           log.info("App Started");
           primaryStage.show();
       }catch (Exception e){
           log.error("Start App" + e.getMessage());
       }
    }
}
