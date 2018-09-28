package inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DBConDAO {

	public int insert(int ranId,String ItemName,String ItemModel, int Qty, long Price) throws SQLException;
	public  ArrayList<Items> view() throws SQLException;
	public  void viewItems() throws SQLException;
	public  int viewItemQty(int id) throws SQLException;
	public float getPrice(int id, int selectedqty) throws SQLException;
	public int update(int id, int qty) throws SQLException;
	
	
	
}
