package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

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
		JList<Object> transactionInfo = gui.getAccountView().getTransactionInfo(); //Lista med transaktioner på ett valt konto.
		JPanel accountPanel = gui.getAccountView().getAccountPanel(); //Panel i kontovyn.
		JPanel accountListPanel = gui.getCustomerView().getAccountsPanel(); //Panel i kundvyn med alla kundens konton.
		String command = e.getActionCommand();
		int newAccountNumber; //Nytt kontonummer på det nyskapade kontot vid val av nytt konto.
		switch(command) {
		//Valt konto från en lista med konton.
		case "chooseAccount":
			currentAccountNumber = customerView.getTableAccounts().getValueAt(customerView.getTableAccounts().getSelectedRow(), 0).toString();
			//Ändra instansvariabeln i main gui klassen.
			gui.setCurrentAccountNumber(currentAccountNumber);
			//Sätter en ny ListModel för att rensa bort den gamla.
			gui.getAccountView().getTransactionInfo().setModel(new DefaultListModel());
			//Hämta transaktionerna till en array för ett specifikt konto.
			Object[] transactionList = bank.getTransactions(gui.currentIdNumber, Integer.parseInt(currentAccountNumber)).toArray();
			//Uppdaterar kontovyns transaktionslist med en uppdaterad transaktionslista.
			gui.getAccountView().setTransactionInfo(new JList<Object>(transactionList));
			//Hämtar information om ett specfikt konto.
			String[] accountInfo = bank.getAccount(gui.currentIdNumber, Integer.parseInt(currentAccountNumber));
			String accountBalance = accountInfo[1];
			String accountType = accountInfo[2];
			String accountInterest = accountInfo[3];
			//Presentera kontoinformationen via en label i kontoVyn.
		try {
			gui.getAccountView().setAccountLabel(currentAccountNumber + " " + accountBalance + "kr " + accountType + " " + accountInterest + "%");
		} catch (NumberFormatException e1) {
			System.out.println("Kontonumrets format ogiltigt");
		}
		accountPanel.remove(transactionInfo);
		accountPanel.add(transactionInfo);
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
				customerView.changeCustomerName(firstName, lastName);
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
			customerView.setCustomerClosingInfo(list);
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
		//Hämtar tabellens "tableModel"
		DefaultTableModel model = (DefaultTableModel) customerView.getTableAccounts().getModel();
		//Sätter radantalet till 0 för att rensa det gamla.
		model.setRowCount(0);
		model.setColumnCount(4);
		Object rowData[] = new Object[5];
		//Loopar igenom listan med konton och för in dessa i tabellen.
		for(int i = 0; i < list.size(); i++) {
			for(int y = 0; y < list.get(i).length; y++) {
				rowData[y] = list.get(i)[y];
				}
			model.addRow(rowData);
			}
		}
}
