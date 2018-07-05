
	


//入口函数相当于java，main方法
layui.use(['table','form','element','layer'], function(){
        var $ = layui.jquery,//调用内部的jquery
        element = layui.element,
        layer = layui.layer,
        table = layui.table;
        var template2=new Object();
        template2.templateName="name2";

        var templateDynamicQueryVo=new Object();
        templateDynamicQueryVo.page=0;
        templateDynamicQueryVo.pageSize=10;
        templateDynamicQueryVo.template=template2;
        //所有的表格按如下设置
        var options = {
        	id:'dateTable',//表格id
        	"order": [[ 1, "desc" ]],   // 排序
        	"pagingType": "simple_numbers",         // 分页样式 simple,simple_numbers,full,full_numbers
        	url: 'template/queryDynamic',//查询接口
        	data:{'queryRestraintJson':JSON.stringify(templateDynamicQueryVo)},//查询参数
        	buttons:['table-details',null,'table-delete'],  //表格里面按钮的class
        	buttonsName:['详情','编辑','删除'],
        	handlers:[ function(td,row){
        		//--------------------layer.open表示弹出一个层------------------------------------------------------------------
        		 layer.open({
                     type: 1 //页面层
                     ,title: '订单详情'
                     ,area: ['600px', '400px']
                     ,shade: 0
                     ,maxmin: true
                     ,content: "第" + (row) + "行:" +td.eq(3).html()
                     ,btn: ['确定', '全部关闭'] //只是为了演示
                     ,yes: function(){
                    	 layer.msg('确定', {icon: 2});
                     }
                     ,btn2: function(){
                       layer.closeAll();
                     }
                     
                     ,zIndex: layer.zIndex //重点1
                     ,success: function(layero){
                       layer.setTop(layero); //重点2
                     }
                   });
        		   
        		//--------------------------------------------------------------------------------------
        		} ,null,
        		function(td,row){
        			//询问框
        			layer.confirm('确定要删除？', {
        				btn: ['删除','取消'] //按钮
        			}, function(){
        				var index = layer.load(0, {shade: false}); 
        				//点击删除按钮后单个删除
        				$.ajax({
        					type: "POST",
        					url: Config.IP+"/delete",//发送到后台的url
        					data:{},
        					success: function(data) {
        						layer.close(index);
        						layer.msg('已经成功删除', {icon: 1});
        					}
        				});
        				
        			}, function(){});
        		}
        	] //对应的button的回调函数
        };
        table.init(options);
       
        //查询按钮事件
        $('#queryBtn').click(function(e){
        	//动态查询
        	$.ajax({
        		type: "POST",
        		url: Config.IP + options.url,
        		data:options.data,
        		success: function(data) {
        			if(data.code == 200){
        				if( data.data.length > 0 ){
        					
        					table.render(data);
        						
        					
        					table.initTable();
        				}else{
        					layer.msg('无记录', {icon: 3});
        				}
        			}else{
        				layer.msg('无记录', {icon: 3});
        			}
        		},
        		error:function(e){
        			
        		}
        	});
        });
        
        // 例:获取ids
        $(document).on('click','#btn-delete-all', function(){
            // getIds(table对象,获取input为id的属性)
            var list = getIds($('#dateTable'),'data-id');
            if(list == null || list == ''){
                layer.msg('未选择');
            }else{
                layer.msg(list);
            }
        });

        

    });