package frelab8;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Klass som skapar och hanterar enskilda transaktioner
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date dateTime = new Date(); // Datum och tid när transaktionen inträffade
	private String amount; // Summa för transaktionen
	private String balanceAfter; // Balansen på kontot efter transaktionen
	
	public Transaction(String amount, String balanceAfter) {
		this.amount = amount;
		this.balanceAfter = balanceAfter;
	}
	
	/*
	 * Hämtar information om transaktionen som datum, summa, saldo efter transaktionen
	 * @return en sträng med information om transaktionen
	 */
	public String getTransactionInfo() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String txInfo = dateFormat.format(dateTime) + " " + amount + " kr Saldo: " + balanceAfter + " kr";
		return txInfo;
	}

}
