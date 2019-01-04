package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NormalGameScreenController implements Initializable {
    public Button wyraz1;
    public Button wyraz2;
    public Button wyraz3;
    public Button wyraz4;
    public Button wyraz5;
    public Button wyraz6;
    public Button wyraz7;
    public Button wyraz8;
    public Button wyraz9;
    public Button wyraz10;
    public Button wyraz11;
    public Button wyraz12;
    public Button wyraz13;
    public Button wyraz14;
    public Button wyraz15;
    public Button wyraz16;
    public Button wyraz17;
    public Button wyraz18;
    public Button wyraz19;
    public Button wyraz20;
    public GridPane tabela;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i = 0; i < 20; i++){
          tabela.getChildren().get(i).setDisable(true);
        }
//        Label label = tabela.getChildren().get(1).;
    }

    public void wypelnijPrzyciski(){

    }

    public void updatePrzyciski(){

    }
}

