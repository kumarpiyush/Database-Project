<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="html.HtmlPages"%>
<%@page import="html.pageRender"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <link rel="stylesheet" type="text/css" href="css/navigation.css">
        <title>Shopper's Stop</title>
        <script type="text/javascript">
            <%
                //inits
                pageRender cc=new pageRender();
            %>
        </script>
    </head>
    <body>
        <div id="mainPage">
            <div id="mainPageText">
                <%
                session=request.getSession();
                String cat=request.getParameter("cat");
                String id=request.getParameter("id");
                String subcat=request.getParameter("subcat");
                out.println(cc.getMainPage(cat,id,subcat));
                %>
            </div>
        </div>
        <div id="sideBar">
            <div id="sideBarText">
                <%
                out.println(cc.getSideBar());
                %>
            </div>
        </div>
        <div id="topBar">
            <%
            out.println(cc.getHeader(session));
            %>
        </div>
        
    </body>
    <%
    //<iframe src="SideBar.jsp" height="100%" width="200" style="position:absolute;top:0px;left:0px" frameborder="0"></iframe>
    //<iframe src="TopBar.html" align="top" height="120" style="position:absolute;top:0px;left:0px" width="100%" frameborder="0"></iframe>
    //<iframe src="MainPage.jsp" style="position:absolute;top:120px;left:200px;" name="mainframe" frameborder="0"></iframe>
    %>
</html>
