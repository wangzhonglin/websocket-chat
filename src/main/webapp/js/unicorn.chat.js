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
	};

	socket.onclose = function(event) {
	};

	socket.onerror = function(event){
	};

	var loginSessionId;
	var sessionId;
	var userId;
	var userName;
	var userNickname;
	var friendId;
	var method_send = "method_send";
	var method_login="method_login";
	var method_create_session="method_create_session";
	var method_add_friend="method_add_friend";
	var method_delete_friend="method_delete_friend";
	var method_push="method_push";
	var host_domain="127.0.0.1:8080";
	var online_focus_session;

	function handleWebSocketMsg(webSocketMsg){
		var jsonWebSocketMsg=JSON.parse(webSocketMsg);
		var data = jsonWebSocketMsg.data;
		if (jsonWebSocketMsg.success==false){}
		else if(jsonWebSocketMsg.method==method_push) {
			if (data.sessionId == sessionId) {
			} else {
				var session=$('.contact-list1 li[data-session-id$='+data.sessionId+']');
				if(session.length<=0){
					var img=$('.contact-list li[data-friend-id$='+data.friendId+']').find('img').attr('src');
					add_session(data.sessionId,data.friendId,data.friendNickname,0,img,'offline');
					add_recent_session(data.sessionId);
				}
				add_hidden_chat_session_inner(data.sessionId);
			}
			var img=$('.contact-list1 li[data-session-id$='+data.sessionId+']').find('img').attr('src');
			add_message(data.sessionId, data.friendNickname, img, data.content, false);
		}else if(jsonWebSocketMsg.method==method_create_session){
			if(data.active==true){turnToSession(data.sessionId,data.friendId,data.friendNickname,data.avatar);}
		}else if(jsonWebSocketMsg.method==method_add_friend){
			add_friend(data.friendId,data.friendNickname,data.avatar);
		}else if(jsonWebSocketMsg.method==method_delete_friend){
			var friend=$('.contact-list li[data-friend-id$='+data.friendId+']');
			delete_friend(friend,data.friendId);
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

	function checkCookies() {
		var data = $.cookie('websocket_chat_data');
		if(data==null) {
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
			$('#login-user-img').attr('src',jsonData.avatar);
			$('#login-user-nickname').append(userNickname);
			$('#login-user-signature').append('个性签名：'+jsonData.signature);
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
	
	function sendCreateSessionMsg(senderUserId,receiverUserId) {
		var request={
			senderUserId:senderUserId,
			receiverUserId:receiverUserId
		};
		var msg=constructMsg(method_create_session,request);
		socket.send(JSON.stringify(msg));
	}

	function sendDeleteFriendMsg(senderUserId,receiverUserId){
		var request={
			senderUserId:senderUserId,
			receiverUserId:receiverUserId
		};
		var msg=constructMsg(method_delete_friend,request);
		socket.send(JSON.stringify(msg));
	}

	function sendAddFriendMsg(senderUserId,receiverUserId){
		var request={
			senderUserId:senderUserId,
			receiverUserId:receiverUserId
		};
		var msg=constructMsg(method_add_friend,request);
		socket.send(JSON.stringify(msg));
	}

	$('.chat-message button').click(function(){
		var input = $(this).siblings('span').children('input[type=text]');		
		if(input.val() != ''){
			sendNormalMsg(input.val());
			var img=$('#login-user-img').attr('src');
			add_message(sessionId,'我',img,input.val(),true);
		}
	});

	$('.chat-message input').keypress(function(e){
		if(e.which == 13) {	
			if($(this).val() != ''){
				sendNormalMsg($(this).val());
				var img=$('#login-user-img').attr('src');
				add_message(sessionId,'我',img,$(this).val(),true);
			}
		}
	});

	$('.chat-messages').on('click','a',function () {
		var chat_messages_click=$(this);
		var lastMsgId=chat_messages_click.attr('data-last-msg-id');
		var existMsgNum=(lastMsgId>0)?0:chat_messages_click.parent().parent().find('img').length;
		getHistoryMessage(chat_messages_click,sessionId,10,chat_messages_click.attr('data-last-msg-id'),existMsgNum,true);
	});

	function getHistoryMessage(chat_messages_click,sessionId,limit,lastMessageId,existMsgNum,removeFlag){
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/getHistoryMessageList",
			data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+',"sessionId":'+sessionId+',"limit":'+limit+',"lastMessageId":'+lastMessageId+',"existMsgNum":'+existMsgNum+'}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_history_message',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					// init false.
				} else {
					var historyMessageList = jsonResult.data.historyMessageList;
					var currTime=new Date();
					$.each(historyMessageList,function(i,eachHistoryMessage) {
						var img=(eachHistoryMessage.userId==userId)?$('#login-user-img').attr('src'):
							$('.contact-list1 li[data-session-id$='+sessionId+']').find('img').attr('src');
						add_history_message(sessionId, eachHistoryMessage.userNickname, img, eachHistoryMessage.content, eachHistoryMessage.messageTime);
					});
					chat_messages_click.attr('data-last-msg-id',''+jsonResult.data.lastMessageId);
					if(removeFlag==true&&historyMessageList.length<10){
						chat_messages_click.parent().remove();
						return false;
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	}
	
   	var i = 0;
	function add_message(sessionId,name,img,msg,clear) {
		if(sessionId==null||sessionId==''||sessionId==0||friendId==null||friendId==''||friendId==0){return false;}
		i = i + 1;
		var inner;
		if(sessionId==null){inner = $('#chat-messages-inner');}
		else{inner=$('.chat-messages div[data-session-id$='+sessionId+']')}
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

	function add_history_message(sessionId,name,img,msg,longTime) {
		if(sessionId==null||sessionId==''||sessionId==0||friendId==null||friendId==''||friendId==0){return false;}
		i = i + 1;
		var inner;
		if(sessionId==null){inner = $('#chat-messages-inner');}
		else{inner=$('.chat-messages div[data-session-id$='+sessionId+']')}
		var time = new Date(longTime);
		var hours = time.getHours();
		var minutes = time.getMinutes();
		var days=time.getDay();
		var month=time.getMonth();
		var year=time.getFullYear();
		if(hours < 10) hours = '0' + hours;
		if(minutes < 10) minutes = '0' + minutes;
		var id = 'msg-'+i;
		var idname = name.replace(' ','-').toLowerCase();
		$('<p id="'+id+'" class="user-'+idname+'"><img src="'+img+'" alt="" />'
			+'<span class="msg-block"><strong>'+name+'</strong> <span class="time">- '+year+'-'+month+'-'+days+' '+hours+':'+minutes+'</span>'
			+'<span class="msg">'+msg+'</span></span></p>').insertAfter(inner.find('a').parent());
		$('#'+id).hide().fadeIn(800);
	}

	var contact_list=$('.contact-list');
	contact_list.on('click','li',function(){
		var friend=$(this);
		var friendId=friend.attr('data-friend-id');
		var session=$('.contact-list1 li[data-friend-id$='+friendId+']');
		if(session.length<=0){sendCreateSessionMsg(userId,friendId);}
		else{turnToSession(session.attr('data-session-id'),friendId,friend.find('span').text(),friend.find('img').attr('src'));}
	});
	contact_list.on('click','li i',function(e){
		e.stopPropagation();
		var friend=$(this).parent();
		var friendId=friend.attr('data-friend-id');
		swal({
				title: "是否确认要删除该好友?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "是, 删除好友!",
				closeOnConfirm: false,
				cancelButtonText: "取消"
			},
			function(){
				swal({
					title: "删除成功",
					type: "success",
					timer: 1000
				});
				sendDeleteFriendMsg(userId,friendId);
				delete_friend(friend,friendId);
			}
		);
	});

	function turnToSession(sessionId,friendId,friendUserName,avatar) {
		$('#tab1').removeClass('active');
		$('#tab2').addClass('active');
		$('.nav-tabs').find('li').each(function (i) {
			if(i==0){$(this).removeClass('active');}
			if(i==1){$(this).addClass('active');}
		});
		var session=$('.contact-list1 li[data-session-id$='+sessionId+']');
		if(session.length<=0){
			add_session(sessionId,friendId,friendUserName,0,avatar,'offline');
			add_recent_session(sessionId);
			session=$('.contact-list1 li[data-session-id$='+sessionId+']');
		}
		session_click(session);
	}

	var contact_list1=$('.contact-list1');
	contact_list1.on('click','li i',function(e){
		e.stopPropagation();
		delete_session($(this).parent());
	});
	function delete_session(sessionBtn){
		sessionBtn.removeClass().addClass('offline').slideUp(function(){
			if(sessionBtn==online_focus_session){online_focus_session=null;}
			sessionBtn.remove();
			sessionId=sessionBtn.attr('data-session-id');
			var message_inner=$('.chat-messages div[data-session-id$='+sessionId+']');
			if(message_inner.length>0){message_inner.remove();}
			$.ajax({
				url: "http://"+host_domain+"/websocket/chat/api/deleteRecentSession",
				data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+',"sessionId":'+sessionId+'}',
				dataType: "jsonp",
				jsonpCallback: 'success_jsonpCallback_delete_recent_session',
				jsonp: 'cb',
				success: function(result) {
					var jsonResult = eval(result);
					if (jsonResult.success == false) {
						// init false.
					} else {
						//
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
				}
			});
			var session=$('.contact-list1 li:first');
			if(session.length>0){session_click(session);}
			else{sessionId=0;}
		});
	}
	contact_list1.on('click','li',function(){
		var session=$(this);
		var unreadMsgCount=session.find('.msg-count').text();
		session_click(session);
		if(unreadMsgCount>0){
			if(unreadMsgCount>20){unreadMsgCount=20;}
			var sessionId=session.attr('data-session-id');
			updateMsgStatus(sessionId);
			var message_inner=$('.chat-messages div[data-session-id$='+sessionId+']');
			var chat_messages_click=message_inner.find('a');
			var existMsgNum=message_inner.find('img').length;
			if(existMsgNum==0){getHistoryMessage(chat_messages_click,sessionId,unreadMsgCount,0,0,false);}
		}
	});

	function updateMsgStatus(sessionId){
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/updateSessionMessageStatus",
			data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+',"sessionId":'+sessionId+'}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_msg_status',
			jsonp: 'cb',
			success: function(result) {
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	}

	function session_click(session) {
		if(online_focus_session!=null){online_focus_session.removeClass().addClass('online');}
		online_focus_session=session;
		session.removeClass().addClass('online-click');
		var msg_count=session.find('.msg-count');
		msg_count.removeClass('badge-info-msg').addClass('badge-info-msg-hidden');
		msg_count.html(0);
		sessionId=session.attr('data-session-id');
		friendId=session.attr('data-friend-id');
		add_chat_session_inner(sessionId);
	}

	function add_session(sessionId,friendId,userNickname,unreadMsgCount,img,class_name) {
		var newSession;
		var msg_hidden_tail=(unreadMsgCount>0)?'':'-hidden';
		if(unreadMsgCount>99){unreadMsgCount=99;}
		newSession='<li class="'+class_name+'" data-session-id="'+sessionId+'" data-friend-id="'+friendId+'"><a href="javascript:void(0);"><img src="'+img+'" alt="" /> <span>'+userNickname+'</span></a><span class="msg-count badge badge-info-msg'+msg_hidden_tail+'">'+unreadMsgCount+'</span><i class="icon-close"></i></li>';
		if(class_name=='offline'){$(newSession).hide().prependTo($('#session_list')).slideDown(800);}
		else{$(newSession).prependTo($('#session_list'));}
		return $(newSession);
	}

	function add_recent_session(sessionId){
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/addRecentSession",
			data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+',"sessionId":'+sessionId+'}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_add_recent_session',
			jsonp: 'cb',
			success: function(result) {
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	}

	function add_friend(friendId,friendNickname,img) {
		$('<li class="online" data-friend-id="'+friendId+'"><a href="javascript:void(0);"><img src="'+img+'" alt="" /> <span>'+friendNickname+'</span></a><i class="icon-close"></i></li>').hide().appendTo($('.contact-list')).slideDown(800);
	}

	function delete_friend(friend,friendId){
		friend.slideUp(800,function(){
			friend.remove();
			var session=$('.contact-list1 li[data-friend-id$='+friendId+']');
			if(session.length>0){delete_session(session);}
		});
	}

	function add_hidden_chat_session_inner(sessionId){
		var msg_count=$('.contact-list1 li[data-session-id$='+sessionId+']').find('.msg-count');
		var unreadMsgCount=msg_count.text();
		if(unreadMsgCount<=0){
			msg_count.removeClass('badge-info-msg-hidden').addClass('badge-info-msg');
			msg_count.html(1);
		}
		else if(unreadMsgCount<99){
			unreadMsgCount=++unreadMsgCount;
			msg_count.html(unreadMsgCount);
		}
		var chat_session_inner=$('.chat-messages div[data-session-id$='+sessionId+']');
		if(chat_session_inner.length<=0){
			chat_session_inner=' <div id="chat-messages-inner" data-session-id="'+sessionId+'"> <div class="chat-messages-more"> <a class="chat-messages-click" data-last-msg-id="0"><span>查看更多消息</span></a> </div> </div>';
			$(chat_session_inner).hide().appendTo($('.chat-messages'));
		}
	}

	function add_chat_session_inner(sessionId){
		var chat_messages=$('.chat-messages');
		var is_chat_session_inner_already_contained=false;
		chat_messages.find('div').each(function(i){
			if(i%2==0) {
				var data_session_id = $(this).attr('data-session-id');
				if (data_session_id == sessionId) {
					is_chat_session_inner_already_contained = true;
					$(this).show();
				}
				else {
					$(this).hide();
				}
			}
		});
		if(is_chat_session_inner_already_contained==false){
			var chat_session_inner=' <div id="chat-messages-inner" data-session-id="'+sessionId+'"> <div class="chat-messages-more"> <a class="chat-messages-click" data-last-msg-id="0"><span>查看更多消息</span></a> </div> </div>';
			$(chat_session_inner).appendTo(chat_messages);
		}
	}

	$('#nav-logout').on('click',function () {
		$.cookie('websocket_chat_data',null);
	});
	$('#nav-user-info').on('click',function () {
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/getUserInfo",
			data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+'}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_user_info',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					// init false.
				} else {
					var data = jsonResult.data;
					$('#opt-user-nickname').val(data.userNickname);
					if(data.sex==0){
						$('#opt-user-female').parent().addClass('checked');
					}else{
						$('#opt-user-male').parent().addClass('checked');
					}
					$('#opt-user-signature').val(data.signature);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	});

	function initSessionList() {
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/getLatestSessionList",
			data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+'}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_sessions',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					// init false.
				} else {
					var latestSessionList = jsonResult.data.latestSessionList;
					var newSession=null;
					$.each(latestSessionList,function(i,eachSession){
						newSession=add_session(eachSession.sessionId,eachSession.friendId,eachSession.friendNickname,eachSession.unreadMsgCount,eachSession.friendAvatar,'online');
					});
					if(newSession!=null){
						sessionId=newSession.attr('data-session-id');
						friendId=newSession.attr('data-friend-id');
						add_chat_session_inner(sessionId);
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	} initSessionList();
	
	function initFriendList() {
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/getFriendList",
			data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+'}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_friends',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					// init false.
				} else {
					var friendList = jsonResult.data.friendList;
					$.each(friendList,function(i,eachFriend){
						add_friend(eachFriend.userId,eachFriend.userNickname,eachFriend.avatar);
					});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	} initFriendList();

	function search_user(keyword){
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/searchUser",
			data: 'd={"loginSessionId":"'+loginSessionId+'","userId":'+userId+',"keyword":"'+keyword+'"}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_search_user',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					clear_search_response_user();
				} else {
					if(jsonResult.data.userList.length<=0){clear_search_response_user();}
					else{
						add_search_response_user(jsonResult.data);
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	}

	function clear_search_response_user(){
		$('.search-user-list li').remove();
	}

	function add_search_response_user(data){
		clear_search_response_user();
		var userList=data.userList;
		$.each(userList,function(i,eachUser){
			var img=eachUser.avatar;
			var userLi='<li data-user-id="'+eachUser.userId+'"><a href="javascript:void(0);"><img alt="" src="'+img+'" /> <span>'+eachUser.userNickname+'</span></a><i class="icon-plus tip-left" title="加为好友"></i></li>';
			$('.search-user-list').append(userLi);
		});
	}

	$('#search-user-btn').click(function(){
		var input = $(this).siblings('input[type=text]');
		if(input.val() != ''){
			search_user(input.val());
		}
	});

	$('#search_user').keypress(function(e){
		if(e.which == 13) {
			if($(this).val() != ''){
				search_user($(this).val());
			}
		}
	});

	$('.search-user-list').on('click','i',function () {
		var user=$(this).parent();
		$.ajax({
			url: "http://"+host_domain+"/websocket/chat/api/addFriend",
			data: 'd={"loginSessionId":"'+loginSessionId+'","senderUserId":'+userId+',"receiverUserId":"'+user.attr('data-user-id')+'"}',
			dataType: "jsonp",
			jsonpCallback: 'success_jsonpCallback_search_user',
			jsonp: 'cb',
			success: function(result) {
				var jsonResult = eval(result);
				if (jsonResult.success == false) {
					$('.modal-backdrop').click();
					swal({
						title: "添加失败",
						type: "error",
						timer: 1000
					});
				} else {
					$('.modal-backdrop').click();
					swal({
						title: "添加成功",
						type: "success",
						timer: 1000
					});
					add_friend(user.attr('data-user-id'),user.find('span').text(),user.find('img').attr('src'));
					sendAddFriendMsg(userId,user.attr('data-user-id'));
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	});

});