<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12">
	<h2>Quản Lý Sản Phẩm</h2>
	<a href="${pageContext.request.contextPath}/admin/product/add"
		class="btn btn-primary" style="margin-bottom: 15px;">Thêm Sản Phẩm
		Mới</a>
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th>ID</th>
				<th>Hình Ảnh</th>
				<th>Tên Sản Phẩm</th>
				<th>Giá</th>
				<th>Danh Mục</th>
				<th>Hành Động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="p">
				<tr>
					<td>${p.productId}</td>
					<td><c:if test="${p.images != null}">
							<img
								src="${pageContext.request.contextPath}/image?fname=${p.images}"
								width="60">
						</c:if></td>
					<td>${p.productname}</td>
					<td>${p.price}</td>
					<td>${p.category.categoryname}</td>
					<td><a
						href="${pageContext.request.contextPath}/admin/product/edit?id=${p.productId}"
						class="btn btn-warning btn-sm">Sửa</a> <a
						href="${pageContext.request.contextPath}/admin/product/delete?id=${p.productId}"
						class="btn btn-danger btn-sm"
						onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?')">Xóa</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>