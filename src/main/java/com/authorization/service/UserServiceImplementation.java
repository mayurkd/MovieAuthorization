package com.authorization.service;

import java.util.List;
import java.util.Optional;

import com.authorization.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authorization.model.User;
import com.authorization.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User addUser(User user) {
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		Optional<User> findById = userRepository.findById(user.getLoginId());
		if (findById.isEmpty() && user != null && password.equals(confirmPassword)) {
			return userRepository.saveAndFlush(user);

		}
		return null;
	}

	@Override
	public boolean loginUser(String loginid, String password) {
		User user1 = userRepository.validateUser(loginid, password);
		System.out.println("User: " + user1.getLoginId());
		if (user1 != null) {
			return true;
		}else{
			return false;
		}

	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = userRepository.findAll();

		if (userList != null & userList.size() > 0) {
			return userList;
		} else
			return null;
	}

	@Override
	public Object passwordReset(String userId, User user)  {
		Optional<User> findUser = userRepository.findById(userId);
		if(findUser.isPresent()) {
			String answer = findUser.get().getAnswer();
			if(user.getAnswer().equals(answer) && user.getPassword().equals(user.getConfirmPassword())) {
				findUser.get().setPassword(user.getPassword());
				findUser.get().setConfirmPassword(user.getConfirmPassword());
				return userRepository.saveAndFlush(findUser.get());
			}else {
				return "No record found";
			}
			
		}
		return "No User Found";
	}

}