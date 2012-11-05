import random
import commands
rand=random.randint
def main():
    category=["earphone","fan","headphone","mobile","mp3player","refrigerator","television"]

    brand=["Sony","Samsung","iBall","LogiTech","Dell"]
    model=["SNX","SMNG","IB","LTH","DL"]

    basepath="/home/piyush/Database-Project/NetBeans-source/ReTailor/web/images/electronics/"

    description=["High precision ","Latest presentation of ","Lightweight "]
    for i in range(0,2134):
        line=[0]*10
        line[0]=i                                     # ID
        randval=rand(0,len(brand)-1)
        line[1]=model[randval]+`rand(0,10000)`        # model
        line[2]=brand[randval]                        # brand
        line[3]=category[rand(0,len(category)-1)]     # category

        line[4]=rand(500,5000)                        # MRP
        line[5]=line[4]-rand(100,300)                 # price

        line[6]=description[rand(0,len(description)-1)]+line[3]+" by "+line[2]
        line[7]=rand(1000,2000)                       # quantity
        line[9]=rand(1000,10000)                      # popularity

        path=basepath+line[3]+"/"
        x = commands.getoutput("ls "+path+" -l| wc -l")
        y = int(x) - 1
        line[8]="images/electronics/"+line[3]+"/"+`rand(0,y-1)`+".jpeg"

        for i in line:
            print i,"\t",
        print "\n"

if __name__=="__main__":
    main()



"""
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