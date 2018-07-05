<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<title>热门头条</title>
<link rel="stylesheet" href="${basePath }/layui-v2.2.45/layui/css/layui.css?t=1506699022911" media="all" />
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
	  #formperm .layui-form-label{
	  	width:90px;
	  }
	  #title{
	  	width:92%;
	  }
	  #layui_editor1{
	  	width:92%;
	  }
	</style>
</head>
<body>
<form class="layui-form" action="" target="nm_iframe">
	<blockquote class="layui-elem-quote">
	   	<div class="layui-form-item">
	        <span class="layui-form-label" style="width:103px;">热门资讯名称：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="name" autocomplete="off" placeholder="热门资讯名称" class="layui-input" />
		    </div>
		    
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	   		<button type="reset" id="resetId" class="layui-btn layui-btn-primary">重置</button>
	   		
	    </div>
    </blockquote>
</form>
<blockquote class="layui-elem-quote">
	<button class="layui-btn" id="add"><i class="layui-icon"></i>添加热门资讯</button>
</blockquote>
<!-- 表格 -->
<div id="dateTable" lay-filter="demo"></div>
<!-- 第一步：编写模版。你可以使用一个script标签存放模板，如：-->
<script id="editDemo" type="text/html">
<form class="layui-form " action="####" style="margin-right: 5%; margin-top: 10px;" id="formperm">
		
	<div class="layui-form-item" >
		<label class="layui-form-label">热门资讯标题:</label>
		<div class="layui-input-block">
			<input type="text" id="title" lay-verify="required" placeholder="标题最长不超过18个字" autocomplete="off" class="layui-input" value="{{d.title}}" maxlength="18"/>
		</div>
	</div>
	
	<!-- 时间选择器 -->	
	<div class="layui-form-item" >
		<label class="layui-form-label">热门资讯时间:</label>
		<div class="layui-input-inline">
		    <input type="text" class="layui-input" id="headTime" placeholder="yyyy-MM-dd HH:mm:ss" value="{{d.headTime}}"/>
		</div>
	</div>
	<div class="layui-form-item">
    <label class="layui-form-label">是否显示:</label>
    <div class="layui-input-block">
      <input type="checkbox" name="close" {{d.type=='1'?'checked':''}} id="typeInput" lay-skin="switch" lay-text="是|否" lay-filter="switchTest" value="0">
    </div>
  </div>
	
	<div class="layui-form-item" >
	    <label class="layui-form-label">热门资讯衔接:</label>
	    <div class="layui-input-block">
	    	<input id="layui_editor1" lay-verify="required" placeholder="输入链接地址" autocomplete="off" class="layui-input" value="{{d.content}}" />
	    </div>
    </div>
		
</form> 
</script>
<script type="text/html" id="switchTpl">
  <input type="checkbox" disabled="" name="sex" value="{{d.type}}" lay-skin="switch" lay-text="是|否" lay-filter="sexDemo" {{ d.type == '1' ? 'checked' : '' }}>
</script>
<script src="${basePath }/layui-v2.2.45/layui/layui.js?t=1506699022911"></script>

