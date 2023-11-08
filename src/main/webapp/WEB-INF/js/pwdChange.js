function passwordChange() {
	let password1 = $('#password').val();
	let password2 = $('#password2').val();
	
	if(password1.length == 0 || password1 == '') {
		alert('비밀번호를 입력하세요');
		return false;
	}
	
	if(password2.length == 0 || password2 == '') {
		alert('비밀번호를 입력하세요');
		return false;
	}
		
	if (password1.trim() != password2.trim()) {
	   alert('비밀번호가 일치하지 않습니다.');
	   return false;
	}
	
}

//비밀번호 유호성 검사
function pwdCheck1() {
	let password = $('#pw').val().trim();
	let pattern1 = new RegExp('(?=.*[0-9]+)(?=.*[a-zA-Z]+)(?=.*[!@#$%^&]+)');
	let pattern2 = new RegExp('[^a-zA-Z0-9!@#$%^&]+');
	
	if(password.length == 0) {
		$('#pwdChkMessage1').html('');
		return false;
	}
	$('#pwdChkMessage1').html('');
	
	if (pattern2.test(password)) {
		$('#pwdChkMessage1').html('특수문자는 ! @ # $ % ^ & 만 입력가능합니다.');
		return false;
	}
	$('#pwdChkMessage1').html('');

	if (!pattern1.test(password)) {
		$('#pwdChkMessage1').html('영문자, 숫자, 특수문자가 각각 1개 이상 포함되어야 합니다.');
		return false;
	}
	$('#pwdChkMessage1').html('');
	
	if(password.length < 8 || password.length > 16) {
		$('#pwdChkMessage1').html('8자이상 16자이하로 입력하세요');
		return false;
	}
	$('#pwdChkMessage1').html('');
	
    return true;
}

//	비밀번호와 비밀번호 확인 일치 검사
function pwdCheck2() {
	let password1 = $('#pw').val();
	let password2 = $('#pw2').val();

	if(password2.length == 0) {
		$('#pwdChkMessage2').html('');
		return false;
	}	
	if (password1.trim() != password2.trim()) {
	   $('#pwdChkMessage2').html('비밀번호가 일치하지 않습니다.');
	   return false;
	}
	$('#pwdChkMessage2').html('');
	
	return true;
}

function passwordCheck() {
	let password1 = $('input[name=pw]').val();
	let password2 = $('input[name=pw2]').val();
	let originPassword = $('input[name=originPw]').val();
	
	if(password1.length == 0 || password1 == '' || 
		password2.length == 0 || password2 == '' ) {

		alert('비밀번호를 입력하세요');
		return false;
	}
	
	if (password1.trim() != password2.trim()) {
	   alert('변경할 비밀번호 서로 일치하지 않습니다.');
	   return false;
	}
	
	if (password1.trim() == originPassword) {
	   alert('현재 비밀번호와 같습니다.');
	   return false;
	}
	
	if(!pwdCheck1()){
		return false;
	}
	if(!pwdCheck2()){
		return false;
	}
}