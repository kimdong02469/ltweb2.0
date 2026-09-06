package kimdong.vn.controllers;

import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kimdong.vn.dao.IUserDao;
import kimdong.vn.dao.impl.UserDaoImpl;
import kimdong.vn.entity.User;
import kimdong.vn.utils.EmailUtils;

@WebServlet(urlPatterns = { "/forgotpassword" })
public class ForgotPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao = new UserDaoImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/forgotpassword.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String email = req.getParameter("email");

		User user = userDao.findByEmail(email);
		if (user != null) {
			// Tạo mã OTP mới gồm 6 chữ số
			String otp = String.format("%06d", new Random().nextInt(999999));
			user.setCode(otp);
			userDao.update(user);

			// Gửi email
			EmailUtils.sendEmail(email, "Khôi phục mật khẩu tài khoản",
					"Mã OTP để đặt lại mật khẩu của bạn là: " + otp);

			// Lưu email vào session để phục vụ bước tiếp theo
			HttpSession session = req.getSession();
			session.setAttribute("emailForgot", email);

			resp.sendRedirect(req.getContextPath() + "/resetpassword");
		} else {
			req.setAttribute("alert", "Email không tồn tại trong hệ thống!");
			req.getRequestDispatcher("/views/forgotpassword.jsp").forward(req, resp);
		}
	}
}