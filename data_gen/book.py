import random
import commands
def main():
    alpha=open("book.dat","r")
    data=alpha.readlines()
    alpha.close()

    catset=["fiction","health","kids","biography","science"]
    set1=["this is a good book on ","this is a masterpiece on ","this book talks about ","there are breathtaking descriptions of "]
    for line in data:
        line=line.split("\t")
        if(len(line)<5):
            continue
        
        out=[]
        out+=[line[0].strip()]
        out+=[line[1].strip()]
        out+=[line[2].strip()]
        out+=[catset[random.randint(0,len(catset)-1)]]
        out+=[random.randint(100,200)]
        out+=[out[4]-random.randint(0,50)]
        out+=[set1[random.randint(0,len(set1)-1)]+out[3]+" by "+out[2]]
        out+=[random.randint(1000,2000)]


        #line[3]=catset[random.randint(0,len(catset)-1)]   # category
        #line[4]=random.randint(100,200)                   # MRP
        #line[5]=line[4]-random.randint(0,50)              # price
        #line[6]=set1[random.randint(0,len(set1)-1)]+line[3]+" by "+line[2]   # description
        #line[7]=random.randint(1000,2000)

        path="/home/piyush/Database-Project/NetBeans-source/Foobar/web/images/book/"+out[3]
        x = commands.getoutput("ls "+path+" -l| wc -l")
        y = int(x) - 1
        out+=["images/book/"+out[3]+"/"+`random.randint(0,y-1)`+".jpeg"]
        out+=[random.randint(1000,100000)]
        for i in out:
            print i,"\t",
        print "\n"

if __name__=="__main__":
    main()