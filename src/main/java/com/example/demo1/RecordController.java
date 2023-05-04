/*
Kevin Wang
3 May 2023
AP Computer Science A
2nd Period
Master Project
FXML Record Controller Class
 */

package com.example.demo1;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecordController {


    //GUI Section
    public Stage myStage;
    private int currentRecord;
    @FXML
    private Label questionLbl;
    @FXML
    private Label answer1Lbl;
    @FXML
    private Label answer2Lbl;
    @FXML
    private Label answer3Lbl;
    @FXML
    private TextField questionTxt;
    @FXML
    private TextField answer1Txt;
    @FXML
    private TextField answer2Txt;
    @FXML
    private TextField answer3Txt;
    @FXML
    private Button connectBtn;
    @FXML
    private Button firstBtn;
    @FXML
    private Button back3Btn;
    @FXML
    private Button backBtn;
    @FXML
    private Button forBtn;
    @FXML
    private Button for3Btn;
    @FXML
    private Button lastBtn;
    @FXML
    private Button insertBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField idTxt;
    @FXML
    private TextField difficultyTxt;
    @FXML
    private TextField powerTxt;
    @FXML
    private TextField categoryTxt;
    @FXML
    private Label difficultyLbl;
    @FXML
    private Label powerLbl;
    @FXML
    private Label categoryLbl;
    @FXML
    private Label messageLbl;
    @FXML
    private Button modeBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button buzzBtn;
    @FXML
    private Button skipBtn;
    @FXML
    private TextField userAnswerTxt;
    @FXML
    private TextField scoreTxt;
    @FXML
    private Label scoreLbl;
    @FXML
    private AnchorPane myAnchorPane;

    //Initializing variables
    private String mode = "e";
    private int useRecord = 0;
    private int points = 0;
    boolean continuePrint = false;
    Record records = new Record();


    //Function to connect to database
    @FXML
    public void btnConnectClicked(ActionEvent event) {

        //If not connected, then connect to database
        if (connectBtn.getText().equals("Connect")) {

            currentRecord = 0;
            boolean success = records.findConnection();
            if (success) {

                connectBtn.setText("Disconnect");
                enableControls();
                modeBtn.setDisable(false);

            //If unsuccessful, display failure message to user
            } else {
                messageLbl.setText("Could not connect.");
            }

            //Loading first record in the table
            String loaded = records.loadResults();
            if (records.getResults().size() != 0) {
                displayRecord(currentRecord);
            }

        } else {

            //Disconnecting from table
            boolean success = records.disconnect();
            if (success) {
                disableControls();
                connectBtn.setDisable(false);
                connectBtn.setText("Connect");

            //If unsuccessful, display failure message to user
            } else {
                messageLbl.setText("Could not disconnect.");
            }
        }

    }

    //Method to display record in GUI
    private void displayRecord(int recordNum) {

        //Setting text fields to data from table
        idTxt.setText(String.valueOf(records.getResults().get(recordNum).getId()));
        questionTxt.setText(records.getResults().get(recordNum).getQuestion());
        answer1Txt.setText(records.getResults().get(recordNum).getAnswer1());
        answer2Txt.setText(records.getResults().get(recordNum).getAnswer2());
        answer3Txt.setText(records.getResults().get(recordNum).getAnswer3());
        categoryTxt.setText(records.getResults().get(recordNum).getCategory());
        difficultyTxt.setText(String.valueOf(records.getResults().get(recordNum).getDifficulty()));
        powerTxt.setText(String.valueOf(records.getResults().get(recordNum).getPower()));
    }

    //Method to insert a new record
    @FXML
    public void btnInsertClicked(ActionEvent event) {

        if (insertBtn.getText().equals("Insert")) {

            //Updating GUI
            disableControls();
            insertBtn.setDisable(false);
            cancelBtn.setVisible(true);

            //Clearing text fields
            idTxt.setText("");
            questionTxt.setText("");
            answer1Txt.setText("");
            answer2Txt.setText("");
            answer3Txt.setText("");
            categoryTxt.setText("");
            categoryTxt.setText("");
            difficultyTxt.setText("");

            //Waiting for user to commit changes
            insertBtn.setText("COMMIT");

        //Commit mode
        } else {

            //Updating GUI
            cancelBtn.setVisible(false);
            insertBtn.setText("Insert");

            //Pulling user input from GUI
            Table w = pullRecord();
            updateMessage(records.addRecord(w));

            //Updating GUI and displaying new record
            enableControls();
            currentRecord = records.getResults().size() - 1;
            displayRecord(currentRecord);

        }
    }

    //Function to cancel inserting a new record
    @FXML
    public void btnCancelAddClicked(ActionEvent event) {

        //Updating GUI
        insertBtn.setText("Insert");
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        updateMessage("Insert cancelled.");
        displayRecord(currentRecord);
        cancelBtn.setVisible(false);
        enableControls();
    }

    //Method to delete a function
    @FXML
    public void btnDeleteClicked(ActionEvent event) {

        //Pulling current record from the GUI
        Table w = pullRecord();

        //Deleting record from the table
        updateMessage(records.deleteRecord(w));

        //Displaying adjacent record
        if (currentRecord != 0) {
            currentRecord = currentRecord - 1;
        } else {
            currentRecord = currentRecord + 1;
        }
        displayRecord(currentRecord);

    }


    //Method to update a record
    @FXML
    public void btnUpdateClicked(ActionEvent event) {

        updateMessage("Attempting Edit.");

        //Pulling current record from the GUI
        Table w = pullRecord();
        updateMessage(records.editRecord(w));

        //Displaying changes
        displayRecord(currentRecord);
    }

    //Utility function to update user on events in GUI
    private void updateMessage(String msg) {
        messageLbl.setText(msg);

    }

    //Setting record to contents of GUI
    private Table pullRecord() {

        Table w = new Table();

        //Getting text from GUI text boxes
        w.setQuestion(questionTxt.getText());
        w.setAnswer1(answer1Txt.getText());
        w.setAnswer2(answer2Txt.getText());
        w.setAnswer3(answer3Txt.getText());
        w.setCategory(categoryTxt.getText());
        w.setDifficulty(Integer.parseInt(difficultyTxt.getText()));
        w.setPower(Integer.parseInt(powerTxt.getText()));

        return w;
    }


    //Method to go to first record in the table
    @FXML
    public void btnFirstClicked(ActionEvent event) {
        currentRecord = 0;
        checkIndex(currentRecord);
        displayRecord(currentRecord);
    }

    //Method to go backwards three records in the table
    @FXML
    public void btnBack3Clicked(ActionEvent event) {
        currentRecord = currentRecord - 3;
        checkIndex(currentRecord);
        displayRecord(currentRecord);
    }

    //Method to go backwards one record in the table
    @FXML
    public void btnBackClicked(ActionEvent event) {
        currentRecord = currentRecord - 1;
        checkIndex(currentRecord);
        displayRecord(currentRecord);
    }

    //Method to go forwards one record in the table
    @FXML
    public void btnForClicked(ActionEvent event) {
        currentRecord = currentRecord + 1;
        checkIndex(currentRecord);
        displayRecord(currentRecord);
    }

    //Method to go forwards three records in the table
    @FXML
    public void btnFor3Clicked(ActionEvent event) {
        currentRecord = currentRecord + 3;
        checkIndex(currentRecord);
        displayRecord(currentRecord);
    }

    //Method to go to the last record in the table
    @FXML
    public void btnLastClicked(ActionEvent event) {
        currentRecord = records.getResults().size() - 1;
        checkIndex(currentRecord);
        displayRecord(currentRecord);
    }

    //Method to disable all buttons on the GUI
    private void disableControls() {
        connectBtn.setDisable(true);
        insertBtn.setDisable(true);
        deleteBtn.setDisable(true);
        updateBtn.setDisable(true);
        firstBtn.setDisable(true);
        forBtn.setDisable(true);
        for3Btn.setDisable(true);
        backBtn.setDisable(true);
        back3Btn.setDisable(true);
        lastBtn.setDisable(true);
        modeBtn.setDisable(true);
    }

    //Method to enable all buttons on the GUI
    private void enableControls() {
        connectBtn.setDisable(false);
        insertBtn.setDisable(false);
        deleteBtn.setDisable(false);
        updateBtn.setDisable(false);
        firstBtn.setDisable(false);
        forBtn.setDisable(false);
        for3Btn.setDisable(false);
        backBtn.setDisable(false);
        back3Btn.setDisable(false);
        lastBtn.setDisable(false);
    }


    //Method to prevent user from going out-of-bounds when scrolling records
    private void checkIndex(int indexNow) {

        //Preventing user from going past first record
        if (indexNow < 0) {
            currentRecord = 0;
        }

        //Preventing user from going past last record
        int maxRec = records.getResults().size() - 1;
        if (indexNow > maxRec) {
            currentRecord = maxRec;
        }

    }

    //Method ran on startup to initialize GUI
    public void initialize() {
        cancelBtn.setVisible(false);
        disableControls();
        connectBtn.setDisable(false);
        startBtn.setVisible(false);
        buzzBtn.setVisible(false);
        userAnswerTxt.setVisible(false);
        skipBtn.setVisible(false);
        buzzBtn.setDisable(true);
        userAnswerTxt.setDisable(true);
        skipBtn.setDisable(true);
        modeBtn.setDisable(true);
        updateMessage("Welcome to QB Tracker!");
        mode = "e";
    }

    //Method to switch between edit mode and play mode
    @FXML
    private void btnModeClicked(ActionEvent event) {

        //Switching to edit mode
        if (mode.equals("p")) {

            //Updating GUI
            mode = "e";
            modeBtn.setText("Mode: Edit");
            connectBtn.setVisible(true);
            insertBtn.setVisible(true);
            deleteBtn.setVisible(true);
            updateBtn.setVisible(true);
            firstBtn.setVisible(true);
            back3Btn.setVisible(true);
            backBtn.setVisible(true);
            forBtn.setVisible(true);
            for3Btn.setVisible(true);
            lastBtn.setVisible(true);
            startBtn.setVisible(false);
            buzzBtn.setVisible(false);
            userAnswerTxt.setVisible(false);
            skipBtn.setVisible(false);

        //Switching to play mode
        } else if (mode.equals("e")) {

            //Updating GUI
            mode = "p";
            modeBtn.setText("Mode: Play");
            connectBtn.setVisible(false);
            insertBtn.setVisible(false);
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
            firstBtn.setVisible(false);
            back3Btn.setVisible(false);
            backBtn.setVisible(false);
            forBtn.setVisible(false);
            for3Btn.setVisible(false);
            lastBtn.setVisible(false);
            startBtn.setVisible(true);
            skipBtn.setVisible(true);
            buzzBtn.setVisible(true);
            userAnswerTxt.setVisible(true);
        }
    }

    //Method to start a question
    @FXML
    private void btnStartClicked(ActionEvent event) throws InterruptedException {

        //Setting record to get to a random number
        useRecord = (int) Math.random() * records.getResults().size()-1;

        //Setting text fields to data from table
        idTxt.setText(String.valueOf(records.getResults().get(useRecord).getId()));
        String question = records.getResults().get(useRecord).getQuestion();
        String answer1 = records.getResults().get(useRecord).getAnswer1();
        String answer2 = records.getResults().get(useRecord).getAnswer2();
        String answer3 = records.getResults().get(useRecord).getAnswer3();
        categoryTxt.setText(records.getResults().get(useRecord).getCategory());
        difficultyTxt.setText(String.valueOf(records.getResults().get(useRecord).getDifficulty()));
        powerTxt.setText(String.valueOf(records.getResults().get(useRecord).getPower()));

        //Updating GUI
        buzzBtn.setDisable(false);
        skipBtn.setDisable(false);
        userAnswerTxt.setDisable(false);
        startBtn.setDisable(true);

        int i = 0;

        //Splitting question into array split by spaces
        String[] splitQuestion = question.split(" ");

        //Getting length of array to use as a counter
        int counter = splitQuestion.length;
        continuePrint = true;

        //Keep printing before question ends
        while(i < counter && continuePrint == true) {

            //Waiting 0.15 seconds between printing each word
            Thread.sleep(150);
            questionTxt.setText(questionTxt.getText() + " " +  splitQuestion[i]);
            counter--;

            //If at the end of the question
            if (i == splitQuestion.length - 1) {

                //Update GUI
                startBtn.setDisable(false);
                skipBtn.setDisable(true);

                //Wait 3 seconds before disallowing the user from buzzing in
                Thread.sleep(300);
                questionTxt.setText(question);
                answer1Txt.setText(answer1);
                answer2Txt.setText(answer2);
                answer3Txt.setText(answer3);

                buzzBtn.setDisable(true);
                userAnswerTxt.setDisable(true);
            }

            //Iterating through the question
            i++;
        }
    }

    //Function for when user buzzes in with an answer
    @FXML
    private void btnBuzzClicked(ActionEvent event) throws InterruptedException {

        //Getting user-inputted answer and answers from database
        String userAnswer = userAnswerTxt.getText();
        userAnswer.toLowerCase();
        String answer1 = records.getResults().get(useRecord).getAnswer1();
        String answer2 = records.getResults().get(useRecord).getAnswer2();
        String answer3 = records.getResults().get(useRecord).getAnswer3();

        //Using external distance to find how similar the user answer is to given answers
        //If distance is small enough, the user has entered a correct answer
        double distance1 = Levenshtein.findSimilarity(userAnswer, answer1);
        double distance2 = Levenshtein.findSimilarity(userAnswer, answer2);
        double distance3 = Levenshtein.findSimilarity(userAnswer, answer3);
        userAnswerTxt.setText("");

        //If distance is small enough, user entered a correct answer
        if (distance1 < answer1.length() / 4 + 1 || distance2 < answer1.length() / 4 + 1 || distance3 < answer1.length() / 4 + 1) {
            messageLbl.setText("Correct!");
            Thread.sleep(50);
            end();
            points = points + 10;

        //If distance is too large, then answer was incorrect
        } else {
            continuePrint = false;
            messageLbl.setText("Incorrect.");
            Thread.sleep(50);
            continuePrint = true;
            points = points - 5;

        }

        //Updating point value to the user
        scoreTxt.setText(String.valueOf(points));

    }

    //Method to end a question early
    private void end() {

        //Getting question and answer from table
        String question = records.getResults().get(useRecord).getQuestion();
        String answer1 = records.getResults().get(useRecord).getAnswer1();
        String answer2 = records.getResults().get(useRecord).getAnswer2();
        String answer3 = records.getResults().get(useRecord).getAnswer3();

        //Stopping the print
        continuePrint = false;

        //Displaying answers
        questionTxt.setText(question);
        answer1Txt.setText(answer1);
        answer2Txt.setText(answer2);
        answer3Txt.setText(answer3);

        //Updating GUI
        buzzBtn.setDisable(true);
        startBtn.setDisable(false);
        skipBtn.setDisable(true);

    }

    //Function to skip a question
    @FXML
    private void btnSkipClicked(ActionEvent event) {
        end();
    }

    //Function to connect to database from menu
    @FXML
    public void mConnectClicked(ActionEvent event) {
        btnConnectClicked(event);
    }

    //Function to disconnect from database from menu
    @FXML
    public void mDisconnectClicked(ActionEvent event) {
        btnConnectClicked(event);
    }

    //Function to insert new record from menu
    @FXML
    public void mInsertClicked(ActionEvent event) {
        btnInsertClicked(event);
    }

    //Function to update record from menu
    @FXML
    public void mUpdateClicked(ActionEvent event) {
        btnUpdateClicked(event);
    }

    //Function to delete record from menu
    @FXML
    public void mDeleteClicked(ActionEvent event) {
        btnDeleteClicked(event);
    }

    //Function to show about panel from menu
    @FXML
    public void mAboutClicked(ActionEvent event) {

        //Creating new alert and displaying it
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("Quiz-Bowl DB About");
        aboutAlert.setHeaderText("Quiz-Bowl Program Information");
        aboutAlert.setContentText("This program can be used to help you practice for academic/quiz bowl tournaments.");
        aboutAlert.showAndWait();
    }

}