package frelab8;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/*
 * Denna klass är huvudvyn där menyn + menyns vyer finns tillsammans med den cardlayout som ska hantera navigationen i systemet.
 * Klassen innehåller alltså instanser av alla andra vyer för att bestämma vilket vy som är aktiv (längst fram i cardlayouten).
 * 
 * @author Fredrik Larsson, frelab-8
 */

public class Gui implements ActionListener {
	BankLogic bank = new BankLogic();
	GuiCustomerView customerView; //Medlemsvariabel som ska innehålla en referens till vyn för en vald kund.
	GuiStartView startView; //Medlemsvariabel som ska innehålla en referens till vyn för startsidan på systemet.
	GuiAccountView accountView; //Medlemsvariabel som ska innehålla en referens till vyn för ett valt konto.
	JFrame frame = new JFrame("Banksystem");
	CardLayout cardLayout = new CardLayout();
	Container c = frame.getContentPane();
	JPanel customersPanel = new JPanel(); //Panel som ska innehålla systemets alla kunder i en lista.
	JPanel createCustomerPanel = new JPanel(); //Panel som ska innehålla interaktionskomponenter för att skapa en ny kund.
	JPanel createCustomerView = new JPanel(); //Panel som ska innehålla "createCustomerPanel".
	JPanel allCustomerView = new JPanel(); //Denna ska innehålla komponenter för att presentera alla kunder i systemet.
	JButton createCustomerButton = new JButton("Skapa kund");
	JButton chooseCustomerButton = new JButton("Välj kund");
	JTable tableCustomers = new JTable(); //En tabell med alla kunder som systemet har förtillfället.
	String currentAccountNumber; //Det nuvarande konto som användaren befinner sig på.
	String currentIdNumber = ""; //Den nuvarande användaren som systemet har som fokus.
	JTextField createCustomerIdNumber = new JTextField("Personnummer", 15);
	JTextField createCustomerFirstname = new JTextField("Förnamn");
	JTextField createCustomerLastname = new JTextField("Efternamn");
	
