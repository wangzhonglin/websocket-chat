$(document).ready(function(){
	var socket;
	if (!window.WebSocket) {
		//noinspection JSUnresolvedVariable
		window.WebSocket = window.MozWebSocket;
	}
	if (window.WebSocket) {
		socket = new WebSocket("ws://localhost:8888");
	} else {
		alert("Your browser does not support Web Socket.");
	}
	socket.onmessage = function(event) {
		handleWebSocketMsg(event.data);
	};

	socket.onopen = function(event) {
		sendConnectMsg();
		//alert("onopen.");
	};

	socket.onclose = function(event) {
		//alert("onclose.");
	};

	socket.onerror = function(event){
		//alert("onerror.");
	};

	function handleWebSocketMsg(webSocketMsg){
		var jsonWebSocketMsg=JSON.parse(webSocketMsg);
		if (jsonWebSocketMsg.success==false){}
		else if(jsonWebSocketMsg.method=='method_push'){
			var data=jsonWebSocketMsg.data;
			if(data.sessionId==sessionId){
				add_message(data.friendNickname,'images/demo/av1.jpg',data.content,false);
			}
		}
	}

	function getCookies() {
		if (document.cookie.length > 0) {
			var data_start_index = document.cookie.indexOf("data=");
			if (data_start_index != -1) {
				data_start_index = data_start_index + "data".length + 1;
				var data_end_index = document.cookie.indexOf(";", data_start_index);
				if (data_end_index == -1) data_end_index = document.cookie.length;
				var data = document.cookie.substring(data_start_index, data_end_index);
				return document.cookie.substring(data_start_index, data_end_index);
			}
		}
		return "";
	}

	var loginSessionId;
	var sessionId = 1;
	var userId;
	var userName;
	var userNickname;
	var friendId = 15;
	var method_send = "method_send";
	var method_login="method_login";

	function checkCookies() {
		var data = getCookies();
		if(data==null||data=='') {
			location.href = "index.html";
		}
		var jsonData = JSON.parse(data);
		if (jsonData==null||jsonData=='') {
			location.href = "index.html";
		} else {
			loginSessionId = jsonData.sessionId;
			userId = jsonData.userId;
			userName = jsonData.name;
			userNickname = jsonData.nickname;
		}
	} checkCookies();

	function constructMsg(method,request) {
		return {
			method:method,
			loginSessionId:loginSessionId,
			request:request
		}
	}

	function sendConnectMsg(){
		var request={
			userId:userId,
			status:0
		};
		var msg = constructMsg(method_login,request);
		socket.send(JSON.stringify(msg));
	}

	function sendNormalMsg(content) {
		var request = {
			sessionId:sessionId,
			senderUserId:userId,
			receiverUserId:friendId,
			messageType:0,
			content:content
		};
		var msg = constructMsg(method_send,request);
		socket.send(JSON.stringify(msg));
	}

	$('.chat-message button').click(function(){
		var input = $(this).siblings('span').children('input[type=text]');		
		if(input.val() != ''){
			sendNormalMsg(input.val());
			add_message('You','images/demo/av1.jpg',input.val(),true);
		}
	});

	$('.chat-message input').keypress(function(e){
		if(e.which == 13) {	
			if($(this).val() != ''){
				sendNormalMsg($(this).val());
				add_message('You','images/demo/av1.jpg',$(this).val(),true);
			}
		}
	});
	
	setTimeout(function(){
			add_message('Neytiri','images/demo/av1.jpg','I have a problem. My computer not work!')
		},'6000');
	setTimeout(function(){
			add_message('Cartoon Man','images/demo/av1.jpg','Turn off and turn on your computer then see result.')
		},'11000');
	setTimeout(function(){
            remove_user('neytiri','Neytiri')
        },'13500');
   	var i = 0;
	function add_message(name,img,msg,clear) {
		i = i + 1;
		var  inner = $('#chat-messages-inner');
		var time = new Date();
		var hours = time.getHours();
		var minutes = time.getMinutes();
		if(hours < 10) hours = '0' + hours;
		if(minutes < 10) minutes = '0' + minutes;
		var id = 'msg-'+i;
        var idname = name.replace(' ','-').toLowerCase();
		inner.append('<p id="'+id+'" class="user-'+idname+'"><img src="'+img+'" alt="" />'
										+'<span class="msg-block"><strong>'+name+'</strong> <span class="time">- '+hours+':'+minutes+'</span>'
										+'<span class="msg">'+msg+'</span></span></p>');
		$('#'+id).hide().fadeIn(800);
		if(clear) {
			$('.chat-message input').val('').focus();
		}
		$('#chat-messages').animate({ scrollTop: inner.height() },1000);
	}

    function remove_user(userId,name) {
        i = i + 1;
        $('.contact-list li#user-'+userId).addClass('offline').delay(1000).slideUp(800,function(){
            $(this).remove();
        });
        var  inner = $('#chat-messages-inner');
        var id = 'msg-'+i;
        inner.append('<p class="offline" id="'+id+'"><span>User '+name+' left the chat</span></p>');
        $('#'+id).hide().fadeIn(800);
    }

	var contact_list1=$('.contact-list1');
	contact_list1.on('click','li i',function(){
		$(this).parent().slideUp(function(){
			$(this).remove();
			sessionId=$(this).attr('data-session-id');
			$.ajax({
				url: "http://127.0.0.1:8080/websocket/chat/api/deleteSession",
				data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+',"sessionId":'+sessionId+'}',
				dataType: "jsonp",
				jsonpCallback: 'success_jsonpCallback',
				jsonp: 'cb',
				success: function(result) {
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
				}
			});
		});
	});
	contact_list1.on('click','li',function(){
		$(this).removeClass().addClass('online');
		$(this).find('.msg-count').remove();
		sessionId=$(this).attr('data-session-id');
		friendId=$(this).attr('data-friend-id');
	});

	function add_session(sessionId,friendId,userNickname,unreadMsgCount,img,class_name) {
		var newSession;
		var msg_hidden_tail=(unreadMsgCount>0)?'':'-hidden';
		if(unreadMsgCount>99){unreadMsgCount=99;}
		newSession='<li class="'+class_name+'" data-session-id="'+sessionId+'" data-friend-id="'+friendId+'"><a><img src="'+img+'" alt="" /> <span>'+userNickname+'</span></a><span class="msg-count badge badge-info-msg'+msg_hidden_tail+'">'+unreadMsgCount+'</span><i class="icon-close"></i></li>';
		if(class_name=='offline'){$(newSession).hide().prependTo($('#session_list')).slideDown(800);}
		else{$(newSession).prependTo($('#session_list'));}
	}

	function initSessionList() {
		$.ajax({
			url: "http://127.0.0.1:8080/websocket/chat/api/getLatestSessionList",
			data: 'd={"loginSessionId":"' + loginSessionId + '","userId":'+ userId + '}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					// init false.
				} else {
					var latestSessionList = jsonResult.data.latestSessionList;
					$.each(latestSessionList,function(i,eachSession){
						add_session(eachSession.sessionId,eachSession.friendId,eachSession.friendNickname,0,'images/demo/av1.jpg','online');
					});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	} initSessionList();
});