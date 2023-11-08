<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>먹장 - 비밀번호 찾기</title>

<!-- common -->
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>			<!-- device-width -->
<link rel="icon" href="images/favicon.png">										<!--  favicon -->
<script type="text/javascript" src="common/jquery-3.7.0.js"></script>			<!-- jQury -->
<link rel="stylesheet" href="common/bootstrap/bootstrap.min.css">				<!-- bootstrap -->
<script src="common/bootstrap/bootstrap.min.js"></script>						<!-- bootstrap -->
<script src="common/bootstrap/popper.min.js"></script>							<!-- bootstrap -->
<script type="text/javascript" src="common/custom.js" defer="defer"></script>	<!-- template -->
<link rel="stylesheet" href="common/style.css"/>								<!-- template -->

<link rel="stylesheet" href="common/slick-carousel/slick-theme.css">			<!-- slick -->
<link rel="stylesheet" href="common/slick-carousel/slick.css">					<!-- slick -->
<script src="common/slick-carousel/slick.min.js"></script>						<!-- slick -->
<script type="text/javascript" src="js/loginCheck.js" defer="defer"></script>

<!-- private -->
<script type="text/javascript" src="js/pwdChange.js"></script>

</head>
<body>
<section class="section-padding">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-6 col-md-8 col-sm-12 col-xs-12">
				<form id="myProfile" class="comment-form mb-3 gray-bg p-5" action="pwdChange" method="post" onsubmit="return passwordCheck()">
					<h1 class="mb-4" style="vertical-align: middle; text-align: center; font-size: 25px;">내 정보 확인</h1>
					<div class="row">
						<div class="col-lg-12">
							<input id="pw" type="password" placeholder="변경할 비밀번호" name="pw" class="form-control mb-3" onkeyup="pwdCheck1()"/>
							<h5 id="pwdChkMessage1" style="color: red; font-weight: bold"></h5>
							<input id="pw2" type="password" placeholder="비밀번호 확인" name="pw2" class="form-control mb-3" onkeyup="pwdCheck2()"/>
							<h5 id="pwdChkMessage2" style="color: red; font-weight: bold"></h5>
							<input type="hidden" name="originPw" value="${user.pw}"><br/>
							<input type="hidden" name="member_id" value="${user.member_id}">
						</div>
					</div>
					<div class="d-grid">
						<input class="btn btn-primary btn-block mb-3" type="submit" value="비밀번호 변경"/>
						<input class="btn btn-primary btn-block mb-3" type="button" value="돌아가기" onclick="history.back()"/>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
</body>
</html>