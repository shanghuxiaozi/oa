<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>交友图片</title>
    <link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css"  media="all" />
    <link rel="icon" href="${basePath }/static/image/code.png" />
    <script type="text/javascript" src="${basePath }/layui-v2.1.4/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
</head>
<style>
				  
</style>
<body class="body">
       <div style="display: inline-block;width: 50%;float: left;height: 410px;max-width:480px;margin-right: 10px;">
      <div class="layui-form-item" pane>
			<div class="layui-upload" >
				<button type="button" class="layui-btn" id="test10">交友轮播图片一</button>	
				<span style="color: red;">(上传图片请按宽高：750*320的比例)</span>
				<div class="layui-upload-list"  style="width: 100%;height: 302px;">
					    <img class="layui-upload-img" id="demo1" style="width: 100%;height: 100%;"/>
					    <p id="demoText" ></p>

			    </div>

				<div class="layui-form-item" pane>
					<label class="layui-form-label">图片1地址:</label>
					<div class="layui-input-block">
						<input type="text" id="photo1" class="layui-input" />
					</div>
				</div>

	        </div>
	   </div>
	   </div>	  
	
	   <div style="display: inline-block;width: 50%;float: left;height: 410px;max-width:480px;margin-right: 10px;">
	   <div class="layui-form-item" pane style="display:inline">
			<div class="layui-upload" >
				<button type="button" class="layui-btn" id="test11">交友轮播图片二</button>	
				<div class="layui-upload-list"  style="width: 100%;height: 302px;">
					    <img class="layui-upload-img" id="demo2" style="width: 100%;height: 100%;"/>
					    <p id="demoText2" ></p>					   
			    </div>
	        </div>
		   <div class="layui-form-item" pane>
			   <label class="layui-form-label">图片2地址:</label>
			   <div class="layui-input-block">
				   <input type="text" id="photo2" class="layui-input" />
			   </div>
		   </div>
	   </div>
	   </div>
	   <div style="clear:both;"></div>	  
	
	   <div style="display: inline-block;width: 50%;float: left;height: 410px;max-width:480px;margin-right: 10px;">
	   <div class="layui-form-item" pane>
			<div class="layui-upload" >
				<button type="button" class="layui-btn" id="test12">交友轮播图片三</button>	
				<div class="layui-upload-list"  style="width: 100%;height: 302px;">
					    <img class="layui-upload-img" id="demo3" style="width: 100%;height: 100%;"/>
					    <p id="demoText3" ></p>					   
			    </div>
	        </div>
		   <div class="layui-form-item" pane>
			   <label class="layui-form-label">图片3地址:</label>
			   <div class="layui-input-block">
				   <input type="text" id="photo3" class="layui-input" />
			   </div>
		   </div>
	   </div>
	   </div>
	   
	   <div style="display: inline-block;width: 50%;float: left;height: 410px;max-width:480px;margin-right: 10px;">
	   <div class="layui-form-item" pane>
			<div class="layui-upload" >
				<button type="button" class="layui-btn" id="test13">交友轮播图片四</button>	
				<div class="layui-upload-list" style="width: 100%;height: 302px;">
					    <img class="layui-upload-img" id="demo4" style="width: 100%;height: 100%;"/>
					    <p id="demoText4" style="color: red;"></p>					   
			    </div>
	        </div>
		   <div class="layui-form-item" pane>
			   <label class="layui-form-label">图片4地址:</label>
			   <div class="layui-input-block">
				   <input type="text" id="photo4" class="layui-input" />
			   </div>
		   </div>
	   </div>
	   </div>	  
	 
	   </br>
	   <div style="text-align: center;clear:both;">
	    	<button class="layui-btn"  id="submitBtn" onclick="saveImg()">立即提交</button>	 
	    </div>
 
<script type="text/javascript">
  var photo_one;
  var photo_two;
  var photo_three;
  var photo_five;
