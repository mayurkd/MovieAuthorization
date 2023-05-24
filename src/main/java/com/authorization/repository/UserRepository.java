package com.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.authorization.model.User;

import jakarta.transaction.Transactional;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value="select u from User u where u.loginId= :loginid and u.password= :password")
	public User validateUser(String loginid, String password);

}
