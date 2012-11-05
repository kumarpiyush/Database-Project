/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package html;

import database.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sanket
 */
@WebServlet(name = "HtmlPages", urlPatterns = {"/HtmlPages"})
public class HtmlPages extends HttpServlet {
    
    DatabaseConnection cc=new DatabaseConnection();
    int noOfProducts=9;
    int offset=0;
    
    private String getSiteBrand() {
        String page="<div id=\"siteBrand\">";
        page+="<img src=\"images/retailor-logo.png\"/>";
        page+="</div>";
        return page;
    }

    private String getLoginOut() {
        return "";
    }

    private String getSearch() throws SQLException {
        String page="";
        page+="<div id=\"search\">";
        page+="<form >";
        page+="Search:";
        page+="<input type=\"text\" name=\"mainSearch\">";
        page+="<select>";
        ResultSet rs=cc.listofcategories();
        while(rs.next()){
            page+="<option value=\""+rs.getString(1)+"\">"+rs.getString(1)+"</option>";
        }
        page+="</select>";
        page+="</form>";
        page+="</div>";
        return page;
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HtmlPages</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HtmlPages at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
    
    public String getSubCatsDropDown(String cat) throws SQLException{
        String page = "<ul>\n";
        ResultSet rs = cc.listofsubcats(cat);
        while(rs.next()){
            page+="<li><a href = \"index.jsp?cat="+cat+"&subcat=";
            page+= rs.getString(1)+"\">"+rs.getString(1)+"</a></li>\n";
        }
        page+="</ul>\n";
        return page;
    }
    
    public String getHeader() throws SQLException{
        String page="";
        page+="<div id=\"topLeft\">"+getSiteBrand()+"</div>";
        page +="\n"+"<div id=\"topRight\">"+"\n"+getLoginOut();
        page+="\n"+ getSearch()+"</div>"+"\n";
        
        return page;
    }
    
    public String getSideBar() throws SQLException{
        String page="";
        //page="<div id=\"topLevel\">";
        ResultSet rs=cc.listofcategories();
        page+="<ul class=\"nav\">\n";
        while(rs.next()){
            page+="<li class=\"dropdown\" ><a href = \"index.jsp?cat="+rs.getString(1)+"\" >";
            page+=rs.getString(1);
            page+="</a>";
            page+=getSubCatsDropDown(rs.getString(1));
            page+="</li>\n";
        }
        page+="</ul>\n";
        //page+="<ul class=\"nav\">\n<li><a href=\"#\">Home</a></li>\n<li class=\"dropdown\">\n<a href=\"#\">Work</a>\n<ul><li><a href=\"#\">Sublink</a></li><li><a href=\"#\">Sublink</a></li></ul></li><li><a href=\"#\">Portofolio</a></li><li><a href=\"#\">About</a></li><li><a href=\"#\">Contact</a></li></ul>";
        return page;
    }
    
    public String getElem(String category,String id,String arg1,String arg2,String mrp, String price, String img_url){
        String page="";
        page+="<div id=\"entry\">";
        page+="<a href=\"index.jsp?cat="+category+"&id="+id+"\">";
        page+="<img class=\"product_img\" src=\""+img_url+"\" alt=\"Image Missing\" /></a><br/>";
        page+="<a href=\"index.jsp?cat="+category+"&id="+id+"\">";
        page+=arg1;
        page+="</a>";
        page+="<br/>";
        page+=arg2;
        page+="<br/>";
        page+="<strike>";
        page+=mrp;
        page+="</strike>&nbsp;&nbsp;";
        page+=price;
        page+="</div>";
        return page;
    }
    
    public String organiseResult(ResultSet rs, String cat) throws SQLException{
        String page="";
        if("Books".equals(cat)){
            while(rs.next()){
                page+=getElem(cat, rs.getString(1), rs.getString("title"), rs.getString("author"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page+="\n";
            }
        }
        else if("Clothing".equals(cat)){
            while(rs.next()){
                page+=getElem(cat, rs.getString(1), rs.getString("category"), rs.getString("category2"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page+="\n";
            }
        }
        else if("Electronics".equals(cat)){
            while(rs.next()){
                page+=getElem(cat, rs.getString(1), rs.getString("model"), rs.getString("category"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page+="\n";
            }
        }
        page+="\n";
        return page;
    }
    
    public String getMainPage(String cat,String id,String subcat) throws SQLException{
        String page="";
        page+="<div id=\"collectedEntry\">\n";
        if(cat==null){
            ResultSet rs=cc.listofcategories();
            while(rs.next()){
                ResultSet rs2=cc.topByCat(rs.getString(1),3);

                page+=organiseResult(rs2, rs.getString(1));
                page+="\n";
            }
        }
        else{
            if(id==null && subcat==null){
                ResultSet rs2=cc.listofproducts(cat,noOfProducts,offset);
                page+=organiseResult(rs2, cat);
                page+="\n";
            }
            else if(id!=null){
                ResultSet rs2=cc.itemByID(cat, id);
                while(rs2.next()){
                    page+="<table class=\"product_detail\">\n";
                    page+="<tr>";
                    page+="<td><img class=\"detail_img\" src=\""+rs2.getString("img_url")+"\"/></td>\n";
                    page+="<td>";
                    page+="<div class = \"product_description\" id=\"entry\"><p>";
                    page+=rs2.getString(2);
                    page+="</p>\n<p>";
                    page+=rs2.getString(3);
                    page+="</p>\n<p>";
                    page+=rs2.getString(4);
                    page+="</p>\n<p>";
                    page+="<strike>";
                    page+=rs2.getString(5);
                    page+="</strike>&nbsp;&nbsp;";
                    page+=rs2.getString(6);
                    page+="</p>\n<p>";
                    page+=rs2.getString(7);
                    page+="</p>\n";
                    page+="</div>\n</td>";
                    page+="</tr>\n";
                    page+="</table>\n";
                }
            }
            else{
                System.err.println(subcat);
                ResultSet rs2=cc.itemBySubCat(cat, subcat, noOfProducts, offset);
                page+=organiseResult(rs2, cat);
                page+="\n";
            }
        }
        page+="</div>";
        return page;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
