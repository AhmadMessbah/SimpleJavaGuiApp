package mft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
       try{
           Scene scene = new Scene(
                   FXMLLoader.load(getClass().getResource("/view/PersonView.fxml"))
           );
           primaryStage.setScene(scene);
           primaryStage.setTitle("Person Information");
           log.info("App Started");
           primaryStage.show();
       }catch (Exception e){
           log.error("Start App" + e.getMessage());
       }
    }
}
