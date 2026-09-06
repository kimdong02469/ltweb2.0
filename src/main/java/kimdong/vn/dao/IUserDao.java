package kimdong.vn.dao;

import java.util.List;

import kimdong.vn.entity.User;

public interface IUserDao {
	
	List<User> findAll();
	
	User findById(int id);
	
	void insert(User user);
	
	User findByUserName(String username);
	
	User findByEmail(String email);
	
	void update(User user);

}
