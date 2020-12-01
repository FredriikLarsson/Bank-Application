package frelab8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class StartViewController implements ActionListener{
	private Gui gui;
	private GuiStartView guiStartView;
	private String currentIdNumber;
	
	public StartViewController(Gui gui, GuiStartView guiStartView) {
		this.gui = gui;
		this.guiStartView = guiStartView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GuiCustomerView customerView = gui.getCustomerView();
		currentIdNumber = guiStartView.getCustomerIdNumberInput().getText();
		gui.setCurrentIdNumber(currentIdNumber);
		customerView.getAccountsPanel().remove(customerView.getTableAccounts());
		customerView.getAccountsPanel().add(customerView.getTableAccounts());
		customerView.getAccountsPanel().revalidate();
		gui.frame.repaint();
		gui.getCardLayout().show(gui.getC(), "customerView");
	}
	

}
