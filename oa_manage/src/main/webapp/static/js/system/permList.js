
//获取当前约束条件
//包括分页信息，查询条件信息等
//对象转JSON
//翻页、点击查询会触发此方法
function getCurrentRestraintJson(){
	//此处仅供测试，实际需要读取标签值
    var t=new Object();
    var sysPermissionVo=new Object();
    sysPermissionVo.page=0;
    sysPermissionVo.pageSize=10;
    t.resourceType=$("#resourceType").val();
    sysPermissionVo.t=sysPermission;
    return JSON.stringify(sysPermissionVo);
}
var ztree;	
var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: -1
			},
			key: {
				url:"nourl"
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick: onClick
		}
};

function updateTree(){
	$.ajax({
        type: "POST",
        dataType: "json",
        url: js_path+"/sysPermission/menu",
        data: {},
        success: function (obj) {
			ztree = $.fn.zTree.init($("#menuTree"), setting, obj.data);
			ztree.expandAll(true);
        },
        error: function(data) {
        	layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
        }
   }); 
}

var className = "dark";
function beforeClick(treeId, treeNode, clickFlag) {
	className = (className === "dark" ? "":"dark");
	//showLog("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.name );
	return (treeNode.click != false);
}
function onClick(event, treeId, treeNode, clickFlag){ 		
	$("#nodeId").val(treeNode.id);
	$("#nodeValue").val(treeNode.name);
	//入口函数相当于java，main方法
	layui.use(['table','form','element','layer'], function(){
	        var $ = layui.jquery,//调用内部的jquery
	        element = layui.element,
	        layer = layui.layer,
	        form = layui.form();
	        table = layui.table;
	            var sysPermission=new Object();
	            var templateDynamicQueryVo=new Object();
	            templateDynamicQueryVo.page=0;
	            templateDynamicQueryVo.pageSize=10;
	            $("#name").val("");
	            $("#resourceType").val("");
	            sysPermission.parentId=treeNode.id;
	            templateDynamicQueryVo.sysPermission=sysPermission;
	            var jsondata=JSON.stringify(templateDynamicQueryVo);
	            if(treeNode.id=="0"){
	            	$.ajax({
		        		type: "POST",
		        		dataType: "json",
		        		url:js_path+'/sysPermission/queryDynamic',
		        		data:{'queryRestraintJson':jsondata},
		        		success: function(datas) {
		        			if(datas.code == 200){
		        				if( datas.data.length > 0 ){
		        					table.render(datas);
		        				}else{
		        					layer.msg('无记录', {icon: 3});
		        					table.render(datas);
		        				}
		        			}else{
		        				layer.msg('系统异常', {icon: 3});
		        			}
		        		},
		        		error:function(e){
		        		}
		        	});   
	            }else{
	            $.ajax({
	        		type: "POST",
	        		dataType: "json",
	        		url:js_path+'/sysPermission/validateIsLeaf',
	        		data:{'id':treeNode.id},
	        		success: function(data) {
	        			if(data.code!=200){
	        				$.ajax({
				        		type: "POST",
				        		dataType: "json",
				        		url:js_path+'/sysPermission/queryDynamic',
				        		data:{'queryRestraintJson':jsondata},
				        		success: function(datas) {
				        			if(datas.code == 200){
				        				if( datas.data.length > 0 ){
				        					table.render(datas);
				        				}else{
				        					layer.msg('无记录', {icon: 3});
				        					table.render(datas);
				        				}
				        			}else{
				        				layer.msg('系统异常', {icon: 3});
				        			}
				        		},
				        		error:function(e){
				        		}
				        	});     
	        			}
	        		}
	        			
	        	});  
	         } 
		}); 
}

