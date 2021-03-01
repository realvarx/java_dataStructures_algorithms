/*
 * 
 * @author Álvaro Merino Argumánez
 * 
 * @version 2
 */

import store.*;
import dataStructures.*;

import java.util.ArrayList;
import java.util.Scanner;

public class RunApp {
	
	public static void main(String[] args) {
		
		int switch_number = 10;
		StoreManager Store = new StoreManager();
		
		do {
			
			switch_number = print_menu.printMain(Store);
			switch (switch_number) {
			 
			 case 1:
				 
				 System.out.println(print_menu.printTime() + "Creating a new store");
				 Store = Store.readFromStdio();
				 
				 break;
				 
			 case 2:
				 
				 int case2 = print_menu.printManage("stock");

				 switch(case2) {
				 
				 case 0:
					 break;
					 
				 case 1:
					 System.out.println(print_menu.printTime() + "Inserting new stock");
					 Store.insert("stock");
					 break;
				 case 2: 
					 System.out.println(print_menu.printTime() + "Removing the stock");
					 Store.remove("stock");
					 break;
				 case 3:
					 System.out.println(print_menu.printTime() + "Modifying the stock");
					 Store.modify("stock");
					 break;
				 case 4:
					 System.out.println(print_menu.printTime() + "Searching the stock");
					 Store.search("stock");
					 break;
				 case 5:
					 Store.print("stock");
					 break;
				 }
				 
				 break;
				 
			 case 3:
				 int case3 = print_menu.printManage("orderTP");
				 
				 switch(case3) {
				 
				 case 0:
					 break;
					 
				 case 1:
					 System.out.println(print_menu.printTime() + "Inserting new order");
					 Store.insert("ordersToProcess");
					 break;
				 case 2: 
					 System.out.println(print_menu.printTime() + "Removing the order");
					 Store.remove("ordersToProcess");
					 break;
				 case 3:
					 System.out.println(print_menu.printTime() + "Modifying the order");
					 Store.modify("ordersToProcess");
					 break;
				 case 4:
					 System.out.println(print_menu.printTime() + "Searching the order");
					 Store.search("ordersToProcess");
					 break;
				 case 5:
					 Store.print("ordersToProcess");
					 break;
				 case 6:
					try {
						Store.processOrders();
					}catch (ProcessOrdersException e) {
						e.printStackTrace();
					}
					 break;
				 }
				 
				 break;
				 
			 case 4:
				 int case4 = print_menu.printManage("order");
				 
				 switch(case4) {
				 
				 case 0:
					 break;
					 
				 case 1:
					 System.out.println(print_menu.printTime() + "Inserting new order");
					 Store.insert("ordersProcessed");
					 break;
				 case 2: 
					 System.out.println(print_menu.printTime() + "Removing the order");
					 Store.remove("ordersProcessed");
					 break;
				 case 3:
					 System.out.println(print_menu.printTime() + "Modifying the order");
					 Store.modify("ordersProcessed");
					 break;
				 case 4:
					 System.out.println(print_menu.printTime() + "Searching the order");
					 Store.search("ordersProcessed");
					 break;
				 case 5:
					 Store.print("ordersProcessed");
					 break;
				 }

				 break;
				 
			 case 5:
				 int case5 = print_menu.printManage("client");
				 
				 switch(case5) {
				 
				 case 0:
					 break;
					 
				 case 1:
					 System.out.println(print_menu.printTime() + "Inserting new client");
					 Store.insert("storeCustomers");
					 break;
				 case 2: 
					 System.out.println(print_menu.printTime() + "Removing the client");
					 Store.remove("storeCustomers");
					 break;
				 case 3:
					 System.out.println(print_menu.printTime() + "Modifying the client");
					 Store.modify("storeCustomers");
					 break;
				 case 4:
					 System.out.println(print_menu.printTime() + "Searching the client");
					 Store.search("storeCustomers");
					 break;
				 case 5:
					 Store.print("storeCustomers");
					 break;
				 }

				 break;
				 
			 case 6:
				 int case6 = print_menu.printManage("provider");
				 
				 switch(case6) {
				 
				 case 0:
					 break;
					 
				 case 1:
					 System.out.println(print_menu.printTime() + "Inserting new provider");
					 Store.insert("storeProviders");
					 break;
				 case 2: 
					 System.out.println(print_menu.printTime() + "Removing the provider");
					 Store.remove("storeProviders");
					 break;
				 case 3:
					 System.out.println(print_menu.printTime() + "Modifying the provider");
					 Store.modify("storeProviders");
					 break;
				 case 4:
					 System.out.println(print_menu.printTime() + "Searching the provider");
					 Store.search("storeProviders");
					 break;
				 case 5:
					 Store.print("storeProviders");
					 break;
				 }

				 break;
				 
			 case 7:
				 int case7 = print_menu.printManage("employee");
				 
				 switch(case7) {
				 
				 case 0:
					 break;
					 
				 case 1:
					 System.out.println(print_menu.printTime() + "Inserting new employee");
					 Store.insert("storeEmployees");
					 break;
				 case 2: 
					 System.out.println(print_menu.printTime() + "Removing the employee");
					 Store.remove("storeEmployees");
					 break;
				 case 3:
					 System.out.println(print_menu.printTime() + "Modifying the employee");
					 Store.modify("storeEmployees");
					 break;
				 case 4:
					 System.out.println(print_menu.printTime() + "Searching the employee");
					 Store.search("storeEmployees");
					 break;
				 case 5:
					 Store.print("storeEmployees");
					 break;
				 }

				 break;
				 
			 case 8:
				 
				 System.out.println(print_menu.printTime());
				 Store.print();
				 
				 break;
				 
			 case 9:
				 
				 System.out.println(print_menu.printTime() + "Testing the program");
				 Store = new StoreManager(
						 "Testing", 
						 ".\\src\\storeName\\stock.txt", 
						 ".\\src\\storeName\\ordersToProcess", 
						 ".\\src\\storeName\\ordersProcessed", 
						 ".\\src\\storeName\\storeCustomers.txt", 
						 ".\\src\\storeName\\storeProviders.txt", 
						 ".\\src\\storeName\\storeEmployees.txt");
				 
				 /* 	
				  * 	Here we used relative paths, but absolute ones work too.
				  * 	We just used this option to charge the routes fastly, instead of 
				  * 	using the first option of the menu (Create Store), which would had 
				  * 	required to introduce the routes all the time while testing the program.
				  * 
				  */
				 
				 break;
				 
			 case 0:
				 String case0;
				 
					do {
						System.out.println(print_menu.printTime() + "Are you sure you want to exit? [Y/N]:");
						case0 = new Scanner(System.in).nextLine();
						if(!(case0.equals("Y") || case0.equals("N"))) 
							System.err.println(print_menu.printTime() + "Invalid input. Try again.");
					}while(!(case0.equals("Y") || case0.equals("N")));
					
					if(case0.equals("Y")) return;
				 
				 break;
				 
			}
			
		}while(switch_number != -1);
		
	}
}
