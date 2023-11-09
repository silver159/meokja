<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>         <!-- jstl c -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>        <!-- jstl fmt -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>   <!-- jstl fn -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>먹장 - 회원가입</title>

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

<!-- private -->
<script type="text/javascript" src="js/member.js"></script>
<link rel="stylesheet" href="css/member.css"/>
<script type="text/javascript" src="js/loginCheck.js" defer="defer"></script>

</head>
<body>
<!-- header -->
<jsp:include page="common/header.jsp"></jsp:include>

<!-- 메인 시작 -->
<section class="section-padding">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="col-lg-12">
					<h3 class="mb-4 text-center">회원가입</h3>
					<!-- form 시작 -->
					<form class="bg-grey" method="post" action="member" enctype="multipart/form-data" onsubmit="return formCheck()"
						style="padding: 10px 150px;">
						<!-- 프로필 사진 -->
						<div class="col-lg-12">
							<div class="row mb-3">
						    	<div class="col-md-2"></div>
						    		<div class="col-md-8">
						         		<h5 class="mb-3 text-center">프로필 사진 업로드</h5>
						         		<input class="form-control" type="file" accept="image/*" name="fileName" onchange="photoView(event)"/>
						      		</div>
						      	<div class="col-md-2"></div>
						   	</div>
						   	<div class="row mb-3" style="float: none; margin: 5 auto;">
								<div class="col-lg-4" style="float: none; margin: 0 auto;">
									<img id="output" class="img-fluid w-100"  src="upload/memberphoto/default.jpg" /><br/>
								</div>
						   	</div>
						</div>
						<!-- 아이디 -->
						<div class="col-lg-12">
							아이디
							<input id="member_id" class="form-control" type="text" name="member_id" placeholder="아이디" autocomplete="off" onkeyup="idCheck()"/>
                     		<!-- 아이디 오류 문구 출력 -->
							<h5 id="idChkMessage1" style="color: red; font-weight: bold"></h5>
							<h5 id="idChkMessage2" style="color: blue; font-weight: bold"></h5>
						</div>
						<!-- 비밀번호 -->
						<div class="col-lg-12">
							비밀번호
							<input id="pw" class="form-control" type="password" name="pw" placeholder="비밀번호" autocomplete="off" onkeyup="pwdCheck1()"/>
							 <!-- 비밀번호 오류 문구 출력 -->
                  			<h5 id="pwdChkMessage1" style="color: red; font-weight: bold"></h5>
						</div>
						<!-- 비밀번호 확인 -->
						<div class="col-lg-12">
							비밀번호 확인
							<input id="pw2" class="form-control" type="password" name="password2" placeholder="비밀번호 확인" autocomplete="off" onkeyup="pwdCheck2()"/>
							<h5 id="pwdChkMessage2" style="color: red; font-weight: bold"></h5>
						</div>
						<!-- 이름 -->
						<div class="col-lg-12">
							이름
							<input id="name" class="form-control" type="text" name="name" placeholder="이름" autocomplete="off"/>
						</div>
						<!-- 닉넥임 -->
						<div class="col-lg-12">
							닉네임
							<input id="nickname" class="form-control" type="text" name="nickname" placeholder="닉네임" autocomplete="off"/>
						</div>
						<!-- 주민번호 -->
						<div class="col-lg-12">
							주민번호
							<div class="d-flex">
								<input type="text" class="form-control" name="jumin1" maxlength="6"  placeholder="주민번호 앞자리"/>
								<b style="margin: 10px 15px;">-</b>
								<input type="password" class="form-control" name="jumin2" maxlength="7"  placeholder="뒷자리"/>
							</div>
						</div>
						<!-- 이메일 -->
						<div class="col-lg-12">
							이메일
							<div class="d-flex">
								<input type="text" name="email1" class="form-control" autocomplete="off" placeholder="이메일"/>
								<b style="margin: 10px;">@</b>
								<select class="form-control" style="margin: 0 0 14px 0;" onchange="selectEmail()">
				                	<option>gmail.com</option>
				                    <option>naver.com</option>	
				                    <option>daum.net</option>
				                    <option>nate.net</option>
				                    <option selected="selected">직접 입력하기</option>
								</select>
								<input type="text" name="email2" class="form-control"  autocomplete="off" placeholder="직접 입력하기" style="width: 130px; height: 46px; margin-left: 20px;"/>
							</div>
						</div>
						<!-- 전화번호 -->
						<div class="col-lg-12">
							전화번호
							<div class="d-flex">
								<select class="form-control" style="margin-bottom: 15px;">
							    	<option>010</option>
									<option>011</option>
									<option>016</option>
									<option>017</option>
									<option>018</option>
									<option>019</option>
								</select>
								<b style="margin: 10px 15px;">-</b>
								<input id="phone" class="form-control" type="text" placeholder="휴대폰 번호('-'없이 입력하세요)" maxlength="8" autocomplete="off"/>
							</div>
						</div>
						
						<!-- 주소 -->
						<div class="col-lg-12">
							우편번호
							<div class="d-flex justify-content-between">
								<input type="text" class="form-control mr-4" id="postcode" name="postCode" placeholder="우편번호" readonly="readonly">
								<input type="button" class="btn btn-success ml-4" onclick="execDaumPostcode()" value="주소 찾기"><br>
							</div>
							주소
							<input type="text" class="form-control" id="address" name="address" placeholder="주소" readonly="readonly">
							상세주소
							<input type="text" class="form-control" id="detailAddress" name="detailAddress" placeholder="상세주소">
						</div>
						<div class="col-lg-12 text-center p-3">
							<input class="btn btn-primary" type="submit" value="가입완료" style="width: 200px;">
							<!-- hidden -->
							<input type="hidden" id="IDCheckOK"/>
							<input type="hidden" name="jumin">
							<input type="hidden" name="email"/>
							<input type="hidden" name="phone"/>
							<input type="hidden" name="gender"/>
							<input type="hidden" name="age"/>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- 회원 저장 모달 창 -->
<div id="messageModal" class="modal fade" role="dialog" aria-hidden="true">
   <div class="vertical-alignment-helper">
      <div class="modal-dialog">
         <!-- 모달 창의 종류(색상)를 설정한다. -->
         <div id="messageCheck" class="modal-content">
            <!-- 헤더 -->
            <div class="modal-header">
               <h5 class="modal-title" id="staticBackdropLabel">에러메세지</h5>
            </div>
            <!-- 바디 -->
            <div id="messageContent" class="modal-boby">
            </div>
            <div class="modal-footer">
               <button class="btn btn-primary" type="button" data-dismiss="modal">닫기</button>
            </div>
         </div>
      </div>
   </div>
</div>
<!-- footer -->
<jsp:include page="common/footer.jsp"></jsp:include>
   
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</body>
</html>