	public Gui() {
		c.setLayout(cardLayout); //Ger fönstrets "content pane" en cardlayout som ska ligga som grund för navigeringen över systemets olika vyer.
		//Skapar instanser av de tre olika vyerna som systemet kommer ha (startVyn, kundVyn och kontoVyn).
		startView = new GuiStartView(this);
		customerView = new GuiCustomerView(this, bank);
		accountView = new GuiAccountView(this, bank);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Meny");
		JMenuItem searchCustomer = new JMenuItem("Sök kund");
		searchCustomer.addActionListener(this);
		searchCustomer.setActionCommand("searchCustomer");
		JMenuItem getAllCustomer = new JMenuItem("Hämta alla kunder");
		getAllCustomer.addActionListener(this);
		getAllCustomer.setActionCommand("getAllCustomer");
		JMenuItem createCustomer = new JMenuItem("Skapa ny kund");
		createCustomer.addActionListener(this);
		createCustomer.setActionCommand("createCustomer");
		GridBagConstraints cGrid = new GridBagConstraints(); //Skapar en GridBagConstraints för GridBagLayout används i olika paneler på systemet.
		
		//Lägger till de komponenter som ska finnas med när man vill hämta alla kunder.
		customersPanel.add(tableCustomers);
		customersPanel.setLayout(new BoxLayout(customersPanel, BoxLayout.PAGE_AXIS));
		customersPanel.setBorder(BorderFactory.createTitledBorder("Kunder"));
		chooseCustomerButton.addActionListener(this);
		chooseCustomerButton.setActionCommand("chooseCustomer");
		
		//Lägger till de komponenter som ska finnas på sidan då man ska skapa en ny kund.
		createCustomerPanel.add(createCustomerIdNumber);
		createCustomerPanel.add(createCustomerFirstname);
		createCustomerPanel.add(createCustomerLastname);
		createCustomerPanel.add(Box.createVerticalStrut(10));
		createCustomerPanel.add(createCustomerButton);
		createCustomerPanel.setLayout(new BoxLayout(createCustomerPanel, BoxLayout.PAGE_AXIS));
		createCustomerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Skapa ny kund"), new EmptyBorder(50, 50, 50, 50)));
		createCustomerButton.addActionListener(this);
		createCustomerButton.setActionCommand("createCustomerButton");
		
		//Sätter layouten på de yttersta panelerna som innehåller olika vyer till layouten "GridBagLayout".
		createCustomerView.setLayout(new GridBagLayout());
		allCustomerView.setLayout(new GridBagLayout());
		
		//Här adderas komponenterna till vyn som ska låta användaren skapa en ny kund.
		cGrid.gridx = 0;
		cGrid.gridy = 0;
		createCustomerView.add(createCustomerPanel, cGrid);
		
		//Här adderas komponenterna till vyn som ska presentera alla kunder i systemet via en tabell.
		cGrid.gridx = 0;
		cGrid.gridy = 0;
		allCustomerView.add(customersPanel, cGrid);
		cGrid.gridx = 0;
		cGrid.gridy = 1;
		allCustomerView.add(chooseCustomerButton, cGrid);
		
		menuBar.add(menu); //Adderar den menu jag ska ha i mitt menyfält.
		//Adderar menyobjekten i menyn för att kunna hämta alla kunder eller skapa en ny kund.
		menu.add(searchCustomer);
		menu.add(createCustomer);
		menu.add(getAllCustomer);
		frame.setSize(800, 600); //Fönstret "frame" ska ha storleken 800*600.
		frame.setJMenuBar(menuBar);//Fönstret ska ha menufältet "menuBar".
		//Här adderas alla olika vyer i systemet till container "c" som refererar till fönstrets "content pane" som har layouten "cardlayout".
		c.add(startView.getMainPanel(), "startView");
		c.add(customerView.getMainPanel(), "customerView");
		c.add(accountView.getMainPanel(), "accountView");
		c.add(allCustomerView, "allCustomerView");
		c.add(createCustomerView, "createCustomerView");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Gui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command; //Den interaktion som användaren har gjort och resulterat i en "actioncommand" sträng.
		//Kontrollerar ifall kommandot är av typen JMenuItem eller en JButton.
		if(e.getSource() instanceof JMenuItem) {
			command = ((JMenuItem) e.getSource()).getActionCommand();
		} else {
			command = ((JButton) e.getSource()).getActionCommand();
		}
		//Tar reda på vilken av knapparna eller menyalternativen som användaren har tryckt in.
		switch(command) {
		case "getAllCustomer": //När alla kunder i systemet ska hämtas och presenteras.
			addRowToCustomerTable();
			customersPanel.remove(tableCustomers);
			customersPanel.add(tableCustomers);
			customersPanel.revalidate();
			frame.repaint();
			cardLayout.show(c, "allCustomerView"); //För en specifik panel baserat på "allCustomerView" längst fram i "cardlayouten" som containern "c" har.
			break;
		case "chooseCustomer": //När man väljer en specifik kund i kundtabellen som presenterar alla kunder.
			//Lägg till idNummer för vald kund, idNumret hämtas från kundtabellen från den valda raden i kolumn 0 där personnumren är placerade.
			try {
				currentIdNumber = tableCustomers.getValueAt(tableCustomers.getSelectedRow(), 0).toString();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(frame, "Ingen kund vald");
				return;
			}
			customerView.getCustomerViewController().addRowToTable(); //Kallar på en metod i kundvyns controller som gör att tabellen med en kunds alla konton tas bort och byggs om pånytt för den nu valda kunden.
			customerView.getAccountsPanel().remove(customerView.getTableAccounts());
			customerView.getAccountsPanel().add(customerView.getTableAccounts());
			customerView.getAccountsPanel().revalidate();
			frame.repaint();
			cardLayout.show(c, "customerView"); //För panelen för kundVyn längst fram i cardlayouten.
			break;
		case "createCustomer": //Här tar systemet fram vyn för att skapa en ny kund.
			cardLayout.show(c, "createCustomerView"); //Lägger panelen för vyn att skapa en ny kund längst fram i cardlayouten.
			break;
		case "createCustomerButton": //Här skapar systemet en ny kund.
			String idNumber = createCustomerIdNumber.getText();
			String firstName = createCustomerFirstname.getText();
			String lastName = createCustomerLastname.getText();
			Boolean check = bank.createCustomer(firstName, lastName, idNumber);
			//Kontrollerar ifall instansen bank lyckades skapa en ny kund genom metoden "createCustomer"
			if(check) {
				JOptionPane.showMessageDialog(frame, "Kunden finns nu tillagd i systemet");
			} else {
				JOptionPane.showMessageDialog(frame, "Kunden kunde inte läggas till i systemet");
			}
			cardLayout.show(c, "startView"); //Lägger startvyn längst fram i cardlayouten.
			break;
		case "searchCustomer": //Lägger startvyn längst fram i cardlayouten, en väg tillbaka till startsidan ifall användaren befinner sig på andra sidor.
			cardLayout.show(c, "startView");
			break;
		}
	}
	
	//Tar bort alla rader och bygger om på nytt tabellen som hanterar alla kunder i systemet.
	private void addRowToCustomerTable() {
		ArrayList<String[]> list = bank.getAllCustomers();
		DefaultTableModel model = (DefaultTableModel) tableCustomers.getModel();
		model.setRowCount(0);
		model.setColumnCount(3);
		Object rowData[] = new Object[5];
		for(int i = 0; i < list.size(); i++) {
			for(int y = 0; y < list.get(i).length; y++) {
				rowData[y] = list.get(i)[y];
			}
			model.addRow(rowData);
		}
	}
	
	/*private void changeCustomerRow(String firstName, String lastName) {
		tableAccounts.setValueAt(firstName, 0, 1);
		tableAccounts.setValueAt(lastName, 0, 2);
	}*/
	
	public GuiCustomerView getCustomerView() {
		return customerView;
	}
	
	public CardLayout getCardLayout() {
		return cardLayout;
	}
	
	public Container getC() {
		return c;
	}
	
	public GuiAccountView getAccountView() {
		return accountView;
	}

	public String getCurrentAccountNumber() {
		return currentAccountNumber;
	}
	
	public void setCurrentAccountNumber(String currentAccountNumber) {
		this.currentAccountNumber = currentAccountNumber;
	}
	
	public void setCurrentIdNumber(String currentIdNumber) {
		this.currentIdNumber = currentIdNumber;
	}
	
}
