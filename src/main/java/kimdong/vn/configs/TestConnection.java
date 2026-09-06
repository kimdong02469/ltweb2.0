package kimdong.vn.configs;

import jakarta.persistence.EntityManager;

public class TestConnection {
	public static void main(String[] args) {
		try {
			EntityManager em = JPAConfig.getEntityManager();
			if (em != null && em.isOpen()) {
				System.out.println(">>> KẾT NỐI DATABASE THÀNH CÔNG! <<<");
				em.close();
			} else {
				System.out.println(">>> Kết nối thất bại! <<<");
			}
		} catch (Exception e) {
			System.out.println(">>> LỖI KẾT NỐI: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
