<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>风采管理</title>
	<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/zoomove.min.css" />
	<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css?t=1506699022911" media="all" />
	<style>
	  body{margin: 10px;}
	  .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
	  .layui-table-body.layui-table-main{height:508.99px;}
	  .layui-table-view .layui-table td, .layui-table-view .layui-table th{padding:10px 0;}
	  .layui-input-block .zoo-item .zoo-img{background-size: 100% 100%;}
	</style>
</head>
<body>

<form class="layui-form" action="" target="nm_iframe">
	<blockquote class="layui-elem-quote">
	    <div class="layui-form-item" >
			<label class="layui-form-label">发布状态:</label>
			<div class="layui-input-inline">
			<select name="" id="stateus" lay-verify="" >
				<option value="1">待审核</option>
				<option value="2">审核通过</option>
				<option value="3">审核未通过</option>
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
	<a class="layui-btn layui-btn-mini" lay-event="detail">详情</a>
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

	<!-- <a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">编辑</a>
	<a class="layui-btn layui-btn-mini" lay-event="detail">详情</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a> -->


<!-- 详情显示页面 -->
<div style="display: none" id="handle" >
<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;" id="formperm">
				
	<div class="layui-form-item" >
		<label class="layui-form-label">发布人名称:</label>
		<div class="layui-input-block">
			<input type="text" id="releaseName" lay-verify="required" autocomplete="off" class="layui-input" />
		</div>
	</div>

	<div class="layui-form-item" >
		<label class="layui-form-label">发布的内容:</label>
		<div class="layui-input-block">
			<textarea id="releaseContent" required lay-verify="required" class="layui-textarea"></textarea>
		</div>
	</div>

	<div class="layui-form-item" pane>
		<label class="layui-form-label">发布的相片:</label>
		<div class="layui-input-block" style="height:500px;">
			<div id="imgContent">

			</div>
			<%--<figure style="width: 50%;height: 93%;" class="zoo-item" id="releaseImg" data-zoo-scale="3" data-zoo-image="">
			</figure>--%>
		</div>
		<div class="item">

		</div>
	</div>


</form>
</div>

<!-- 删除显示页面 -->
<div style="display: none" id="deleteDiv" >
	<form class="layui-form layui-form-pane" id="deleteForm" style="margin-right: 5%; margin-top: 10px;" >
		<div class="layui-form-item" >
			<label class="layui-form-label">删除理由:</label>
			<div class="layui-input-block">
				<textarea id="delContent" placeholder="由于您发布的内容违反了。。。现给予删除处理。" class="layui-textarea"></textarea>
			</div>
		</div>
	</form>
</div>

<script src="${basePath }/layui-v2.1.4/layui/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="${basePath }/layui-v2.1.4/layui/zoomove.min.js" type="text/javascript"></script>    
<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script type="text/html" id="titleTpl">
	{{#  if(d.status ==1){ }}
		待审核
	{{#  } }}
	{{#  if(d.status ==2){ }}
		审核通过
	{{#  } }}
	{{#  if(d.status ==3){ }}
		审核不通过
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
	{{# if(d.imgUrl != ''){ }}
		{{ imgFormat(d.imgUrl) }}
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
  	,carousel = layui.carousel 	//轮播
 	,upload = layui.upload 		//上传
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
	 	  ,url: js_path+'/memberStyle/queryJoinTableDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
	 	        {field: 'nickname', title: '发布者名称', width: 150, align:'center'}
	 	       ,{field: 'messages', title: '举报内容', width: 150, align:'center'}
	 	       ,{field: 'imgUrl', title: '发布图片', templet:'#imgTpl',width: 320, align:'center'}
		       ,{field: 'createDate', title: '创建时间', width: 150, align:'center'}
			   ,{field: 'status', title: '状态', width: 150,templet:"#titleTpl",align:'center'}
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
	//================数据渲染 end======================

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

		//详情
		if(layEvent === 'detail'){
		    //赋值操作
			$('#releaseName').val(data.nickname);
            $('#releaseContent').val(data.messages);

                var photo = [];
                photo = JSON.parse(data.imgUrl);
                var htmlImg = "";
                for(var i=0;i<photo.length;i++){
                    htmlImg += '<img src="'+photo[i]+'" class="mui-img-response">';
                }
				$("#imgContent").html(htmlImg);
//            $('#releaseImg').attr('data-zoo-image',data.imgUrl);

            // 打开一个页面
            layer.open({
                type: 1
                ,skin: 'demo-class'
                ,title: '动态详情'
                ,area: ['800px', '400px']
                ,offset: '100px' 			//只定义top坐标，水平保持居中
                ,shade:['0.3','#000']
                ,maxmin: true
                ,content:$('#handle')
                ,btn: ['审核通过', '审核未通过','取消']
                ,btn1: function(index,layero){
                    if(data.status != "1"){
                        layer.msg("该数据已经被审核。", {icon: 1});
                        return false;
                    }
                    $.ajax({
                        type:"POST",
                        dataType: 'json',
                        url:js_path+'/memberStyle/pass',
                        data:{'id':data.id},
                        success: function(data) {
							layer.msg(data.msg, {icon: 1});
                            queryDynamic();
                        }
                    });
                    layer.closeAll();
                },btn2:function(){
                    if(data.status != "1"){
                        layer.msg("该数据已经被审核。", {icon: 1});
                        return false;
                    }
                    $.ajax({
                        type:"POST",
                        dataType: 'json',
                        url:js_path+'/memberStyle/notThrough',
                        data:{'id':data.id},
                        success: function(data) {
                            layer.msg(data.msg, {icon: 1});
                            queryDynamic();
                        }
                    });
                    layer.closeAll();
				}

            });

		//删除
		}else if(layEvent === 'del'){
			var delContent = $('#delContent').val();
            // 打开一个页面
            layer.open({
                type: 1
                ,skin: 'demo-class'
                ,title: '删除动态将理由发送给发表人'
                ,area: ['800px', '400px']
                ,offset: '100px' 			//只定义top坐标，水平保持居中
                ,shade:['0.3','#000']
                ,maxmin: true
                ,content:$('#deleteDiv')
                ,btn: ['确定', '取消']
                ,yes: function(index,layero){
                    //执行删除方法
                    $.ajax({
                        type:"POST",
                        dataType: 'json',
                        url:js_path+'/memberStyle/delete',
                        data:{'id':data.id,'delContent':delContent},
                        success: function(data) {
                            layer.msg(data.msg, {icon: 1});
                            queryDynamic();
                        }
                    });
                    layer.close(index);
                    $('#delContent').val("");
                }
            });
        }

     /*   $('.zoo-item').ZooMove({
            cursor: 'true',
            scale: '1.2',
        });*/
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
		t.status=$.trim($("#stateus").val());
//		t.types="1";
		tipOffDynamicQueryVo.t=t;
		return JSON.stringify(tipOffDynamicQueryVo);
	}

});
	  	  
</script>
</body>
</html>