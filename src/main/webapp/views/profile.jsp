<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12 col-sm-12">
	<div class="content-form-page">
		<div class="row">
			<div class="col-md-7 col-sm-7">
				<h3>Thông Tin Cá Nhân</h3>
				<c:if test="${alert != null}">
					<div class="alert alert-success">${alert}</div>
				</c:if>
				<form action="${pageContext.request.contextPath}/profile"
					method="post" enctype="multipart/form-data" class="form-horizontal"
					role="form">

					<div class="form-group">
						<label class="col-lg-4 control-label">Username</label>
						<div class="col-lg-8">
							<input type="text" class="form-control"
								value="${sessionScope.account.username}" disabled>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-4 control-label">Email</label>
						<div class="col-lg-8">
							<input type="text" class="form-control"
								value="${sessionScope.account.email}" disabled>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-4 control-label">Full Name</label>
						<div class="col-lg-8">
							<input type="text" class="form-control" name="fullname"
								value="${sessionScope.account.fullname}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-4 control-label">Phone</label>
						<div class="col-lg-8">
							<input type="text" class="form-control" name="phone"
								value="${sessionScope.account.phone}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-4 control-label">Avatar Hiện Tại</label>
						<div class="col-lg-8">
							<c:if
								test="${sessionScope.account.images != null && sessionScope.account.images != ''}">
								<img
									src="${pageContext.request.contextPath}/image?fname=${sessionScope.account.images}"
									width="100" class="img-thumbnail mb-2">
							</c:if>
							<input type="file" class="form-control" name="images">
						</div>
					</div>

					<div class="row">
						<div
							class="col-lg-8 col-md-offset-4 padding-left-0 padding-top-20">
							<button type="submit" class="btn btn-primary">Lưu Thay
								Đổi</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>