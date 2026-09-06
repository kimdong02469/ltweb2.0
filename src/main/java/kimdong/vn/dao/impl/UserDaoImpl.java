package kimdong.vn.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import kimdong.vn.configs.JPAConfig;
import kimdong.vn.dao.IUserDao;
import kimdong.vn.entity.User;

public class UserDaoImpl implements IUserDao {

	@Override
	public List<User> findAll() {
		EntityManager enm = JPAConfig.getEntityManager();
		try {
			TypedQuery<User> query = enm.createQuery("SELECT u FROM User u", User.class);
			return query.getResultList();
		} finally {
			enm.close();
		}
	}

	@Override
	public User findById(int id) {
		EntityManager enm = JPAConfig.getEntityManager();
		try {
			return enm.find(User.class, id);
		} finally {
			enm.close();
		}
	}

	@Override
	public void insert(User user) {
		EntityManager enm = JPAConfig.getEntityManager();
		EntityTransaction trans = enm.getTransaction();
		try {
			trans.begin();
			enm.persist(user); // Thêm mới entity vào database
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
		} finally {
			enm.close();
		}
	}

	@Override
	public User findByUserName(String username) {
	    EntityManager enm = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT u FROM User u WHERE u.username = :username";
	        TypedQuery<User> query = enm.createQuery(jpql, User.class);
	        query.setParameter("username", username);
	        List<User> list = query.getResultList();
	        return list.isEmpty() ? null : list.get(0);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        enm.close();
	    }
	}

	public static void main(String[] args) {
		IUserDao userdao = new UserDaoImpl();

		// Test thêm mới user
		User newUser = new User(2, "donk", "123", "donk@gmail.com", "dong kim thach", "k", "123456", 2, null);
		userdao.insert(newUser);

		// Test tìm kiếm tất cả
		List<User> list = userdao.findAll();
		for (User user : list) {
			System.out.println(user);
		}
	}
}