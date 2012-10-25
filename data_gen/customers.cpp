/*
    file to create tab sepearted records for the customer table. 
    Names and addresses are are picked up from the file, and other details are randomly generated
*/

#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <cmath>
#include <algorithm>
#include <vector>
#include <list>
#include <queue>
#include <stack>
#include <map>
#include <set>
#include <utility>
#include <climits>
#include <cfloat>
#include <cassert>
#define readint(n)      scanf("%d",&n)
#define readull(n)      scanf("%llu",&n)
#define readll(n)       scanf("%lld",&n)
#define readf(n)        scanf("%f",&n)
#define readd(n)        scanf("%lf",&n)
#define init(mem)       memset(mem,0,sizeof(mem))
#define ll              long long int
#define ull             unsigned long long int
using namespace std;
#define db

// lower a string
void lowercase(string* str){
    if(str==NULL){
        cerr<<"Null sting given\n";
        return;
    }
    for(int i=0;i<str->length();i++){
        if((*str)[i]>='A' and (*str)[i]<='Z'){
            (*str)[i]=(*str)[i]-'A'+'a';
        }
    }
}

int main(){
    srand(time(0));
    FILE *alpha,*beta,*gamma;
    alpha=fopen("random_names","r");    // to read names
    gamma=fopen("random_address","r");  // random addresses
    string addresses[501];
    char addr_tmp[200];
    int cnt=0;

    // getting addresses
    while(fgets(addr_tmp,1000,gamma)!=NULL){
        addr_tmp[strlen(addr_tmp)-1]='\0';
        addresses[cnt++]=string(addr_tmp);
    }

    string name;
    char first[100],last[100];
    int index=1;

    // getting name
    while(fscanf(alpha,"%s%s",first,last)!=EOF){
        name=string(first);
        name+=" ";
        name+=string(last);
#ifdef db
        cout<<index<<"\t"<<name<<"\t";
#endif

        // 
        int mail_no=rand()%3;
        string email;
        switch (mail_no){
            case 0:
                email=string(first)+string(last)+"@gmail.com";
                break;
            case 1:
                email=string(last)+string(first)+"@gmail.com";
                break;
            case 2:
                int suff=1+rand()%137;
                char suffstr[5];
                sprintf(suffstr,"%d\0",suff);
                email=string(first)+string(last)+string(suffstr)+"@gmail.com";
        }
        lowercase(&email);
#ifdef db
        cout<<email<<"\t";
#endif
        // phone number
        int phNo[10];
        phNo[0]=1+rand()%9;
        for(int i=1;i<10;i++){
            phNo[i]=rand()%10;
        }
#ifdef db
        for(int i=0;i<10;i++){
            cout<<phNo[i];
        }
        cout<<"\t";
#endif

        // addresses
#ifdef db
        cout<<addresses[index]<<"\t";
#endif
        // accnt number
        int accnt[20];
        accnt[0]=1+rand()%9;
        for(int i=1;i<20;i++){
            accnt[i]=rand()%10;
        }
#ifdef db
        for(int i=0;i<20;i++){
            cout<<accnt[i];
        }
        cout<<"\n";
        index++;
#endif
    }
    return 0;
}