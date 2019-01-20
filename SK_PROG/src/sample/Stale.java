package sample;

import java.awt.*;
import java.net.Socket;

public class Stale {
    private static Socket socket;
    private static WriteKlient wk;
    private static ReadKlien rk;
    private static int kliknal = 0;


    public static Socket getSocket() {
        return socket;
    }
    public static void start(Thread thread){
        thread.start();
    }
    public static void setSocket(Socket socket) {
        Stale.socket = socket;
    }

    public static WriteKlient getWk() {
        return wk;
    }

    public static void setWk(WriteKlient wk) {
        Stale.wk = wk;
    }

    public static ReadKlien getRk() {
        return rk;
    }

    public static void setRk(ReadKlien rk) {
        Stale.rk = rk;
    }

    public static int getKliknal() {
        return kliknal;
    }

    public static void setKliknal(int kliknal) {
        Stale.kliknal = kliknal;
    }
}
