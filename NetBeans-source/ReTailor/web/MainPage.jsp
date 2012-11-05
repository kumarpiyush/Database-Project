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
        String subcat=request.getParameter("id");
        if(cat==null){
            out.println("Fodu3!!!");
        }
        else{
            if(id==null && subcat==null){
                DatabaseConnection cc=new DatabaseConnection();
                ResultSet rs=cc.topByCat(cat, 5);
                int count=0;
                while(rs.next()){
                    count++;
                    String title=rs.getString("title");
                    String author=rs.getString("author");
                    String description=rs.getString("description");
                    int ID=rs.getInt("ID");
                    out.println("<a href=\"MainPage.jsp?cat="+cat+"&id="+ID+"\"><b>"+title+"</b></a>");
                    out.println("<br/>"+description+"<br/>");
                    out.println("<br/>");
                }
            }
            else if(id!=null){
                DatabaseConnection cc=new DatabaseConnection();
                ResultSet rs=cc.itemByID(cat, id);
                if(!rs.next()){
                    out.println("404 Not Found!!<br/>Go to the <a href=\"index.jsp\">home page</a>");
                }
                else{
                    out.println(rs.getString("title")+"<br/>");
                    out.println("by "+rs.getString("author")+"<br/><br/>");
                    out.println(rs.getString("description"));
                    out.println("<br/>");
                    out.println("For Rs. <del><small>"+rs.getInt("mrp")+"</small></del> "+rs.getInt("price")+"<br/>");
                    if(rs.getInt("quantity")>=5){
                        out.println("available-"+rs.getInt("quantity"));
                    }
                    else if(rs.getInt("quantity")>=1){
                        out.println("available-"+rs.getInt("quantity"));
                        out.println("<br/>Order Fast!!!");
                    }
                    else{
                        out.println("Unvailable at present.<br/>");
                    }
                }
            }
            else{
                out.println("Page not made till now :P<br/>");
            }
        }
        
        %>
        <!--div>TODO write content</div-->
        <!--form method="get" id="mainform" action="MainPage.jsp?cat=book">
            <a href="MainPage.jsp?cat=book" onclick="this.submit()">Books</a>
        </form-->
    </body>
</html>
