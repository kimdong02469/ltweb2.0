package kimdong.vn.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kimdong.vn.dao.IUserDao;
import kimdong.vn.dao.impl.UserDaoImpl;
import kimdong.vn.entity.User;

@WebServlet(urlPatterns = { "/verify" })
public class VerifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao = new UserDaoImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("emailRegister");
		String codeInput = req.getParameter("code");

		if (email == null) {
			resp.sendRedirect(req.getContextPath() + "/register");
			return;
		}

		User user = userDao.findByEmail(email);

		if (user != null && user.getCode() != null && user.getCode().equals(codeInput)) {
			user.setIsVerify(1);
			user.setCode(null); // Xóa mã OTP sau khi xác thực thành công
			userDao.update(user);

			session.removeAttribute("emailRegister");
			resp.sendRedirect(req.getContextPath() + "/login?alert=verify_success");
		} else {
			req.setAttribute("alert", "Mã OTP không chính xác!");
			req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
		}
	}
}