package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.Stale;
import resources.Words;

import java.io.IOException;
import java.util.Timer;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class NormalGameScreenController extends MainContoller implements Initializable  {
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
    public List <Integer> zaznaczone;
    public Button wysOdp;
    public Label podpowiedz;
    public Label ilosc;
    public Label voteInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stale.getRk().setPage(this);
        for(int i = 0; i < 20; i++){
          tabela.getChildren().get(i).setDisable(true);
        }
        zaznaczone = new ArrayList<Integer>(20);
        for(int i = 0; i<20; i++) {
            zaznaczone.add(0);
        }
        zeruj();
        wysOdp.setDisable(true);

//        Label label = tabela.getChildren().get(1).;
    }


    public String slowoAsText(Integer index){
        return Words.words.get(index - 1);
    }
    public boolean isRight(Integer index){
        return Stale.getRk().getRigAns().contains(Stale.getRk().getSlowa().get(index));
    }
    public boolean isWrong(Integer index){
        return Stale.getRk().getWrAns().contains(Stale.getRk().getSlowa().get(index));
    }
    public void zeruj(){
        wysOdp.setDisable(false);
       for(int i = 0;i < 20 ;i++){
           if(Stale.getRk().getZgadniete().get(i) == 0){
               tabela.getChildren().get(i).setStyle("-fx-text-fill: black");
               zaznaczone.set(i,0);
               tabela.getChildren().get(i).setDisable(false);
           }
       }
    }
    public void zerujZaznaczone(){
        for(int i=0;i<20;i++){
            zaznaczone.set(i,0);
        }
    }

    public void startTimer(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                wysOdp.setDisable(true);
            }
        },22*1000);
    }

    public void odbierzPodpowiedz(){
        ilosc.setText(Stale.getRk().getWiad().substring(0,1));
        podpowiedz.setText(Stale.getRk().getWiad().substring(1));
        voteInfo.setText("");
        zeruj();
        zerujZaznaczone();
        wysOdp.setDisable(false);
        startTimer();

    }

    public void odbierzDrugaTure(){
        voteInfo.setText("Oto wybrane wśród graczy odpowiedzi - zagłosuj!");
        zeruj();
        updatePrzyciski();
        if(Stale.getRk().getDrugaRunda().get(0) == 0){ wyraz1.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(1) == 0){ wyraz2.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(2) == 0){ wyraz3.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(3) == 0){ wyraz4.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(4) == 0){ wyraz5.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(5) == 0){ wyraz6.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(6) == 0){ wyraz7.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(7) == 0){ wyraz8.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(8) == 0){ wyraz9.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(9) == 0){ wyraz10.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(10) == 0){ wyraz11.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(11) == 0){ wyraz12.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(12) == 0){ wyraz13.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(13) == 0){ wyraz14.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(14) == 0){ wyraz15.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(15) == 0){ wyraz16.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(16) == 0){ wyraz17.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(17) == 0){ wyraz18.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(18) == 0){ wyraz19.setDisable(true); }
        if(Stale.getRk().getDrugaRunda().get(19) == 0){ wyraz20.setDisable(true); }

        zerujZaznaczone();
        startTimer();

    }


    public void wypelnijPrzyciski(){
        wyraz1.setText(slowoAsText(Stale.getRk().getSlowa().get(0)));
        wyraz2.setText(slowoAsText(Stale.getRk().getSlowa().get(1)));
        wyraz3.setText(slowoAsText(Stale.getRk().getSlowa().get(2)));
        wyraz4.setText(slowoAsText(Stale.getRk().getSlowa().get(3)));
        wyraz5.setText(slowoAsText(Stale.getRk().getSlowa().get(4)));
        wyraz6.setText(slowoAsText(Stale.getRk().getSlowa().get(5)));
        wyraz7.setText(slowoAsText(Stale.getRk().getSlowa().get(6)));
        wyraz8.setText(slowoAsText(Stale.getRk().getSlowa().get(7)));
        wyraz9.setText(slowoAsText(Stale.getRk().getSlowa().get(8)));
        wyraz10.setText(slowoAsText(Stale.getRk().getSlowa().get(9)));
        wyraz11.setText(slowoAsText(Stale.getRk().getSlowa().get(10)));
        wyraz12.setText(slowoAsText(Stale.getRk().getSlowa().get(11)));
        wyraz13.setText(slowoAsText(Stale.getRk().getSlowa().get(12)));
        wyraz14.setText(slowoAsText(Stale.getRk().getSlowa().get(13)));
        wyraz15.setText(slowoAsText(Stale.getRk().getSlowa().get(14)));
        wyraz16.setText(slowoAsText(Stale.getRk().getSlowa().get(15)));
        wyraz17.setText(slowoAsText(Stale.getRk().getSlowa().get(16)));
        wyraz18.setText(slowoAsText(Stale.getRk().getSlowa().get(17)));
        wyraz19.setText(slowoAsText(Stale.getRk().getSlowa().get(18)));
        wyraz20.setText(slowoAsText(Stale.getRk().getSlowa().get(19)));

    }

    public void updatePrzyciski(){
        if(Stale.getRk().getZgadniete().get(0) == 1){
            if(isRight(0)){wyraz1.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(0)){wyraz1.textFillProperty().setValue(Color.RED);}
            else {wyraz1.textFillProperty().setValue(Color.YELLOW);}
            wyraz1.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(1) == 1){
            if(isRight(1)){wyraz2.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(1)){wyraz2.textFillProperty().setValue(Color.RED);}
            else {wyraz2.textFillProperty().setValue(Color.YELLOW);}
            wyraz2.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(2) == 1){
            if(isRight(2)){wyraz3.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(2)){wyraz3.textFillProperty().setValue(Color.RED);}
            else {wyraz3.textFillProperty().setValue(Color.YELLOW);}
            wyraz3.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(3) == 1){
            if(isRight(3)){wyraz4.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(3)){wyraz4.textFillProperty().setValue(Color.RED);}
            else {wyraz4.textFillProperty().setValue(Color.YELLOW);}
            wyraz4.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(4) == 1){
            if(isRight(4)){wyraz5.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(4)){wyraz5.textFillProperty().setValue(Color.RED);}
            else {wyraz5.textFillProperty().setValue(Color.YELLOW);}
            wyraz5.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(5) == 1){
            if(isRight(5)){wyraz6.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(5)){wyraz6.textFillProperty().setValue(Color.RED);}
            else {wyraz6.textFillProperty().setValue(Color.YELLOW);}
            wyraz6.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(6) == 1){
            if(isRight(6)){wyraz7.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(6)){wyraz7.textFillProperty().setValue(Color.RED);}
            else {wyraz7.textFillProperty().setValue(Color.YELLOW);}
            wyraz7.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(7) == 1){
            if(isRight(7)){wyraz8.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(7)){wyraz8.textFillProperty().setValue(Color.RED);}
            else {wyraz8.textFillProperty().setValue(Color.YELLOW);}
            wyraz8.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(8) == 1){
            if(isRight(8)){wyraz9.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(8)){wyraz9.textFillProperty().setValue(Color.RED);}
            else {wyraz9.textFillProperty().setValue(Color.YELLOW);}
            wyraz9.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(9) == 1){
            if(isRight(9)){wyraz10.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(9)){wyraz10.textFillProperty().setValue(Color.RED);}
            else {wyraz10.textFillProperty().setValue(Color.YELLOW);}
            wyraz10.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(10) == 1){
            if(isRight(10)){wyraz11.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(10)){wyraz11.textFillProperty().setValue(Color.RED);}
            else {wyraz11.textFillProperty().setValue(Color.YELLOW);}
            wyraz11.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(11) == 1){
            if(isRight(11)){wyraz12.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(11)){wyraz12.textFillProperty().setValue(Color.RED);}
            else {wyraz12.textFillProperty().setValue(Color.YELLOW);}
            wyraz12.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(12) == 1){
            if(isRight(12)){wyraz13.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(12)){wyraz13.textFillProperty().setValue(Color.RED);}
            else {wyraz13.textFillProperty().setValue(Color.YELLOW);}
            wyraz13.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(13) == 1){
            if(isRight(13)){wyraz14.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(13)){wyraz14.textFillProperty().setValue(Color.RED);}
            else {wyraz14.textFillProperty().setValue(Color.YELLOW);}
            wyraz14.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(14) == 1){
            if(isRight(14)){wyraz15.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(14)){wyraz15.textFillProperty().setValue(Color.RED);}
            else {wyraz15.textFillProperty().setValue(Color.YELLOW);}
            wyraz15.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(15) == 1){
            if(isRight(15)){wyraz16.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(15)){wyraz16.textFillProperty().setValue(Color.RED);}
            else {wyraz16.textFillProperty().setValue(Color.YELLOW);}
            wyraz16.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(16) == 1){
            if(isRight(16)){wyraz17.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(16)){wyraz17.textFillProperty().setValue(Color.RED);}
            else {wyraz17.textFillProperty().setValue(Color.YELLOW);}
            wyraz17.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(17) == 1){
            if(isRight(17)){wyraz18.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(17)){wyraz18.textFillProperty().setValue(Color.RED);}
            else {wyraz18.textFillProperty().setValue(Color.YELLOW);}
            wyraz18.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(18) == 1){
            if(isRight(18)){wyraz19.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(18)){wyraz19.textFillProperty().setValue(Color.RED);}
            else {wyraz19.textFillProperty().setValue(Color.YELLOW);}
            wyraz1.setDisable(true);
        }
        if(Stale.getRk().getZgadniete().get(19) == 1){
            if(isRight(19)){wyraz20.textFillProperty().setValue(javafx.scene.paint.Color.GREEN);}
            else if(isWrong(19)){wyraz20.textFillProperty().setValue(Color.RED);}
            else {wyraz20.textFillProperty().setValue(Color.YELLOW);}
            wyraz20.setDisable(true);
        }
        zeruj();

    }

    public void wyr1(ActionEvent actionEvent) {
        if(zaznaczone.get(0) == 0) {
            zaznaczone.set(0, 1);
            wyraz1.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(0,0);
            wyraz1.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr2(ActionEvent actionEvent) {
        if(zaznaczone.get(1) == 0) {
            zaznaczone.set(1, 1);
            wyraz2.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(1,0);
            wyraz2.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr3(ActionEvent actionEvent) {
        if(zaznaczone.get(2) == 0) {
            zaznaczone.set(2, 1);
            wyraz3.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(2,0);
            wyraz3.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr4(ActionEvent actionEvent) {
        if(zaznaczone.get(3) == 0) {
            zaznaczone.set(3, 1);
            wyraz4.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(3,0);
            wyraz4.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr5(ActionEvent actionEvent) {
        if(zaznaczone.get(4) == 0) {
            zaznaczone.set(4, 1);
            wyraz5.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(4,0);
            wyraz5.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr6(ActionEvent actionEvent) {
        if(zaznaczone.get(5) == 0) {
            zaznaczone.set(5, 1);
            wyraz6.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(5,0);
            wyraz6.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr7(ActionEvent actionEvent) {
        if(zaznaczone.get(6) == 0) {
            zaznaczone.set(6, 1);
            wyraz7.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(6,0);
            wyraz7.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr8(ActionEvent actionEvent) {
        if(zaznaczone.get(7) == 0) {
            zaznaczone.set(7, 1);
            wyraz8.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(7,0);
            wyraz8.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr9(ActionEvent actionEvent) {
        if(zaznaczone.get(8) == 0) {
            zaznaczone.set(8, 1);
            wyraz9.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(8,0);
            wyraz9.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr10(ActionEvent actionEvent) {
        if(zaznaczone.get(9) == 0) {
            zaznaczone.set(9, 1);
            wyraz10.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(9,0);
            wyraz10.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr11(ActionEvent actionEvent) {
        if(zaznaczone.get(10) == 0) {
            zaznaczone.set(10, 1);
            wyraz11.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(10,0);
            wyraz11.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr12(ActionEvent actionEvent) {
        if(zaznaczone.get(11) == 0) {
            zaznaczone.set(11, 1);
            wyraz12.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(11,0);
            wyraz12.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr13(ActionEvent actionEvent) {
        if(zaznaczone.get(12) == 0) {
            zaznaczone.set(12, 1);
            wyraz13.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(12,0);
            wyraz13.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr14(ActionEvent actionEvent) {
        if(zaznaczone.get(13) == 0) {
            zaznaczone.set(13, 1);
            wyraz14.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(13,0);
            wyraz14.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr15(ActionEvent actionEvent) {
        if(zaznaczone.get(14) == 0) {
            zaznaczone.set(14, 1);
            wyraz15.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(14,0);
            wyraz15.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr16(ActionEvent actionEvent) {
        if(zaznaczone.get(15) == 0) {
            zaznaczone.set(15, 1);
            wyraz16.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(15,0);
            wyraz16.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr17(ActionEvent actionEvent) {
        if(zaznaczone.get(16) == 0) {
            zaznaczone.set(16, 1);
            wyraz17.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(16,0);
            wyraz17.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr18(ActionEvent actionEvent) {
        if(zaznaczone.get(17) == 0) {
            zaznaczone.set(17, 1);
            wyraz18.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(17,0);
            wyraz18.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr19(ActionEvent actionEvent) {
        if(zaznaczone.get(18) == 0) {
            zaznaczone.set(18, 1);
            wyraz19.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(18,0);
            wyraz19.setStyle("-fx-text-fill: black");
        }
    }

    public void wyr20(ActionEvent actionEvent) {
        if(zaznaczone.get(19) == 0) {
            zaznaczone.set(19, 1);
            wyraz20.setStyle("-fx-text-fill: green");
        }
        else{
            zaznaczone.set(19,0);
            wyraz20.setStyle("-fx-text-fill: black");
        }
    }

    public void wyslijOdp(ActionEvent actionEvent) throws IOException {
        String odp = "";
        for(int i = 0; i < 20; i++){
            if(zaznaczone.get(i) == 1){
                tabela.getChildren().get(i).setStyle("-fx-text-fill: black");
                if(i < 10){
                    odp = odp + "0" + Integer.toString(i);
                }
                else{
                    odp = odp + Integer.toString(i);
                }
            }
        }
        Stale.getWk().wyslijOdpowiedz(odp);
        wysOdp.setDisable(true);
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
    public void showAlertAboutGameStatus(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Wynik rundy");
        alert.setContentText(info);

        alert.showAndWait();

    }

    public void goLogin(){
        try {
            LoginScreenController.stage.setScene(utwurzLoginScreen());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

