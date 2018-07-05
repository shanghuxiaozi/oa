<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>

<!-- 统一的配置信息 -->
<script type="text/javascript" src="${basePath }/layui-v2.1.4/layui/layui.js"></script>
<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css" />
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
</script>
<c:set scope="page" value="${pageContext.request.contextPath}" var="basePath"></c:set>

