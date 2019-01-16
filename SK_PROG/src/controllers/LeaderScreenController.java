package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import resources.Words;
import sample.Stale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class
LeaderScreenController extends MainContoller implements Initializable {
@FXML
    public Label slowo1;
    public Label slowo2;
    public Label slowo3;
    public Label slowo4;
    public Label slowo5;
    public Label slowo6;
    public Label slowo7;
    public Label slowo8;
    public Label slowo9;
    public Label slowo10;
    public Label slowo11;
    public Label slowo12;
    public Label slowo13;
    public Label slowo14;
    public Label slowo15;
    public Label slowo16;
    public Label slowo17;
    public Label slowo18;
    public Label slowo19;
    public Label slowo20;
    public Button play;
    public TextField hint;
    public TextField numberOfWords;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Stale.getRk().setPage(this);

    }

    public String slowoAsText(Integer index){
        return Words.words.get(index-1);  //@michal tu powinno być -1 xD jezu ile tego szukałem
    }

    public boolean isRight(Integer index){
       return Stale.getRk().getRigAns().contains(index);

    }
    public boolean isWrong(Integer index){
        return Stale.getRk().getWrAns().contains(index);
    }

    public void startTimer(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Stale.getWk().koniecCzasu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },22*1000);
    }

    public void updatePrzyciski(){
        play.setDisable(false);
    }

    public void wypelnijPrzyciski(){
        slowo1.setText(slowoAsText(Stale.getRk().getSlowa().get(0)));
        slowo2.setText(slowoAsText(Stale.getRk().getSlowa().get(1)));
        slowo3.setText(slowoAsText(Stale.getRk().getSlowa().get(2)));
        slowo4.setText(slowoAsText(Stale.getRk().getSlowa().get(3)));
        slowo5.setText(slowoAsText(Stale.getRk().getSlowa().get(4)));
        slowo6.setText(slowoAsText(Stale.getRk().getSlowa().get(5)));
        slowo7.setText(slowoAsText(Stale.getRk().getSlowa().get(6)));
        slowo8.setText(slowoAsText(Stale.getRk().getSlowa().get(7)));
        slowo9.setText(slowoAsText(Stale.getRk().getSlowa().get(8)));
        slowo10.setText(slowoAsText(Stale.getRk().getSlowa().get(9)));
        slowo11.setText(slowoAsText(Stale.getRk().getSlowa().get(10)));
        slowo12.setText(slowoAsText(Stale.getRk().getSlowa().get(11)));
        slowo13.setText(slowoAsText(Stale.getRk().getSlowa().get(12)));
        slowo14.setText(slowoAsText(Stale.getRk().getSlowa().get(13)));
        slowo15.setText(slowoAsText(Stale.getRk().getSlowa().get(14)));
        slowo16.setText(slowoAsText(Stale.getRk().getSlowa().get(15)));
        slowo17.setText(slowoAsText(Stale.getRk().getSlowa().get(16)));
        slowo18.setText(slowoAsText(Stale.getRk().getSlowa().get(17)));
        slowo19.setText(slowoAsText(Stale.getRk().getSlowa().get(18)));
        slowo20.setText(slowoAsText(Stale.getRk().getSlowa().get(19)));

        while(Stale.getRk().getRigAns().size()<10);
        while(Stale.getRk().getWrAns().size()<4);
        if(isRight(Stale.getRk().getSlowa().get(0))){slowo1.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(0))){slowo1.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(1))){slowo2.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(1))){slowo2.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(2))){slowo3.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(2))){slowo3.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(3))){slowo4.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(3))){slowo4.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(4))){slowo5.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(4))){slowo5.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(5))){slowo6.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(5))){slowo6.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(6))){slowo7.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(6))){slowo7.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(7))){slowo8.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(7))){slowo8.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(8))){slowo9.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(8))){slowo9.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(9))){slowo10.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(9))){slowo10.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(10))){slowo11.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(10))){slowo11.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(11))){slowo12.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(11))){slowo12.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(12))){slowo13.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(12))){slowo13.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(13))){slowo14.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(13))){slowo14.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(14))){slowo15.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(14))){slowo15.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(15))){slowo16.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(15))){slowo16.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(16))){slowo17.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(16))){slowo17.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(17))){slowo18.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(17))){slowo18.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(18))){slowo19.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(18))){slowo19.textFillProperty().setValue(Color.RED);}
        if(isRight(Stale.getRk().getSlowa().get(19))){slowo20.textFillProperty().setValue(Color.GREEN);}
        else if(isWrong(Stale.getRk().getSlowa().get(19))){slowo20.textFillProperty().setValue(Color.RED);}



    }

    public void wyslijPod(ActionEvent actionEvent) throws IOException {
        Stale.getWk().wyslijPodpowiedz(hint.getText(), Integer.parseInt(numberOfWords.getText()));
        play.setDisable(true);
    }
}
