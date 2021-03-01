/*
 * 
 * @author Álvaro Merino Argumánez
 * 
 * @version 2
 */


package store;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import dataStructures.*;

public class StoreManager implements classModelling{
	
	private String name;
	private double stockCost;
	private double stockBenefit;
	
	private ProductList stock = new ProductList();
	private LinkedQueue<Order> ordersToProcess = new LinkedQueue<Order>();
	private LinkedList<Order> ordersProcessed = new LinkedList<Order>();
	private LBSTree<Person> storeCustomers = new LBSTree<Person>();
	private LBSTree<Provider> storeProviders = new LBSTree<Provider>();
	private LBSTree<Person> storeEmployees = new LBSTree<Person>();
	private String[] storeDataInfo = new String[7];
	
	public StoreManager() {
		this.name = "-";
		this.stockCost = 0.0;
		this.stockBenefit = 0.0;
	}
	
	public StoreManager(String name, double stockCost, 
			double stockBenefit, ProductList stock, 
			LinkedQueue<Order> ordersToProcess, 
			LinkedList<Order> ordersProcessed, 
			LBSTree<Person> storeCustomers, 
			LBSTree<Provider> storeProviders,
			LBSTree<Person> storeEmployees, 
			String[] storeDataInfo) {
		
		this.name = name;
		this.stockCost = stockCost;
		this.stockBenefit = stockBenefit;
		this.stock = stock;
	}
	
	public StoreManager(String name, String stock, String ordersToProcess, 
			String ordersProcessed, String storeCustomers, String storeProviders, 
			String storeEmployees) {
		
		genericSet(0, name);
		genericSet(1, stock);
		genericSet(2, ordersToProcess);
		genericSet(3, ordersProcessed);
		genericSet(4, storeCustomers);
		genericSet(5, storeProviders);
		genericSet(6, storeEmployees);
		
		this.stockCost = this.stock.getTotalCost();
		this.stockBenefit = this.stock.getTotalBenefit();
	}
	
	/**
	 * The purpose of this method is to evade having the code 
	 * relative to the additional sets separated. The function 
	 * will be called from those additional sets reducing the 
	 * lines of code (as the majority of the method is 
	 * programmed here).
	 * 
	 * 0-name of the store, 1-stock.txt, 2-ordersToProcess, 3-ordersProcessed, 
	 * 4-storeCustomers.txt, 5-storeProviders.txt, 6-storeEmployees.txt.
	 * 
	 * @param numStructure Determines the behavior of the method depending on
	 * the integer number introduced (numbers reference described above).
	 * 
	 * @param directory	The directory that will be analyzed.
	 */
	
