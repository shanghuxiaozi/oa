<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>投诉管理</title>
	<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/zoomove.min.css" />
	<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css?t=1506699022911" media="all" />
	<style>
	  body{margin: 10px;}
	  .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
	  .layui-table-body.layui-table-main{height:508.99px;}
	  .layui-table-view .layui-table td, .layui-table-view .layui-table th{padding:10px 0;}
	  .layui-input-block .zoo-item .zoo-img{background-size: 100% 100%;}
		#reportImage img{
			width:300px;
			height: 300px;
		}
	</style>
</head>
<body>

<form class="layui-form" action="" target="nm_iframe">
	<blockquote class="layui-elem-quote">
	    <div class="layui-form-item" >
			<label class="layui-form-label">投诉状态:</label>
			<div class="layui-input-inline">
			<select name="" id="processingState" lay-verify="" >
				<option value="2">未处理</option>
				<option value="1">已处理</option>
				<option value="3">--全部--</option>
			</select>  
			</div>
			<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
		</div>
    </blockquote>
</form>

<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table> 
 
<!-- 设置操作按钮 --> 
<script type="text/html" id="barDemo">
  	<a class="layui-btn layui-btn-mini" lay-event="edit">处理</a>
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

	<!-- <a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">编辑</a>
	<a class="layui-btn layui-btn-mini" lay-event="detail">详情</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a> -->


<!-- 处理显示页面 -->
<div style="display: none" id="handle" >
<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;" id="formperm">
				
	<div class="layui-form-item" >
		<label class="layui-form-label">投诉人名称:</label>
		<div class="layui-input-block">
			<input type="text" id="reportName" lay-verify="required" autocomplete="off" class="layui-input" />
		</div>
	</div>
	
	<div class="layui-form-item" >
		<label class="layui-form-label">投诉人昵称:</label>
		<div class="layui-input-block">
			<input type="text" id="reportnickName" lay-verify="required" autocomplete="off" class="layui-input" />
		</div>
	</div>
		
	<div class="layui-form-item" >
		<label class="layui-form-label">被投诉人名称:</label>
		<div class="layui-input-block">
			<input type="text" id="coverReportName" lay-verify="required" autocomplete="off" class="layui-input" />
		</div>
	</div>	
	
	<div class="layui-form-item" >
		<label class="layui-form-label">被投诉人昵称:</label>
		<div class="layui-input-block">
			<input type="text" id="coverReportNickname" lay-verify="required" autocomplete="off" class="layui-input" />
		</div>
	</div>
	
	<div class="layui-form-item" >
		<label class="layui-form-label">投诉内容:</label>
		<div class="layui-input-block">
			<textarea id="reportContent" required lay-verify="required" class="layui-textarea"></textarea>
		</div>
	</div>
	
	<div class="layui-form-item" pane>
		<label class="layui-form-label">投诉截图:</label>
		<div class="layui-input-block" style="height:500px;">
			<div  id="reportImage" >

			</div>
		</div>
	</div>
	<hr class="layui-bg-black"></hr>
</form> 
</div>

<!-- 向用户发送信息页面 -->
<div style="display: none" id="selectForm" >
	<form class="layui-form" action="">
		<div class="layui-form-item">
		    <label class="layui-form-label">请选择：</label>
		    <div class="layui-input-block">
		    	1<input type="radio" name="rd" value="1" title="将投诉人增加10积分，将被投诉人减少10积分。" checked />
		    	<textarea id="message1" placeholder="系统收到您的投诉，感谢您对本平台的支持，特奖励您10积分，希望您一如既往的支持本平台。" class="layui-textarea"></textarea>
		    	<textarea id="message2" placeholder="由于您：xxxxx违反平台规则，将您积分减少10分，以示惩戒。" class="layui-textarea"></textarea>
            </div>
		</div>
		<hr class="layui-bg-black"></hr>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      2<input type="radio" name="rd" value="2" title="将被投诉人封号处理" />
		    </div>
		</div>
    </form>
</div>

