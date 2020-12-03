package frelab8;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/*
 * Detta är kundvyn som inkluderar alla komponenter och hur dessa ska visas när en specifik kund är vald av användaren.
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class GuiCustomerView {
	Gui gui;
	CustomerViewController customerViewController; //Vyns egna controller där lyssnaren finns för alla interaktionskomponenter.
	private JPanel mainPanel = new JPanel(); //Huvudpanelen för denna vy som alla komponenter ska ligga inom.
	private JPanel accountsPanel = new JPanel(); //Den panel där alla konton ska presenteras för en specifik kund.
	private JPanel customerPanel = new JPanel();
	private JPanel customerButtonPanel = new JPanel(); //Knappar som ska finnas i kundvyn.
	private JPanel changeCustomerNamePanel = new JPanel(); //Den panel som innehåller interaktionskomponenterna för att byta namn på kund.
	private JTextField changeFirstNameInput = new JTextField("Förnamn", 15);
	private JTextField changeLastNameInput = new JTextField("Efternamn", 15);
	private JLabel customerInfo = new JLabel("Kund info");
	private JButton deleteCustomerButton = new JButton("Ta bort kund");
	private JButton changeCustomerNameButton = new JButton("Ändra kundnamn");
	private JButton chooseAccountButton = new JButton("Välj konto");
	private JButton createCreditAccountButton = new JButton("Skapa kreditkonto");
	private JButton createSavingsAccountButton = new JButton("Skapa sparkonto");
	private JTable tableAccounts = new JTable(); //Tabell som ska innehålla alla konton för en specifik kund.
	private JList<Object> customerClosingInfo = new JList<Object>(); //Information om en kund vid borttagning av kunden ur systemet.
	
	public GuiCustomerView(Gui gui, BankLogic bank) {
		this.gui = gui;
		customerViewController = new CustomerViewController(gui, this, bank);
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(new GridBagLayout());
		accountsPanel.add(tableAccounts);
		accountsPanel.setLayout(new BoxLayout(accountsPanel, BoxLayout.PAGE_AXIS));
		accountsPanel.setBorder(BorderFactory.createTitledBorder("Konton"));
		customerPanel.add(customerInfo);
		customerPanel.add(accountsPanel);
		customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.PAGE_AXIS));
		chooseAccountButton.addActionListener(customerViewController);
		chooseAccountButton.setActionCommand("chooseAccount");
		customerButtonPanel.setLayout(new GridLayout(4, 1, 10, 5));
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
		
		//Alla dessa komponenter ska ligga inom samma vy.
		c.gridx = 1;
		c.gridy = 0;
		mainPanel.add(customerButtonPanel, c);
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(customerPanel, c);
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

	public JList<Object> getCustomerClosingInfo() {
		return customerClosingInfo;
	}
	
	public CustomerViewController getCustomerViewController() {
		return customerViewController;
	}
	
	public void setCustomerInfo(String customerInfo) {
		this.customerInfo.setText(customerInfo);
	}

	public void setCustomerClosingInfo(JList<Object> list) {
		this.customerClosingInfo = list;
	}
	
}
