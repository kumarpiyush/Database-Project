package database;

/**
 *
 * @author sameer
 */
import java.sql.*;

public class DatabaseConnection {
    
    
    private static Connection con = null;
    //private static final String DBNAME = "foobar";
    private static final String DB_USERNAME = "sameer";
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
    
    //gives the list of main categories
    public void listofcategories(){
        try{
            Statement stmt = con.createStatement();   
            ResultSet rs = stmt.executeQuery("select * from categories order by name;");
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void category_to_table(String category){
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
    
    //gives the list of products sorted by some order with an offset on index
    public void listofproducts(String category,int index){
        category_to_table(category);
        try{
            //TODO fill all the sort bys and check order by
            PreparedStatement stmt = con.prepareStatement("select * from ? order by ? ?, ? asc limit 30,?");   
            stmt.setString(1, CATEGORY);
            stmt.setString(2, SORT_BY_1);
            stmt.setString(3, ORDER);
            stmt.setString(4, SORT_BY_2);
            stmt.setInt(5, index);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    //gives the list of sub-categories for given product
    public void list_of_categories_for_product(String category){
        category_to_table(category);
        try{
            //TODO fill all the sort bys and check order by
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
        try{
            PreparedStatement stmt = con.prepareStatement("select * from ? order by ? ?, ? asc limit 30");   
            stmt.setString(1, CATEGORY);
            stmt.setString(2, SORT_BY_1);
            stmt.setString(3, ORDER);
            stmt.setString(4, SORT_BY_2);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void search_field(){
        
    }
    
    public static void sanket() throws Exception{
        System.err.println("Sanket Rocks!!!");
    }
}

