/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package html;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.DatabaseConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author
 * piyush
 */
@WebServlet(name = "AddAccount", urlPatterns = {"/AddAccount"})
public class AddAccount extends HttpServlet {

    DatabaseConnection cc = new DatabaseConnection();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        HttpSession session=request.getSession();
        boolean emailExists=false;
        try{
            emailExists=cc.checkEmail(email);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        if(emailExists){
            session.setAttribute("invalidemail", "1");
            response.sendRedirect("SignUp.jsp");
        }
        else{
            String name,phno,address,passwd;
            name=request.getParameter("name");
            email=request.getParameter("email");
            phno=request.getParameter("number");
            address=request.getParameter("address");
            passwd=request.getParameter("password");
            try {
                int userid=cc.signupCustomer(name,email,phno,address,passwd);
                session.setAttribute("userid", userid);
                session.setAttribute("name", name);
            } 
            catch (SQLException ex) {
                Logger.getLogger(AddAccount.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
