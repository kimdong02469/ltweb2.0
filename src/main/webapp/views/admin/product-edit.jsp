<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-7">
	<h2>Cập Nhật Sản Phẩm</h2>
	<form action="${pageContext.request.contextPath}/admin/product/edit"
		method="post" enctype="multipart/form-data" class="form-horizontal">
		<!-- Hidden chứa ID để biết đang sửa sản phẩm nào -->
		<input type="hidden" name="productId" value="${product.productId}">

		<div class="form-group">
			<label class="control-label">Tên sản phẩm</label> <input type="text"
				class="form-control" name="productname"
				value="${product.productname}" required>
		</div>
		<div class="form-group">
			<label class="control-label">Giá</label> <input type="number"
				step="0.01" class="form-control" name="price"
				value="${product.price}" required>
		</div>
		<div class="form-group">
			<label class="control-label">Danh mục</label> <select
				class="form-control" name="categoryId" required>
				<c:forEach items="${categories}" var="c">
					<option value="${c.categoryId}"
						${product.category.categoryId == c.categoryId ? 'selected' : ''}>
						${c.categoryname}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label class="control-label">Hình ảnh hiện tại</label><br>
			<c:if test="${product.images != null}">
				<img
					src="${pageContext.request.contextPath}/image?fname=${product.images}"
					width="80" class="mb-2">
				<br>
			</c:if>
			<label class="control-label">Đổi hình ảnh mới (nếu muốn)</label> <input
				type="file" class="form-control" name="images">
		</div>
		<div class="form-group">
			<label class="control-label">Mô tả</label>
			<textarea class="form-control" name="description">${product.description}</textarea>
		</div>
		<button type="submit" class="btn btn-primary"
			style="margin-top: 15px;">Cập Nhật</button>
		<a href="${pageContext.request.contextPath}/admin/products"
			class="btn btn-default" style="margin-top: 15px; margin-left: 10px;">Hủy</a>
	</form>
</div>