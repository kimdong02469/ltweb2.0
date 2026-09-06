package kimdong.vn.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import kimdong.vn.configs.JPAConfig;
import kimdong.vn.dao.ICategoryDao;
import kimdong.vn.entity.Category;

public class CategoryDaoImpl implements ICategoryDao {

	@Override
	public void insert(Category category) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(category);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public void update(Category category) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.merge(category);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public void delete(int cateid) throws Exception {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Category category = enma.find(Category.class, cateid);
			if (category != null) {
				enma.remove(category);
			} else {
				throw new Exception("Không tìm thấy");
			}
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public Category findById(int cateid) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			return enma.find(Category.class, cateid);
		} finally {
			enma.close();
		}
	}

	@Override
	public Category findByCategoryname(String name) throws Exception {
		EntityManager enma = JPAConfig.getEntityManager();
		String jpql = "SELECT c FROM Category c WHERE c.categoryname = :catename";
		try {
			TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
			query.setParameter("catename", name);
			List<Category> list = query.getResultList();
			if (list.isEmpty()) {
				return null;
			}
			return list.get(0);
		} finally {
			enma.close();
		}
	}

	@Override
	public List<Category> findAll() {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			// Sử dụng JPQL trực tiếp thay vì createNamedQuery để tránh lỗi
			String jpql = "SELECT c FROM Category c";
			TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
			return query.getResultList();
		} finally {
			enma.close();
		}
	}

	@Override
	public List<Category> searchByName(String catname) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT c FROM Category c WHERE c.categoryname LIKE :catname";
			TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
			query.setParameter("catename", "%" + catname + "%");
			return query.getResultList();
		} finally {
			enma.close();
		}
	}

	@Override
	public List<Category> findAll(int page, int pagesize) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			// Sử dụng JPQL trực tiếp cho phân trang
			String jpql = "SELECT c FROM Category c";
			TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
			query.setFirstResult(page * pagesize);
			query.setMaxResults(pagesize);
			return query.getResultList();
		} finally {
			enma.close();
		}
	}

	@Override
	public int count() {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT count(c) FROM Category c";
			Query query = enma.createQuery(jpql);
			return ((Long) query.getSingleResult()).intValue();
		} finally {
			enma.close();
		}
	}
}