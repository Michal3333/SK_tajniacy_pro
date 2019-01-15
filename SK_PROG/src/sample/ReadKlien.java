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
    private ArrayList<Integer> slowa;
    private ArrayList<Integer> rigAns;
    private ArrayList<Integer> wrAns;
    private ArrayList<Integer> zgadniete;
    private ArrayList<Integer> drugaRunda;
    private int runda;


    public ReadKlien(LoginScreenController page1) throws IOException {
        Stale.setSocket(new Socket("127.0.0.1", 1234));
        is = Stale.getSocket().getInputStream();
        tab = new byte[41];
        runda =0;
        slowa = new ArrayList<Integer>();
        rigAns= new ArrayList<Integer>();
        wrAns = new ArrayList<Integer>();
        zgadniete = new ArrayList<Integer>(20);
        drugaRunda = new ArrayList<Integer>(20);
        for(int i = 0; i<20; i++){
            zgadniete.add(0);
            drugaRunda.add(0);
        }

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
                System.out.println(wiad);
                action();
                }
        }
    }


    public void action(){
        String firstletter;
        firstletter = wiad.substring(0,1);
        wiad = wiad.substring(1);
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
//        if(firstletter.equals("r")){
//            Platform.runLater(()->page.przejdzDalej());
//        }
        if(firstletter.equals("k")) {
//            Platform.runLater(()->page.czytajSlowa());

            Integer slowo;
            for(int i=0; i<20; i++){
                slowo = Integer.parseInt(wiad.substring(2*i,2*i+2));
                slowa.add(slowo);
            }
            Platform.runLater(()->page.przejdzDalej());
        }
        if(firstletter.equals("a")){
//            Platform.runLater(()->page.ustawKlucz());

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
            Platform.runLater(()->page.wypelnijPrzyciski());

        }

        if(firstletter.equals("t")){
            Platform.runLater(()->page.startTimer());
        }
        if(firstletter.equals("h")){
            runda = 0;
            Platform.runLater(()->page.odbierzPodpowiedz());
        }
        if(firstletter.equals("d")){
            runda = 1;
            for(int i=0; i < wiad.length()/2;i++){
                drugaRunda.set(Integer.parseInt(wiad.substring(2*i,2*i+2)),1);
            }
            if(master == 1){
                Platform.runLater(()->page.startTimer());
            }
            else{
                Platform.runLater(()->page.odbierzDrugaTure());
            }
        }
        if(firstletter.equals("w")){
            for(int i=0; i < wiad.length()/2;i++){
                zgadniete.set(Integer.parseInt(wiad.substring(2*i,2*i+2)),1);
            }
            Platform.runLater(()->page.updatePrzyciski());
        }
//        Platform.runLater(() -> page.czytaj());

    }

    public int getRunda() {
        return runda;
    }

    public void setRunda(int runda) {
        this.runda = runda;
    }

    public ArrayList<Integer> getDrugaRunda() {
        return drugaRunda;
    }

    public void setDrugaRunda(ArrayList<Integer> drugaRunda) {
        this.drugaRunda = drugaRunda;
    }

    public ArrayList<Integer> getZgadniete() {
        return zgadniete;
    }

    public void setZgadniete(ArrayList<Integer> zgadniete) {
        this.zgadniete = zgadniete;
    }

    public MainContoller getPage() {
        return page;
    }

    public void setPage(MainContoller page) {
        this.page = page;
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

    public List<Integer> getSlowa() {
        return slowa;
    }

    public void setSlowa(ArrayList<Integer> slowa) {
        this.slowa = slowa;
    }

    public ArrayList<Integer> getRigAns() {
        return rigAns;
    }

    public void setRigAns(ArrayList<Integer> rigAns) {
        this.rigAns = rigAns;
    }

    public ArrayList<Integer> getWrAns() {
        return wrAns;
    }

    public void setWrAns(ArrayList<Integer> wrAns) {
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
