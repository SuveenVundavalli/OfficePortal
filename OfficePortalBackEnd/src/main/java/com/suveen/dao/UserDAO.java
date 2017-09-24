package com.suveen.dao;

import java.util.List;

import com.suveen.model.User;

public interface UserDAO {
	
	public boolean save(User user);
	public boolean update(User user);
	public boolean delete(int userId);
	public User get(int userId);
	public List<User> list();
	public User validate(String userName, String password);
	public int getMaxId();

}
