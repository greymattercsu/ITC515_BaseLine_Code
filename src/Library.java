
/**
@author:Sanchay Gurung
@reviewer: Ashish Shrestha
@Mediator: Bijan Dhakal
@Scriber: Saurav Pradhan

*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {		//changed class name library to Library
	
	private static final String LIBRARY_FILE = "library.obj";
	private static final int LOAN_LIMIT = 2;
	private static final int LOAN_PERIOD = 2;
	private static final double FINE_PER_DAY = 1.0;
	private static final double MAX_FINES_OWED = 5.0;
	private static final double DAMAGE_FEE = 2.0;
	
	private static Library self;	//change type name library to Library
	private int bid;				//change private variable name BID to bid
	private int mid;				//change private variable name MID to mid
	private int lid;				//change private variable name LID to lid
	private Date loadDate;
	
	private Map<Integer, Book> catalog;			//change book to Book
	private Map<Integer, Member> members;		//change member to Member
	private Map<Integer, Loan> loans;			//change loan to Loan
	private Map<Integer, Loan> currentLoans;	//change loan to Loan
	private Map<Integer, Book> damagedBooks;	//change book to Book
	

	private Library() {		//change constructer name library to Library
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bid = 1;			// change BID to bid
		mid = 1;			//change MID to mid
		lid = 1;			//change LID to lid
	}

	
	public static synchronized Library instance() {			//change library to Library and INSTANCE to instance
		if (self == null) {
			Path path = Paths.get(LIBRARY_FILE);			
			if (Files.exists(path)) {	
				try {		//fixed the syntax previous syntax => try(ObjectInputStream lof) {}
					ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(LIBRARY_FILE));	//change iof ot inputStream		    
					self = (Library) inputStream.readObject();		//change library to Library
					Calendar.getInstance().setDate(self.loadDate);
					inputStream.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else self = new Library();		//change library to Library
		}
		return self;
	}

	
	public static synchronized void save() {		//change SAVE to save
		if (self != null) {
			self.loadDate = Calendar.getInstance().Date();
			try {		//fixed the syntax previous syntax => try(ObjectInputStream lof) {}
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(LIBRARY_FILE));		//change variable lof to outputStream
				outputStream.writeObject(self);
				outputStream.flush();
				outputStream.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int bookID() {		//change method name BookId to bookId
		return bid;				//change variable BID to bid
	}
	
	
	public int memberID() {		//change method name MemberID to memberID
		return mid;				//change attribute name MID to mid
	}
	
	
	private int nextBID() {			
		return bid++;			//change attribute name BID to bid
	}

	
	private int nextMID() {
		return mid++;			//change attribute name MID to mid
	}

	
	private int nextLID() {
		return lid++;			//change attribute name LID to lid
	}

	
	public List<Member> members() {		//change List<member> to List<Member> and Members() to members()
		return new ArrayList<Member>(members.values()); //change ArrayList<member>() to ArrayList<Member>()
	}


	public List<Book> books() {			//change List<book> to List<Book> and Books() to books()
		return new ArrayList<Book>(catalog.values()); //ArrayList<book>() to ArrayList<Book>()
	}


	public List<Loan> currentLoans() {		//change List<loan> to List<Loan> and CurrentLoans() to currentLoans()
		return new ArrayList<Loan>(currentLoans.values());		//ArrayList<loan>() to ArrayList<Loan>()
	}


	public Member addMember(String lastName, String firstName, String email, int phoneNo) {		//change type member to Member, Add_mem() to addMember()	
		Member member = new Member(lastName, firstName, email, phoneNo, nextMID());			//change type member to Member and parameterized constructor new member() to new Member()
		members.put(member.getId(), member);		
		return member;
	}

	
	public Book addBook(String a, String t, String c) {		//change return type book to Book and method name Add_book() tp addBook()
		Book b = new Book(a, t, c, nextBID());		//change book to Book
		catalog.put(b.ID(), b);		
		return b;
	}

	
	public Member getMember(int memberId) {			//change member to Member
		if (members.containsKey(memberId)) {		//added parentheses
			return members.get(memberId);
		}
		return null;
	}

	
	public Book book(int bookId) {				//change type name book to Book and method name Book() to book()
		if (catalog.containsKey(bookId)) {		//added parentheses
			return catalog.get(bookId);		
		}
		return null;
	}

	
	public int loanLimit() {
		return LOAN_LIMIT;
	}

	
	public boolean memberCanBorrow(Member member) {				//change argument type member to Member		
		if (member.getNumberOfCurrentLoans() == LOAN_LIMIT ){	//added parenthesis 
			return false;
		}				
		if (member.getFinesOwed() >= MAX_FINES_OWED) {			//added parenthesis 
			return false;
		}
		for (Loan loan : member.getLoans()) {					//added parenthesis 
			if (loan.isOverDue()) {								//added parenthesis 
				return false;
			}
		}			
		return true;
	}

	
	public int loansRemainingForMember(Member member) {			//change argument type member to Member	
		return LOAN_LIMIT - member.getNumberOfCurrentLoans();
	}

	
	public Loan issueLoan(Book book, Member member) {			//change return type loan to Loan, argument types book and member to Book and Member
		Date dueDate = Calendar.getInstance().getDueDate(LOAN_PERIOD);
		Loan loan = new Loan(nextLID(), book, member, dueDate);		//change loan to Loan
		member.takeOutLoan(loan);
		book.Borrow();
		loans.put(loan.getId(), loan);
		currentLoans.put(book.ID(), loan);
		return loan;
	}
	
	
	public Loan getLoanByBookId(int bookId) {			//change return type loan to Loan,
		if (currentLoans.containsKey(bookId)) {
			return currentLoans.get(bookId);
		}
		return null;
	}

	
	public double calculateOverDueFine(Loan loan) {		//change argument types loan to Loan
		if (loan.isOverDue()) {
			long daysOverDue = Calendar.getInstance().getDaysDifference(loan.getDueDate());
			double fine = daysOverDue * FINE_PER_DAY;
			return fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(Loan currentLoan, boolean isDamaged) {	//change argument types loan to Loan
		Member member = currentLoan.Member();				//type member to Member
		Book book  = currentLoan.Book();					//type book to Book		
		double overDueFine = calculateOverDueFine(currentLoan);
		member.addFine(overDueFine);			
		member.dischargeLoan(currentLoan);
		book.Return(isDamaged);
		if (isDamaged) {
			member.addFine(DAMAGE_FEE);
			damagedBooks.put(book.ID(), book);
		}
		currentLoan.Loan();
		currentLoans.remove(book.ID());
	}


	public void checkCurrentLoans() {
		for (Loan loan : currentLoans.values()) {		//change type loan to Loan
			loan.checkOverDue();
		}		
	}


	public void repairBook(Book currentBook) {				//change argument type book to Book
		if (damagedBooks.containsKey(currentBook.ID())) {
			currentBook.Repair();
			damagedBooks.remove(currentBook.ID());
		}
		else {
			throw new RuntimeException("Library: repairBook: book is not damaged");
		}		
	}
	
	
}
