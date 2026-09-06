package kimdong.vn.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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

@WebServlet(urlPatterns = { "/profile" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao = new UserDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User account = (User) session.getAttribute("account");
		if (account == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		HttpSession session = req.getSession();
		User account = (User) session.getAttribute("account");
		if (account == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// Lấy thông tin chỉnh sửa từ form
		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");

		// Xử lý upload ảnh mới (nếu có chọn file)
		String uploadPath = Constant.DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		String fileName = account.getImages(); // Giữ lại ảnh cũ nếu không đổi
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

		// Cập nhật lại thông tin đối tượng User hiện tại
		account.setFullname(fullname);
		account.setPhone(phone);
		account.setImages(fileName);

		// Gọi DAO cập nhật vào database
		userDao.update(account);

		// Cập nhật lại session để giao diện hiển thị ngay lập tức thông tin mới
		session.setAttribute("account", account);

		req.setAttribute("alert", "Cập nhật thông tin thành công!");
		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}
}