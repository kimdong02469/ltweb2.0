package kimdong.vn.controllers.products;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import kimdong.vn.dao.ICategoryDao;
import kimdong.vn.dao.IProductDao;
import kimdong.vn.dao.impl.CategoryDaoImpl;
import kimdong.vn.dao.impl.ProductDaoImpl;
import kimdong.vn.entity.Category;
import kimdong.vn.entity.Product;
import kimdong.vn.utils.Constant;

@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/edit", "/admin/product/delete" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ProductAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IProductDao productDao = new ProductDaoImpl();
	private ICategoryDao categoryDao = new CategoryDaoImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if (url.contains("delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			productDao.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		} else if (url.contains("add")) {
			List<Category> categories = categoryDao.findAll();
			req.setAttribute("categories", categories);
			req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
		} else if (url.contains("edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Product product = productDao.findById(id);
			List<Category> categories = categoryDao.findAll();
			req.setAttribute("product", product);
			req.setAttribute("categories", categories);
			req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
		} else {
			List<Product> list = productDao.findAll();
			req.setAttribute("products", list);
			req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String productIdStr = req.getParameter("productId");
		String productname = req.getParameter("productname");
		double price = Double.parseDouble(req.getParameter("price"));
		String description = req.getParameter("description");
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));

		String uploadPath = Constant.DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		String fileName = "";
		try {
			Part filePart = req.getPart("images");
			if (filePart != null && filePart.getSize() > 0) {
				fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				filePart.write(uploadPath + File.separator + fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Product product = new Product();
		product.setProductname(productname);
		product.setPrice(price);
		product.setDescription(description);
		Category category = categoryDao.findById(categoryId);
		product.setCategory(category);

		if (productIdStr != null && !productIdStr.isEmpty()) {
			// Cập nhật sản phẩm cũ
			int productId = Integer.parseInt(productIdStr);
			product.setProductId(productId);

			Product oldProduct = productDao.findById(productId);
			if (fileName.isEmpty()) {
				product.setImages(oldProduct.getImages()); // Giữ ảnh cũ nếu không chọn ảnh mới
			} else {
				product.setImages(fileName);
			}
			product.setCreatedDate(oldProduct.getCreatedDate());

			productDao.update(product);
		} else {
			// Thêm mới sản phẩm
			if (!fileName.isEmpty()) {
				product.setImages(fileName);
			}
			product.setCreatedDate(new Date());
			productDao.insert(product);
		}

		resp.sendRedirect(req.getContextPath() + "/admin/products");
	}
}