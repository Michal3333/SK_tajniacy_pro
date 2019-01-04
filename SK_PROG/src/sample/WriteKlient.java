package sample;

import java.io.IOException;
import java.io.OutputStream;

public class WriteKlient {
    private OutputStream os;
    private String nick;
    byte [] wiadomosc;
    public WriteKlient(String nick1) throws IOException {
        os = Stale.getSocket().getOutputStream();
        wiadomosc = new byte[16];
        nick = nick1;

    }
    public void write() throws IOException {
        os.write(wiadomosc);
    }
    public void logIn() throws IOException {
        wiadomosc = ("l" + nick).getBytes();
        os.write(wiadomosc);
    }
    public void gotowosc() throws IOException { //wysyłamy serwerowi wiadomość o naszej gotowości
        wiadomosc = "j".getBytes();
        os.write(wiadomosc);
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public byte[] getWiadomosc() {
        return wiadomosc;
    }

    public void setWiadomosc(byte[] wiadomosc) {
        this.wiadomosc = wiadomosc;
    }
}