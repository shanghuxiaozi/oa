<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>工作单位管理</title>
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
	        <span class="layui-form-label">单位名称：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="name" autocomplete="off" placeholder="名称" class="layui-input" />
		    </div>
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	   		<button type="reset" id="resetId" class="layui-btn layui-btn-primary">重置</button>
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
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>


<!-- 添加显示页面 -->
<div style="display: none" id="addworkunit" >
<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;" id="formperm">
	<!-- 树形菜单文档  -->
	<ul id="demo"></ul>
		
</form> 
</div>

<!-- 添加显示页面 -->
<div style="display: none" id="workunit" >
<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;" id="formperm">
				
	<div class="layui-form-item" >
		<label class="layui-form-label">单位名称:</label>
		<div class="layui-input-block">
			<input type="text" id="unitName" lay-verify="required" autocomplete="off" class="layui-input" />
		</div>
	</div>
	
</form> 
</div>

<script src="${basePath }/layui-v2.1.4/layui/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="${basePath }/layui-v2.1.4/layui/zoomove.min.js" type="text/javascript"></script>   
<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script type="text/javascript" >
var currentPage=1;
layui.use(['layedit', 'jquery','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element','tree'], function(){
	var $ = layui.jquery;  			//引用自身的JQuery
	var laydate = layui.laydate 	//日期
  	,laypage = layui.laypage 		//分页
  	,layer = layui.layer 			//弹层
  	,table = layui.table 			//表格
  	,carousel = layui.carousel 		//轮播
 	,upload = layui.upload 			//上传
 	,layedit = layui.layedit 		//文本编辑器
 	,tree = layui.tree				//树形菜单
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
	 	  ,url: js_path+'/workUnits/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
	 	        {field: 'id', title: 'id编号', width: 150, align:'center'}
	 	       ,{field: 'workUnits', title: '单位名称', width: 320, align:'center'}
		       ,{field: 'creationTime', title: '录入时间', width: 180, align:'center' }
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
	
	//监听工具条
	table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data 		//获得当前行数据
		,layEvent = obj.event; 		//获得 lay-event 对应的值
		if(layEvent === 'del'){
			layer.confirm('确定要删除?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
			        type:"POST",
			        dataType: 'json',
			        url:js_path+'/workUnits/delete',
				    data:{'id':data.id},
				    success: function(data) {
				    	if(data.code == 200){
				    		layer.msg('删除成功!!!', {icon: 6}); 
				    		queryDynamic();
				    	}
				    },
				}); 
				layer.close(index);
			});
		}
	});
	
	// 全局变量   防止重复查询
	var nodes=[];
	//添加按钮事件
	$('#add').click(function(e){
		if(nodes.length == 0){
			$.ajax({
		        type:"POST",
		        dataType: 'json',
		        url:js_path+'/workUnits/getAll',
			    data:{},
			    success: function(data) {
			    	if(data.code == 200){
			    		layer.msg('查询成功!!!', {icon: 6});
			    		//树形菜单
			    		var parents = [];				//父级
			    		var children_first = [];		//子级1
			    		var children_sec = [];			//子级2
			    		var children_three = [];
			    		for(var i=0;i<data.data.length;i++){
			    			var item = data.data[i];
			    			if(item.type == 1){
			    				parents.push(item);
			    			}else if(item.type == 2){
			    				children_first.push(item);
			    			}else if(item.type == 3){
			    				children_sec.push(item);
			    			}else if(item.type == 4){
			    				children_three.push(item);
			    			}
			    		}
			    		// 循环父级
			    		for(var i=0;i<parents.length;i++){
			    			var p = {};
			    			p.name = parents[i].workUnits;
			    			p.uuid = parents[i].id;
			    			p.type = parents[i].type;
			    			p.spread = true;
			    			p.id = 'p_'+i;
			    			p.children = [];
			    			
			    			// 子级循环
			    			for(var j=0;j<children_first.length;j++){
			    				var child = {};
			    				child.pId =children_first[j].pId;
			    				if(child.pId == parents[i].id){
			    					child.name = children_first[j].workUnits;
			    					child.uuid = children_first[j].id;
			    					child.type = children_first[j].type;
				    				child.spread = true;
				    				child.id = 'p_'+i+j;
				    				child.children = [];
				    				
				    				// 再子级循环
				    				for(var z=0;z<children_sec.length;z++){
				    					var sec = {};
				    					if(children_sec[z].pId == children_first[j].id){
				    						sec.name = children_sec[z].workUnits;
				    						sec.uuid = children_sec[z].id;
				    						sec.type = children_sec[z].type;
						    				sec.spread = true;
						    				sec.id = 'p_'+i+j+z;
						    				sec.children = [];
						    				for(var k=0;k<children_three.length;k++){
						    					var three = {};
						    					if(children_three[k].pId == children_sec[z].id){
						    						three.name = children_three[k].workUnits;
							    					three.uuid = children_three[k].id;
							    					three.type = children_three[k].type;
							    					three.spread = true;
								    				three.id = 'p_'+i+j+z+k;
								    				sec.children.push(three);
						    					}
						    				}
						    				child.children.push(sec);
				    					}
				    				}
				    				p.children.push(child);
			    				}
			    			}
			    			nodes.push(p);
			    		}
			    		//树形菜单 	
			    		layui.tree({
			    		  	elem: '#demo' 						//传入元素选择器
			    		  	,click: function(node){
			    		  		//alert("ID:"+node.uuid+"类型:"+node.type); 			//node即为当前点击的节点数据
			    		  		$('#unitName').val("");
			    		  		// 打开页面
			    				layer.open({
			    					type: 1
			    					,skin: 'demo-class'
			    					,title: '添加工作单位'
			    					,offset: '100px' 			//只定义top坐标，水平保持居中
			    					,area: ['400px', '200px']
			    					,shade:['0.3','#000']
			    					,maxmin: true
			    					,content:$('#workunit')
			    					,btn: ['确认', '取消']
			    					,yes: function(index,layero){
			    						//获取单位名称
			    						var unitName = $.trim($('#unitName').val());
			    						$.ajax({
			    					        type:"POST",
			    					        dataType: 'json',
			    					        url:js_path+'/workUnits/add',
			    						    data:{'pId':node.uuid,'type':node.type,'unitName':unitName},
			    						    success: function(data) {
			    						    	if(data.code == 200){
			    						    		nodes == null;
			    						    		layer.msg('添加成功', {icon: 6});
			    						    		layer.close(index);
			    						    	}
			    						    }
			    						});
			    						layer.close(index);
			    					}
			    					,btn2: function(){
			    		                layer.close();
			    		            }
			    					,zIndex: layer.zIndex
			    					,success: function(layero){
			    		           	 	element.init;
			    		           	 	layer.setTop(layero);
			    		            }
			    				});	
			    		  		
			    		    }  
			    		  	,nodes:nodes
			    		});
			    	}
			    },
			}); 
		}
		// 打开页面
		layer.open({
			type: 1
			,skin: 'demo-class'
			,title: '添加工作单位'
			,offset: '100px' 			//只定义top坐标，水平保持居中
			,area: ['800px', '400px']
			,shade:['0.3','#000']
			,maxmin: true
			,content:$('#addworkunit')
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
		
	});
	
	// 重置按钮注册事件
	$('#resetId').click(function(){
		$("#name").val("");		
	});
	
	// 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var workUnitsVo = new Object();
		workUnitsVo.page=currentPage;
		workUnitsVo.pageSize=10;
		// 认证表，默认查询待审核状态的
		t.workUnits=$.trim($("#name").val());
		workUnitsVo.t=t;
		return JSON.stringify(workUnitsVo);
	}
	
});
	  	  
</script>
</body>
</html>