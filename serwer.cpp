#include <iostream>
#include <unistd.h>
#include <fcntl.h>
#include <sys/socket.h>
#include <sys/epoll.h>
#include <netinet/in.h>
#include <stdio.h>
#include <sys/types.h>
#include <netdb.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>
#include <vector>
#include <algorithm>
#include <cstdlib>
#include <inttypes.h>
using namespace std;
int playersFd[10];
int oczekujacy[20];
int liczbaOczekujacych =0;
string playersNicks[10];
int currentPlayer;
int numberPlayer;
string temp;
int numbers[20];
int status = 0;
int tabodp[20];
int iloscpodpowiedzi;
int przegraneRundy=0;
int wtgraneRundy=0;
int iloscgier = 0;
int wymaganaIloscGier = 4; //TODO ustawaic od graczy
bool notStarted = true;

void OdblokujWszystkich(){
    for(int i =0;i<liczbaOczekujacych ;i++){
        write(oczekujacy[i],"b,",2);
        oczekujacy[i] =0;
    }
    liczbaOczekujacych =0;

}

void ustawMainPlayera(int nr){
    currentPlayer = nr;
    write(playersFd[nr],"m,",2);
    cout<<"m,"<<endl;

}
void zeruj(){
    for(int i = 0; i < 20; i++){
    tabodp[i] = 0;
    }
}
string podlicz(){
    string a="";
    for (int i = 0; i < iloscpodpowiedzi; i++){
        int maxi=0;
        int maxindeks=0;
        for(int j = 0; j < 20; j++){
            if(tabodp[j] > maxi){
                    maxi = tabodp[j];
                    maxindeks = j;
            }
        }
        if(maxi>0){
            if (maxindeks < 10) {
                a = a + '0' + to_string(maxindeks);
            }
            else {a = a + to_string(maxindeks);}
        }
        tabodp[maxindeks] = 0;
    }
    return a;
}

void setTable(char *tab, string option){
    string number;
    strcpy(tab, option.c_str());
    for (int i = 0; i < 20; i++) {
        if (numbers[i] < 10) {
            number = '0' + to_string(numbers[i]);
        }
        else
        {number = to_string(numbers[i]);}
        strcpy(tab + (1 + 2 * i), number.c_str());
    }
}

