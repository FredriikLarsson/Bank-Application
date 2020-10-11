package frelab8;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	private Date dateTime = new Date();
	private String amount;
	private String balanceAfter;
	
	public Transaction(String amount, String balanceAfter) {
		this.amount = amount;
		this.balanceAfter = balanceAfter;
	}
	
	public String getTransactionInfo() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String txInfo = dateFormat.format(dateTime) + " " + amount + " kr Saldo: " + balanceAfter + " kr";
		return txInfo;
	}

}
