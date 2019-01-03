package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    public void uzupenijGracza(){
        if(labels == 2){
            gracz2.setText(Stale.getRk().getWiad());
            labels++;
        }
        else if(labels == 3){
            gracz3.setText(Stale.getRk().getWiad());
            labels++;
        }
        else if(labels == 4){
            gracz5.setText(Stale.getRk().getWiad());
            labels++;
        }
        else if(labels == 5){
            gracz5.setText(Stale.getRk().getWiad());
            labels++;
        }
    }
}

