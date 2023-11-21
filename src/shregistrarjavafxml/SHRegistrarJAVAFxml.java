/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shregistrarjavafxml;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 *
 * @author ACER
 */
public class SHRegistrarJAVAFxml extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        
        Scene scene = new Scene(root);
//        stage.setFullScreen(true);
//        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        String sysver = "SHSRegistrarJAVAFxml  v2.18  10-27-2022";
        
        //stage.getIcons().add(new Image("/adzsealhallow.png"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
        stage.setTitle(sysver);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
//        if (HibernateUtil.setSessionFactory()) {
//            launch(args);
//            HibernateUtil.getSessionFactory().close();
//        } else {
//            Platform.runLater(() -> {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("An error has occured!");
//                alert.setHeaderText("Database Connection Error!");
//                alert.setContentText("Please contact the developer");
//                alert.showAndWait();
//                Platform.exit();
//            });
//        }
    }
    
}
