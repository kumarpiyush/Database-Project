/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package html;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author piyush
 */
@WebServlet(name = "order_handler", urlPatterns = {"/order_handler"})
public class order_handler extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Vector<String[]> cart=null;
        String[] order=new String[3];
        order[0]=request.getParameter("cat");
        order[1] = request.getParameter("id");
        order[2] = request.getParameter("prod_cnt");
        try{
            cart=(Vector<String[]>)session.getAttribute("cart_array");
            if(cart==null){
                cart=new Vector<String[]>();
            }
        }
        catch(Exception e){
            cart=new Vector<String[]>();
        }
        boolean found=false;
        for(int i=0;i<cart.size();i++){
            if(cart.elementAt(i)[1].equals(order[1]) && cart.elementAt(i)[0].equals(order[0])){
                System.err.println("found it! "+i);
                Integer res=new Integer(Integer.parseInt(order[2])+Integer.parseInt(cart.elementAt(i)[2]));
                order[2]=res.toString();
                found=true;
                cart.setElementAt(order, i);
                break;
            }
        }
        if(!found){
            System.err.println("didn't find it!");
            cart.add(order);
        }
        
        session.setAttribute("cart_array",cart);
        response.sendRedirect("index.jsp");
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
