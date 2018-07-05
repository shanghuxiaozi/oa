var currentPage=1;
function getParametersJson(){
	  //此处仅供测试，实际需要读取标签值
	  var t=new Object();
	  var sysRoleVo=new Object();
	  sysRoleVo.page=currentPage - 1;
	  sysRoleVo.pageSize=10;
	  //alert($("#activityId").val());
	  t.activityId =$("#ids").val();
	 // t.title=$("#name").val();
	  sysRoleVo.t=t;
	  return JSON.stringify(sysRoleVo);
}
//入口函数相当于java，main方法
layui.use(['table','form','element','layer'], function(){
        var $ = layui.jquery,//调用内部的jquery
        form = layui.form,
        element = layui.element,
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
        	id:'dateTable',				//表格id
        	order: [[ 1, "desc" ]],   	// 排序
        	pagingType: "simple_numbers",// 分页样式 simple,simple_numbers,full,full_numbers
        	url:js_path+'/activity/ByActivityIdqueryUser',//查询接口
        	data:{'id':$('#ids').val()},//查询参数,
        	buttons:['table-update','table-details'],  //表格里面按钮的class
        	buttonsName:['已到','未到'],
        	handlers:[ 
				//添加积分事件
				function(item,row){
					//debugger;
					/*alert(item);
					alert(row);*/
					addData(item,row);
				}, 
				//减少积分事件
				function(item,row){
					//debugger;
					console.log(item);
					/*alert(item.name +":"+item.type);*/
 				    subtractData(item,row);
				}
        	]
        	//分页事件
        	,pageHandler:function(page){
        		currentPage=page;
        		queryDynamic();
        	}
        };
        table.init(options);       
        //添加活动积分调用方法 改变状态 生成积分详情
        function addData(item,row){
        	layer.confirm('确定签到？', {
                btn: ['确定', '取消'] //按钮       
            },
            function() {
            	var loadWin= layer.load(0, {shade:['0.3','#000']}); 
            	$.ajax({
                    type: "POST",
                    dataType: "json",
                    traditional :true, 
                    url: js_path+"/activity/addPoints",
                    data:{'activityId':item.activityId,'userId':item.userId},
                    success: function (obj) {
    	               	 if(obj.code==200){
    	               		/* queryDynamic();*/
    	               		 window.location.reload();
    	               		 layer.close(loadWin);
    	               		 layer.msg('签到成功', {icon: 1});
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
        
        //减少活动积分调用方法 改变状态 生成积分详情
        function  subtractData(item,row){
        	layer.confirm('确定扣分？', {
                btn: ['确定', '取消'] //按钮     	   
            },
            function() {
            	var loadWin= layer.load(0, {shade:['0.3','#000']}); 
            	$.ajax({
                    type: "POST",
                    dataType: "json",
                    traditional :true, 
                    url: js_path+"/activity/minusPoints",
                    data:{'activityId':item.activityId,'userId':item.userId},
                    success: function (obj) {
    	               	 if(obj.code==200){
    	               	/*	 queryDynamic();*/
    	               		 window.location.reload();
    	               		 layer.close(loadWin);
    	               		 layer.msg('扣分成功', {icon: 1});
    	               		 
    	               		 
    	               		 
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
        
        //批量添加积分 改变状态 生成积分详情
        $('#add').click(function (){
        	var ids = getSelectIds();    
 	   	    if(ids.length<1){
 	   	        layer.msg('请选择数据！');
 	   	        return;
 	   	    }
 	      /*	var o=$("#dateTable");  
 	   	    var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
	 	   	obj.each(function(index,elem){	         
	          	for(var i = 0 , i<=ids.length;i++){
	          		if($(this).next().next().val()!=0){
	          			layer.msg('有不符合操作的数据！');
	          		}
	          	}
	          });*/
 	   	    
             layer.confirm('确定签到？', {
                 btn: ['确定', '取消'] //按钮
             },
             function(){
             	var loadWin= layer.load(0, {shade:['0.3','#000']}); 
             	$.ajax({
                     type: "POST",
                     dataType: "json",
                     traditional :true, 
                     url: js_path+"/activity/batchadd",
                     data:{'ids':ids},
                     success: function (obj) {
     	               	 if(obj.code==200){
     	               		 queryDynamic();
     	               		 layer.close(loadWin);
      	               		 layer.msg('签到成功', {icon: 1});
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
        //批量减少积分
        $('#minus').click(function (){
      	    var ids = getSelectIds();
      	    
      	    /***
      	    for(var i=0;i<ids.length;i++){
      	    	if(ids[i] != 0){
      	    		layer.msg('有不符合操作的数据！');
     	   	        return false;
      	    	}
      	    }
      	    ***/
      	    
	   	    if(ids.length<1){
	   	        layer.msg('请选择数据！');
	   	        return;
	   	    }
            layer.confirm('确定要扣分？', {
                btn: ['确定', '取消'] //按钮
            },
            function() {
            	var loadWin= layer.load(0, {shade:['0.3','#000']}); 
            	$.ajax({
                    type: "POST",
                    dataType: "json",
                    traditional :true, 
                    url: js_path+"/activity/batchminus",
                    data:{'ids':ids},
                    success: function (obj) {
    	               	 if(obj.code==200){
    	               		 queryDynamic();
    	               		 layer.close(loadWin);
     	               		 layer.msg('扣分成功', {icon: 1});
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
        
        //查询按钮事件
		$('#query').click(function(e){
			queryDynamic();
		});		
		//根据当前约束条件查询并重绘列表数据
		function queryDynamic(   ){
			//获取查询条件，ajax请求，重新渲染数据
			$.ajax({
				type: "POST",
				url: js_path+'/activity/ByActivityIdqueryUser',
				data:{'queryRestraintJson':getParametersJson()},
				success: function(data) {
					if(data.code == 200){
						if( data.data.length > 0 ){
							/*if(callBack && typeof callBack == 'function')
								callBack(data.data);*/
							
							table.render(data);
							//table.upDatePage(data);
						}else{
							table.render(data);
							//table.upDatePage(data);
						}
					}else{
						layer.msg('请重新刷新页面', {icon: 3});
					}
				},
				error:function(e){
				}
			});        	
	  }

});