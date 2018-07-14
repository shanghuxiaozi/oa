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
	/*  .layui-table-body.layui-table-main{height:508.99px;}*/
	  .layui-table-view .layui-table td, .layui-table-view .layui-table th{padding:10px 0;}
	  .layui-input-block .zoo-item .zoo-img{background-size: 100% 100%;}
	</style>
</head>
<body>

<form class="layui-form" action="" target="nm_iframe" id="i_form">
	<blockquote class="layui-elem-quote">
	   	<div class="layui-form-item">
	        <span class="layui-form-label" style="width: 85px;">姓名：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="query_username" autocomplete="off" placeholder="姓名" class="layui-input" />
		    </div>
		    <span class="layui-form-label">银行卡号：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="query_bankCardNumber" autocomplete="off" placeholder="银行卡号" class="layui-input" />
		    </div>
			
		
		</div>
			<div class="layui-form-item">
		<span class="layui-form-label">发粮时间：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="query_payrollTime" autocomplete="off" placeholder="年月" class="layui-input" />
		    </div>
		</div>
<!-- 		<div class="layui-form-item"> -->

<!-- 			<span class="layui-form-label" style="width: 85px;">工作单位：</span> -->
<!-- 			<div class="layui-input-inline"> -->
<!-- 					<input type="text" id="unit" placeholder="工作单位" class="layui-input" /> -->
<!-- 			</div> -->

<!-- 			<span class="layui-form-label">单位属性：</span> -->
<!-- 			<div class="layui-input-inline"> -->
<!-- 				<select id="s1" lay-filter="state" > -->
<!-- 					<option value="请选择">请选择</option> -->
<!-- 				</select> -->
<!-- 			</div> -->
<!-- 			<span class="layui-form-label">岗位性质：</span> -->
<!-- 			<div class="layui-input-inline"> -->
<!-- 				<select id="s2"  > -->
<!-- 					<option value="请选择">请选择</option> -->
<!-- 				</select> -->
<!-- 			</div> -->
<!-- 		</div> -->

	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	    </div>
    </blockquote>

	<blockquote class="layui-elem-quote">
		<button type="button" class="layui-btn layui-btn-warm" id="downLoadTemplateBtn">下载模板</button>
		<button type="button" class="layui-btn layui-btn-normal" id="import">导入数据</button>
		<button type="button" class="layui-btn " id="downLoadBtn">下载员工工资excel表格</button>

	</blockquote>
</form>

<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table>

