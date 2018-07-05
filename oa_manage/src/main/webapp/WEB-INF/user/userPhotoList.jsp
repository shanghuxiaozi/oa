<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>用户头像管理</title>
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
	   	<div class="layui-form-item">
	        <span class="layui-form-label">身份证号码：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="idcard" autocomplete="off" placeholder="名称" class="layui-input" />
		    </div>
		    <span class="layui-form-label">手机号码：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="phoneNumber" autocomplete="off" placeholder="名称" class="layui-input" />
		    </div>
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	    </div>
    </blockquote>

</form>

<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table>

<!-- 设置详情页面 -->
<div style="display: none" id="detail" lay-filter="test22">
	<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;height: 100px"  id="formperm">

		<div class="layui-form-item" pane>
			<label class="layui-form-label">用户图像:</label>
			<div class="layui-input-block">
				<img id="userImg" src="" alt=""/>
			</div>
		</div>

	</form>
</div>

<%-- --%>
<div style="display: none" id="edit" lay-filter="">
	<form class="layui-form layui-form-pane" action="####" >
		<div class="layui-form-item">
			<label class="layui-form-label">头像:</label>
			<div class="layui-input-block">
				<input type="radio" name="sexPhoto" value="http://p3d590l7j.bkt.clouddn.com/1523862796630.jpg" title="选择默认男头像">
				<img style="width: 80px;" id="" src="http://p3d590l7j.bkt.clouddn.com/1523862796630.jpg" alt="" />
			</div>
			<div class="layui-input-block">
				<input type="radio" name="sexPhoto" value="http://p3d590l7j.bkt.clouddn.com/1523862880842.jpg" title="选择默认女头像">
				<img style="width: 80px;" src="http://p3d590l7j.bkt.clouddn.com/1523862880842.jpg" alt="" />
			</div>
		</div>
	</form>
</div>
<!-- 设置操作按钮 -->
<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-mini" lay-event="detail">详情</a>
	<a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="edit">修改头像</a>
</script>
	<!--
	<a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
	-->

<script src="${basePath }/layui-v2.1.4/layui/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="${basePath }/layui-v2.1.4/layui/zoomove.min.js" type="text/javascript"></script>   
<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script type="text/html" id="titleTpl">
	{{# if(d.sex =='1'){ }}
		男
	{{#  } else { }}
		女
	{{#  } }}
</script>
<script type="text/html" id="titleTpl2">
	{{#
		var fn = function(date){
			return date.substring(0,11);
		};
	}}
	{{# if(d.birthTime != '' ){ }}
		{{ fn(d.birthTime)  }}
	{{#  } else { }}
		d.birthTime
	{{#  } }}
</script>
<script type="text/html" id="titleTp3">
	{{#
		var fn = function(img){
		return '<img src="'+img+'" alt="" />';
		};
	}}
	{{# if(d.headImg != '' ){ }}
		{{ fn(d.headImg)  }}
	{{#  } else { }}
		/
	{{#  } }}
</script>
<script>
var currentPage=1;
layui.use(['layedit', 'jquery','laydate', 'laypage', 'layer','form', 'table', 'carousel', 'upload', 'element'], function(){
	var $ = layui.jquery;  			//引用自身的JQuery
    var form = layui.form;
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
//  layer.msg('Hello World');
  
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
	 	  ,url: js_path+'/userPhoto/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
				{field: 'realName', title: '姓名', width: 100, align:'center'}
				,{field: 'headImg', title: '头像',templet:'#titleTp3', width: 100, align:'center'}
			   ,{field: 'sex', title: '性别',templet:'#titleTpl', width: 100,align:'center'}
			   ,{field: 'nation', title: '民族', width: 100, align:'center'}
			   ,{field: 'birthTime', title: '出生日期',templet:"#titleTpl2", width: 130, align:'center'}
			   ,{field: 'hometown', title: '籍贯', width: 100, align:'center'}
			   ,{field: 'birthPlace', title: '出生地', width: 100, align:'center'}
			   ,{field: 'politicalVisage', title: '政治面貌', width: 100, align:'center'}
			   ,{field: 'education', title: '学历', width: 100, align:'center'}
			   ,{field: 'marriageStatus', title: '婚姻状态', width: 100, align:'center'}
			   ,{field: 'height', title: '身高', width: 100, align:'center'}
			   ,{field: 'idcard', title: '身份证号码', width: 80, align:'center'}
			   ,{field: 'phone', title: '手机号码', width: 80, align:'center'}
		       ,{field: 'workUnit', title: '工作单位', width: 120, align:'center' }
			   ,{field: 'unitAttribute', title: '单位属性', width: 120, align:'center' }
               ,{field: 'postNature', title: '岗位性质', width: 120, align:'center' }
			   ,{fixed: 'right', width:150, align:'center',title:'操作',toolbar:'#barDemo'}
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
		if(layEvent === 'detail'){
			//给详情页面赋值
			$("#userImg").attr("src",data.headImg);
            // 打开详情页面
            layer.open({
                type: 1
                ,skin: 'demo-class'
                ,title: '用户详情'
                ,area: ['800px', '400px']
                ,offset: '100px' 			//只定义top坐标，水平保持居中
                ,shade:['0.3','#000']
                ,maxmin: true
                ,content:$('#detail')
                ,btn: ['确认','取消']
                ,yes: function(index,layero){
                    layer.closeAll();
                }
            });
		}else if(layEvent === 'edit'){
            layer.open({
                type: 1
                ,skin: 'demo-class'
                ,title: '只支持修改头像'
                ,area: ['800px', '400px']
                ,offset: '100px' 			//只定义top坐标，水平保持居中
                ,shade:['0.3','#000']
                ,maxmin: true
                ,content:$('#edit')
                ,btn: ['确认','取消']
                ,yes: function(index,layero){
                    var photo = $('input:radio[name=sexPhoto]:checked').val();
					if(typeof(photo) == "undefined"){
						layer.msg("请选择一个默认头像",{icon: 2});
					    return false;
					}
					$.ajax({
					    type:"POST",
					    dataType: 'json',
					    url:js_path+'/userPhoto/updateImg',
					    data:{'id':data.id,"headImg":photo},
					    success: function(data) {
						    if(data.code == 200){
							    layer.msg('修改成功!', {icon: 6});
							    queryDynamic();
						    }
					    }
					});
                    layer.closeAll();
                }
            });
		}
	});
	
    //获取修改的信息
    function getEntity(){
        var entity = new Object();
        entity.realName=$('#realName').val();
        entity.sex=$('input:radio[name=sex]:checked').val();
        entity.birthTime = $("#date1").val()+" 10:00:00";
        entity.growingPlace = $("#growingPlace").val();
        entity.education = $("#education").val();
        entity.height = $("#height").val();
        entity.idcard = $("#idcard_2").val();
        entity.phone = $("#phone").val();

		entity.nation = $("#nation").val();
		entity.politicalVisage = $("#face").val();
		entity.birthplace = $("#birthplace").val();
		entity.workUnit = $("#workUnit").val();
		entity.maritalStatus = $('#maritalStatus').val();
        entity.unitAttribute=$("#unitAttribute").val();	//单位属性
        entity.postNature=$("#postNature").val();			//岗位性质

        return entity;
    }
	
	// 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var invitationUserVo = new Object();
		invitationUserVo.page=currentPage;
		invitationUserVo.pageSize=10;
		// 认证表，默认查询待审核状态的
		t.idcard=$.trim($("#idcard").val());
		t.phone=$.trim($("#phoneNumber").val());
		invitationUserVo.t=t;
		return JSON.stringify(invitationUserVo);
	}

});
</script>
</body>
</html>