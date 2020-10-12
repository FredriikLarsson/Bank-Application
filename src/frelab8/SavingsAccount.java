package frelab8;

import java.math.BigDecimal;import java.math.RoundingMode;

public class SavingsAccount extends Account {
	//private static double interestRate = 1.0;
	//private double withdrawalFee = 2.0;
	private boolean freeWithdrawal = true;
	private static BigDecimal interestRate = new BigDecimal("1.0");
	private BigDecimal withdrawalFee = new BigDecimal("2.0");
	
	public SavingsAccount() {
		super("Sparkonto");
	}
	
	/*
	 * Ta ut pengar från ett sparkonto
	 * @param amount är summan som ska tas ut från kontot
	 * @return sant/falskt ifall uttaget lyckades eller inte
	 */
	public boolean setBalanceWithdrawal(double amount) {
		//double amountWithFee = amount + (amount * (withdrawalFee/100));
		BigDecimal amountTemp = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN);
		BigDecimal amountWithFee = amountTemp.add(amountTemp.multiply(withdrawalFee.divide(new BigDecimal("100"))).setScale(1, RoundingMode.HALF_EVEN));
		
		//Kontrollerar ifall kontot fortfarande har ett fritt uttag kvar
		if(freeWithdrawal == true) {
			if(amountTemp.compareTo(super.getBalance()) <= 0) {
				//Anropar superklassens setbalance metod för den privata balance variabeln i superklassen
				super.setBalance(amountTemp.toString());
				super.addTransaction(amountTemp.negate().toString(), super.getBalance().toString());
				freeWithdrawal = false;
				return true;
			} else {
				return false;
			}
		} else {
			//Kontrollerar ifall uttagssumman + uttagsavgiften är lika med eller mindre än balansen på kontot
			if((amountWithFee.compareTo(super.getBalance()) <= 0)) {
				super.setBalance(amountWithFee.toString());
				super.addTransaction(amountWithFee.negate().toString(), super.getBalance().toString());
				return true;
			} else {
				return false;
			}
		}
	}
	
	/*public double getInterestRate() {
		return interestRate;
	}*/
	
	/*
	 * Skickar ut den specifika kontoinformation för sparkonton
	 * @return konverterad sträng från double av den specifika årsräntan för sparkonton
	 */
	private String getSpecificAccountInfo() {
		BigDecimal calculatedInterest = super.getBalance().multiply(interestRate.divide(new BigDecimal("100")));
		return "" + calculatedInterest.setScale(1, RoundingMode.HALF_EVEN);
	}
	
	/*
	 * Hämtar information om kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + årsräntan
	 */
	public String getAccountInfo() {
		return super.getAccountNumber() + " " + super.getBalance() + " kr " + "Sparkonto" + " " + interestRate + " %";
	}
	
	/*
	 * Hämtar information om kontot vid borttagning av kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + kalkylerad årsränta i kronor
	 */
	public String getClosingAccountInfo() {
		return super.getAccountNumber() + " " + super.getBalance() + " kr " + super.getAccountType() + " " + getSpecificAccountInfo() + " kr";
	}
	
	/*
	 * Beräknar årsränta på kontots balans i kronor
	 * @return Årsränta i kronor på kontot
	 */
	/*public double calculateRate() {
		return super.getBalance() * (interestRate / 100);
	}*/

}
