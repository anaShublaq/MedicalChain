package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DoctorPageController implements Initializable {

    @FXML
    private Button buttonAddPatient;
    @FXML
    private Button buttonViewAllPatient;
    @FXML
    private Button buttonNewPatient;
    @FXML
    private Button buttonExit;
    @FXML
    private TextField textFieldDoctorId;
    @FXML
    private TextField textFieldDoctorName;
    @FXML
    private TextField textFieldDoctorSection;
    @FXML
    private TextField textFieldDoctorSpecialization;
    @FXML
    private TextField textFieldDoctorCity;
    @FXML
    private TextField textFieldDoctorAddress;
    @FXML
    private TextField textFieldDoctorEmail;
    @FXML
    private TextField textFieldDoctorNumber;
    @FXML
    private Label DrPageLabel;

    Statement statement;

    File myFile = new File("user.txt");
    File myFile3 = new File("switch.txt");

    String doctorName;
    int doctorId;
    int numOfOperation;

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

        try {
            ResultSet rs = this.statement.executeQuery("Select DoctorID From doctors WHERE DoctorFirstName='" + doctorName + "';");
            if (rs.next()) {
                doctorId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ResultSet rs = this.statement.executeQuery("Select * From doctors WHERE DoctorID='" + doctorId + "';");
            while (rs.next()) {
                textFieldDoctorId.setText(rs.getString("DoctorID"));
                textFieldDoctorName.setText(rs.getString("DoctorFirstName") + " " + rs.getString("DoctorLastName"));
                textFieldDoctorSpecialization.setText(rs.getString("DoctorSpecialization"));
                textFieldDoctorSection.setText(rs.getString("DoctorSection"));
                textFieldDoctorAddress.setText(rs.getString("DoctorAddress"));
                textFieldDoctorCity.setText(rs.getString("DoctorCity"));
                textFieldDoctorNumber.setText(rs.getString("DoctorNumber"));
                textFieldDoctorEmail.setText(rs.getString("DoctorEmail"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DrPageLabel.setText("Dr. " + doctorName + " control panel");
        DrPageLabel.setTranslateX(200);
        DrPageLabel.setStyle("-fx-font-size: 35px; -fx-text-fill: Blue;");
    }

    @FXML
    private void buttonAddHandle(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PatientPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Patient Page...");
        stage.show();
    }

    @FXML
    private void buttonViewHandle(ActionEvent event) throws IOException {
        numOfOperation = 1;
        try (FileWriter fWriter = new FileWriter("switch.txt")) {
            fWriter.write(String.valueOf(numOfOperation));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        Parent root = FXMLLoader.load(getClass().getResource("PatientVerificationGate.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Patient Verification Gate...");
        stage.show();
    }

    @FXML
    private void buttonNewtHandle(ActionEvent event) throws IOException {
        numOfOperation = 2;
        try (FileWriter fWriter = new FileWriter("switch.txt")) {
            fWriter.write(String.valueOf(numOfOperation));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        Parent root = FXMLLoader.load(getClass().getResource("PatientVerificationGate.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Patient Verification Gate...");
        stage.show();
    }

    @FXML
    private void buttonlogoutHandle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Dr." + doctorName + "\nDo you want Exit ??");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            myFile.delete();
            myFile3.delete();
            Stage stage = (Stage) buttonExit.getScene().getWindow();
            stage.close();
            System.exit(0);
        } else if (option.get() == ButtonType.CANCEL) {
        }
    }

}
