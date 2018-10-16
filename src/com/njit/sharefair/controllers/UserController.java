package com.njit.sharefair.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.njit.sharefair.models.Expense;
import com.njit.sharefair.models.Friends;
import com.njit.sharefair.models.LoginResponse;
import com.njit.sharefair.models.RegisterResponse;
import com.njit.sharefair.models.UpdateResponse;
import com.njit.sharefair.models.User;
import com.njit.sharefair.network.DBConnection;
import com.njit.sharefair.network.EmailUtilities;

public class UserController {
	public List<User> getAllUsers() {

		List<User> userList = new ArrayList<User>();

		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
			if (resultSet != null) {
				while (resultSet.next()) {
					User user = new User();
					user.setId((String) resultSet.getObject("User_Id"));
					user.setEmail(resultSet.getNString("Email"));
					user.setPhoneNumber(resultSet.getString("PhoneNo"));
					user.setFirstName(resultSet.getString("First_Name"));
					user.setLastName(resultSet.getString("Last_Name"));
					user.setProfilePicture(resultSet.getNString("Profile_Picture"));
					userList.add(user);
				}
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	public User getUserFromEmail(String email) {

		User user = new User();

		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from Users where Email='" + email + "'");
			if (resultSet != null && resultSet.next()) {
				user.setId((String) resultSet.getObject("User_Id"));
				user.setEmail(resultSet.getNString("Email"));
				user.setPhoneNumber(resultSet.getString("PhoneNo"));
				user.setFirstName(resultSet.getString("First_Name"));
				user.setLastName(resultSet.getString("Last_Name"));
				user.setProfilePicture(resultSet.getNString("Profile_Picture"));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public void invite(String email) {
		EmailUtilities.sendEmail(email, "You are invited");
	}

	public User getUserId(String accessToken) {
		User user = new User();
		try {
			String userId = "";
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			String queryAccessToken = "select User_Id from Sessions where Access_Token='" + accessToken
					+ "' and isActive=1";
			ResultSet resultSetUser = statement.executeQuery(queryAccessToken);
			if (resultSetUser != null && resultSetUser.next()) {
				userId = (String) resultSetUser.getObject("User_Id");
			}
			try {
				String queryUser = "select * from Users where User_Id='" + userId + "'";
				ResultSet resultSet = statement.executeQuery(queryUser);
				if (resultSet != null && resultSet.next()) {
					user.setId((String) resultSet.getObject("User_Id"));
					user.setEmail(resultSet.getNString("Email"));
					user.setPhoneNumber(resultSet.getString("PhoneNo"));
					user.setFirstName(resultSet.getString("First_Name"));
					user.setLastName(resultSet.getString("Last_Name"));
					user.setProfilePicture(resultSet.getNString("Profile_Picture"));
				}
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
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

	private void getExcel() {
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
	}

	public List<Friends> getFriends(String UserId) {
		List<Friends> friends = new ArrayList<Friends>();

		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			String queryFriend = "SELECT F.FID, F.User_Id, F.Friend_Id from Friends F, Users U where F.User_Id='"
					+ UserId + "'";
			ResultSet resultSet = statement.executeQuery(queryFriend);
			if (resultSet != null) {
				while (resultSet.next()) {
					Friends friend = new Friends();
					String friendId = (String) resultSet.getObject("Friend_Id");
					String queryUsername = "SELECT P.Username, P.UserId from Passwords P, Friends F where P.UserId = '"
							+ friendId + "'";
					ResultSet resultSet2 = statement.executeQuery(queryUsername);
					if (resultSet2 != null && resultSet2.next()) {
						friend.setUsername((String) resultSet2.getObject("Username"));
						friend.setUser_Id((String) resultSet2.getObject("UserId"));
						friends.add(friend);
					}
				}
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friends;
	}

	public LoginResponse authenticate(String username, String password) {
		LoginResponse loginResponse = new LoginResponse();

		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select UserId from Passwords p where p.Username='" + username
					+ "' and p.Password='" + password + "'");
			if (resultSet != null && resultSet.next()) {
				String userId = (String) resultSet.getObject("UserId");
				SecureRandom random = new SecureRandom();
				byte bytes[] = new byte[128];
				random.nextBytes(bytes);
				UUID sessionId = UUID.randomUUID();
				String accessToken = random.toString();
				Statement insertStatement = connection.createStatement();
				String query = "insert into Sessions values (CONVERT(uniqueidentifier,'" + sessionId.toString()
						+ "'), CONVERT(uniqueidentifier,'" + userId.toString() + "'), 1, '" + accessToken + "')";
				insertStatement.executeUpdate(query);
				loginResponse.setAccessToken(accessToken);
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginResponse;
	}

	public RegisterResponse register(String firstname, String lastname, String email, String password) {

		RegisterResponse registerResponse = new RegisterResponse();

		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			UUID User_Id = UUID.randomUUID();
			UUID Pwd_Id = UUID.randomUUID();
			statement.executeUpdate("insert into Users values (CONVERT(uniqueidentifier,'" + User_Id.toString()
					+ "'), '" + email + "', '9876543210', '" + firstname + "', '" + lastname
					+ "', 'https://upload.wikimedia.org/wikipedia/commons/7/70/User_icon_BLACK-01.png')");
			statement.executeUpdate("insert into Passwords values (CONVERT(uniqueidentifier,'" + Pwd_Id.toString()
					+ "'), CONVERT(uniqueidentifier,'" + User_Id.toString() + "'), '" + password + "', '" + email
					+ "' )");

			registerResponse.setRegistrationDone(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return registerResponse;
	}

	public UpdateResponse update(String firstname, String email, String password, String phoneNo, String AccessToken) {
		UpdateResponse updateResponse = new UpdateResponse();

		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT S.User_Id from Sessions S where S.Access_Token = '" + AccessToken + "'  ");
			if (resultSet != null && resultSet.next()) {
				String userId = (String) resultSet.getObject("User_Id");
				String queryUpdate = "UPDATE Users SET First_Name= '" + firstname + "' , Email= '" + email
						+ "' , PhoneNo= '" + phoneNo + "' where Users.User_Id = '" + userId + "'";
				statement.executeUpdate(queryUpdate);

				String queryUpdate2 = "UPDATE Passwords SET Password= '" + password + "' where Passwords.UserId= '"
						+ userId + "'";
				statement.executeUpdate(queryUpdate2);

				updateResponse.setUpdateDone(true);
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateResponse;
	}

}
