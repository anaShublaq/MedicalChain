package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Label labelUserName;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField textFieldUserName;
    @FXML
    private Button buttonLogin;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private Button buttonCancle;
    @FXML
    private Label labelWelcome;

    Statement statement;

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
    }

    @FXML
    private void buttonLoginHandle(ActionEvent event) throws SQLException, IOException {
        String userNameEntered = textFieldUserName.getText();
        String passwordEntered = textFieldPassword.getText();
        boolean isDoctor = false;

        ArrayList<String> usersName = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        if (userNameEntered.isEmpty() || passwordEntered.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all required fields !!!");
            alert.showAndWait();
        } else {

            ResultSet rsUserName = this.statement.executeQuery("Select DoctorFirstName From doctors");
            while (rsUserName.next()) {
                String getUserName = rsUserName.getString("DoctorFirstName");
                usersName.add(getUserName);
            }

            ResultSet rsPassword = this.statement.executeQuery("Select DoctorID From doctors");
            while (rsPassword.next()) {
                String getPassword = rsPassword.getString("DoctorID");
                passwords.add(getPassword);
            }

            for (int i = 0; i < usersName.size(); i++) {
                for (int j = i; j < passwords.size(); j++) {
                    if (usersName.get(i).equals(userNameEntered) && passwords.get(j).equals(passwordEntered)) {
                        isDoctor = true;
                    }
                }
            }

            if (isDoctor) {
                File myFile = new File("user.txt");
                try (FileWriter fWriter = new FileWriter("user.txt")) {
                    fWriter.write(userNameEntered);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Wellcome (" + userNameEntered + ")  ^_^");
                alert.showAndWait();

                Stage stage = (Stage) buttonCancle.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("DoctorPage.fxml"));
                Scene scene = new Scene(root);
                stage = new Stage();
                stage.setMinHeight(650);
                stage.setMinWidth(850);
                stage.setMaxHeight(780);
                stage.setMaxWidth(850);
                stage.setScene(scene);
                stage.setTitle("Doctor Page...");
                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The Username or Password is incorrect !!!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void buttonCancelHandle(ActionEvent event) throws Exception {
        if (textFieldPassword.getText().equals("") && textFieldUserName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText(null);
            alert.setContentText("Do you want Exit ??");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) buttonCancle.getScene().getWindow();
                stage.close();
                File myFile = new File("user.txt");
                myFile.delete();
                File myFile2 = new File("patientId.txt");
                myFile2.delete();
                File myFile3 = new File("switch.txt");
                myFile3.delete();
            } else if (option.get() == ButtonType.CANCEL) {
            }
        } else {
            textFieldUserName.setText("");
            textFieldPassword.setText("");
        }
    }

}
