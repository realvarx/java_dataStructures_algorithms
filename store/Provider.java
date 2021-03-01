package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Provider implements Comparable<Object>{
	
	private int vat;
	private String name;
	private String taxAddress;
	private Person contactPerson;
	
	public Provider() {
		this(0000, "Not defined", "Not defined", new Person());
	}
	
	public Provider(int vat, String name, String taxAddress, 
			Person contactPerson) {
		this.vat = vat;
		this.name = name;
		this.taxAddress = taxAddress;
		this.contactPerson = contactPerson;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaxAddress() {
		return taxAddress;
	}

	public void setTaxAddress(String taxAddress) {
		this.taxAddress = taxAddress;
	}

	public Person getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(Person contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void set(String[] data) {	
		
		setVat(Integer.parseInt(data[0]));
		setName(data[1]);
		setTaxAddress(data[2]);
		setContactPerson(new Person(Integer.parseInt(data[3]), data[4], data[5], data[6]));
	}
	
	public String toString() {
		return Integer.toString(vat) + "|" + name + 
				"|" + taxAddress + "|" + 
				contactPerson.toString();
	}

	public void print() {
		System.out.println(toString());
	}

	public void writeToFile(String File) {	
		
		/*System.out.println("Introduce the name of the file (without any extension) :\n" + 
					"(If the file specified doesn't exist, "
						+ "a new file with that name will be created)");*/
		
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
		
		Provider p = new Provider(this.vat, this.name, 
				this.taxAddress, this.contactPerson);
		
		wbuffer.write(p.vat + "|" + p.name + "|" + p.taxAddress);
		p.contactPerson.writeToFile("./src/PersonFiles/" + File);
		
		//We store persons in the PersonFiles folder
		
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
	
	public Provider readFromStdio() {
		
		System.out.println("Introduce the data of the new Provider\n"
				+ "(Information about contactPerson will be asked later)");
		int numComma = 0;
		String readFSP = null;
		
		do {
			
		System.out.println("Usage : "
				+ "vat|name|taxAddress");
		
		readFSP = new Scanner(System.in).nextLine();
		char[] commaArray = readFSP.toCharArray();
		
		for(int i = 0; i<commaArray.length; i++) 
			if(commaArray[i] == '|') numComma++;
		if(numComma != 2 || numComma == 0 || numComma == 1) 
			System.err.println("Error : Invalid input.");
		
		}while(numComma != 2 || numComma == 0 || numComma == 1);
		
		String[] parts = readFSP.split("\\|");
		
		Person newContactPerson = new Person();
		newContactPerson = newContactPerson.readFromStdio();
		
		Provider newProvider = new Provider(Integer.parseInt(parts[0]), parts[1], 
				parts[2], newContactPerson);
		
		return newProvider;
	}
	
	public Provider[] readFromFile(String File) {
		
		//System.out.println("Which file do you want to read from? :");
		
		/*Scanner fileScan = new Scanner(System.in);
		String fileName = fileScan.nextLine();*/
		
		String fileName = File;
		
		ArrayList<Provider> arrayListProvider = new ArrayList<Provider>();
		
		File fileR = new File(fileName);
		Scanner scanRFP = null;
		
		try {
			
			if(!(fileR.exists())) {
				System.err.println("Error : File not found (Provider).");
				return null;
			}
			
			scanRFP = new Scanner(fileR);
		
			while(scanRFP.hasNextLine()) {
			
				String rbuffer = scanRFP.nextLine();
				String[] parts = rbuffer.split("\\|");
			
				arrayListProvider.add(new Provider(Integer.parseInt(parts[0]), parts[1], 
						parts[2], new Person(Integer.parseInt(parts[3]), parts[4], parts[5], parts[6])));
	
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanRFP != null) scanRFP.close();
		}
		
		
		Provider[] arrayProvider = arrayListProvider.toArray(
				new Provider[arrayListProvider.size()]);
		
		return arrayProvider;
	}

	public boolean equals(Provider p) {
		return (getVat() == p.getVat())?true:false;
	}
	
	public int compareTo(Object p) {
	
		try {
			if(getVat() > ((Provider) p).getVat()) return 1;
			if(getVat() < ((Provider) p).getVat()) return -1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			System.err.println("The parameter of the compareTo() method should be "
					+ "a Provider");
		}
		return 0;
	}
	
}
