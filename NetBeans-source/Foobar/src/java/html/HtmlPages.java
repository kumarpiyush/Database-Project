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
    
    private String getSiteBrand() {
        return "<img src=\"images/4622_thumb-10-9-2012-6b0dfc22e3f44fd13c268fcdc28bcdf1.jpg\"/>";
    }

    private String getLoginOut() {
        return "";
    }

    private String getSearch() {
        return "";
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
    
    public String getHeader(){
        String page;
        page = "<div id=\"siteBrand\">" + getSiteBrand() + "</div>" + getLoginOut() + getSearch();
        
        return page;
    }
    
    public String getSideBar() throws SQLException{
        String page="";
        //page="<div id=\"topLevel\">";
        ResultSet rs=cc.listofcategories();
        while(rs.next()){
            page+="<a href = \"index.jsp?cat="+rs.getString(1)+"\" ><b>";
            page+=rs.getString(1);
            page+="</b></a></br></br>\n\t\t\t\t";
        }
        return page;
    }
    
    public String getElem(String category,String id,String arg1,String arg2,String mrp, String price){
        String page="";
        page+="<div id=\"entry\">";
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
    
    public String getMainPage(String cat,String id,String subcat) throws SQLException{
        String page="";
        
        if(cat==null){
            ResultSet rs=cc.listofcategories();
            while(rs.next()){
                System.err.println(rs.getString(1));
                ResultSet rs2=cc.topByCat(rs.getString(1),3);
                page+="<div id=\"collectedEntry\">";
                if("Books".equals(rs.getString(1))){
                    while(rs2.next()){
                        page+=getElem(rs.getString(1), rs2.getString(1), rs2.getString("title"), rs2.getString("author"), rs2.getString("mrp"), rs2.getString("price"));
                    }
                }
                page+="</div>";
            }
        }
        else{
            if(id==null && subcat==null){
                ResultSet rs2=cc.listofproducts(cat,10,0);
                while(rs2.next()){
                    page+="<div id=\"entry\">";
                    page+="<a href=\"index.jsp?cat="+rs.getString(1)+"&id="+rs2.getString(1)+"\">";
                    page+=rs2.getString("title");
                    page+="</a>";
                    page+="<br/>";
                    page+=rs2.getString("author");
                    page+="<br/>";
                    page+="<strike>";
                    page+=rs2.getString("mrp");
                    page+="</strike>&nbsp;&nbsp;";
                    page+=rs2.getString("price");
                    page+="</div>";
                }
            }
        }
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
