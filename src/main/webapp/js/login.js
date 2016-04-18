$(function(){

	$('#switch_qlogin').click(function(){
		$('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_bottom').animate({left:'0px',width:'70px'});
		$('#qlogin').css('display','none');
		$('#web_qr_login').css('display','block');

		});
	$('#switch_login').click(function(){

		$('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_bottom').animate({left:'154px',width:'70px'});

		$('#qlogin').css('display','block');
		$('#web_qr_login').css('display','none');
	});
	if(getParam("a")=='0'){
		$('#switch_login').trigger('click');
	}

});

function logintab(){
	scrollTo(0);
	$('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
	$('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
	$('#switch_bottom').animate({left:'154px',width:'96px'});
	$('#qlogin').css('display','none');
	$('#web_qr_login').css('display','block');
}

function setCookies(data) {
	document.cookie='websocket_data=' + JSON.stringify(data);
}

//根据参数名获得该参数 pname等于想要的参数名 
function getParam(pname) { 
    var params = location.search.substr(1); // 获取参数 平且去掉？ 
    var ArrParam = params.split('&'); 
    if (ArrParam.length == 1) { 
        //只有一个参数的情况 
        return params.split('=')[1]; 
    } 
    else { 
         //多个参数参数的情况 
        for (var i = 0; i < ArrParam.length; i++) { 
            if (ArrParam[i].split('=')[0] == pname) { 
                return ArrParam[i].split('=')[1]; 
            } 
        } 
    } 
}


var reMethod = "GET",
	pwdmin = 6;
var host_domain="127.0.0.1:8080";

$(document).ready(function() {

	$('#submit_login').click(function() {

		if ($('#login_userId').val() == "") {
			$('#login_userId').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			swal({
				type: "warning",
				title: "账号不能为空",
				timer: 2000
			});
			return false;
		}

		if ($('#login_password').val() == "") {
			$('#login_password').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			swal({
				type: "warning",
				title: "密码不能为空",
				timer: 2000
			});
			return false;
		}

		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/login",
			data: 'd={"userId":"' + $("#login_userId").val() + '","password":"'+ $("#login_password").val() + '"}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					swal({
						type: "error",
						title: "账号或密码不正确",
						timer: 2000
					});
				} else {
					$.cookie('websocket_chat_data',JSON.stringify(jsonResult.data));
					location.href="chat.html";
				}
			},
            error:function(XMLHttpRequest, textStatus, errorThrown){
            }
		});

	});

	$('#reg').click(function() {

		if ($('#reg_username').val() == "") {
			$('#reg_username').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b></b></font>");
			return false;
		}

		if ($('#reg_username').val().length < 4 || $('#reg_username').val().length > 16) {

			$('#reg_username').focus();
			$('#userCue').html("<font color='red'><b>×昵称为4-16字符</b></font>");
			return false;

		}

		if ($('#reg_passwd').val().length < pwdmin) {
			$('#reg_passwd').focus();
			$('#userCue').html("<font color='red'><b>×密码不能小于" + pwdmin + "位</b></font>");
			return false;
		}

		if ($('#reg_passwd2').val() != $('#reg_passwd').val()) {
			$('#reg_passwd2').focus();
			$('#userCue').html("<font color='red'><b>×两次密码不一致！</b></font>");
			return false;
		}

		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/register",
			// 暂时只有用户昵称, 用户姓名 = 昵称
			data: 'd={"username":"' + $("#reg_username").val() + '","password":"'+ $("#reg_passwd").val() + '","userNickname":"'+ $("#reg_username").val() + '"}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					swal({
						type: "error",
						title: "注册失败"
					});
				} else {
					swal({
						type: "success",
						title: "注册成功",
						text: "您的账号: " + jsonResult.data.userId
					});
					$('#userCue').html("<font color='green'><b>注册成功, 您的账号为: " + jsonResult.data.userId + "</b></font>");
				}
			},
            error:function(XMLHttpRequest, textStatus, errorThrown){
				swal({
					type: "error",
					title: "注册失败",
					text: "非常抱歉, 服务连接失败"
				});
            }
		});

	});

});