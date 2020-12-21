package frelab8;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * Klass för att skapa och hantera kontotypen "sparkonto".
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class SavingsAccount extends Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean freeWithdrawal = true; // Ifall kontot har kvar det fria uttaget som sparkonto har per år.
	private static BigDecimal interestRate = new BigDecimal("1.0"); // Med antagandet att årsräntan ska vara samma för alla konton under denna kontotyp
	private static BigDecimal withdrawalFee = new BigDecimal("2.0"); // Med antagandet att uttagsavgiften ska vara samma för alla konton under denna kontotyp
	private String accountType = "Sparkonto";
	
	public SavingsAccount() {
		super();
	}
	
	/*
	 * Ta ut pengar från ett sparkonto
	 * @param amount är summan som ska tas ut från kontot
	 * @return sant/falskt ifall uttaget lyckades eller inte
	 */
	public boolean setBalanceWithdrawal(double amount) {
		BigDecimal amount_ = new BigDecimal(amount).setScale(1, RoundingMode.HALF_EVEN); // (double)amount konverterad till BigDecimal
		BigDecimal amountWithFee = amount_.add(amount_.multiply(withdrawalFee.divide(new BigDecimal("100"))).setScale(1, RoundingMode.HALF_EVEN)); // Uttagssumman + avgiften för uttaget
		//Kontrollerar ifall kontot fortfarande har ett fritt uttag kvar
		if(freeWithdrawal == true) {
			//Kontrollerar ifall uttagssumman kan täckas av den tillgängliga kontobalansen på kontot.
			if(amount_.compareTo(super.getBalance()) <= 0) {
				//Anropar superklassens setbalance metod för att ändra balansen på kontot
				super.setBalance(amount_.toString());
				//Adderar en ny transaktion till transaktionslistan med uttagssumman konverterad till ett negativt värde, sen även den nuvarande kontobalansen efter uttaget är gjort
				super.addTransaction(amount_.negate().toString(), super.getBalance().toString());
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
	
	/*
	 * Hämtar information om kontot
	 * @return en sträng som innehåller kontonumret + kontobalansen + kontotypen + årsräntan
	 */
	public String[] getAccountInfo() {
		String[] list = new String[4];
		list[0] = Integer.toString(super.getAccountNumber());
		list[1] = super.getBalance().toString();
		list[2] = accountType;
		list[3] = interestRate.toString();
		
		return list;
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
	 * Kalkylerar räntan på de pengar som finns förtillfället på kontot 
	 * @return kalkylerad årsränta för kontot
	 */
	private String getCalculatedInterest() {
		BigDecimal calculatedInterest = super.getBalance().multiply(interestRate.divide(new BigDecimal("100"))); // Tar kontobalansen "super.getBalance" och multiplicerar med räntesatsen "interestRate" dividerat med 100.
		return "" + calculatedInterest.setScale(2, RoundingMode.HALF_EVEN);
	}
	
	/*
	 * Returnerar kontots balans.
	 * @return balansen på kontot.
	 */
	public BigDecimal getAccountBalance() {
		return super.getBalance();
	}

}
