package controllers;

import classes.MedicalRecord;
import blockchain.BlockUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NewMedicalRecordController implements Initializable {

    @FXML
    private RadioButton medicalnoteRadio;
    @FXML
    private RadioButton prescriptionRadio;
    @FXML
    private RadioButton labresultRadio;
    @FXML
    private TextArea textArea;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button exit;

    Statement statement;

    File myFile = new File("user.txt");
    File myFile2 = new File("patientId.txt");

    String doctorName;
    int patientId;
    int doctorId;

    ArrayList<RadioButton> buttons;

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
            ResultSet rs = this.statement.executeQuery("Select DoctorID From doctors WHERE DoctorFirstName='" + doctorName + "';");
            if (rs.next()) {
                doctorId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void RadiobuttonNoteHandle(ActionEvent event) {
        labresultRadio.setSelected(false);
        prescriptionRadio.setSelected(false);
        medicalnoteRadio.setSelected(true);
    }

    @FXML
    private void RadiobuttonprescriptionHandle(ActionEvent event) {
        medicalnoteRadio.setSelected(false);
        labresultRadio.setSelected(false);
        prescriptionRadio.setSelected(true);
    }

    @FXML
    private void RadiobuttonlabresultHandle(ActionEvent event) {
        medicalnoteRadio.setSelected(false);
        prescriptionRadio.setSelected(false);
        labresultRadio.setSelected(true);
    }

    @FXML
    private void buttonAddHandle(ActionEvent event) throws SQLException, IOException {
        if ((!medicalnoteRadio.isSelected() && !prescriptionRadio.isSelected() && !labresultRadio.isSelected())
                || textArea.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING !!");
            alert.setHeaderText(null);
            alert.setContentText("You must fill in all patient information !!!");
            alert.showAndWait();
        } else {
            MedicalRecord newMedicalRecord = new MedicalRecord();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            newMedicalRecord.setMedicalRecordId(formatter.format(date));
            newMedicalRecord.setDoctorId(doctorId);
            newMedicalRecord.setPatientId(patientId);
            newMedicalRecord.setRecordContent(textArea.getText());
            buttons = new ArrayList();
            buttons.add(medicalnoteRadio);
            buttons.add(prescriptionRadio);
            buttons.add(labresultRadio);
            for (RadioButton button : buttons) {
                if (button.isSelected()) {
                    newMedicalRecord.setRecordType(button.getText());
                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add New Medical Record...");
            alert.setHeaderText(null);
            alert.setContentText("Dr." + doctorName + "\nDo you want Add This Medical Record ??");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {

                blockchain.BlockUtils addBlock = new BlockUtils();
                addBlock.mineBlock(2, newMedicalRecord.toString());
//                addBlock.blocksExplorer();

                String insertPatientsql = "Insert into medical_record values('" + newMedicalRecord.getMedicalRecordId() + "','" + newMedicalRecord.getRecordType()
                        + "','" + newMedicalRecord.getRecordContent() + "'," + newMedicalRecord.getDoctorId()
                        + "," + newMedicalRecord.getPatientId() + ")";
                this.statement.executeUpdate(insertPatientsql);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Add Patient...");
                alert2.setHeaderText(null);
                alert2.setContentText("Add Medical Record for this Patient ID (" + patientId + ") is success ^_^");
                alert2.showAndWait();
            } else if (option.get() == ButtonType.CANCEL) {
            }
        }
    }

    @FXML
    private void buttonClearHandle(ActionEvent event) {
        textArea.setText("");
        medicalnoteRadio.setSelected(false);
        labresultRadio.setSelected(false);
        prescriptionRadio.setSelected(false);
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
            myFile.delete();
            myFile2.delete();
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        } else if (option.get() == ButtonType.CANCEL) {

        }
    }

}
