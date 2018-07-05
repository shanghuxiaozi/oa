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
		#r-result{position: relative;
				  left: 110px;
				  top: -280px;
				  display: inline-block;
				  }
	  #r-result #suggestId{width: 260px;height: 38px;}
				  
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qugbHzIDxNmBtgGjAUy4bU6BwsaKSQzT" ></script>
<body class="body">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>添加活动</legend>
	</fieldset>

	<form class="layui-form  layui-form-pane" action="${basePath }/activity/save"
		style="margin-right: 5%; margin-top: 10px; " id="formperm">
		<input type="hidden" name="type" value="1" />
				
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
		<!-- 信誉分，积分
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动所需信誉分</label>
			<div class="layui-input-block">
				<input type="text" name="title" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动奖励信誉分</label>
			<div class="layui-input-block">
				<input type="text" name="rewardCredit" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动奖励积分</label>
			<div class="layui-input-block">
				<input type="text" name="rewardIntegral" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动所需信誉分</label>
			<div class="layui-input-block">
				<input type="text" name="requiredCredit" lay-verify="required"
					autocomplete="off" class="layui-input"/>
			</div>
		</div>    -->
		
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
		    <label class="layui-form-label"><span style="color: red">*</span>活动内容</label>
			<textarea class="layui-textarea" id="LAY_demo1"  style="display: none"  placeholder="请输入内容" > 
	        </textarea> 
	   </div> 
	   
	   <input type="hidden"  id="content_1" name="activity.content" value=""/>
	   	   
	   <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span style="color: red">*</span>活动流程</label>
			<textarea class="layui-textarea" id="LAY_demo2"  style="display: none"  placeholder="请输入内容">  
	        </textarea> 
	   </div>
	    
	   <input type="hidden"  name="activity.activityProgress" id="thing"/>	
				  
	   <div class="layui-form-item layui-form-text">
		    <div class="layui-form-item layui-form-text">
			        <label class="layui-form-label"><span style="color: red">*</span>注意事项</label>
				    <div class="layui-input-block">
				            <textarea placeholder="请输入内容" class="layui-textarea" lay-verify="required" name="activityAttentionMatter" id="activityAttentionMatter" data-type="content"></textarea>
				    </div>
            </div>           
	   </div>
		
	   <div class="layui-form-item" pane>
			<div class="layui-upload" style="height: 220px;width: 100px;">
				<button type="button" class="layui-btn" id="test10">活动主题图片，建议大小750*370</button>
				<div class="layui-upload-list" >
					    <img class="layui-upload-img" id="demo1" style="height: 150px;width: 300px"/>
					    <p id="demoText" ></p>					   
			    </div>
	        </div>   
	   </div>
	   	  
	   <input type="hidden" name="activityImage" id="photo"/>
	   
	   <div class="layui-form-item" pane>
			<label class="layui-form-label"><span style="color: red">*</span>活动地点</label>
			<div class="layui-input-block">
				<input type="text" readonly="readonly" autocomplete="off" class="layui-input" lay-verify="required" placeholder="请点击，然后下拉在地图中搜索地址" name="activity.activityAddress" id="activityAddress" onclick="a()"/>
			</div>
		</div>
				    
	    <div id="box" style="display: none;">
			<div id="l-map" style="width:100%;"></div>
			<div id="r-result">			
			<input id="suggestId" class="searchbox-content-common" type="text"  autocomplete="off" maxlength="256" placeholder="搜地点" value="" />
			</div> 
			<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px; height: 10px; display:none;"></div>
	   </div>
	   
	   <input type="hidden"  id="addressLongitude"/>
	   <input type="hidden"  id="addressLatitude"/>
	  	
	   <div style="display: none">
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
		        //监听提交        
		        form.on('submit(demo1)', function(data){
		        	console.log(data);
		            layer.alert(JSON.stringify(data.field), {
		                title: '最终的提交信息'
		            });
		            return false;
		          });	 
		       
	    
	         });	         
	              			 
			 function formSubmit(){
			       //自定义验证规则 
			        var start;
					var start2;
			        form.verify({
			        	number:[/^[1|2|3|4|5|6|7|8|9]\d{0,3}$/, '请填写正确人数，只能是数字！'],  		        	
						start:function(value){
							  	start =value;
							  	if(value.length <1){
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
				 				 
				 $('#submitBtn').click();
				 var data = $("#formperm").serializeArray();
			  	 return data;			  	
			 }
			//图片上传
		 	   layui.use('upload', function(){
				  var $ = layui.jquery
				  ,upload = layui.upload;				  
				  //普通图片上传
				  var uploadInst = upload.render({
				    elem: '#test10'
				    ,url: js_path+'/activity/upload'
				    ,accept: 'images' //允许上传的文件类型
				    ,method: 'post'  //可选项。HTTP类型，默认post
				    ,size: 500       //最大允许上传的文件大小
				    ,before: function(obj){
				       //alert(9)
				      //预读本地文件示例，不支持ie8
				      obj.preview(function(index, file, result){
				        $('#demo1').attr('src', result); //图片链接（base64）
				        //alert(file);
				      });				       
				    }
				    ,done: function(res){
				      //如果上传失败
				      if(res.code > 0){
				    	  $('#demoText').html('<span style="color: red;">图片必须小于200k</span>');
				      }
				      //上传成功
				      else{
				    	 /*  alert(res.activityImage) */
				    	  $('#photo').val(res.activityImage); 
				      }
				    }
				    ,error: function(){
				      //演示失败状态，并实现重传
				      var demoText = $('#demoText');
				      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
				      demoText.find('.demo-reload').on('click', function(){
				        uploadInst.upload();
						      });
						    }
						  });						         	
					  });
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
			
			//富文本编辑器
			//活动内容		
			layui.use('layedit', function(){
				  var layedit = layui.layedit
				  ,$ = layui.jquery;
				  //活动图片上传
				  layedit.set({				
					  uploadImage: {
							  url: js_path+'/activity/upload2'
							  ,type: 'post' //默认post
							  ,size: 500    //图片大小 					 	 
						  }
					});			  
				  //构建一个默认的编辑器
				  var index = layedit.build('LAY_demo1', {
								  tool: ['strong','left', 'center', 'right', 'link', 'face','image', 'help']
							});  			  
				  //编辑器外部操作
				  var active = {
				    content: function(){					    					     				    	
				    	 $('#content_1').val(layedit.getContent(index));				    	
				    }	    
				  };
				  
				  $('#activityAttentionMatter').on('blur', function(){
				    var type = $(this).data('type');
				    active[type] ? active[type].call(this) : '';
					  });			  					  			 
				});
		
		   //活动流程
		   layui.use('layedit', function(){
						  var layedit = layui.layedit
						  ,$ = layui.jquery;			  
							//构建一个默认的编辑器
						  var index2 = layedit.build('LAY_demo2', {
										  tool: ['strong','left', 'center', 'right','help']
									});   
						//编辑器外部操作
						  var active = {
						    content: function(){
						    	 $('#thing').val(layedit.getContent(index2));		     
						    }	    
						  };						  
						  $('#activityAttentionMatter').on('blur', function(){
						    var type = $(this).data('type');
						    active[type] ? active[type].call(this) : '';
						  }); 						  
			       });
				
		//地图坐标
		  function a(){
		    	$("#box").show();
		    }		   
			// 百度地图API功能
			function G(id) {
				return document.getElementById(id);
			}		
			var map = new BMap.Map("l-map");
			var point = new BMap.Point(114.066112,22.550117); 
			map.centerAndZoom("深圳",15);                   // 初始化地图,设置城市和地图级别。
		    map.enableScrollWheelZoom(true);
			var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
				{"input" : "suggestId"
				,"location" : map
			});		
			ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
			var str = "";
				var _value = e.fromitem.value;
				var value = "";
				if (e.fromitem.index > -1) {
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;				
				value = "";
				if (e.toitem.index > -1) {
					_value = e.toitem.value;
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
				G("searchResultPanel").innerHTML = str;
			});		
			var myValue;
			ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
		    if(confirm("确定此位置吗？")){		
			var _value = e.item.value;
				myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;		
				setPlace();				
				var geolocation = new BMap.Geolocation();
				geolocation.getCurrentPosition(function(r){
					var mk = new BMap.Marker(r.point);
					map.addOverlay(mk);
					map.panTo(r.point);									
				})
			    $("#activityAddress").val($("#suggestId").val()); 
				//根据位置转换成腾讯经纬度
				var address =$("#suggestId").val();
				$.ajax({
			        type:"get",
				    dataType:'jsonp',
				    data:{
				    	address:address,
				    	key:'32FBZ-NEP3X-OE74R-TC2J4-VTLW5-X2B5Z',
				    	output:'jsonp'
				    },
				    jsonp:"callback",
				    jsonpCallback:"xxx",
				    url:"http://apis.map.qq.com/ws/geocoder/v1",
				    success:function(json){	
				    	$('#addressLongitude').html('<input type="hidden"  id="addressLongitude"  name="activity.addressLongitude" value="'+json.result.location.lng+'"/>');
				    	$('#addressLatitude').html('<input type="hidden"  id="addressLatitude"  name="activity.addressLatitude" value="'+json.result.location.lat+'"/>');
				    },
				    error : function(err){
				    	alert("服务端错误，请刷新浏览器后重试");
				    }
			      });				
				}
			});
			function setPlace(){
				map.clearOverlays();    //清除地图上所有覆盖物
				function myFun(){
					var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
					map.centerAndZoom(pp, 18);
					map.addOverlay(new BMap.Marker(pp));    //添加标注
				}
				var local = new BMap.LocalSearch(map, { //智能搜索
				  onSearchComplete: myFun
				});
				local.search(myValue);
			}

</script>	
</body>
</html>