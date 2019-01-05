package sample;

import controllers.LoginScreenController;
import controllers.MainContoller;
import controllers.LobbyController;
import javafx.application.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReadKlien implements Runnable{
    private InputStream is;
    private byte [] tab;
    private String wiad;
    private MainContoller page;
    private int master;
    private ArrayList<String> slowa;
    private ArrayList<String> rigAns;
    private ArrayList<String> wrAns;


    public ReadKlien(LoginScreenController page1) throws IOException {
        Stale.setSocket(new Socket("127.0.0.1", 1234));
        is = Stale.getSocket().getInputStream();
        tab = new byte[41];
        slowa = new ArrayList<String>();
        rigAns= new ArrayList<String>();
        wrAns = new ArrayList<String>();
        wiad = "";
        page = page1;
        master = 0;
    }
    public void read() throws IOException {
        while(true){
            System.out.println("niezepsulem");
            int len = is.read(tab);
            if(len == -1){
                System.out.println("blad wiadomosci");
                break;
            }
            if(len > 0){
                String temp = new String(tab);
                temp = temp.substring(0,len);
                wiad = temp;
                System.out.println("Długość odebranej wiadomści=" + len);
                System.out.println(wiad);
                action();
                }
        }
    }

    public MainContoller getPage() {
        return page;
    }

    public void setPage(MainContoller page) {
        this.page = page;
    }

    public void action(){
        String firstletter;
        firstletter = wiad.substring(0,1);
        wiad = wiad.substring(1,wiad.length());
        if(firstletter.equals("l")){
            Platform.runLater(() -> page.uzupenijGracza(0));
        }
        if(firstletter.equals("s")){
            Platform.runLater(() -> page.uzupenijGracza(1));
        }
        if(firstletter.equals("m")){
            master = 1;
            Platform.runLater(() -> page.setAsMaster());
        }
        if(firstletter.equals("r")){
            Platform.runLater(()->page.przejdzDalej());
        }
        if(firstletter.equals("k")) {
            Platform.runLater(()->page.czytajSlowa());
        }
        if(firstletter.equals("a")){
            Platform.runLater(()->page.ustawKlucz());
        }
//        Platform.runLater(() -> page.czytaj());

    }

    public int getMaster() { return master; }

    public void setMaster(int master) { this.master = master; }

    public String getWiad() {
        return wiad;
    }

    public void setWiad(String wiad) {
        this.wiad = wiad;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public byte[] getTab() {
        return tab;
    }

    public void setTab(byte[] tab) {
        this.tab = tab;
    }

    public List<String> getSlowa() {
        return slowa;
    }

    public void setSlowa(ArrayList<String> slowa) {
        this.slowa = slowa;
    }

    public ArrayList<String> getRigAns() {
        return rigAns;
    }

    public void setRigAns(ArrayList<String> rigAns) {
        this.rigAns = rigAns;
    }

    public ArrayList<String> getWrAns() {
        return wrAns;
    }

    public void setWrAns(ArrayList<String> wrAns) {
        this.wrAns = wrAns;
    }


    public void ropocnij(){
        Stale.start(new Thread(this));
    }

    @Override
    public void run() {
        try {
            System.out.println("dzialam");
            this.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
