<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="html.HtmlPages"%>
<%@page import="html.pageRender"%>
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
        </div>

        <!--Side Bar ends--> 


        <!--Main Page starts-->

        <div id="mainPage">
            <div id="mainPageText">
                <%
                    session = request.getSession();

                    int sortOption = 0;
                    int offset = 0;
                    Map<String, String[]> mm = request.getParameterMap();
                    String cat = request.getParameter("cat");
                    String id = request.getParameter("id");
                    String subcat = request.getParameter("subcat");
                    String searchQuery = request.getParameter("mainSearch");
                    String table = request.getParameter("table");

                    if (request.getParameter("offset") != null) {
                        offset = Integer.parseInt(request.getParameter("offset"));
                    }

                    if (request.getParameter("sort") != null) {
                        sortOption = Integer.parseInt(request.getParameter("sort"));
                    }

                    if ((cat != null || searchQuery != null) && id == null) {
                        out.println(cc.getSortBy(mm));
                    }

                    out.println(cc.getMainPage(cat, id, subcat, searchQuery, table, sortOption, offset));

                    if ((cat != null || searchQuery != null) && id == null) {
                        out.println(cc.getPrevNextLinks(mm));
                    }
                %>
                
            </div>
        </div>
                <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="myModalLabel">Log in</h3>
                    </div>
                    <div class="modal-body">
                        <%  String modal = "";
                        
                        /*<div class="container" style="margin-top: 100px; width: 450px; padding: 30px 30px 30px 30px;  border:1px solid #021a40;">
            <form class="form-signin"  action="HtmlPages" name="login_form" method="post" onsubmit="HtmlPages">
                <h2 class="form-signin-heading">Please sign up</h2>
                <input name="name" class="input-block-level" type="text" placeholder="Name">
                <input name="email" class="input-block-level" type="email" placeholder="Email address" >
                <input name="number" class="input-block-level" type="number" placeholder="Phone Number">
                <input name="address" class="input-block-level" type="text" placeholder="Address">
                <input class="input-block-level" type="password" placeholder="Password">
                <input class="input-block-level" type="repassword" placeholder="Confirm Password">
                <div><p></p></div>
                <button class="btn btn-large btn-info" type="submit">Sign Up</button>
            </form>
        </div>
 * 
 * */
                            modal += "\n<div class=\"container\" style=\"margin-top: 100px; width: 450px; padding: 30px 30px 30px 30px;  border:1px solid #021a40;\">";
                            modal += "\n<form class=\"form-signin\"  action=\"HtmlPages\" name=\"login_form\" method=\"post\" onsubmit=\"HtmlPages\">";
                            modal += "\n<input name=\"username\" class=\"input-block-level\" type=\"email\" placeholder=\"Email address\" >";
                            modal += "\n<input name=\"password\" class=\"input-block-level\" type=\"password\" placeholder=\"Password\">";
                            modal += "\n<div><p></p></div>";
                            modal += "\n<button class=\"btn btn-large btn-info\" type=\"submit\" value=\"Login\">Log In</button>";
                            modal += "\n</form></div>\n\n";
                            
                            out.println(modal);
                        %>
                    </div>
                </div>

                </body>
                <%
                    //<iframe src="SideBar.jsp" height="100%" width="200" style="position:absolute;top:0px;left:0px" frameborder="0"></iframe>
                    //<iframe src="TopBar.html" align="top" height="120" style="position:absolute;top:0px;left:0px" width="100%" frameborder="0"></iframe>
                    //<iframe src="MainPage.jsp" style="position:absolute;top:120px;left:200px;" name="mainframe" frameborder="0"></iframe>
%>
                </html>
