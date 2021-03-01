package store;

import java.util.Calendar;
import java.time.LocalDateTime;
import java.util.Scanner;

public class print_menu {
	
	/*public static final String YELLOW = "\u001B[33m";
	public static final String RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	No van los colores en Windows :(
	*/
	
	public static int printMain(StoreManager mainManager){
		
		//ProductList def = new ProductList();
		//StoreManager mainManager = new StoreManager();
		Calendar time = Calendar.getInstance();

		/*System.out.println("--------MainMenu--------\n\n"
				+ printTime() + " " + LocalDateTime.now().getDayOfWeek()
				+ ", " + time.get(Calendar.DAY_OF_MONTH) 
				+ " of " + LocalDateTime.now().getMonth() + " of " 
				+ time.get(Calendar.YEAR) + "\n\n"
				+ "StoreInfo : Store name: " + mainManager.getName() +  "\n"
				+ "Stock cost: " + mainManager.getStockCost() + 
				"\nStock benefit: " + mainManager.getStockBenefit() + "\n\n"
				+ "1.-Create Store\n2.-Manage Stock\n"
				+ "3.-Manage Orders  (To process)\n"
				+ "4.-Manage Orders  (To be processed)\n"
				+ "5.-Manage Clients\n6.-Manage Providers\n"
				+ "7.-Manage Employees\n8.-Print Store Info\n"
				+ "9.-Testing\n0.-Exit application\nOption>");
		*/
		System.out.println("--------MainMenu--------\n\n"
				+ printTime() + " " + LocalDateTime.now().getDayOfWeek()
				+ ", " + time.get(Calendar.DAY_OF_MONTH) 
				+ " of " + LocalDateTime.now().getMonth() + " of " 
				+ time.get(Calendar.YEAR) + "\n\n");
		
		//mainManager.setStockCost(mainManager.getStock().calculateCost());
		//mainManager.setStockBenefit(mainManager.getStock().calculateBenefit());
		mainManager.print();
		
		System.out.println("\n\n"
				+ "1.-Create Store\n2.-Manage Stock\n"
				+ "3.-Manage Orders  (To process)\n"
				+ "4.-Manage Orders  (Processed)\n"
				+ "5.-Manage Clients\n6.-Manage Providers\n"
				+ "7.-Manage Employees\n8.-Print Store Info\n"
				+ "9.-Testing\n0.-Exit application\nOption>");
		
		int switch_number;
		
		do {
			Scanner menu = new Scanner(System.in);
			switch_number = menu.nextInt();
			if(switch_number<0 || switch_number>9) System.err.println("Invalid input. Try again.");
		}while(switch_number<0 || switch_number>9);
		
		return switch_number;
	}
	
	public static int printManage(String title) {
		
		boolean isTP = false;
		if(title.contentEquals("orderTP")) isTP = true; 
		
		char[] arrayChain = title.toCharArray();
		arrayChain[0] = Character.toUpperCase(arrayChain[0]);
		
		String newTitle = new String(arrayChain);
		if(isTP) newTitle = newTitle.substring(0, 5);
		
		String rtitle = title;
		if(isTP) rtitle = title.substring(0, 5);
		
		String optionParse = "";
		int option = 0;
		
		
		System.out.println("--------Manage " + rtitle + 
				"s Menu--------\n\n++1.-Insert " + newTitle 
				+ "\n++2.-Remove " + newTitle + "\n++3.-Modify " 
				+ newTitle + "\n++4.-Search " + newTitle 
				+ "\n++5.-Print " + newTitle); 
		if(isTP) System.out.print("++6.-Process " + newTitle + "s\n");
		System.out.print("++0.-Exit Menu\nOption>");
		
		if(isTP) {
			do {
				
				optionParse = new Scanner(System.in).nextLine();
				option = Integer.parseInt(optionParse);
				
					if(option<0 || option>6) 
						System.out.println("Invalid input. Try again.");
			}while(option<0 || option>6);
			
		}else {
			do {
				
				optionParse = new Scanner(System.in).nextLine();
				option = Integer.parseInt(optionParse);
				
					if(option<0 || option>5) 
						System.out.println("Invalid input. Try again.");
			}while(option<0 || option>5);
		}
		
		
		
		return option;
	}
	
	public static String printTime() {
		Calendar time = Calendar.getInstance();
		return "[" + time.get(Calendar.HOUR_OF_DAY) + 
				":" + time.get(Calendar.MINUTE) + 
				":" + time.get(Calendar.SECOND) + "]";
	}
}
