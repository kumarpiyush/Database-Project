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
                //cc.category_to_table(rs.getString(1));
                //char name[]=cc.CATEGORY.toCharArray();
                out.println("<a href = \"MainPage.jsp?cat="+rs.getString(1)+"\" target = \"mainframe\" ><b>");
                out.println(rs.getString(1));
                out.println("</b></a></br></br>"); 
            }
            %>
	</div>
    </body>
</html>
