package com.njit.sharefair.services;

import java.util.List; 
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.njit.sharefair.controllers.UserController;
import com.njit.sharefair.models.Expense;
import com.njit.sharefair.models.Friends;
import com.njit.sharefair.models.LoginResponse;
import com.njit.sharefair.models.RegisterResponse;
import com.njit.sharefair.models.UpdateResponse;
import com.njit.sharefair.models.User;  
@Path("services/UserService") 

public class UserService {  
   UserController userController = new UserController();  
   @GET 
   @Path("/users") 
   @Produces(MediaType.APPLICATION_JSON) 

   public List<User> getUsers(){ 
      return userController.getAllUsers(); 
   }  
   
   @GET 
   @Path("/userfromemail") 
   @Produces(MediaType.APPLICATION_JSON) 

   public User getUserFromEmail(@QueryParam("email") String email){ 
      return userController.getUserFromEmail(email); 
   }  
   
   @GET 
   @Path("/userid") 
   @Produces(MediaType.APPLICATION_JSON) 

   public User getUserId(@QueryParam("usertoken") String accessToken){ 
      return userController.getUserId(accessToken); 
   }     
   
   @GET 
   @Path("/expenses") 
   @Produces(MediaType.APPLICATION_JSON) 

   public List<Expense> getExpenses(){ 
      return userController.getExpenses(); 
   }  
   
   @GET 
   @Path("/friends") 
   @Produces(MediaType.APPLICATION_JSON) 

   public List<Friends> getFriends(@QueryParam("UserId") String UserId){ 
      return userController.getFriends(UserId); 
   }  
   
   @GET 
   @Path("/invite") 
   @Produces(MediaType.APPLICATION_JSON) 

   public boolean invite(@QueryParam("email") String email){ 
      userController.invite(email); 
      return true;
   }  
   
   @GET 
   @Path("/authenticate") 
   @Produces(MediaType.APPLICATION_JSON) 

   public LoginResponse authenticate(@QueryParam("username") String username, @QueryParam("password") String password){ 
      return userController.authenticate(username, password); 
   }  
   
   @GET
   @Path("/register") 
   @Produces(MediaType.APPLICATION_JSON) 
   
   public RegisterResponse register(@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname, @QueryParam("email") String email, @QueryParam("password") String password) {
	   return userController.register(firstname, lastname, email, password); 
   }
   
   @GET
   @Path("/update") 
   @Produces(MediaType.APPLICATION_JSON) 
   
   public UpdateResponse update(@QueryParam("firstname") String firstname, @QueryParam("email") String email, @QueryParam("password") String password, @QueryParam("phoneNo") String phoneNo, @QueryParam("AccessToken") String AccessToken) {
	   return userController.update(firstname, email, password, phoneNo, AccessToken); 
   }
   
   
}