	public void genericSet(int numStructure, String directory) {
		this.storeDataInfo[numStructure] = directory;
				
		switch(numStructure) {
		
		case 0:
			
			this.name = directory;
			break;
			
		case 1:
			
			ProductList newStock = new ProductList();
			this.stock = newStock.readFromFile(directory);
			break;
			
		case 2:
		case 3:	
			
			if(numStructure == 2) {
				ArrayList<Order> newOrdersTP = readOrdersFromFolder(directory);
				for(Order order:newOrdersTP) this.ordersToProcess.enqueue(order);
			}
			
			if(numStructure == 3) {
				ArrayList<Order> newOrdersP = readOrdersFromFolder(directory);
				for(Order order:newOrdersP) this.ordersProcessed.insert(order);
			}
			break;
			
		case 4:
		case 6:
			
				ArrayList<Person> newPersons = readPersonsFromFile(directory);
				for(Person person:newPersons) {
					if(numStructure == 4) storeCustomers.insert(person, person);
					if(numStructure == 6) storeEmployees.insert(person, person);
				}
			
			break;
			
		case 5:
			
			ArrayList<Provider> newProviders = readProvidersFromFile(directory);
			for(Provider provider:newProviders) storeProviders.insert(provider, provider);
			
			break;
			
		default:
			System.err.println("Invalid structure number");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getStockCost() {
		return stockCost;
	}

	public void setStockCost(double stockCost) {
		this.stockCost = stockCost;
	}

	public double getStockBenefit() {
		return stockBenefit;
	}

	public void setStockBenefit(double stockBenefit) {
		this.stockBenefit = stockBenefit;
	}

	public ProductList getStock() {
		return stock;
	}

	public void setStock(ProductList stock) {
		this.stock = stock;
	}

	public LinkedQueue<Order> getOrdersToProcess() {
		return ordersToProcess;
	}

	public void setOrdersToProcess(LinkedQueue<Order> ordersToProcess) {
		this.ordersToProcess = ordersToProcess;
	}

	public LinkedList<Order> getOrdersProcessed() {
		return ordersProcessed;
	}

	public void setOrdersProcessed(LinkedList<Order> ordersProcessed) {
		this.ordersProcessed = ordersProcessed;
	}

	public LBSTree<Person> getStoreCustomers() {
		return storeCustomers;
	}

	public void setStoreCustomers(LBSTree<Person> storeCustomers) {
		this.storeCustomers = storeCustomers;
	}

	public LBSTree<Provider> getStoreProviders() {
		return storeProviders;
	}

	public void setStoreProviders(LBSTree<Provider> storeProviders) {
		this.storeProviders = storeProviders;
	}

	public LBSTree<Person> getStoreEmployees() {
		return storeEmployees;
	}

	public void setStoreEmployees(LBSTree<Person> storeEmployees) {
		this.storeEmployees = storeEmployees;
	}

	public String[] getStoreDataInfo() {
		return storeDataInfo;
	}

	public void setStoreDataInfo(String[] storeDataInfo) {
		this.storeDataInfo = storeDataInfo;
	}

	public void set(String[] data) {		
	}
	
	public String toString() {
		return "|" + storeDataInfo[0] + "|\n|" + storeDataInfo[1] + 
				"|\n|" + storeDataInfo[2] + "|\n|" + storeDataInfo[3] + 
				"|\n|" + storeDataInfo[4] + "|\n|" + storeDataInfo[5] + 
				"|\n|" + storeDataInfo[6] + "|";
	}

	public void print() {	//Edit in print_menu
		
		if(this.storeDataInfo[1] != null) {
			this.stockCost = this.stock.calculateCost();
			this.stockBenefit = this.stock.calculateBenefit();
		}
		
		System.out.println(toString() + "\nStockCost : " + stockCost 
				+ "\nPotential benefit : " + stockBenefit);
	}

	public StoreManager readFromStdio() {	
		
		String[] newSM = new String[7];
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Introduce the name of the store : \n");
		newSM[0] = scanner.nextLine();
		
		String[] scannerNames = {"'stock'", "'ordersToProcess'", "'ordersProcessed'", 
				"'storeCustomers'", "'storeProviders'", "'storeEmployees'"};
		
		for(int i = 1; i<scannerNames.length+1; i++) {
			System.out.println("Introduce the path of the " + scannerNames[i-1] + " : \n");
			newSM[i] = scanner.nextLine();
		}
		
		StoreManager newStoreManager = 
				new StoreManager(newSM[0], newSM[1], newSM[2], newSM[3], newSM[4], 
				newSM[5], newSM[6]);
		
		return newStoreManager;
	}
	
	public ArrayList<Person> readPersonsFromFile(String file) {
		Person buffer = new Person();
		Person[] personArray = buffer.readFromFile(file);
		ArrayList<Person> personList = new ArrayList<Person>();
		for(Person person:personArray) personList.add(person);
		return personList;
	}
	
	public ArrayList<Provider> readProvidersFromFile(String file) {
		Provider buffer = new Provider();
		Provider[] providerArray = buffer.readFromFile(file);
		ArrayList<Provider> providerList = new ArrayList<Provider>();
		for(Provider provider:providerArray) providerList.add(provider);
		return providerList;
	}
	
	public static ArrayList<Order> readOrdersFromFolder(String folder) {
		ArrayList<Order> listOrder = new ArrayList<Order>();
		File f = new File(folder);
		File[] fileList = null;
		if(f.isDirectory()) {
			fileList = f.listFiles();
			for(File file:fileList) {
				Order buffer = new Order();
				buffer = buffer.readFromFile(folder + "\\" +  file.getName());
				listOrder.add(buffer);
			}
		}else {
			System.err.println("Invalid directory");
		}
		return listOrder;
	}
	
	public void insert(String context) {
		System.out.println("Inserting " + context);
		switch(context) {
		
		case "stock":
			StockableProduct newSP = new StockableProduct();
			newSP = newSP.readFromStdio();
			this.stock.getList().add(newSP);
			break;
			
		case "ordersToProcess":
		case "ordersProcessed":
			Order newOrder = new Order();
			newOrder = newOrder.readFromStdio();
			if(context.equals("ordersToProcess")) this.ordersToProcess.enqueue(newOrder);
			if(context.equals("ordersProcessed")) this.ordersProcessed.insert(newOrder);
			break;
			
		case "storeCustomers":
		case "storeEmployees":
			Person newPerson = new Person();
			newPerson = newPerson.readFromStdio();
			if(context.equals("storeCustomers")) this.storeCustomers.insert(newPerson, newPerson);
			if(context.equals("storeEmployees")) this.storeEmployees.insert(newPerson, newPerson);
			break;
			
		case "storeProviders":
			Provider newProvider = new Provider();
			newProvider = newProvider.readFromStdio();
			if(context.equals("storeProviders")) this.storeProviders.insert(newProvider, newProvider);
			break;
		
		default:
			System.err.println("Invalid context");
		}
	}
	
	public Object search(String context) {
		System.out.println("Searching " + context + "\nIntroduce the unique identifier "
				+ "of the element to be searched : ");
		String identifier = new Scanner(System.in).nextLine();
		
		switch(context) {
		
		case "stock":
			/*for(StockableProduct sp:this.stock.getList()) {
				if(sp.getProductID() == Integer.parseInt(identifier)) 
					return sp;
			}
			return null;*/
			StockableProduct searchStock = this.stock.search(Integer.parseInt(identifier));
			if(searchStock != null) searchStock.print();
			return searchStock;
			
		case "ordersToProcess":
			LinkedQueue<Order> auxLQ = ordersToProcess;			
			for(int i = 0; i<this.ordersToProcess.size(); i++) {
				if(auxLQ.front().getOrderID() == Integer.parseInt(identifier)) {
					auxLQ.front().print();
					return auxLQ.front();
				}
				auxLQ.dequeue();
			}
			return null;
			
		case "ordersProcessed":
			Node<Order> aux = ordersProcessed.getFirst();
			for(int i=0; i<ordersProcessed.size() && aux.getNext()!=null; i++) {
				if(aux.getInfo().getOrderID() == Integer.parseInt(identifier)) {
					aux.getInfo().print();
					return (Order) aux.getInfo();
				}
				aux = aux.getNext();
			}
			return null;
			
		case "storeCustomers":
			Person customer = new Person();
			customer.setId(Integer.parseInt(identifier));
			customer = storeCustomers.search(customer).getInfo();
			customer.print();
			return customer;

		case "storeProviders":
			Provider provider = new Provider();
			provider.setVat(Integer.parseInt(identifier));
			if(storeProviders.search(provider) != null) {
				provider = storeProviders.search(provider).getInfo();
				provider.print();
				return provider;
			}else {
				System.out.println("Provider not found");
				return null;
			}
			

		case "storeEmployees":
			Person employee = new Person();
			employee.setId(Integer.parseInt(identifier));
			employee = storeEmployees.search(employee).getInfo();
			employee.print();
			return employee;
			
		default:
			System.err.println("Invalid context");
			return null;
		}
		
	}
	
	public Object remove(String context) {
		System.out.println("Removing " + context + "\nIntroduce the unique identifier "
				+ "of the element to be searched : ");
		String identifier = new Scanner(System.in).nextLine();
		
		switch(context) {
		
		case "stock":
			System.out.println("How many units would you like to remove? : ");
			int numUnits = new Scanner(System.in).nextInt();
			/*if(stock.getList().indexOf(search(identifier)) != -1)
				return stock.getList().remove(stock.getList().indexOf(search(identifier)));
			else return null;*/
			StockableProduct temp = stock.remove(Integer.parseInt(identifier), numUnits);
			this.stockCost = this.stock.calculateCost();
			this.stockBenefit = this.stock.calculateBenefit();
			
			return temp;
			
		case "ordersToProcess":
			LinkedQueue<Order> newLQ = new LinkedQueue<Order>();
			LinkedQueue<Order> aux = this.ordersToProcess;
			Order auxOrder = new Order();
			Order removedOrder = null;
			for(int i = 0; i<aux.size(); i++) {
				auxOrder = aux.dequeue();
				if(auxOrder.getOrderID() != Integer.parseInt(identifier)) newLQ.enqueue(auxOrder);
				if(auxOrder.getOrderID() == Integer.parseInt(identifier)) removedOrder = auxOrder;
			}
			this.ordersToProcess = newLQ;
			return removedOrder;
			
		case "ordersProcessed":
			for(int i = 0; i<ordersProcessed.size(); i++) {
				if(ordersProcessed.get(i).getOrderID() == Integer.parseInt(identifier)) {
					return ordersProcessed.extract(ordersProcessed.searchNode(ordersProcessed.get(i-1)));
				}
					
			}
			return null;
			
		case "storeCustomers":
		case "storeProviders":
		case "storeEmployees":
			System.err.println(context + " : removing " + context.substring(5) + 
					" from Store is not allowed");
			return null;
			
		default:
			System.err.println("Invalid context");
			return null;
		}
		
	}
	
	public void modify(String context) {
		System.out.println("Modifying " + context + "\nIntroduce the unique identifier "
				+ "of the element to be searched : ");
		String identifier = new Scanner(System.in).nextLine();
		
		switch(context) {
		
		case "stock":
			this.stock.modify(Integer.parseInt(identifier));
			break;
			
		case "ordersToProcess":
			LinkedQueue<Order> newLQ = new LinkedQueue<Order>();
			LinkedQueue<Order> aux = this.ordersToProcess;
			Order auxOrder = new Order();
			
			for(int i = 0; i<aux.size(); i++) {
				auxOrder = aux.dequeue();
				if(auxOrder.getOrderID() != Integer.parseInt(identifier)) newLQ.enqueue(auxOrder);
				if(auxOrder.getOrderID() == Integer.parseInt(identifier)) {
					auxOrder = auxOrder.readFromStdio();
					newLQ.enqueue(auxOrder);
				}
			}
			this.ordersToProcess = newLQ;
			break;
			
		case "ordersProcessed":	
			
			Node<Order> nodeAux = ordersProcessed.getFirst();
			Node<Order> prev = ordersProcessed.getFirst();
			
			if(ordersProcessed.size() == 0) {
				System.err.println("Empty list");
				return;
			}
			else if(ordersProcessed.size() == 1) {
				if(nodeAux.getInfo().getOrderID() == Integer.parseInt(identifier)) {
					ordersProcessed.extract();
					Order newFirst = new Order();
					newFirst = newFirst.readFromStdio();
					ordersProcessed.insert(newFirst);
					return;
				}
			}else {
				for(int i = 0; i<ordersProcessed.size() && nodeAux.getNext()!= null; i++) {
					if(nodeAux.getNext().getInfo().getOrderID() == Integer.parseInt(identifier)) {
						prev = nodeAux;
						int buffIndex = ordersProcessed.indexOf(prev.getNext().getInfo());
						ordersProcessed.extract(buffIndex);
						Order newOrder = new Order();
						newOrder = newOrder.readFromStdio();
						ordersProcessed.insert(newOrder, prev);
						return;
					}else {
						nodeAux = nodeAux.getNext();
					}
				}
			}
			
			/*int numIndex = ordersProcessed.indexOf((Order) search(identifier)); 
			if(numIndex != -1) {
				ordersProcessed.extract(numIndex);
				Order newOrder = new Order();
				newOrder = newOrder.readFromStdio();
				if(ordersProcessed.size() > 1) {
					Order prevInfo = ordersProcessed.get(numIndex-1);
					Node<Order> prev = ordersProcessed.searchNode(prevInfo);
					ordersProcessed.insert(newOrder, prev);
				}
				else if(ordersProcessed.size() == 1) {
					ordersProcessed.extract(0);
					ordersProcessed.insert(newOrder);
				}

			}else {
				System.err.println("Item not found");
				if(ordersProcessed.size() == 0) System.err.println("Empty list");
			}*/
			
			break;			
			
		case "storeCustomers":
		case "storeProviders":
		case "storeEmployees":
			System.err.println(context + " : modifying " + context.substring(5) + 
					" from Store is not allowed");
			break;
			
		default:
			System.err.println("Invalid context");
			break;
		}
		
	}
	
	//Recuerda que los StockableProduct llevan asociado el Provider, y los Order el Employee
	
	public void processOrders() throws ProcessOrdersException {
		
		while(ordersToProcess.front() != null) {
			
			boolean flag = false;
			Order currentOrder = ordersToProcess.front();
			StockableProduct currentProductStock, currentProductOrder = new StockableProduct();
			ProductList auxStock = this.stock;
			
			/*
			 * Necesitamos un aux del Stock para no irle quitando unidades al de verdad, imagina 
			 * que el for no pasa correctamente por todos los objetos del currentOrder, habríamos 
			 * restado unidades a un Order que no se habría completado. Igualaremos el Stock de 
			 * verdad al auxiliar cuando hayamos terminado de iterar completamente por los productos.
			 */
			
			try {
			
				for(int i = 0; i<currentOrder.getList().size(); i++) {

					currentProductStock = this.stock.search(currentOrder.getList().get(i).getProductID());
					currentProductOrder = currentOrder.getList().get(i);

					if(currentProductStock != null && currentProductStock.getNumUnits()
							>= currentProductOrder.getNumUnits() && (storeProviders.search(
									currentProductOrder.getProductProvider()) != null) && 
									storeEmployees.search(currentOrder.getEmployee()) != null) {

						//Le vamos borrando las unidades al auxiliar, sin tocar el de verdad
						System.out.println("Ha entrado a borrar!");

						auxStock.remove(currentProductStock.getProductID(), currentProductOrder.getNumUnits());
							

					}else {
							
						flag = true;
						if(!ordersToProcess.isEmpty()) ordersToProcess.dequeue();
						throw new ProcessOrdersException();
							
					}
				}
			
			}catch(ProcessOrdersException e) {
				e.printStackTrace();
			}
			
				//Si ha saltado la excepción, volvemos al while con el siguiente elemento
				
			if(flag) continue; 
			
			/*
			 * Aquí todas las unidades ya han iterado sin problema, por tanto añadimos el cliente si 
			 * no estaba en storeCustomers, cambiamos el Stock por el auxiliar, y movemos el Order que 
			 * estábamos analizando de ordersToProcess a ordersProcessed. Por último, actualizamos 
			 * los costes y beneficios llamando a la función.
			 */
			
			if(storeCustomers.search(currentOrder.getClient()) == null) 
				storeCustomers.insert(currentOrder.getClient(), currentOrder.getClient());
			
			this.stock = auxStock;
			this.stockCost = this.stock.calculateCost();
			this.stockBenefit = this.stock.calculateBenefit();
			
			if(!ordersToProcess.isEmpty()) ordersProcessed.insert(ordersToProcess.dequeue());
		}
		
	}
	
	public void print(String context) {
		System.out.println("Printing " + context + "\n");
		switch(context) {
		
		case "stock":
			if(this.storeDataInfo[1] != null) this.stock.print();
			break;
		case "ordersToProcess":
			if(this.storeDataInfo[2] != null) this.ordersToProcess.print();
			break;
		case "ordersProcessed":
			if(this.storeDataInfo[3] != null) this.ordersProcessed.print();
			break;
		case "storeCustomers":
			if(this.storeDataInfo[4] != null) this.storeCustomers.print();
			break;

		case "storeProviders":
			if(this.storeDataInfo[5] != null) this.storeProviders.print();
			break;

		case "storeEmployees":
			if(this.storeDataInfo[6] != null) this.storeEmployees.print();
			break;
			
		default:
			System.err.println("Invalid context");
			break;
		
		}
	}
}
