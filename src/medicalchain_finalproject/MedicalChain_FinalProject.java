package medicalchain_finalproject;

import blockchain.BlockUtils;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MedicalChain_FinalProject extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        blockchain.BlockUtils firstBlock = new BlockUtils();
        firstBlock.createGenesisBlock();
        launch(args);
    }

}
