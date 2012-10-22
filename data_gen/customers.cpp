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
    FILE *alpha,*beta;
    alpha=fopen("random_names","r");
    beta=fopen("customer.dat","w");
    string name;
    char first[100],last[100];
    int count=0;

    // getting name
    while(fscanf(alpha,"%s%s",first,last)!=EOF){
        name=string(first);
        name+=" ";
        name+=string(last);
#ifdef db
        cout<<name<<endl;
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
        cout<<email<<endl;
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
        cout<<endl;
#endif
    }
    return 0;
}
