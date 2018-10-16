package com.njit.sharefair.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.njit.sharefair.controllers.ExpenseController;
import com.njit.sharefair.models.AddExpenseResponse;
import com.njit.sharefair.models.Expense;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("services/ExpenseService")

public class ExpenseService {

	ExpenseController expenseController = new ExpenseController();

	@GET
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public AddExpenseResponse addExpense(@QueryParam("payerid") String payerId, @QueryParam("payeeid") String payeeId,
			@QueryParam("amount") double amount, @QueryParam("description") String description,
			@QueryParam("date") String date) {
		return expenseController.addExpense(payerId, payeeId, amount, description, date);
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		createFolderIfNotExists("/uploads");
		String uploadedFileLocation = "/uploads/"
				+ fileDetail.getFileName();

		// save it
		expenseController.uploadFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;

		return Response.status(200).entity(output).build();

	}
	
	@GET 
	   @Path("/expenses") 
	   @Produces(MediaType.APPLICATION_JSON) 

	   public List<Expense> getExpenses(){ 
	      return expenseController.getExpenses(); 
	   } 
	
	@GET
	  @Path("/export")
	  @Produces("application/vnd.ms-excel")
	  public Response getFile() {
	    //LOG.info("get - Function begins from here");

	    File file = new File("/home/test/file.xls");

	    ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition",
	      "attachment; filename=new-excel-file.xls");
	    response.header("Content-Type","application/octet-stream");
	    return response.build();

	  }
	
	private void createFolderIfNotExists(String dirName)
			throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
}
