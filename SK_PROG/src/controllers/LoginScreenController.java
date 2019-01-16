package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ReadKlien;
import sample.Stale;
import sample.WriteKlient;

import java.io.IOException;

public class LoginScreenController extends MainContoller{


    public static Stage stage;
    public TextField nikcField;
    public Button button;

    public void conect(ActionEvent actionEvent) throws IOException {
        String nick;
        if(nikcField.getText().equals("")){ nick="Nowy Gracz";}
        else{ nick= nikcField.getText();}
        Stale.setRk(new ReadKlien(this));
        Stale.setWk(new WriteKlient(nick));
        Stale.getRk().ropocnij();
        try {
            Stale.getWk().DajOSobieZnac();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene utwurzLobby() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Lobby.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }

    public void pozwolSieZalogowac(){
        try {
            LoginScreenController.stage.setScene(utwurzLobby());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
