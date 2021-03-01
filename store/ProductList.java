package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class ProductList implements classModelling{
	
	protected ArrayList<StockableProduct> list;
	protected double totalCost;
	protected double totalPrice;
	protected double totalBenefit;
	
	public ProductList() {}
	
	public ProductList(ArrayList<StockableProduct> list) {
		this.list = list;
		this.totalCost = calculateCost();
		this.totalPrice = calculatePrice();
		this.totalBenefit = calculateBenefit();
	}	
	
	/*User shouldn't be allowed to declare totalCost, 
	totalPrice or totalBenefit, as they will be computed by
	the functions below.*/

	public ArrayList<StockableProduct> getList() {
		return list;
	}

	public void setList(ArrayList<StockableProduct> list) {
		this.list = list;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTotalBenefit() {
		return totalBenefit;
	}

	public void setTotalBenefit(double totalBenefit) {
		this.totalBenefit = totalBenefit;
	}

	public void set(String[] data) {	
		
		StockableProduct newProduct = new StockableProduct(data[1], data[2], 
				data[3].charAt(0), Boolean.valueOf(data[4]), data[5], 
				Integer.parseInt(data[6]), Double.parseDouble(data[7]), 
				Double.parseDouble(data[8]), new Provider(
						Integer.parseInt(data[9]), data[10], data[11], new Person(
								Integer.parseInt(data[12]), data[13], data[14], data[15])));
		
		newProduct.setProductID(Integer.parseInt(data[0]));
		
		ArrayList<StockableProduct> list = new ArrayList<StockableProduct>();
		list.add(newProduct);
		
		this.setList(list);
	}

	public String toString() {
		
		String fullList = "";
		
		for(int i = 0; i<list.size() ;i++) {
			fullList += this.list.get(i).toString() + "\n";
		}
		
		return fullList + "|" + totalCost + "|" 
				+ totalPrice + 
				"|" + totalBenefit;
	}
	
	public void print() {
		System.out.println(toString());
	}

	public void writeToFile(String File) {	//Reviewed (check directories)
	
		String fileName = File;

		File fileW = null;
		FileWriter writeWFP = null;
		BufferedWriter wbuffer = null;

		try {

			fileW = new File(fileName);
			if(!(fileW.exists())) {

				fileW.createNewFile();
				System.out.println("A new file has been created");
			}

			writeWFP = new FileWriter(fileW, true);
			wbuffer = new BufferedWriter(writeWFP);
			
			for(int i = 0; i<this.list.size(); i++) {
				
				wbuffer.write(getList().get(i).toString());
				wbuffer.newLine();
				wbuffer.flush();
			}

		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(writeWFP != null) {
				try {
					writeWFP.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public ProductList readFromStdio() {	//Reviewed (check directories)
		
		String contString = "";
		
		ArrayList<StockableProduct> stockableProductList = 
				new ArrayList<StockableProduct>();
		
		do {
		
			System.out.println("Introduce the data of the new ProductList :\n");
			int numComma = 0;
			String readFSP = null;

			do {
				numComma = 0;
				System.out.println("Usage : "
						+ "name|brand|category|isCountable|measurementUnits|"
						+ "numUnits|costPerUnit|pricePerUnit");

				readFSP = new Scanner(System.in).nextLine();
				char[] commaArray = readFSP.toCharArray();

				for(int i = 0; i<commaArray.length; i++) 
					if(commaArray[i] == '|') numComma++;
				if(numComma%7 != 0 || numComma == 0 || numComma == 1) 
					System.err.println("Error : Invalid input.");

			}while(numComma%7 != 0 || numComma == 0 || numComma == 1);

			String[] parts = readFSP.split("\\|");

			StockableProduct newProduct = new StockableProduct(parts[0], parts[1], 
					parts[2].charAt(0), Boolean.valueOf(parts[3]), parts[4], 
					Integer.parseInt(parts[5]), Double.parseDouble(parts[6]), 
					Double.parseDouble(parts[7]), new Provider().readFromStdio());

			stockableProductList.add(newProduct);
			
			System.out.println("The StockableProduct was successfuly "
					+ "added to the new ProductList");

			System.out.println("Press any key to add a new StockableProduct to "
					+ "the new ProductList or introduce 'No' if you dont want to "
					+ "add more products : \n");
			
			contString = new Scanner(System.in).nextLine();
		
		}while(!(contString.equals("No")));
		
		return new ProductList(stockableProductList);
	}
	
	public ProductList readFromFile(String File) {	//Reviewed (check directories)
		
		String fileName = File;
				
		ArrayList<ProductList> listOfLists = 
				new ArrayList<ProductList>();
		
		ArrayList<StockableProduct> bufferList = 
				new ArrayList<StockableProduct>();
		
		ProductList newList = new ProductList();
		
		File fileR = null;
		Scanner scanRFP = null;
		
		try {
			
			fileR = new File(fileName);
			
			if(!(fileR.exists())) {
				System.err.println("Error : File not found (ProductList).");
				return null;
			}
			
			scanRFP = new Scanner(fileR);
		
			while(scanRFP.hasNextLine()) {
				
				String rbuffer = scanRFP.nextLine();
				String[] parts = rbuffer.split("\\|");
			
				StockableProduct newProduct = new StockableProduct(parts[1], parts[2], 
						parts[3].charAt(0), Boolean.valueOf(parts[4]), parts[5], 
						Integer.parseInt(parts[6]), Double.parseDouble(parts[7]), 
						Double.parseDouble(parts[8]), new Provider(
								Integer.parseInt(parts[9]), parts[10], parts[11], new Person(
										Integer.parseInt(parts[12]), parts[13], parts[14], parts[15])));
				
				newProduct.setProductID(Integer.parseInt(parts[0]));
			
				bufferList.add(newProduct);
			}
				
				newList = new ProductList(bufferList);
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanRFP != null) scanRFP.close();
		}
				
		return newList;
	}
	
	public double calculateCost() {
		
		ListIterator<StockableProduct> costIterator = 
				(this.list).listIterator();
		
		StockableProduct costSP;
		double costCount = 0.0;
		
		while(costIterator.hasNext()) {
			costSP = costIterator.next();
			costCount = costCount + costSP.getCostPerUnit() * costSP.getNumUnits();
		}
		
		/* using for loop
		 
		 for(int i=0; i<list.size();i++) {
		 	costCount = costCount + this.list.get(i).getCostPerUnit() 
		 	* costSP.getNumUnits();
		 } 
		 
		 */
		
		return costCount;
	}
	
	public double calculatePrice() {
		
		ListIterator<StockableProduct> priceIterator = 
				(this.list).listIterator();
		
		StockableProduct priceSP;
		double priceCount = 0.0;
		
		while(priceIterator.hasNext()) {
			priceSP = priceIterator.next();
			priceCount = priceCount + 
					(priceSP.getPricePerUnit() * priceSP.getNumUnits());
		}
		
		return priceCount;
	}
	
	public double calculateBenefit() {

		return calculatePrice() - calculateCost();
	}
	
	public StockableProduct mostExpensiveProduct() {
		
		ListIterator<StockableProduct> expensiveIterator = 
				(this.list).listIterator();
		
		StockableProduct expensiveBuffer = null;
		double costCount = 0.0;
		
		while(expensiveIterator.hasNext()) {
			
			if(expensiveIterator.next().getPricePerUnit() > costCount) {
				expensiveBuffer = expensiveIterator.next();
				costCount = expensiveIterator.next().getPricePerUnit();
			}
		}
		
		return expensiveBuffer;
	}
	
	public StockableProduct cheapestProduct() {
		
		ListIterator<StockableProduct> cheapIterator = 
				(this.list).listIterator();
		
		StockableProduct cheapBuffer = null;
		double costCount = 0.0;
		boolean firstIteration = true;
		
		while(cheapIterator.hasNext()) {
			
			if(firstIteration) {
				costCount = cheapIterator.next().getPricePerUnit();
				firstIteration = false;
			}
			
			if(cheapIterator.next().getPricePerUnit() < costCount) {
				cheapBuffer = cheapIterator.next();
				costCount = cheapIterator.next().getPricePerUnit();
			}
		}
		
		return cheapBuffer;
	}
	
	public int indexOf(int productID) {
		for(int i = 0; i<getList().size(); i++) {
			if(getList().get(i).getProductID() == productID) return i;
		}
		return -1;
	}
	
	public StockableProduct search(int productID) {
		for(int i = 0; i<getList().size(); i++) {
			if(getList().get(i).getProductID() == productID) 
				return getList().get(i);
		}
		System.err.println("Item not found");
		return null;
	}
	
	public void insert(StockableProduct other) {
		int buffIndex = indexOf(other.getProductID());
		if(buffIndex != -1) {
			this.getList().get(buffIndex).setNumUnits(
					getList().get(buffIndex).getNumUnits() + other.getNumUnits());
		}else {
			this.getList().add(other);
		}
	}
	
	public StockableProduct remove(int productID, int numUnits) {
		int buffIndex = indexOf(productID);
		if(buffIndex != -1) {
			int newNumUnits = getList().get(buffIndex).getNumUnits() - numUnits;
			getList().get(buffIndex).setNumUnits(newNumUnits);
			if(newNumUnits <= 0) {
				StockableProduct returnDeleted = getList().get(buffIndex);
				getList().remove(buffIndex);
				return returnDeleted;
			}else {
				return getList().get(buffIndex);
			}
			
		}else {
			System.err.println("Item not found");
			return null;
		}
	}
	
	public void modify(int productID) {
		int buffIndex = indexOf(productID);
		if(buffIndex != -1) {
			System.out.println("Item found");
			
			StockableProduct newSP = new StockableProduct();
			newSP = newSP.readFromStdio();
			newSP.setProductID(productID);
			
			getList().remove(buffIndex);
			getList().add(buffIndex, newSP);
		}else {
			System.err.println("Item not found");
		}
		
	}
	
}
