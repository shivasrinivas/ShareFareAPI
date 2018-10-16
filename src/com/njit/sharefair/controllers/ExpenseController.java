package com.njit.sharefair.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.njit.sharefair.models.AddExpenseResponse;
import com.njit.sharefair.models.Expense;
import com.njit.sharefair.network.DBConnection;
import com.njit.sharefair.network.EmailUtilities;

public class ExpenseController {
	public AddExpenseResponse addExpense(String payerId, String payeeId, double amount, String description,
			String date) {
		AddExpenseResponse addExpenseResponse = new AddExpenseResponse();

		try {
			Connection connection = DBConnection.getConnection();
			UUID expenseId = UUID.randomUUID();
			UUID transactionId = UUID.randomUUID();
			Statement insertStatement = connection.createStatement();
			String queryExpense = "insert into [Expense] values (CONVERT(uniqueidentifier,'" + expenseId.toString()
					+ "'), CONVERT(uniqueidentifier,'" + payerId + "'), " + amount + ", '" + date + "', '" + description
					+ "')";
			String queryTransaction = "insert into [Transaction] values (CONVERT(uniqueidentifier,'"
					+ transactionId.toString() + "'), CONVERT(uniqueidentifier,'" + expenseId.toString() + "'), "
					+ amount + ", CONVERT(uniqueidentifier,'" + payeeId + "'), CONVERT(uniqueidentifier,'" + payerId
					+ "'))";

			insertStatement.executeUpdate(queryExpense);
			insertStatement.executeUpdate(queryTransaction);
			addExpenseResponse.setExpenseAdded(true);
			addExpenseResponse.setRefreshDashboard(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmailUtilities.sendEmail("shivasrinivasgadicherla@gmail.com", "Its working");
		return addExpenseResponse;
	}

	public List<Expense> getExpenses() {
		try {
			String filename = "/NewExcelFile.xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("No.");
			rowhead.createCell(1).setCellValue("Name");
			rowhead.createCell(2).setCellValue("Address");
			rowhead.createCell(3).setCellValue("Email");

			HSSFRow row = sheet.createRow((short) 1);
			row.createCell(0).setCellValue("1");
			row.createCell(1).setCellValue("Sankumarsingh");
			row.createCell(2).setCellValue("India");
			row.createCell(3).setCellValue("sankumarsingh@gmail.com");

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			System.out.println(ex);
		}
		List<Expense> expenses = new ArrayList<Expense>();

		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT t.Transaction_Id, t.Expense_Id, t.Amount_Owed, t.Payee_Id, t.Payer_Id, e.Amount, e.Date, e.Description, p.First_Name, p.Last_Name \n"
							+ "FROM [Expense] e, [Transaction] t, [Users] p where t.Expense_Id = e.Expense_Id AND t.Payee_Id = p.User_Id");
			if (resultSet != null) {
				while (resultSet.next()) {
					Expense expense = new Expense();
					expense.setTransactionId((String) resultSet.getObject("Transaction_Id"));
					expense.setExpenseId((String) resultSet.getObject("Expense_Id"));
					expense.setPayerId((String) resultSet.getObject("Payer_Id"));
					expense.setAmountOwed(resultSet.getDouble("Amount_Owed"));
					expense.setAmount(resultSet.getDouble("Amount"));
					expense.setDate(resultSet.getString("Date"));
					expense.setDescription(resultSet.getString("Description"));
					expense.setPayeeFName(resultSet.getString("First_Name"));
					expense.setPayeeLName(resultSet.getString("Last_Name"));
					expense.setPayeeId((String) resultSet.getObject("Payee_Id"));
					expenses.add(expense);
				}
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expenses;
	}
	
	// save uploaded file to new location
	public void uploadFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
