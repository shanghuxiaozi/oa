<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">

    
   


	<head>
		<title>客服热线</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type='text/javascript' src='${basePath }/static/js/webim.config.js'></script>
		<script type='text/javascript' src='${basePath }/static/js/strophe-1.2.8.min.js'></script>
		<script type='text/javascript' src='${basePath }/static/js/websdk-1.4.12.min.js'></script>
		
		<meta charset="UTF-8" />
	    <meta name="renderer" content="webkit" />
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	    <link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
	    <link rel="stylesheet" href="${basePath }/static/css/style.css" />
	    <link rel="icon" href="${basePath }/static/image/code.png" /> 
		<style type="text/css">
			html,
			body {
				width: 100%;
				height: 100%;
			}
			
			html,
			body,
			p {
				margin: 0;
				padding: 0;
				overflow: hidden;
			}
			
			.main_online {
				min-height: 100%;
				height: 100%;
				position: relative;
			}
			
			.on_middel {
				width: 800px;
				height: 500px;
				border: 1px solid #ccc;
				box-sizing: border-box;
				position: absolute;
				top: 50%;
				left: 50%;
				transform: translate(-50%, -50%);
				border-radius: 6px;
			}
			
			.on_top {
				width: 100%;
				height: 44px;
				box-sizing: border-box;
				background-color: #8bca1b;
			}
			
			.on_content {
				width: 800px;
				height: 456px;
				box-sizing: border-box;
			}
			
			.on_left,
			.on_right {
				float: left;
				height: 100%;
			}
			
			.on_left {
				width: 600px;
				border-right: 1px solid #e0e0e0;
				box-sizing: border-box;
			}
			
			.on_right {
				width: 200px;
			}
			
			.receive {
				padding: 0 15px;
				display: flex;
				margin-bottom: 12px;
			}
			
			.receive .on_logo {
				width: 55px;
				height: 55px;
				border-radius: 100%;
			}
			
			.receive .content {
				position: relative;
				flex: 1;
				padding-left: 10px;
				padding-top: 5px;
				margin-left: 10px;
			}
			
			.stra_l {
				position: absolute;
				left: 0px;
				top: 21px;
				width: 0;
				height: 0;
				border-top: 8px solid transparent;
				border-right: 10px solid #ededed;
				border-bottom: 8px solid transparent;
			}
			
			.receive .content p {
				display: inline-block;
				background-color: #ededed;
				border-radius: 4px;
				font-size: 15px;
				color: #333333;
				padding: 14px;
			}
			
			.send_r .content {
				padding-right: 10px;
				margin-right: 10px;
			}
			
			.send_r .content p {
				float: right;
				border-radius: 4px;
			}
			
			.stra_r {
				position: absolute;
				right: 0;
				top: 21px;
				width: 0;
				height: 0;
				border-top: 8px solid transparent;
				border-left: 10px solid #ededed;
				border-bottom: 8px solid transparent;
			}
			
			.on_txts {
				width: 100%;
				height: 300px;
				padding: 10px 0;
				box-sizing: border-box;
				overflow-y: auto;
				overflow-x: hidden;
			}
			
			.on_face {
				width: 100%;
				height: 30px;
				background-color: #ededed;
				position: relative;
			}
			
			.on_face img {
				width: 22px;
				margin: 4px 0 0 15px;
				cursor: pointer;
			}
			
			.on_input .txt_area {
				height: 80px;
				width: 100%;
				box-sizing: border-box;
				border: none;
				outline: none;
				font-size: 15px;
				color: #333333;
				padding-left: 5px;
			}
			
			.on_send {
				font-size: 15px;
				color: #333333;
				padding: 4px 13px;
				border: 1px solid #e0e0e0;
				text-decoration: none;
				float: right;
				border-radius: 4px;
				margin-right: 10px;
			}
			
			.on_right {
				box-sizing: border-box;
			}
			
			.on_name {
				font-size: 14px;
				color: #808080;
				padding: 10px 0 0 10px;
			}
			
			.his_list {
				padding-left: 0px;
				height: 400px;
				box-sizing: border-box;
				overflow-y: auto;
				overflow-x: hidden;
			}
			
			.his_list li {
				list-style-type: none;
				cursor: pointer;
				padding: 8px 10px;
			}
			
			.his_list li.active {
				background-color: #f3f7f7;
			}
			
			.his_list li img {
				width: 24px;
				height: 24px;
				border-radius: 100%;
				vertical-align: middle;
			}
			
			.his_list li .use_name {
				font-size: 12px;
				color: #808080;
				display: inline-block;
				max-width: 120px;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
				vertical-align: middle;
			}
			
			.face_lists {
				position: absolute;
				width: 260px;
				height: 120px;
				top: -120px;
				box-sizing: border-box;
				overflow-y: auto;
				overflow-x: hidden;
				background-color: #fff;
				z-index: 9;
				display: none;
			}
			
			.new_info {
				display: inline-block;
				width: 15px;
				height: 15px;
				border-radius: 100%;
				background-color: #f43f3f;
				color: #fff;
				font-size: 12px;
				text-align: center;
				line-height: 15px;
				font-style: normal;
				vertical-align: text-bottom;
				margin-left: 5px;
			}
		</style>
	</head>

	<body>
	
		<div class='layui-form-item'>
			<label class='layui-form-label'>车辆配置</label>
			<div class='layui-input-inline' style='width: 600px;'>
				<textarea name="carConfig" class='layui-textarea' 
					id='messageValueAdd' style='display: none;'></textarea>
			</div>
		</div>
		

		<main class="main_online">
			<div class="on_middel">
				<div class="on_top"></div>
				<div class="on_content">
					<div class="on_left">
						<div class="on_txts">
							<!-- 左边信息 -->
							<!--
							<div class="receive">
								<img src="img/logo (2).png" class="on_logo">
								<div class="content">
									<i class="stra_l"></i>
									<p>您好，有什么可以帮你</p>
								</div>
							</div>
							-->
							<!-- 右边信息 -->
							<!--
							<div class="receive send_r">
								<div class="content">
									<i class="stra_r"></i>
									<p>您好，有什么可以帮你</p>
								</div>
								<img src="img/logo (2).png" class="on_logo">
							</div>
							-->
						</div>

						<div class="on_face">
							<div class="face_lists">

							</div>
							<img src="${basePath }/static/image/xiao_icon.png" onclick="show_face()" />
						</div>
						<!-- 输入内容 -->
						<div class="on_input">
							<textarea type="text" class="txt_area" placeholder="说点什么吧......" id="msgs"></textarea>
							<a href="javascript:void(0);" class="on_send" onclick="sendTexts()">发送</a>
						</div>
					</div>
					<div class="on_right">
						<p class="on_name">最近联系人</p>
						<ul class="his_list">
							<!--
							<li>
								<img src="img/logo (2).png">
								<b class="use_name">俄方博客</b>
							</li>
							-->
						</ul>
					</div>

					<div style="clear: both;"></div>
				</div>
			</div>
		</main>
		

	<!-- 	<script type="text/javascript" src="js/jquery-1.6.2.js"></script> -->
		<script type="text/javascript" src="${basePath }/static/js/jquery.cookie.js"></script> 
		<script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {

				var num = 35;
				var html = "";
				for(var i = 1; i <= num; i++) {
					html += "<img onclick='get_my(this)' facet='ee_" + i + "' src='${basePath }/static/image/faces/ee_" + i + ".png'>";
				}
				$(".face_lists").html(html);

			})

			function show_face() {
				$(".face_lists").toggle();
			}

			/*选择表情*/
			function get_my(e) {
				//alert($(e).attr('facet') + '.png');
				$("#msgs").val($("#msgs").val() + "/" + $(e).attr('facet') + '.png/');
			}

			/*创建连接*/
			var conn = new WebIM.connection({
				isMultiLoginSessions: WebIM.config.isMultiLoginSessions,
				https: typeof WebIM.config.https === 'boolean' ? WebIM.config.https : location.protocol === 'https:',
				url: WebIM.config.xmppURL,
				heartBeatWait: WebIM.config.heartBeatWait,
				autoReconnectNumMax: WebIM.config.autoReconnectNumMax,
				autoReconnectInterval: WebIM.config.autoReconnectInterval,
				apiUrl: WebIM.config.apiURL,
				isAutoLogin: true
			});

			/*添加回调函数*/
			conn.listen({
				onOpened: function(message) { //连接成功回调
					// 如果isAutoLogin设置为false，那么必须手动设置上线，否则无法收消息
					// 手动上线指的是调用conn.setPresence(); 如果conn初始化时已将isAutoLogin设置为true
					// 则无需调用conn.setPresence();
					//获取好友列表
					handleOpen(conn);
				},
				onClosed: function(message) {}, //连接关闭回调
				onTextMessage: function(message) {
					//alert(JSON.stringify(message));
					getMsg(message);
				}, //收到文本消息
				onEmojiMessage: function(message) {}, //收到表情消息
				onPictureMessage: function(message) {}, //收到图片消息
				onCmdMessage: function(message) {}, //收到命令消息
				onAudioMessage: function(message) {}, //收到音频消息
				onLocationMessage: function(message) {}, //收到位置消息
				onFileMessage: function(message) {}, //收到文件消息
				onVideoMessage: function(message) {
					var node = document.getElementById('privateVideo');
					var option = {
						url: message.url,
						headers: {
							'Accept': 'audio/mp4'
						},
						onFileDownloadComplete: function(response) {
							var objectURL = WebIM.utils.parseDownloadResponse.call(conn, response);
							node.src = objectURL;
						},
						onFileDownloadError: function() {
							console.log('File down load error.')
						}
					};
					WebIM.utils.download.call(conn, option);
				}, //收到视频消息
				onPresence: function(message) {
					handlePresence(message);
				}, //处理“广播”或“发布-订阅”消息，如联系人订阅请求、处理群组、聊天室被踢解散等消息
				onRoster: function(message) {}, //处理好友申请
				onInviteMessage: function(message) {}, //处理群组邀请
				onOnline: function() {}, //本机网络连接成功
				onOffline: function() {}, //本机网络掉线
				onError: function(message) {}, //失败回调
				onBlacklistUpdate: function(list) { //黑名单变动
					// 查询黑名单，将好友拉黑，将好友从黑名单移除都会回调这个函数，list则是黑名单现有的所有好友信息
					console.log(list);
				},
				onReceivedMessage: function(message) {}, //收到消息送达客户端回执
				onDeliveredMessage: function(message) {}, //收到消息送达服务器回执
				onReadMessage: function(message) {}, //收到消息已读回执
				onCreateGroup: function(message) {}, //创建群组成功回执（需调用createGroupNew）
				onMutedMessage: function(message) {} //如果用户在A群组被禁言，在A群发消息会走这个回调并且消息不会传递给群其它成员
			});

			//注册
			/*var options = {
				username: 'username',
				password: 'password',
				nickname: 'nickname',
				appKey: WebIM.config.appkey,
				success: function() {},
				error: function() {},
				apiUrl: WebIM.config.apiURL
			};
			conn.registerUser(options);*/

			//登录
			var options = {
				apiUrl: WebIM.config.apiURL,
				user: 'xianda',
				pwd: '123456',
				appKey: WebIM.config.appkey
			};
			conn.open(options);

			//添加对方为好友
			//收到联系人订阅请求的处理方法，具体的type值所对应的值请参考xmpp协议规范
			var handlePresence = function(e) {

				//对方收到请求加为好友
				if(e.type === 'subscribe') {
					/*同意添加好友操作的实现方法*/
					conn.subscribed({
						to: e.from,
						message: '[resp:true]'
					});
					conn.subscribe({ //需要反向添加对方好友
						to: e.from,
						message: '[resp:true]'
					});
				}
			}

			var windon = 0;

			/*获取好友列表*/
			var handleOpen = function(conn) {

				//从连接中获取到当前的登录人注册帐号名

				conn.getRoster({

					success: function(roster) {
						//获取好友列表，并进行好友列表渲染，roster格式为：
						/** [
						      {
						        jid:'asemoemo#chatdemoui_test1@easemob.com',
						        name:'test1',
						        subscription: 'both'
						      }
						    ]
						*/
						for(var i = 0, l = roster.length; i < l; i++) {
							var ros = roster[i];
							//ros.subscription值为both/to为要显示的联系人，此处与APP需保持一致，才能保证两个客户端登录后的好友列表一致
							if(ros.subscription === 'both' || ros.subscription === 'to') {
								//ajax请求，获取用户信息
								jQuery.ajax({
									type: 'POST',
									url: 'http://192.168.1.126:8084/HFive/get_userId_msg',
									data: {
										"userId": ros.name
									},
									dataType: 'json',
									success: function(data) {
										var data_html = "";

										data_html += " <li user='" + data.id + "'>";
										data_html += " <img src=" + data.Img + ">";
										data_html += " <b class='use_name'>" + data.username + "</b>";
										data_html += "<i class='new_info' id = " + data.id + " style='display: none;'></i>";
										data_html += " </li>";
										jQuery(".his_list").append(data_html);

										/*选中状态切换*/
										$(".his_list li").click(function() {
											$(".his_list li").removeClass("active");
											$(this).addClass("active");

											windon = $(this).attr("user");
											cutWindow();
										})
									}
								});
							}
						}
					},

				});

			};

			//接收消息
			function getMsg(message) {
				/*
				var data_html = "";

				data_html += " <div class='receive'>";
				data_html += " <img src='img/logo (2).png' class='on_logo'>";
				data_html += " <div class='content'>";
				data_html += " <p>" + message.data + "</p>";
				data_html += " </div>";
				data_html += " </div>";
				jQuery(".on_main").append(data_html);
				*/

				if(message.from == windon) {
					var msgsStr = message.data;

					var strs = new Array(); //定义一数组 
					strs = msgsStr.split("/"); //字符分割 
					for(i = 0; i < strs.length; i++) {
						//判断这个字符串是否包含.png
						if(strs[i].indexOf(".png") > 0) {
							var strOld = "/" + strs[i] + "/";
							var strNew = "<img src='img/faces/" + strs[i] + "'>";
							msgsStr = msgsStr.replace(strOld, strNew);
						}
					}

					//表示当前窗口,叠加内容
					var data_html = "";

					data_html += " <div class='receive'>";
					data_html += " <img src='img/logo (2).png' class='on_logo'>";
					data_html += " <div class='content'>";
					data_html += " <i class='stra_l'></i>";
					data_html += " <p>" + msgsStr + "</p>";
					data_html += " </div>";
					data_html += " </div>";
					jQuery(".on_txts").append(data_html);

					var h = $(".on_txts")[0].scrollHeight - $(".on_txts").height();
					$(".on_txts").scrollTop(h);

					//将数据存入cookie中
					var num = jQuery.cookie('msgNum' + message.from);
					if(num == null) {
						num = 1;
					}

					//将聊天记录存入cookie
					jQuery.cookie('msg' + num + message.from, "get," + msgsStr, {
						expires: 7
					});
					num++;
					jQuery.cookie('msgNum' + message.from, num, {
						expires: 7
					});
				} else {
					var msgsStr = message.data;

					var strs = new Array(); //定义一数组 
					strs = msgsStr.split("/"); //字符分割 
					for(i = 0; i < strs.length; i++) {
						//判断这个字符串是否包含.png
						if(strs[i].indexOf(".png") > 0) {
							var strOld = "/" + strs[i] + "/";
							var strNew = "<img src='img/faces/" + strs[i] + "'>";
							msgsStr = msgsStr.replace(strOld, strNew);
						}
					}
					
					
					//不是当前窗口将数据存入cookie中
					var num = jQuery.cookie('msgNum' + message.from);
					if(num == null) {
						num = 1;
					}

					//将聊天记录存入cookie
					jQuery.cookie('msg' + num + message.from, "get," + msgsStr, {
						expires: 7
					});
					num++;
					jQuery.cookie('msgNum' + message.from, num, {
						expires: 7
					});

					$("#" + message.from).show();
					//标注有几条未读消息
					var msg_num = $("#" + message.from).html();
					if(msg_num == "") {
						msg_num = 0
					}
					//将消息增加1
					$("#" + message.from).html(parseInt(msg_num) + 1);
				}

			}

			//发送消息
			function sendTexts() {
				var msgs = $("#msgs").val();
				if(msgs != "" && typeof(msgs) != "undefined") {
					sendPrivateText(msgs);

					//将发送的消息显示出来
					sendMsg(msgs);

					$("#msgs").val("");
				}
			}

			//窗口切换，清空上一个窗口的数据
			function cutWindow() {
				//清空数据
				$(".on_txts").html("");

				//从cookie中拿出数据
				/*将聊天记录从cookie中拿出来*/
				//windon表示当前窗口是哪个用户
				var num = jQuery.cookie('msgNum' + windon) - 1;
				if(num != null) {
					var iNum = 0;
					if(num >= 100) {
						iNum = num - 100;
					} else {
						iNum = 1;
					}
					for(var i = iNum; i <= num; i++) {
						var msgOld = jQuery.cookie('msg' + i + windon);
						//拆分字符串，查看是接收的还是发送的
						var arr = new Array();
						arr = msgOld.split(','); //注split可以用字符或字符串分割  

						if(arr[0] == "send") {
							//发送的消息
							var msgsStr = arr[1];

							var data_html = "";

							data_html += " <div class='receive send_r'>";
							data_html += " <div class='content'>";
							data_html += " <i class='stra_r'></i>";
							data_html += " <p>" + msgsStr + "</p>";
							data_html += " </div>";
							data_html += " <img src='img/logo (2).png' class='on_logo'>";
							data_html += " </div>";
							jQuery(".on_txts").append(data_html);
						} else {
							//就是接收的消息
							var msgsStr = arr[1];

							var data_html = "";

							data_html += " <div class='receive'>";
							data_html += " <img src='img/logo (2).png' class='on_logo'>";
							data_html += " <div class='content'>";
							data_html += " <i class='stra_l'></i>";
							data_html += " <p>" + msgsStr + "</p>";
							data_html += " </div>";
							data_html += " </div>";
							jQuery(".on_txts").append(data_html);
						}
					}

					//清空当前未读信息的数量
					$("#" + windon).hide();
					//标注有几条未读消息
					$("#" + windon).html("");

					//滚动到底部
					var h = $(".on_txts")[0].scrollHeight - $(".on_txts").height();
					$(".on_txts").scrollTop(h);
				}

			}

			// 单聊发送文本消息
			var sendPrivateText = function(msgs) {
				var id = conn.getUniqueId(); // 生成本地消息id
				var msg = new WebIM.message('txt', id); // 创建文本消息
				msg.set({
					msg: msgs, // 消息内容
					to: '70', // 接收消息对象（用户id）
					roomType: false,
					success: function(id, serverMsgId) {
						console.log('send private text Success');
					},
					fail: function(e) {
						console.log("Send private text error");
					}
				});
				msg.body.chatType = 'singleChat';
				conn.send(msg.body);
			};

			//发送消息
			function sendMsg(msgs) {
				var msgsStr = msgs;

				var strs = new Array(); //定义一数组 
				strs = msgs.split("/"); //字符分割 
				for(i = 0; i < strs.length; i++) {
					//判断这个字符串是否包含.png
					if(strs[i].indexOf(".png") > 0) {
						var strOld = "/" + strs[i] + "/";
						var strNew = "<img src='img/faces/" + strs[i] + "'>";
						msgsStr = msgsStr.replace(strOld, strNew);
					}
				}

				var data_html = "";

				data_html += " <div class='receive send_r'>";
				data_html += " <div class='content'>";
				data_html += " <i class='stra_r'></i>";
				data_html += " <p>" + msgsStr + "</p>";
				data_html += " </div>";
				data_html += " <img src='img/logo (2).png' class='on_logo'>";
				data_html += " </div>";
				jQuery(".on_txts").append(data_html);

				//发送消息后将内容固定到最下面
				var h = $(".on_txts")[0].scrollHeight - $(".on_txts").height();
				$(".on_txts").scrollTop(h);

				//缩小表情窗口
				$(".face_lists").hide();
				
				//将数据存到cookie中
				var num = jQuery.cookie('msgNum' + windon);
				if(num == null) {
					num = 1;
				}

				//将聊天记录存入cookie
				jQuery.cookie('msg' + num + windon, "send," + msgsStr, {
					expires: 7
				});
				num++;
				jQuery.cookie('msgNum' + windon, num, {
					expires: 7
				});

			}
		</script>

	</body>

</html>


</body>
</html>