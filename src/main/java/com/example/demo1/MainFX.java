/*
Kevin Wang
3 May 2023
AP Computer Science A
2nd Period
Master Project
Main JavaFx Class
 */

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

    //Creating the stage
    @Override
    public void start(Stage arg0) throws Exception {

        try {

            //Needed to prevent null pointer error on launch
            primaryStage = new Stage();

            //Setting the primary stage element
            setPrimaryStage(primaryStage);

            //Loading FXML file
            Parent root = FXMLLoader.load(getClass().getResource("quiz.fxml"));

            //Create the Scene in memory and assign our UI to that scene object
            Scene scene = new Scene(root);

            //Getting stylesheet for FXML file
            scene.getStylesheets().add(String.valueOf(this.getClass().getResource("style.css")));

            //Setting title
            primaryStage.setTitle("Quiz-bowl Practice");

            //Putting scene on primary stage
            primaryStage.setScene(scene);

            //Showing stage
            primaryStage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}