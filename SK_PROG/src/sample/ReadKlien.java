package sample;

import controllers.LoginScreenController;
import controllers.MainContoller;
import javafx.application.Platform;
import org.omg.PortableInterceptor.INACTIVE;

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
    private boolean jestWiad;
    private String  zapisanaWiad;
    private ArrayList<Integer> slowa;
    private ArrayList<Integer> rigAns;
    private ArrayList<Integer> wrAns;
    private ArrayList<Integer> zgadniete;
    private ArrayList<Integer> drugaRunda;
    private int runda;
    private String zapisanaWiadomosc;
    private int wynikDobre;
    private int wynikZle;
    private int wymaganeDobre;
    private int wymaganeZle;
    private int iloscRund;
    private int  wymaganailoscrund;
    private boolean stop;
    private int iloscGier;
    private int wymaganaIloscGier;



    public ReadKlien(LoginScreenController page1) throws IOException {
        Stale.setSocket(new Socket("127.0.0.1", 1234));
        is = Stale.getSocket().getInputStream();
        tab = new byte[42];
        runda =0;
        jestWiad = false;
        zapisanaWiad= "";
        slowa = new ArrayList<Integer>();
        rigAns= new ArrayList<Integer>();
        wrAns = new ArrayList<Integer>();
        zgadniete = new ArrayList<Integer>(20);
        drugaRunda = new ArrayList<Integer>(20);
        for(int i = 0; i<20; i++){
            zgadniete.add(0);
            drugaRunda.add(0);
        }
        zapisanaWiadomosc = "";
        jestWiad = false;
        wiad = "";
        page = page1;
        master = 0;
        wynikDobre = 0;
        wynikZle = 0;
        wymaganeDobre = 10;
        wymaganeZle = 4;
        iloscRund = 0;
        wymaganailoscrund = 6;
        stop = false;
        iloscGier = 0;
        wymaganaIloscGier =2;

    }

    public void read() throws IOException {
        while(true){
            if(stop)break;
            int len = is.read(tab);
            if(len == -1){
                System.out.println("blad wiadomosci");
                break;
            }
            if(len > 0){
                String temp = new String(tab);

                temp = temp.substring(0,len);
                System.out.println("cala wiadmosc- "+temp);
                while(temp.contains(",")){
                    System.out.println("xd");
                    String newWiad="";
                    if(jestWiad){
                        newWiad = zapisanaWiadomosc;
                        jestWiad = false;
                        zapisanaWiadomosc = "";}
                    wiad = newWiad + temp.substring(0,temp.indexOf(","));
                    if(temp.indexOf(",") == temp.length() - 1){
                        temp = "";
                    }
                    else temp = temp.substring(temp.indexOf(",")+1);
                    System.out.println("wiad- "+wiad);
                    if(!stop)action();

                }
                if(temp.length()>0){
                    zapisanaWiadomosc = temp;
                    jestWiad = true;
                }
                }
            }
        }



    public void action() throws IOException {
        String firstletter;
        System.out.println("action");
        firstletter = wiad.substring(0,1);
        wiad = wiad.substring(1);
        System.out.println(firstletter);
        System.out.println(wiad);
        if(firstletter.equals("v")){
            Stale.getWk().zakonczGre();
            stop = true;
        }
        if(firstletter.equals("i")){ //wiadomość z poziomem i ilością gier
            wymaganaIloscGier= Integer.parseInt(wiad.substring(0,2));
            if(Integer.parseInt(wiad.substring(2))==0)wymaganailoscrund=7;   //łatwy
            else if(Integer.parseInt(wiad.substring(2))==1)wymaganailoscrund=5; //Zawansowany
            else if(Integer.parseInt(wiad.substring(2))==2)wymaganailoscrund=4; //Ekspert

        }
        if(firstletter.equals("b")){
            Platform.runLater(() -> page.odblokuj());
        }
        if(firstletter.equals("c")){
            Platform.runLater(() -> page.nieZostanPrzyjety());
        }
        if(firstletter.equals("z")){
            Platform.runLater(() -> page.zostanPrzyjety());
        }
        if(firstletter.equals("l")){
            Platform.runLater(() -> page.uzupenijGracza(0));
        }
        if(firstletter.equals("s")){
            Platform.runLater(() -> page.uzupenijGracza(1));
        }
        if(firstletter.equals("m")){

            master = 1;
//            Platform.runLater(() -> page.setAsMaster());

        }
//        if(firstletter.equals("r")){
//            Platform.runLater(()->page.przejdzDalej());
//        }
        else if(firstletter.equals("k")) {
            if(iloscGier == wymaganaIloscGier){
                Stale.getWk().zakonczGre();
                stop = true;

            }
            else{
                System.out.println("ilsocgier------------"+iloscGier);
                iloscGier++;
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
                zapisanaWiadomosc = "";
                jestWiad = false;
                wynikDobre = 0;
                wynikZle = 0;
                wymaganeDobre = 10;
                wymaganeZle = 4;
                iloscRund = 0;
                Integer slowo;
                for(int i=0; i<20; i++){
                    slowo = Integer.parseInt(wiad.substring(2*i,2*i+2));
                    slowa.add(slowo);
                }
                Platform.runLater(()->page.przejdzDalej());
            }
        }
        else if(firstletter.equals("a")){
//            Platform.runLater(()->page.ustawKlucz());

            Integer slowo;
            for (int i = 0; i < 10; i++) {
                slowo=Integer.parseInt(Stale.getRk().getWiad().substring(2*i,2*i+2));
                Stale.getRk().getRigAns().add(slowo);
            }
            for (int i=9; i<14; i++)            //zapisuje do List RighAns i WrAns złe i dobre odpowiedzi
            {
                slowo=Integer.parseInt(Stale.getRk().getWiad().substring(2*i,2*i+2));
                Stale.getRk().getWrAns().add(slowo);
            }
            Platform.runLater(()->page.wypelnijPrzyciski());

        }

        else if(firstletter.equals("t")){
            Platform.runLater(()->page.startTimer());
        }
        else if(firstletter.equals("h")){
            runda = 0;
			for(int i =0; i< 20;i++){
                drugaRunda.set(i,0);
            }
            Platform.runLater(()->page.odbierzPodpowiedz());
        }
        else if(firstletter.equals("d")){
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
        else if(firstletter.equals("w")){
            iloscRund++;
            for(int i=0; i < wiad.length()/2;i++){
                int odp = Integer.parseInt(wiad.substring(2*i,2*i+2));
                zgadniete.set(odp,1);
                if(isRight(odp))wynikDobre++;
                if(isWrong(odp))wynikZle++;
            }
            if(wynikZle >= wymaganeZle){//TODO licznie punktow
                //koniec przegranko
               Platform.runLater(()-> page.showAlertAboutGameStatus("Runda przegrana: udzielono zbyt wiele niepoprawnych odpowiedzi"));
                if(master == 1){
                    Stale.getWk().zakonczpartiePrzgranie();
                    master=0;
                }
            }
            else if(wynikDobre >= wymaganeDobre){
                Platform.runLater(()-> page.showAlertAboutGameStatus("Brawo! Odgadliście już wszystkie poprawne odpowiedzi"));
                if(master == 1){
                    Stale.getWk().zakonczPartieWygranie();
                    master=0;
                }
            }
            else if(iloscRund ==wymaganailoscrund){
                //przegranko
                if(master==1){
                    Platform.runLater(()-> page.showAlertAboutGameStatus("Niestety - nie zdążyliście odgadnąć wszystkich poprawnych odpowiedzi"));
                    Stale.getWk().zakonczpartiePrzgranie();
                    master=0;
                }
            }
			else{
				 Platform.runLater(()->page.updatePrzyciski());
			}

            if(iloscGier == wymaganaIloscGier){
                Stale.getWk().zakonczGre();
                stop = true;

            }

			
           
        }
//        Platform.runLater(() -> page.czytaj());

    }

    public boolean isRight(Integer index){
        return rigAns.contains(Stale.getRk().getSlowa().get(index));
    }
    public boolean isWrong(Integer index){
        return wrAns.contains(Stale.getRk().getSlowa().get(index));
    }

    public void updateWynik(){

    }


    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
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
