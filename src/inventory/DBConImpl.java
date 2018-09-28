package inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConImpl implements DBConDAO{

	Items item=new Items();
	ArrayList<Items> actualBucket=new ArrayList<Items>();
	 //Step 1  
    // create a JDBCSingleton class.  
   //static member holds only one instance of the JDBCSingleton class.  
           
       private static DBConImpl jdbc;  
         
   //JDBCSingleton prevents the instantiation from any other class.  
     private DBConImpl() {  }  
      
  //Now we are providing global point of access.  
       public static DBConImpl getInstance() {    
                                   if (jdbc==null)  
                                 {  
                                          jdbc=new  DBConImpl();  
                                 }  
                       return jdbc;  
           }  
          
 // to get the connection from methods like insert, view etc.   
        private static Connection getConnection()throws ClassNotFoundException, SQLException  
        {  
              
            Connection con=null;  
            Class.forName("com.mysql.jdbc.Driver");  
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "Welcome123");  
            return con;  
              
        }  
          
//to insert the Item into the inventory   
        @SuppressWarnings("static-access")
		public int insert(int ranId,String ItemName,String ItemModel, int Qty, long Price) throws SQLException  
        {  
            Connection c=null;  
              
            PreparedStatement ps=null;  
              
            int recordCounter=0;  
              
            try {  
                  
                   	c=this.getConnection();  
                    ps=c.prepareStatement("insert into electronics(idElectronics,Item,Model,Quantity,Price)values(?,?,?,?,?)");  
                    ps.setInt(1, ranId);  
                    ps.setString(2, ItemName);
                    ps.setString(3, ItemModel);
                    ps.setInt(4, Qty);
                    ps.setLong(5, Price);
                    
                    recordCounter=ps.executeUpdate();  
                      
            } catch (Exception e) { e.printStackTrace(); } finally{  
                  if (ps!=null){  
                    ps.close();  
                }if(c!=null){  
                    c.close();  
                }   
            }  
           return recordCounter;  
        }  

//to view the Items and details from the inventory        
    @SuppressWarnings("static-access")
	public  ArrayList<Items> view() throws SQLException  
    {  
    	
              Connection con = null;  
      PreparedStatement ps = null;  
      ResultSet rs = null;  
                
              try {  
                    
                      con=this.getConnection();  
                      ps=con.prepareStatement("select * from electronics");  
                     // ps.setString(1, name);  
                      rs=ps.executeQuery();  
                      while (rs.next()) {  
                            System.out.println("ItemID: "+rs.getInt(1));
                            System.out.println("Item Name: "+rs.getString(2));
                            System.out.println("Item Model: "+rs.getString(3));
                            System.out.println("Quantity: "+rs.getInt(4));
                            System.out.println("Price: "+rs.getLong(5));
                            System.out.println();
                       
                      }  
                     
                    
        } catch (Exception e) { System.out.println("Problem occured while Retrieving data from DB");}  
        finally{  
                  if(rs!=null){  
                      rs.close();  
                  }if (ps!=null){  
                    ps.close();  
                }if(con!=null){  
                    con.close();  
                }   
              }  
              
              
             
              
              return actualBucket;
    }  
      
    
    //Display Electronic Items in inventory 
    @SuppressWarnings("static-access")
	public  void viewItems() throws SQLException  
    {  
              Connection con = null;  
      PreparedStatement ps = null;  
      ResultSet rs = null;  
                
              try {  
                    
                      con=this.getConnection();  
                      ps=con.prepareStatement("select idElectronics,Item from electronics");  
                     // ps.setString(1, name);  
                      rs=ps.executeQuery();  
                      
                      System.out.println("Id"+"\t\t\t\t"+"Item Name");
                      System.out.println();
                      while (rs.next()) {  
                            System.out.print(rs.getInt(1));
                            System.out.print("\t\t\t\t"+rs.getString(2));
                            System.out.println();
                                                  
                      }  
              
                    
        } catch (Exception e) { System.out.println("Problem occured while Retrieving data from DB");}  
        finally{  
                  if(rs!=null){  
                      rs.close();  
                  }if (ps!=null){  
                    ps.close();  
                }if(con!=null){  
                    con.close();  
                }   
              }  
              
    } 
    
    //no. of items in stock
    @SuppressWarnings("static-access")
	public  int viewItemQty(int id) throws SQLException  
    {  int i=0;
              Connection con = null;  
      PreparedStatement ps = null;  
      ResultSet rs = null;  
                
              try {  
                    
                      con=this.getConnection();  
                      ps=con.prepareStatement("select Quantity from electronics where idElectronics='"+id+"'");  
                     // ps.setString(1, name);  
                      rs=ps.executeQuery();  
                      while (rs.next()) {  
                          
                           i=rs.getInt(1);
                                                
                      }  
                      
                      if(i==0)
                    	  System.out.println("Sorry Item Out of Stock");
                        
              
                    
        } catch (Exception e) { System.out.println("Sorry out of Stock");}  
        finally{  
                  if(rs!=null){  
                      rs.close();  
                  }if (ps!=null){  
                    ps.close();  
                }if(con!=null){  
                    con.close();  
                }   
                
              }
			return i;  
              
    } 
    
    
    //fetching price of selected no of items
    @SuppressWarnings("static-access")
	public float getPrice(int id, int selectedqty) throws SQLException{
    	
    	float price=0;
        Connection con = null;  
PreparedStatement ps = null;  
ResultSet rs = null;  
          
        try {  
              
                con=this.getConnection();  
                ps=con.prepareStatement("select Price from electronics where idElectronics='"+id+"'");  
               // ps.setString(1, name);  
                rs=ps.executeQuery();  
                while (rs.next()) {  
                    
                     price=rs.getFloat(1);
                                            
                }  
        
              
  } catch (Exception e) { System.out.println("Sorry out of Stock");}  
  finally{  
            if(rs!=null){  
                rs.close();  
            }if (ps!=null){  
              ps.close();  
          }if(con!=null){  
              con.close();  
          }   
          
        }
		return price*selectedqty;  
    	
    	
    	
    }
    
   // to update inventory qty after buying 
    @SuppressWarnings("static-access")
	public int update(int id, int qty) throws SQLException  {  
            Connection c=null;  
            PreparedStatement ps=null;  
              
            int recordCounter=0;  
            try {  
                    c=this.getConnection();  
                    ps=c.prepareStatement(" update electronics set Quantity=? where idElectronics='"+id+"' ");  
                    ps.setInt(1, qty);  
                    recordCounter=ps.executeUpdate();  
            } catch (Exception e) {  e.printStackTrace(); } finally{  
                    
                if (ps!=null){  
                    ps.close();  
                }if(c!=null){  
                    c.close();  
                }   
             }  
           return recordCounter;  
        }  
          
}
