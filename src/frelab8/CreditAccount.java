package frelab8;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CreditAccount extends Account {
	//private static String interestRate = "0.5";
	//private static String loanFee = "7.0";
	//private int creditLimit = 5000;
	//private String creditUsed = "0";
	private BigDecimal creditLimit = new BigDecimal("5000");
	private static BigDecimal interestRate = new BigDecimal("0.5");
	private static BigDecimal loanFee = new BigDecimal("7.0");
	private BigDecimal creditUsed = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_EVEN);
	
	
	public CreditAccount() {
		super("Kreditkonto");
	}
	
	/*
	 * Skickar ut den specifika kontoinformation för kreditkonton
	 * @return konverterad sträng från double av den specifika årsräntan eller låneräntan för kreditkonton
	 */
	private String getSpecificAccountInfo() {
		BigDecimal interestRateTemp = interestRate.divide(new BigDecimal("100"));
		if(creditUsed.compareTo(BigDecimal.ZERO) > 0) {
			return "" + (creditUsed.multiply(loanFee.divide(new BigDecimal("100"))).setScale(1, RoundingMode.HALF_EVEN).negate().toString());
		} else {
			return "" + interestRateTemp.multiply(super.getBalance()).setScale(1, RoundingMode.HALF_EVEN);
		}
	}
	
	/*
	 * Ta ut pengar från ett kreditkonto
	 * @param amount är summan som ska tas ut från kontot
	 * @return sant/falskt ifall uttaget lyckades eller inte
	 */
	public boolean setBalanceWithdrawal(double amount) {
		BigDecimal amountTemp = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN);
		BigDecimal creditLeft = creditLimit.subtract(creditUsed).setScale(1, RoundingMode.HALF_EVEN);
		//Kontrollerar ifall uttagssumman är större än nuvarande balansen och mindre än den kvarvarande krediten på kontot
		if(((amountTemp.compareTo(super.getBalance())) > 0) && (amountTemp.compareTo(creditLeft.add(super.getBalance())) <= 0 )) {
			creditUsed = creditUsed.add(amountTemp.subtract(super.getBalance()));
			super.setBalance(super.getBalance().toString());
			super.addTransaction(amountTemp.negate().toString(), creditUsed.negate().toString());
			return true;
		//Eller ifall summan är mindre än nuvarande saldot på kontot
		} else if(amountTemp.compareTo(super.getBalance()) <= 0){
			super.setBalance(amountTemp.toString());
			super.addTransaction(amountTemp.negate().toString(), super.getBalance().toString());
			return true;
		} else {
			return false;
		}
	}
	
	/*public double getInterestRate() {
		return interestRate;
	}*/
	
	/*
	 * Hämtar information om kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + årsräntan
	 */
	public String getAccountInfo() {
		if(creditUsed.compareTo(BigDecimal.ZERO) > 0) {
			return super.getAccountNumber() + " " + getAccountBalance() + " kr " + "Kreditkonto" + " " + loanFee.toString() + " %";
		} else {
			return super.getAccountNumber() + " " + getAccountBalance() + " kr " + "Kreditkonto" + " " + interestRate.toString() + " %";
		}
	}
	
	private String getAccountBalance() {
		if(creditUsed.compareTo(BigDecimal.ZERO) > 0) {
			return creditUsed.negate().toString();
		} else {
			return super.getBalance().toString();
		}
	}
	
	/*
	 * Hämtar information om kontot vid borttagning av kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + kalkylerad årsränta i kronor
	 */
	public String getClosingAccountInfo() {
		return super.getAccountNumber() + " " + getAccountBalance() + " kr " + super.getAccountType() + " " + getSpecificAccountInfo() + " kr";
	}
	
	/*public double calculateRate() {
		
	}*/

}
