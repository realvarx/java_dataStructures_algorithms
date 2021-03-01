package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Person implements Comparable<Object> {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	
	public Person(){
		this(0000, "Not defined", "Not defined", "xxxx@xxxx.com");
	}
	
	public Person(int id, String firstName, 
			String lastName, String email){
		
		try {
			
			setId(id);
            setFirstName(firstName);
            setLastName(lastName);
            setEmail(email);
			
			char[] emailArray = email.toCharArray();
			int counter = 0;
			for(int i = 0; i<emailArray.length; i++) {
				if (emailArray[i] == '@') counter++;
			}
			
			if(counter == 0) throw new PersonException(
					"Error : email field doesnt contain @");
			
		}catch(PersonException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws PersonException {
		
		if (firstName.length() > 15) {
            throw new PersonException(
            		"The first name must have a maximum of 15 characters");
        } else {
            this.firstName = firstName;
        }
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws PersonException {
		if (lastName.length() > 15) {
            throw new PersonException(
                    "The last name must have a maximum of 15 characters");
        } else {
            this.lastName = lastName;
        }
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void set(String[] data) {
		
		try {
			
			setId(Integer.parseInt(data[0]));
	        setFirstName(data[1]);
	        setLastName(data[2]);
	        setEmail(data[3]);
			
			char[] emailArray = data[3].toCharArray();
			int counter = 0;
			for(int i = 0; i<emailArray.length; i++) {
				if (emailArray[i] == '@') counter++;
			}
			
			if(counter == 0) throw new PersonException(
					"Error : email field doesnt contain @");
			
		}catch(PersonException e) {
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
		return Integer.toString(id) + "|" + firstName + 
				"|" + lastName + "|" + email + "\n";
	}

	public void print() {
		System.out.println(toString());
	}
	
	public Person readFromStdio() {
		
		System.out.println("Introduce the data of the new Person :\n"/* + 
				"Usage : " + "id,firstName,lastName,email;"*/);
		int numComma = 0;
		String readFSP = null;
		
		do {
			numComma = 0;
			System.out.println("Usage : id|firstName|lastName|email");

			readFSP = new Scanner(System.in).nextLine();
			char[] commaArray = readFSP.toCharArray();

			for(int i = 0; i<commaArray.length; i++) 
				if(commaArray[i] == '|') numComma++;
			if(numComma%3 != 0 || numComma == 0 || numComma == 1) 
				System.err.println("Error : Invalid input.");

		}while(numComma%3 != 0 || numComma == 0 || numComma == 1);
		
		String[] parts = readFSP.split("\\|");
		
		Person newPerson = new Person(Integer.parseInt(parts[0]), 
				parts[1], parts[2], parts[3]);
		
		return newPerson;
		
	}
	
	public Person[] readFromFile(String File){
		
		//System.out.println("Which file do you want to read from? :");
		
		/*Scanner fileScan = new Scanner(System.in);
		String fileName = fileScan.nextLine();*/
		
		String fileName = File;
		
		ArrayList<Person> arrayListPerson = new ArrayList<Person>();
		
		File fileR = null;
		Scanner scanRFP = null;
		
		try {
			fileR = new File(fileName);
			if(!(fileR.exists())) {
				System.err.println("Error : File not found (Person).");
				return null;
			}
			
			scanRFP = new Scanner(fileR);
		
			while(scanRFP.hasNextLine()) {
			
				String rbuffer = scanRFP.nextLine();
				String[] parts = rbuffer.split("\\|");
			
				Person newPerson = new Person(Integer.parseInt(parts[0]), 
						parts[1], parts[2], parts[3]);
				
				arrayListPerson.add(newPerson);
			}
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(scanRFP != null) scanRFP.close();
		}
		
		Person[] arrayPerson = arrayListPerson.toArray(
				new Person[arrayListPerson.size()]);
		
		return arrayPerson;
	}
	
	public void writeToFile(String File) {	
		
		/*System.out.println("Introduce the name of the file (without any extension) :\n" + 
				"(If the file specified doesn't exist, "
					+ "a new file with that name will be created)");
	
		String fileName = null;
		fileName = "./src/PersonFiles/" + new Scanner(System.in).nextLine() + ".txt";*/
		
		String fileName = "./src/PersonFiles/" + File;

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
		 				
		 wbuffer.write(this.getId() + "," + this.getFirstName() + "," + 
						this.getLastName() + "," + this.getEmail() + ";");
		 wbuffer.newLine();
		 wbuffer.flush();
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(writeWFP != null) {
				try {
					writeWFP.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean equals(Person pe) {
		return (getId() == pe.getId())?true:false;
	}
	
	public int compareTo(Object pe) {
		
		try {
			if(getId() > ((Person) pe).getId()) return 1;
			if(getId() < ((Person) pe).getId()) return -1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			System.err.println("The parameter of the compareTo() method should be "
					+ "a Person");
		}
		return 0;
	}
	
}
