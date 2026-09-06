<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12 col-sm-12">
	<div class="content-form-page">
		<div class="row">
			<div class="col-md-7 col-sm-7">
				<h3>Đặt Lại Mật Khẩu Mới</h3>
				<c:if test="${alert != null}">
					<h3 class="alert alert-danger">${alert}</h3>
				</c:if>
				<form action="${pageContext.request.contextPath}/resetpassword"
					method="post" class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-lg-4 control-label">Mã OTP <span
							class="require">*</span></label>
						<div class="col-lg-8">
							<input type="text" class="form-control" name="code" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label">Mật khẩu mới <span
							class="require">*</span></label>
						<div class="col-lg-8">
							<input type="password" class="form-control" name="newPassword"
								required>
						</div>
					</div>
					<div class="row">
						<div
							class="col-lg-8 col-md-offset-4 padding-left-0 padding-top-20">
							<button type="submit" class="btn btn-primary">Xác Nhận</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>