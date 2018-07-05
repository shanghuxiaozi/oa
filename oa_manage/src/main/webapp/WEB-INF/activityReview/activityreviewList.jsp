<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>活动回顾</title>
	<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css?t=1506699022911" media="all" />
	<style>
	  body{margin: 10px;}
	  .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
	  .layui-table-body.layui-table-main{height:508.99px;}
	  .layui-table-view .layui-table td, .layui-table-view .layui-table th{padding:10px 0;}
	  .img-item{
	  	display:inline-block;
	  	position: relative;
	  }
	  .img-delete{
	  	position: absolute;
	  	right:0;
	  	top:0;
	  	width:20px;
	  	height:20px;
	  	text-align:center;
	  	line-height:20px;
	  	border-radius:100px;
  	    background-color: #009688;
  	    font-style: normal;
	    color: #fff;
	    cursor: pointer;
	  }
	</style>
</head>
<body>

<form class="layui-form" action="" target="nm_iframe">
	<blockquote class="layui-elem-quote">
	   	<div class="layui-form-item">
	        <span class="layui-form-label">活动名称：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="name" autocomplete="off" placeholder="活动名称" class="layui-input" />
		    </div>
		    
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	   		<button type="reset" id="resetId" class="layui-btn layui-btn-primary">重置</button>
	   		
	    </div>
    </blockquote>
</form>

<blockquote class="layui-elem-quote">
	<button class="layui-btn" id="add">添加活动</button>
</blockquote>

 
<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table> 
 
<!-- 设置操作按钮 --> 
<script type="text/html" id="barDemo">
	<!-- <a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">编辑</a> -->
	<a class="layui-btn layui-btn-mini" lay-event="detail">详情</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>


<!-- 添加页面 -->
<div style="display: none" id="addActivity" >
<form class="layui-form  layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;" id="formperm">
		
	<div class="layui-form-item" pane>
		<label class="layui-form-label">活动标题:</label>
		<div class="layui-input-block">
			<input type="text" id="title" lay-verify="required" autocomplete="off" class="layui-input" />
		</div>
	</div>
	
	<!-- 时间选择器 -->	
	<div class="layui-form-item" pane>
		<label class="layui-form-label">活动时间:</label>
		<div class="layui-input-inline">
		    <input type="text" class="layui-input" id="birthdateId" />
		</div>
	</div>
	
	<div class="layui-form-item" pane>
		<label class="layui-form-label">活动图片:</label>
		<div class="layui-input-block" id="appendDiv">
			<p style="padding-left:20px;">
				<input type="button" id="addImg" class="layui-btn" value="添加主题图片-建议规格475*375" />
			</p>
			<!-- <span class="img-item">
				<i class="img-delete delImg" >x</i>
				<img src="" class="layui-upload-img" style="height: 150px;width: 300px" />
			</span> -->
		</div>
	</div>
	
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">活动内容:</label>
	    <div class="layui-input-block">
	    	<textarea id="layui_editor1" style="display: none;"></textarea>
	    </div>
    </div>
		
</form> 
</div>

<!-- 查看信息页面 -->
<div style="display: none" id="detail" >
<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;" id="formperm">
		
	<div class="layui-form-item" pane>
		<label class="layui-form-label">活动标题:</label>
		<div class="layui-input-block">
			<input type="text" id="titleName" disabled="disabled" class="layui-input" />
		</div>
	</div>
	<div class="layui-form-item" pane>
		<label class="layui-form-label">活动时间:</label>
		<div class="layui-input-inline">
		    <input type="text" class="layui-input" disabled="disabled" id="activityDate" />
		</div>
	</div>
	<div class="layui-form-item" pane>
	    <label class="layui-form-label">活动内容:</label>
	    <div class="layui-input-block">
	    	<textarea id="layui_editor2" style="display: none;"></textarea>
	    </div>
    </div>
</form> 
</div>

<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script>
var currentPage=1;

