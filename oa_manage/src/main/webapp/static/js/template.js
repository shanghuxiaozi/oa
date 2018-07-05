var currentPage=1;
function getParametersJson(){
	  //此处仅供测试，实际需要读取标签值
	  var t=new Object();
	  t.templateName=$("#templateName").val();
	  var dynamicQueryVo=new Object();
	  dynamicQueryVo.page=currentPage - 1;
	  dynamicQueryVo.pageSize=10;
	  dynamicQueryVo.t=t;
	  return JSON.stringify(dynamicQueryVo);
}

//入口函数相当于java，main方法
layui.use(['table','form','element','layer'], function(){
        var $ = layui.jquery,//调用内部的jquery
        form = layui.form(),
        element = layui.element(),
        layer = layui.layer,
        table = layui.table;

        function getSelectIds(){
        	var o=$("#dateTable");
        	var str='data-id';
            var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
            var list = [];
            obj.each(function(index,elem){
            	dataList=table.getDataList();
            	for(var i = 0 , m = dataList.length;i<m;i++){
            		if(i ==  $(elem).attr(str)){
            			list.push(dataList[i].id);//此处保存的是主键
            		}
            	}
            });
            return list;
        }
        
        //所有的表格按如下设置
        var options = {
        	id:'dateTable',//表格id
        	order: [[ 1, "desc" ]],   // 排序
        	pagingType: "simple_numbers",// 分页样式 simple,simple_numbers,full,full_numbers
        	url:js_path+'/template/queryDynamic',//查询接口
        	data:{'queryRestraintJson':getParametersJson()},//查询参数,
        	buttons:['table-update','table-delete','table-details'],  //表格里面按钮的class
        	buttonsName:['编辑','删除','详情'],
        	handlers:[
        		//编辑事件
        	    function(item,row){
        	    	editData(item,row);
				},
				//删除事件
        		function(item,row){
					deleteData(item,row);
        		},
				//详情事件
				function(item,row){
					detailData(item,row);
				}
        	]
        	//分页事件
        	,pageHandler:function(page){
        		currentPage=page;
        		queryDynamic();
        	}
        };
        table.init(options);

        
        //删除调用方法
        function editData(item,row){
        	var layerEdit = layer.open({
                type: 2,
                title: '模板修改',
                skin: 'layui-layer-molv',
                area: ['600px', '400px'],
                shade: 0,
                maxmin: true,
                content:js_path+'/template/edit?id='+item.id,
                btn: ['确定', '取消'], //只是为了演示
                yes: function(index,layero){
                	var flag=window["layui-layer-iframe" + index].formSubmit();
               	 	if(flag){
               	 		var dataAdd=window["layui-layer-iframe" + index].formData();
               	 		var loadWin= layer.load(0, {shade:['0.3','#000']}); 
                    	$.ajax({
                    		type: "PUT",
                    		dataType: "json",
                    		url: js_path+'/template/update',
                    		data:dataAdd,
                    		success: function(data) {
                    			if(data.code == 200){
                    				layer.msg('修改成功', {icon: 1});
            						layer.close(loadWin);
            						layer.close(layerEdit);
            						queryDynamic();
                    			}else{
            						layer.close(loadWin);
            						layer.close(layerEdit);
                    				layer.msg('系统异常', {icon: 3});
                    			}
                    		},
                    		error:function(e){
                    			layer.close(loadWin);
                    			layer.close(layerEidt);
                    			layer.msg('系统异常', {icon: 3});
                    		}
                    	});
               	 	}
                }
                ,btn2: function(){
                	layer.closeAll();//删除所有layer弹出窗口
                }
                ,zIndex: layer.zIndex //重点1
                ,success: function(layero){
                   element.init();
				   form.render();
                   layer.setTop(layero); //重点2
                }
            });
        };
      //删除调用方法
        function deleteData(item,row){
        	layer.confirm('确定要删除？', {
                btn: ['确定', '取消'] //按钮
            },
            function() {
            	var loadWin= layer.load(0, {shade:['0.3','#000']}); 
            	$.ajax({
                    type: "POST",
                    dataType: "json",
                    traditional :true, 
                    url: js_path+"/template/delete",
                    data:{'ids':item.id},
                    success: function (obj) {
    	               	 if(obj.code==200){
    	               		 queryDynamic();
    	               		 layer.close(loadWin);
    	               		 layer.msg('删除成功', {icon: 1});
    	               	 }else{
    	               		 layer.msg(obj.msg, {icon: 1});
    	               		 layer.close(loadWin);
    	               	 }
                    },
                    error: function(data) {
                    	layer.close(loadWin);
                    	layer.msg('系统异常', {icon: 3});
                    }
               });  
            });
        };
        //详情调用方法
        function detailData(item,row){
        	var layerEdit = layer.open({
                type: 2,
                title: '模板详情',
                skin: 'layui-layer-molv',
                area: ['600px', '400px'],
                shade: 0,
                maxmin: true,
                content:js_path+'/template/detail?id='+item.id,
                btn: ['关闭'],
                yes: function(index,layero){
                	layer.closeAll();
                }
                ,zIndex: layer.zIndex //重点1
                ,success: function(layero){
                   element.init();
				   form.render();
                   layer.setTop(layero); //重点2
                }
            });
        };    
        
        /*********************************************************************************************/
        
       //添加调用方法
       $('#add').click(function(e){
        	var layerAdd=layer.open({
                 type: 2,
                 skin: 'layui-layer-molv',
                 title: '模板添加',
                 area: ['600px', '400px'],
                 shade:['0.3','#000'],
                 maxmin: true,
                 content:js_path+'/template/add',
                 btn: ['确认', '取消'],
                 yes: function(index,layero){
                	var flag=window["layui-layer-iframe" + index].formSubmit();
               	 	if(flag){
               	 		var dataAdd=window["layui-layer-iframe" + index].formData();
               	 		var loadWin= layer.load(0, {shade:['0.3','#000']}); 
                    	$.ajax({
                    		type: "POST",
                    		dataType: "json",
                    		url: js_path+'/template/save',
                    		data:dataAdd,
                    		success: function(data) {
                    			if(data.code == 200){
                    				layer.close(loadWin);
                    				layer.msg('新增成功', {icon: 1});
            						layer.close(layerAdd);
            						queryDynamic();
                    			}else{
            						layer.close(loadWin);
            						layer.close(layerAdd);
                    				layer.msg('系统异常', {icon: 3});
                    			}
                    		},
                    		error:function(e){
                    			layer.close(loadWin);
                    			layer.close(layerAdd);
                    			layer.msg('网络异常', {icon: 3});
                    		}
                    	});
               	 	}
                	//获取数据  触发ajax
  
                 },
                 btn2: function(){
                   layer.closeAll();
                 },
                 zIndex: layer.zIndex, 
                 success: function(layero){
                	 element.init();
                	 form.render();
                	 layer.setTop(layero);
                 }
               });
        });
       
       
       //修改调用方法
       $('#edit').click(function(e){
    	    var ids = getSelectIds();
	   	    if(ids.length!=1){
	   	        layer.msg('请选择一条数据进行修改！');
	   	        return;
	   	    }
        	var layerEidt=layer.open({
                 type: 2,
                 skin: 'layui-layer-molv',
                 title: '模板修改',
                 area: ['600px', '400px'],
                 shade:['0.3','#000'],
                 maxmin: true,
                 content:js_path+'/template/edit?id='+ids[0],
                 btn: ['确认', '取消'],
                 yes: function(index,layero){
                	var flag=window["layui-layer-iframe" + index].formSubmit();
               	 	if(flag){
               	 		var dataAdd=window["layui-layer-iframe" + index].formData();
               	 		var loadWin= layer.load(0, {shade:['0.3','#000']}); 
                    	$.ajax({
                    		type: "PUT",
                    		dataType: "json",
                    		url: js_path+'/template/update',
                    		data:dataAdd,
                    		success: function(data) {
                    			if(data.code == 200){
                    				layer.msg('修改成功', {icon: 1});
            						layer.close(loadWin);
            						layer.close(layerEidt);
            						queryDynamic();
                    			}else{
            						layer.close(loadWin);
            						layer.close(layerEidt);
                    				layer.msg('系统异常', {icon: 3});
                    			}
                    		},
                    		error:function(e){
                    			layer.close(loadWin);
                    			layer.close(layerEidt);
                    			layer.msg('网络异常', {icon: 3});
                    		}
                    	});
               	 	}
                 },
                 btn2: function(){
                   layer.closeAll();
                 },
                 zIndex: layer.zIndex, 
                 success: function(layero){
                	 element.init();
                	 form.render();
                	 layer.setTop(layero);
                 }
               });
        });
         
       //删除调用方法
       $('#delete').click(function(e){
    	    var ids = getSelectIds();
    	    
	   	    if(ids.length<1){
	   	        layer.msg('请选择数据！');
	   	        return;
	   	    }
            layer.confirm('确定要删除？', {
                btn: ['确定', '取消'] //按钮
            },
            function() {
            	var loadWin= layer.load(0, {shade:['0.3','#000']}); 
            	$.ajax({
                    type: "POST",
                    dataType: "json",
                    traditional :true, 
                    url: js_path+"/template/delete",
                    data:{'ids':ids},
                    success: function (obj) {
    	               	 if(obj.code==200){
    	               		 queryDynamic();
    	               		 layer.close(loadWin);
     	               		 layer.msg('删除成功', {icon: 1});
    	               	 }else{
    	               		layer.close(loadWin);
   	               		    layer.msg(obj.msg, {icon: 1});
    	               	 }
                    },
                    error: function(data) {
                   	 	layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
                    }
               });  
            });
        });
       /*********************************************************************************************/
       //查询按钮事件
		$('#query').click(function(e){
			queryDynamic();
		});

		
		//根据当前约束条件查询并重绘列表数据
		function queryDynamic(){
			//获取查询条件，ajax请求，重新渲染数据
			$.ajax({
				type: "POST",
				url: js_path+'/template/queryDynamic',
				data:{'queryRestraintJson':getParametersJson()},
				success: function(data) {
					if(data.code == 200){
						if( data.data.length > 0 ){
							table.render(data);
							//table.upDatePage(data);
						}else{
							table.render(data);
							//table.upDatePage(data);
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



