<%@page import="java.sql.ResultSet"%>
<%@page import="database.DatabaseConnection"%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/SideBar.css" />
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div id="topLevel">
            <% 
            DatabaseConnection cc=new DatabaseConnection();
            ResultSet rs=cc.listofcategories();
            while(rs.next()){
                char name[]=rs.getString(1).toCharArray();
                boolean flag=true;
                for(int i=0;i<name.length;i++){
                    if(flag){
                        flag=false;
                        name[i]=(char)(name[i]-32);
                    }
                    else{
                        if(name[i]=='_'){
                            name[i]=' ';
                            flag=true;
                        }
                    }
                }
                out.println("<a href = \"MainPage.jsp?cat="+rs.getString(1)+"\" target = \"mainframe\" ><b>");
                out.println(name);
                out.println("</b></a></br></br>"); 
            }
            %>
	</div>
    </body>
</html>
