package kimdong.vn.controllers.products;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kimdong.vn.dao.IProductDao;
import kimdong.vn.dao.impl.ProductDaoImpl;
import kimdong.vn.entity.Product;

@WebServlet(urlPatterns = {"/product"})
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IProductDao productDao = new ProductDaoImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int pageSize = 6;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        List<Product> products = productDao.findByPagination(page, pageSize);
        int totalProducts = productDao.countAll();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        req.setAttribute("productlist", products);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/product-list.jsp").forward(req, resp);
    }
}