<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>自动预订订单</title>
    <link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
    <link rel="stylesheet" href="${basePath }/static/css/style.css" />
    <link rel="icon" href="${basePath }/static/image/code.png" />
    <link rel="stylesheet" href="<%=basePath %>/static/ztree/css/metroStyle/metroStyle.css" />
    <script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
</head>
<body class="body">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>用户信息</legend>
</fieldset>
<!-- <blockquote class="layui-elem-quote">
    温馨提示：若想添加多个菜单或者按钮，名称、菜单地址、授权标识用';’隔开,且要一一对应！
</blockquote> -->
<form class="layui-form  layui-form-pane" action=""  style="margin-right:5%;margin-top: 10px;" id="formperm">
    <div  class="layui-form-item" pane   id="type" style="display: none;">
        <label class="layui-form-label">类型</label>
        <div class="layui-input-block" id="auth">
            <input type="hidden" name="id"  id="id" value="${perm.id}" />
            <input type="text" name="type"  id="type" value="1" />
        </div>
    </div>
    
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="${perm.name}" lay-verify="required"   autocomplete="off" class="layui-input"  onchange="valueChange('name',this)"  />
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">上级菜单</label>
        <div class="layui-input-block">
        	<input type="hidden" name="pid"  id="pid" value="${perm.pid}"  />
           <input type="text" name="pname" readonly="readonly"  id="pname" value="${perm.pname}"  onclick="menuTrees()"  autocomplete="off" class="layui-input"  />
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label" style="width:130px;">是否有子菜单</label>
        <div class="layui-input-block" onclick="radioType(this)" >
           <input type="radio" name="isParent" title="有"  value="true" <c:if test="${perm.isParent==true||perm==null}">checked</c:if>  id="isParent1"/> 
           <input type="radio" name="isParent" title="无"  value="false" <c:if test="${perm.isParent==false}">checked</c:if>  id="isParent0" />
        </div>
    </div>
    
    <div class="layui-form-item url" <c:if test="${perm.isParent==true||perm==null}">style="display: none;"</c:if>>
        <label class="layui-form-label">菜单地址</label>
        <div class="layui-input-block">
           <input type="text" name="url" value="${perm.url}"  id="url"   autocomplete="off" class="layui-input"    onchange="valueChange('url',this)"  />
        </div>
    </div>
    	<div class="layui-form-item permissionName" <c:if test="${perm.isParent==true||perm==null}">style="display: none;"</c:if>>
			<label class="layui-form-label">授权名称</label>
			<div class="layui-input-block">
				<input type="text" name="permissionName" id="permissionName"  value="${perm.permissionName}" 
					autocomplete="off" class="layui-input"  <c:if test="${perm.isParent==false}" >lay-verify="required"</c:if>
					onchange="valueChange('permission_name',this)" />
			</div>
		</div>
		<div class="layui-form-item " pane>
			<label class="layui-form-label">授权标识</label>
			<div class="layui-input-block">
				<input type="text" name="permissionValue" <c:if test="${perm.isParent==false}" >lay-verify="required"</c:if>  value="${perm.permissionValue}" 
					autocomplete="off" class="layui-input"
					onchange="valueChange('permission_value',this)" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">排序</label>
			<div class="layui-input-block">
				<input type="text" name="orderNum" id="orderNum" lay-verify="number"  value="${perm.orderNum}" 
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">图标</label>
			<div class="layui-input-block">
				<input type="text" name="iconCode" autocomplete="off"  value="${perm.iconCode}" 
					class="layui-input" /> <a
					href="http://www.layui.com/doc/element/icon.html" target="_blank"
					style="color: red;">获取图标，请点击这里！</a>
			</div>
		</div>
</form>
<!-- 选择菜单 -->
<div id="menuLayer" style="padding:10px;display: none;">
	<ul id="menuTree" class="ztree"></ul>
</div>
<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
<script src="<%=basePath %>/static/ztree/jquery.ztree.all.min.js"></script>

<script type="text/javascript">
	var form ;
    layui.use(['form', 'layedit', 'laydate'], function(){
        form = layui.form()
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;
	        //监听提交
	        form.on('submit(demo1)', function(data){
	            layer.alert(JSON.stringify(data.field), {
	                title: '最终的提交信息'
	            });
	            return false;
	        });
    });
    var formData=function(){
    	var data = $("#formperm").serializeArray();
   	    return data;
    }
    
    
    var ztree;	
    $(function(){
		var setting = {
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pid",
						rootPId : -1
					},
					key : {
						url : "nourl"
					}
				}
		};
    	var list=${sysPermissionlist};
		ztree = $.fn.zTree.init($("#menuTree"), setting, list);
		ztree.expandAll(true);
    })
    
    function menuTrees(){
    	var isParents="${isParents}";
    	if(isParents!="false"){
	    	layer.open({
	    		type: 1,
	    		skin: 'layui-layer-molv',
	    		title: "选择菜单",
	    		area: ['300px', '280px'],
	    		shade: 0,
	    		offset:"t",
	    		shadeClose: false,
	    		content: jQuery("#menuLayer"),
	    		btn: ['确定', '取消'],
	    		btn1: function (index) {
	    			var node = ztree.getSelectedNodes();
	    			//选择上级菜单
	    			$("#pid").val(node[0].id);
	    			$("#pname").val(node[0].name);
	    			layer.close(index);
	            }
	    	});
    	}
    }
    
    function valueChange(field,va){
    	var value=$(va).val();
    	value=value.replace(/\s/g, "");
    	if(""!=value){
	    	$.ajax({
		   	         type: "POST",
		   	         dataType: "json",
		   	         url: js_path+"/sysPermission/validateMenu",
		   	         data: {"value":value,"field":field},
		   	         success: function (obj) {
		   	            if(obj.code==300){
	  	   	          		layer.msg("“"+obj.data+"”已经存在!");
	  	   	          	    $(va).val("");
	  	   	          		$(va).attr("class","layui-input layui-form-danger").focus();
	  	   	          		
		   	            }
		   	         },
		   	         error: function(data) {
		   	              alert("网络错误");
		   	         }
	      	});
    	}
    	
    }
    var formSubmit=function(){
    	return form.verifyField('#formperm');
    }
</script>
<script type="text/javascript">
		 $(function(){
			var nodeId="${nodeId}";
			if(nodeId!=""){
				$("#pid").val(nodeId);
				$("#pname").val("${nodeValue}");
			}
	    })
	    function radioType(val){
			 var val=$('input:radio[name="isParent"]:checked').val();
			 if(val=="true"){
				 $("#permissionName").removeAttr("lay-verify");
				 $("#url").removeAttr("lay-verify");
				 $("#url").val("");
				 $("#permissionName").val("");
				 $(".url").hide();
				 $(".permissionName").hide();
			 }else{
				 $("#url").attr("lay-verify","required");
				 $("#permissionName").attr("lay-verify","required");
				 $(".url").show();
				 $(".permissionName").show();
			 }
			 form.render();
		}
	</script>
</body>
</html>