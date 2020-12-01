package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class AccountViewController implements ActionListener{
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
		String command = e.getActionCommand();
		currentAccountNumber = gui.getCurrentAccountNumber();
		Object[] newTransactionList;
		switch(command) {
		case "deposit":
			JOptionPane.showMessageDialog(gui.frame, accountView.getAmountPanel());
			String amount = accountView.getAmountInput().getText();
			Boolean checkAmount = bank.deposit(gui.currentIdNumber, Integer.parseInt(currentAccountNumber), Double.parseDouble(amount));
			if(checkAmount) {
				accountView.getTransactionInfo().setModel(new DefaultListModel());
				newTransactionList = bank.getTransactions(gui.currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
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
		case "withdraw":
			JOptionPane.showMessageDialog(gui.frame, accountView.getAmountPanel());
			String amountWithdraw = accountView.getAmountInput().getText();
			Boolean checkAmountWithdraw = bank.withdraw(gui.currentIdNumber, Integer.parseInt(currentAccountNumber), Double.parseDouble(amountWithdraw));
			if(checkAmountWithdraw) {
				accountView.getTransactionInfo().setModel(new DefaultListModel());
				newTransactionList = bank.getTransactions(gui.currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
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
		case "deleteAccount":
			Object[] closingAccountList = bank.closeAccount(gui.currentIdNumber, Integer.parseInt(currentAccountNumber));
			accountView.setAccountClosingInfo(new JList<Object>(closingAccountList));
			JOptionPane.showMessageDialog(gui.frame, accountView.getAccountClosingInfo());
			gui.getCustomerView().getCustomerViewController().addRowToTable();
			gui.getCustomerView().getAccountsPanel().remove(gui.getCustomerView().getTableAccounts());
			gui.getCustomerView().getAccountsPanel().add(gui.getCustomerView().getTableAccounts());
			gui.getCustomerView().getAccountsPanel().revalidate();
			gui.frame.repaint();
			gui.getCardLayout().show(gui.getC(), "customerView");
			break;
		}
	}

}
