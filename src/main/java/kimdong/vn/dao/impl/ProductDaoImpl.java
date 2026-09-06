package kimdong.vn.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import kimdong.vn.configs.JPAConfig;
import kimdong.vn.dao.IProductDao;
import kimdong.vn.entity.Product;

public class ProductDaoImpl implements IProductDao {
	@Override
	public void insert(Product product) {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			en.getTransaction().begin();
			en.persist(product);
			en.getTransaction().commit();
		} catch (Exception e) {
			en.getTransaction().rollback();
			throw e;
		} finally {
			en.close();
		}
	}

	@Override
	public void update(Product product) {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			en.getTransaction().begin();
			en.merge(product);
			en.getTransaction().commit();
		} catch (Exception e) {
			en.getTransaction().rollback();
			throw e;
		} finally {
			en.close();
		}
	}

	@Override
	public void delete(int id) {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			en.getTransaction().begin();
			Product product = en.find(Product.class, id);
			if (product != null) {
				en.remove(product);
			}
			en.getTransaction().commit();
		} catch (Exception e) {
			en.getTransaction().rollback();
			throw e;
		} finally {
			en.close();
		}
	}

	@Override
	public Product findById(int id) {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			return en.find(Product.class, id);
		} finally {
			en.close();
		}
	}

	@Override
	public List<Product> findAll() {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			TypedQuery<Product> query = en.createNamedQuery("Product.findAll", Product.class);
			return query.getResultList();
		} finally {
			en.close();
		}
	}

	@Override
	public List<Product> findNewest(int limit) {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			TypedQuery<Product> query = en.createQuery("SELECT p FROM Product p ORDER BY p.createdDate DESC",
					Product.class);
			query.setMaxResults(limit);
			return query.getResultList();
		} finally {
			en.close();
		}
	}

	@Override
	public List<Product> findByPagination(int page, int pageSize) {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			TypedQuery<Product> query = en.createQuery("SELECT p FROM Product p ORDER BY p.productId DESC",
					Product.class);
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.getResultList();
		} finally {
			en.close();
		}
	}

	@Override
	public int countAll() {
		EntityManager en = JPAConfig.getEntityManager();
		try {
			Long count = en.createQuery("SELECT COUNT(p) FROM Product p", Long.class).getSingleResult();
			return count.intValue();
		} finally {
			en.close();
		}
	}

}