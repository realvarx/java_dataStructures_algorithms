package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Product implements classModelling{
	
	protected String name;
	protected String brand;
	protected char category;
	protected boolean isCountable;
	protected String measurementUnits;
	
	public Product() {}
	
	public Product(String name, String brand, char category, 
			boolean isCountable, String measurementUnits) {
		
		this.name = name;
		this.brand = brand;
		setCategory(category);
		this.isCountable = isCountable;
		this.measurementUnits = measurementUnits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public char getCategory() {
		return category;
	}

	public void setCategory(char category) {
		if((category == 'f') || 
				(category == 's') || 
				(category == 'e') ||
				(category == 'm'))this.category = category;
		else {
			System.err.println("Category couldn't be added in "
					+ "one or more products(Invalid category)");
			this.category = ' ';
		}
	}

	public boolean isCountable() {
		return isCountable;
	}

	public void setCountable(boolean isCountable) {
		this.isCountable = isCountable;
	}

	public String getMeasurementUnits() {
		return measurementUnits;
	}

	public void setMeasurementUnits(String measurementUnits) {
		this.measurementUnits = measurementUnits;
	}

	public void set(String[] data) {
		
		setName(data[0]);
		setBrand(data[1]);
		setCategory(data[2].charAt(0));
		setCountable(Boolean.valueOf(data[3]));
		setMeasurementUnits(data[4]);
		
	}
	
	public String toString() {
		return name + "|" + brand + "|" + category + 
				"|" + Boolean.toString(isCountable) + 
				"|" + measurementUnits;
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public void writeToFile(String File) {	
		
		/*System.out.println("Introduce the name of the file (without any extension) :\n" + 
					"(If the file specified doesn't exist, "
						+ "a new file with that name will be created)");*/
		
		String fileName = "./src/ProductFiles/" + File;
		
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
		
		Product p = new Product(this.name, this.brand, 
				this.category, this.isCountable, this.measurementUnits);
		
		wbuffer.write(p.name + "," + p.brand + "," + p.category 
				+ "," + p.isCountable + "," + p.measurementUnits);
		wbuffer.newLine();
		wbuffer.flush();
		
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
	
	public Product readFromStdio() {
		
		System.out.println("Introduce the data of the new Product :\n" + 
						"Usage : " + "name,brand,category,isCountable,"
						+ "measurementUnits");
		int numComma = 0;
		String readFSP = null;
		
		do {
			
		System.out.println("Usage : "
				+ "name,brand,category,isCountable,measurementUnits");
		
		readFSP = new Scanner(System.in).nextLine();
		char[] commaArray = readFSP.toCharArray();
		
		for(int i = 0; i<commaArray.length; i++) 
			if(commaArray[i] == ',') numComma++;
		if(numComma != 4 || numComma == 0 || numComma == 1) 
			System.err.println("Error : Invalid input.");
		
		}while(numComma != 4 || numComma == 0 || numComma == 1);
		
		String[] parts = readFSP.split(",");
		
		Product newProduct = new Product(parts[0], parts[1], 
				parts[2].charAt(0), Boolean.valueOf(parts[3]), parts[4]);
		
		return newProduct;
	}
	
	public Product[] readFromFile(String File) {	//Implement ArrayList (done)
		
		System.out.println("Which file do you want to read from? :");
		
		/*Scanner fileScan = new Scanner(System.in);
		String fileName = fileScan.nextLine();*/
		
		String fileName = "./src/ProductFiles/" + File;
		
		ArrayList<Product> arrayListProduct = new ArrayList<Product>();
		
		File fileR = new File(fileName);
		Scanner scanRFP = null;
		
		try {
			
			if(!(fileR.exists())) {
				System.err.println("Error : File not found.");
				return null;
			}
			
			scanRFP = new Scanner(fileR);	//Implement try/catch (done)
		
			while(scanRFP.hasNextLine()) {
			
				String rbuffer = scanRFP.nextLine();
				String[] parts = rbuffer.split(",");
			
				Product newProduct = new Product(parts[0], parts[1], 
						parts[2].charAt(0), Boolean.valueOf(parts[3]), parts[4]);
			
				arrayListProduct.add(newProduct);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanRFP != null) scanRFP.close();
		}
	          
		Product[] arrayProduct = (Product[]) arrayListProduct.toArray();
		
		return arrayProduct;
	}
}
