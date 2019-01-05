package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import sample.Stale;
import resources.Words;
import java.net.URL;
import java.util.ResourceBundle;

public class NormalGameScreenController extends MainContoller implements Initializable {
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
        Stale.getRk().setPage(this);
        for(int i = 0; i < 20; i++){
          tabela.getChildren().get(i).setDisable(true);
        }
//        Label label = tabela.getChildren().get(1).;
    }
    @Override
    public void czytajSlowa(){
        Integer slowo;
        for(int i=0; i<20; i++){
            slowo=Integer.parseInt(Stale.getRk().getWiad().substring(2*i,2*i+2));
            Stale.getRk().getSlowa().add(slowo);
            //tabela.getChildren().get(i).setAccessibleText(slowo);
        }
    }
    @Override
    public void ustawKlucz() {
        Integer slowo;
        for (int i = 0; i < 9; i++) {
            slowo=Integer.parseInt(Stale.getRk().getWiad().substring(2*i,2*i+2));
            Stale.getRk().getRigAns().add(slowo);
        }
        for (int i=9; i<13; i++)            //zapisuje do List RighAns i WrAns złe i dobre odpowiedzi
        {
            slowo=Integer.parseInt(Stale.getRk().getWiad().substring(2*i,2*i+2));
            Stale.getRk().getWrAns().add(slowo);
        }
        System.out.println("uzupełniłem rozwiązania");
    }
    public String slowoAsText(Integer index){
        return Words.words.get(index);
    }
    public boolean isRight(Integer index){
        for(Integer i : Stale.getRk().getRigAns()) {
            if(index == i) return true;
        }
        return false;
    }
    public boolean isWrong(Integer index){
        for(Integer i : Stale.getRk().getWrAns()) {
            if(index == i) return true;
        }
        return false;
    }



    public void wypelnijPrzyciski(){

    }

    public void updatePrzyciski(){

    }
}