<script >
var currentPage=1;
layui.use(['form','layedit', 'jquery','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element','laytpl'], function(){
	var $ = layui.jquery;  			//引用自身的JQuery
	var laydate = layui.laydate 	//日期
  	,laypage = layui.laypage 		//分页
  	,layer = layui.layer 			//弹层
  	,table = layui.table 			//表格
  	,carousel = layui.carousel 		//轮播
 	,upload = layui.upload 			//上传
 	,form = layui.form
 	,layedit = layui.layedit 		//文本编辑器
 	,element = layui.element; 		//元素操作
 	var editorFirst;				//富文本编辑器 全局变量!
 	var laytpl = layui.laytpl; //模板引擎
 	var tableIns = table.render({
		elem: '#dateTable' //指定原始表格元素选择器（推荐id选择器）
			,
		height: 320 //容器高度
			,
		width:1150,
		skin: 'row' //行边框风格
			,
		even: true //开启隔行背景
			,
		id: 'dataCheck',
		url: js_path+'/headlines/queryDynamic',
		method: 'get',
		where: { //传递给后台的参数
			queryRestraintJson:getQueryParam()
		},
		request: {
			pageName: 'page' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		response: {
			statusName: 'code' //数据状态的字段名称，默认：code
				,
			statusCode: 200 //成功的状态码，默认：0
				,
			msgName: 'msg' //状态信息的字段名称，默认：msg
				,
			countName: 'total' //数据总数的字段名称，默认：count
				,
			dataName: 'data' //数据列表的字段名称，默认：data
		},
		page: true,
		limits: [10, 20],
		limit: 10 //默认采用2
			,
		loading: true,
		cols: [
			[
				{type:'numbers'}
				 ,{field:'title', width:180, title: '热门资讯标题'}
				 ,{field:'content', width:330, title: '热门资讯内容'}
				 ,{field:'headTime', width:180, title: '热门资讯发布时间'}
				 ,{field:'type', width:180, title: '是否显示', templet: '#switchTpl'}
				 , {
						fixed: 'right',
						title: '操作',
						width: 250,
						align: 'center',
						toolbar: '#barOption',
						unresize: true,
						rowspan: 2
					} //这里的toolbar值是模板元素的选择器
			]
		],
		done: function(res, curr, count) {
			//如果是异步请求数据方式，res即为你接口返回的信息。
			//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
			console.log(res);

			//得到当前页码
			console.log(curr);

			//得到数据总量
			console.log(count);
		}
	});
 	
 	//按钮查询就是执行数据表格重载
 	function queryDynamic(){
 	      var demoReload = $('#name');
 	      //执行重载
 	      table.reload('dataCheck', {
 	        page: {
 	          curr: 1 //重新从第 1 页开始
 	        }
 	        ,where: {
 	        	queryRestraintJson:getQueryParam()
 	        }
 	      });
 	}
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
	table.on('tool(demo)', function(obj) {
		var data = obj.data;
		if(obj.event === 'detail') {
			var editDemo = document.getElementById('editDemo');
			var getTpl = editDemo.innerHTML;
			laytpl(getTpl).render(data, function(html) {
				//自定页
				layer.open({
					type: 1,
					skin: 'layui-layer-demo', //样式类名
					closeBtn: 1, //不显示关闭按钮0
					area: ['600px', '500px'], //大小
					anim: 2,
					shadeClose: true, //开启遮罩关闭
					content: html,
					cancel: function() {
						
					},btn: [ '取消']
					
					,btn2: function(){
		                layer.closeAll();
		            }
				});
				form.render();
				renderHandler();
			});
			
			
		} else if(obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				
				$.ajax({
			        type:"POST",
			        dataType: 'json',
			        url:js_path+'/headlines/delete',
				    data:{'id':data.id},
				    success: function(data) {
				    	if(data.code == 200){
				    		layer.msg('删除成功', {icon: 6}); 
				    	}else{
				    		layer.msg('删除失败，数据可能已经被删除，请刷新后重试。', {icon: 6}); 
				    	}
				    	queryDynamic();
				    }
				});
				
				
			});
		} else if(obj.event === 'edit') {
			var editDemo = document.getElementById('editDemo');
			var getTpl = editDemo.innerHTML;
			laytpl(getTpl).render(data, function(html) {
				//自定页
				layer.open({
					type: 1,
					skin: 'layui-layer-demo', //样式类名
					closeBtn: 1, //不显示关闭按钮0
					area: ['600px', '500px'], //大小
					anim: 2,
					shadeClose: true, //开启遮罩关闭
					content: html,
					cancel: function() {
						
					},btn: ['确认', '取消']
					,yes: function(index,layero){
						$.ajax({
					        type:"POST",
					        dataType: 'json',
					        url:js_path+'/headlines/update',
						    data:{json:JSON.stringify(getHeadLinesInfo(data.id))},
						    success: function(data) {
						    	if(data.code == 200){
						    		layer.msg('编辑成功!!!', {icon: 6}); 
						    		queryDynamic();
						    	}else{
						    		layer.msg('编辑失败!!!', {icon: 6}); 
						    	}
						    },
						}); 
						layer.closeAll();
					}
					,btn2: function(){
		                layer.closeAll();
		            }
				});
				form.render();
				renderHandler();
			});

		} else if(obj.event === 'setSign') {
			layer.prompt({
				formType: 2,
				title: '修改 ID 为 [' + data.id + '] 的用户签名',
				value: data.content
			}, function(value, index) {
				layer.close(index);

				//这里一般是发送修改的Ajax请求

				//同步更新表格和缓存对应的值
				obj.update({
					content: value
				});
			});
		}
	});
 	
 	
 	$('#add').click(function(){
		var editDemo = document.getElementById('editDemo');
		var getTpl = editDemo.innerHTML;
		laytpl(getTpl).render({content:'',title:'',headTime:''}, function(html) {
			//自定页
			layer.open({
				type: 1,
				skin: 'layui-layer-demo', //样式类名
				closeBtn: 1, //不显示关闭按钮0
				area: ['600px', '500px'], //大小
				anim: 2,
				shadeClose: true, //开启遮罩关闭
				content: html,
				cancel: function() {

				}
				,btn: ['确认', '取消']
				,yes: function(index,layero){
					$.ajax({
				        type:"POST",
				        dataType: 'json',
				        url:js_path+'/headlines/add',
					    data:{'json':JSON.stringify(getHeadLinesInfo())},
					    success: function(data) {
					    	if(data.code == 200){
					    		layer.msg('添加成功!!!', {icon: 6}); 
					    		queryDynamic();
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
			});
			form.render();
			renderHandler();
		});
 	});
 	
 	
	function renderHandler() {
		//日期
		laydate.render({
			elem: '#headTime'
			,type: 'datetime'
		});
		
		// 如富文本编辑器需要上传图片，必须在build方法之前set图片上传的路劲。	
		/*layedit.set({
			uploadImage:{
				url: js_path+'/activityReview/uploadImg'  	//此方法返回格式 {code:0,msg:'',data:{src:'http://xxx/xxx.png',title:'xxxx.png'}}
				,type: 'post'
				,size: 200    			//图片大小 		
			}
		});*/
		
		
		//editorFirst =layedit.build('layui_editor1',{height: 280}); 	//建立编辑器
		
		//图片上传 初始化
	  	upload.render({
		    elem: '#addImg' //绑定元素
		    ,url: js_path+'/headlines/uploadImg' //上传接口
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
	  	//监听指定开关
	    form.on('switch(switchTest)', function(data){
	    	if(this.checked){
	    		 layer.msg('系统将显示默认头条！', {
	    		        offset: '6px'
	    		      });
	    		 $('#typeInput').val('1');
	    	}else{
	    		 layer.msg('默认头条显示关闭！', {
	    		        offset: '6px'
	    		      });
	    		 $('#typeInput').val('0');
	    	}
	     
	    });
	}
 	
	// 获取添加活动信息
	function getHeadLinesInfo(id){
		var t = new Object();
		if(id){
			t.id = id;
		}
		t.title=$('#title').val();
		t.content=$('#layui_editor1').val();//layedit.getContent(editorFirst);
		t.headTime=$('#headTime').val();
		t.headImage="";
		t.type= $('#typeInput').val();
		return t;
	}
 // 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var mfHeadlinesVo = new Object();
		mfHeadlinesVo.page=currentPage;
		mfHeadlinesVo.pageSize=10;
		//如有查询条件，请写在此处	
		t.title=$.trim($("#name").val());
		mfHeadlinesVo.t=t;
		return JSON.stringify(mfHeadlinesVo);
	}
});
</script>
<!-- 表格操作按钮集 -->
<script type="text/html" id="barOption">
	<a class="layui-btn layui-btn-mini layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
	<a class="layui-btn layui-btn-mini layui-btn-xs" lay-event="edit">编辑</a>
	<a class="layui-btn layui-btn-mini layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>


</body>
</html>