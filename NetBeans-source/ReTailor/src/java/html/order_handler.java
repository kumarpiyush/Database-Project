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


/**
 *
 * @author piyush (believe it :P)
 */
@WebServlet(name = "order_handler", urlPatterns = {"/order_handler"})
public class order_handler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String isOrderDone=null;
        try{
            isOrderDone=session.getAttribute("place_order").toString();
        }
        catch(Exception e){
            // my work is done here
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
        
        else{                // customer iss done shopping
            DatabaseConnection cc=new DatabaseConnection();
            cc.storeOrders((Vector<String[]>)session.getAttribute("cart_array"));
            response.sendRedirect("index.jsp");
            
            session.setAttribute("place_order",null);   // huh!
            return;         // bye!
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
