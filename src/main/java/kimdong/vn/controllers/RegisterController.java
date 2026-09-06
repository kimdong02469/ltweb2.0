package kimdong.vn.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kimdong.vn.dao.IUserDao;
import kimdong.vn.dao.impl.UserDaoImpl;
import kimdong.vn.entity.User;
import kimdong.vn.utils.Constant;
import kimdong.vn.utils.EmailUtils;

@WebServlet(urlPatterns = { "/register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao = new UserDaoImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		// Lấy dữ liệu theo thứ tự: fullname, phone, username, password, email
		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		// Xử lý upload file hình ảnh lưu vào thư mục định nghĩa ở Constant.DIR
		String uploadPath = Constant.DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		String fileName = "";
		try {
			Part filePart = req.getPart("images");
			if (filePart != null && filePart.getSize() > 0) {
				fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				String filePath = uploadPath + File.separator + fileName;
				filePart.write(filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Tạo mã OTP ngẫu nhiên gồm 6 chữ số
		String otp = String.format("%06d", new Random().nextInt(999999));

		// Kiểm tra xem email đã tồn tại trong DB chưa để tránh tạo trùng nhiều dòng rác
		User existingUser = userDao.findByEmail(email);

		if (existingUser != null) {
			if (existingUser.getIsVerify() != null && existingUser.getIsVerify() == 1) {
				req.setAttribute("alert", "Email này đã được đăng ký và kích hoạt từ trước!");
				req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
				return;
			} else {
				// Nếu đã tồn tại nhưng chưa verify, cập nhật đè thông tin và mã OTP mới vào
				// đúng dòng đó
				existingUser.setFullname(fullname);
				existingUser.setPhone(phone);
				existingUser.setUsername(username);
				existingUser.setPassword(password);
				if (!fileName.isEmpty()) {
					existingUser.setImages(fileName);
				}
				existingUser.setCode(otp);
				userDao.update(existingUser);
			}
		} else {
			// Tạo mới user nếu chưa tồn tại trong hệ thống
			User user = new User();
			user.setFullname(fullname);
			user.setPhone(phone);
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setImages(fileName);
			user.setRoleid(2);
			user.setIsVerify(0);
			user.setCode(otp);
			user.setCreatedDate(new Date());
			userDao.insert(user);
		}

		// Gửi email chứa mã OTP
		EmailUtils.sendEmail(email, "Xác thực tài khoản đăng ký", "Mã OTP kích hoạt tài khoản của bạn là: " + otp);

		// Lưu email vào session để chuyển sang trang xác thực
		HttpSession session = req.getSession();
		session.setAttribute("emailRegister", email);

		resp.sendRedirect(req.getContextPath() + "/verify");
	}
}