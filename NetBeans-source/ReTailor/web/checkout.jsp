<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="html.HtmlPages"%>
<%@page import="html.pageRender"%>
<%@page import="html.checkoutFuncs"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-latest.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <link href="css/bootstrap.css" rel="stylesheet" media="screen">
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <link rel="stylesheet" type="text/css" href="css/navigation.css">
        <title>ReTailor</title>
        <script type="text/javascript" src="js/javas.js"></script>
        <%
            //inits
            pageRender cc = new pageRender();
            checkoutFuncs cf = new checkoutFuncs();
        %>
    </head>
    <body>


        <!--Top Bar starts-->
        <div id="topBar">
            <%
                out.println(cc.getHeader(session));
            %>
        </div>

        <!--Top Bar ends-->


        <!--Side Bar starts-->

        <div id="sideBar">
            <div id="sideBarText">
                <%
                    out.println(cc.getSideBar());
                %>
            </div>
        </div>

        <!--Side Bar ends--> 


        <!--Main Page starts-->

        <div id="mainPage">
            <div id="mainPageText">
                <%
                    session = request.getSession();

                    Vector<String[]> order = (Vector<String[]>) session.getAttribute("cart_array");
                    String ret = cf.getOrderCode(order, session);
                    out.println(ret);
                %>
            </div>
        </div>

        <%
            out.println(cc.getModal());
        %>


    </body>
    <%
        //<iframe src="SideBar.jsp" height="100%" width="200" style="position:absolute;top:0px;left:0px" frameborder="0"></iframe>
        //<iframe src="TopBar.html" align="top" height="120" style="position:absolute;top:0px;left:0px" width="100%" frameborder="0"></iframe>
        //<iframe src="MainPage.jsp" style="position:absolute;top:120px;left:200px;" name="mainframe" frameborder="0"></iframe>
%>
</html>
