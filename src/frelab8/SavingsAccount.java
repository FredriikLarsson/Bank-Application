package frelab8;

public class SavingsAccount extends Account {
	private static double interestRate = 1.0;
	private double withdrawalFee = 2.0;
	private boolean freeWithdrawal = true;
	
	public SavingsAccount() {
		super("Savings Account");
	}
	
	/*
	 * Ta ut pengar från ett sparkonto
	 * @param amount är summan som ska tas ut från kontot
	 * @return sant/falskt ifall uttaget lyckades eller inte
	 */
	public boolean setBalanceWithdrawal(double amount) {
		//Kontrollerar ifall kontot fortfarande har ett fritt uttag kvar
		if(freeWithdrawal == true) {
			if(amount <= super.getBalance()) {
				//Anropar superklassens setbalance metod för den privata balance variabeln i superklassen
				super.setBalance(amount);
				return true;
			} else {
				return false;
			}
		} else {
			//Kontrollerar ifall uttagssumman + uttagsavgiften är lika med eller mindre än balansen på kontot
			if((amount*(withdrawalFee/100) <= super.getBalance())) {
				super.setBalance(amount + (amount * (withdrawalFee/100)));
				return true;
			} else {
				return false;
			}
		}
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	/*
	 * Skickar ut den specifika kontoinformation för sparkonton
	 * @return konverterad sträng från double av den specifika årsräntan för sparkonton
	 */
	public String getSpecificAccountInfo() {
		return "" + super.getBalance() * (interestRate/100);
	}
	
	/*
	 * Beräknar årsränta på kontots balans i kronor
	 * @return Årsränta i kronor på kontot
	 */
	/*public double calculateRate() {
		return super.getBalance() * (interestRate / 100);
	}*/

}