void obsluz(char polecenie, int sender) {
    if (polecenie == 'l') {
        int current;

        for(int i=0;i<5 ;i++){
            if(playersFd[i] == sender){
                current = i;
            }
        }
            cout<<"wysylam wszytkim nowego"<<endl;
        for (int i = 0; i < 5; i++) {
            if(playersFd[i]!=-1){
                if (playersFd[i] == sender){
                    playersNicks[i] = temp;

                }
                else{
                    char tabtemp[21];
                    string nickznumerem ="";
                    nickznumerem = temp[0] + to_string(current) + temp.substr(1) + ",";
                    strcpy(tabtemp, nickznumerem.c_str());
                    write(playersFd[i], tabtemp, nickznumerem.size());
                    write(1, tabtemp, nickznumerem.size());
                    write(1, "\n", 1);
                }
            }
        }
        cout<<"wysylam nowemu wysztskich"<<endl;
        for (int i = 0; i < 5; i++) {
            if(playersFd[i] != -1 ){
                char tabtemp[21];
                string nickname = playersNicks[i] + ",";
                string nickznumerem ="";
                nickznumerem = nickname[0] + to_string(i) + nickname.substr(1);
                strcpy(tabtemp, nickznumerem.c_str());

                if (i == currentPlayer) {
                    tabtemp[0] = 's';
                }

                write(sender, tabtemp, nickznumerem.size());
                write(1, tabtemp, nickznumerem.size());
                write(1, "\n", 1);
            }
        }
    } else if (polecenie == 'r') { //rozpoczęcie gry

        notStarted = false;
        int n;
        bool unique;
        char tabtemp[42];
        string wiad;
        wymaganaIloscGier=2*numberPlayer;
        if(numberPlayer < 5) wiad="i0"+ to_string(2*numberPlayer) + temp.substr(1,1) + ",";
        else wiad="i10,"; //bo max=5, więc 2x5=10 quickmaths XD
        strcpy(tabtemp, wiad.c_str());

        for (int i = 0; i < 5; i++) {
            if (playersFd[i] != -1) {
                write(playersFd[i], tabtemp, 5);   //wysyłanie info o ilosci rund do wszystkich
            }
        }
                for (int i = 0; i < 20; i++) {
            n = rand() % 99 + 1;
            unique = true;
            for (int j = 0; j < i; j++) {
                if (numbers[j] == n) unique = false;     //generowanie unikalnych numerów
            }
            if (unique) {
                numbers[i] = n;
            } else i--;
        }

        setTable(tabtemp, "k");
        tabtemp[41] = ',';
//        for (int i = 1; i < 41; i += 2) {
//            cout << tabtemp[i] << tabtemp[i + 1] << " " << endl;
//        }
        //send the numbers
        for (int i = 0; i < 5; i++) {
            if(playersFd[i] != -1){
                write(playersFd[i], tabtemp, 42);
                cout<<tabtemp<<endl;
            }
        }

        random_shuffle(&numbers[0], &numbers[19]); //przemieszanie tablicy
        int pom = numbers[0];
        numbers[0] = numbers[19];
        numbers[19] = pom;   //ostatnia była zawsze ta sama, teraz jest losowa
        //goodAnswers 0-8, bad 9-13, neutral 14-19
        setTable(tabtemp, "a");
        tabtemp[41] = ',';
        for (int i = 0; i < 5; i++) {
            if(playersFd[i] != -1){
                write(playersFd[i], tabtemp, 42);   //wysyłanie klucza
                write(1, tabtemp, 42);
                write(1, "\n", 1);
            }
        }

    } else if (polecenie == 'h') {
        status = 1;

        iloscpodpowiedzi = atoi(temp.substr(1,1).c_str());
        for (int i = 0; i < 5; i++) {
           if(playersFd[i] != -1){
               if (i != currentPlayer) {
                   char tabtemp[21];
                   string zprzecinkiem = temp+",";
                   strcpy(tabtemp, zprzecinkiem.c_str());
                   write(playersFd[i], tabtemp, zprzecinkiem.size());
                   cout<<tabtemp<<endl;
               }
           }
        }
        write(playersFd[currentPlayer], "t,", 2);
    } else if (polecenie == 'o') {
        if (status == 1) {
            int odpowiedz;
            temp = temp.substr(1);
            int size = temp.size()/2;
            for (int j = 0; j < size; j++) {
                odpowiedz = stoi(temp.substr(0, 2));
                temp = temp.substr(2);
                tabodp[odpowiedz]++;
            }
        }
    } else if (polecenie == 'p') {
        if (status == 2) {
            int odpowiedz;
            temp = temp.substr(1);
            int size = temp.size()/2;
            for (int j = 0; j < size; j++) {
                odpowiedz = stoi(temp.substr(0, 2));
                temp = temp.substr(2);
                tabodp[odpowiedz]++;
            }
        }
    } else if (polecenie == 'e') {
        if (status == 1) {
            status = 2;
            temp = podlicz();
            zeruj();
            temp = 'd' + temp +",";
            for (int i = 0; i < 5; i++) {
                if(playersFd[i] != -1){
                    char tabtemp[21];
                    strcpy(tabtemp, temp.c_str());
                    write(playersFd[i], tabtemp, temp.size());
                }
            }
        }
        else if (status == 2) {
            status = 0;
            temp = podlicz();
            zeruj();

            temp = 'w' + temp + ",";
            for (int i = 0; i < 5; i++) {
                if(playersFd[i] != -1){
                    char tabtemp[21];
                    strcpy(tabtemp, temp.c_str());
                    write(playersFd[i], tabtemp, temp.size());
                }
            }


        }
    }
    else if(polecenie == 'f' || polecenie =='i'){
        if(polecenie == 'f')wtgraneRundy++;
        else przegraneRundy++;
        iloscgier++;
        cout<<"-----iloscrunf"<<iloscgier<<endl;
        if(iloscgier == wymaganaIloscGier){
            OdblokujWszystkich();
            //TODO zeruj serwer
            notStarted = true;
        }
        else{
            int main = currentPlayer + 1 ;
            while(playersFd[main] == -1){
                main++;
                if(main > 4){
                    main = 0;
                }
            }
            ustawMainPlayera(main);
            int n;
            bool unique;
            for (int i = 0; i < 20; i++) {
                n = rand() % 24 + 1;
                unique = true;
                for (int j = 0; j < i; j++) {
                    if (numbers[j] == n) unique = false;     //generowanie unikalnych numerów
                }
                if (unique) {
                    numbers[i] = n;
                } else i--;
            }

            char tabtemp[42];
            setTable(tabtemp, "k");
            tabtemp[41] = ',';
            for (int i = 1; i < 41; i += 2) {
                cout << tabtemp[i] << tabtemp[i + 1] << " " << endl;
            }
            //send the numbers
            for (int i = 0; i < 5; i++) {
                if(playersFd[i] != -1){
                    write(playersFd[i], tabtemp, 42);
                }
            }

            random_shuffle(&numbers[0], &numbers[19]); //przemieszanie tablicy
            int pom = numbers[0];
            numbers[0] = numbers[19];
            numbers[19] = pom;   //ostatnia była zawsze ta sama, teraz jest losowa
            //goodAnswers 0-8, bad 9-13, neutral 14-19
            setTable(tabtemp, "a");
            tabtemp[41] = ',';
            for (int i = 0; i < 5; i++) {
                if(playersFd[i] != -1){
                    write(playersFd[i], tabtemp, 42);
                }
            }

        }


    }
}

