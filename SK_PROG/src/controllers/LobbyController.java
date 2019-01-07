package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public Button rozpocznijButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stale.getRk().setPage(this);
        gracz1.setText(Stale.getWk().getNick());
        labels = 2;
        try {
            Stale.getWk().logIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Stale.getRk().getMaster() != 1){
            rozpocznijButton.setDisable(true);
        }

    }
    @Override
    public void uzupenijGracza(int playerType){
        if(labels == 2){
            gracz2.setText(Stale.getRk().getWiad());
            labels++;
            if(playerType == 1){
                gracz2.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
        else if(labels == 3){
            gracz3.setText(Stale.getRk().getWiad());
            labels++;
            if(playerType == 1){
                gracz3.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
        else if(labels == 4){
            gracz5.setText(Stale.getRk().getWiad());
            labels++;
            if(playerType == 1){
                gracz4.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
        else if(labels == 5){
            gracz5.setText(Stale.getRk().getWiad());
            labels++;
            if(playerType == 1){
                gracz5.textFillProperty().setValue(Color.ALICEBLUE);
            }
        }
    }

    @Override
    public void setAsMaster(){
        gracz1.textFillProperty().setValue(Color.ALICEBLUE);
    }

    public void rozpocznij(ActionEvent actionEvent) throws IOException {
        Stale.getWk().rozpocznijGre();
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
}

