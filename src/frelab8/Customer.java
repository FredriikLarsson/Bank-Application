package frelab8;

import java.util.ArrayList;

/*
 * Klass för att skapa och hantera kunder inom banksystemet.
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class Customer {

	private String firstName;
	private String lastName;
	private final String ID_NUMBER;
	private ArrayList<Account> accounts = new ArrayList<Account>(); // Konton som kundobjekten har

	/*
	 * Konstruktor för att skapa nya kundobjekt.
	 * @param firstName och lastName är kundens för- och efternamn, @param idNumber
	 * är kundens personnummer
	 * @return ett nytt kundobjekt
	 */
	public Customer(String firstName, String lastName, String idNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ID_NUMBER = idNumber;
	}

	/*
	 * Ändrar värdet på medlemsvariabeln firstName
	 * @param firstName är det nya värdet på förnamnet
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/*
	 * Ändrar värdet på medlemsvariabeln lastName
	 * @param lastName är det nya värdet på efternamnet
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * Hämta information om specifik kund
	 * @return en sträng med kundens personnummer + för- och efternamn
	 */
	public String getCustomerInfo() {
		return ID_NUMBER + " " + firstName + " " + lastName;
	}

	/*
	 * Hämta en specifik kunds personnummer
	 * @return en sträng med kundens personnummer
	 */
	public String getIdNumber() {
		return ID_NUMBER;
	}

	/*
	 * Tar bort ett konto från kontolistan
	 * @param kontonumret för kontot som ska tas bort
	 */
	public void deleteAccount(int accountId) {
		accounts.remove(getAccountIndex(accountId));
	}

	/*
	 * Kontrollerar ifall kontot existerar i kontolistan hos kunden
	 * @param kontonumret för kontot
	 * @return kontoobjektet ifall det existerar annars returneras null
	 */
	public Account checkAccount(int accountId) {
		// Loopar igenom kundens kontolista för att kontrollera om kontot existerar
		// annars returneras null
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountNumber() == accountId) {
				return accounts.get(i);
			}
		}
		return null;
	}

	/*
	 * Hämta kontonumret för det senast skapade kontot
	 * @return kontonumret från det senast skapade kontot
	 */
	public int getNewAccountNumber() {
		// Returnerar det nya kontot genom att ta storleken på kontolistan och ta bort
		// -1 (eftersom array startar på 0)
		return accounts.get(accounts.size() - 1).getAccountNumber();
	}

	/*
	 * Skapa ett nytt konto i kontolistan
	 */
	public void createNewAccount() {
		accounts.add(new Account());
	}

	/*
	 * Hämta kundens lista med nuvarande konton
	 * @return en ny arraylista<String> som representerar kundens nuvarande konton
	 */
	public ArrayList<String> getCustomerAccounts() {
		ArrayList<String> accountInfo = new ArrayList<String>();
		// Addera den valda kundens information på första platsen till den array som
		// sedan ska returneras.
		accountInfo.add(getCustomerInfo());
		// Loopa igenom den valda kundens lista över konton och addera dessa till den
		// nya arrayen som ska returneras.
		for (int i = 0; i < accounts.size(); i++) {
			accountInfo.add(i + 1, accounts.get(i).getAccountInfo());
		}
		return accountInfo;
	}

	/*
	 * Hämta information om kundens konton vid borttagning av konton
	 * @return en ny arraylista<String> som representerar kundens borttagna konton.
	 */
	public ArrayList<String> getCustomerClosingAccounts() {
		ArrayList<String> accountInfo = new ArrayList<String>();
		// Lägger till kund information på första platsen i arraylistan
		accountInfo.add(getCustomerInfo());
		// Loopa igenom den valda kundens lista över konton och addera dessa till den
		// nya arrayen som ska returneras.
		for (int i = 0; i < accounts.size(); i++) {
			accountInfo.add(i + 1, accounts.get(i).getClosingAccountInfo());
		}
		return accountInfo;
	}
	
	/*
	 * Kontrollerar vilket index i kontolistan ett specifikt konto ligger på.
	 * @param accountId är kontonumret för kontot
	 * @return det index som kontot ligger på i kontolistan hos kunden, finns inte
	 * kontot med så returneras -1
	 */
	private int getAccountIndex(int accountId) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountNumber() == accountId) {
				return i;
			}
		}
		return -1;
	}

}
