package html;
import java.util.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author
 * piyush
 */
public class checkoutFuncs {
    // returns the code to be displayed on checkout page
    public String getOrderCode(Vector<String[]> order,HttpSession session){
        String ret="";
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
}
