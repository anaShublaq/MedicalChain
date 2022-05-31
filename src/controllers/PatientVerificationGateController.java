package controllers;

import classes.Patients;
import classes.MedicalRecord;
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
import static javafx.application.Application.STYLESHEET_CASPIAN;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PatientVerificationGateController implements Initializable {

    public GridPane getGridPane(String medicalRecordId) {

        MedicalRecord medicalRecord = null;

        try {
            ResultSet rs = this.statement.executeQuery("SELECT * FROM medical_record WHERE MedicalRecordId='" + medicalRecordId + "';");
            while (rs.next()) {
                medicalRecord = new MedicalRecord();
                medicalRecord.setMedicalRecordId(rs.getString("MedicalRecordId"));
                medicalRecord.setRecordType(rs.getString("RecordType"));
                medicalRecord.setRecordContent(rs.getString("RecordContent"));
                medicalRecord.setDoctorId(Integer.parseInt(rs.getString("DoctorID")));
                medicalRecord.setPatientId(Integer.parseInt(rs.getString("PatientID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Label label = new Label("Medical Record ID");
        Label labe2 = new Label("Record Type");
        Label labe3 = new Label("Record Content");
        Label labe4 = new Label("Doctor ID");
        Label labe5 = new Label("Patient ID");

        label.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 15));
        labe2.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 14));
        labe3.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 15));
        labe4.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 14));
        labe5.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 14));

        TextField textField1 = new TextField(medicalRecord.getMedicalRecordId());
        TextField textField2 = new TextField(medicalRecord.getRecordType());
        TextField textField3 = new TextField(medicalRecord.getDoctorId() + "");
        TextField textField4 = new TextField(medicalRecord.getPatientId() + "");
        TextArea textArea = new TextArea(medicalRecord.getRecordContent());
        textArea.setPrefSize(300, 200);
        textArea.setDisable(true);
        textArea.setStyle("-fx-font: 15pt Arial;"
                + "-fx-text-fill: Black;"
                + "-fx-opacity: 1;"
                + "-fx-background-color : White;"
                + "-fx-font-weight: bold");
        textArea.setWrapText(true);
        textField1.setDisable(true);
        textField2.setDisable(true);
        textField3.setDisable(true);
        textField4.setDisable(true);

        textField1.setOpacity(1);
        textField1.setStyle("-fx-font-weight: bold");
        textField2.setOpacity(1);
        textField3.setOpacity(1);
        textField4.setOpacity(1);

        GridPane gridPane = new GridPane();

        gridPane.add(label, 0, 0);
        gridPane.add(labe2, 0, 1);
        gridPane.add(labe3, 0, 2);
        gridPane.add(labe4, 0, 3);
        gridPane.add(labe5, 0, 4);

        gridPane.add(textField1, 1, 0);
        gridPane.add(textField2, 1, 1);
        gridPane.add(textArea, 1, 2);
        gridPane.add(textField3, 1, 3);
        gridPane.add(textField4, 1, 4);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 0, 20));
        return gridPane;
    }

    @FXML
    private Button cancelButton;
    @FXML
    private Button verificationButton;
    @FXML
    private TextField textFieldPatientId;

    Statement statement;

    File myFile = new File("user.txt");
    File myFile2 = new File("patientId.txt");
    File myFile3 = new File("switch.txt");

    String doctorName;
    int patientId;
    int numOfOperation;

    Patients p;

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
            try (Scanner myReader = new Scanner(myFile3)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    numOfOperation = Integer.parseInt(data);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    @FXML
    private void cancelButtonHandle(ActionEvent event) {
        myFile2.delete();
        myFile3.delete();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void verificationButtonHandle(ActionEvent event) throws SQLException, IOException {
        int isFound = 0;
        int patientIdNum = Integer.parseInt(textFieldPatientId.getText());
        ResultSet rs = this.statement.executeQuery("Select * From patient");
        while (rs.next()) {
            if (patientIdNum == rs.getInt("PatientID")) {
                isFound++;
            }
        }
        if (isFound == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search for Patient...");
            alert.setHeaderText(null);
            alert.setContentText("Dr." + doctorName + "\npatient with this ID number (" + patientIdNum + ") does not found !!");
            Optional<ButtonType> option = alert.showAndWait();
        } else {
            Stage stage = new Stage();
            patientId = patientIdNum;
            try (FileWriter fWriter = new FileWriter("patientId.txt")) {
                fWriter.write(String.valueOf(patientId));
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }

            try {
                try (Scanner myReader = new Scanner(myFile3)) {
                    int i = 0;
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        numOfOperation = Integer.parseInt(data.trim());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }

            try {
                try (Scanner myReader = new Scanner(myFile2)) {
                    int i = 0;
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        patientId = Integer.parseInt(data.trim());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }

            if (numOfOperation == 1) {
                int numOfPatientRecords = 0;
                try {
                    ResultSet rs2 = this.statement.executeQuery("SELECT COUNT(PatientID) FROM medical_record WHERE PatientID=" + patientId + ";");
                    if (rs2.next()) {
                        numOfPatientRecords = rs2.getInt(1);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DoctorPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String[] medicalRecordId = new String[numOfPatientRecords];
                try {
                    ResultSet rs3 = this.statement.executeQuery("SELECT MedicalRecordId FROM medical_record WHERE PatientID=" + patientId + " ORDER BY MedicalRecordId ASC;");
                    int i = 0;
                    while (rs3.next()) {
                        medicalRecordId[i] = rs3.getString("MedicalRecordId");
                        i++;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DoctorPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (numOfPatientRecords == 0) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Medical Patient Records...");
                    alert2.setHeaderText(null);
                    alert2.setContentText("The patient with the ID number (" + patientId + ") has no records -_-");
                    alert2.showAndWait();
                } else {
                    Stage primaryStage = new Stage();
                    TabPane tabpane = new TabPane();

                    for (int i = 0; i < numOfPatientRecords; i++) {
                        Tab t = new Tab("" + (i + 1));
                        t.setClosable(false);
                        tabpane.getTabs().add(t);
                        t.setContent(getGridPane(medicalRecordId[i]));
                    }

                    tabpane.getTabs().addAll();
                    tabpane.setTabMinWidth(70);
                    tabpane.setTabMinHeight(33);
                    tabpane.setTabMaxWidth(69);
                    tabpane.setTabMaxHeight(69);

                    VBox vbox = new VBox(tabpane);
                    vbox.setStyle("-fx-background-color : lightblue;");
                    primaryStage.setTitle("Medical Records...");
                    Scene scene = new Scene(vbox, 500, 420);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    stage = (Stage) verificationButton.getScene().getWindow();
                    stage.close();
                    myFile3.delete();
                }
            } else if (numOfOperation == 2) {
                Parent root = FXMLLoader.load(getClass().getResource("PatientPageReview.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Patient Review Page ...");
                stage.show();
                stage = (Stage) verificationButton.getScene().getWindow();
                stage.close();
                myFile3.delete();
            }

        }
    }

}
