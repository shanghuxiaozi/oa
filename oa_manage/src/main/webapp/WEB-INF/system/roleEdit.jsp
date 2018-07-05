<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>角色修改和查看</title>
    <link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
    <link rel="stylesheet" href="${basePath }/static/css/style.css" />
    <link rel="icon" href="${basePath }/static/image/code.png" />
    <link rel="stylesheet" href="<%=basePath %>/static/ztree/css/metroStyle/metroStyle.css"/>
    <script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
</head>
<body class="body">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>用户信息</legend>
</fieldset>
<form class="layui-form  layui-form-pane" action=""  style="margin-right:5%;margin-top: 10px;" id="forms">
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-inline">
            <input type="hidden" name="id"  id="id" value="${role.id}"/>
            <input type="text" name="name" style="width:300px;" value="${role.name}"  lay-verify="required"   autocomplete="off" class="layui-input" />
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
       		<input type="text" name="orderNum"  value="${role.orderNum}"   lay-verify="number"  style="width:300px;" value="99"  lay-verify="required"   autocomplete="off" class="layui-input" />
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
           <textarea rows="3" cols="30" style="width:300px;" name="description">${role.description} </textarea>
        </div>
    </div> 
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">授权</label>
        <div class="layui-input-block">
           <ul id="menuTree" class="ztree"></ul>
        </div>
    </div>
	<input type="hidden"  id="ar" name="originalId"/>
</form>
<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
<script src="<%=basePath %>/static/ztree/jquery.ztree.all.min.js"></script>

<script type="text/javascript">
	var array=new Array();
	
	if (!Array.indexOf) {
		  Array.prototype.indexOf = function (obj) {
		    for (var i = 0; i < this.length; i++) {
		      if (this[i] == obj) {
		        return i;
		      }
		    }
		    return -1;
		  }
	}
	var form ;
    layui.use(['form', 'layedit', 'laydate'], function(){
        form = layui.form()
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;

        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');

        //自定义验证规则
        form.verify({
            title: function(value){
                if(value.length < 5){
                    return '标题至少得5个字符啊';
                }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,content: function(value){
                layedit.sync(editIndex);
            }
        });



        //监听提交
        form.on('submit(demo1)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            });
            return false;
        });


    });
    var formData=function(){
    	var data = $("#forms").serializeArray();
    	//return data;
    	
    	
   				//获取选择的菜单
	var nodes = ztree.getCheckedNodes(true);
	var arr = new Array();
	var obj = new Object();
	for(var i=0; i<nodes.length; i++) {
		arr.push(nodes[i].id);
	}
	 obj.menuIdList=arr;
	 var len=data.length;
	 data[len]={"name":"menuIdList","value":arr};
   	 var json=JSON.stringify(data); 
   	 var s=1;
   	 for(var i=0;i<data.length;i++){
   		 if(data[i].name=="userName"){
   	    	if(data[i].value==""||data[i].value==null){
   	        	layer.msg("用户名不能为空!");
   	        	s=0;
   	        	break;
   	        }
   	    }
   		if(data[i].name=="password"){
    	    if(data[i].value==""||data[i].value==null){
    	    	data[i].value="123456";
    	    }
    	}
   	 }
   	 if(s==0){
   		 return;
   	 } else{
   		 return data;
   	 }
	}
    
    
    var ztree;	
    $(function(){
    	var setting = {
    			data: {
    				simpleData: {
    					enable: true,
    					idKey: "id",
    					pIdKey: "pid",
    					rootPId: -1
    				},
    				key: {
    					url:"nourl"
    				}
    			},
				check:{
					enable:true,
					chkboxType: { "Y": "ps", "N": "s" }
					//nocheckInherit:true
				}
    	};
    	
		
    	$.ajax({
            type: "POST",
            dataType: "json",
            url: js_path+"/sysRole/menu",
            data: {},
            success: function (obj) {
            	
    			ztree = $.fn.zTree.init($("#menuTree"), setting, obj.data);
    			ztree.expandAll(true);
    			var roleId="${role.id}";
    			$.ajax({
    	            type: "POST",
    	            dataType: "json",
    	            url: js_path+"/sysRole/queryIds",
    	            data: {"roleId":roleId},
    	            success: function (obj) {
    	            	
    	            	var data=obj.data;
    	    			for(var i=0; i<data.length; i++) {
    	    				var node = ztree.getNodeByParam("id", data[i].permissionId);
    	    				ztree.checkNode(node, true, false);
    	    				array.push(data[i].permissionId);
    	    			}
    	    			$("#ar").val(array);
    	            },
    	            error: function(data) {
    	                alert("网络错误");
    	            }
    	       }); 
            },
            error: function(data) {
                alert("网络错误");
            }
       }); 
    })
    
    var formSubmit=function(){
    	return form.verifyField('#forms');
    }
    
	$(function() {
		var flag = "${detail}";
		if (flag == "detail") {
			$('#forms').find('input,textarea,select').attr('disabled', true);
		}
	})
</script>
</body>
</html>