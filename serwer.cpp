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
string playersNicks[10];
int currentPlayer;
int numberPlayer;
string temp;
int numbers[20];
int status = 0;
int tabodp[20];
int iloscpodpowiedzi;
int player;

int ktoryGracz(int fd){ //returns logical index of player with diven descriptor
    for(int i =0; i < numberPlayer; i++)
    {
        if(fd==playersFd[i]) return i;
    }
    printf("zla ta funkcja chyba w takim razie");
}

void ustawMainPlayera(int nr){
    currentPlayer = nr;
    write(playersFd[nr],"m,",2);
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
        for (int i = 0; i < numberPlayer; i++) {
            if (playersFd[i] != sender) {
                char tabtemp[20];
                write(1, "w1", 2);
                temp=temp+','; // PRZECINEK
                strcpy(tabtemp, temp.c_str());
                write(playersFd[i], tabtemp, temp.size());
                write(1, tabtemp, temp.size());
                write(1, "\n", 1);
            } else {
                current = i;
                playersNicks[i] = temp;
            }
        }
        for (int i = 0; i < current; i++) {
            char tabtemp[20];
            write(1, "w2", 2);
            string nickname = playersNicks[i]+',';// PRZECINEK
            strcpy(tabtemp, nickname.c_str());

            if (i == currentPlayer) {
                tabtemp[0] = 's';
            }

            write(sender, tabtemp, playersNicks[i].size());
            write(1, tabtemp, playersNicks[i].size());
            write(1, "\n", 1);
        }
    } else if (polecenie == 'r') { //rozpoczęcie gry
//        for (int i = 0; i < numberPlayer; i++) {
//            write(playersFd[i], "r", 1);
//        }
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
        tabtemp[41]=',';        // PRZECINEK
        for (int i = 1; i < 41; i += 2) {
            cout << tabtemp[i] << tabtemp[i + 1] << " " << endl;
        }
        //send the numbers
        for (int i = 0; i < numberPlayer; i++) {
            write(playersFd[i], tabtemp, 42);
        }

        random_shuffle(&numbers[0], &numbers[19]); //przemieszanie tablicy
        int pom = numbers[0];
        numbers[0] = numbers[19];
        numbers[19] = pom;   //ostatnia była zawsze ta sama, teraz jest losowa
        //goodAnswers 0-8, bad 9-13, neutral 14-19
        setTable(tabtemp, "a");
        tabtemp[41]=',';        // PRZECINEK
        for (int i = 0; i < numberPlayer; i++) {
            write(playersFd[i], tabtemp, 42);   //wysyłanie klucza
        }

    } else if (polecenie == 'h') {
        status = 1;

        iloscpodpowiedzi = atoi(temp.substr(1,1).c_str());
        for (int i = 0; i < numberPlayer; i++) {
            if (i != currentPlayer) {
                char tabtemp[20];
                strcpy(tabtemp, temp.c_str());
                write(playersFd[i], tabtemp, temp.size());
            }
        }
        write(playersFd[currentPlayer], "t,", 2);  // PRZECINEK

    } else if (polecenie == 'o') {
        if (status == 1) {
            int odpowiedz;
            temp = temp.substr(1, temp.size());
            for (int j = 0; j < temp.size()+1 / 2; j++) {
                odpowiedz = stoi(temp.substr(0, 2));
                temp = temp.substr(2, temp.size());
                tabodp[odpowiedz]++;
            }
        }
    } else if (polecenie == 'p') {
        if (status == 2) {
            int odpowiedz;
            temp = temp.substr(1, temp.size());
            for (int j = 0; j < temp.size() / 2; j++) {
                odpowiedz = stoi(temp.substr(0, 2));
                temp = temp.substr(2, temp.size());
                tabodp[odpowiedz]++;
            }
        }
    } else if (polecenie == 'e') {
        if (status == 1) {
            status = 2;
            temp = podlicz();
            zeruj();
            temp = 'd' + temp + ',';  // PRZECINEK
            for (int i = 0; i < numberPlayer; i++) {
                char tabtemp[21];
                strcpy(tabtemp, temp.c_str());
                write(playersFd[i], tabtemp, temp.size());
            }
        }
        else if (status == 2) {
            status = 0;
            temp = podlicz();
            zeruj();
            temp = 'w' + temp + ','; // PRZECINEK
            for (int i = 0; i < numberPlayer; i++) {
                char tabtemp[20];
                strcpy(tabtemp, temp.c_str());
                write(playersFd[i], tabtemp, temp.size());
            }
        }
    }
}

int main() {
    srand(time(NULL));
    struct epoll_event ee, events[10];
    struct sockaddr_in sck_addr, sck_user;
    socklen_t ntmp;
    int userfd;
    int odp;
    int ewait;
    char savedMsgBuffer[5][40];
    int savedMsgLength[5];
    for(int i=0;i<5;i++) savedMsgLength[i]=0;
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
    write(1,"z\n",2);
    while(q == 0){
        write(1,"l",1);
        ewait = epoll_wait(epollfd, events, 10, -1);
        for(int i=0; i < ewait; ++i){
            if(events[i].data.fd == serwersock){
                write(1,"1\n",2);
                userfd = accept(serwersock, (struct sockaddr*) &sck_user, &ntmp );
                ee.events = EPOLLIN ;
                ee.data.fd = userfd;
                epoll_ctl(epollfd, EPOLL_CTL_ADD, userfd, &ee);
                playersFd[numberPlayer]= userfd;
                numberPlayer++;

                if(numberPlayer == 1){
                    write(1,"---",3);
                    ustawMainPlayera(0);
                }

            }
            else{
                write(1,"r",1);
                odp = read(events[i].data.fd,buffer, 40);
                temp = buffer;
                temp = temp.substr(0,odp);

                if(temp[odp-1]==',') //jeśli cała wiadomość została już dosłana
                {
                    write(1,"cała wiad\n", 10);
                if(savedMsgLength==0) { // i jest to cała wiadomość
                    temp=temp.substr(0,odp-1); //bez przecinka
                    obsluz(buffer[0], events[i].data.fd);
                    write(1, buffer, odp);
                    write(1, "\n", 1);
                }
                else   {
                    temp=temp.substr(0,odp-1); //bez przecinka
                    player = ktoryGracz(events[i].data.fd); //który gracz? (0-4) (mamy fd, nie mamy ich "uszeregowanych")

                    strcpy(savedMsgBuffer[player] + savedMsgLength[player], temp.c_str()); //dopisujemy ogon

                    savedMsgLength[player]+=odp-1; //bez przecinka!
                    temp=savedMsgBuffer[player]; //wkładamy całą wiadomość do temp
                    temp=temp.substr(0, savedMsgLength[player]); //i bierzemy tylko tę, która nas interesuje
                    savedMsgLength[player]=0; //i nic już nie pamiętamy!
                    obsluz(temp[0], events[i].data.fd);

                }
                }
//                write(events[i].data.fd, buffer,odp);
                else{       //jeśli tylko czesc

                strcpy(savedMsgBuffer[player] + savedMsgLength[player], temp.c_str()); //dopisujemy
                savedMsgLength[player]+=odp; //tyle teraz mamy
                /* mam wrażenie że tu coś jeszcze powinno być, ale na ten moment nie wpadło mi do głowy */
                }
            }
        }
    }

    return 0;
}
