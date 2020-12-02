package frelab8;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuiAccountView {
	private JPanel mainPanel = new JPanel(); //Huvudpanelen för kontoVyn "GuiAccountView".
	private JPanel accountPanel = new JPanel(); //Panelen som ska innehålla information om ett konto (transaktioner).
	private JPanel accountButtonPanel = new JPanel(); //Panel som ska hålla interaktionsknappar till det valda kontot.
	private JButton deleteAccountButton = new JButton("Ta bort konto");
	private JButton depositButton = new JButton("Sätt in pengar");
	private JButton withdrawButton = new JButton("Ta ut pengar");
	private JPanel amountPanel = new JPanel(); //Panel som innehåller interaktionskomponenter till den popupruta som ska låta användaren sätta in eller ta ut pengar från ett valt konto.
	private JTextField amountInput = new JTextField(10);
	private JLabel accountLabel = new JLabel(); //Information om det valda kontot (inte transaktioner).
	private JList<Object> transactionInfo = new JList<Object>(); //Transaktioner på det valda kontot.
	private JList<Object> accountClosingInfo = new JList<Object>(); //Information om kontot vid borttagning av kontot.
	
	public GuiAccountView(Gui gui, BankLogic bank) {
		AccountViewController accountViewController = new AccountViewController(gui, this, bank); //Controller till kontoVyn som ska hantera events i vyn.
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(new GridBagLayout());
		accountPanel.add(transactionInfo);
		accountButtonPanel.add(depositButton);
		accountButtonPanel.add(withdrawButton);
		accountButtonPanel.add(deleteAccountButton);
		accountButtonPanel.setLayout(new FlowLayout());
		deleteAccountButton.setActionCommand("deleteAccount");
		deleteAccountButton.addActionListener(accountViewController);
		depositButton.setActionCommand("deposit");
		depositButton.addActionListener(accountViewController);
		withdrawButton.setActionCommand("withdraw");
		withdrawButton.addActionListener(accountViewController);
		accountPanel.setBorder(BorderFactory.createTitledBorder("Transaktioner"));
		accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));
		
		amountPanel.add(amountInput);
		
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(accountLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(accountPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		mainPanel.add(accountButtonPanel, c);
	}

	public JList<Object> getTransactionInfo() {
		return transactionInfo;
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public JPanel getAccountPanel() {
		return accountPanel;
	}

	public JPanel getAmountPanel() {
		return amountPanel;
	}

	public JTextField getAmountInput() {
		return amountInput;
	}

	public JList<Object> getAccountClosingInfo() {
		return accountClosingInfo;
	}

	public void setTransactionInfo(JList<Object> transactionInfo) {
		this.transactionInfo = transactionInfo;
	}

	public void setAccountLabel(String text) {
		this.accountLabel.setText(text);
	}

	public void setAccountClosingInfo(JList<Object> accountClosingInfo) {
		this.accountClosingInfo = accountClosingInfo;
	}
	
}
