package html;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.http.HttpSession;
import database.DatabaseConnection;
/**
 *
 * @author
 * piyush
 */
public class checkoutFuncs {
    DatabaseConnection cc=new DatabaseConnection();
    
    // returns the code to be displayed on checkout page
    public String getOrderCode(Vector<String[]> order,HttpSession session){
        String ret="";
        if(order!=null){
            for(String[] i : order){
                ret+=(i[0]+" "+i[1]+" "+i[2]+"<br />");
            }
            String login_str="Order Current Selection";
            boolean logged_in=true;
            if(session.getAttribute("userid").toString().equals("-1")){
                logged_in=false;
                login_str="Please login and proceed to order";
            }

            if(logged_in){  // put in database
                ret+="<form action='order_handler' method='post'>";
                ret+="<input type='hidden' name='place_order' value='1'>";
                ret+="<input type='submit' value='"+login_str+"'";
                return ret;
            }
            else{           // call the login page address
                // TODO : ask around for a login page
                ret+="<form action='index.jsp' method='post'>";
                ret+="<input type='submit' value='"+login_str+"'";
                return ret;
            }
        }
        else{
            ret="<h1>If you don't have money then get out!! Don't you ever bring an empty cart to me!!!!</h1>";
            // TODO : soften this
            return ret;
        }
    }
    
    // thank you message
    public String getThankYou(String billid){
        ResultSet rs=null;
        try{
            rs=cc.getSpecificBillDetails(billid);
        }
        catch(Exception e){
            //die
        }
        return null;
    }
}
