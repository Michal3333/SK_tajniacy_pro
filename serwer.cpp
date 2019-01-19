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
int wymaganaIloscGier = 2; //TODO ustawaic od graczy
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

        for (int i = 0; i < 5; i++) {
            if(playersFd[i]!=-1){
                if (playersFd[i] != sender) {
                    char tabtemp[21];
                    temp = temp + ",";
                    strcpy(tabtemp, temp.c_str());
                    write(playersFd[i], tabtemp, temp.size());
                    write(1, tabtemp, temp.size());
                    write(1, "\n", 1);
                } else {
                    current = i;
                    playersNicks[i] = temp;
                }
            }
        }
        for (int i = 0; i < current; i++) {
            char tabtemp[21];
            string nickname = playersNicks[i] + ",";
            strcpy(tabtemp, nickname.c_str());

            if (i == currentPlayer) {
                tabtemp[0] = 's';
            }

            write(sender, tabtemp, nickname.size());
            write(1, tabtemp, nickname.size());
            write(1, "\n", 1);
        }
    } else if (polecenie == 'r') { //rozpoczęcie gry
//        for (int i = 0; i < numberPlayer; i++) {
//            write(playersFd[i], "r", 1);
//        }
        notStarted = false;
        int n;
        bool unique;
        char tabtemp[42];
        string wiad;
        if(numberPlayer < 5) wiad="i0"+ to_string(2*numberPlayer) + ",";
        else wiad="i10,"; //bo max=5, więc 2x5=10 quickmaths
        strcpy(tabtemp, wiad.c_str());

        for (int i = 0; i < 5; i++) {
            if (playersFd[i] != -1) {
                write(playersFd[i], tabtemp, 4);   //wysyłanie info o ilosci rund do wszystkich
            }
        }
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
                   temp = temp + ",";
                   strcpy(tabtemp, temp.c_str());
                   write(playersFd[i], tabtemp, temp.size());
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

int main() {
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
    sck_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    sck_addr.sin_port = htons(1234);
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
                    playersFd[numberPlayer]= userfd;
                    numberPlayer++;
                    write(userfd,"z,",2);
                    cout<<"logowanie";
                    if(numberPlayer == 1){
                        ustawMainPlayera(0);
                    }
                }
                else{
                    userfd = accept(serwersock, (struct sockaddr*) &sck_user, &ntmp );
                    oczekujacy[liczbaOczekujacych] = userfd;
                    write(userfd,"c,",2);
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
					if(numberPlayer == 0){
						notStarted = true;
						//zeroanie serwera
					}
                    if(currentPlayer == del){
                        //next Round
                    }
                    epoll_ctl(epollfd, EPOLL_CTL_DEL, events[i].data.fd, NULL);
                    close(events[i].data.fd);
                }
            }
        }
    }

    return 0;
}
