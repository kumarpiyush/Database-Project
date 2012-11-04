package database;

/**
 *
 * @author sameer
 */
import java.sql.*;

public class DatabaseConnection {
    
    
    private static Connection con = null;
    //private static final String DBNAME = "foobar";
    //private static final String DB_USERNAME = "sameer";
    //private static final String DB_PASSWORD = "sundarban";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "sundarban";
    private static final String URL = "jdbc:mysql://localhost/foobar";
    
    private static String CATEGORY = null;
    private static String ORDER = "desc";
    private static String SORT_BY_1 = "ID"; //this has to be popularity
    private static String SORT_BY_2 = null;
    
    public DatabaseConnection() {
        try{
            if (con == null){
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
            }
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }   
    }
    
    public void category_to_table(String category){
        if(category.equals("Books")){
            CATEGORY = "book";
            SORT_BY_2 = "title";
        }else if(category.equals("Computer Accessories")){
            CATEGORY = "computer_accessories";
            SORT_BY_2 = "";
        }else if(category.equals("Electronics")){
            CATEGORY = "electronics";
            SORT_BY_2 = "";
        }else if(category.equals("Clothing")){
            CATEGORY = "clothing";
            SORT_BY_2 = "";
        }
    }
    
    //***************************************************************************
    //gives the list of main categories
    public ResultSet listofcategories(){
        try{
            Statement stmt = con.createStatement();   
            ResultSet rs = stmt.executeQuery("select * from categories order by name;");
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    //***************************************************************************
    public ResultSet topByCat(String cat, int no){
        if(cat!=null)
            category_to_table(cat);
        try{
            Statement stmt = con.createStatement();   
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" order by "+ SORT_BY_1+" "+ORDER+" limit "+no+";");
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    //***************************************************************************
    public ResultSet itemByID(String cat, String id){
        if(cat!=null)
            category_to_table(cat);
        try{
            Statement stmt = con.createStatement();   
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" where ID="+id+";");
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultSet itemBySubCat(String cat, String subcat,int no,int offset){
        if(cat!=null)
            category_to_table(cat);
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" where category = '"+subcat+"' order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no);
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index
    public ResultSet listofproducts(String category,int no,int offset){
        if(category!=null)
            category_to_table(category);
        try{
            //TODO fill all the sort bys and check order by
            Statement stmt = con.createStatement();
            /*stmt.setString(1, CATEGORY);
            stmt.setString(2, SORT_BY_1);
            stmt.setString(3, ORDER);
            stmt.setString(4, SORT_BY_2);
            stmt.setInt(5, offset);
            stmt.setInt(6, no);*/
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no);
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    //gives the list of sub-categories for given product
    public void list_of_categories_for_product(String category){
        category_to_table(category);
        try{
            //TODO fill all the sort byif(cat!=null)s and check order by
            PreparedStatement stmt = con.prepareStatement("select distinct(category) from ? order by category asc");   
            stmt.setString(1, CATEGORY);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    //order the data according to sortby and if whatorder = false, increasing, else descreasing
    public void order_data(String sortby,boolean whatorder){
        SORT_BY_1 = sortby;
        if (whatorder){
            ORDER = "asc";
        }
        else{
            ORDER = "desc";
        }
    }
    
    public void search_field(){
        
    }
    
    public static void sanket() throws Exception{
        System.err.println("Sanket Rocks!!!");
    }
}

