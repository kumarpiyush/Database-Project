import random
def main():
    alpha=open("book.dat","r")
    data=alpha.readlines()
    alpha.close()

    catset=["fiction","biography","science","drama","poetry","kids"]
    for line in data:
        line=line.split("\t")
        print line[3]   # category
        print line[4]   # MRP
        print line[5]   # price
        print line[6]   # description
        print line[7]   # quantity

if __name__=="__main__":
    main()

    # :P
