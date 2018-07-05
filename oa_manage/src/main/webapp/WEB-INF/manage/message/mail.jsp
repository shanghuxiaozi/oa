<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>受邀用户</title>
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
		   		<input type="text" id="idcard" autocomplete="off" placeholder="身份证号码" class="layui-input" />
		    </div>
		    <span class="layui-form-label">手机号码：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="phoneNumber" autocomplete="off" placeholder="手机号码" class="layui-input" />
		    </div>
<!-- 			<span class="layui-form-label">用户状态：</span> -->
<!-- 			<div class="layui-input-inline"> -->
<!-- 				<select id="status" lay-verify="" > -->
<!-- 					<option value="0">--全部--</option> -->
<!-- 					<option value="1">注册用户</option> -->
<!-- 					<option value="2">未注册用户</option> -->
<!-- 				</select> -->
<!-- 			</div> -->
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	    </div>
    </blockquote>

	
</form>
<div class="layui-btn-group demoTable">
  <button class="layui-btn" data-type="getCheckData">群发给选中的人员</button>
  <button class="layui-btn" data-type="isAll">群发所有人员</button>
  <button class="layui-btn" data-type="widthDrawAll">撤回群发所有人员</button>
</div>
<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table>

<!-- 设置编辑页面 -->
<div style="display: none" id="detail" lay-filter="test22">
	<!-- <table id="activityDetail" lay-filter="activityDetail"></table> -->
	<form class="layui-form " action="####" style="margin-right: 5%; margin-top: 10px;height: 500px"  id="formperm">
			<div class="layui-form-item" >
			<label class="layui-form-label" id="sendTo">站内信内容:</label>
			<div class="layui-input-block">
				 <textarea id="message" placeholder="站内信内容。" class="layui-textarea"></textarea>
			</div>
		</div>
	</form>
</div>

	<!-- 设置操作按钮 -->
<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-mini" lay-event="edit">发送</a>
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">是否撤回</a>
</script>
	<!--
	<a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>

	-->


<script id="editDemo" type="text/html">
<form class="layui-form ">
<div class="layui-form-item">
    <label class="layui-form-label">是否撤销:</label>
    <div class="layui-input-block">
      <input type="checkbox" name="close" data-id="{{d.id}}" {{d.isNewuserTips=='1'?'checked':''}} id="typeInput" lay-skin="switch" lay-text="是|否" lay-filter="widthDraw" value="0">
    </div>
