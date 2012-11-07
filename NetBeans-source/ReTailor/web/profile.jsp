<%@page import="java.util.*"%>
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
        <title>ReTailor</title>
        <script type="text/javascript">
            <%
                //inits
                pageRender cc = new pageRender();
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
        </div>detail_img

        <!--Side Bar ends--> 


        <!--Main Page starts-->

        <div id="mainPage">
            <div id="mainPageText">
                <%
                    session = request.getSession();
                    int id=Integer.parseInt(request.getParameter("id"));
                    int sessionId=Integer.parseInt(session.getAttribute("userid").toString());
                    if(sessionId<0 || sessionId!=id){
                        out.println("Trying to break in???");
                    }
                    else{
                        String bill=request.getParameter("bill");
                        if(bill!=null && bill.equals("true")){
                            String specificBill=request.getParameter("specific");
                            out.println(cc.getUserDetails(id,true,specificBill));
                        }
                        else{
                            out.println(cc.getUserDetails(id,false,null));
                        }
                    }
                %>
            </div>
        </div>
    </body>
</html>