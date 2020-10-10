package frelab8;

public class CreditAccount extends Account {
	private static double interestRate = 0.5;
	private static double loanFee = 7.0;
	private int creditLimit = 5000;
	private double creditUsed = 0;
	
	
	public CreditAccount() {
		super("Kreditkonto");
	}
	
	/*
	 * Skickar ut den specifika kontoinformation för kreditkonton
	 * @return konverterad sträng från double av den specifika årsräntan eller låneräntan för kreditkonton
	 */
	private String getSpecificAccountInfo() {
		if(creditUsed > 0) {
			return "" + (creditUsed * (loanFee / 100))*(-1);
		} else {
			return "" + super.getBalance() * (interestRate / 100);
		}
	}
	
	/*
	 * Ta ut pengar från ett kreditkonto
	 * @param amount är summan som ska tas ut från kontot
	 * @return sant/falskt ifall uttaget lyckades eller inte
	 */
	public boolean setBalanceWithdrawal(double amount) {
		//Kontrollerar ifall uttagssumman är större än nuvarande balansen och mindre än den kvarvarande krediten på kontot
		if(amount > super.getBalance() && amount <= super.getBalance() + (creditLimit - creditUsed)) {
			creditUsed = creditUsed + (amount - super.getBalance());
			super.setBalance(super.getBalance());
			super.addTransaction(amount * (-1), creditUsed * (-1));
			return true;
		//Eller ifall summan är mindre än nuvarande saldot på kontot
		} else if(amount <= super.getBalance()){
			super.setBalance(amount);
			super.addTransaction(amount * (-1), super.getBalance());
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
		if(creditUsed > 0) {
			return super.getAccountNumber() + " " + getAccountBalance() + " kr " + "Kreditkonto" + " " + loanFee + " %";
		} else {
			return super.getAccountNumber() + " " + getAccountBalance() + " kr " + "Kreditkonto" + " " + interestRate + " %";
		}
	}
	
	private double getAccountBalance() {
		if(creditUsed > 0) {
			return creditUsed*(-1);
		} else {
			return super.getBalance();
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
