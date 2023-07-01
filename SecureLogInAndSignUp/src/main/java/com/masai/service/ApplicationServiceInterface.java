package com.masai.service;


//import java.util.List;

import com.masai.exception.ApplicationException;
import com.masai.model.User;

public interface ApplicationServiceInterface {


	User logInUserDetails(String email) throws Exception;

	User signUpInUser(User user) throws ApplicationException;

//	List<User> getAllUser(String name) throws ApplicationException;


}
