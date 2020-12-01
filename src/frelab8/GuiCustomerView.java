package frelab8;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GuiCustomerView {
	Gui gui;
	CustomerViewController customerViewController;
	private JPanel mainPanel = new JPanel();
	private JPanel accountsPanel = new JPanel();
	private JPanel customerButtonPanel = new JPanel();
	private JPanel changeCustomerNamePanel = new JPanel();
	private JTextField changeFirstNameInput = new JTextField("Förnamn", 15);
	private JTextField changeLastNameInput = new JTextField("Efternamn", 15);
	private JButton deleteCustomerButton = new JButton("Ta bort kund");
	private JButton changeCustomerNameButton = new JButton("Ändra kundnamn");
	private JButton chooseAccountButton = new JButton("Välj konto");
	private JButton createCreditAccountButton = new JButton("Skapa kreditkonto");
	private JButton createSavingsAccountButton = new JButton("Skapa sparkonto");
	private JTable tableAccounts = new JTable();
	private JList<Object> customerClosingInfo = new JList<Object>();
	
	public GuiCustomerView(Gui gui, BankLogic bank) {
		this.gui = gui;
		customerViewController = new CustomerViewController(gui, this, bank);
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(new GridBagLayout());
		accountsPanel.add(tableAccounts);
		accountsPanel.setLayout(new BoxLayout(accountsPanel, BoxLayout.PAGE_AXIS));
		accountsPanel.setBorder(BorderFactory.createTitledBorder("Konton"));
		chooseAccountButton.addActionListener(customerViewController);
		chooseAccountButton.setActionCommand("chooseAccount");
		customerButtonPanel.setLayout(new BoxLayout(customerButtonPanel, BoxLayout.PAGE_AXIS));
		customerButtonPanel.add(changeCustomerNameButton);
		customerButtonPanel.add(deleteCustomerButton);
		customerButtonPanel.add(createCreditAccountButton);
		customerButtonPanel.add(createSavingsAccountButton);
		deleteCustomerButton.addActionListener(customerViewController);
		deleteCustomerButton.setActionCommand("deleteCustomer");
		changeCustomerNameButton.addActionListener(customerViewController);
		changeCustomerNameButton.setActionCommand("changeCustomerName");
		createCreditAccountButton.addActionListener(customerViewController);
		createCreditAccountButton.setActionCommand("createCreditAccount");
		createSavingsAccountButton.addActionListener(customerViewController);
		createSavingsAccountButton.setActionCommand("createSavingsAccount");
		
		changeCustomerNamePanel.add(changeFirstNameInput);
		changeCustomerNamePanel.add(changeLastNameInput);
		
		c.gridx = 1;
		c.gridy = 0;
		mainPanel.add(customerButtonPanel, c);
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(accountsPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(chooseAccountButton, c);
	}

	public JPanel getAccountsPanel() {
		return accountsPanel;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JPanel getChangeCustomerNamePanel() {
		return changeCustomerNamePanel;
	}

	public JTextField getChangeFirstNameInput() {
		return changeFirstNameInput;
	}

	public JTextField getChangeLastNameInput() {
		return changeLastNameInput;
	}

	public JTable getTableAccounts() {
		return tableAccounts;
	}

	public void changeCustomerName (String firstName, String lastName) {
		tableAccounts.setValueAt(firstName, 0, 1);
		tableAccounts.setValueAt(lastName, 0, 2);
	}

	public JList<Object> getCustomerClosingInfo() {
		return customerClosingInfo;
	}

	public void setCustomerClosingInfo(Object[] list) {
		this.customerClosingInfo = new JList<Object>(list);
	}

	public CustomerViewController getCustomerViewController() {
		return customerViewController;
	}
	
}