<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
  <title>活动报名</title>
  <link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css?t=1506699022911" media="all" />
  <style>
    body{margin: 10px;}
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
    .layui-table-body.layui-table-main{height:508.99px;}
    .layui-table-view .layui-table td, .layui-table-view .layui-table th{padding:10px 0;}
  </style>
</head>
<body>

<form class="layui-form" action="" target="nm_iframe">
	<blockquote class="layui-elem-quote">
	   	<div class="layui-form-item">
	        <span class="layui-form-label">活动名称：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="name" autocomplete="off" placeholder="名称" class="layui-input" />
		    </div>
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>	   		
	    </div>
    </blockquote>
</form>

 
<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table> 
 
<!-- 设置操作按钮 --> 
<script type="text/html" id="barDemo">
          <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="detail">报名情况</a>
</script>

<!-- 分页组件 --> 
<div id="pageElem"></div> 

<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
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
  	//layer.msg('Hello World');
 	
  	
  
 	//================数据渲染     方法一Start====================
 	function queryDynamic(){	
	table.render({
		  elem: '#dateTable' 		//指定原始表格元素选择器（推荐id选择器）
		  ,height: 500 		 		//容器高度
		  ,page:false 		 		//开启分页 
		  ,method:'post'
		  ,request: {		 		//分页   设置分页名称
			  pageName: 'page' 		//页码的参数名称，默认：page
		 	  ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		  }
			  	  							//分页参数设置  	
	  	  ,limits:[8]
	  	  ,limit: 8 				//默认采用60	
		  ,response: {
			  statusName: 'code'  	//数据状态的字段名称，默认：code
			  ,statusCode: 200 		//成功的状态码，默认：0
			  ,msgName: 'msg'  		//状态信息的字段名称，默认：msg
			  ,countName: 'total' 	//数据总数的字段名称，默认：count
			  ,dataName: 'data' 	//数据列表的字段名称，默认：data
		  } 	
	 	  ,where:{queryRestraintJson:getQueryParam()}
	 	  ,url: js_path+'/friendsActivity/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[		        
		        {field: 'title', title: '活动名称', width: 300}
		       ,{field: 'time', title: '发布时间', width: 240}
		       ,{field: 'activityStartTime', title: '活动开始时间', width: 240}
		       ,{field: 'activityEndTime', title: '活动结束时间', width: 240}
		       ,{field: 'applyStartTime', title: '报名开始时间', width: 240}
		       ,{field: 'applyEndTime', title: '报名结束时间', width: 240}
		       ,{fixed: 'right', width:200,height:150, align:'center',title:'操作',toolbar:'#barDemo'}
	 		    ]]
  		  // 数据渲染完的回调   无论是异步请求数据，还是直接赋值数据，都会触发该回调.
	 	  ,done: function(res, curr, count){
	 		  //如果是异步请求数据方式，res即为你接口返回的信息。
	 		  //如果是直接赋值的方式，res即为：{code: 200, msg: "查询成功", total: 17, data: Array(10)} 
	 		/*   console.log(res);
	 		  //得到当前页码
	 		  console.log(curr);
	 		  //得到数据总量
	 		  console.log(count); */
	 		    
	 		  //分页模块加载
	 		  laypage.render({
	 			  elem:'pageElem'
	 			  ,curr:curr    //当前页码 	
	 			  ,count: count
	 			  ,limits: [8]
	 			  ,limit:8
	 			  ,skin: '#1E9FFF' 		//自定义选中色值
	 			  ,skip: true 			//开启跳页
	 			  ,hash: 'fenye' 			//自定义hash值
	 			  ,jump: function(obj, first){
	 			  	 /*  console.log("obj.curr:"+obj.curr);
	 			      console.log("obj.curr:"+obj.count);
	 			      console.log("obj.curr:"+first); */
	 			      if(first != true ){
	 			          //console.log('当前第'+ obj.curr +'页');
	 			      	  //重新定义查询参数
	 				      var t = new Object();
	 				      var entityVo = new Object();
	 				      entityVo.page=obj.curr-1;
	 				      entityVo.pageSize=8;
	 				      //如有查询条件，请写在此处	
	 				      entityVo.t=t;
	 				      var queryParam = JSON.stringify(entityVo);
	 				      	
		 			      $.ajax({
		 				      type: "POST",
		 				      dataType: "json",
		 				      traditional :true, 
		 				      url: js_path+'/friendsActivity/queryDynamic',
		 				      data:{'queryRestraintJson':queryParam},
		 				      success: function (data) {
		 				          var tableData = data.data;
		 				       	  table.render({ //其它参数在此省略
		 				       		  data: tableData
		 				       		  ,page: false //开启分页
		 				       		  ,height:500
		 				       		  ,elem:'#dateTable'
		 				       		  ,cols:[[
		 				       		      {field: 'title', title: '活动名称', width: 300}
			 				  		       ,{field: 'time', title: '发布时间', width: 240}
			 				  		       ,{field: 'activityStartTime', title: '活动开始时间', width: 240}
			 				  		       ,{field: 'activityEndTime', title: '活动结束时间', width: 240}
			 				  		       ,{field: 'applyStartTime', title: '报名开始时间', width: 240}
			 				  		       ,{field: 'applyEndTime', title: '报名结束时间', width: 240}
			 				  		       ,{fixed: 'right', width:200,height:150, align:'center',title:'操作',toolbar:'#barDemo'}
		 				       			      ]]
		 				       		  ,limit: 8 //默认采用60
		 				       			
		 				       	  });
		 				      },
		 				      error: function(data) {
		 				          layer.close(loadWin);
		 				          layer.msg('系统异常', {icon: 3});
		 				      }
		 				  }); 
	 			       }
	 			    }
	 		   }); 
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
		var data = obj.data //获得当前行数据
		,layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'detail'){
			//报名详情
			var layerAdd=layer.open({
                type: 2,
                skin: 'layui-layer-molv',
                title: '报名详情',
                area: ['70%', '90%'],
                shade:['0.3','#000'],
                maxmin: true,
                content:js_path+'/friendsActivity/enrollDetail?id='+data.id,
                btn: ['取消'],             
              });
		}else if(layEvent === 'del'){
		    layer.confirm('真的删除行么', function(index){
		      	obj.del(); //删除对应行（tr）的DOM结构
		      	layer.close(index);
		      	//向服务端发送删除指令
		    });
		}else if(layEvent === 'edit'){
		    layer.msg('编辑操作');
		}
	});
		
	//监听Tab切换
	element.on('tab(demo)', function(data){
	  	layer.msg('切换了：'+ this.innerHTML);
	  	//console.log(data);
	});
	
	//执行一个轮播实例
	carousel.render({
	  	elem: '#test1'
	  	,width: '100%' //设置容器宽度
	  	,height: 200
	  	,arrow: 'none' //不显示箭头
	  	,anim: 'fade' //切换动画方式
	});
	
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var entityVo = new Object();
		entityVo.page=currentPage-1;
		entityVo.pageSize=8;
	//  如有查询条件，请写在此处	
	    t.title=$("#name").val();
		t.type="0";
		entityVo.t=t;
		return JSON.stringify(entityVo);
	 }
    });		  
</script> 
</body>
</html>