package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ReadKlien;
import sample.Stale;
import sample.WriteKlient;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController extends MainContoller implements Initializable {


    public static Stage stage;
    public TextField nikcField;
    public Button button;
    public TextField InetAddress;
    public TextField Port;
    public Label info;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    info.setVisible(false);
    }

    public void conect(ActionEvent actionEvent) throws IOException {
        Stale.setKliknal(1);
        String nick = nikcField.getText();
        Stale.setSocket(new Socket((InetAddress.getText().equals("") ?  InetAddress.getText() : "127.0.0.1"), (Port.getText().equals("") ?  Integer.parseInt(Port.getText()) : 1234)));
        Stale.setRk(new ReadKlien(this));
        Stale.setWk(new WriteKlient(nick));
        Stale.getRk().ropocnij();

    }

    private Scene utwurzLobby() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Lobby.fxml"));
        Parent root = loader.load();
        return new Scene(root);
    }

    public void zostanPrzyjety(){
        try {
            LoginScreenController.stage.setScene(utwurzLobby());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void nieZostanPrzyjety(){
        button.setDisable(true);
        info.setVisible(true);
    }
    public void odblokuj(){
        button.setDisable(false);
        info.setVisible(false);
    }


}
