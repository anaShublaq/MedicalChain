package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientPageReviewController implements Initializable {

    @FXML
    private TextField textFieldPatientIdNumber;
    @FXML
    private TextField textFieldPatientFirstName;
    @FXML
    private TextField textFieldPatientLastName;
    @FXML
    private TextField textFieldPatientCity;
    @FXML
    private TextField textFieldPatientAddress;
    @FXML
    private TextField textFieldPatientNumber;
    @FXML
    private Button backButton;
    @FXML
    private Button continueButton;

    Statement statement;

    File myFile2 = new File("patientId.txt");

    int patientId;

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
            try (Scanner myReader = new Scanner(myFile2)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    patientId = Integer.parseInt(data.trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        try {
            ResultSet rs = this.statement.executeQuery("Select * From patient WHERE PatientID='" + patientId + "';");
            while (rs.next()) {
                textFieldPatientIdNumber.setText(rs.getString("PatientID"));
                textFieldPatientFirstName.setText(rs.getString("PatientFirstName"));
                textFieldPatientLastName.setText(rs.getString("PatientLastName"));
                textFieldPatientAddress.setText(rs.getString("PatientAddress"));
                textFieldPatientCity.setText(rs.getString("PatientCity"));
                textFieldPatientNumber.setText(rs.getString("PatientNumber"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void backButtonHandle(ActionEvent event) {
        myFile2.delete();
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void continueButtonHandle(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("newMedicalRecord.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("New Medical Record Page...");
        stage.show();
    }

}