//图片上传
 layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;				  
	  //普通图片上传
	  var uploadInst = upload.render({
	    elem: '#test10'
	    ,url: js_path+'/activity/upload'
	    ,accept: 'images' //允许上传的文件类型
	    ,method: 'post'  //可选项。HTTP类型，默认post
	    ,size: 100000       //最大允许上传的文件大小
	    ,before: function(obj){
	       //alert(9)
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#demo1').attr('src', result); //图片链接（base64）
	        //alert(file);
	      });				       
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code > 0){
	    	  $('#demoText').html('<span style="color: red;">图片必须小于200k</span>');
	      }
	      //上传成功
	      else{
	    	  photo_one=res.activityImage;
	      }
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
			      });
			    }
			  });						         	
		  });
		  
 layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;				  
	  //普通图片上传
	  var uploadInst = upload.render({
	    elem: '#test11'
	    ,url: js_path+'/activity/upload'
	    ,accept: 'images' //允许上传的文件类型
	    ,method: 'post'  //可选项。HTTP类型，默认post
	    ,size: 100000       //最大允许上传的文件大小
	    ,before: function(obj){
	       //alert(9)
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#demo2').attr('src', result); //图片链接（base64）
	        //alert(file);
	      });				       
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code > 0){
	    	  $('#demoText2').html('<span style="color: red;">图片必须小于200k</span>');
	      }
	      //上传成功
	      else{
	    	  photo_two=res.activityImage;
	      }
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText2');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
			      });
			    }
			  });						         	
		  });
 
 layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;				  
	  //普通图片上传
	  var uploadInst = upload.render({
	    elem: '#test12'
	    ,url: js_path+'/activity/upload'
	    ,accept: 'images' //允许上传的文件类型
	    ,method: 'post'  //可选项。HTTP类型，默认post
	    ,size: 100000       //最大允许上传的文件大小
	    ,before: function(obj){
	       //alert(9)
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#demo3').attr('src', result); //图片链接（base64）
	        //alert(file);
	      });				       
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code > 0){
	    	  $('#demoText3').html('<span style="color: red;">图片必须小于200k</span>');
	      }
	      //上传成功
	      else{
	    	  photo_three=res.activityImage;

	      }
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText3');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
			      });
			    }
			  });						         	
		  });
 layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;				  
	  //普通图片上传
	  var uploadInst = upload.render({
	    elem: '#test13'
	    ,url: js_path+'/activity/upload'
	    ,accept: 'images' //允许上传的文件类型
	    ,method: 'post'  //可选项。HTTP类型，默认post
	    ,size: 100000       //最大允许上传的文件大小
	    ,before: function(obj){
	       //alert(9)
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#demo4').attr('src', result); //图片链接（base64）
	        //alert(file);
	      });				       
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code > 0){
	    	  $('#demoText4').html('<span style="color: red;">图片必须小于200k</span>');
	      }
	      //上传成功
	      else{
	    	  photo_five=res.activityImage;

	      }
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText4');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
			      });
			    }
			  });						         	
		  });
       
		var i = 1; 
		var intervalid; 	
		function fun() { 
			if (i == 0) { 
				window.location.reload();
				clearInterval(intervalid); 
			  } 		
			   i--; 
		 } 	
       function saveImg(){   	   
		   var t1 = new Object;
		   t1.imageName=photo_one;
		   t1.connectionAddress=$("#photo1").val();
           var t2 = new Object;
           t2.imageName=photo_two;
           t2.connectionAddress=$("#photo2").val();
           var t3 = new Object;
           t3.imageName=photo_three;
           t3.connectionAddress=$("#photo3").val();
           var t4 = new Object;
           t4.imageName=photo_five;
           t4.connectionAddress=$("#photo4").val();

    	   $.ajax({
				url:js_path+"/activity/saveActivityImg",
				data:{'json1':JSON.stringify(t1),'json2':JSON.stringify(t2),'json3':JSON.stringify(t3),'json4':JSON.stringify(t4)},
				dataType:'json',//服务器返回json格式数据
				type:'POST',//HTTP请求类型
				success:function(data){ 
					if(data.code == 200){
						layer.msg(data.msg, {icon: 1});
						intervalid = setInterval("fun()", 1000); 
						
					}else{
						layer.msg(data.msg, {icon: 3});
					}					
				},error:function(e){
					layer.msg('系统异常', {icon: 3});
				}
			});
       }
   
		
</script>
</body>
</html>