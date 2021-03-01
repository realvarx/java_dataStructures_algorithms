package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StockableProduct extends Product implements Comparable<Object> {
	
	private int productID;
	private int numUnits;
	private double costPerUnit;
	private double pricePerUnit;
	private Provider productProvider;
	private static int counter = 1;
	
	public StockableProduct() {}
	
	public StockableProduct(String name, String brand, char category,
			boolean isCountable, String measurementUnits, int numUnits, 
			double costPerUnit, double pricePerUnit, Provider productProvider) {
		
		super(name, brand, category, isCountable, measurementUnits);
		this.productID = counter;
		this.numUnits = numUnits;
		this.costPerUnit = costPerUnit;
		this.pricePerUnit = pricePerUnit;
		this.productProvider = productProvider;
		counter++;
	}
	
	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getNumUnits() {
		return numUnits;
	}

	public void setNumUnits(int numUnits) {
		this.numUnits = numUnits;
	}

	public double getCostPerUnit() {
		return costPerUnit;
	}

	public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	
	public Provider getProductProvider() {
		return this.productProvider;
	}
	
	public void setProductProvider(Provider productProvider) {
		this.productProvider = productProvider;
	}
	
	public void set(String[] data) {	
		
		super.set(data);
		setNumUnits(Integer.parseInt(data[5])); 
		setCostPerUnit(Double.parseDouble(data[6]));
		setPricePerUnit(Double.parseDouble(data[7]));
		setProductProvider(new Provider(Integer.parseInt(data[8]), data[9], data[10], new Person(
								Integer.parseInt(data[11]), data[12], data[13], data[14])));
		
	}
	
	public String toString() {	//Reviewed
		
		return  productID + "|" + super.toString() + "|" + numUnits + "|" + 
				costPerUnit + "|" + pricePerUnit + "|" + 
				productProvider.getVat() + "|" + 
				productProvider.getName() + "|" + 
				productProvider.getTaxAddress() + "|" + 
				productProvider.getContactPerson().getId() + "|" + 
				productProvider.getContactPerson().getFirstName() + "|" + 
				productProvider.getContactPerson().getLastName() + "|" + 
				productProvider.getContactPerson().getEmail();
		
		/* 	productName|productBrand|category|isCountable|measurement
			Units|numUnits|costPerUnit|pricePerUnit|providerVat|provi
			derName|providerAddress|contactVat|contactFirstName|cont
			actLastName|contactEmail 
		*/
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

			wbuffer.newLine();
			wbuffer.write(toString());
			
			/* 	productName|productBrand|category|isCountable|measurement
			Units|numUnits|costPerUnit|pricePerUnit|providerVat|provi
			derName|providerAddress|contactId|contactFirstName|cont
			actLastName|contactEmail 
		*/

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

	
	public StockableProduct readFromStdio() {	//Reviewed (check directories)
		
		System.out.println("Introduce the data of the new StockableProduct :\n");
		int numComma = 0;
		String readFSP = null;

		do {

			System.out.println("Usage : "
					+ "name|brand|category|isCountable|measurementUnits|"
					+ "numUnits|costPerUnit|pricePerUnit");

			readFSP = new Scanner(System.in).nextLine();
			char[] commaArray = readFSP.toCharArray();

			for(int i = 0; i<commaArray.length; i++) 
				if(commaArray[i] == '|') numComma++;
			if(numComma != 7 || numComma == 0 || numComma == 1) 
				System.err.println("Error : Invalid input.");

		}while(numComma != 7 || numComma == 0 || numComma == 1);

		String[] parts = readFSP.split("\\|");

		//Provider productProvider = new Provider().readFromStdio();
		
		StockableProduct newProduct = new StockableProduct(parts[0], parts[1], 
				parts[2].charAt(0), Boolean.valueOf(parts[3]), parts[4], 
				Integer.parseInt(parts[5]), Double.parseDouble(parts[6]), 
				Double.parseDouble(parts[7]), new Provider().readFromStdio());

		return newProduct;
	}

	
	public StockableProduct[] readFromFile(String File) {	//Reviewed (check directories)
				
		Scanner fileScan = new Scanner(System.in);
		String fileName = fileScan.nextLine();
		
		ArrayList<StockableProduct> arrayListProduct = 
				new ArrayList<StockableProduct>();
		
		File fileR = new File(fileName);
		Scanner scanRFP = null;
		
		try {
			
			if(!(fileR.exists())) {
				System.err.println("Error : File not found.");
				return null;
			}
			
			scanRFP = new Scanner(fileR);
		
			while(scanRFP.hasNextLine()) {
			
				String rbuffer = scanRFP.nextLine();
				String[] parts = rbuffer.split("\\|");
			
				StockableProduct newProduct = new StockableProduct(parts[0], parts[1], 
						parts[2].charAt(0), Boolean.valueOf(parts[3]), parts[4], 
						Integer.parseInt(parts[5]), Double.parseDouble(parts[6]), 
						Double.parseDouble(parts[7]), new Provider(
								Integer.parseInt(parts[8]), parts[9], parts[10], new Person(
										Integer.parseInt(parts[11]), parts[12], parts[13], parts[14])));
			
				arrayListProduct.add(newProduct);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanRFP != null) scanRFP.close();
		}
	          
		StockableProduct[] arrayProduct = arrayListProduct.toArray(
				new StockableProduct[arrayListProduct.size()]);
		
		return arrayProduct;
	}
	
	public boolean equals(StockableProduct sp) {
		return (getProductID() == sp.getProductID())?true:false;
	}
	
	public double computeBenefit() {
		return getNumUnits()*
				(getPricePerUnit() - getCostPerUnit());
	}

	public int compareTo(Object sp) {
		
		//Recursive compareTo() calls for a more specific sorting if needed
		try {
			if(computeBenefit() > ((StockableProduct) sp).computeBenefit()) return 1;
			if(computeBenefit() < ((StockableProduct ) sp).computeBenefit()) return -1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			System.err.println("The parameter of the compareTo() method should be "
					+ "a StockableProduct");
		}
		return 0;
	}
	
}