layui.use(['layedit', 'jquery','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	var $ = layui.jquery;  			//引用自身的JQuery
	var laydate = layui.laydate 	//日期
  	,laypage = layui.laypage 		//分页
  	,layer = layui.layer 			//弹层
  	,table = layui.table 			//表格
  	,carousel = layui.carousel 		//轮播
 	,upload = layui.upload 			//上传
 	,layedit = layui.layedit 		//文本编辑器
 	,element = layui.element; 		//元素操作
 	var editorFirst;				//富文本编辑器 全局变量!
  	//向世界问个好
//  	layer.msg('Hello World');
  
 	//================数据渲染     方法一Start====================
 	function queryDynamic(){	
	table.render({
		  elem: '#dateTable' 		//指定原始表格元素选择器（推荐id选择器）
		  ,height: 590 		 		//容器高度
		  ,page:true 		 		//开启分页 
		  ,even: true 				//开启隔行背景
		  ,method:'post'
		  ,request: {		 		//分页   设置分页名称
			  pageName: 'page' 		//页码的参数名称，默认：page
		 	 ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		  }
			  	  					//分页参数设置  	
	  	  ,limits:[10]
	  	  ,limit: 10 				//默认采用60	
		  ,response: {
			  statusName: 'code'  	//数据状态的字段名称，默认：code
			  ,statusCode: 200 		//成功的状态码，默认：0
			  ,msgName: 'msg'  		//状态信息的字段名称，默认：msg
			  ,countName: 'total' 	//数据总数的字段名称，默认：count
			  ,dataName: 'data' 	//数据列表的字段名称，默认：data
		  } 	
	 	  ,where:{queryRestraintJson:getQueryParam()}
	 	  ,url: js_path+'/activityReview/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
	 	        {field: 'title', title: '活动名称', width: 250, align:'center'}
	 	       ,{field: 'content', title: '活动内容', width: 400, align:'center'}
	 	       ,{field: 'activityTime', title: '活动时间', width: 180, align:'center'}
		       ,{fixed: 'right', width:200,height:150, align:'center',title:'操作',toolbar:'#barDemo'}
	 		    ]]
  		  // 数据渲染完的回调   无论是异步请求数据，还是直接赋值数据，都会触发该回调.
	 	  ,done: function(res, curr, count){
	 		  //如果是异步请求数据方式，res即为你接口返回的信息。
	 		  //如果是直接赋值的方式，res即为：{code: 200, msg: "查询成功", total: 17, data: Array(10)} 
	 		  console.log(res);
	 		  //得到当前页码
	 		  console.log(curr);
	 		  //得到数据总量
	 		  console.log(count);
		  }
	}); 
 	}	  		  
	//================数据渲染   end======================
		
		
	// 初始化加载查询方法	
	queryDynamic();
	
	//查询按钮事件
	$('#query').click(function(e){
		queryDynamic();
	});
	
	//重置按钮事件
	$('#resetId').click(function(e){
		$('#name').val("");
		queryDynamic();
	});
		
	//监听工具条
	table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data 	//获得当前行数据
		,layEvent = obj.event;  //获得 lay-event 对应的值
		if(layEvent === 'detail'){
			// 详情页面  赋值
			$('#titleName').val(data.title);
			$('#activityDate').val(data.activityTime);
			$('#layui_editor2').html(data.content);
			// 打开页面
			layer.open({
				type: 1
				,skin: 'demo-class'
				,title: '活动详情'
				,offset: '100px' 			//只定义top坐标，水平保持居中
				,area: ['1000px', '500px']
				,shade:['0.3','#000']
				,maxmin: true
				,content:$('#detail')
				,btn: ['确认', '取消']
				,yes: function(index,layero){
					layer.closeAll();
				}
				,btn2: function(){
	                layer.closeAll();
	            }
				,zIndex: layer.zIndex
				,success: function(layero){
	           	 	element.init;
	           	 	layer.setTop(layero);
	            }
			});	
			layedit.build('layui_editor2',{height: 280}); 	//建立编辑器
		// 删除事件	
		}else if(layEvent === 'del'){
            layer.confirm('确定删除该条数据?删除后将不可恢复哦。', function(index){
				$.ajax({
					type:"POST",
					dataType: 'json',
					url:js_path+'/activityReview/delete',
					data:{'id':data.id},
					success: function(data) {
						if(data.code == 200){
							layer.msg('删除成功', {icon: 6});
							delspotAndModuleHandler('2');
						}else{
							layer.msg('删除失败，数据可能已经被删除，请刷新后重试。', {icon: 6});
						}
						queryDynamic();
					}
				});
            });
        }
	});
  
	//添加按钮事件
	$('#add').click(function(e){
		// 打开页面
		layer.open({
			type: 1
			,skin: 'demo-class'
			,title: '添加活动'
			,offset: '100px' 			//只定义top坐标，水平保持居中
			,area: ['800px', '400px']
			,shade:['0.3','#000']
			,maxmin: true
			,content:$('#addActivity')
			,btn: ['确认', '取消']
			,yes: function(index,layero){
			 	$.ajax({
			        type:"POST",
			        dataType: 'json',
			        url:js_path+'/activityReview/add',
				    data:{'json':JSON.stringify(getActivityInfo())},
				    success: function(data) {
				    	if(data.code == 200){
				    		layer.msg('添加成功!!!', {icon: 6}); 
				    		queryDynamic();
				    		spotAndModuleHandler('2');
				    	}else{
				    		layer.msg('添加失败!!!', {icon: 6}); 
				    	}
				    },
				}); 
				layer.closeAll();
			}
			,btn2: function(){
                layer.closeAll();
            }
			,zIndex: layer.zIndex
			,success: function(layero){
           	 	element.init;
           	 	layer.setTop(layero);
            }
			
		});		
		//===========用layui.open打开的页面，必须打开后 再初始化 富文本编辑器,否则会报错！==============
		// 如富文本编辑器需要上传图片，必须在build方法之前set图片上传的路劲。	
		layedit.set({
			uploadImage:{
				url: js_path+'/activityReview/uploadImg'  	//此方法返回格式 {code:0,msg:'',data:{src:'http://xxx/xxx.png',title:'xxxx.png'}}
				,type: 'post'
				,size: 200    			//图片大小 		
			}
		});
		editorFirst = layedit.build('layui_editor1',{height: 180}); 	//建立编辑器
	});
	
	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#birthdateId'
		,type: 'date'
		//,range: true //或 range: '~' 来自定义分割字符
	});
	
	//图片上传 初始化
  	upload.render({
	    elem: '#addImg' //绑定元素
	    ,url: js_path+'/activityReview/uploadImg' //上传接口
	    ,done: function(req){
	        //上传完毕回调
	        if(req.code == 0){
	        	var imgUrl = req.data.src;
	        	var html = "<span class='img-item'><i class='img-delete delImg'>x</i><img src='"+imgUrl+"' class='layui-upload-img' style='height: 150px;width: 300px' /></span>";
	        	$('#appendDiv').append(html);
	        	
	        }
	    }
	    ,error: function(){
	      	//请求异常回调
	    }
  	});
	
	// 删除图片事件delImg
  	$("#appendDiv").on('click','.delImg',function(e){
		$(this).parents('.img-item').remove();
	});
	
	// 获取添加活动信息
	function getActivityInfo(){
		var t = new Object();
		t.title=$('#title').val();
		t.content=layedit.getContent(editorFirst);
		t.activityTime=$('#birthdateId').val()+" 00:00:00";
		// 获取图片 
		var imgs = $('#appendDiv').find("img");
		var photo = "";
		imgs.each(function (){
			photo += ($(this).attr("src"))+",";
		});
		t.activityImage=photo;
		return t;
	}
	
	// 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var mfActivityReviewVo = new Object();
		mfActivityReviewVo.page=currentPage;
		mfActivityReviewVo.pageSize=10;
		//如有查询条件，请写在此处	
		t.title=$.trim($("#name").val());
		mfActivityReviewVo.t=t;
		return JSON.stringify(mfActivityReviewVo);
	}
	
	/**
	*活动回顾的热点提示
	*moduleId 模块id
	*/
	function spotAndModuleHandler(moduleId,userId){
		
		$.ajax({
			type:"POST",
	        dataType: 'json',
	        url:js_path+'/spotpush/queryModule',
		    data:{'json':JSON.stringify({'moduleId':moduleId,'userId':userId})},		
		    success: function(data) {
		    	
		    	//存在就更新
		    	if(data.data&&data.data.length>0){
		    		var item = data.data[0];
		    		var timesOfBrowsing = parseInt(item.timesOfBrowsing);
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/updateModule',
    				    data:{'json':JSON.stringify({'id':item.id,'timesOfBrowsing':++timesOfBrowsing,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    		
		    	}else{//不存在就创建
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/addModule',
    				    data:{'json':JSON.stringify({'timesOfBrowsing':'1','moduleId':moduleId,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    	}
		    	
		    }
		});
	}
	
	/**
	*减少对应的热点提示
	*moduleId 模块id
	*/
	function delspotAndModuleHandler(moduleId,userId){
		$.ajax({
			type:"POST",
	        dataType: 'json',
	        url:js_path+'/spotpush/queryModule',
		    data:{'json':JSON.stringify({'moduleId':moduleId,'userId':userId})},		
		    success: function(data) {
		    	
		    	//存在就更新
		    	if(data.data&&data.data.length>0){
		    		var item = data.data[0];
		    		var timesOfBrowsing = parseInt(item.timesOfBrowsing);
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/updateModule',
    				    data:{'json':JSON.stringify({'id':item.id,'timesOfBrowsing':--timesOfBrowsing,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    		
		    	}else{//不存在就创建
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/addModule',
    				    data:{'json':JSON.stringify({'timesOfBrowsing':'0','moduleId':moduleId,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    	}
		    	
		    }
		});
	}
	
});
</script>
</body>
</html>