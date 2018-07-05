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
<form class="layui-form  layui-form-pane" action=""  style="margin-right:5%;margin-top: 10px;" id="forms"  target="hidden_frame">
     <input type="hidden" name="id"  value="${iconManage.id}"/>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">图标名称</label>
        <div class="layui-input-block">
            <input type="text" name="name"  lay-verify="required"  class="layui-input" value="${iconManage.name}"/>
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">图标地址</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"  name="url" id="url" value="${iconManage.url}"/>
            <button class="layui-btn layui-btn-warm" id="fileUploads">上传图片</button>
        </div>
    </div>
    <div  class="layui-form-item" pane>
        <label class="layui-form-label">图标地址</label>
        <div class="layui-input-block">
        	<img id="images"  width="50px" src="${iconManage.url}"/>
        </div>
    </div>
</form>
<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
<script type="text/javascript">
	var form,layerAdd;
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
        
        $('#fileUploads').click(function(e){
        	layerAdd=layer.open({
                type: 2,
                skin: 'layui-layer-molv',
                title: '图标上传',
                area: ['500px', '180px'],
                shade:['0.3','#000'],
                maxmin: true,
                content:js_path+'/file/temp',
                btn: ['确认', '取消'],
                yes: function(index,layero){
 
                },
                btn2: function(){
                  layer.closeAll();
                },
                zIndex: layer.zIndex, 
                success: function(layero){
               	 //element.init();
               	 form.render();
               	 layer.setTop(layero);
                }
              });
        });



    });
    var formData=function(){
    	var data = $("#forms").serializeArray();
    	return data;
	}
    
    var formSubmit=function(){
    	return form.verifyField('#forms');
    }
    
    $(function(){
    	var flag= "${detail}";
    	if(flag=="detail"){
    		$('#forms').find('input,textarea,select').attr('disabled',true);
    	}
    })
    function callback(msg)   
    {   
    	$("#url").val("${basePath }/static/upload/"+msg);
    	$("#images").attr("src","${basePath }/static/upload/"+msg);
    	layer.close(layerAdd);
    }
    
</script>
</body>
</html>