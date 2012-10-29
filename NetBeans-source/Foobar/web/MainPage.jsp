<%@page import="java.sql.ResultSet"%>
<%@page import="database.DatabaseConnection"%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%
        String cat=request.getParameter("cat");
        String id=request.getParameter("id");
        System.err.println(cat+" "+id);
        if(cat!="" && cat!=null && id!="" && id!=null){

        }
        else if(cat!="" && cat!=null){
            DatabaseConnection cc=new DatabaseConnection();
            ResultSet rs=cc.topByCat(cat, 5);
            while(rs.next()){
                //System.err.println(rs.getString("title"));
                String title=rs.getString("title");
                String author=rs.getString("author");
                String description=rs.getString("description");
                int ID=rs.getInt("ID");
                out.println("<a href=\"MainPage.jsp?cat="+cat+"&id="+ID+"\"><b>"+title+"</b></a>");
                out.println("<br/>"+description+"<br/>");
                out.println("<br/>");
            }
        }
        else{
            out.println("Fodu3!!!");
        }
        %>
        <!--div>TODO write content</div-->
        <!--form method="get" id="mainform" action="MainPage.jsp?cat=book">
            <a href="MainPage.jsp?cat=book" onclick="this.submit()">Books</a>
        </form-->
    </body>
</html>
