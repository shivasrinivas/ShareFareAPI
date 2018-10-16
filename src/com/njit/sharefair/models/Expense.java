package com.njit.sharefair.models;

public class Expense {
	private String expenseId;
	private String payerId;
	private double amount;
	private String date;
	private String transactionId;
	private double amountOwed;
	private String payeeId;
	private String description;
	private String payeeFName;
	private String payeeLName;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayeeFName() {
		return payeeFName;
	}

	public void setPayeeFName(String payeeFName) {
		this.payeeFName = payeeFName;
	}

	public String getPayeeLName() {
		return payeeLName;
	}

	public void setPayeeLName(String payeeLName) {
		this.payeeLName = payeeLName;
	}
	
	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmountOwed() {
		return amountOwed;
	}

	public void setAmountOwed(double amountOwed) {
		this.amountOwed = amountOwed;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

}
