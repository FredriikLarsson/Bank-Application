package frelab8;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/*
 * Klass för att skapa och hantera konton som ska finnas med i banksystemet.
 * 
 * @author Fredrik Larsson, frelab-8
 */

public abstract class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int lastAssignedNumber = 1000; // Första kontot som skapas får nummer 1001.
	private final int ACCOUNT_NUMBER; // Kontonummer bör inte kunna ändras efter skapandet av kontot därav "final"
	private BigDecimal balance = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_EVEN); // Kontobalansen satt till 0 som standard och innehar 1 decimals precision och rundar av till närmsta 50öre.
	private ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>(); // Lista med alla transaktioner som gjorts på kontot
	
	/*
	 * Konstruktor för att skapa ett konto med ett unikt kontonummer inom hela banksystemet.
	 * Varje nytt konto får ett kontonummer som är + 1 högre än det föregående som skapades.
	 * @return ett nytt konto objekt.
	 */
	public Account() {
		this.ACCOUNT_NUMBER = lastAssignedNumber + 1;
		lastAssignedNumber += 1;
	}
	
	/*
	 * Kontrollerar saldot på kontot vid ett uttag och ta bort uttaget belopp.
	 * @param amount är beloppet som ska tas ut
	 * @return är sant/falskt över hur det gick att ta ut pengar.
	 */
	public void setBalance(String amount) {
		BigDecimal amount_ = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN); // Konvertera argumentet (String)amount till en BigDecimal för att använda i metoden
		//Kontrollera ifall uttagssumman finns tillgänglig i kontobalansen för att kunna ta ut pengar
		if (balance.compareTo(amount_) >= 0) {
			balance = balance.subtract(amount_);
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
		BigDecimal amount_ = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN); // Konvertera (double)amount till en BigDecimal som används i metoden.
		balance = balance.add(amount_);
		//Addera transaktionen till transaktionslistan på kontot, genom att konvertera både amount_ och balance till strängar som "transaction(String, String)" metoden kräver
		transactionHistory.add(new Transaction(amount_.toString(), balance.toString()));
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
	
	/*
	 * Hämtar och konverterar transaktionslistan till en ny arraylista med transaktionsinformation(strängar)
	 * @return en ny arraylista konverterad till String
	 */
	public ArrayList<String> getTransactionHistory() {
		ArrayList<String> transactionList = new ArrayList<String>();
		//Loopar igenom transaktionslistan med transaktionsobjekt och konverterar varje transaktionsobjekt till en annan arraylista med strängar
		for (int i = 0; i < transactionHistory.size(); i++) {
			transactionList.add(transactionHistory.get(i).getTransactionInfo());
		}
		return transactionList;
	}
	
	/*
	 * När ett objekt läses in från objectInputStream och ifall objektets kontonummer är högre än klassvariabeln "lastAssignedNumber"
	 * så ändrar vi lastAssignedNumber till objektetskontonummer.
	 */
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		if (ACCOUNT_NUMBER > lastAssignedNumber) {
			lastAssignedNumber = ACCOUNT_NUMBER;
		}
	}
	
	public abstract BigDecimal getAccountBalance();
	
	public abstract boolean setBalanceWithdrawal(double amount);
	
	public abstract String[] getAccountInfo();
	
	public abstract String[] getClosingAccountInfo();

}
