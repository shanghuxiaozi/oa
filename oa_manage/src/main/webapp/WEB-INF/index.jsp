<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
	<meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>义合信达OA系统</title>
    <link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
    <link rel="stylesheet" href="${basePath }/static/css/style.css" />
</head>
<body>
<%@ include file="/static/common/common.jsp" %>
<!-- admin -->
<div class="layui-layout layui-layout-admin"> <!-- 添加skin-1类可手动修改主题为纯白，添加skin-2类可手动修改主题为蓝白 -->
    <!-- header -->
    <div class="layui-header my-header">
        <a href="index.html">
           <img class="my-header-logo" src="${basePath }/static/images/logo.png" alt="logo" />
            
        </a>
<!--         <div class="my-header-btn">
            <button class="layui-btn layui-btn-small btn-nav"><i class="layui-icon">&#xe620;</i>隐藏菜单</button>
        </div> -->
<%--         <ul class="layui-nav" lay-filter="side-left">
            <li class="layui-nav-item"><a href="javascript:;" href-url="${basePath }/static/demo/btn.html"><i class="layui-icon">&#xe621;</i>按钮</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe621;</i>基础</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" href-url="${basePath }/static/demo/btn.html"><i class="layui-icon">&#xe621;</i>按钮</a></dd>
                    <dd><a href="javascript:;" href-url="${basePath }/static/demo/form.html"><i class="layui-icon">&#xe621;</i>表单</a></dd>
                </dl>
            </li>
        </ul> --%>
        <ul class="layui-nav my-header-user-nav" lay-filter="side-right">
            <%-- <li class="layui-nav-item"><a href="javascript:;" class="pay" href-url="${basePath }/baseMessage/list">消息</a></li> --%>
            <li class="layui-nav-item">
                <a class="name" href="javascript:;"><i class="layui-icon">&#xe629;</i>主题</a>
                <dl class="layui-nav-child">
                    <dd data-skin="0"><a href="javascript:;">默认</a></dd>
                    <dd data-skin="1"><a href="javascript:;">粉红</a></dd>
                    <dd data-skin="2"><a href="javascript:;">蓝白</a></dd>
                    <dd data-skin="3"><a href="javascript:;">红</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a class="name" href="javascript:;"><%-- <img src="${basePath }/static/image/code.png" alt="logo"> --%>${sysUser.userName}</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" href-url="${basePath }/sysUserInfo/login"><i class="layui-icon">&#xe621;</i>登录页</a></dd>                    
                    <dd><a href="${basePath }/logout"><i class="layui-icon">&#x1006;</i>退出</a></dd>
                </dl>
            </li>
        </ul>
    </div>
    <!-- side -->
    <div class="layui-side my-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="side">
            	<c:forEach var="datas" items="${list}">
            		<shiro:hasPermission name="${datas.permissionValue}">
						<li class="layui-nav-item"><!-- 去除 layui-nav-itemed 即可关闭展开 -->
		                    <a href="javascript:;"><i class="layui-icon">${datas.iconCode}</i>${datas.name}</a>
		                    <dl class="layui-nav-child">
		                    	<c:forEach var="data" items="${datas.children}">
		                    		<shiro:hasPermission name="${data.permissionValue}">
			                       		<dd><a href="javascript:;" href-url="${data.url}"><i class="layui-icon">${data.iconCode}</i>${data.name}</a></dd>
			                        </shiro:hasPermission>
			                    </c:forEach>
		                    </dl>
		                </li>
	                </shiro:hasPermission>
	            </c:forEach>
            </ul>
        </div>
    </div>
    <!-- body -->
    <div class="layui-body my-body" style="height:100%">
        <div class="layui-tab layui-tab-card my-tab" lay-filter="card" lay-allowClose="true" >
            <ul class="layui-tab-title">
                <li class="layui-this" lay-id="0"><span>欢迎页</span></li>
            </ul>
            <div class="layui-tab-content"  scrolling = 'auto'>
                <div class="layui-tab-item layui-show">
                   <%-- <iframe id="iframe" src="${basePath }/static/demo/welcome.html" frameborder="0"></iframe>  --%>
                   <div id="spaceRow" style="width: 600px;height:40px;"></div> 
                   <div id="orderCount" style="width: 600px;height:400px;"></div> 
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
<script type="text/javascript" src="${basePath }/static/js/index.js"></script>
<script type="text/javascript" src="${basePath }/static/js/config.js"></script>
 <script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script> 
 <script type="text/javascript" src="${basePath }/static/js/jquery.dataTables.min.js"></script> 
 <script type="text/javascript" src="${basePath }/static/js/common/timeUtil.js"></script>
 <script type="text/javascript" src="${basePath }/static/js/echarts.js"></script>
 <script type="text/javascript" src="${basePath }/static/js/common/enchartsDataAdaptor.js"></script>
</body>
</html>