<!-- 设置编辑页面 -->
<div style="display: none" id="detail" lay-filter="test22">
	<!-- <table id="activityDetail" lay-filter="activityDetail"></table> -->
	<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;height: 500px"  id="formperm">

		<div class="layui-form-item" pane>
			<label class="layui-form-label">姓名:</label>
			<div class="layui-input-block">
				<input type="text" id="username" readyonly autocomplete="off" class="layui-input" />
			</div>
		</div>

		

		<div class="layui-form-item" pane>
			<label class="layui-form-label">发工资时间:</label>
			<div class="layui-input-block">
				<input type="text" id="payrollTime" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">应出勤天数:</label>
			<div class="layui-input-block">
				<input type="text" id="actual" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">缺勤天数:</label>
			<div class="layui-input-block">
				<input type="text" id="absenteeism" autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">工资:</label>
			<div class="layui-input-block">
				<input type="text" id="wages" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">应付工资:</label>
			<div class="layui-input-block">
				<input type="text" id="payWages" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">实际工资:</label>
			<div class="layui-input-block">
				<input type="text" id="realWages" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">第一次发金额:</label>
			<div class="layui-input-block">
				<input type="text" id="firstAmount" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">第二次发金额:</label>
			<div class="layui-input-block">
				<input type="text" id="secondAmount" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">报销:</label>
			<div class="layui-input-block">
				<input type="text" id="reimbursement" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">银行卡号:</label>
			<div class="layui-input-block">
				<input type="text" id="bankCardNumber" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">绩效:</label>
			<div class="layui-input-block">
				<input type="text" id="achievements" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">扣发:</label>
			<div class="layui-input-block">
				<input type="text" id="withholding" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">转正:</label>
			<div class="layui-input-block">
				
				<input type="radio" name="regular" value="0" title="试用期"> 
				<input type="radio" name="regular" value="1" title="已转正">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">扣发原因:</label>
			<div class="layui-input-block">
				<input type="text" id="reason" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">社保:</label>
			<div class="layui-input-block">
				<input type="text" id="socialSecurity" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">公积金:</label>
			<div class="layui-input-block">
				<input type="text" id="accumulationFund" autocomplete="off" class="layui-input" />
			</div>
		</div>
	
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">学历:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="education" autocomplete="off"  class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">身高:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="height" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">身份证号码:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="idcard_2" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">手机号码:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="phone" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<hr> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">民族:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="nation" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">籍贯:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="birthplace" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">政治面貌:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="face" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">婚姻状况:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="maritalStatus" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		 <div class="layui-form-item" pane> -->
<!-- 			<span class="layui-form-label">单位属性：</span> -->
<!-- 			<div class="layui-input-inline"> -->
<!-- 				<select id="s3" lay-filter="state2" > -->
<!-- 				</select> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<span class="layui-form-label">岗位性质：</span> -->
<!-- 			<div class="layui-input-inline"> -->
<!-- 				<select id="s4" > -->
<!-- 				</select> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">工作单位:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="workUnit" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
		
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">入职时间:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="startTime" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">离职时间:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="endTime" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">合同开始时间:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="contractStartTime" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">合同结束时间:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="contractEndTime" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">基本工资:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="basyPay" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">是否离职1是0否:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="isLeave" autocomplete="off" class="layui-input" /> --> 
<!-- 				<input type="radio" name="isLeave" value="0" title="在职"> -->
<!-- 				<input type="radio" name="isLeave" value="1" title="离岗"> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">是否在项目中1是0不是:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="isJob" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="layui-form-item" pane> -->
<!-- 			<label class="layui-form-label">项目名:</label> -->
<!-- 			<div class="layui-input-block"> -->
<!-- 				<input type="text" id="whichOne" autocomplete="off" class="layui-input" /> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<%--<div class="layui-form-item" pane>
			<label class="layui-form-label">单位属性:</label>
			<div class="layui-input-block">
				<input type="text" id="unitAttribute" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">岗位性质:</label>
			<div class="layui-input-block">
				<input type="text" id="postNature" autocomplete="off" class="layui-input" />
			</div>
		</div>--%>

	</form>
</div>

	<!-- 设置操作按钮 -->
<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>
	<!--
	<a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>

	-->

<!-- 下载模板提交表单 -->
<form id="downLoadTemplateForm" action="/pay/downLoadTemplate">
	<input type="hidden" name="" />
</form>

<!-- 下载用户资料表格 -->
<form id="downLoadForm" action="/pay/downloadUserData" method="post">
	<input type="hidden" name="pay" id="pay2" />
	<input type="hidden" name="username" id="username2" />
	<input type="hidden" name="regular" id="regular2"/>
