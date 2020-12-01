package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class CustomerViewController implements ActionListener{
	private BankLogic bank;
	private Gui gui;
	private GuiCustomerView customerView;
	private String currentAccountNumber;
	
	public CustomerViewController(Gui gui, GuiCustomerView customerView, BankLogic bank) {
		this.gui = gui;
		this.customerView = customerView;
		this.bank = bank;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JList<Object> transactionInfo = gui.getAccountView().getTransactionInfo();
		JPanel accountPanel = gui.getAccountView().getAccountPanel();
		JPanel accountListPanel = gui.getCustomerView().getAccountsPanel();
		String command = e.getActionCommand();
		int newAccountNumber;
		switch(command) {
		case "chooseAccount":
			currentAccountNumber = customerView.getTableAccounts().getValueAt(customerView.getTableAccounts().getSelectedRow(), 0).toString();
			gui.setCurrentAccountNumber(currentAccountNumber);
			gui.getAccountView().getTransactionInfo().setModel(new DefaultListModel());
			Object[] transactionList = bank.getTransactions(gui.currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
			gui.getAccountView().setTransactionInfo(new JList<Object>(transactionList));
			String[] accountInfo = bank.getAccount(gui.currentIdNumber, Integer.parseInt(currentAccountNumber));
			String accountBalance = accountInfo[1];
			String accountType = accountInfo[2];
			String accountInterest = accountInfo[3];
		try {
			gui.getAccountView().setAccountLabel(currentAccountNumber + " " + accountBalance + "kr " + accountType + " " + accountInterest + "%");
		} catch (NumberFormatException e1) {
			System.out.println("Kontonumrets format ogiltigt");
		}
		accountPanel.remove(transactionInfo);
		accountPanel.add(transactionInfo);
		accountPanel.revalidate();
		gui.frame.repaint();
		gui.getCardLayout().show(gui.getC(), "accountView");
		break;
		case "changeCustomerName":
			JOptionPane.showMessageDialog(gui.frame, customerView.getChangeCustomerNamePanel());
			String firstName = customerView.getChangeFirstNameInput().getText();
			String lastName = customerView.getChangeLastNameInput().getText();
			Boolean checkChange = bank.changeCustomerName(firstName, lastName, gui.currentIdNumber);
			if(checkChange) {
				JOptionPane.showMessageDialog(gui.frame, "Kundnamnet har ändrats");
				customerView.changeCustomerName(firstName, lastName);
				accountListPanel.remove(customerView.getTableAccounts());
				accountListPanel.add(customerView.getTableAccounts());
				accountListPanel.revalidate();
				gui.frame.repaint();
			} else {
				JOptionPane.showMessageDialog(gui.frame, "Kundnamnet har inte ändrats");
			}
			break;
		case "deleteCustomer":
			Object[] list = bank.deleteCustomer(gui.currentIdNumber).toArray();
			customerView.setCustomerClosingInfo(list);
			JOptionPane.showMessageDialog(gui.frame, customerView.getCustomerClosingInfo());
			gui.getCardLayout().show(gui.getC(), "startView");
			break;
		case "createCreditAccount":
			newAccountNumber = bank.createCreditAccount(gui.currentIdNumber);
			addRowToTable();
			customerView.getAccountsPanel().remove(customerView.getTableAccounts());
			customerView.getAccountsPanel().add(customerView.getTableAccounts());
			customerView.getAccountsPanel().revalidate();
			gui.frame.repaint();
			JOptionPane.showMessageDialog(gui.frame, "Det nya kontot har kontonummer: " + newAccountNumber);
			break;
		case "createSavingsAccount":
			newAccountNumber = bank.createSavingsAccount(gui.currentIdNumber);
			addRowToTable();
			customerView.getAccountsPanel().remove(customerView.getTableAccounts());
			customerView.getAccountsPanel().add(customerView.getTableAccounts());
			customerView.getAccountsPanel().revalidate();
			gui.frame.repaint();
			JOptionPane.showMessageDialog(gui.frame, "Det nya kontot har kontonummer: " + newAccountNumber);
			break;
		}
		
	}
	
	public void addRowToTable() {
		ArrayList<String[]> list = bank.getCustomer(gui.currentIdNumber);
		DefaultTableModel model = (DefaultTableModel) customerView.getTableAccounts().getModel();
		model.setRowCount(0);
		model.setColumnCount(4);
		Object rowData[] = new Object[5];
		for(int i = 0; i < list.size(); i++) {
			for(int y = 0; y < list.get(i).length; y++) {
				rowData[y] = list.get(i)[y];
				}
			model.addRow(rowData);
			}
		}
}