int main(int argc, char ** argv) {
    if(argc!=3) {
        perror("Czy nie zapomniałeś wskazać adresu i portu?");
        exit(0);
    }
    for(int i =0; i< 5 ;i++){
        playersFd[i] = -1;
    }
    srand(time(NULL));
    struct epoll_event ee, events[10];
    struct sockaddr_in sck_addr, sck_user;
    socklen_t ntmp;
    int userfd;
    int odp;
    int ewait;
    char buffer[40] = "dodano";
    sck_addr.sin_family = AF_INET;
    sck_addr.sin_addr.s_addr = inet_addr(argv[1]);

    sck_addr.sin_port = htons(atoi(argv[2]));
    int serwersock = socket(AF_INET, SOCK_STREAM, 0);
    bind(serwersock, (struct sockaddr*)&sck_addr, sizeof(struct sockaddr));
    listen(serwersock, 10);
    int epollfd = epoll_create1(0);
    ee.events = EPOLLIN;
    ee.data.fd = serwersock;
    epoll_ctl(epollfd,EPOLL_CTL_ADD, serwersock,&ee);
    int q = 0;
    string savedMsgBuffer[5];
    int savedMsgLength[5];
    for(int i=0;i<5;i++) {
        savedMsgLength[i]=0;
        savedMsgBuffer[i]="";
    }
    while(q == 0){
        ewait = epoll_wait(epollfd, events, 10, -1);
        for(int i=0; i < ewait; ++i){
            if(events[i].data.fd == serwersock){
                if(numberPlayer < 5 && notStarted){
                    write(1,"1\n",2);
                    userfd = accept(serwersock, (struct sockaddr*) &sck_user, &ntmp );
                    ee.events = EPOLLIN ;
                    ee.data.fd = userfd;
                    epoll_ctl(epollfd, EPOLL_CTL_ADD, userfd, &ee);
                    int numergracza=0;
                    for(int y = 0; y<5; y++){
                        if(playersFd[y]==-1){
                            numergracza = y;
                            break;
                        }
                    }
                    cout<<"---numergracza"<<numergracza<<" "<<numberPlayer<<endl;
                    playersFd[numergracza]= userfd;
                    numberPlayer++;
                    write(userfd,"z,",2);
                    cout<<"logowanie"<<endl;
                    if(numberPlayer == 1){
                        cout<<"------ustawiamMaina"<<endl;
                        ustawMainPlayera(0);
                    }
                }
                else{
                    userfd = accept(serwersock, (struct sockaddr*) &sck_user, &ntmp );
                    oczekujacy[liczbaOczekujacych] = userfd;
                    write(userfd,"c,",2);
					liczbaOczekujacych++;
                }
            }
            else{
                odp = read(events[i].data.fd,buffer, 40);
                if(odp > 0){
                    temp = buffer;
                    temp = temp.substr(0,odp);
                    string calaWiadomosc = temp;
                    int graczDoObslugi;
//                   write(events[i].data.fd, buffer,odp);
                    for(int j = 0; j < 5; j++){
                        if(playersFd[j] == events[i].data.fd) graczDoObslugi = j;
                    }
                    while(calaWiadomosc.find(",") != string::npos){
                        string nowaWiadosc = "";
                        if(savedMsgLength[graczDoObslugi] > 0){
                            nowaWiadosc = savedMsgBuffer[graczDoObslugi];
                            savedMsgBuffer[graczDoObslugi]="";
                            savedMsgLength[graczDoObslugi]=0;
                        }
                        temp = nowaWiadosc + calaWiadomosc.substr(0,calaWiadomosc.find(',',0));
                        calaWiadomosc = calaWiadomosc.substr(calaWiadomosc.find(',',0) + 1);
                        obsluz(temp[0], events[i].data.fd);


                    }
                    if(calaWiadomosc.length()>0){
                        savedMsgBuffer[graczDoObslugi] = calaWiadomosc;
                        savedMsgLength[graczDoObslugi] = calaWiadomosc.length();
                    }
                }
                if(odp == 0){
                    numberPlayer--;
                    cout<<"usuwam gracza"<<endl;
                    int del;
                    for(int k = 0;k<5 ;k++){
                        if(events[i].data.fd == playersFd[k]){
                            playersFd[k]=-1;
                            del = k;
                        }
                    }
                    if(notStarted == true){
                        for(int n =0; n<5; n++){
                            if(playersFd[n] != -1){
                                string x = "x" + to_string(del)+",";
                                char w[3];
                                strcpy(w, x.c_str());
                                write(playersFd[n],w, 3);
                            }
                        }
                    }
					if(numberPlayer == 0){
					    OdblokujWszystkich();
						notStarted = true;
						for(int p=0;p<5;p++){
						    playersFd[p] = -1;
                            savedMsgLength[p]=0;
                            savedMsgBuffer[p]="";
						}
						currentPlayer = 0;
                        temp = "";
                        status = 0;
                        iloscpodpowiedzi = 0;
                        przegraneRundy=0;
                        wtgraneRundy=0;
//                        int iloscgier = 0;

						//zeroanie serwera
					}

                    epoll_ctl(epollfd, EPOLL_CTL_DEL, events[i].data.fd, NULL);
                    close(events[i].data.fd);
                    if(currentPlayer == del && numberPlayer>1 && notStarted == false){
                        //next Round
                        obsluz('i',0);
                    }
					if(currentPlayer == del && notStarted && numberPlayer>0){
                        for(int e =0; e < 5; e++ ){
                            if(playersFd[e] != -1){
                                ustawMainPlayera(e);
                                break;
                            }
                        }
                    }
					
                    if(numberPlayer == 1 && notStarted == false){

                        for(int q =0;q<5;q++){
                            if(playersFd[q] != -1){
                                write(playersFd[q],"v,",2);
                            }
                        }
//                        write(playersFd[currentPlayer],"v,",2);
//                        notStarted = true;
//                        OdblokujWszystkich();
//                        playersFd[currentPlayer] = -1;
//                        numberPlayer=0;
//                        for(int p=0;p<5;p++){
//                            playersFd[p] = -1;
//                            savedMsgLength[p]=0;
//                            savedMsgBuffer[p]="";
//                        }
//                        currentPlayer = 0;
//                        temp = "";
//                        status = 0;
//                        iloscpodpowiedzi = 0;
//                        przegraneRundy=0;
//                        wtgraneRundy=0;
////                        int iloscgier = 0;
//
//                        epoll_ctl(epollfd, EPOLL_CTL_DEL, events[currentPlayer].data.fd, NULL);
//                        close(events[currentPlayer].data.fd);

                    }
                }
            }
        }
    }

    return 0;
}