</form>
</script>
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
		   id:'idTest'
		  ,elem: '#dateTable' 		//指定原始表格元素选择器（推荐id选择器）
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
	 	  ,url: js_path+'/InvitationUser/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
	 		 	{checkbox: true}
			   ,{field: 'realName', title: '真实姓名', width: 180, align:'center'}
			   ,{field: 'sex', title: '性别',templet:'#titleTpl', width: 100,align:'center'}
			 //  ,{field: 'nation', title: '民族', width: 100, align:'center'}
			   ,{field: 'birthTime', title: '出生日期',templet:"#titleTpl2", width: 100, align:'center'}
			   
			 //  ,{field: 'birthplace', title: '籍贯', width: 100, align:'center'}
			  // ,{field: 'growingPlace', title: '出生地', width: 100, align:'center'}
			 //  ,{field: 'politicalVisage', title: '政治面貌', width: 100, align:'center'}
			   ,{field: 'education', title: '学历', width: 100, align:'center'}
			   ,{field: 'maritalStatus', title: '婚姻状态', width: 100, align:'center'}
			 //  ,{field: 'height', title: '身高', width: 100, align:'center'}
			   ,{field: 'idcard', title: '身份证号码', width: 80, align:'center'}
			   ,{field: 'phone', title: '手机号码', width: 80, align:'center'}
		       ,{field: 'workUnit', title: '工作单位', width: 120, align:'center' }
			  // ,{field: 'unitAttribute', title: '单位属性', width: 120, align:'center' }
              // ,{field: 'postNature', title: '岗位性质', width: 120, align:'center' }
			   ,{fixed: 'right', width:160, align:'center',title:'操作',toolbar:'#barDemo'}
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
			sendMail(data,'3');
		}else if(layEvent == 'del'){
			//loading层
			var indexWithdraw = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			queryWithdraw(data.userId,'3',function(){
				layer.close(indexWithdraw);
				layer.open({
	                type: 1
	                ,skin: 'demo-class'
	                ,title: '给'+data.realName+'的站内信内容'
	                ,area: ['800px', '400px']
	                ,offset: '100px' 			//只定义top坐标，水平保持居中
	                ,shade:['0.3','#000']
	                ,maxmin: true
	                ,content:$('#withdrawContainer')
	                ,btn: ['确认','取消']
	                ,yes: function(index,layero){

	    				layer.closeAll();
	                }
	            });
			});
			
		}
	});
	//查询需要撤回的站内信表格
	function queryWithdraw(userId,messageType,func){	
		table.render({
			  elem: '#withdrawTable' 		//指定原始表格元素选择器（推荐id选择器）
			  ,height: 590 		 		//容器高度
			  ,page:true 		 		//开启分页 
			  ,even: true 				//开启隔行背景
			  ,method:'post'
			  ,request: {		 		//分页   设置分页名称
				  pageName: 'page' 		//页码的参数名称，默认：page
			 	 ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
			  }
		,loading:false   //没办法此版本没有回掉
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
		 	  ,where:{'userId':userId,'messageType':messageType}
		 	  ,url: js_path+'/mail/querySystemMessage'
		 	  //设置表头  进行数据渲染
		 	  ,cols:[[
				   {field: 'messageContent', title: '内容', width: 550,align:'center'}
					
				   ,{fixed: 'right', width:250, align:'center',title:'操作',toolbar:'#editDemo'}
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
		 		  if('function' == typeof func){
		 			 func();
		 		  }
			  }
		}); 
	 	}	  		  

	form.on('switch(widthDraw)',function(data){
		var id = ($(this).attr('data-id'));
		layer.msg(id+'您正进行：'+ (this.checked ? '撤回' : '恢复')+'操作', {
		      offset: '6px'
		    });
		
		    layer.tips('温馨提示：请注意撤回后发送给微信端的站内信就无法读取', data.othis);
		 // messageType=3表示点对点站内信
			$.ajax({
				type:"POST",
		        dataType: 'json',
		        url:js_path+'/mail/updateOne',
			    data:{'id':id,'isNewuserTips':(this.checked ? '1' : '0')},		
			    success: function(datas) {
			    	layer.msg('更新成功!', {icon: 6});
			    	//spotAndModuleHandler('10',data.userId);
			    }
			});
		console.log(data.othis);
	});
	
	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#birthdateId'
		,type: 'date'
		//,range: true //或 range: '~' 来自定义分割字符
	});
	 var active = {
			    getCheckData: function(){ //获取选中数据
			      var checkStatus = table.checkStatus('idTest')
			      ,data = checkStatus.data;
			     // layer.alert(JSON.stringify(data));
			    //给详情页面赋值
					$('#sendTo').html("给"+data.length+"人的站内信");
			      var time = (new Date()).Format('yyyy-MM-dd hh:mm:ss');
					form.render();
			        // 打开详情页面
			        layer.open({
			            type: 1
			            ,skin: 'demo-class'
			            ,title: '站内信内容'
			            ,area: ['800px', '220px']
			            ,offset: '100px' 			//只定义top坐标，水平保持居中
			            ,shade:['0.3','#000']
			            ,maxmin: true
			            ,content:$('#detail')
			            ,btn: ['确认','取消']
			            ,yes: function(index,layero){

			            	var content = $('#message').val();
			            	var arr = [];
							for( var i = 0,m=data.length;i<m;i++ ){
								var item = {};
								item.messageContent = content;
								item.messageType = '3';
								item.userId = data[i].userId;
								item.isNewuserTips = '0';
								item.type = '0';
								item.creatTime = time;
								arr.push(item);
							}
							// messageType=3表示点对点站内信
							$.ajax({
								type:"POST",
						        dataType: 'json',
						        url:js_path+'/mail/sendSome',
							    data:{'json':JSON.stringify(arr)},		
							    success: function(datas) {
							    	
							    	for( var i = 0,m=data.length;i<m;i++ ){
							    		
							    		spotAndModuleHandler('10',data[i].userId);
							    	}
							    	if('function' == typeof func){
							    		func();
							    	}else{
							    		layer.msg('发送成功!', {icon: 6});
							    	}
							    },error:function(e){
							    	if('function' == typeof errorFunc){
							    		errorFunc();
							    	}else{
							    		layer.msg('发送失败!', {icon: 6});
							    	}
							    }
							    
							});
							layer.closeAll();
							//queryDynamic();
			                $('#message').val("");
			            }
			        });
			        //日期选择器
			        laydate.render({
			            elem: '#date1'
			            ,type: 'date' //默认，可不填
			        });
			      
			    }
	 			,widthDrawAll:function(){//撤回
	 				table.render({
	 					  elem: '#withdrawTable' 		//指定原始表格元素选择器（推荐id选择器）
	 					  ,height: 590 		 		//容器高度
	 					  ,page:true 		 		//开启分页 
	 					  ,even: true 				//开启隔行背景
	 					  ,method:'post'
	 					  ,request: {		 		//分页   设置分页名称
	 						  pageName: 'page' 		//页码的参数名称，默认：page
	 					 	 ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
	 					  }
	 				,loading:false   //没办法此版本没有回掉
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
	 				 	  ,where:{'userId':'88','messageType':'4'}
	 				 	  ,url: js_path+'/mail/querySystemMessage'
	 				 	  //设置表头  进行数据渲染
	 				 	  ,cols:[[
	 						   {field: 'messageContent', title: '内容', width: 550,align:'center'}
	 							
	 						   ,{fixed: 'right', width:250, align:'center',title:'操作',toolbar:'#editDemo'}
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
	 				 		layer.open({
	 			                type: 1
	 			                ,skin: 'demo-class'
	 			                ,title: '群发的站内信内容'
	 			                ,area: ['800px', '400px']
	 			                ,offset: '100px' 			//只定义top坐标，水平保持居中
	 			                ,shade:['0.3','#000']
	 			                ,maxmin: true
	 			                ,content:$('#withdrawContainer')
	 			                ,btn: ['确认','取消']
	 			                ,yes: function(index,layero){

	 			    				layer.closeAll();
	 			                }
	 			            });
	 					  }
	 				}); 
	 			}
			    ,isAll: function(){ //群发所有人
			    	var item = {};
			    	item.realName="所有人"
					item.userId = '88';//表示统一所有人的Id
			    	sendMail(item,'4');
			    }
			  };
			  
			  $('.demoTable .layui-btn').on('click', function(){
			    var type = $(this).data('type');
			    active[type] ? active[type].call(this) : '';
			  });
	
	function sendMail(data_bak,messageType, func,errorFunc){
		//给详情页面赋值
		$('#sendTo').html("给"+data_bak.realName+"的站内信");

		form.render();
        // 打开详情页面
        layer.open({
            type: 1
            ,skin: 'demo-class'
            ,title: '站内信内容'
            ,area: ['800px', '220px']
            ,offset: '100px' 			//只定义top坐标，水平保持居中
            ,shade:['0.3','#000']
            ,maxmin: true
            ,content:$('#detail')
            ,btn: ['确认','取消']
            ,yes: function(index,layero){

            	var content = $('#message').val();
				// messageType=3表示点对点站内信
				$.ajax({
					type:"POST",
			        dataType: 'json',
			        url:js_path+'/mail/send',
				    data:{'userId':data_bak.userId,'content':content,'messageType':messageType},		
				    success: function(datas) {
				    	
				    	if("3" == messageType)
				    		spotAndModuleHandler('10',data_bak.userId);
				    	else if("4" == messageType)
				    		spotAndModuleHandler('11',data_bak.userId);
				    	if('function' == typeof func){
				    		func();
				    	}else{
				    		layer.msg('发送成功!', {icon: 6});
				    	}
				    },error:function(e){
				    	if('function' == typeof errorFunc){
				    		errorFunc();
				    	}else{
				    		layer.msg('发送失败!', {icon: 6});
				    	}
				    }
				    
				});
				layer.closeAll();
				//queryDynamic();
                $('#message').val("");
            }
        });
        //日期选择器
        laydate.render({
            elem: '#date1'
            ,type: 'date' //默认，可不填
        });
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
		t.userId = "1";//$.trim($("#status").val());'1'表示注册用户
        t.unitAttribute = "请选择";				//单位属性
        t.postNature = "请选择";					//岗位性质
		invitationUserVo.t=t;
		return JSON.stringify(invitationUserVo);
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
    				    data:{'json':JSON.stringify({'id':item.id,'timesOfBrowsing':++timesOfBrowsing,'moduleId':moduleId,'userId':userId})},		
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

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
        "H+" : this.getHours(), //小时     
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
</script>
</body>
<div id="withdrawContainer" style="display:none">
<table id="withdrawTable" lay-filter="withdraw"></table>
</div>
</html>