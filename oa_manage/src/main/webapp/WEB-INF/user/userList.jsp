<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>用户管理</title>
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
	        <span class="layui-form-label">用户名称：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="name" autocomplete="off" placeholder="名称" class="layui-input" />
		    </div>

			<span class="layui-form-label">用户类型：</span>
			<div class="layui-input-inline">
				<select id="userType" lay-verify="" >
					<option value="0">--全部--</option>
					<option value="1">注册用户</option>
					<option value="2">认证用户</option>
					<option value="3">完善资料用户</option>
				</select>
			</div>

		    <label class="layui-form-label">积分范围：</label>
		    <div class="layui-input-inline" style="width: 100px;">
		   		<input type="text" id="integralStart" autocomplete="off" class="layui-input" />
		    </div>
		    <div class="layui-form-mid">-</div>
		    <div class="layui-input-inline" style="width: 100px;">
		   		<input type="text" id="integralEnd" autocomplete="off" class="layui-input" />
		    </div>
		    
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	   		<button type="reset" id="resetId" class="layui-btn layui-btn-primary">重置</button>
	    </div>
    </blockquote>
</form>

<!--  
<blockquote class="layui-elem-quote">
	<button class="layui-btn" id="add">添加</button>
</blockquote>
-->
 
<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table> 
 
<!-- 设置操作按钮 --> 
<script type="text/html" id="barDemo">
  	<a class="layui-btn layui-btn-mini" lay-event="detail">详情</a>
	<a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="LockUser">封号</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="unlock">解封</a>
</script>

<!-- 查看信息页面 -->
<div style="display: none" id="detail" >
	<table id="activityDetail" lay-filter="activityDetail"></table>
</div>


