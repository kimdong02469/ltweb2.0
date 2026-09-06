package kimdong.vn.controllers;

import java.io.IOException;
import java.util.List;
import kimdong.vn.dao.IProductDao;
import kimdong.vn.dao.impl.ProductDaoImpl;
import kimdong.vn.entity.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/user/home"})
public class HomeController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IProductDao productDao = new ProductDaoImpl();
		List<Product> newestProducts = productDao.findNewest(10);
		req.setAttribute("newestList", newestProducts);
		req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
	}

}
