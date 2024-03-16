package christianchofinal;

import java.io.IOException;

public class Transaction {
	
	private String date;
	private double amount; // The amount of the transaction.
	private String description; // A description of the transaction for the user to fill in.
	private String deposit; // true if a deposit. false if a withdrawal.
	private boolean posted; // A posted transaction is one that has been fully processed by a bank.
	
	private Transaction(TransactionBuilder builder) {
		this.date = builder.date;
		this.amount = builder.amount;
		this.description = builder.description;
		this.deposit = builder.deposit;
		this.posted = builder.posted;
	}
	
	// Getters
	public String getdate () { return date; }
	public double getamount () { return amount; }
	public String getdescription () { return description; }
	public String getdeposit () { return deposit; }
	public boolean getposted () { return posted; }

	public void print () {
		System.out.print("\t" + date + "\t" + amount + "\t" + "Posted? ");
		if (posted)
			System.out.println("Yes");
		else
			System.out.println("No");
		System.out.println("\t" + description);
		System.out.println("------------------------------------------------------------");
	}
	
	// Copy another Transaction
	public void copyFrom(Transaction t){
		date = t.getdate();
		amount = t.getamount();
		description = t.getdescription();
		posted = t.getposted();
	}
	
	// Writes the transaction to the file and in order of ID
	public void writeToFile() throws IOException {

		try {
			
			MoneyCents.writer.write(
					date + "," +
					String.valueOf(amount) + "," +
					description + "," +
					String.valueOf(posted)
					);
			MoneyCents.writer.newLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Converts data into String with formatting to display on JScrollPane
	public String displayFormat() {
		
		String content = "";
		String temp = "";
		content += "<html><pre>Date: " + date + "	"; // Add date content

		// Formatting for amount
		// Change font color depending on whether the amount is a deposit or a withdrawal
		if (amount < 0)
			temp = "<font color=red>";
		else if (amount > 0)
			temp = "<font color=green>";
		content += temp + "$ " + String.valueOf(amount); // Add amount content
		// Look at the last two chars of the amount as String to check if it has a decimal
		// Formatting to show two 0's instead of one
		temp = String.valueOf(amount);
		temp = temp.substring(temp.length() - 2);
		if (temp.contains("."))
			temp = "0";
		else
			temp = "";
		content += temp  + "</font>	";
		if (String.valueOf(amount).length() < 5)
			content += "	";
			
		if (posted) // Add posted content
			content += "POSTED";
		else
			content += "UNPOSTED";
		
		content += "<br><i>" + description + "</i></pre></html>"; // Add description content
		
		return content;
	}
	
	public static class TransactionBuilder {
		private String date;
		private double amount;
		private String description;
		private String deposit;
		private boolean posted;
		
		public TransactionBuilder() {
			this.date = "00000000";
			this.amount = 0.0;
			this.description = "No description.";
			this.deposit = "N";
			this.posted = true;
		}
		
		public TransactionBuilder(String s) {
			String[] data = s.split(",");
			this.date = data[0];
			this.amount = Double.parseDouble(data[1]);
			this.description = data[2];
			this.deposit = (amount < 0) ? "W" : (amount > 0) ? "D" : "N";
			this.posted = Boolean.parseBoolean(data[3]);
		}
		
		public TransactionBuilder setDate(String date) {
			this.date = date;
			return this;
		}
		
		public TransactionBuilder setAmount(double amount) {
			this.amount = amount;
			this.deposit = (amount < 0) ? "W" : (amount > 0) ? "D" : "N";
			return this;
		}
		
		public TransactionBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		
		public TransactionBuilder setPosted(boolean posted) {
			this.posted = posted;
			return this;
		}
		
		public Transaction build() {
			return new Transaction(this);
		}
	}
}