</form>
<script id="regularDemo" type="text/html">

      <input type="checkbox" name=""  {{d.regular=='1'?'checked':''}} id="typeInput" lay-skin="switch" lay-text="是|否" lay-filter="widthDraw" value="0">
   
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
  
 	//日期选择器
    laydate.render({
        elem: '#query_payrollTime'
        ,type: 'month' //默认，可不填
    });
 	//================数据渲染     方法一Start====================
 	function queryDynamic(){	
	table.render({
		  elem: '#dateTable' 		//指定原始表格元素选择器（推荐id选择器）
		 // ,height: 590 		 		//容器高度
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
	 	  ,url: js_path+'/pay/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
				{field: 'username', title: '姓名', width: 180, align:'center'}
			   ,{field: 'attendance', title: '应出勤天数', width: 100,align:'center'}
			   ,{field: 'actual', title: '实际出勤天数', width: 130, align:'center'}
			   ,{field: 'absenteeism', title: '缺勤天数', width: 100, align:'center'}
			   ,{field: 'wages', title: '工资', width: 100, align:'center'}
			   ,{field: 'payWages', title: '应付工资', width: 100, align:'center'}
			   ,{field: 'realWages', title: '实际工资', width: 100, align:'center'}
			   ,{field: 'firstAmount', title: '第一次发金额', width: 130, align:'center'}
			   ,{field: 'secondAmount', title: '第二次发金额', width: 130, align:'center'}
			   ,{field: 'reimbursement', title: '报销', width: 100, align:'center'}
			   ,{field: 'bankCardNumber', title: '银行卡号', width: 130, align:'center'}
			   ,{field: 'achievements', title: '绩效', width: 80, align:'center'}
			   
		       ,{field: 'withholding', title: '扣发', width: 120, align:'center' }
		      
		       ,{field: 'reason', title: '扣发原因', width: 120, align:'center' }
		       ,{field: 'socialSecurity', title: '社保', width: 120, align:'center' }
		       ,{field: 'accumulationFund', title: '公积金', width: 120, align:'center' }
		       ,{field: 'payrollTime', title: '发工资时间', width: 120, align:'center' }
		       ,{field: 'regular', title: '转正', width: 120, align:'center' ,templet:"#regularDemo"} //1转0没转
			   ,{fixed: 'right', width:120, align:'center',title:'操作',toolbar:'#barDemo'}
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

	//初始化下拉框赋值
    $(document).ready(function() {
//         
        //此方法一定要加，相当于重新渲染
        form.render();
       
    });


	//监听工具条
	table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data 		//获得当前行数据
		,layEvent = obj.event; 		//获得 lay-event 对应的值
		if(layEvent === 'edit'){
			//给详情页面赋值
			$('#username').val(data.username);
			
			$('#payrollTime').val(data.payrollTime);

			
			$("#actual").val(data.actual);
			$('#absenteeism').val(data.absenteeism);
			$("#wages").val(data.wages);
			$("#payWages").val(data.payWages);
			$("#realWages").val(data.realWages);
			$("#firstAmount").val(data.firstAmount);
			$("#secondAmount").val(data.secondAmount);
			$("#reimbursement").val(data.reimbursement);
			$("#bankCardNumber").val(data.bankCardNumber);
			$("#achievements").val(data.achievements);
			$("#withholding").val(data.withholding);
			
			$("#reason").val(data.reason);
			$("#socialSecurity").val(data.socialSecurity);
			$("#payrollTime").val(data.payrollTime);

			if(data.regular == 1){
                $("input[name='regular'][value=1]").prop("checked",true);
			}else{
                $("input[name='regular'][value=0]").prop("checked",true);
			}
			//$("#basyPay").val(data.basyPay);
			form.render();
            // 打开详情页面
            layer.open({
                type: 1
                ,skin: 'demo-class'
                ,title: '工资详情'
                ,area: ['800px', '400px']
                ,offset: '100px' 			//只定义top坐标，水平保持居中
                ,shade:['0.3','#000']
                ,maxmin: true
                ,content:$('#detail')
                ,btn: ['确认','取消']
                ,yes: function(index,layero){
					// 单位属性  岗位性质   不能为空
                  
                    layer.closeAll();
                }
            });
            //日期选择器
            laydate.render({
                elem: '#date1'
                ,type: 'date' //默认，可不填
            });
		}else if(layEvent == 'del'){
		    //打开一个询问框
            layer.confirm('确定要删除?删除后数据不可恢复！', {icon: 3, title:'提示'}, function(index){
                $.ajax({
                    type:"POST",
                    dataType: 'json',
                    url:js_path+'/pay/delete',
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
	
	//下载模板
    $("#downLoadTemplateBtn").click(function(){
 	   $('#downLoadTemplateForm').submit();
    });

    //下载用户资料
	$("#downLoadBtn").on("click",function(){
	    $("#pay2").val($.trim($("#pay").val()));
        $("#username2").val($.trim($("#username").val()));
        $("#regular2").val($.trim($("#regular").val()));
      

		$("#downLoadForm").submit();
	});

	//数据导入
    upload.render({
        url: js_path+'/pay/import'
        ,elem: '#import' 		//指定原始元素，默认直接查找class="layui-upload-file"
        ,method: 'post' 		//上传接口的http类型
        ,accept:'file'			//允许上传类型可选值有：images（图片）、file（所有文件）、video（视频）、audio（音频）
        ,before: function(obj){
        	console.log(obj);
        	layer.msg('正在导入,请稍后...', {icon: 6}); 
        },done: function(res, index, upload){  //上传成功之后的回调
        	if(res.code == 200){
        		layer.msg('导入成功!', {icon: 6}); 
        		queryDynamic();
        	}
        }
    });

	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#birthdateId'
		,type: 'date'
		//,range: true //或 range: '~' 来自定义分割字符
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
        entity.unitAttribute=$("#s3").val();	//单位属性
        entity.postNature=$("#s4").val();			//岗位性质

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
		t.username=$.trim($("#query_username").val());
		t.bankCardNumber=$.trim($("#query_bankCardNumber").val());
		invitationUserVo.queryDate = $.trim($("#query_payrollTime").val());
		invitationUserVo.t=t;
		return JSON.stringify(invitationUserVo);
	}

});
</script>
</body>
</html>