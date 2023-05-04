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
    private AnchorPane myAnchorPane;
    private String mode = "e";
    Record records = new Record();


    //Function to connect to database
    @FXML
    public void btnConnectClicked(ActionEvent event) {

        if (connectBtn.getText().equals("Connect")) {

            currentRecord = 0;
            boolean success = records.findConnection();
            if (success) {

                connectBtn.setText("Disconnect");
                enableControls();
            } else {
                questionLbl.setText("no");
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
            } else {
                questionLbl.setText("yesD");
            }
        }

    }

    //Method to display record in Gui
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

    @FXML
    public void btnInsertClicked(ActionEvent event) {
        if (insertBtn.getText().equals("Insert")) {

            disableControls();
            insertBtn.setDisable(false);
            cancelBtn.setVisible(true);

            // Clearing text fields
            idTxt.setText("");
            questionTxt.setText("");
            answer1Txt.setText("");
            answer2Txt.setText("");
            answer3Txt.setText("");
            categoryTxt.setText("");
            categoryTxt.setText("");
            difficultyTxt.setText("");


            insertBtn.setText("COMMIT");
        } else {

            cancelBtn.setVisible(false);
            insertBtn.setText("Insert");
            Table w = pullRecord();
            String sus = records.addRecord(w);
            System.out.println(sus);
            enableControls();
            currentRecord = records.getResults().size() - 1;
            displayRecord(currentRecord);

        }
    }

    @FXML
    public void btnCancelAddClicked(ActionEvent event) {

    }

    @FXML
    public void btnDeleteClicked(ActionEvent event) {
        Table w = pullRecord();
        updateMessage(records.deleteRecord(w));
        currentRecord = currentRecord - 1;
        displayRecord(currentRecord);

    }

    @FXML
    public void btnUpdateClicked(ActionEvent event) {
        updateMessage("Attempting Edit.");
        Table w = pullRecord();
        String sus = records.editRecord(w);
        System.out.println(sus);
        displayRecord(currentRecord);
    }

    private void updateMessage(String msg) {
        messageLbl.setText(msg);

    }

    //Setting record to contents of GUI
    private Table pullRecord() {

        Table w = new Table();

        w.setQuestion(questionTxt.getText());
        w.setAnswer1(answer1Txt.getText());
        w.setAnswer2(answer2Txt.getText());
        w.setAnswer3(answer3Txt.getText());
        w.setCategory(categoryTxt.getText());
        w.setDifficulty(Integer.parseInt(difficultyTxt.getText()));
        w.setPower(Integer.parseInt(powerTxt.getText()));

        return w;
    }

    private Table getRecord(int recordNum) {

        Table w = new Table();
        int qID = records.getResults().get(recordNum).getId();
        String gotRecord = records.selectRecord(w);
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
    }

    //Method to switch between edit mode and play mode
    @FXML
    private void btnModeClicked(ActionEvent event) {
        if (mode.equals("p")) {
            mode = "e";
            modeBtn.setText("Mode: Edit");
            connectBtn.setVisible(true);
            insertBtn.setVisible(true);
            deleteBtn.setVisible(true);
            updateBtn.setVisible(true);
            startBtn.setVisible(false);
        } else if (mode.equals("e")) {
            mode = "p";
            modeBtn.setText("Mode: Play");
            connectBtn.setVisible(false);
            insertBtn.setVisible(false);
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
            startBtn.setVisible(true);
        }
    }

    @FXML
    private void btnStartClicked(ActionEvent event) {

    }

}