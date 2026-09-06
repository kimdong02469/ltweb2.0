<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12 col-sm-12">
	<div class="content-form-page">
		<div class="row">
			<div class="col-md-7 col-sm-7">
				<h3>Khôi Phục Mật Khẩu</h3>
				<c:if test="${alert != null}">
					<h3 class="alert alert-danger">${alert}</h3>
				</c:if>
				<form action="${pageContext.request.contextPath}/forgotpassword"
					method="post" class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-lg-4 control-label">Nhập Email của bạn <span
							class="require">*</span></label>
						<div class="col-lg-8">
							<input type="email" class="form-control" name="email" required>
						</div>
					</div>
					<div class="row">
						<div
							class="col-lg-8 col-md-offset-4 padding-left-0 padding-top-20">
							<button type="submit" class="btn btn-primary">Gửi Mã OTP</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>