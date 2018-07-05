
//获取当前约束条件
//包括分页信息，查询条件信息等
//对象转JSON
//翻页、点击查询会触发此方法
var currentPage= 0;
function getCurrentRestraintJson(){
	//此处仅供测试，实际需要读取标签值
    var dataDict=new Object();
    var dataDictDynamicQueryVo=new Object();
    dataDict.pId=$("#pId").val();
    dataDict.dName=$("#dName").val();
    dataDictDynamicQueryVo.dataDict=dataDict;
    dataDictDynamicQueryVo.page=currentPage;
    dataDictDynamicQueryVo.pageSize=10;
    return JSON.stringify(dataDictDynamicQueryVo);
}



//入口函数相当于java，main方法
layui.use(['table','form','element','layer'], function(){
        var $ = layui.jquery,//调用内部的jquery
        element = layui.element,
        layer = layui.layer,
        table = layui.table;
        //所有的表格按如下设置
        var options = {
        	id:'dateTable',//表格id
        	order: [[ 1, "desc" ]],   // 排序
        	pagingType: "simple_numbers",// 分页样式 simple,simple_numbers,full,full_numbers
        	url:js_path+'/dataDict/queryDynamic',//查询接口
        	data:{'queryRestraintJson':getCurrentRestraintJson()},//查询参数
        	buttons:['table-details','table-edit'],  //表格里面按钮的class
        	buttonsName:['详情','编辑'],
        	handlers:[
        		//详情事件
        	    function(item,row){        	    	
					var layerDetial = layer.open({
	                     type: 2 //页面层
	                     ,title: '系统默认配置信息'
	                     ,offset: '0px'
	                	 ,area: ['900px', '350px']
					     ,shade:['0.3','#000']
	                     ,maxmin: true
	                     ,content:js_path+"/dataDict/dataDictInfo?id="+item.id+"&mark=0"
	                     ,btn: ['关闭'] //只是为了演示
                    	 ,yes: function(index,layero){
                    		 layer.closeAll();
                    	 }
	                 });
        	    },
				//编辑事件
        		function(item,row){
        	    	layer.open({
	                     type: 2 //页面层
	                     ,title: '系统默认配置信息'
	                     ,offset: '0px'
	                	 ,area: ['900px', '350px']
					     ,shade:['0.3','#000']
	                     ,maxmin: true
	                     ,content:js_path+"/dataDict/dataDictInfo?id="+item.id+"&mark=1"
	                     ,btn: ['确定', '关闭'] //只是为了演示
	                   	 ,yes: function(index,layero){
	                   		var flag=window["layui-layer-iframe" + index].formSubmit();
	                    	if(flag==true){
	                    		 var data=window["layui-layer-iframe" + index].formData();
	   	                    	 if(data){
	   	                    		var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t",zIndex:-1});  
	   	                    		$.ajax({
	   		    		                 type: "POST",
	   		    		                 dataType: "json",
	   		    		                 url: js_path+"/dataDict/update",
	   		    		                 data: data,
	   		    		                 success: function (obj) {
	   		    		                	layer.closeAll();
			    		                	if(currentPage==0){
			    		                		queryDynamic(0);
			    		                	}else{
			    		                		queryDynamic(currentPage-1);
			    		                	}
	   		    		                	layer.alert("修改成功");
	   		    		                	layer.closeAll();
	   		    		                	 
	   		    		                 },
	   		    		                 error: function(data) {
	   		    		                	 layer.alert("网络错误");
	   		    		                     layer.closeAll();
	   		    		                 }
	   		    		            }); 
	   	                    	 }
	                    	}
	                   	 },btn2: function(){
		                         layer.closeAll();
		                     }
		                 });
        		}
        	]
        	//对应的button的回调函数  页面事件page是当前的页数
        	,pageHandler:function(page){
        		currentPage = parseInt(page)-1;  
        		layer.msg('第 '+ page +' 页');
        		queryDynamic(currentPage);
        	}
        };
        table.init(options);
        //查询按钮事件
        
        function getDetailData(item,row){
			//ajax获取数据
			//无特殊情况无需异步获取
			var detailData=item;
			return detailData;
		}
        $('#queryBtn').click(function(e){
        	queryDynamic();
        });
       
        $(document).on('click','#clearForm', function(){
        	$('#queryForm').reset();
        });
        
    	function queryDynamic(currentPage){
    		//获取查询条件，ajax请求，重新渲染数据
    		$.ajax({
    			type: "POST",
    			url:js_path+'/dataDict/queryDynamic',//查询接口
    			data:{'queryRestraintJson':getCurrentRestraintJson()},
    			success: function(data) {
    				if(data.code == 200){
    					if( data.data.length > 0 ){
    						table.render(data);
    					}else{
    						layer.msg('无记录', {icon: 3});
    						table.render(data);
    					}
    				}else{
    					layer.msg('系统异常', {icon: 3});
    				}
    			},
    			error:function(e){
    			}
    		});        	
    	}
    });


