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

@WebServlet(urlPatterns = { "/resetpassword" })
public class ResetPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao = new UserDaoImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/resetpassword.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("emailForgot");
		String codeInput = req.getParameter("code");
		String newPassword = req.getParameter("newPassword");

		if (email == null) {
			resp.sendRedirect(req.getContextPath() + "/forgotpassword");
			return;
		}

		User user = userDao.findByEmail(email);

		if (user != null && user.getCode() != null && user.getCode().equals(codeInput)) {
			user.setPassword(newPassword); // Nên mã hóa mật khẩu mới nếu dự án có dùng mã hóa
			user.setCode(null); // Xóa mã OTP sau khi đổi xong
			userDao.update(user);

			session.removeAttribute("emailForgot");
			resp.sendRedirect(req.getContextPath() + "/login?alert=reset_success");
		} else {
			req.setAttribute("alert", "Mã OTP không chính xác!");
			req.getRequestDispatcher("/views/resetpassword.jsp").forward(req, resp);
		}
	}
}