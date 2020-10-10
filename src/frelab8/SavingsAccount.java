package frelab8;

public class SavingsAccount extends Account {
	private static double interestRate = 1.0;
	private double withdrawalFee = 2.0;
	private boolean freeWithdrawal = true;
	
	public SavingsAccount() {
		super("Sparkonto");
	}
	
	/*
	 * Ta ut pengar från ett sparkonto
	 * @param amount är summan som ska tas ut från kontot
	 * @return sant/falskt ifall uttaget lyckades eller inte
	 */
	public boolean setBalanceWithdrawal(double amount) {
		//Kontrollerar ifall kontot fortfarande har ett fritt uttag kvar
		if(freeWithdrawal == true) {
			if(amount * (withdrawalFee/100) <= super.getBalance()) {
				//Anropar superklassens setbalance metod för den privata balance variabeln i superklassen
				super.setBalance(amount+(amount*(withdrawalFee/100)));
				super.addTransaction(amount * (-1), super.getBalance());
				freeWithdrawal = false;
				return true;
			} else {
				return false;
			}
		} else {
			//Kontrollerar ifall uttagssumman + uttagsavgiften är lika med eller mindre än balansen på kontot
			if((amount*(withdrawalFee/100) <= super.getBalance())) {
				super.setBalance(amount + (amount * (withdrawalFee/100)));
				super.addTransaction(amount * (-1), super.getBalance());
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
	public String getSpecificAccountInfo() {
		return "" + super.getBalance() * (interestRate/100);
	}
	
	/*
	 * Hämtar information om kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + årsräntan
	 */
	public String getAccountInfo() {
		return super.getAccountNumber() + " " + super.getBalance() + " kr " + "Sparkonto" + " " + interestRate + " %";
	}
	
	/*
	 * Beräknar årsränta på kontots balans i kronor
	 * @return Årsränta i kronor på kontot
	 */
	/*public double calculateRate() {
		return super.getBalance() * (interestRate / 100);
	}*/

}
