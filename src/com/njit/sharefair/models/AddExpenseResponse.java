package com.njit.sharefair.models;

public class AddExpenseResponse {

	private boolean expenseAdded;
	private boolean refreshDashboard;

	public boolean isExpenseAdded() {
		return expenseAdded;
	}

	public void setExpenseAdded(boolean expenseAdded) {
		this.expenseAdded = expenseAdded;
	}

	public boolean isRefreshDashboard() {
		return refreshDashboard;
	}

	public void setRefreshDashboard(boolean refreshDashboard) {
		this.refreshDashboard = refreshDashboard;
	}

}
