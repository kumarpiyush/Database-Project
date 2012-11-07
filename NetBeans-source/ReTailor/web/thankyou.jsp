<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="html.HtmlPages"%>
<%@page import="html.pageRender"%>
<%@page import="html.checkoutFuncs"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <link rel="stylesheet" type="text/css" href="css/navigation.css">
        <title>ReTailor</title>
        <script type="text/javascript">
            <%
                //inits
                pageRender cc=new pageRender();
                checkoutFuncs cf=new checkoutFuncs();
            %>
        </script>
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
                session=request.getSession();
                String billid=(String)session.getAttribute("tmp_billid");
                session.setAttribute("tmp_billid", null);
                String ret=cf.getThankYou(billid);
                out.println(ret);
                
                %>
            </div>
        </div>
        
        
    </body>
</html>
