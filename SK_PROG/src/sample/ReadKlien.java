package sample;

import controllers.LoginScreenController;
import controllers.MainContoller;
import javafx.application.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReadKlien implements Runnable{
    private InputStream is;
    private byte [] tab;
    private String wiad;
    private MainContoller page;


    public ReadKlien(LoginScreenController page1) throws IOException {
        Stale.setSocket(new Socket("127.0.0.1", 1234));
        is = Stale.getSocket().getInputStream();
        tab = new byte[16];
        wiad = "";
        page = page1;
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
            Platform.runLater(() -> page.uzupenijGracza());
        }
//        Platform.runLater(() -> page.czytaj());

    }

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
