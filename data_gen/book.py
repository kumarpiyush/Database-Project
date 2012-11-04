import random
def main():
    alpha=open("book.dat","r")
    data=alpha.readlines()
    alpha.close()

    catset=["fiction","biography","science","drama","poetry","kids"]
    set1=["this is a good book on ","this is a masterpiece on ","this book talks about ","there are breathtaking descriptions of "]
    for line in data:
        line=line.split("\t")
        line[3]=catset[random.randint(0,len(catset)-1)]   # category
        line[4]=random.randint(100,200)                   # MRP
        line[5]=line[4]-random.randint(0,50)              # price
        line[6]=set1[random.randint(0,len(set1)-1)]+line[3]+" by "+line[2]   # description
        line[7]=random.randint(1000,2000)
        line[8]=0   # book link
        print line

if __name__=="__main__":
    main()
#:P
