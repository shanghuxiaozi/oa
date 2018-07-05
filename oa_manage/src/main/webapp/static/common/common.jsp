<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
<link rel="stylesheet" href="${basePath }/static/css/style.css" />
<link rel="icon" href="${basePath }/static/image/code.png" />
<script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
<!-- 统一的配置信息 -->
<script type="text/javascript" src="${basePath }/static/js/config.js"></script>
<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
<script type="text/javascript" src="${basePath }/static/js/jquery.dataTables.min.js"></script>
<%
	String path = request.getContextPath();
	int port = request.getServerPort();
	String basePath = null;
	if (port == 80) {
		basePath = request.getScheme() + "://" + request.getServerName() + path;
	} else {
		basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	}
	pageContext.getSession().setAttribute("path", basePath);
%>
<script type="text/javascript">
	var js_path = "${path}";
	$(function(){ 
		$("body").append("<iframe id='id_iframe' name='nm_iframe' style='display:none;' ></iframe>"); 
	}); 
</script>
<c:set scope="page" value="${pageContext.request.contextPath}" var="basePath"></c:set>

