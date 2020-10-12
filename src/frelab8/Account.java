package frelab8;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
//import java.util.Date;

/*
 * Klass för att skapa och hantera konton som ska finnas med i banksystemet.
 * 
 * @author Fredrik Larsson, frelab-8
 */

public abstract class Account {

	//private static double interestRate = 1; // Räntesats i procent.
	private static int lastAssignedNumber = 1000; // Första kontot som skapas får nummer 1001.

	private final int ACCOUNT_NUMBER; // Kontonummer bör inte kunna ändras efter skapandet av kontot därav "final"
	private BigDecimal balance = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_EVEN);
	//private double balance;
	private final String ACCOUNT_TYPE; // Med antagandet att kontotyp på ett konto aldrig ska kunna ändras ifall fler kontotyper tillkommer, därav final.
	private ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();
	
	/*
	 * Konstruktor för att skapa ett konto med ett unikt kontonummer inom hela banksystemet.
	 * Varje nytt konto får ett kontonummer som är + 1 högre än det föregående som skapades.
	 * @return ett nytt konto objekt.
	 */
	public Account(String accountType) {
		this.ACCOUNT_TYPE = accountType;
		this.ACCOUNT_NUMBER = lastAssignedNumber + 1;
		lastAssignedNumber += 1;
	}

	
	public abstract boolean setBalanceWithdrawal(double amount);
	
	
	/*
	 * Kontrollerar saldot på kontot vid ett uttag och ta bort uttaget belopp.
	 * @param amount är beloppet som ska tas ut
	 * @return är sant/falskt över hur det gick att ta ut pengar.
	 */
	public void setBalance(String amount) {
		BigDecimal amountTemp = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN);
		if (balance.compareTo(amountTemp) >= 0) {
			balance = balance.subtract(amountTemp);
		}
	}
	
	/*
	 * Presenterar saldot på kontot
	 * @return är saldot som finns på kontot
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	
	/*
	 * Ändrar saldot vid insättning av pengar.
	 * @param amount är beloppet som ska sättas in
	 */
	public void setBalanceDeposit(double amount) {
		BigDecimal amountTemp = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN);
		balance = balance.add(amountTemp);
		transactionHistory.add(new Transaction(amountTemp.toString(), balance.toString()));
	}
	
	/*
	 * Adderar en ny transaktion i form av ett Transaction objekt i arraylistan transactionhistory
	 * @param amount är uttaget eller insättningen som görs 
	 * @param balance är balansen efter att uttaget eller instättningen gjorts
	 */
	public void addTransaction(String amount, String balance) {
		transactionHistory.add(new Transaction(amount, balance));
	}

	
	/*
	 * Hämtar kontonumret hos ett kontoobjekt
	 * @return kontonumret
	 */
	public int getAccountNumber() {
		return ACCOUNT_NUMBER;
	}

	
	public abstract String getAccountInfo();
	
	//public abstract double getInterestRate();
	
	//public abstract double getBalanceSpecific();

	

	public abstract String getClosingAccountInfo();
	
	/*
	 * Hämtar och konverterar transaktionslistan till en ny arraylista med transaktionsinformation(strängar)
	 * @return en ny arraylista konverterad till String
	 */
	public ArrayList<String> getTransactionHistory() {
		ArrayList<String> transactionList = new ArrayList<String>();
		for (int i = 0; i < transactionHistory.size(); i++) {
			transactionList.add(transactionHistory.get(i).getTransactionInfo());
		}
		return transactionList;
	}
	
	//public abstract String getSpecificAccountInfo();
	
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

	//public abstract double calculateRate();

}
