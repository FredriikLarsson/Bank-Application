package frelab8;

import java.util.ArrayList;

/*
 * Denna klass hanterar logiken i banksystemet, det som systemet i funktionsväg ska kunna erbjuda användarna
 *
 * @author Fredrik Larsson, frelab-8
 */

public class BankLogic {
	private static ArrayList<Customer> customers = new ArrayList<Customer>(); // Lista med nuvarande kunder i systemet

	/*
	 * Skapar en ny kund som lagras i kundlistan
	 * @param firstName och lastName är för- och efternamn på kunden, @param
	 * idNumber är kundens personnummer
	 * @return sant/falskt över hur det gick att skapa kunden
	 */
	public boolean createCustomer(String firstName, String lastName, String idNumber) {
		Customer customer = getCustomerObject(idNumber);
		// Kontrollerar ifall kunden redan existerar
		if (customer == null) {
			customers.add(new Customer(firstName, lastName, idNumber));
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Hämtar och presenterar information om en specifik kund och de bankkonton som
	 * kunden har
	 * @param idNumber är kundens personnummer
	 * @return en lista med information om kunden på första platsen i listan och
	 * informaton om bankkonton på efterföljande platser i listan
	 */
	public ArrayList<String[]> getCustomer(String idNumber) {
		Customer customer = getCustomerObject(idNumber);
		// Kontrollera ifall kunden finns i banksystemet
		if (customer == null) {
			return null;
		} else {
			return customer.getCustomerAccounts();
		}
	}

	/*
	 * Ändrar för- eller efternamn på kunden 
	 * @param firstName och lastName är för- och efternamn på kunden, @param
	 * idNumber är kundens personnummer
	 * @return sant/falskt över hur det gick att ändra namnet på kunden
	 */
	public boolean changeCustomerName(String firstName, String lastName, String idNumber) {
		Customer customer = getCustomerObject(idNumber);
		// Kontrollerar ifall kunden finns i banksystemet
		if (customer == null) {
			return false;
		} else {
			// Kontrollerar ifall någon av de inskickade värdena på för- och efternamn är
			// tomma
			if ((firstName == "") || (lastName == "")) {
				if (firstName == "") {
					customer.setLastName(lastName);
					return true;
				} else {
					customer.setFirstName(firstName);
					return true;
				}
			} else {
				customer.setFirstName(firstName);
				customer.setLastName(lastName);
				return true;
			}
		}
	}

	/*
	 * Tar bort kund från kundlistan i banksystemet
	 * @param idNumber är kundens personnummer 
	 * @return en lista med kundinformation på plats 1 och bankkonton på
	 * efterföljande platser, detta är den kund+konton som har tagits bort
	 */
	public ArrayList<String> deleteCustomer(String idNumber) {
		Customer customer = getCustomerObject(idNumber);
		// Kontrollerar ifall kunden existerar i banksystemet
		if (customer == null) {
			return null;
		} else {
			// Tar bort kunden från kundlistan som banken har
			customers.remove(getCustomerIndex(idNumber));
			return customer.getCustomerClosingAccounts();
		}
	}

	/*
	 * Hämtar information om alla kunder i kundlistan (customers)
	 * @return en lista med information om alla kunder i banksystemet
	 */
	public ArrayList<String[]> getAllCustomers() {
		ArrayList<String[]> allCustomerInfo = new ArrayList<String[]>();
		// Loopar igenom kundlistan för att hämta information om alla kunder till den
		// lista som sedan ska returneras
		for (int i = 0; i < customers.size(); i++) {
			allCustomerInfo.add(customers.get(i).getCustomerInfo());
		}
		return allCustomerInfo;
	}

	/*
	 * Skapa ett nytt sparkonto till en specifik kund
	 * @param idNumber är kundens personnummer
	 * @return kontonumret på det nyss skapade nya kontot
	 */
	public int createSavingsAccount(String idNumber) {
		Customer customer = getCustomerObject(idNumber);
		// Kontrollera ifall kunden existerar i banksystemet
		if (customer == null) {
			return -1;
		} else {
			customer.createNewAccount();
			return customer.getNewAccountNumber();
		}
	}
	
	/*
	 * Skapa ett nytt kreditkonto till en specifik kund
	 * @param idNumber är kundens personnummer
	 * @return kontonumret på det nyss skapade nya kontot
	 */
	public int createCreditAccount(String idNumber) {
		Customer customer = getCustomerObject(idNumber);
		//Kontrollerar ifall kunden existerar i banksystemet
		if(customer == null) {
			return -1;
		} else {
			customer.createNewCreditAccount();
			return customer.getNewAccountNumber();
		}
	}

	/*
	 * Hämtar information om ett specifikt konto
	 * @param idNumber är personnumret hos kontoägaren, @param accountId är
	 * kontonumret på kontot
	 * @return en sträng med information om det specifika kontot
	 */
	public String[] getAccount(String idNumber, int accountId) {
		Account account = getAccountObject(idNumber, accountId);
		// Kontrollerar ifall kontot och kontoägare(kund) existerar i banksystemet
		if (account == null) {
			return null;
		} else {
			return account.getAccountInfo();
		}
	}

	/*
	 * Tar bort ett konto från banksystemet
	 * @param idNumber är kontoägarens personnummer, @param accountId är kontonumret
	 * på kontot som ska tas bort
	 * @return en sträng med information om kontot som nyss tagits bort
	 */
	public String[] closeAccount(String idNumber, int accountId) {
		Customer customer = getCustomerObject(idNumber);
		Account account = getAccountObject(idNumber, accountId);
		// Kontrollerar ifall konto och kontoägare(kund) existerar i banksystemet
		if (account == null) {
			return null;
		} else {
			// Tar bort kontot från kontolistan hos kontoägaren(kunden)
			customer.deleteAccount(accountId);
			return account.getClosingAccountInfo();
		}
	}

	/*
	 * Sätta in pengar på ett specifikt sparkonto
	 * @param idNumber är kontoägarens personnummer, accountId är kontonumret på
	 * kontot där pengarna ska sättas in, @param amount är det belopp som ska sättas
	 * in
	 * @return sant/falskt över hur insättningen gick, ifall den lyckades eller inte
	 */
	public boolean deposit(String idNumber, int accountId, double amount) {
		Account account = getAccountObject(idNumber, accountId);
		if (amount > 0) {
			// Kontrollerar ifall kontot och kontoägare existerar i banksystemet
			if (account == null) {
				return false;
			} else {
				account.setBalanceDeposit(amount);
				return true;
			}
		}
		// Returnerar falskt ifall insättningen är <= 0
		return false;
	}

	/*
	 * Göra ett uttag från ett specifikt konto
	 * @param idNumber är kontoägarens personnummer, accountId är kontonumret på
	 * kontot där uttaget ska göras, @param amount är det belopp på pengarna som ska
	 * tas ut
	 * @return sant/falskt över hur uttaget gick, ifall den lyckades eller inte
	 */
	public boolean withdraw(String idNumber, int accountId, double amount) {
		Account account = getAccountObject(idNumber, accountId);
		if (amount > 0) {
			// Kontrollerar ifall kontot och kontoägaren existerar i banksystemet
			if (account == null) {
				return false;
			} else {
				return account.setBalanceWithdrawal(amount);
			}
		} else {
			// Returnerar false ifall uttagsbeloppet är <= 0
			return false;
		}
	}
	
	/*
	 * Hämtar en lista med alla transaktioner på ett specifikt konto
	 * @param idNumber är kontoägarens personnummer, @param accountId är kontots kontonummer
	 * @return är en arrayList med alla transaktioner konverterad till strängar
	 */
	public ArrayList<String> getTransactions(String idNumber, int accountId) {
		Account account = getAccountObject(idNumber, accountId);
		if(account == null) {
			return null;
		} else {
			return account.getTransactionHistory();
		}
	}

	/*
	 * Kontrollerar ifall kontot finns i banksystemet
	 * @param idNumber är kontoägarens personnummer, @param accountId är kontonumret
	 * på kontot som ska kontrolleras
	 * @return är kontoobjektet ifall kontot existerar annars returneras null
	 */
	private Account getAccountObject(String idNumber, int accountId) {
		Customer customer = getCustomerObject(idNumber);
		// Kontrollerar ifall kontoägaren(kunden) existerar i banksystemet
		if (customer == null) {
			return null;
		} else {
			return customer.checkAccount(accountId);
		}
	}

	/*
	 * Kontrollerar ifall kund existerar i banksystemet
	 * @param idNumber är kundens personnummer
	 * @return är kundobjektet ifall kunden finns annars returneras null
	 */
	private Customer getCustomerObject(String idNumber) {
		// Loopar igenom kundlistan på banken för att kontrollera ifall kunden existerar
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getIdNumber().equals(idNumber)) {
				return customers.get(i);
			}
		}
		return null;
	}

	/*
	 * Kontrollerar vilket index en kund med ett specifikt personnummer har.
	 * @param idNumber är kundens personnummer
	 * @return index som kunden har i bankens kundlista, finns inte kunden där så
	 * returneras -1
	 */
	private int getCustomerIndex(String idNumber) {
		// Loopar igenom kundlistan för att kontrollera vilket index som kunden har i
		// kundlistan
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getIdNumber().equals(idNumber)) {
				return i;
			}
		}
		return -1;
	}

}
