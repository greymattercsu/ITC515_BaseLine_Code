
/*
@author:Ashish Shrestha
@reviewer: Bijan Dhakal
@Mediator: Saurav Pradhan
@Scriber: Sanchay Gurung

*/

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.lang.StringBuilder; //added package to use StringBuilder() method


public class Main {
	
	private static Scanner scan; //changed variable IN to scan
	private static Library library; //renamed library to Library and variable LIB to library
	private static String menu; //renamed variable MENU to menu
	private static Calendar calendar;  //changed variable name CAL to calendar
	private static SimpleDateFormat SDF;
	
	
	private static String Get_menu() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nLibrary Main Menu\n\n")
		  .append("  M  : add member\n")
		  .append("  LM : list members\n")
		  .append("\n")
		  .append("  B  : add book\n")
		  .append("  LB : list books\n")
		  .append("  FB : fix books\n")
		  .append("\n")
		  .append("  L  : take out a loan\n")
		  .append("  R  : return a loan\n")
		  .append("  LL : list loans\n")
		  .append("\n")
		  .append("  P  : pay fine\n")
		  .append("\n")
		  .append("  T  : increment date\n")
		  .append("  Q  : quit\n")
		  .append("\n")
		  .append("Choice : ");
		  
		return sb.toString();
	}


	public static void main(String[] args) {		
		try {			
			scan = new Scanner(System.in); //changed variable IN to scan
			library = Library.INSTANCE(); //renamed library to Library and variable LIB to library
			calendar = Calendar.getInstance(); //changed variable name CAL to calendar
			SDF = new SimpleDateFormat("dd/MM/yyyy");
	
			for (member m : library.Members()) {  //renamed variable LIB to library
				output(m);
			}
			output(" ");
			for (book b : library.Books()) {  //renamed variable LIB to library
				output(b);
			}
						
			menu = Get_menu();  //renamed variable MENU to menu
			
			boolean e = false;
			
			while (!e) {
				
				output("\n" + SDF.format(calendar.Date()));  //changed variable name CAL to calendar
				String c = input(menu);  //renamed variable MENU to menu
				
				switch (c.toUpperCase()) {
				
				case "M": 
					addMember();
					break;
					
				case "LM": 
					listMembers();
					break;
					
				case "B": 
					addBook();
					break;
					
				case "LB": 
					listBooks();
					break;
					
				case "FB": 
					fixBooks();
					break;
					
				case "L": 
					borrowBook();
					break;
					
				case "R": 
					returnBook();
					break;
					
				case "LL": 
					listCurrentLoans();
					break;
					
				case "P": 
					payFine();
					break;
					
				case "T": 
					incrementDate();
					break;
					
				case "Q": 
					e = true;
					break;
					
				default: 
					output("\nInvalid option\n");
					break;
				}
				
				Library.SAVE();  //renamed library to Library
			}			
		} catch (RuntimeException e) {
			output(e);
		}		
		output("\nEnded\n");
	}	

		private static void payFine() {
		new PayFineUI(new PayFineControl()).run();		
	}


	private static void listCurrentLoans() {
		output("");
		for (loan loan : LIB.CurrentLoans()) {
			output(loan + "\n");
		}		
	}



	private static void listBooks() {
		output("");
		for (book book : library.Books()) {  //renamed variable LIB to library
			output(book + "\n");
		}		
	}



	private static void listMembers() {
		output("");
		for (member member : library.Members()) {  //renamed variable LIB to library
			output(member + "\n");
		}		
	}



	private static void borrowBook() {
		new BorrowBookUI(new BorrowBookControl()).run();		
	}


	private static void returnBook() {
		new ReturnBookUI(new ReturnBookControl()).run();		
	}


	private static void fixBooks() {
		new FixBookUI(new FixBookControl()).run();		
	}


	private static void incrementDate() {
		try {
			int days = Integer.valueOf(input("Enter number of days: ")).intValue();
			calendar.incrementDate(days);  //changed variable name CAL to calendar
			library.checkCurrentLoans();  //renamed variable LIB to library
			output(SDF.format(CAL.Date()));
			
		} catch (NumberFormatException e) {
			 output("\nInvalid number of days\n");
		}
	}


	private static void addBook() {
		
		String author = input("Enter author: ");
		String title  = input("Enter title: ");
		String callNo = input("Enter call number: ");
		book book = library.Add_book(author, title, callNo);  //renamed variable LIB to library
		output("\n" + book + "\n");
		
	}

	
	private static void addMember() {
		try {
			String lastName = input("Enter last name: ");
			String firstName  = input("Enter first name: ");
			String email = input("Enter email: ");
			int phoneNo = Integer.valueOf(input("Enter phone number: ")).intValue();
			member member = library.Add_mem(lastName, firstName, email, phoneNo);  //renamed variable LIB to library
			output("\n" + member + "\n");
			
		} catch (NumberFormatException e) {
			 output("\nInvalid phone number\n");
		}
		
	}


	private static String input(String prompt) {
		System.out.print(prompt);
		return scan.nextLine(); //changed variable IN to scan
	}
	
	
	
	private static void output(Object object) {
		System.out.println(object);
	}

	
}
