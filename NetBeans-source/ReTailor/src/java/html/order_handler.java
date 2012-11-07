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
            // my work is done here
        }
        System.err.println("isOrderDone "+isOrderDone);
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
                    System.err.println("found already in cart! "+i);        // sometimes, reminds me to clean and build
                    Integer res=new Integer(Integer.parseInt(order[2])+Integer.parseInt(cart.elementAt(i)[2]));
                    order[2]=res.toString();
                    found=true;
                    cart.setElementAt(order, i);
                    // now check the quantity in table
                    Integer amt=cc.quantityOfItemID(cart.elementAt(i)[0],cart.elementAt(i)[1]);
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
                System.err.println("didn't find it in cart!");
                // now check the quantity in table
                Integer amt=cc.quantityOfItemID(order[0],order[1]);
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
            System.err.println("target_url "+target_url);
            response.sendRedirect(target_url==null?"index.jsp":target_url);
        }
        
        else{                // customer is done shopping, this one finalizes the purchase
            int userid=-1;
            try{
                Integer.parseInt(session.getAttribute("userid").toString());
            }
            catch(Exception e){
                System.err.println("should never come here, didn't you check valid login before directing here (see checkout.jsp)");
                e.printStackTrace();
            }
            int billid=cc.storeOrders(session.getAttribute("userid").toString(),(Vector<String[]>)session.getAttribute("cart_array"));
            session.setAttribute("tmp_billid", billid); // temporary, will be removed soon
            
            // send the user to thankyou page
            session.setAttribute("cart_array",null);   // huh!
            response.sendRedirect("thankyou.jsp");
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
