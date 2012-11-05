import random
import commands
rand=random.randint
def main():
    category=['M','W','K']
    convertor={'M':"men",'W':"women",'K':"kids"}

    catM=["footwear","jeans","shirt"]
    catW=["footwear","jeans","saree"]
    catK=["footwear","trousers","tshirt"]
    sizes=["S","M","L","XL","XXL","Umang"]
    companies=["Nike","Levis","Calvin Klein","Jilly and Johny","Lee"]
    description=["Fine product by ","Compelling fabric quality, very nice creation by ","The best of "]


    for i in range(0,1000):
        line=[0]*10
        line[0]=i                                               # ID
        line[1]=category[rand(0,2)]                   # category

        # now set line[2] and line[8]
        if(line[1]=='M'):
            line[2]=catM[rand(0,len(catM)-1)]
            path="/home/piyush/Database-Project/NetBeans-source/ReTailor/web/images/clothing/M/"+line[2]
            x = commands.getoutput("ls "+path+" -l| wc -l")
            y = int(x) - 1
            line[8]="images/clothing/"+line[1]+"/"+line[2]+"/"+`rand(0,y-1)`+".jpeg"

        elif(line[1]=='W'):
            line[2]=catW[rand(0,len(catW)-1)]
            path="/home/piyush/Database-Project/NetBeans-source/ReTailor/web/images/clothing/W/"+line[2]
            x = commands.getoutput("ls "+path+" -l| wc -l")
            y = int(x) - 1
            line[8]="images/clothing/"+line[1]+"/"+line[2]+"/"+`rand(0,y-1)`+".jpeg"

        else:
            line[2]=catK[rand(0,len(catK)-1)]
            path="/home/piyush/Database-Project/NetBeans-source/ReTailor/web/images/clothing/K/"+line[2]
            x = commands.getoutput("ls "+path+" -l| wc -l")
            y = int(x) - 1
            line[8]="images/clothing/"+line[1]+"/"+line[2]+"/"+`rand(0,y-1)`+".jpeg"

        line[3]=sizes[rand(0,len(sizes)-1)]             # size
        line[4]=rand(500,5000)                        # MRP
        line[5]=line[4]-rand(100,300)                 # price
        
        line[6]=description[rand(0,len(description)-1)]+companies[rand(0,len(companies)-1)]

        line[7]=rand(1000,2000)                       # quantity
        line[9]=rand(1000,10000)
        """
        path="/home/piyush/Database-Project/NetBeans-source/Foobar/web/images/book/"+line[3]
        x = commands.getoutput("ls "+path+" -l| wc -l")
        y = int(x) - 1
        line+=["images/book/"+line[3]+"/"+`rand(0,y-1)`+".jpeg"]
        """
        for i in line:
            print i,"\t",
        print "\n"

if __name__=="__main__":
    main()
