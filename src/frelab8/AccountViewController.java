package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class AccountViewController implements ActionListener{
	//Hämtar referenser till skapade instanser av BankLogic, Gui och GuiAccountView för att kunna manipulera dessa vyer beroende på olika händelser.
	BankLogic bank;
	Gui gui;
	GuiAccountView accountView;
	String currentAccountNumber;
	
	public AccountViewController(Gui gui, GuiAccountView accountView, BankLogic bank) {
		this.bank = bank;
		this.gui = gui;
		this.accountView = accountView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); //Den händelse som användaren har startat i form av ett kommando "actionCommand". 
		currentAccountNumber = gui.getCurrentAccountNumber(); //Hämtar nuvarande kontonummer på det konto som all manipulation ska ske på.
		Object[] newTransactionList; //Ny och uppdaterad transaktionslista till det valda kontot.
		switch(command) {
		case "deposit": //Sätta in pengar.
			JOptionPane.showMessageDialog(gui.frame, accountView.getAmountPanel()); //Skapar en dialog ruta som innehåller "getAmountPanel" med tillgång till text input med hur mycket användaren vill sätta in.
			String amount = accountView.getAmountInput().getText(); //Hämtar inputen om summa som användaren vill sätta in på kontot.
			//Lägger in pengar på nuvarandekundnummer och valt konto med den summa pengar som användaren har fyllt i.
			Boolean checkAmount = bank.deposit(gui.currentIdNumber, Integer.parseInt(currentAccountNumber), Double.parseDouble(amount));
			//Kontrollerar ifall insättningen lyckades.
			if(checkAmount) {
				//Nollställer den nuvarande transaktionslistan i kontoVyn.
				accountView.getTransactionInfo().setModel(new DefaultListModel());
				//Hämtar en ny transaktionslista från det valda kontot.
				newTransactionList = bank.getTransactions(gui.currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
				//Lägger till den nya transaktionslistan till kontoVyns transaktionslista.
				accountView.setTransactionInfo(new JList<Object>(newTransactionList));
				JOptionPane.showMessageDialog(gui.frame, "Pengarna är insatta på kontot");
			} else {
				JOptionPane.showMessageDialog(gui.frame, "Pengarna kunde inte sättas in");
			}
			accountView.getAccountPanel().remove(accountView.getTransactionInfo());
			accountView.getAccountPanel().add(accountView.getTransactionInfo());
			accountView.getAccountPanel().revalidate();
			gui.frame.repaint();
			break;
		case "withdraw": //Ta ut pengar.
			//Skapar en dialog ruta som innehåller "getAmountPanel" med tillgång till text input med hur mycket användaren vill ta ut.
			JOptionPane.showMessageDialog(gui.frame, accountView.getAmountPanel());
			//Hämtar inputen om summa som användaren vill ta ut från kontot.
			String amountWithdraw = accountView.getAmountInput().getText();
			//Tar ut pengar från nuvarandekundnummer och valt konto med den summa pengar som användaren har fyllt i.
			Boolean checkAmountWithdraw = bank.withdraw(gui.currentIdNumber, Integer.parseInt(currentAccountNumber), Double.parseDouble(amountWithdraw));
			//Kontrollerar ifall uttaget lyckades.
			if(checkAmountWithdraw) {
				//Nollställer den nuvarande transaktionslistan i kontoVyn.
				accountView.getTransactionInfo().setModel(new DefaultListModel());
				//Hämtar en ny transaktionslista från det valda kontot.
				newTransactionList = bank.getTransactions(gui.currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
				//Lägger till den nya transaktionslistan till kontoVyns transaktionslista.
				accountView.setTransactionInfo(new JList<Object>(newTransactionList));
				JOptionPane.showMessageDialog(gui.frame, "Pengarna har dragits från kontot");
			} else {
				JOptionPane.showMessageDialog(gui.frame, "Pengarna kunde inte dras");
			}
			accountView.getAccountPanel().remove(accountView.getTransactionInfo());
			accountView.getAccountPanel().add(accountView.getTransactionInfo());
			accountView.getAccountPanel().revalidate();
			gui.frame.repaint();
			break;
		case "deleteAccount": //Tar bort det valda kontot.
			//Tar bort kontot och lagrar information om kontot i en array.
			Object[] closingAccountList = bank.closeAccount(gui.currentIdNumber, Integer.parseInt(currentAccountNumber));
			//Lägger in informationen om det borttagna kontot i kontoVyn.
			accountView.setAccountClosingInfo(new JList<Object>(closingAccountList));
			//Presenterar information om det borttagna kontot i en popupruta.
			JOptionPane.showMessageDialog(gui.frame, accountView.getAccountClosingInfo());
			//Kallar på kundvyn´s controller för att rensa och bygga om kontolistan som vyn ska visa.
			gui.getCustomerView().getCustomerViewController().addRowToTable();
			gui.getCustomerView().getAccountsPanel().remove(gui.getCustomerView().getTableAccounts());
			gui.getCustomerView().getAccountsPanel().add(gui.getCustomerView().getTableAccounts());
			gui.getCustomerView().getAccountsPanel().revalidate();
			gui.frame.repaint();
			//Lägger kundVyn längst fram i cardlayouten för att ta användaren från kontoVyn tillbaka till kundVyn när kontot är borttaget.
			gui.getCardLayout().show(gui.getC(), "customerView");
			break;
		}
	}

}