<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script type="text/html" id="titleTpl">
	{{# if(d.sex =='1'){ }}
		男
	{{#  } else { }}
		女
	{{#  } }}
</script>
<script type="text/html" id="titleTpl2" >
	{{#  if(d.userType ==1){ }}
 		注册用户
	{{#  } }}
	{{#  if(d.userType ==2){ }}
 		单身用户
	{{#  } }}
	{{#  if(d.userType ==3){ }}
 		会员用户
	{{#  } }}
	{{#  if(d.userType ==4){ }}
		封号用户
	{{#  } }}
</script>
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
	 	  ,url: js_path+'/sysUserinfo/queryDynamic2'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
	 	        {field: 'name', title: '姓名', width: 150, align:'center'}
	 	       ,{field: 'nickname', title: '昵称', width: 125, align:'center'}
	 	       ,{field: 'sex', title: '性别', templet:'#titleTpl' , width: 150, align:'center'}
		       ,{field: 'phone', title: '号码', width: 170, align:'center' }
		       ,{field: 'userType', title: '用户类型',templet:'#titleTpl2', width: 170, align:'center'}
		       ,{field: 'integral', title: '用户积分', width: 100, align:'center'}
		       ,{field: 'createTime', title: '创建时间', width: 200, align:'center'}
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
		$('name').val("");
		$('integralStart').val("");
		$('integralEnd').val("");
//		queryDynamic();
	});
		
	//监听工具条
	table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
		,layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'detail'){
		    //查询该用户参加过的所有活动
		    $.ajax({
			        type:"POST",
			        dataType: 'json',
			        url:js_path+'/activity/queryActivityByUserId',
				    data:{'id':obj.data.id},
				    success: function(data) {
				    	if(data.code == 200){
				    		//confirm('添加横过!!!');
				    		var tableData = data.data;
				    		layer.msg('查询成功!!!', {icon: 6}); 
				    		// 打开一个页面
				    		layer.open({
				    			type: 1
				    			,skin: 'demo-class'
				    			,title: '活动详情'
				    			,area: ['800px', '400px']
				    			,offset: '100px' 			//只定义top坐标，水平保持居中
				    			,shade:['0.3','#000']
				    			,maxmin: true
				    			,content:$('#detail')
				    			,btn: ['确认', '取消']
				    			,yes: function(index,layero){
				    				layer.closeAll();
				    			}
				    		});
				    		// 进行数据渲染
				    		table.render({
				    			data:tableData
				    		//	,skin: 'line' 					//行边框风格
				    			,even: true 					//开启隔行背景
				    			,elem: '#activityDetail' 		//指定原始表格元素选择器（推荐id选择器）
				    	//		,height: 200 					//容器高度
				    			,cols: [[						//设置表头
				    				 {field: 'name', title: '姓名', width: 120, align:'center'}
				    				 ,{field: 'sex', title: '性别',templet:'#titleTpl', width: 100, align:'center'}
				    		 	     ,{field: 'phone', title: '手机号码', width: 150, align:'center'}
				    		 	     ,{field: 'gainIntegral', title: '获得积分' , width: 100, align:'center'}
				    			     ,{field: 'title', title: '参加活动名称', width: 210, align:'center' }
				    			]] 		
				    		});
				    		
				    	}else if(data.code == 400){
				    		layer.msg(data.msg);
				    	}
				    },
				}); 

		}else if(layEvent == "LockUser"){
		    //锁定用户
			if(data.userType != "2"){
                layer.msg('不是单身用户没必要封号。', {icon: 2});
                return false;
            }
			$.ajax({
                type:"POST",
                dataType: 'json',
                url:js_path+'/sysUserinfo/LockUser',
                data:{'userId':obj.data.id},
                success:function(data){
                    layer.msg(data.msg, {icon: 6});
                    queryDynamic();
				}
			});

		}else if(layEvent == "unlock"){
			//解锁用户
            if(data.userType != "4"){
                layer.msg('该用户不是封号状态，不需要解封。', {icon: 2});
                return false;
            }
            $.ajax({
                type:"POST",
                dataType: 'json',
                url:js_path+'/sysUserinfo/unlock',
                data:{'userId':obj.data.id},
                success:function(data){
                    layer.msg(data.msg, {icon: 6});
                    queryDynamic();
                }
            });

		}

	});
  
	//添加按钮事件
	$('#add').click(function(e){
		// 打开页面
		layer.open({
			type: 1
			,skin: 'demo-class'
			,title: '添加用户'
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
			        url:js_path+'/sysUserinfo/add',
				    data:{'json':JSON.stringify(getUserInfo())},
				    success: function(data) {
				    	if(data.code == 200){
				    		//confirm('添加横过!!!');
				    		layer.msg('添加成功!!!', {icon: 6}); 
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
		//===========用layui.open打开的页面，必须打开后zai初始化 富文本编辑器,否则会报错！==============
		layedit.set({
			uploadImage:{
				url: js_path+'/activity/upload2'
				,type: 'post'
				,size: 200    			//图片大小 		
			}
		});
		editorFirst = layedit.build('LAY_editor1',{height: 180}); 	//建立编辑器
//		layedit.build('LAY_editor2'); 	//建立编辑器
		
	});
	
	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#birthdateId'
		,type: 'date'
		//,range: true //或 range: '~' 来自定义分割字符
	});
	
	// 获取添加用户信息
	function getUserInfo(){
		var t = new Object();
		t.phone=$('#phoneId').val();
		t.password=$('#passwordId').val();
		t.userType=$('#userTypeId').val();
		t.name=$('#userNameId').val();
		t.nickname=$('#nicknameId').val();
		t.age=$('#userAgeId').val();
		t.idCard=$('#idCardId').val();
		t.sex=$('#userSexId').val();
		t.birthdate=$('#birthdateId').val()+" 00:00:00";
		t.points=100;
		return t;
	}
	
	// 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var sysUserinfoVo = new Object();
		sysUserinfoVo.page=currentPage;
		sysUserinfoVo.pageSize=10;
	//  如有查询条件，请写在此处	
		t.name=$.trim($("#name").val());
		t.status=$("#userType").val();

		// 判断积分范围  是否为空为数字
		if($.trim($('#integralStart').val()) != ''){
			sysUserinfoVo.integralStart=$.trim($('#integralStart').val());
			if($.isNumeric($('#integralStart').val())==false){
				alert("请输入一个整数!");
				sysUserinfoVo.integralStart=null;
			}
		}
		// 判断积分范围  是否为空为数字
		if($.trim($('#integralEnd').val()) != ''){
			sysUserinfoVo.integralEnd=$.trim($('#integralEnd').val());
			if($.isNumeric($('#integralEnd').val())==false){
				layer.msg("请输入一个整数!");
				sysUserinfoVo.integralEnd=null;
			}
		}
		sysUserinfoVo.t=t;
		return JSON.stringify(sysUserinfoVo);
	}

});
</script>
</body>
</html>