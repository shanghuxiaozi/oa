<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>添加活动</title>
<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css"  media="all" />
<link rel="stylesheet" href="${basePath }/static/css/style.css" />
<link rel="icon" href="${basePath }/static/image/code.png" />
<link rel="stylesheet" href="${basePath }/static/ztree/css/metroStyle/metroStyle.css" />
<script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=qugbHzIDxNmBtgGjAUy4bU6BwsaKSQzT"></script>  
</head>
<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";font-size:14px;}
		#l-map{height:300px;width: 80%;float: left;}
		#r-result{width:100%;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qugbHzIDxNmBtgGjAUy4bU6BwsaKSQzT"></script>
<body class="body" onUnload="opener.location.reload()"> 
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>修改活动</legend>
	</fieldset>

	<form class="layui-form  layui-form-pane" action="${basePath }"
		style="margin-right: 5%; margin-top: 10px; " id="formperm">
		
		<input type="hidden" name="type" value="1" />
		
		<input type="hidden" id="id2" name="id2"/>		
		<div class="layui-form-item" pane>
			<label class="layui-form-label"><span style="color: red">*</span>活动名称</label>
			<div class="layui-input-block">
				<input type="text" name="activity.title" id="activityTitle"  lay-verify="required" autocomplete="off" class="layui-input" />
			</div>
		</div>	
		<!-- 时间选择器 -->	
		
		<div class="layui-form-item" pane>
			     <label class="layui-form-label"><span style="color: red">*</span>活动时间</label>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test3" placeholder="活动开始时间" lay-verify="start" name="activity.activityStartTime">
				 </div>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test4" placeholder="活动结束时间" lay-verify="end" name="activity.activityEndTime">
				 </div>
		</div>
				
		<div class="layui-form-item" pane>
			     <label class="layui-form-label"><span style="color: red">*</span>报名时间</label>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test1" placeholder="报名开始时间" lay-verify="start2" name="activity.applyStartTime">
				  </div>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test2" placeholder="报名结束时间" lay-verify="end2" name="activity.applyEndTime">
				 </div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label"><span style="color: red">*</span>活动人数</label>
			<div class="layui-input-block">
				<input type="text"   lay-verify="number" autocomplete="off" class="layui-input" name="activity.applyNumber" id="activityApplyNumber" />
			</div>
		</div>

	     
		<div class="layui-form-item" pane>
			<label class="layui-form-label"><span style="color: red">*</span>联系人方式</label>
			<div class="layui-input-block">
				<input type="text" name="activity.activityPhone" lay-verify="required" placeholder="张三：18888888888" autocomplete="off" class="layui-input" id="activityPhone"/>
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label"><span style="color: red">*</span>主办单位</label>
			<div class="layui-input-block">
				<input type="text" name="activity.sponsor" lay-verify="required" placeholder="宝安工会" autocomplete="off" class="layui-input" id="sponsor"/>
			</div>
		</div>

				  
	   <div class="layui-form-item layui-form-text">
		    <div class="layui-form-item layui-form-text">
			        <label class="layui-form-label"><span style="color: red">*</span>注意事项</label>
				    <div class="layui-input-block">
				            <textarea placeholder="请输入内容" class="layui-textarea" lay-verify="required" name="activityAttentionMatter" id="activityAttentionMatter" data-type="content"></textarea>
				    </div>
            </div>           
	   </div>
		
	  	
	   <div style="display: none;">
	        <button class="layui-btn" lay-submit="" id="submitBtn">立即提交</button>	 	
	   </div>    
    </form>
	<!-- 选择菜单 -->
	<div id="menuLayer" style="padding: 10px; display: none;">
		<ul id="menuTree" class="ztree"></ul>
	</div>
	
