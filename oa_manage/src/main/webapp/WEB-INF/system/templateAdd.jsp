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
    <script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
</head>
<body class="body">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>用户信息</legend>
</fieldset>
<form class="layui-form  layui-form-pane" action=""  style="margin-right:5%;margin-top: 10px;" id="forms">
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">模块名称</label>
        <div class="layui-input-block">
            <input type="text" name="templateName"  lay-verify="required"  class="layui-input" />
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">模块状态</label>
        <div class="layui-input-block">
             <select name="templateStatus" lay-verify="required" >
		        <option value="">请选择</option>
		        <option value="0" selected="selected">未使用</option>
		        <option value="1">使用中</option>
		      </select>
        </div>
    </div>
     <div  class="layui-form-item" pane>
        <label class="layui-form-label">模块类型</label>
        <div class="layui-input-block">
              <select name="templateType">
		        <option value="">请选择</option>
		        <option value="0" selected="selected">模块一</option>
		        <option value="1">模块二</option>
		        <option value="2">模块三</option>
		      </select>
        </div>
    </div>

</form>
<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
<script type="text/javascript">
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
    	return data;
	}
    
    var formSubmit=function(){
    	return form.verifyField('#forms');
    }
    
</script>
</body>
</html>