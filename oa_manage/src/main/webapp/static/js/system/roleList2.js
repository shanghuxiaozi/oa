
//获取当前约束条件
//包括分页信息，查询条件信息等
//对象转JSON
//翻页、点击查询会触发此方法
function getCurrentRestraintJson(){
	//此处仅供测试，实际需要读取标签值
    var sysRole=new Object();
    var templateDynamicQueryVo=new Object();
    templateDynamicQueryVo.page=0;
    templateDynamicQueryVo.pageSize=10;
    templateDynamicQueryVo.sysRole=sysRole;
    return JSON.stringify(templateDynamicQueryVo);
}

var scrollWidth= document.body.scrollWidth/2-400;

//入口函数相当于java，main方法
layui.use(['table','form','element','layer'], function(){
        var $ = layui.jquery,//调用内部的jquery
        element = layui.element,
        layer = layui.layer,
        table = layui.table;
        var currentPage= 0;
        //所有的表格按如下设置
        var options = {
        	id:'dateTable',//表格id
        	order: [[ 1, "desc" ]],   // 排序
        	pagingType: "simple_numbers",// 分页样式 simple,simple_numbers,full,full_numbers
        	url:js_path+'/sysRole/queryDynamic',//查询接口
        	data:{'queryRestraintJson':getCurrentRestraintJson()},//查询参数
        	buttons:['table-edit','table-delete'],  //表格里面按钮的class
        	buttonsName:['编辑','删除'],
        	handlers:[
        		//编辑
        	    function(item,row){        	    	
        	    	layer.open({
          	             type: 2 //页面层
          	             ,title: '修改用户'
          	             ,area: ['800px', '420px']
               		 ,shade: [0.3,'#000']
          	             ,offset:"t"
          	                     ,maxmin: true
          	                     ,content:js_path+"/sysRole/initinfo?id="+item.id+"&mark=edit"
          	                     ,btn: ['确定', '关闭'] //只是为了演示
          	                     ,yes: function(index,layero){
          	                    	 //alert($("#username").val());
          	                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
       	                    	 if(flag==true){
       	   	                    	 var data=window["layui-layer-iframe" + index].formData();
       	   	                    	 if(data){
       	   	                    		 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
       	   	                    		 $.ajax({
       	   		    		                 type: "POST",
       	   		    		                 dataType: "json",
       	   		    		                 url: js_path+"/sysRole/update",
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
        	    },
				//删除
        		function(item,row){
        	    	//询问框
    	            layer.confirm('确定要删除？', {
    	                btn: ['删除', '取消'] //按钮
    	            },
    	            function() {
    	                var circle = layer.load(0, {
    	                	shade: ['0.3','#000']
    	                });
    	                //点击删除按钮后单个删除
    	                $.ajax({
    	                    type: "POST",
    	                    url: js_path + "/sysRole/batchDelete",
    	                    //发送到后台的url
    	                    data: {
    	                        'ids': item.id
    	                    },
    	                    success: function(obj) {
    	                    	layer.closeAll();
	   		                	 if(obj.code==200){
	   		                		 layer.close(circle);
	   		                		 layer.alert("删除成功",{shade: [0.3,'#000'],offset:"t"});
	   	    		                	if(currentPage==0){
	   	    		                		queryDynamic(0);
	   	    		                	}else{
	   	    		                		queryDynamic(currentPage-1);
	   	    		                	}
	   		                	 }else{
	   		                		 layer.close(circle);
	   		                		 layer.alert(obj.msg,{shade: [0.3,'#000'],offset:"t"}); 
	   		                	 }
    	                    },
			                 error: function(data) {
			                	 layer.closeAll();
			                	 layer.alert("网络错误",{shade: [0.3,'#000'],offset:"t"});
			                 }
    	                });
    	            });
        		}
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
	          		//var queryRestraintJson=JSON.stringify(list);
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
		                 url: js_path+"/sysRole/batchDelete",
		                 data: {'ids':id},
		                 success: function (obj) {
		                	 layer.closeAll();
		                	 if(obj.code==200){
		                		 layer.close(circle);
		                		 layer.alert("删除成功",{shade: [0.3,'#000'],offset:"t"});
	    		                	if(currentPage==0){
	    		                		queryDynamic(0);
	    		                	}else{
	    		                		queryDynamic(currentPage-1);
	    		                	}
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
        		
        		layer.open({
   	             type: 2 //页面层
   	             ,title: '修改角色'
   	             ,area: ['800px', '420px']
   	             ,shade: [0.3,'#000']
   	             ,offset:['80px',scrollWidth]
   	                     ,maxmin: true
   	                     ,content:js_path+"/sysRole/initinfo?id="+sing.id+"&mark=edit"
   	                     ,btn: ['确定', '关闭'] //只是为了演示
   	                     ,yes: function(index,layero){
   	                    	var flag=window["layui-layer-iframe" + index].formSubmit();
	                    	 if(flag==true){
	   	                    	 var data=window["layui-layer-iframe" + index].formData();
	   	                    	 if(data){
	   	                    		 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
	   	                    		 $.ajax({
	   		    		                 type: "POST",
	   		    		                 dataType: "json",
	   		    		                 url: js_path+"/sysRole/update",
	   		    		                 data: data,
	   		    		                 success: function (obj) {
	   		    		                	layer.closeAll();
	   		    		                	layer.close(circle);
	   		    		                	layer.alert("修改成功",{shade: [0.3,'#000'],offset:"t"});
			    		                	if(currentPage==0){
			    		                		queryDynamic(0);
			    		                	}else{
			    		                		queryDynamic(currentPage-1);
			    		                	}
	   		    		                	 
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
          	}
        };
        table.init(options);
        //查询按钮事件
        $('#queryBtn').click(function(e){
        	queryDynamic(0);
        });
        //删除数据
        table.batchDelete('#delbtn');
        table.singleEdit('#editBtn');
       //新增
      
       
       $('#addBtn').click(function(e){
			layer.open({
	             type: 2 //页面层
	             ,title: '添加角色'
	             ,area: ['800px', '420px']
	             ,shade: [0.3,'#000']
	             ,offset:['80px',scrollWidth]
	                     ,maxmin: true
	                     ,content:js_path+"/sysRole/initinfo?id=&mark=add"
	                     ,btn: ['确定', '关闭'] //只是为了演示
	                     ,yes: function(index,layero){
	                    	 //alert($("#username").val());
	                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
	                    	 if(flag==true){
		                    	 var data=window["layui-layer-iframe" + index].formData();
		                    	 if(data){
		                    		 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
		                    		 $.ajax({
			    		                 type: "POST",
			    		                 dataType: "json",
			    		                 url: js_path+"/sysRole/save",
			    		                 data: data,
			    		                 success: function (obj) {
			    		                	layer.closeAll();
			    		                	layer.close(circle);
			    		                	layer.alert("添加成功",{shade: [0.3,'#000'],offset:"t"});
			    		                	if(currentPage==0){
			    		                		queryDynamic(0);
			    		                	}else{
			    		                		queryDynamic(currentPage-1);
			    		                	}
			    		                	 
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
        });
       
    
        //注册新增事件
        function queryDynamic(page){
        	//获取查询条件，ajax请求，重新渲染数据
        	
            var sysRole=new Object();
            var templateDynamicQueryVo=new Object();
            templateDynamicQueryVo.page=page;
            templateDynamicQueryVo.pageSize=10;
            sysRole.role=$("#role").val();
            sysRole.available=$("#available").val();
            templateDynamicQueryVo.sysRole=sysRole;
            var jsondata=JSON.stringify(templateDynamicQueryVo);
        	$.ajax({
        		type: "POST",
        		dataType: "json",
        		url:js_path+'/sysRole/queryDynamic',
        		data:{'queryRestraintJson':jsondata},
        		success: function(data) {
        			
        			if(data.code == 200){
        				if( data.data.length > 0 ){
        					table.render(data);
        				}else{
        					if(page>0){
        						queryDynamic(0);
        					}else{
        						layer.msg('无记录', {icon: 3});
        					}
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
        
        //删除:获取ids
       /* $(document).on('click','#delbtn', function(){
            // getIds(table对象,获取input为id的属性)
            var list = getIds($('#dateTable'),'data-id');
            if(list == null || list == ''){
                layer.msg('请选择要删除的数据！');
            }else{
            	layer.confirm('确认要删除吗？', {
            		  btn: ['删除','取消'] //按钮
            	}, function(index){
            		$.ajax({
		                 type: "POST",
		                 dataType: "json",
		                 url: js_path+"/sysRole/batchDelete",
		                 data: {'ids':list},
		                 success: function (obj) {
		                	 layer.alert("删除成功");
		                	 fresh();
		                 },
		                 error: function(data) {
		                     alert("网络错误");
		                 }
		            });   
            	}, function(index){
            		layer.closeAll();
            	});
            }
        });*/
        
        function getIds(o,str){
		    var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
		    var list = '';
		    obj.each(function(index,elem){
		        list += $(elem).attr(str)+',';
		    });
		    //
		    list = list.substr(0,(list.length-1));
		    return list;
		}
    });
