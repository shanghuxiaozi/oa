


var currentPage=1;
var currentDetail=new Object();
var systemLogList=new Object();



//入口函数相当于java，main方法
layui.use(['table','form','element','layer','laydate'], function(){
        var $ = layui.jquery,//调用内部的jquery
        form = layui.form(),
        element = layui.element(),
        layer = layui.layer,
        table = layui.table;

        //所有的表格按如下设置
        var options = {
        	id:'dateTable',//表格id
        	order: [[ 1, "desc" ]],   // 排序
        	pagingType: "simple_numbers",// 分页样式 simple,simple_numbers,full,full_numbers
        	url:js_path+'/systemLog/queryDynamic',//查询接口
        	data:{'queryRestraintJson':getCurrentRestraintJson()},//查询参数,
        	buttons:['table-details'],  //表格里面按钮的class
        	buttonsName:['详情'],
        	handlers:[
        	    //详情事件
        	    function(item,row){
					var layerDetial = layer.open({
	                     type: 1 //页面层
	                     ,title: '详情'
	                     ,area: ['600px', '400px']
	                     ,shade:['0.3','#000']
	                     ,maxmin: true
	                     ,content:getDetailPage(item,row)
	                     ,btn: ['关闭'] //只是为了演示
//	                     ,yes: function(){
//	                    	 var circle = layer.load(0, {shade: false}); 
//	                    	 //触发ajax
//	         				 $.ajax({
//	         					type: "post",
//	        					url: js_path+"/systemLog/update",
//	        					data:getUpdateData(),
//	        					dataType: "json",
//	        					success: function(data) {
//		        					if(data.code == 200){
//		        						layer.close(circle);
//		        						layer.close(layerDetial);
//		        						queryDynamic();
//		        						layer.msg('已成功更新', {icon: 1});
//		                			}else{
//		        						layer.close(circle);
//		        						layer.close(layerDetial);
//		                				layer.msg('系统异常', {icon: 3});
//		                			}
//	        					}
//	        				});
//	                     }
	                     ,btn2: function(){
	                       layer.closeAll();
	                     }
	                     ,zIndex: layer.zIndex //重点1
	                     ,success: function(layero){
		                   element.init();
		 				   form.render();
	                       layer.setTop(layero); //重点2
	                     }
	                 });
				},
				//删除事件
        		function(item,row){
        			var layerDelete=layer.confirm('确定要删除？', {
        				btn: ['删除','取消'] //按钮
        			}, function(){
        				var circle = layer.load(0, {shade: false}); 
        				var deleteData=new Object();
        				deleteData.id=item.id;
        				//触发ajax
        				$.ajax({
        					type: "POST",
        					dataType: "json",
        					url: js_path+"/systemLog/delete",
        					data:deleteData,//构造参数  from item
        					success: function(data) {
        						if(data.code == 200){
	        						layer.close(circle);
	        						layer.close(layerDelete);
	        						queryDynamic();
	        						layer.msg('已成功删除', {icon: 1});
	                			}else{
	        						layer.close(circle);
	        						layer.close(layerDelete);
	                				layer.msg('系统异常', {icon: 3});
	                			}
        					}
        				});
        			}, function(){});
        		}
        	]
        	//分页事件
        	,pageHandler:function(page){
        		currentPage=page;
        		queryDynamic();
        	}
        	//批量删除
        	,batchChooseHandler:function(list){
        		////console.log("batchDelete:"+list);
        		var systemLogArrayJson=JSON.stringify(list);
        		var layerBatchDelete=layer.confirm('确定要删除？', {
    				btn: ['删除','取消'] //按钮
    			}, function(){
    				var circle = layer.load(0, {shade: false}); 
    				//触发ajax
    				$.ajax({
                		type: "POST",
                		dataType: "json",
                		url: js_path+'/systemLog/batchDelete',
                		data:{'systemLogArrayJson':systemLogArrayJson},
                		success: function(data) {
                			if(data.code == 200){
        						layer.close(circle);
        						layer.close(layerBatchDelete);
            					layer.msg('批量删除成功', {icon: 1});
        						queryDynamic();
                			}else{
        						layer.close(circle);
        						layer.close(layerBatchDelete);
                				layer.msg('系统异常', {icon: 3});
                			}
                		},
                		error:function(e){
                			
                		}
                	}); 
    			}, function(){});
        	}
        };
        
        


        //新增按钮事件
        $('#addBtn').click(function(e){
        	var layerAdd=layer.open({
                 type: 1 //页面层
                 ,title: '新增模板'
                 ,area: ['600px', '400px']
                 ,shade: 0
                 ,maxmin: true
                 ,content:createAddPage()
                 ,btn: ['确认新增', '取消'] //只是为了演示
                 ,yes: function(){
                	var circle = layer.load(0, {shade: false}); 
                	//获取数据  触发ajax
                	$.ajax({
                		type: "POST",
                		dataType: "json",
                		url: js_path+'/systemLog/add',
                		data:getAddData(),
                		success: function(data) {
                			if(data.code == 200){
        						layer.close(circle);
        						layer.close(layerAdd);
            					layer.msg('新增成功', {icon: 1});
        						queryDynamic();
                			}else{
        						layer.close(circle);
        						layer.close(layerAdd);
                				layer.msg('系统异常', {icon: 3});
                			}
                		},
                		error:function(e){
                		}
                	});  
                 }
                 ,btn2: function(){
                   layer.closeAll();
                 }
                 ,zIndex: layer.zIndex //重点1
                 ,success: function(layero){
	               element.init();
	 			   form.render();
                   layer.setTop(layero); //重点2
                 }
               });
        });
        
        
		//查询按钮事件
		$('#queryBtn').click(function(e){
			//需要将分页插件重置
			currentPage=1;
			queryDynamic();
		});
		
		
		//根据当前约束条件查询并重绘列表数据
		function queryDynamic(){
			//获取查询条件，ajax请求，重新渲染数据
			$.ajax({
				type: "POST",
				url: js_path+'/systemLog/queryDynamic',
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
		
		
		//获取详情页面数据
		function getDetailData(item,row){
			//ajax获取数据
			//无特殊情况无需异步获取
			var detailData=item;
			return detailData;
		}
		
		//获取新增数据
		function getUpdateData(){
			var updateData=currentDetail;
			//进行选择性修改
			updateData.templateName=$("#templateName").val();
			updateData.templateStatus=$("#templateStatus").val();
			return updateData;
		}
		
		//构建详情页面内容
		function getDetailPage(item,row){
			var detailData=getDetailData(item,row);
			currentDetail=detailData;
			//页面内容
			var htmlStr=new String();
			htmlStr=htmlStr+
			"<form class='layui-form layui-form-pane' target='nm_iframe' >"+
		    "<div class='layui-form-item'>"+
		        "<label class='layui-form-label' >业务类型</label>"+
		        "<div class='layui-input-inline'>"+
		            "<input id='businessName' style='width:400px'  type='text' name='title' class='layui-input' value='"+detailData.businessName+"' readonly>"+
		        "</div>"+
		    "</div>"+
		    "<div class='layui-form-item' pane>"+
		    	"<label class='layui-form-label'>操作类型</label>"+
		        "<div class='layui-input-inline'>"+
		            "<input id='operationType' style='width:400px'  type='text' name='title' class='layui-input' value='"+detailData.operationType+"' readonly>"+
		        "</div>"+
		    "</div>"+
		    "<div class='layui-form-item pane'>"+
			    "<label class='layui-form-label'>操作内容</label>"+
			    "<div class='layui-input-inline'>"+
			      "<textarea class='layui-textarea' style='width:400px'  readonly>"+detailData.operationContent+"</textarea>"+
			    "</div>"+
		    "</div>"+
		    "<div class='layui-form-item' pane>"+
		    	"<label class='layui-form-label'>表名称</label>"+
		        "<div class='layui-input-inline'>"+
		            "<input id='tableName' type='text' style='width:400px'  name='title' class='layui-input' value='"+detailData.tableName+"'  readonly>"+
		        "</div>"+
		    "</div>"+	
		    "<div class='layui-form-item' pane>"+
	        "<label class='layui-form-label' >操作人</label>"+
	        "<div class='layui-input-inline'>"+
	            "<input id='operatorName' type='text' style='width:400px'  name='title' class='layui-input' value='"+detailData.operatorName+"' readonly>"+
	        "</div>"+
		    "</div>"+
		    "<div class='layui-form-item' pane>"+
		    	"<label class='layui-form-label'>操作时间</label>"+
		        "<div class='layui-input-inline'>"+
		            "<input id='operationDate' type='text' style='width:400px'  name='title' class='layui-input' value='"+detailData.operationDate+"' readonly>"+
		        "</div>"+
		    "</div>"+			    
			"</form>";
			return htmlStr;
		}

		
		//构建新增页面内容
		function createAddPage(){
			var htmlStr=new String();
			htmlStr=htmlStr+
			"<form class='layui-form' target='nm_iframe' >"+
		    "<div class='layui-form-item'>"+
		        "<label class='layui-form-label'>模板名称</label>"+
		        "<div class='layui-input-inline'>"+
		            "<input id='templateNameAdd' type='text' name='title' class='layui-input' value=''>"+
		        "</div>"+
		    "</div>"+
		    "<div class='layui-form-item'>"+
		    	"<label class='layui-form-label'>模板状态</label>"+
		        "<div class='layui-input-inline'>"+
		            "<input id='templateStatusAdd' type='text' name='title' class='layui-input' value=''>"+
		        "</div>"+
		    "</div>"+
	        "</form>";
			return htmlStr;
		}
		
		//获取新增数据
		function getAddData(){
			var addData=new Object();
			addData.templateName=$("#templateNameAdd").val();
			addData.templateStatus=$("#templateStatusAdd").val();
			return addData;
		}
		
		//获取当前约束条件  包括分页信息，查询条件信息等
		//对象转JSON
		//翻页、点击查询会触发此方法
		function getCurrentRestraintJson(){
		  //此处仅供测试，实际需要读取标签值
		  var systemLog=new Object();
		  systemLog.businessName=$("#businessNameQuery").val();
		  systemLog.operatorName=$("#operatorNameQuery").val();
		  systemLog.operationType=$("#operationTypeQuery").val();
		  var dynamicQueryVo=new Object();
		  dynamicQueryVo.page=currentPage-1;
		  dynamicQueryVo.pageSize=10;
		  dynamicQueryVo.systemLog=systemLog;
		  dynamicQueryVo.operationDateMax=$("#operationDateMaxQuery").val();
		  dynamicQueryVo.operationDateMin=$("#operationDateMinQuery").val();
		  return JSON.stringify(dynamicQueryVo);
		}

		function queryAllTempalteJson(){
			  var systemLog=new Object();
			  var dynamicQueryVo=new Object();
			  dynamicQueryVo.page=0;
			  dynamicQueryVo.pageSize=100;
			  dynamicQueryVo.systemLog=systemLog;
			  return JSON.stringify(dynamicQueryVo);
		}
		
		//异步获取模板字典
		function getTemplateList(){
			$.ajax({
				type: "post",
				url: js_path+"/systemLog/queryDynamic",
				data: {
					'queryRestraintJson':queryAllTempalteJson()
				},
				dataType: "json",
				success: function(data) {
					//console.log("getdata from server:" + JSON.stringify(data));
					systemLogList=data.data;
				}
			});
		}
		
		
		
		//初始化操作
        table.init(options);
        table.setPageSize(10);
        table.batchDelete('#btn-delete-all');
//        getTemplateList();
});



