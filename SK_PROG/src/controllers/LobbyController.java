package controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Stale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController extends MainContoller implements Initializable {
    public static Stage stage;
    public Label gracz1;
    public Label gracz2;
    public Label gracz3;
    public Label gracz4;
    public Label gracz5;
    public int labels;
    public Button rozpocznij;

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
    public void gotowy(javafx.event.ActionEvent actionEvent) throws InterruptedException, IOException { //when all players are ready, launch game
        if(rozpocznij.getText().equals("rozpocznij"))
        {
            rozpocznij.setText("gotowy");
            rozpocznij.setStyle("-fx-background-color: #00ff00");
        }
        else{
            rozpocznij.setText("rozpocznij");
            rozpocznij.setStyle("-fx-background-color: #ffffff");
        }
        rozpocznij.setDisable(true);
        Thread.sleep(150);
        rozpocznij.setDisable(false);

        Stale.getWk().gotowosc();

        //wiadomość do serwera
    }
    @Override
    public void launchGame() throws IOException {
        Stale.getRk().ropocnij();
        LobbyController.stage.setScene(utworzGre());
    }
    private Scene utworzGre() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/NormalGameScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }



    @Override
    public void setAsMaster(){
        gracz1.textFillProperty().setValue(Color.ALICEBLUE);
    }


}

