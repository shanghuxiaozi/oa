<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
  <title>layui增删查改demo</title>
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
	   		<button type="reset" class="layui-btn layui-btn-primary">重置</button>
	    </div>
    </blockquote>
</form>

<blockquote class="layui-elem-quote">
	<button class="layui-btn" id="add">添加</button>
</blockquote>
 
<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table> 
 
<!-- 设置操作按钮 --> 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="detail">查看</a>
  <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

<!-- 分页组件 --> 
<div id="pageElem"></div> 


<!-- 添加页面 -->
<div style="display: none" id="addActivity" >
<form class="layui-form  layui-form-pane" action="${basePath }/activity/save"
		style="margin-right: 5%; margin-top: 10px; " id="formperm">
		<input type="hidden" name="type" value="1" />
				
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动名称</label>
			<div class="layui-input-block">
				<input type="text" name="activity.title" id="activityTitle" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>	
		<!-- 时间选择器 -->	
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动时间</label>
			<div class="layui-input-inline">
			    <input type="text" class="layui-input" id="dateTime1" placeholder="活动开始时间" name="activity.activityStartTime" />
			</div>
			<div class="layui-input-inline">
			    <input type="text" class="layui-input" id="dateTime2" placeholder="活动结束时间" name="activity.activityEndTime" />
			</div>
		</div>
				
		<div class="layui-form-item" pane>
		    <label class="layui-form-label">报名时间</label>
		    <div class="layui-input-inline">
		          <input type="text" class="layui-input" id="dateTime3" placeholder="报名开始时间" name="activity.applyStartTime" />
			</div>
		    <div class="layui-input-inline">
		          <input type="text" class="layui-input" id="dateTime4" placeholder="报名结束时间" name="activity.applyEndTime" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动人数</label>
			<div class="layui-input-block">
				<input type="text"  lay-verify="required"
					autocomplete="off" class="layui-input"
					  name="activity.applyNumber" id="activityApplyNumber" />
			</div>
		</div>
		
		<div class="layui-form-item">
		    <label class="layui-form-label">活动条件</label>
		    <div class="layui-input-block">
			    <select name="activity.activityTerm" lay-filter="aihao" id="activityActivityTerm">
			        <option value="" selected=""></option>
			        <option value="普通用户">普通用户</option>
			        <option value="注册用户" >注册用户</option>
			        <option value="认证会员">认证会员</option>	
			        <option value="单身会员">单身会员</option>					        
			    </select>
		    </div>
		</div>

		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动地点</label>
			<div class="layui-input-block">
				<input type="text"  lay-verify="required"
					autocomplete="off" class="layui-input"
					name="activity.activityAddress" id="activityAddress" />
			</div>
		</div>
		 
		<div class="layui-form-item" pane>
			<label class="layui-form-label">联系人方式</label>
			<div class="layui-input-block">
				<input type="text" name="activity.activityPhone" lay-verify="required" placeholder="张三：18888888888" 
					autocomplete="off" class="layui-input" id="activityPhone"/>
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">主办单位</label>
			<div class="layui-input-block">
				<input type="text" name="activity.sponsor" lay-verify="required" placeholder="" 
					autocomplete="off" class="layui-input" id="activityPhone"/>
			</div>
		</div>
		  
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">活动内容</label>
		    <textarea class="layui-textarea" id="LAY_editor1" style="display: none;" name="" placeholder="请输入内容">  
		    </textarea> 
		</div> 
	   
	    <input type="hidden"  id="content_1"/>	
	    <input type="hidden"  id="content_img"/>	
			   
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">活动流程</label>
		   	<div class="layui-input-block">
		   		<textarea class="layui-textarea" id="LAY_editor2" name="" placeholder="请输入内容"></textarea> 
			</div>
		</div> 
	   
	   	<input type="hidden"  id="thing"/>	
	   
		<div class="layui-form-item layui-form-text">
		    <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label">注意事项</label>
				    <div class="layui-input-block">
				         <textarea placeholder="请输入内容" class="layui-textarea"  name="activityAttentionMatter" id="activityAttentionMatter" data-type="content"></textarea>
				    </div>
             </div>           
		</div>
		
		<div class="layui-form-item" >
			<div class="layui-upload" style="height: 220px;width: 100px;">
			<button type="button" class="layui-btn" id="uploadImg">活动宣传图</button>	
			<div class="layui-upload-list" >
				<img class="layui-upload-img" id="demo1" style="height: 150px;width: 300px" />
				    <p id="demoText" ></p>					   
			    </div>
			</div>   
		</div> 	  
	  
	  <input type="hidden" id="photo"/>	   	 	   
</form> 
</div>