//入口函数相当于java，main方法
layui.use(['table','form','element','layer'], function(){
	  
        var $ = layui.jquery,//调用内部的jquery
        element = layui.element,
        layer = layui.layer,
        form = layui.form();
        table = layui.table;
        var dataList=table;
        var currentPage= 0;
        //所有的表格按如下设置
        var options = {
        	id:'dateTable',//表格id
        	order: [[ 1, "desc" ]],   // 排序
        	pagingType: "simple_numbers",// 分页样式 simple,simple_numbers,full,full_numbers
        	url:js_path+'/sysPermission/queryDynamic',//查询接口
        	data:{'queryRestraintJson':getCurrentRestraintJson()},//查询参数
        	buttons:[],  //表格里面按钮的class
        	buttonsName:[],
        	handlers:[
        	    
        	]
        	//对应的button的回调函数  页面事件page是当前的页数
        	,pageHandler:function(page){
        		currentPage = page;
        		layer.msg('第 '+ page +' 页');
        		queryDynamic(page-1);
        	}
        	,batchChooseHandler:function(list){
        		layer.confirm('确认要删除吗？', {
          		  btn: ['删除','取消'] //按钮
        		}, function(index){
        			var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
	          		var queryRestraintJson=JSON.stringify(list);
	          		var id="";
        			for(var i=0;i<list.length;i++){
        				if(i==0){
        					id+=list[i].id;
        				}else{
        					id+=","+list[i].id;
        				}
        			}

	          		$.ajax({
		                 type: "POST",
		                 dataType: "json",
		                 url: js_path+"/sysPermission/batchDelete",
		                 data: {'ids':id},
		                 success: function (obj) {
		                	 if(obj.code==200){
		                		 if(currentPage!=0){
		                			 queryDynamic(currentPage-1);
		                		 }else{
		                			 queryDynamic(0);
		                		 }
		                		 updateTree();
		                		 layer.close(circle);
		                		 layer.alert("删除成功",{shade: [0.3,'#000'],offset:"t"});
		                	 }else{
		                		 layer.close(circle);
		                		 layer.alert(obj.msg,{shade: [0.3,'#000'],offset:"t"}); 
		                	 }
		                 },
		                 error: function(data) {
		                	 layer.close(circle);
		                	 layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
		                 }
		            });   
	          	}, function(index){
	          		layer.closeAll();
	          	});
        	},singleChooseEditHandler:function(sing){
        		var nodeId=$("#nodeId").val();
        		if(nodeId==""){
        			layer.alert("请选择菜单",{shade: [0.3,'#000'],offset:"t"});
        			return;
        		}
        		layer.open({
   	             type: 2 //页面层
   	             ,title: '修改权限'
   	             ,area: ['800px', '360px']
   	             ,shade: [0.3,'#000']
	             ,offset:"t"
   	                     ,maxmin: true
   	                     ,content:js_path+"/sysPermission/initinfo?id="+sing.id+"&mark=edit&nodeId="+nodeId+"&nodeValue="+$("#nodeValue").val()
   	                     ,btn: ['确定', '关闭'] //只是为了演示
   	                     ,yes: function(index,layero){
   	                    	 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
   	                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
	                    	 if(flag==true){
	   	                    	 var data=window["layui-layer-iframe" + index].formData();
	   	                    	 if(data){
	   	                    		 $.ajax({
	   		    		                 type: "POST",
	   		    		                 dataType: "json",
	   		    		                 url: js_path+"/sysPermission/update",
	   		    		                 data: data,
	   		    		                 success: function (obj) {
	   		    		                	
	   		    		                    layer.closeAll();
			    		                	if(currentPage==0){
			    		                		queryDynamic(0);
			    		                	}else{
			    		                		queryDynamic(currentPage-1);
			    		                	}
			    		                	layer.close(circle);
	   		    		                	layer.alert("修改成功",{shade: [0.3,'#000'],offset:"t"});
	   		    		                	 
	   		    		                 },
	   		    		                 error: function(data) {
	   		    		                	layer.close(circle);
	   		    		                	layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
	   		    		                 }
	   		    		            }); 
	   	                    	 }
	                    	 }
   	                     }
   	                     ,btn2: function(){
   	                       layer.closeAll();
   	                     }
   	                     ,zIndex: layer.zIndex //重点1
   	                     ,success: function(layero){
   	                       //layer.setTop(layero); //重点2
   	                     }
   	         });
          	}
        };
        table.init(options);
        //查询按钮事件
        $('#queryBtn').click(function(e){
        	queryDynamic(0);
        });
        //删除数据
        table.batchDelete('#delbtn');
        //table.singleEdit('#edit');
        function getIds(o,str){
		    var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
		    var list = [];
		    obj.each(function(index,elem){
		    	dataList=table.getDataList();
		    	for(var i = 0 , m = dataList.length;i<m;i++){
		    		if(i ==  $(elem).attr(str)){
		    			list.push(dataList[i]);
		    		}
		    	}
		    });
		    return list;
		}
        $('#editButton').click(function(e){        	
        	/*var that = this;
	   		var options = that.options;
	   	    var list = getIds($("#dateTable"),'data-id');
	   	    if(list == null || list == ''){
	   	        layer.msg('未选择');
	   	    }else if(list.length!=1){
	   	        layer.msg('请选择一条数据进行修改！');
	   	    }else{
	   	    	var id=list[0].id;*/
         	var nodeId=$("#nodeId").val();
	         	if(nodeId=="0"||nodeId==""){
	         		layer.alert("不能修改权限按钮.请选择根目录！",{shade: [0.3,'#000'],offset:"t"});
	         		return;
	         	}
	   	    	$.ajax({
	   	       		type: "POST",
	   	       		dataType: "json",
	   	       		url:js_path+'/sysPermission/validateIsLeaf',
	   	       		data:{'id':nodeId},
	   	       		success: function(datas) {
	   	       			if(datas.code==200){	
			   	       			layer.open({
			   	   	             type: 2 //页面层
			   	   	             ,title: '修改按钮权限'
			   	   	             ,area: ['800px', '360px']
			   	   	             ,shade: [0.3,'#000']
			   		             ,offset:"t"
			   	   	                     ,maxmin: true
			   	   	                     ,content:js_path+"/sysPermission/initinfo?id="+nodeId+"&mark=edit&nodeId="+$("#nodeId").val()+"&nodeValue="+$("#nodeValue").val()+"&flag=0"
			   	   	                     ,btn: ['确定', '关闭'] //只是为了演示
			   	   	                     ,yes: function(index,layero){
			   	   	                    	 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
			   	   	                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
			   		                    	 if(flag==true){
			   		   	                    	 var data=window["layui-layer-iframe" + index].formData();
			   		   	                    	 if(data){
			   		   	                    		 $.ajax({
			   		   		    		                 type: "POST",
			   		   		    		                 dataType: "json",
			   		   		    		                 url: js_path+"/sysPermission/buttonUpdate",
			   		   		    		                 data: data,
			   		   		    		                 success: function (obj) {
			   		   		    		                	
			   		   		    		                    layer.closeAll();
			   				    		                	if(currentPage==0){
			   				    		                		queryDynamic(0);
			   				    		                	}else{
			   				    		                		queryDynamic(currentPage-1);
			   				    		                	}
			   				    		                	layer.close(circle);
			   		   		    		                	layer.alert("修改成功",{shade: [0.3,'#000'],offset:"t"});
			   		   		    		                	 
			   		   		    		                 },
			   		   		    		                 error: function(data) {
			   		   		    		                	 layer.close(circle);
			   		   		    		                	 layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
			   		   		    		                 }
			   		   		    		            }); 
			   		   	                    	 }
			   		                    	 }
			   	   	                     }
			   	   	                     ,btn2: function(){
			   	   	                       layer.closeAll();
			   	   	                     }
			   	   	                     ,zIndex: layer.zIndex //重点1
			   	   	                     ,success: function(layero){
			   	   	                       //layer.setTop(layero); //重点2
			   	   	                     }
			   	   	         });
	   	       			}else{
	   	       				layer.alert("请选择末级菜单！",{shade: [0.3,'#000'],offset:"t"});
	   	       			}
	   	       		}
	   	    	})
	   	   
        })
        
        $("#editMenu").click(function(e){        	
        	/*var that = this;
	   		var options = that.options;
	   	    var list = getIds($("#dateTable"),'data-id');
	   	    if(list == null || list == ''){
	   	        layer.msg('未选择');
	   	    }else if(list.length!=1){
	   	        layer.msg('请选择一条数据进行修改！');
	   	    }else{*/
        	var nodeId=$("#nodeId").val();
         	if(nodeId=="0"){
         		layer.alert("请选择非根目录！",{shade: [0.3,'#000'],offset:"t"});
         		return;
         	}else if(nodeId==""){
         		layer.alert("请选择菜单！",{shade: [0.3,'#000'],offset:"t"});
	   	    	return;
	   	    }
	   	    		//var id=list[0].id;	
			   	       			layer.open({
			   	   	             type: 2 //页面层
			   	   	             ,title: '修改菜单权限'
			   	   	             ,area: ['800px', '360px']
			   	   	             ,shade: [0.3,'#000']
			   		             ,offset:"t"
			   	   	                     ,maxmin: true
			   	   	                     ,content:js_path+"/sysPermission/initinfo?id="+nodeId+"&mark=edit&nodeId="+$("#nodeId").val()+"&nodeValue="+$("#nodeValue").val()+"&flag=1"
			   	   	                     ,btn: ['确定', '关闭'] //只是为了演示
			   	   	                     ,yes: function(index,layero){		
			   	   	                    	 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
			   	   	                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
			   		                    	 if(flag==true){
			   		   	                    	 var data=window["layui-layer-iframe" + index].formData();
			   		   	                    	 if(data){
			   		   	                    		 $.ajax({
			   		   		    		                 type: "POST",
			   		   		    		                 dataType: "json",
			   		   		    		                 url: js_path+"/sysPermission/update",
			   		   		    		                 data: data,
			   		   		    		                 success: function (obj) {
			   		   		    		                	
			   		   		    		                    layer.closeAll();
			   				    		                	if(currentPage==0){
			   				    		                		queryDynamic(0);
			   				    		                	}else{
			   				    		                		queryDynamic(currentPage-1);
			   				    		                	}
			   				    		                	updateTree();
			   				    		                	layer.close(circle);
			   		   		    		                	layer.alert("修改成功",{shade: [0.3,'#000'],offset:"t"});
			   		   		    		                	 
			   		   		    		                 },
			   		   		    		                 error: function(data) {
			   		   		    		                	 layer.close(circle);
			   		   		    		                	 layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
			   		   		    		                 }
			   		   		    		            }); 
			   		   	                    	 }
			   		                    	 }
			   	   	                     }
			   	   	                     ,btn2: function(){
			   	   	                       layer.closeAll();
			   	   	                     }
			   	   	                     ,zIndex: layer.zIndex //重点1
			   	   	                     ,success: function(layero){
			   	   	                      // layer.setTop(layero); //重点2
			   	   	                     }
	   	    	})
	   	    
        })
       //新增菜单
       $('#addMenu').click(function(e){
    	   var nodeId=$("#nodeId").val();
    	   if(nodeId==""){
    		   layer.alert("请选择菜单",{shade: [0.3,'#000'],offset:"t"});
    		   return;
    	   }else if(nodeId=="0"){
    		   layer.open({
		             type: 2 //页面层
		             ,title: '添加菜单权限'
		             ,area: ['800px', '360px']
		             ,shade: [0.3,'#000']
		             ,offset:"t"
		                     ,maxmin: true
		                     ,content:js_path+"/sysPermission/initinfo?id=&mark=add&nodeId="+$("#nodeId").val()+"&flag=1"
		                     ,btn: ['确定', '关闭'] //只是为了演示
		                     ,yes: function(index,layero){	
		             			 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
		                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
		                    	 if(flag==true){
			                    	 var data=window["layui-layer-iframe" + index].formData();
			                    	 if(data){
			                    		 $.ajax({
				    		                 type: "POST",
				    		                 dataType: "json",
				    		                 url: js_path+"/sysPermission/saveMenu",
				    		                 data: data,
				    		                 success: function (obj) {
		   		    		                    layer.closeAll();
				    		                	if(currentPage==0){
				    		                		queryDynamic(0);
				    		                	}else{
				    		                		queryDynamic(currentPage-1);
				    		                	}
		   		    		                	updateTree();
		   		    		                	layer.close(circle);
		   		    		                	layer.alert("添加成功",{shade: [0.3,'#000'],offset:"t"});
				    		                 },
				    		                 error: function(data) {
				    		                	 layer.close(circle);
				    		                	 layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
				    		                 }
				    		            }); 
			                    	 }
		                    	 }
		                     }
		                     ,btn2: function(){
		                       layer.closeAll();
		                     }
		                     ,zIndex: layer.zIndex //重点1
		                     ,success: function(layero){
		                      // layer.setTop(layero); //重点2
		                     }
		         });
    	   }else{
   
    		   $.ajax({
   	       		type: "POST",
   	       		dataType: "json",
   	       		url:js_path+'/sysPermission/validateIsLeaf',
   	       		data:{'id':nodeId},
   	       		success: function(datas) {
   	       			if(datas.code!=200){
   						layer.open({
   				             type: 2 //页面层
   				             ,title: '添加菜单权限'
   				             ,area: ['800px', '360px']
   				             ,shade: [0.3,'#000']
				             ,offset:"t"
   				                     ,maxmin: true
   				                     ,content:js_path+"/sysPermission/initinfo?id=&mark=add&nodeId="+$("#nodeId").val()+"&flag=1"
   				                     ,btn: ['确定', '关闭'] //只是为了演示
   				                     ,yes: function(index,layero){
   				                    	 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
   				                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
   				                    	 if(flag==true){
   					                    	 var data=window["layui-layer-iframe" + index].formData();
   					                    	 if(data){
   					                    		 $.ajax({
   						    		                 type: "POST",
   						    		                 dataType: "json",
   						    		                 url: js_path+"/sysPermission/saveMenu",
   						    		                 data: data,
   						    		                 success: function (obj) {
   				   		    		                    layer.closeAll();
   						    		                	if(currentPage==0){
   						    		                		queryDynamic(0);
   						    		                	}else{
   						    		                		queryDynamic(currentPage-1);
   						    		                	}
   						    		                	updateTree();
   						    		                	layer.close(circle);
   				   		    		                	layer.alert("添加成功",{shade: [0.3,'#000'],offset:"t"});
   						    		                 },
   						    		                 error: function(data) {
   						    		                	layer.close(circle);
   						    		                	layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
   						    		                 }
   						    		            }); 
   					                    	 }
   				                    	 }
   				                     }
   				                     ,btn2: function(){
   				                       layer.closeAll();
   				                     }
   				                     ,zIndex: layer.zIndex //重点1
   				                     ,success: function(layero){
   				                      // layer.setTop(layero); //重点2
   				                     }
   				         });
   	       			}else{
   	       				layer.alert("不能添加子菜单",{shade: [0.3,'#000'],offset:"t"});
   	       			}
   	       			}
   	       		});
    	   }
        });
       //新增按钮
       $('#addButton').click(function(e){
    	   var nodeId=$("#nodeId").val();
    	   if(nodeId!="0"&&nodeId!=""){
	    	   $.ajax({
	  	       		type: "POST",
	  	       		dataType: "json",
	  	       		url:js_path+'/sysPermission/validateIsLeaf',
	  	       		data:{'id':nodeId},
	  	       		success: function(datas) {
	  	       			if(datas.code==200){
	  	       			 $.ajax({
	  		  	       		type: "POST",
	  		  	       		dataType: "json",
	  		  	       		url:js_path+'/sysPermission/validateButton',
	  		  	       		data:{'id':nodeId},
	  		  	       		success: function(datas) {
	  		  	       			if(datas.code==200){
									layer.open({
							             type: 2 //页面层
							             ,title: '添加按钮权限'
							             ,area: ['800px', '360px']
							             ,shade: [0.3,'#000']
							             ,offset:"t"
							                     ,maxmin: true
							                     ,content:js_path+"/sysPermission/initinfo?id=&mark=add&nodeId="+$("#nodeId").val()+"&flag=0"
							                     ,btn: ['确定', '关闭'] //只是为了演示
							                     ,yes: function(index,layero){
							                    	 
							                    	 //alert($("#username").val());
							             			 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
							                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
							                    	 if(flag==true){
								                    	 var data=window["layui-layer-iframe" + index].formData();
								                    	 if(data){
								                    		 $.ajax({
									    		                 type: "POST",
									    		                 dataType: "json",
									    		                 url: js_path+"/sysPermission/buttonUpdate",
									    		                 data: data,
									    		                 success: function (obj) {
							   		    		                    layer.closeAll();
							   		    		                    layer.close(circle);
							   		    		                	layer.alert("添加成功",{shade: [0.3,'#000'],offset:"t"});
									    		                	 
									    		                 },
									    		                 error: function(data) {
									    		                	 layer.close(circle);
									    		                	 layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
									    		                 }
									    		            }); 
								                    	 }
							                    	 }
							                     }
							                     ,btn2: function(){
							                       layer.closeAll();
							                     }
							                     ,zIndex: layer.zIndex //重点1
							                     ,success: function(layero){
							                       //layer.setTop(layero); //重点2
							                     }
							         });
	  		  	       				}else{
	  		  	       					layer.alert("已存在权限按钮！",{shade: [0.3,'#000'],offset:"t"});
	  		  	       				}
		  		  	       		}
	  	       			 	});
	  	       			}
	  	       			else{
	  	       				layer.alert("不能添加权限按钮.请选择根目录！",{shade: [0.3,'#000'],offset:"t"});
	  	       			}
	  	       		}
	  	       	 });
    	   }else{
    		   layer.alert("不能添加权限按钮.请选择根目录！",{shade: [0.3,'#000'],offset:"t"});
    	   }
       });
       
        //注册新增事件
        function queryDynamic(page){
        	//获取查询条件，ajax请求，重新渲染数据
            var sysPermission=new Object();
            var templateDynamicQueryVo=new Object();
            templateDynamicQueryVo.page=page;
            templateDynamicQueryVo.pageSize=10;
            sysPermission.name=$("#name").val();
            //sysPermission.available=$("#available").val();
            sysPermission.resourceType=$("#resourceType").val();
            sysPermission.parentId=$("#nodeId").val();
            templateDynamicQueryVo.sysPermission=sysPermission;
            var jsondata=JSON.stringify(templateDynamicQueryVo);
        	$.ajax({
        		type: "POST",
        		dataType: "json",
        		url:js_path+'/sysPermission/queryDynamic',
        		data:{'queryRestraintJson':jsondata},
        		success: function(data) {        			
        			if(data.code == 200){
        				if( data.data.length > 0 ){
        					dataList=data.data;
        					table.render(data);
        				}else{
        					if(page>0){
        						queryDynamic(0);
        					}else{
        						layer.msg('无记录', {icon: 3});
        					}
        					table.render(data);
        					dataList=data.data;
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

	