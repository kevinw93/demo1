package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    //Fields of the class
    private static Stage primaryStage;

    //Getters and Setters
    static public Stage getPrimaryStage() {
        return MainFX.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        MainFX.primaryStage = stage;
    }

    @Override
    public void start(Stage arg0) throws Exception {

        try {

            //Required to avoid null pointer error on launch
            primaryStage = new Stage();

            //Setting the primary stage element
            setPrimaryStage(primaryStage);

            //Loading the FXML
            Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));

            //Create the Scene in memory and assign our UI to that scene object
            Scene scene = new Scene(root);


            scene.getStylesheets().add(String.valueOf(this.getClass().getResource("style.css")));
            //Setting the title, and putting the scene on the primary stage - then showing the stage
            primaryStage.setTitle("Quiz-bowl Practice");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}