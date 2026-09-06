package kimdong.vn.services;

import kimdong.vn.entity.User;

public interface IUserService {
	
	User login(String username, String password);
	
	User FindByUserName(String username);

}
