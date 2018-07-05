<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="UTF-8" />
	<title>工会交友</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/static/css/login.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}/static/css/verify.css">
	<script src="${basePath }/static/js/jquery.md5.js"></script>
</head>
<body>
<font color="red" id="msg" style="display: none;"></font>

<form class="layui-form" action=""  method="post" id="formId">
	<div class="main">
		<div class="box">
			<div class="logo">
				<img src="${basePath}/static/image/logo_04.png"/>
			</div>
			<div class="login-box">
				<div class="top">
					<span id="helder">
						用户登录
					</span>
				</div>
				<div class="lg-main">
					<p class="p1">账号</p>
					<p><input type="text" name="username" required style="padding-left: 6px;" placeholder="用户名" autocomplete="off" id="userName" /></p>
					<p class="p1">密码</p>
					<p>
					<input type="password" id="password" required style="padding-left: 6px;" placeholder="密码" autocomplete="off" class="layui-input" />
						<input type="hidden" name="password" id="psw" />
					</p>
					<p class="p1">验证码</p>

					<div id="mpanel3" class="clearfix">
					</div>

				</div>
				<div class="foot" id="foot1">
					<button type="button" id="check-btn2" class="verify-btn">确定</button>
				</div>

				<div class="foot" id="foot2" style="display: none">
					<input type="submit" id="Btn" value="登录" />
				</div>
				<div class="bot clearfix">
					<p class="bot-p1"><a href="${basePath }/">进入后台</a></p>
					<p class="bot-p2"><a href="${basePath }/logout">注销</a></p>
				</div>
				<div class="bot" style="margin-top: 8px;"><font color="red" size="2">${msg}</font></div>
			</div>

			<div class="footer">
				<div class="con_left">深圳义和信达科技有限公司技术支持 粤ICP备 11016239号-7<p><span>
			<img src="${basePath }/static/image/images/gonghui20160706_84.jpg" /></span><span><img src="${basePath }/static/image/images/gonghui20160706_86.jpg" /></span></p></div></div>

			</div>

		</div>
	</form>

<script type="text/javascript" src="${basePath }/static/js/verify.js" ></script>
<script type="text/javascript">

		$("#Btn").click(function(){
			var password = $.md5($('#password').val());
			$('#psw').val($.md5(password+"_Random_Key"));
//			$('#psw').val($.md5($('#password').val()));
		});

		$('#mpanel3').codeVerify({
			type : 2,
			figure : 100,		//位数，仅在type=2时生效
			arith : 0,			//算法，支持加减乘，不填为随机，仅在type=2时生效
			width : '100px',
			height : '30px',
			fontSize : '16px',
			btnId : 'check-btn2',
			ready : function() {
			},
			success : function() {
				$("#foot1").hide();
                $("#foot2").show();
			},
			error : function() {

			}
		});

</script>
</body>
</html>