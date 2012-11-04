<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="html.HtmlPages"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <title>Shopper's Stop</title>
    </head>
    <body>
        <div id="topBar">
            <%
            HtmlPages cc=new HtmlPages();
            out.println(cc.getHeader());
            %>
        </div>
        <div id="sideBar">
            <%
            //HtmlPages cc=new HtmlPages();
            out.println(cc.getSideBar());
            %>
        </div>
    </body>
    <%
    //<iframe src="SideBar.jsp" height="100%" width="200" style="position:absolute;top:0px;left:0px" frameborder="0"></iframe>
    //<iframe src="TopBar.html" align="top" height="120" style="position:absolute;top:0px;left:0px" width="100%" frameborder="0"></iframe>
    //<iframe src="MainPage.jsp" style="position:absolute;top:120px;left:200px;" name="mainframe" frameborder="0"></iframe>
    %>
</html>
