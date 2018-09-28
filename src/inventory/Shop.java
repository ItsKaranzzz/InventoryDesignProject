package inventory;


import java.util.Scanner;

/**
 * @author Karan Chaudhary
 *
 */
public class Shop {

	
public static void main(String[] args) {
		
		InventoryFunc display=new InventoryFunc();
		Scanner scn=new Scanner(System.in);
		
		int userchoice;
		
		try {
			
			do {
				//Displaying the menu to the user...
				display.inventoryMenu();
				
			System.out.println("\n\nPlease Enter any Menu option number:");
			userchoice=Integer.parseInt(scn.next());
							
				//Displaying the choices according to the User Input
			display.inventoryMenuChoices(userchoice);
			}while(userchoice!=4);
			
			}catch(Exception e) {
				System.out.println("Enter proper Serial no. value next time");
				
			}
		
		
		}
		
			
	 
}

