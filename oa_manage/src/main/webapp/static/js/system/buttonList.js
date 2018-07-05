var currentPage=1;
function getParametersJson(){
	//此处仅供测试，实际需要读取标签值
    var t=new Object();
    var sysPermissionVo=new Object();
    sysPermissionVo.page=0;
    sysPermissionVo.pageSize=10;
    t.type=2;
    t.name=$("#name").val();
    t.pid=$("#nodeId").val();
    sysPermissionVo.t=t;
    return JSON.stringify(sysPermissionVo);
}

var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid",
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

var ztree,table;
//入口函数相当于java，main方法
layui.use(['table','form','element','layer'], function(){
        var $ = layui.jquery,//调用内部的jquery
        element = layui.element,
        layer = layui.layer,
        form = layui.form();
        table = layui.table;
        var dataList=table;
        var currentPage= 0;
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
        	url:js_path+'/sysPermission/queryDynamic',//查询接口
        	data:{'queryRestraintJson':getParametersJson()},//查询参数
        	buttons:['table-edit','table-delete'],  //表格里面按钮的class
        	buttonsName:['编辑','删除'],
        	handlers:[
        		//编辑
        	    function(item,row){        	    	
        	    	layer.open({
          	             type: 2 //页面层
          	             ,skin: 'layui-layer-molv'
          	             ,title: '修改用户'
          	             ,area: ['800px', '420px']
        	    		 ,shade: [0.3,'#000']
          	             ,offset:"t"
          	                     ,maxmin: true
          	                     ,content:js_path+"/sysPermission/buttonEdit?id="+item.id
          	                     ,btn: ['确定', '关闭'] //只是为了演示
          	                     ,yes: function(index,layero){
          	                    	 //alert($("#username").val());
          	                    	 var flag=window["layui-layer-iframe" + index].formSubmit();
       	                    	 if(flag==true){
       	   	                    	 var data=window["layui-layer-iframe" + index].formData();
       	   	                    	 debugger
       	   	                    	 if(data){
       	   	                    		 var circle = layer.load("正在加载中...", {shade: [0.3,'#000'],offset:"t"});  
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
    	                    url: js_path + "/sysPermission/batchDelete",
    	                    //发送到后台的url
    	                    data: {
    	                        'ids': item.id
    	                    },
    	                    success: function(obj) {
	   		                	 if(obj.code==200){
			                		 if(currentPage!=0){
			                			 queryDynamic(currentPage-1);
			                		 }else{
			                			 queryDynamic(0);
			                		 }
			                		 layer.alert("删除成功",{shade: [0.3,'#000'],offset:"t"});
			                	 }else{
			                		 layer.alert(obj.msg,{shade: [0.3,'#000'],offset:"t"}); 
			                	 }
	    		        		 layer.closeAll();
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
        		queryDynamic();
        	}
        };
        table.init(options);
        //查询按钮事件
        $('#query').click(function(e){
        	queryDynamic();
        });

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
        
       //新增按钮
       $('#add').click(function(e){
    	   var id=$("#nodeId").val();
    	   var isparent=$("#isparent").val();
    	   if(id==""||isparent==true){
    		   layer.alert("请选择子集目录！！！",{shade: [0.3,'#000'],offset:"t"});
    		   return;
    	   }
    	   layer.open({
	             type: 2 //页面层
  	             ,skin: 'layui-layer-molv'
	             ,title: '添加按钮权限'
	             ,area: ['800px', '500px']
	             ,shade: [0.3,'#000']
	             ,offset:"t"
	                     ,maxmin: true
	                     ,content:js_path+"/sysPermission/buttonAdd?id="+id
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
			    		                 url: js_path+"/sysPermission/buttonSave",
			    		                 data: data,
			    		                 traditional:false,
			    		                 success: function (obj) {
	   		    		                    layer.closeAll();
			    		                	if(currentPage==0){
			    		                		queryDynamic(0);
			    		                	}else{
			    		                		queryDynamic(currentPage-1);
			    		                	}
	   		    		                	//updateTree();
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
                     url: js_path+"/sysPermission/batchDelete",
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

		//根据当前约束条件查询并重绘列表数据
		function queryDynamic(){
			//获取查询条件，ajax请求，重新渲染数据
			$.ajax({
				type: "POST",
				url: js_path+'/sysPermission/queryDynamic',
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




/**********************************树菜单操作********************************/
var className = "dark";
function beforeClick(treeId, treeNode, clickFlag) {
	className = (className === "dark" ? "":"dark");
	return (treeNode.click != false);
}
function onClick(event, treeId, treeNode, clickFlag) {
	var isParent = treeNode.isParent;
	if (isParent == true) {
		$("#nodeId").val("");
		return;
	} else {
		$("#nodeId").val(treeNode.id);
	}
	$("#reset").click();
	$.ajax({
		type : "POST",
		dataType : "json",
		url : js_path + '/sysPermission/queryDynamic',
		data : {
			'queryRestraintJson' : getParametersJson()
		},
		success : function(datas) {
			if (datas.code == 200) {
				if (datas.data.length > 0) {
					table.render(datas);
				} else {
					layer.msg('无记录', {icon : 3});
					table.render(datas);
				}
			} else {
				layer.msg('系统异常', {
					icon : 3
				});
			}
		},
		error : function(e) {
		}
	});
}
	