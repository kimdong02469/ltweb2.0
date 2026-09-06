package kimdong.vn.services.impl;

import kimdong.vn.dao.IUserDao;
import kimdong.vn.dao.impl.UserDaoImpl;
import kimdong.vn.entity.User;
import kimdong.vn.services.IUserService;

public class UserService implements IUserService{
	//lấy toàn bộ hàm trong tầng Dao của user
	IUserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = this.FindByUserName(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public User FindByUserName(String username) {
		return userDao.findByUserName(username);
	}
		

}
