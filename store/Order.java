package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Order extends ProductList {
	
	private int orderID;
	private Person client;
	private Person employee;
	public static int counter = 0;
	
	public Order() {}
	
	public Order(ArrayList<StockableProduct> list,  
			Person client, Person employee) {
		
		super(list);
		this.orderID = counter;
		this.client = client;
		this.employee = employee;
		counter++;
	}
	
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Person getClient() {
		return client;
	}

	public void setClient(Person client) {
		this.client = client;
	}

	public Person getEmployee() {
		return employee;
	}

	public void setEmployee(Person employee) {
		this.employee = employee;
	}
	
	public void set(String[] data) {	
		super.set(data);
	}

	public String toString() {
		return "Order " + Integer.toString(orderID) + "\n" + 
				super.toString() + "|\n" + client +  
				employee + "\n\n";
		
		/* list + "\\|" + 
				totalCost + "\\|" + totalPrice + 
				"\\|" + totalBenefit
		*/
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public Order readFromStdio() {	//Reviewed
		
		ProductList newList = super.readFromStdio();
		
		Person newClient = new Person();
		Person newEmployee = new Person();
		
		
		System.out.println("Client data : ");
		newClient = newClient.readFromStdio();
		System.out.println("Employee data : ");
		newEmployee = newEmployee.readFromStdio();
		
		return new Order(newList.getList(), newClient, newEmployee);
		
	}
	
	public Order readFromFile(String File) {	//Reviewed (check directories)
		//System.out.println("DEBUG FILE QUE ENTRA : " + File);
		String[] fileStringArray = File.split("\\\\");	// .split("/");
		String fileString = fileStringArray[fileStringArray.length - 1];
		//System.out.println("DEBUG FILESTRING : " + fileString);
		String extensionOut[] = fileString.split("\\.");
		//System.out.println("EXTENSION OUT : " + extensionOut[0] + "|" + extensionOut[1]);
		String parts[] = extensionOut[0].split("_");
		//System.out.println("DEBUG : " + parts[0] + "|" + parts[1] + "|" + parts[2]);
		
		//Vamos a tener que comparar los objetos del array con el identificador de person y provider
		
		ProductList readList = new ProductList();
		readList = readList.readFromFile(File);
		
		Person readClient = new Person();
		Person[] newClient = readClient.readFromFile("./src/storeName/storeCustomers.txt");
		for(Person client:newClient) {
			if(client.getId() == Integer.parseInt(parts[1])) readClient = client;
		}
		
		Person readEmployee = new Person();
		Person[] newEmployee = readEmployee.readFromFile("./src/storeName/storeEmployees.txt");
		for(Person employee:newEmployee) {
			if(employee.getId() == Integer.parseInt(parts[2])) readEmployee = employee;
		}

		Order newOrder = new Order(readList.getList(), readClient, readEmployee);
		newOrder.setOrderID(Integer.parseInt(parts[0]));
		
		return newOrder;
	}
	
	public void writeToFile() {	//Reviewed (check directories)
		
		String File = this.getOrderID() + "_" + this.getClient().getId() + 
				"_" + this.getEmployee().getId() + ".csv";
		
		ProductList writeList = new ProductList(this.getList());
		writeList.writeToFile(File);
	}
	
		
}
