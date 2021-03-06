package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.Stale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController extends MainContoller implements Initializable {
    public Label gracz1;
    public Label gracz2;
    public Label gracz3;
    public Label gracz4;
    public Label gracz5;
    public int labels;
    public Label poziom;
    public Button rozpocznijButton;
    public ChoiceBox box = new ChoiceBox(FXCollections.observableArrayList(
             "Łatwy", "Zaawansowany", "Ekspert")
        );
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stale.getRk().setPage(this);
        box.getSelectionModel().select(0); //domyślnie łatwy
        try {
            Stale.getWk().logIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Stale.getRk().getMaster() != 1){
            box.setVisible(false);
            poziom.setVisible(false);
            rozpocznijButton.setDisable(true);
        }

    }
    @Override
    public void uzupenijGracza(int playerType, int numer, String nick){
        System.out.println("wiadmosc w uzupelnij gracza " + Stale.getRk().getWiad());
        if(numer == 0){
            gracz1.setText(nick);

            if(playerType == 1){
                gracz1.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
        if(numer == 1){
            gracz2.setText(nick);

            if(playerType == 1){
                gracz2.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
        else if(numer == 2){
            gracz3.setText(nick);

            if(playerType == 1){
                gracz3.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
        else if(numer == 3){
            gracz5.setText(nick);

            if(playerType == 1){
                gracz4.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
        else if(numer == 4){
            gracz5.setText(nick);

            if(playerType == 1){
                gracz5.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
    }

    public void usunUzupelnieniGracza(int numer){
        if(numer == 0){
            gracz1.setText("");

        }
        if(numer == 1){
            gracz2.setText("");

        }
        else if(numer == 2){
            gracz3.setText("");

        }
        else if(numer == 3){
            gracz5.setText("");

        }
        else if(numer == 4){
            gracz5.setText("");

        }

    }

    @Override
    public void setAsMaster(){
		box.setVisible(true);
        poziom.setVisible(true);
        rozpocznijButton.setDisable(false);
    }

    public void rozpocznij(ActionEvent actionEvent) throws IOException {
        Stale.getWk().rozpocznijGre(box.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void przejdzDalej(){
        if(Stale.getRk().getMaster() == 0) {
            try {
                LoginScreenController.stage.setScene(utwurzGre());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Stale.getRk().getMaster() == 1){
            try {
                LoginScreenController.stage.setScene(utwurzGreMain());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Scene utwurzGre() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/NormalGameScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }

    private Scene utwurzGreMain() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/LeaderScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }
    private Scene utwurzLoginScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/LoginScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }
    public void goLogin(){
        try {
            LoginScreenController.stage.setScene(utwurzLoginScreen());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

