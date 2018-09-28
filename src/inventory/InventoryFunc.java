package inventory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



public class InventoryFunc  {
	
	DBConImpl con=DBConImpl.getInstance();
	ArrayList<Items> bucket=new ArrayList<Items>();

	Scanner s=new Scanner(System.in);
	public void inventoryMenu() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~MENU~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("\t\t\t 1 - to add to inventory");
		System.out.println("\t\t\t 2 - to display inventory");
		System.out.println("\t\t\t 3 - to make purchase");
		System.out.println("\t\t\t 4 - exit from the program");	
		
		
	}

	public void inventoryMenuChoices(int userchoice) throws SQLException {
		
		switch(userchoice) {
				
		case 1:
		
		inventoryInputbyUser();		
		break;
		
			
		case 2:
			con.view();
			break;
		
		case 3:
		
			try {
				displayItems();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Problem with displaying only items, Please Check");
			}
			try {
				displayqtyofItemSelected();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Error occured while generating the bill");
			}
		
		break;
		
		case 4:		
		System.out.println("See you Later...");
		break;
		
		default:
		
		System.out.println("\t\t\t Invalid Choice");
		
		}	
		
	}
	
	public void inventoryInputbyUser() {
		Items item1=new Items();
		try {
		System.out.println("Enter Product Name :");
		item1.setItemName(s.nextLine());
		System.out.println("Enter Make and Model Name :");
		item1.setModelName((s.nextLine()));
		System.out.println("Enter Number of items :");
		item1.setQuantity(Integer.parseInt(s.nextLine()));
		System.out.println("Enter Price :");
		item1.setPrice(Long.parseLong(s.nextLine()));
		}catch(Exception e) {
			System.out.println("Enter the proper values for the respective fields");
		}
		
		
		try {
			int i=con.insert(randomIDGenerator(),item1.itemName,item1.modelName,item1.quantity,item1.price );
			if(i>0){
			System.out.println("Item details feeded into DB successfully");
			i++;
			}
			else
				System.out.println("Problem in storing the Item details into DB");
			} catch (SQLException e) {
			
			//e.printStackTrace();
			System.out.println("Values not Inserted into DB properly Please check...");
		}
		
	}
	
	public int randomIDGenerator(){
		Random ran=new Random();
		
		int ranId=ran.nextInt(10000);
		
		return ranId;
	}
	
	
	
	
	
	public void displayItems() throws SQLException {
		con.viewItems();
		}
	
	public void displayqtyofItemSelected() throws SQLException {
		System.out.println("Select the Item number from the above list:");
		int selctedinput=Integer.parseInt(s.nextLine());
		int receivedQty=con.viewItemQty(selctedinput);
		if(receivedQty>0)
		{System.out.println("There are "+receivedQty+" number of selected items in Stock go ahead and shop");
		System.out.println("Enter how many you want to buy:");
		int buyingnumber=s.nextInt();			
		con.update(selctedinput, (receivedQty-buyingnumber));
		
			
		System.out.println("Purchase done for "+buyingnumber+" no. of selected Items");
				
		
		System.out.println("Your Bill is : Rs."+con.getPrice(selctedinput, buyingnumber)+" Only for "+buyingnumber+" number of Items purchased.");
		
		System.out.println("Thank you for Purchasing");
		}
			
			}
		
	

}


