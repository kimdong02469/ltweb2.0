package kimdong.vn.controllers.products;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kimdong.vn.dao.IProductDao;
import kimdong.vn.dao.impl.ProductDaoImpl;
import kimdong.vn.entity.Product;

@WebServlet(urlPatterns = {"/product-detail"})
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IProductDao productDao = new ProductDaoImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productDao.findById(id);

        req.setAttribute("product", product);
        req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
    }
}