package christianchofinal;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DeleteConfirm extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel dialog;
	private JButton okay;
	private JButton cancel;
	
	public DeleteConfirm (Transaction t) {
		
		super("Confirm Delete");
		
		final Transaction tempTransaction = new Transaction.TransactionBuilder()
				.setDate(t.getdate())
				.setAmount(t.getamount())
				.setDescription(t.getdescription())
				.setPosted(t.getposted())
				.build();
		tempTransaction.copyFrom(t);
		
		setLayout(new FlowLayout());
		setSize(480,100);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panel = new JPanel(new GridBagLayout());
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel.setSize(480,100);
		
		dialog = new JLabel("<html>Confirm deletion of " + "\"" + MoneyCents.transactions.get(MoneyCents.place).getdescription() + "\"?");
		okay = new DeleteButtonDecorator(new JButton("Confirm"), tempTransaction);
		cancel = new JButton("Cancel");
		
		okay.setPreferredSize(new Dimension(220,25));
		cancel.setPreferredSize(new Dimension(220,25));
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(dialog,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(okay,c);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(cancel,c);
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		add(panel);
		
	}
	
	private class DeleteButtonDecorator extends JButton {
		
		private Transaction transaction;
		
		public DeleteButtonDecorator(JButton button, Transaction transaction) {
			super(button.getText());
			this.transaction = transaction;
			addActionListener(new DeleteButtonListener());
		}
		
		private class DeleteButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Deleting item: ");
				MoneyCents.transactions.get(MoneyCents.place).print();
				MoneyCents.transactions.remove(MoneyCents.place);
				System.out.println("Deleted");
				MoneyCents.transactionsContent = new String[MoneyCents.transactions.size()];
				for (int i = 0; i < MoneyCents.transactions.size(); i++) {
					MoneyCents.transactionsContent[i] = MoneyCents.transactions.get(i).displayFormat();
				}
				if (transaction.getposted())
					MoneyCents.postedAmount -= transaction.getamount();
				MoneyCents.expectedAmount -= transaction.getamount();
				MoneyCents.mainwindow.refresh();
				dispose();
			}
		}
	}
	
}
