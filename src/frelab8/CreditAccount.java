package frelab8;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * Klass som skapar och hanterar konton med kontotypen "kreditkonto".
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class CreditAccount extends Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal creditLimit = new BigDecimal("5000");
	private static BigDecimal interestRate = new BigDecimal("0.5");
	private static BigDecimal loanFee = new BigDecimal("7.0");
	private BigDecimal creditUsed = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_EVEN);
	private String accountType = "Kreditkonto";
	
	public CreditAccount() {
		super();
	}
	
	/*
	 * Ta ut pengar från ett kreditkonto
	 * @param amount är summan som ska tas ut från kontot
	 * @return sant/falskt ifall uttaget lyckades eller inte
	 */
	public boolean setBalanceWithdrawal(double amount) {
		BigDecimal amount_ = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN); // (double)amount konverterad till BigDecimal.
		BigDecimal creditLeft = creditLimit.subtract(creditUsed).setScale(1, RoundingMode.HALF_EVEN); // Tillgänglig kredit på kontot
		//Kontrollerar ifall uttagssumman är större än den nuvarande balansen men mindre än kontobalansen + den kvarvarande krediten på kontot.
		if(((amount_.compareTo(super.getBalance())) > 0) && (amount_.compareTo(creditLeft.add(super.getBalance())) <= 0 )) {
			//Lägger till på creditUsed det som blir kvar efter kontobalansen använts först (uttagssumman-kontobalansen = det som blir över).
			creditUsed = creditUsed.add(amount_.subtract(super.getBalance()));
			//Tar ut de kvarvarande pengarna på kontot.
			super.setBalance(super.getBalance().toString());
			//Adderar en ny transaktion till transaktionslistan med uttagssumman konverterad till ett negativt värde, 
			//kontobalansen efter uttaget hämtas genom att använda "creditUsed" konverterad till ett negativt värde.
			super.addTransaction(amount_.negate().toString(), creditUsed.negate().toString());
			return true;
		//Eller ifall summan är mindre än nuvarande saldot på kontot
		} else if(amount_.compareTo(super.getBalance()) <= 0){
			super.setBalance(amount_.toString());
			//Adderar en ny transaktion till transaktionslistan med uttagssumman konverterad till ett negativt värde, 
			//kontobalansen efter uttaget hämtas genom att använda super.getBalance (behövs inte konverteras eftersom kontobalansen fortfarande är positiv.
			super.addTransaction(amount_.negate().toString(), super.getBalance().toString());
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Hämtar information om kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + årsräntan
	 */
	public String[] getAccountInfo() {
		String[] list = new String[4];
		list[0] = Integer.toString(super.getAccountNumber());
		list[1] = getAccountBalance().toString();
		list[2] = accountType;
		//Kontrollerar ifall kontot har använt krediter
		if(creditUsed.compareTo(BigDecimal.ZERO) > 0) {
			list[3] = loanFee.toString();
			return list;
		} else {
			list[3] = interestRate.toString();
			return list;
		}
	}
	
	/*
	 * Hämtar information om kontot vid borttagning av kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + kalkylerad årsränta i kronor
	 */
	public String[] getClosingAccountInfo() {
		String[] list = new String[4];
		list[0] = Integer.toString(super.getAccountNumber());
		list[1] = getAccountBalance().toString();
		list[2] = accountType;
		list[3] = getCalculatedInterest();
		return list;
	}
	
	/*
	 * Skickar ut den specifika kontoinformation för kreditkonton
	 * @return konverterad sträng från double av den specifika årsräntan eller låneräntan för kreditkonton
	 */
	private String getCalculatedInterest() {
		BigDecimal interestRate_ = interestRate.divide(new BigDecimal("100"));
		//Kontrollerar ifall kontot har använt krediter
		if(creditUsed.compareTo(BigDecimal.ZERO) > 0) {
			//Ifall krediter har använts returneras använda krediter multiplicerat med låneavgift/100
			return "" + creditUsed.multiply(loanFee.divide(new BigDecimal("100"))).setScale(1, RoundingMode.HALF_EVEN).negate().toString();
		} else {
			//Ifall kontobalansen varit positiv returneras istället årsräntan multiplicerat med kontobalansen
			return "" + interestRate_.multiply(super.getBalance()).setScale(1, RoundingMode.HALF_EVEN);
		}
	}
	
	/*
	 * Hämtar kontobalansen för ett kreditkonto
	 * @return om krediter är använda så returneras användakrediter konverterad till ett negativt värde
	 * @return om krediter inte är använda så returneras superklassens positiva kontobalans
	 */
	public BigDecimal getAccountBalance() {
		if(creditUsed.compareTo(BigDecimal.ZERO) > 0) {
			return creditUsed.negate();
		} else {
			return super.getBalance();
		}
	}
}
