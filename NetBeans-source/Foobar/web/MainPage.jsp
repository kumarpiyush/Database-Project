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
        <div>
            <%
            String cat=request.getParameter("cat");
            String id=request.getParameter("id");
            System.err.println(cat+" "+id);
            if(cat!="" && cat!=null && id!="" && id!=null){
                out.println("Fodu1!!!");
            }
            else if(cat!="" && cat!=null){
                out.println("Fodu2!!!");
            }
            else{
                out.println("Fodu3!!!");
            }
            %>
        </div>
        <!--div>TODO write content</div-->
        <!--form method="get" id="mainform" action="MainPage.jsp?cat=book">
            <a href="MainPage.jsp?cat=book" onclick="this.submit()">Books</a>
        </form-->
    </body>
</html>
