package database;

/**
 *
 * @author sameer
 */
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class DatabaseConnection {
    
    
    private static Connection con = null;
    //private static final String DBNAME = "foobar";
    //private static final String DB_USERNAME = "sameer";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "sundarban";
    private static final String URL = "jdbc:mysql://localhost/foobar";
    
    private String CATEGORY = null;
    private String SORT_BY_2 = null;
    
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
            SORT_BY_2 = "brand";
        }else if(category.equals("Electronics")){
            CATEGORY = "electronics";
            SORT_BY_2 = "brand";
        }else if(category.equals("Clothing")){
            CATEGORY = "clothing";
            SORT_BY_2 = "category";
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
    public ResultSet topByCat(String cat,String SORT_BY_1,String ORDER, int no){
        if(cat!=null)
            category_to_table(cat);
        if(SORT_BY_1==null){
            SORT_BY_1="popularity";
            ORDER="desc";
        }
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
    
    public ResultSet itemBySubCat(String cat, String subcat,String SORT_BY_1,String ORDER, int no,int offset){
        if(cat!=null)
            category_to_table(cat);
        if(SORT_BY_1==null){
            SORT_BY_1="popularity";
            ORDER="desc";
        }
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
    public ResultSet listofproducts(String category,String SORT_BY_1,String ORDER,int no,int offset){
        if(category!=null)
            category_to_table(category);
        if(SORT_BY_1==null){
            SORT_BY_1="popularity";
            ORDER="desc";
        }
        try{
            //TODO fill all the sort bys and check order by
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no);
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index
    public ResultSet listofsubcats(String category){
        if(category!=null)
            category_to_table(category);
        try{
           Statement stmt = con.createStatement();   
           ResultSet rs = stmt.executeQuery("select distinct category from "+CATEGORY);
           return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void search_field(){
        
    }
    
    public int loginCheck(String username,String password){
        return 144;
    }
    
    public String useridToName(int userid){
        String name="";
        try{
           Statement stmt = con.createStatement();   
           ResultSet rs = stmt.executeQuery("select name from customer where ID = "+userid);
           rs.next();
           name = rs.getString(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return name;
    }
    
    public List search_field(String search, String table) throws SQLException{
        StringTokenizer st1=new StringTokenizer(search);
        StringTokenizer st2=new StringTokenizer(search);
        StringTokenizer st3=new StringTokenizer(search);
        StringTokenizer st4=new StringTokenizer(search);
        ResultSet book=null;
        if(table.equals("Books") || table.equals("all")){
            String query="select * from book where ";
            String token;
            while(st1.hasMoreTokens()){
                token=st1.nextToken();
                query+="(author like \"%"+token+"%\" or title like \"%"+token+"%\") and";
            }
            query+="(author like \"%\" or title like \"%\") order by popularity limit 10";
            PreparedStatement stmt = con.prepareStatement(query);
            book = stmt.executeQuery();
        }
        ResultSet cloth=null;
        if(table.equals("Clothing") || table.equals("all")){
            String query="select * from clothing where ";
            String token;
            while(st2.hasMoreTokens()){
                token=st2.nextToken();
                if("Men".equals(token)){
                    token="M";
                }
                if("Women".equals(token)){
                    token="W";
                }
                if("Kid".equals(token) || "Kids".equals(token) || "Children".equals(token)){
                    token="K";
                }
                query+="(description like \"%"+token+"%\" or category like \"%"+token+"%\" or category2 like \"%"+token+"%\") and";
            }
            query+="(description like \"%\" or category like \"%\" or category2 like \"%%\") order by popularity limit 10";
            PreparedStatement stmt = con.prepareStatement(query);
            cloth = stmt.executeQuery();
        }
        ResultSet compu=null;
        if(table.equals("Computer Accessories") || table.equals("all")){
            String query="select * from computer_accessories where ";
            String token;
            while(st3.hasMoreTokens()){
                token=st3.nextToken();
                query+="(brand like \"%"+token+"%\" or model like \"%"+token+"%\" or category like \"%"+token+"%\" or description like \"%"+token+"%\") and";
            }
            query+="(brand like \"%\" or model like \"%\" or category like \"%%\" or description like \"%%\") order by popularity limit 10";
            PreparedStatement stmt = con.prepareStatement(query);
            compu = stmt.executeQuery();
        }
        ResultSet elec=null;
        if(table.equals("Electronics") || table.equals("all")){
            String query="select * from electronics where ";
            String token;
            while(st4.hasMoreTokens()){
                token=st4.nextToken();
                query+="(brand like \"%"+token+"%\" or model like \"%"+token+"%\" or category like \"%"+token+"%\" or description like \"%"+token+"%\") and";
            }
            query+="(brand like \"%\" or model like \"%\" or category like \"%%\" or description like \"%%\") order by popularity limit 10";
            PreparedStatement stmt = con.prepareStatement(query);
            elec = stmt.executeQuery();
        }
        List<ResultSet> l=new LinkedList<ResultSet>();
        l.add(book);
        l.add(cloth);
        l.add(compu);
        l.add(elec);
        System.err.println(search+"  "+table);
        return l;
    }
    
}

