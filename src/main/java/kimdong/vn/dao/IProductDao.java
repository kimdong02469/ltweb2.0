package kimdong.vn.dao;

import java.util.List;
import kimdong.vn.entity.Product;

public interface IProductDao {
	void insert(Product product);

	void update(Product product);

	void delete(int id);

	Product findById(int id);

	List<Product> findAll();

	List<Product> findNewest(int limit);

	List<Product> findByPagination(int page, int pageSize);

	int countAll();
}