<script type="text/javascript" src="${basePath }/layui-v2.1.4/layui/layui.js" charset="utf-8"></script>
<script src="${basePath }/static/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${basePath }/static/js/friendsActivity/activity.js"></script>
<script type="text/javascript">
		var form ;
	    layui.use(['form', 'layedit', 'laydate','layedit'], function(){
	        form = layui.form
	                ,layer = layui.layer
	                ,layedit = layui.layedit
	                ,laydate = layui.laydate;
	                 var index ; 
	              
		       
		        //自定义验证规则 
		        var start;
				var start2;
		        form.verify({
		        	number:[/^[1|2|3|4|5|6|7|8|9]\d{0,3}$/, '请填写正确人数，只能是数字！'], 		        	
					start:function(value){
						  	start =value;
						  	if(value.length <1){
						  		    alert("请填写活动开始时间");
							        return '请填写活动开始时间';
							      }
						 	//当前时间戳
						  	var timestamp = Date.parse(new Date());
								timestamp = timestamp / 1000;				 
                            //开始时间戳
                            var timestamp2 = Date.parse(new Date(value));
				            timestamp2 = timestamp2 / 1000;					             					            
							if (timestamp>=timestamp2) {										
									 return '活动开始时间必须大于当前时间！';
								}
								
						     }	
						   
					,end: function(value){
						  	if(value.length <1){
							        return '请填写活动结束时间';
							      }
						  	//开始时间戳							  							
							var timestamp = Date.parse(new Date(start));
							timestamp = timestamp / 1000;			 
                            //结束时间戳
                            var timestamp2 = Date.parse(new Date(value));
				            timestamp2 = timestamp2 / 1000;
								if (timestamp>=timestamp2) {										
									 return '活动结束时间必须大于活动开始时间！';
								}							  	
						     }	
					,start2:function(value){
						  	start2 =value;
						  	if(value.length <1){
							        return '请填写活动报名开始时间';
							      }
						 	//活动开始时间戳
						  	var timestamp = Date.parse(new Date(start));
							timestamp = timestamp / 1000;			 		 
                            //开始报名时间戳
                            var timestamp2 = Date.parse(new Date(value));
				            timestamp2 = timestamp2 / 1000;					             					            
							if (timestamp<=timestamp2) {										
									 return '活动报名开始时间必须小于活动开始时间！';
								}
								
						     }
					,end2: function(value){
						  	if(value.length <1){
							        return '请填写活动报名结束时间';
							      }
						  	//活动开始时间戳							  							
							var timestamp = Date.parse(new Date(start));
							timestamp = timestamp/1000;
							//报名开始时间戳
							var timestamp2 = Date.parse(new Date(start2));
							timestamp2 = timestamp2/1000;
                            //报名结束时间戳
                            var timestamp3 = Date.parse(new Date(value));
				            timestamp3 = timestamp3/1000;					         
							if(timestamp3>=timestamp){
								return '活动报名结束时间必须小于活动开始时间！';
							}else{
								if(timestamp3<=timestamp2){
								return '活动报名结束时间必须大于活动报名开始时间！';
								}
							}								
						    }							  
						  });
		        
		        //监听提交
                form.on('submit(demo1)', function(data){
		            layer.alert(JSON.stringify(data.field), {
		                title: '最终的提交信息'
		            });
		            return false;		       
                });	
		        
	         });       
			 
			 function formSubmit(){
			//	 $('#submitBtn').click();
				 var data = $("#formperm").serializeArray();
			  	 return data;
			  	
			 }

				//时间选择器
			 	layui.use('laydate', function(){	 		
					  var laydate = layui.laydate;		  
					  //常规用法
					  laydate.render({
					    elem: '#test1'
					    ,type: 'datetime'
					  });			  
					  laydate.render({
						    elem: '#test2'
						    ,type: 'datetime'
						  });			  
					  //时间选择器
					  laydate.render({
					    elem: '#test3'
					    ,type: 'datetime'
					  });
					  
					  laydate.render({
						    elem: '#test4'
						    ,type: 'datetime'
						  });			  
				 });				
												
		function getData(a){	
			 console.log(a.id);			
			 $('#id2').val(a.id);	
			 $('#activityTitle').val(a.title);	
			 $('#test3').val(a.activityStartTime);	
			 $('#test4').val(a.activityEndTime);	
			 $('#test1').val(a.applyStartTime);	
			 $('#test2').val(a.applyEndTime);	
			 $('#activityApplyNumber').val(a.applyNumber);	
			 $('#activityAddress').val(a.activityAddress);	
			 $('#activityPhone').val(a.activityPhone);	
			 $('#sponsor').val(a.sponsor);	
			 $('#activityAttentionMatter').val(a.activityAttentionMatter);	
			 
			 $('#content').html('<div>'+a.content+'<div/>');
			 $('#activityProgress').html('<div>'+a.activityProgress+'<div/>');			
			 $('#activityImage').html('<img src =http://ox0w966bt.bkt.clouddn.com/'+a.activityImage+'>');	
		 }	
			
</script>	
</body>
</html>