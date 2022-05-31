package controllers;

import classes.Patients;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientPageController implements Initializable {

    @FXML
    private TextField textFieldPatientCity;
    @FXML
    private TextField textFieldPatientAddress;
    @FXML
    private TextField textFieldPatientNumber;
    @FXML
    private Button buttonExit;
    @FXML
    private TextField textFieldPatientFirstName;
    @FXML
    private TextField textFieldPatientLastName;
    @FXML
    private TextField textFieldPatientIdNumber;

    Statement statement;

    File myFile = new File("user.txt");
    String doctorName;
    @FXML
    private Button buttonAddPatient;
    @FXML
    private Button buttonClear;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection
                    = DriverManager.
                            getConnection("jdbc:mysql://localhost/dbName?serverTimezone=UTC",
                                    "userName", "password");
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
        }

        try {
            try (Scanner myReader = new Scanner(myFile)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    doctorName = data;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    @FXML
    private void buttonAddPatientHandle(ActionEvent event) throws SQLException {
        if (textFieldPatientIdNumber.getText().equals("") || textFieldPatientFirstName.getText().equals("")
                || textFieldPatientLastName.getText().equals("") || textFieldPatientAddress.getText().equals("")
                || textFieldPatientCity.getText().equals("") || textFieldPatientNumber.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING !!");
            alert.setHeaderText(null);
            alert.setContentText("You must fill in all patient information !!!");
            alert.showAndWait();
        } else {
            Patients newPatient = new Patients();
            newPatient.setPatientId(Integer.parseInt(textFieldPatientIdNumber.getText()));
            newPatient.setPatientFirstName(textFieldPatientFirstName.getText());
            newPatient.setPatientLastName(textFieldPatientLastName.getText());
            newPatient.setPatientAddress(textFieldPatientAddress.getText());
            newPatient.setAtientCity(textFieldPatientCity.getText());
            newPatient.setPatientNumber(Integer.parseInt(textFieldPatientNumber.getText()));

            int patientId = Integer.parseInt(textFieldPatientIdNumber.getText());
            int isFound = 0;
            ResultSet rs = this.statement.executeQuery("Select * From patient");
            while (rs.next()) {
                if (patientId == rs.getInt("PatientID")) {
                    isFound++;
                }
            }
            if (isFound == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add Patient...");
                alert.setHeaderText(null);
                alert.setContentText("Dr." + doctorName + "\nDo you want Add (" + textFieldPatientFirstName.getText() + " " + textFieldPatientLastName.getText() + ") Patient ??");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == null) {
                } else if (option.get() == ButtonType.OK) {
                    String insertPatientsql = "Insert into patient values(" + newPatient.getPatientId() + ",'" + newPatient.getPatientFirstName()
                            + "','" + newPatient.getPatientLastName() + "','" + newPatient.getPatientAddress()
                            + "','" + newPatient.getAtientCity() + "'," + newPatient.getPatientNumber() + ")";
                    this.statement.executeUpdate(insertPatientsql);
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Add Patient...");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Add (" + textFieldPatientFirstName.getText() + " " + textFieldPatientLastName.getText() + ") Patient is success ^_^");
                    alert2.showAndWait();
                } else if (option.get() == ButtonType.CANCEL) {
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING !!");
                alert.setHeaderText(null);
                alert.setContentText("The patient with ID number (" + patientId + ") already exists");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void buttonClearHandle(ActionEvent event) {
        textFieldPatientAddress.setText("");
        textFieldPatientCity.setText("");
        textFieldPatientFirstName.setText("");
        textFieldPatientLastName.setText("");
        textFieldPatientNumber.setText("");
        textFieldPatientIdNumber.setText("");
    }

    @FXML
    private void buttonExitHandle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Dr." + doctorName + "\nDo you want Exit ??");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            Stage stage = (Stage) buttonExit.getScene().getWindow();
            stage.close();
        } else if (option.get() == ButtonType.CANCEL) {

        }
    }

}
