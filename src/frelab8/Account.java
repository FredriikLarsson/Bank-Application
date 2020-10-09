package frelab8;

import java.util.ArrayList;

/*
 * Klass för att skapa och hantera konton som ska finnas med i banksystemet.
 * 
 * @author Fredrik Larsson, frelab-8
 */

public abstract class Account {

	//private static double interestRate = 1; // Räntesats i procent.
	private static int lastAssignedNumber = 1000; // Första kontot som skapas får nummer 1001.

	private final int ACCOUNT_NUMBER; // Kontonummer bör inte kunna ändras efter skapandet av kontot därav "final"
	private double balance;
	private final String ACCOUNT_TYPE; // Med antagandet att kontotyp på ett konto aldrig ska kunna ändras ifall fler kontotyper tillkommer, därav final.
	private ArrayList<String> transactionHistory = new ArrayList<String>();

	
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
	public void setBalance(double amount) {
		if (balance >= amount) {
			balance = balance - amount;
		}
	}
	
	/*
	 * Presenterar saldot på kontot
	 * @return är saldot som finns på kontot
	 */
	public double getBalance() {
		return balance;
	}

	
	/*
	 * Ändrar saldot vid insättning av pengar.
	 * @param amount är beloppet som ska sättas in
	 */
	public void setBalanceDeposit(double amount) {
		balance += amount;      
	}

	
	/*
	 * Hämtar kontonumret hos ett kontoobjekt
	 * @return kontonumret
	 */
	public int getAccountNumber() {
		return ACCOUNT_NUMBER;
	}

	
	/*
	 * Hämtar information om kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + årsräntan
	 */
	public String getAccountInfo() {
		return ACCOUNT_NUMBER + " " + balance + " kr " + ACCOUNT_TYPE + " " + getInterestRate() + " %";
	}
	
	public abstract double getInterestRate();

	
	/*
	 * Hämtar information om kontot vid borttagning av kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + kalkylerad årsränta i kronor
	 */
	public String getClosingAccountInfo() {
		return ACCOUNT_NUMBER + " " + balance + " kr " + ACCOUNT_TYPE + " " + getSpecificAccountInfo() + " kr";
	}
	
	public abstract String getSpecificAccountInfo();

	//public abstract double calculateRate();

}
