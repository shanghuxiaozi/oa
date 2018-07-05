<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>用户添加</title>
    <link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
    <link rel="stylesheet" href="${basePath }/static/css/style.css" />
    <link rel="icon" href="${basePath }/static/image/code.png" />
    <link rel="stylesheet" href="<%=basePath %>/static/ztree/css/metroStyle/metroStyle.css"/>
    <script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript">
    </script>
</head>

<body class="body">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>用户信息</legend>
</fieldset>
<form class="layui-form  layui-form-pane" action=""  style="margin-right:5%;margin-top: 10px;" id="formuser">
    
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block">
            <input type="text" name="userName" lay-verify="required" onchange="valueChange(this)" id="username" placeholder="请输入用户名"  autocomplete="off" class="layui-input" />
        </div>
    </div>
    <div  class="layui-form-item" pane>
   		
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input type="text" name="password"   placeholder="8-20个字符，必须同时包含三项：大写字母、小写字母、数字" id="password" autocomplete="off" class="layui-input" />
        </div>
    </div>
    
    
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" placeholder="非必填"  autocomplete="off" class="layui-input"/>
        </div>
    </div>
     <div  class="layui-form-item" pane>
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-inline">
            <input type="text" name="email" placeholder="非必填"   autocomplete="off" class="layui-input"/>
        </div>
    </div>
    
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">授权工会</label>
        <div class="layui-input-block">
           <ul id="menuTree" class="ztree" ></ul>
        </div>
    </div>
    <!-- 选中的工会ID值放置处 -->
    <input type="hidden" name="labourUnionId"  lay-verify="required" readonly="readonly" id="labourUnionId" />
    
    
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">角色</label>
        <div class="layui-input-block">
        	${rolestr}
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="radio" name="locked" title="启用" value="1" checked="checked"/>
            <input type="radio" name="locked" title="禁用"  value="0"/>
        </div>
    </div>
    <button type="reset" style="display: none;" class="layui-btn layui-btn-primary" id="reset">重置</button>
    <input type="hidden" id="mark" value="0"/>
</form>
<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
<script src="${basePath }/static/js/jquery.md5.js"></script>
<script src="${basePath }/static/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
    var form ,layer;
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
    	var data = $("#formuser").serializeArray();
    	for(var i=0;i<data.length;i++){
       		if(data[i].name=="password"){
       		    //如果密码不符合规则
                var regs = /^(?!(?:[0-9a-z]+|[0-9A-Z]+|[a-zA-Z]+)$)[a-zA-Z0-9]{8,20}/;
                if(data[i].value.match(regs)){
//                  data[i].value=$.md5(data[i].value+"+yan");
                    var psword = $.md5(data[i].value);
                    data[i].value = $.md5(psword+"_Random_Key");
                }else{
                    return null;
                }

        	  /*  if(data[i].value==""||data[i].value==null){
        	    	data[i].value=$.md5("123456");
        	    	break;
        	    }else{
        	    	data[i].value=$.md5(data[i].value);
        	    }*/
        	}
       	 }
    	return data;
	}
    
    var formSubmit=function(){
    	return form.verifyField('#formuser');
    }
    
    // 初始化加载  工会树
	$(document).ready(function(){  
		$.ajax({
	         type:"POST",
	         dataType: 'json',
	         url:js_path+'/workUnits/getAll',
	 	     data:{},
	 	     success: function(data) {
	 	    	 if(data.code == 200){
	 	    		 var nodes = data.data;
	 	    		 var nodeData = [];
	 	    		 for(var i=0;i<nodes.length;i++){
	 	    			 var obj = new Object();
	 	    			 obj.id = nodes[i].id;
	 	    			 obj.pId = nodes[i].pId;
	 	    			 obj.type = nodes[i].type;
	 	    			 obj.name = nodes[i].workUnits;
	 	    			 obj.open = true;
	 	    			 nodeData.push(obj);
	 	    		 }
	 	    		 setting = {
	 	    			 check: {
	 	    				 enable: true,
	 	    				 chkStyle: "radio",  //单选框
	 	    		         radioType: "all"    //对所有节点设置单选
	 	    			 },
	 	    			 data: {
	 	    				 simpleData: {
	 	    					enable: true
	 	    				 }
	 	    			 },
	 	    			 callback: {
	 	    				onClick: null,
	 	    				onCheck: zTreeOnCheck
	 	    			 }
	 	    		 };
	 	    		 // 被勾选事件
	 	    		 function zTreeOnCheck(event, treeId, treeNode) {
	 	    		 	$('#labourUnionId').val(treeNode.id);  
	 	    		 };
	 	    		 $.fn.zTree.init($("#menuTree"), setting, nodeData);  
	 	    	 }
	 	     },
	 	}); 
	});


    function valueChange(va){    	
		var value = $(va).val();
		$.ajax({
			type : "POST",
			dataType : "json",
			url : js_path + "/sysUser/validateUserName",
			data : {
				"userName" : value
			},
			success : function(obj) {
				if (obj.code == 300) {
					layer.alert("用户名:“" + value + "”已经存在!");
					$(va).val("");
					$(va).attr("class", "layui-input layui-form-danger").focus();

				}
			},
			error : function(data) {
				alert("网络错误");
			}
		});
	}
    
</script>
</body>
</html>