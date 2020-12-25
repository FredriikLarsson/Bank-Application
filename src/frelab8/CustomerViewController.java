package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/*
 * Denna klass är controllern som ska hantera all interaktion med kundvyn "GuiCustomerView".
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class CustomerViewController implements ActionListener {
	private BankLogic bank;
	private Gui gui;
	private GuiCustomerView customerView;
	private String currentAccountNumber; //Kontonumret på det konto som systemets fokus ligger förnärvarande på.
	
	public CustomerViewController(Gui gui, GuiCustomerView customerView, BankLogic bank) {
		this.gui = gui;
		this.customerView = customerView;
		this.bank = bank;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object[] transactionList; //Uppdaterad transaktionslista.
		JPanel accountPanel = gui.getAccountView().getAccountPanel(); //Panel i kontovyn.
		JPanel accountListPanel = gui.getCustomerView().getAccountsPanel(); //Panel i kundvyn med alla kundens konton.
		String command = e.getActionCommand();
		int newAccountNumber; //Nytt kontonummer på det nyskapade kontot vid val av nytt konto.
		switch(command) {
		//Valt konto från en lista med konton.
		case "chooseAccount":
			try {
				currentAccountNumber = customerView.getTableAccounts().getValueAt(customerView.getTableAccounts().getSelectedRow(), 0).toString();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(gui.frame, "Inget konto valt");
				return;
			}
			//Ändra instansvariabeln i main gui klassen.
			gui.setCurrentAccountNumber(currentAccountNumber);
			//Sätter en ny ListModel för att rensa bort den gamla.
			gui.getAccountView().getTransactionInfo().setModel(new DefaultListModel());
			//Hämta transaktionerna till en array för ett specifikt konto.
			transactionList = bank.getTransactions(gui.currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
			//Uppdaterar kontovyns transaktionslist med en uppdaterad transaktionslista.
			gui.getAccountView().setTransactionInfo(new JList<Object>(transactionList));
			//Ändrar kontoinformationen på kontovyn.
			gui.getAccountView().getAccountViewController().setAccountLabel(gui.currentIdNumber, currentAccountNumber);
			accountPanel.remove(gui.getAccountView().getTransactionInfo());
			accountPanel.add(gui.getAccountView().getTransactionInfo());
			accountPanel.revalidate();
			gui.frame.repaint();
			gui.getCardLayout().show(gui.getC(), "accountView"); //Lägg kontovyn längst fram i cardlayouten.
			break;
		//Ändra namn på vald kund.
		case "changeCustomerName":
			//Lägg fram en popup ruta med en panel som låter användaren att ändra namn på kund.
			JOptionPane.showMessageDialog(gui.frame, customerView.getChangeCustomerNamePanel());
			String firstName = customerView.getChangeFirstNameInput().getText();
			String lastName = customerView.getChangeLastNameInput().getText();
			Boolean checkChange = bank.changeCustomerName(firstName, lastName, gui.currentIdNumber);
			//Kontrollera ifall kundens namn kunde ändras eller inte.
			if(checkChange) {
				JOptionPane.showMessageDialog(gui.frame, "Kundnamnet har ändrats");
				//Kallar på en metod i kundVyn som ändrar namn på kunden i tabellen.
				addRowToTable();
				accountListPanel.remove(customerView.getTableAccounts());
				accountListPanel.add(customerView.getTableAccounts());
				accountListPanel.revalidate();
				gui.frame.repaint();
			} else {
				JOptionPane.showMessageDialog(gui.frame, "Kundnamnet har inte ändrats");
			}
			break;
			//Ta bort vald kund.
		case "deleteCustomer":
			//Hämtar en lista med konton hos den borttagna kunden.
			Object[] list = bank.deleteCustomer(gui.currentIdNumber).toArray();
			//Lägger till de hämtade kontona i en lista som håller information om den borttagna kunden.
			customerView.setCustomerClosingInfo(new JList<Object>(list));
			//Presenterar den lista med information om den borttagna kunden och dennes konton.
			JOptionPane.showMessageDialog(gui.frame, customerView.getCustomerClosingInfo());
			//Lägger startVyn längst fram i cardlayouten.
			gui.getCardLayout().show(gui.getC(), "startView");
			break;
			//Skapa kreditkonto.
		case "createCreditAccount":
			//Skapar ett kreditkonto och lagrar det nya kontonumret.
			newAccountNumber = bank.createCreditAccount(gui.currentIdNumber);
			//Tar bort den gamla tabellen med konton och bygger upp den på nytt genom metoden addRowToTable.
			addRowToTable();
			customerView.getAccountsPanel().remove(customerView.getTableAccounts());
			customerView.getAccountsPanel().add(customerView.getTableAccounts());
			customerView.getAccountsPanel().revalidate();
			gui.frame.repaint();
			JOptionPane.showMessageDialog(gui.frame, "Det nya kontot har kontonummer: " + newAccountNumber);
			break;
			//Skapa sparkonto
		case "createSavingsAccount":
			//Skapar ett sparkonto och lagrar den nya kontonumret.
			newAccountNumber = bank.createSavingsAccount(gui.currentIdNumber);
			//Tar bort den gamla tabellen med konton och bygger upp den på nytt.
			addRowToTable();
			customerView.getAccountsPanel().remove(customerView.getTableAccounts());
			customerView.getAccountsPanel().add(customerView.getTableAccounts());
			customerView.getAccountsPanel().revalidate();
			gui.frame.repaint();
			JOptionPane.showMessageDialog(gui.frame, "Det nya kontot har kontonummer: " + newAccountNumber);
			break;
		}
		
	}
	
	//Denna metod tar bort den gamla tabellen med konton hos en specifik kund och bygger upp den på nytt igen.
	public void addRowToTable() {
		//Hämtar en ny lista med konton från den valda kunden.
		ArrayList<String[]> list = bank.getCustomer(gui.currentIdNumber);
		String customerInfo;
		//Hämtar tabellens "tableModel"
		DefaultTableModel model = (DefaultTableModel) customerView.getTableAccounts().getModel();
		//Sätter radantalet till 0 för att rensa det gamla.
		model.setRowCount(0);
		model.setColumnCount(4);
		Object rowData[] = new Object[5]; //Data som ska representera alla konton i kontotabellen i kundvyn.
		String rowDataCustomerInfo[] = new String[3]; //Data som ska representera information om kunden vilket ska placeras ovanför kontotabellen i kundvyn.
		//För över kundinformation till "rowDataCustomerInfo" som finns på första platsen i arraylistan "list" som kommer från metoden bank.getCustomer.
		rowDataCustomerInfo[0] = list.get(0)[0].toString();
		rowDataCustomerInfo[1] = list.get(0)[1].toString();
		rowDataCustomerInfo[2] = list.get(0)[2].toString();
		customerInfo = rowDataCustomerInfo[0] + " " + rowDataCustomerInfo[1] + " " + rowDataCustomerInfo[2];
		//Ändrar den text i JLabel som ligger ovanför kontotabellen i kundvyn.
		customerView.setCustomerInfo(customerInfo);
		//Loopar igenom listan med konton och för in dessa i tabellen, hoppar över första platsen i arraylistan "list" eftersom detta är kundinformation och inte ett konto.
		for(int i = 1; i < list.size(); i++) {
			for(int y = 0; y < list.get(i).length; y++) {
				rowData[y] = list.get(i)[y];
				}
			model.addRow(rowData);
			}
		}
}
