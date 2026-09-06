package kimdong.vn.services.impl;

import java.util.List;

import kimdong.vn.dao.ICategoryDao;
import kimdong.vn.dao.impl.CategoryDaoImpl;
import kimdong.vn.entity.Category;
import kimdong.vn.services.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {
	public ICategoryDao cateDao = new CategoryDaoImpl();

	@Override
	public List<Category> findAll() {
		return cateDao.findAll();
	}

	@Override
	public Category findById(int id) {
		return cateDao.findById(id);
	}

	@Override
	public List<Category> searchByName(String keyword) {
		return cateDao.searchByName(keyword);
	}

	@Override
	public void insert(Category category) {
		try {
			Category cate = this.findByCategoryname(category.getCategoryname());
			if (cate == null) {
				cateDao.insert(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Category category) {
		Category cate = this.findById(category.getCategoryId());
		if (cate != null) {
			cateDao.update(category);
		}
	}

	@Override
	public void delete(int id) {
		try {
			cateDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int count() {
		return cateDao.count();
	}

	@Override
	public List<Category> findAll(int page, int pagesize) {
		return cateDao.findAll(page, pagesize);
	}

	@Override
	public Category findByCategoryname(String name) {
		try {
			return cateDao.findByCategoryname(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}