<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script>
var currentPage=1;
function getQueryParam(){
	console.log("ll11111111ll")
	//此处仅供测试，实际需要读取标签值
	var t = new Object();
	var entityVo = new Object();
	entityVo.page=currentPage-1;
	entityVo.pageSize=4;
//  如有查询条件，请写在此处	
//	t.name=$("#name").val();
	entityVo.t=t;
	return JSON.stringify(entityVo);
}

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
  	layer.msg('Hello World');
  
 	//================数据渲染     方法一Start====================
 	function queryDynamic(){	
	table.render({
		  elem: '#dateTable' 		//指定原始表格元素选择器（推荐id选择器）
		  ,height: 315 		 		//容器高度
		  ,page:false 		 		//开启分页 
		  ,method:'post'
		  ,request: {		 		//分页   设置分页名称
			  pageName: 'page' 		//页码的参数名称，默认：page
		 	  ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		  }
			  	  							//分页参数设置  	
	  	  ,limits:[4]
	  	  ,limit: 4 				//默认采用60	
		  ,response: {
			  statusName: 'code'  	//数据状态的字段名称，默认：code
			  ,statusCode: 200 		//成功的状态码，默认：0
			  ,msgName: 'msg'  		//状态信息的字段名称，默认：msg
			  ,countName: 'total' 	//数据总数的字段名称，默认：count
			  ,dataName: 'data' 	//数据列表的字段名称，默认：data
		  } 	
	 	  ,where:{queryRestraintJson:getQueryParam()}
	 	  ,url: js_path+'/activity/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
		        {field: 'id', title: 'ID', width: 80}
		       ,{field: 'title', title: '活动名称', width: 120}
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
	 		    
	 		  //分页模块加载
	 		  laypage.render({
	 			  elem:'pageElem'
	 			  ,curr:curr    //当前页码 	
	 			  ,count: count
	 			  ,limits: [4]
	 			  ,limit:4
	 			  ,skin: '#1E9FFF' 		//自定义选中色值
	 			  ,skip: true 			//开启跳页
	 			  ,hash: 'fenye' 			//自定义hash值
	 			  ,jump: function(obj, first){
	 			  	  console.log("obj.curr:"+obj.curr);
	 			      console.log("obj.curr:"+obj.count);
	 			      console.log("obj.curr:"+first);
	 			      if(first != true ){
	 			          console.log('当前第'+ obj.curr +'页');
	 			      	  //重新定义查询参数
	 				      var t = new Object();
	 				      var entityVo = new Object();
	 				      entityVo.page=obj.curr-1;
	 				      entityVo.pageSize=4;
	 				      //如有查询条件，请写在此处	
	 				      entityVo.t=t;
	 				      var queryParam = JSON.stringify(entityVo);
	 				      	
		 			      $.ajax({
		 				      type: "POST",
		 				      dataType: "json",
		 				      traditional :true, 
		 				      url: js_path+'/activity/queryDynamic',
		 				      data:{'queryRestraintJson':queryParam},
		 				      success: function (data) {
		 				          var tableData = data.data;
		 				       	  table.render({ //其它参数在此省略
		 				       		  data: tableData
		 				       		  ,page: false //开启分页
		 				       		  ,height:315
		 				       		  ,elem:'#dateTable'
		 				       		  ,cols:[[
			 				       		  	  {field: 'id', title: 'ID', width: 80}
			 				  		         ,{field: 'title', title: '活动名称', width: 120}
			 				  		         ,{fixed: 'right', width:200,height:150, align:'center',title:'操作',toolbar:'#barDemo'}
		 				       			      ]]
		 				       		  ,limit: 4 //默认采用60
		 				       			
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
		    layer.msg('查看操作');
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
  
	//添加按钮事件
	$('#add').click(function(e){
		// 打开页面
		layer.open({
			type: 1
			,skin: 'demo-class'
			,title: '活动添加'
			,area: ['900px', '600px']
			,shade:['0.3','#000']
			,maxmin: true
			,content:$('#addActivity')
			,btn: ['确认', '取消']
			,yes: function(index,layero){
				alert(layedit.getContent(editorFirst));		//获取编辑器的内容
				alert(layedit.getText(editorFirst));		//获得编辑器的纯文本内容 
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
		elem: '#dateTime1'
		,type: 'datetime'
		//,range: true //或 range: '~' 来自定义分割字符
	});
	
	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#dateTime2'
		,type: 'datetime'
		//,range: true //或 range: '~' 来自定义分割字符
	});
  
	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#dateTime3'
		,type: 'date'
		//,range: true //或 range: '~' 来自定义分割字符
	});
	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#dateTime4'
		,type: 'date'
		//,range: true //或 range: '~' 来自定义分割字符
	});
	
		
	// ========================以下方法仅供参考，本demo未曾使用===========	
		
	//监听Tab切换
	element.on('tab(demo)', function(data){
	  	layer.msg('切换了：'+ this.innerHTML);
	  	console.log(data);
	});
	
	//执行一个轮播实例
	carousel.render({
	  	elem: '#test1'
	  	,width: '100%' //设置容器宽度
	  	,height: 200
	  	,arrow: 'none' //不显示箭头
	  	,anim: 'fade' //切换动画方式
	});
	
	
	
	 //执行实例
	var uploadInst = upload.render({
	    elem: '#uploadImg' 			//绑定元素
	    ,url: '/xxxxx/' 			//上传接口
	    ,done: function(res){
	      	//上传完毕回调
	    }
	    ,error: function(){
	      	//请求异常回调
	    }
	    
	});
	
	
	//将日期直接嵌套在指定容器中
	var dateIns = laydate.render({
	  	elem: '#laydateDemo'
	  	,position: 'static'
	  	,calendar: true //是否开启公历重要节日
	  	,mark: { //标记重要日子
	    	'0-9-1': '开学'
	    	,'2017-9-15': ''
	    	,'2017-9-16': ''
	  	} 
	  	,done: function(value, date, endDate){
	    	if(date.year == 2017 && date.month == 9 && date.date == 15){
	      		dateIns.hint('明天不上班');
	    	}
	  	}
	  	,change: function(value, date, endDate){
	    	layer.msg(value)
	  	}
	});
  
	//上传
	upload.render({
	  	elem: '#uploadDemo'
	  	,url: '' //上传接口
	  	,done: function(res){
	    	console.log(res)
	  	}
	});
	
	
});
	  	  
</script>
</body>
</html>