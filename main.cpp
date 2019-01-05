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

using namespace std;
int playersFd[10];
string playersNicks[10];
int currentPlayer;
int numberPlayer;
string temp;
int numbers[20];


void ustawMainPlayera(int nr){
    currentPlayer = nr;
    write(playersFd[nr],"m",1);
}

void obsluz(char polecenie, int sender){
    if(polecenie == 'l'){
        int current;
        for(int i = 0; i<numberPlayer; i++){
            if(playersFd[i] != sender ){
                char tabtemp[20];
                write(1,"w1",2);
                strcpy(tabtemp, temp.c_str());
                write(playersFd[i], tabtemp, temp.size());
                write(1, tabtemp, temp.size());
                write(1,"\n",1);
            }
            else{
                current = i;
                playersNicks[i] = temp;
            }
        }
        for(int i=0; i<current; i++){
            char tabtemp[20];
            write(1,"w2",2);
            strcpy(tabtemp, playersNicks[i].c_str());

            if( i == currentPlayer){
                tabtemp[0] = 's';
            }

            write(sender, tabtemp, playersNicks[i].size());
            write(1, tabtemp, playersNicks[i].size());
            write(1,"\n",1);
        }
    }
    else if (polecenie == 'r') { //rozpoczęcie gry
        for(int i =0; i< numberPlayer;i++){
            write(playersFd[i],"r",1);
        }
        int n;
        bool unique;
        for (int i = 0; i < 20; i++) {
            n = rand() % 24  + 1;
            unique=true;
            for (int j = 0; j < i; j++) {
                if (numbers[j] == n) unique=false;     //generowanie unikalnych numerów
            }
            if(unique) {
                numbers[i] = n;
            }
            else i--;
            }

        char tabtemp[41];
        tabtemp[0] = 'k';
        string number;
        for (int i = 0; i < 20; i++) {
        if (numbers[i] < 10) {
            number = '0' + to_string(numbers[i]);
        }
        else
            {number = to_string(numbers[i]);}
        strcpy(tabtemp + (1 + 2 * i), number.c_str());
        }

        for(int i=1; i<41;i+=2) {
            cout << tabtemp[i] << tabtemp[i+1] << " " << endl;
        }
                                                //send the numbers
        for(int i=0;i<numberPlayer;i++){
            write(playersFd[i], tabtemp, 41);
        }

        random_shuffle(&numbers[0], &numbers[19]); //przemieszanie tablicy
        //goodAnswers 0-8, bad 9-13, neutral 14-19
        tabtemp[0] = 'a';
        for (int i = 0; i < 20; i++) {
            if (numbers[i] < 10) {
                number = '0' + to_string(numbers[i]);
            }
            else
            {number = to_string(numbers[i]);}
            strcpy(tabtemp + (1 + 2 * i), number.c_str());
        }
        for(int i=0;i<numberPlayer;i++) {
            write(playersFd[i], tabtemp, 41);   //wysyłanie klucza
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
                odp = read(events[i].data.fd,buffer, 20);
                temp = buffer;
                temp = temp.substr(0,odp);
//                write(events[i].data.fd, buffer,odp);
                obsluz(buffer[0], events[i].data.fd);
                write(1,buffer,odp);
                write(1,"\n",1);
            }
        }
    }

    return 0;
}