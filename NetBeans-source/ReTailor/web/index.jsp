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
                String cat=request.getParameter("cat");
                String id=request.getParameter("id");
                String subcat=request.getParameter("subcat");
                String searchQuery=request.getParameter("mainSearch");
                String table=request.getParameter("table");
                out.println(cc.getMainPage(cat,id,subcat,searchQuery,table));

                session=request.getSession();
                    String sortby = "";
                    if(request.getParameter("id")==null && request.getParameter("cat")!=null){
                        String url = "";
                        url+="index.jsp?cat=";
                        url+=request.getParameter("cat");
                        if(request.getParameter("subcat")!=null){
                            url+="&subcat";
                            url+=request.getParameter("subcat");
                        }
                        String current_option;
                        if(request.getParameter("sort")==null) current_option = "";
                        else current_option = request.getParameter("sort").toString();
                        boolean is_sorted = true;
                        if(current_option=="") is_sorted = false;
                        sortby += "<div  class=\"sortSelect\" ><table><tr><td>Sort :</td>\n\t<td><select name=\"Sort By\" onchange=\"location = this.options[this.selectedIndex].value;\">\n\t";
                        sortby+="\t\t\t\t<option ";
                        if(is_sorted && current_option.equals("1")) sortby+=" selected";
                        sortby += " value=";
                        String url1 = url+"&sort=1";
                        sortby = sortby + "\""+ url1 + "\"";
                        sortby += ">Increasing Price</option>\n\t\t\t\t\t<option";
                        if(is_sorted && current_option.equals("2")) sortby+=" selected";
                        sortby += " value=";
                        String url2 = url+"&sort=2";
                        sortby = sortby + "\""+ url2 + "\"";
                        sortby += ">Decreasing Price</option>\n\t\t\t\t\t<option";
                        if(!is_sorted || (is_sorted && current_option.equals("3"))) sortby+=" selected";
                        sortby += " value=";
                        String url3 = url+"&sort=3";
                        sortby = sortby + "\""+ url3 + "\"";
                        sortby += ">Decreasing Popularity</option>\n\t\t\t\t\t<option";
                        if(is_sorted && current_option.equals("4")) sortby+=" selected";
                        sortby += " value=";
                        String url4 = url+"&sort=4";
                        sortby = sortby + "\""+ url4 + "\"";
                        sortby += ">Increasing Popularity</option>\n";
                        sortby += "\t\t\t\t</select></td></tr></table></div>";
                        out.println(sortby);
                        if(current_option=="") current_option = "OMG!!!!!!";
                        System.out.println("current_option = "+current_option);
                        
                    }
                    
                    

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
