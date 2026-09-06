<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-7">
	<h2>Thêm Sản Phẩm Mới</h2>
	<form action="${pageContext.request.contextPath}/admin/product/add"
		method="post" enctype="multipart/form-data" class="form-horizontal">
		<div class="form-group">
			<label class="control-label">Tên sản phẩm</label> <input type="text"
				class="form-control" name="productname" required>
		</div>
		<div class="form-group">
			<label class="control-label">Giá</label> <input type="number"
				step="0.01" class="form-control" name="price" required>
		</div>
		<div class="form-group">
			<label class="control-label">Danh mục</label> <select
				class="form-control" name="categoryId" required>
				<option value="">-- Chọn danh mục --</option>
				<c:forEach items="${categories}" var="c">
					<option value="${c.categoryId}">${c.categoryname}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label class="control-label">Hình ảnh</label> <input type="file"
				class="form-control" name="images">
		</div>
		<div class="form-group">
			<label class="control-label">Mô tả</label>
			<textarea class="form-control" name="description"></textarea>
		</div>
		<button type="submit" class="btn btn-success"
			style="margin-top: 15px;">Lưu Sản Phẩm</button>
	</form>
</div>