<script src="${basePath }/layui-v2.1.4/layui/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="${basePath }/layui-v2.1.4/layui/zoomove.min.js" type="text/javascript"></script>    
<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script type="text/html" id="titleTpl">
	{{# if(d.status =='1'){ }}
		已处理
	{{#  } else { }}
		未处理
	{{#  } }}
</script>
<script type="text/html" id="imgTpl" >
	{{#
	var imgFormat = function(imgUrl){
	var photo = [];
	photo = JSON.parse(imgUrl);
	var htmlImg = "";
	for(var i=0;i<photo.length;i++){
	htmlImg += '<img src="'+photo[i]+'" class="mui-img-response">';
	}
	return htmlImg;
	}
	}}
	{{# if(d.image != ''){ }}
	{{ imgFormat(d.image) }}
	{{#  } else { }}
	/
	{{#  } }}
</script>
<script type="text/javascript" >
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
	 	  ,url: js_path+'/report/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
	 	        {field: 'uname2', title: '举报人名称', width: 150, align:'center'}
	 	       ,{field: 'unickname2', title: '举报人昵称', width: 150, align:'center'}
	 	       ,{field: 'content', title: '举报内容', width: 320, align:'center'}
		       ,{field: 'uname', title: '被举报人名称', width: 150, align:'center'}
		       ,{field: 'unickname', title: '被举报人昵称', width: 150, align:'center'}
		       ,{field: 'time', title: '举报时间', width: 150, align:'center'}
		       ,{field: 'image', title: '举报图片',templet:'#imgTpl',width: 180, align:'center' }
		       ,{field: 'status', title: '状态',templet:'#titleTpl',width: 180, align:'center' }
		       ,{fixed: 'right', width:200,height:80, align:'center',title:'操作',toolbar:'#barDemo'}
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
	
	//监听工具条
	table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data 		//获得当前行数据
		,layEvent = obj.event; 		//获得 lay-event 对应的值
		if(layEvent === 'edit'){
			// 为显示的页面进行赋值
			$('#reportName').val(data.uname2);
			$('#reportnickName').val(data.unickname2);
			$('#coverReportName').val(data.uname);
			$('#coverReportNickname').val(data.unickname);
			$("#reportContent").html(data.content);
            var photo = [];
            photo = JSON.parse(data.image);
            var htmlImg = "";
            for(var i=0;i<photo.length;i++){
                htmlImg += '<img src="'+photo[i]+'" class="mui-img-response">';
            }
            $('#reportImage').html(htmlImg);

			// 打开一个页面
    		layer.open({
    			type: 1
    			,skin: 'demo-class'
    			,title: '投诉详情'
    			,area: ['800px', '400px']
    			,offset: '100px' 			//只定义top坐标，水平保持居中
    			,shade:['0.3','#000']
    			,maxmin: true
    			,content:$('#handle')
    			,btn: ['投诉通过','投诉驳回', '取消']
    			,yes: function(index,layero){
    				if(data.status != '2'){
    					layer.msg('该投诉内容已经处理过,不能进行重复操作!',{icon: 2});
    					return false;
    				}
    				layer.close(index);
    				// 未处理过的数据才能继续处理.
    				layer.open({
    					type: 1
    	    			,skin: 'demo-class'
    	    			,title: '选择处理方式:'
    	    			,area: ['650px', '400px']
    	    			,offset: '100px' 			//只定义top坐标，水平保持居中
    	    			,shade:['0.3','#000']
    	    			,maxmin: false
    					,content: $('#selectForm')
    					,btn:['确定','取消']
    					,yes: function(index,layero){
    						var radio = $("input[name='rd']:checked").val();
    						var complaintMsg = $('#message1').val();
    						var beComplainsMsg = $('#message2').val();
    						// 写审核通过逻辑 
    	    				$.ajax({
    	    					type:"POST",
    	    			        dataType: 'json',
    	    			        url:js_path+'/report/auditPassed',
    	    				    data:{'id':data.id,'processingType':radio,'complaintMsg':complaintMsg,'beComplainsMsg':beComplainsMsg},		
    	    				    success: function(data) {
    	    				    	layer.msg('处理成功!', {icon: 6}); 

    	    				    	spotAndModuleHandler('4',data.tipOffUserId);
    	    				    }
    	    				});
    	    				layer.closeAll();
                            queryDynamic();
                            $('#message1').val("");
                            $('#message2').val("");
    					}
    					
    				});

    			}
    			,btn2:function(){
    				if(data.status != '2'){
    					layer.msg('该举报内容已经处理过,不能进行重复操作!',{icon: 2});
    					return false;
    				}
    				// 写审核不通过逻辑 
    				$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/report/reject',
    				    data:{'id':data.id,'status':data.status},		
    				    success: function(data) {
    				    	layer.msg('处理成功!', {icon: 6}); 
    				    	queryDynamic();
    				    	spotAndModuleHandler('4',data.tipOffUserId);
    				    }
    				});
    				layer.closeAll();
    			}
    		
    		});
			// 图片放大 插件初始化.
    		$('.zoo-item').ZooMove({
				cursor: 'true',
				scale: '1.2',
			});
		}else if(layEvent === 'del'){
			// 询问框
			layer.confirm('确定删除该条数据?删除后将不可恢复哦。', function(index){
                if(data.status == '1'){
                    layer.msg('该信息已经处理，不能删除。',{icon: 2});
                    layer.close(index);
                    return false;
                }
				//执行删除方法
				$.ajax({
					type:"POST",
			        dataType: 'json',
			        url:js_path+'/report/delete',
				    data:{'id':data.id},		
				    success: function(data) {
				    	layer.msg(data.msg, {icon: 1}); 
				    	queryDynamic();
				    }
				});
				layer.close(index);
			});   
		}
	});
	
	// 重置按钮注册事件
	$('#resetId').click(function(){
		$("#name").val("");		
	});
	
	// 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var tipOffDynamicQueryVo = new Object();
		tipOffDynamicQueryVo.page=currentPage;
		tipOffDynamicQueryVo.pageSize=10;
		// 查询参数  写在此处
		t.status=$.trim($("#processingState").val());
		t.types="1";
		tipOffDynamicQueryVo.t=t;
		return JSON.stringify(tipOffDynamicQueryVo);
	}
	
	/**
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
	
	
});
	  	  
</script>
</body>
</html>