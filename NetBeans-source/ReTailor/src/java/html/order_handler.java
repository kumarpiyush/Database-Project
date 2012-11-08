/*
 * This file handles the order placement
 */

/**
 *
 * @author 
 * ReTailor
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
import database.DatabaseConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "order_handler", urlPatterns = {"/order_handler"})
public class order_handler extends HttpServlet {

    DatabaseConnection cc=new DatabaseConnection();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String isOrderDone=null;
        try{
            isOrderDone=request.getParameter("place_order");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(isOrderDone==null){      // order still in progress
            Vector<String[]> cart=null;
            String[] order=new String[3];
            order[0]=request.getParameter("cat");
            order[1] = request.getParameter("id");
            order[2] = request.getParameter("prod_cnt");
            try{
                cart=(Vector<String[]>)session.getAttribute("cart_array");  // try to get currently ordered things
                if(cart==null){                                             // oops, cart got tripped
                    cart=new Vector<String[]>();                            // pick up the empty cart
                }
            }
            catch(Exception e){     // never comes here, but who knows what's java's been up to :P
                cart=new Vector<String[]>();
            }

            // just increase the amount if already ordered
            boolean found=false;
            for(int i=0;i<cart.size();i++){
                if(cart.elementAt(i)[1].equals(order[1]) && cart.elementAt(i)[0].equals(order[0])){
                    Integer res=new Integer(Integer.parseInt(order[2])+Integer.parseInt(cart.elementAt(i)[2]));
                    order[2]=res.toString();
                    found=true;
                    cart.setElementAt(order, i);
                    // now check the quantity in table
                    Integer amt = 0;
                    try {
                        amt = cc.quantityOfItemID(cart.elementAt(i)[0],cart.elementAt(i)[1]);
                    } catch (SQLException ex) { }
                    if(amt<Integer.parseInt(cart.elementAt(i)[2])){
                        // ordered more than what we have
                        order[2]=amt.toString();        // give him what we have
                        cart.setElementAt(order, i);
                    }
                    if(amt<=0){
                        cart.removeElementAt(i);        // well, we're broke
                    }
                    break;
                }
            }
            if(!found){
                // now check the quantity in table
                Integer amt = 0;
                try {
                    amt = cc.quantityOfItemID(order[0],order[1]);
                } catch (SQLException ex) { }
                if(amt<Integer.parseInt(order[2])){
                    // ordered more than one could buy
                    order[2]=amt.toString();        // give him what we have
                }
                if(amt>0){
                    cart.add(order);                // no use adding if we don't have
                }
            }

            session.setAttribute("cart_array",cart);
            String target_url="";
            target_url=request.getParameter("target_url");
            response.sendRedirect(target_url==null?"index.jsp":target_url);
        }
        
        else{                // customer is done shopping, this one finalizes the purchase
                    
            int userid=-1;
            try{
                Integer.parseInt(session.getAttribute("userid").toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
            String billid=null;
            Vector<String[]> cart=(Vector<String[]>)session.getAttribute("cart_array");
            Vector<String[]> newCart=new Vector<String[]>();        // after adjustments are made at the pre-final page
            boolean orderOk=true;
            try{
                for(int i=0;i<cart.size();i++){
                    Integer newCount=Integer.parseInt(request.getParameter("product"+i));
                    if((newCount>0)){
                        String[] tmp=new String[4];
                        tmp[0]=cart.elementAt(i)[0];
                        tmp[1]=cart.elementAt(i)[1];
                        tmp[2]=newCount.toString();
                        tmp[3]=cart.elementAt(i)[3];
                        
                        newCart.add(tmp);
                    }
                    // else the user removed the product
                }
                cart=newCart;
                if(cart.size()==0){
                    orderOk=false;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            try {
                if(orderOk){
                    billid=cc.storeOrders(session.getAttribute("userid").toString(),cart);
                }
            }
            catch (SQLException ex) { }
            session.setAttribute("tmp_billid", billid); // temporary, will be removed soon
            
            // send the user to thankyou page
            session.setAttribute("cart_array",null);   // huh!
            if(orderOk){
                response.sendRedirect("profile.jsp?id="+session.getAttribute("userid").toString()+"&bill=true&specific="+billid);
            }
            else{
                response.sendRedirect("index.jsp");
            }
            return;                                   // bye!
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "";
    }// </editor-fold>
}
