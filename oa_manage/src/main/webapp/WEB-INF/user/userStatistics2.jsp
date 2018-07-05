<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>用户统计</title>
</head>
<body>

<form class="layui-form" action="" target="nm_iframe">
	<%--<blockquote class="layui-elem-quote">
	   	<div class="layui-form-item">

			<span class="layui-form-label">用户类型：</span>
			<div class="layui-input-inline" style="width: 100px;">
				<select id="userType" lay-verify="" >
					<option value="0">--全部--</option>
					<option value="1">注册用户</option>
					<option value="2">单身用户</option>
				</select>
			</div>

		    <label class="layui-form-label">统计时间：</label>
		    <div class="layui-input-inline" style="width: 100px;">
		   		<input type="text" id="startDate" autocomplete="off" class="layui-input" />
		    </div>
		    <div class="layui-form-mid">-</div>
		    <div class="layui-input-inline" style="width: 100px;">
		   		<input type="text" id="endDate" autocomplete="off" class="layui-input" />
		    </div>
		    
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	   		<button type="reset" id="resetId" class="layui-btn layui-btn-primary">重置</button>
	    </div>
    </blockquote>--%>

	<!-- 导航table -->
	<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
		<ul class="layui-tab-title">
			<li class="layui-this">用户年龄性别统计图</li>
			<li class="">用户职业注册信息统计图</li>

		</ul>
		<div class="layui-tab-content" style="height: 100px;">
			<div class="layui-tab-item layui-show">

				<%-- 年龄分布图 --%>
				<div id="echartsAge" style="width: 600px;height:400px;float: left"></div>
				<%-- 男女比例分布图 --%>
				<div id="echartsSex" style="width: 600px;height:400px;float: left;"></div>

			</div>
			<div class="layui-tab-item">

				<!-- 这里放置 echar图   柱状图，以及圆饼图。 -->
				<!-- 职业分布图 -->
				<div id="echartsOccupation" style="width: 1300px;height:400px;"></div>
				<br>
				<br>
				<%-- 已注册未注册分布图 --%>
                <div id="echartsRegister" style="width: 600px;height:400px;"></div>

			</div>
		</div>
	</div>
</form>
 
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
<script src="${basePath }/layui-v2.1.4/layui/echarts.js"></script>
<script src="${basePath }/layui-v2.2.45/layui/layui.js?t=1506699022911"></script>
<script>
var currentPage=1;
layui.use(['layedit', 'jquery','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	var $ = layui.jquery;  			//引用自身的JQuery
	var laydate = layui.laydate 		//日期
  	,laypage = layui.laypage 			//分页
  	,layer = layui.layer 				//弹层
  	,table = layui.table 				//表格
  	,carousel = layui.carousel 		//轮播
 	,upload = layui.upload 			//上传
 	,layedit = layui.layedit 			//文本编辑器
 	,element = layui.element; 			//元素操作
 	var editorFirst;					//富文本编辑器 全局变量!

 	//================数据渲染     方法一Start====================
 	function queryDynamic(){
        $.ajax({
            method:"POST",
            dataType: 'json',
            url:js_path+'/userStatistics/userStatistics',
            data:{},
            success:function(data){
                layer.msg(data.msg, {icon: 6});
				var data = data.data;
				var d = data[0];
				console.log(data);
                //=================用户年龄分布图===========================
                // 基于准备好的dom，初始化echarts实例 年龄分布；
                var ageChart = echarts.init(document.getElementById('echartsAge'));
                newOption = {
                    color: ['#3398DB'],
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    title: {
                        text: '用户年龄分布图'
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data: ['18岁-24岁', '25岁-29岁', '30岁-34岁', '35岁-39岁', '40岁以上'],
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'数量',
                            type:'bar',
                            barWidth: '60%',
                            data: [data[0].age1824,data[0].age2529,data[0].age3034,data[0].age3539,data[0].age4060],
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                ageChart.setOption(newOption);

                //=================用户男女比例分布图===========================
                var sexChart = echarts.init(document.getElementById('echartsSex'));
                option2 = {
                    title : {
                        text: '用户男女比例分布图',
                        x:'center'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['男','女']
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    series : [
                        {
                            name: '数量',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:data[0].male, name:'男'},
                                {value:data[0].female, name:'女'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                sexChart.setOption(option2);

                //=======================用户职业分布=================
                var occupationChart = echarts.init(document.getElementById('echartsOccupation'));
                option3 = {
                    color: ['#3398DB'],
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    title: {
                        text: '用户职业岗位分布图'
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data: ['机关单位','公务员','职员','其他','事业单位','公务员','职员','其他','企业','管理岗','销售岗','技术岗','生产岗','研发岗','综合岗','其他','其他'],
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'数量',
                            type:'bar',
                            barWidth: '51%',
                            data: [d.agencyunit,d.agencyunitServant,d.agencyunitWorkers,d.agencyunitOther,d.institution,d.institutionServant,d.institutionWorkers,d.institutionOther,d.enterprise,d.enterpriseManage,d.enterpriseSales,d.enterpriseTechnology,d.enterpriseProduction,d.enterpriseResearch,d.enterpriseComprehensive,d.enterpriseOther,d.other],
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                occupationChart.setOption(option3);
				//========================已注册未注册分布图==========================
                var registerChart = echarts.init(document.getElementById('echartsRegister'));
                option4 = {
                    title : {
                        text: '用户注册比例分布图',
                        x:'center'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['已注册','未注册']
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    series : [{
                            name: '用户数据',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:data[0].registered, name:'已注册'},
                                {value:data[0].unregistered, name:'未注册'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                registerChart.setOption(option4);
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

		}
	});

	//初始化统计开始时间
    laydate.render({
        elem: '#startDate'
        ,type: 'date'
        //,range: true //或 range: '~' 来自定义分割字符
    });
	//初始化统计结束时间
    laydate.render({
        elem: '#endDate'
        ,type: 'date'
        //,range: true //或 range: '~' 来自定义分割字符
    });

	// 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var sysUserinfoVo = new Object();
		sysUserinfoVo.page=currentPage;
		sysUserinfoVo.pageSize=10;
		//  如有查询条件，请写在此处
		t.name="";
        t.userType=$("#userType").val();
		sysUserinfoVo.t=t;
		return JSON.stringify(sysUserinfoVo);
	}
});
</script>
</body>
</html>