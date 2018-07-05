<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>视频管理</title>
<link rel="stylesheet"
	href="${basePath }/static/frame/layui/css/layui.css" />
<link rel="stylesheet" href="${basePath }/static/css/style.css" />
<link rel="icon" href="${basePath }/static/image/code.png" />
</head>
<body class="body">
	<!-- 加上标识form表单不会提交 -->
	<iframe id="id_iframe" name="nm_iframe" style="display: none;"></iframe>


	<div class="my-btn-box" style="padding-left:30px;">

		<span class="fl"> 
			<button data-method="addHandler" class="layui-btn" id="add">新增</button>
		</span>
	</div>

	<fieldset class="layui-elem-field layui-field-title">
		<legend>
			<span class="layui-breadcrumb"></span>
		</legend>
	</fieldset>

	<div class=layui-form>
		<table id="dateTable" class="layui-table">
			<thead>
				<tr>
					<th name="checked"><input type=checkbox lay-filter="allChoose"
						class="my-checkbox"><div
								class="layui-unselect layui-form-checkbox layui-form-checked"
								lay-skin>
								<i class="layui-icon"></i>
							</div></th>
							<th name=name>视频名字</th>
				            <th name="address"  >视频地址</th>
				            <th name="messages">视频描述</th>
				            <th name="createDate">创建时间</th>
				            <th name="updateDate">更新时间</th>
            <!-- operButton非接口字段，只是表示此栏是存放按钮 -->
            <th name="operButton"
                buttons="table-details,table-edit,table-delete">操作
            </th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>

	<!-- 分页组件 -->
	<span class="fr"><div id="pagingComponent"></div></span>

	<!-- -----------------------------------------表格----------------------------------------- -->


<form class="layui-form" action="" id="andOrUpdate" style="display: none">
	<br></br>

  	<div class="layui-form-item" pane>
        <label class="layui-form-label">视频地址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" lay-verify="required"
                   class="layui-input" id="address" style="width: 400px;"/>
                   <span class="layui-btn" id="upload">上传视频</span>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label"> 视频名</label>
        <div class="layui-input-inline">
            <input type="text" name="name" class="layui-input" />
        </div>
    </div>

	<div class='layui-form-item'>
		<label class='layui-form-label'>视频描述</label>
		<div class='layui-input-inline' style='width: 600px;'>
			<textarea name="messages" class='layui-textarea'
				id='messagesAdd' style='display: none;'></textarea>
		</div>
	</div>


</form>


<form class="showDataForm indeximg" id="imgapp" action="uploadImg"
	enctype="multipart/form-data" method="post">
	<input style="display: none" id="file" type="file" name="file"
		ni="0" onchange="upperCase(this)" class="fileImg file_url">
</form>
<!-- 统一的配置信息 -->
<script type="text/javascript" src="${basePath }/static/js/config.js"></script>
<script type="text/javascript"
	src="${basePath }/static/frame/layui/layui.js"></script>
<!-- jQuery -->
<script type="text/javascript" charset="utf8"
	src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="${basePath }/static/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="${basePath }/static/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	var currentPage = 1;
	var currentDetail = new Object();
	
	//入口函数相当于java，main方法
	layui.use(['table', 'form', 'layedit', 'element', 'layer'],
	function() {
	    var $ = layui.jquery,
	    //调用内部的jquery
	    element = layui.element(),
	    layer = layui.layer,
	    layedit = layui.layedit,
	    table = layui.table;
	    form = layui.form();
        //所有的表格按如下设置
        var options = {
            id: 'dateTable',//表格id
            order: [[1, "desc"]],   // 排序
            pagingType: "simple_numbers",// 分页样式 simple,simple_numbers,full,full_numbers
           // url: js_path + '/imgconfig/findIndexImgConfig?flage=indexImgConfig',//查询接口
            url: js_path + '/mfClassMv/queryDynamic', 
            data: {'queryRestraintJson': getCurrentRestraintJson()},//查询参数,
            buttons: ['table-edit', 'table-delete'],  //表格里面按钮的class
            buttonsName: ['编辑', '删除'],
            handlers: [
                function (item, row) {
                    layer.open({
                        type: 1
                        , maxmin: true
                        , title: '修改视频信息'
                        , area: ['800px', '400px']
                        , content: $('#andOrUpdate')
                        , btn: ['确认修改', '全部关闭']
                        , shade: ['0.3', '#000'] 		//不显示遮罩
                        , yes: function () {
                            var flag = form.verifyField('#andOrUpdate');
                            if (flag == false) {
                                return;
                            }
                            layer.closeAll();
                            // 待完成
	                    var messageValue = layedit.getContent(messageValueLayedit);
	                    $("#messagesAdd").val(messageValue);
                            var object = form.getFormparam('#andOrUpdate');
                            object.id = item.id;
                            // 确定添加
                              var data={id:object.id, name:object.name, address:object.address, messages:object.messages, createDate:object.createDate};
                            $.ajax({
                                type: "POST",
                                url: js_path + "/mfClassMv/update",
                                data: data,
                                success: function (data) {
                                    layer.msg(data.msg, {icon: 1});
                                    // 刷新页面
                                    queryHandler();
                                    spotAndModuleHandler('3');
                                }
                            });
                        }
                    ,success: function(layero) {
                    	
                    	$.ajax({
    		                type: "get",
    		                async: false,
    		                url: js_path + "/mfClassMv/modify?id=" + item.id,
    		                //发送到后台的url
    		                success: function(data) {
    		                    form.setInfo('#andOrUpdate', data.data);
    		                }
    		            });
                    	
    	                    messageValueLayedit = layedit.build('messagesAdd', {
    	                        height: 180,
    	                        //设置编辑器高度
    	                        width: 400,
    	                    });
    	                    element.init();
    	                    form.render();
    	                    layer.setTop(layero); //重点2
    	                }
                    });
                    $.ajax({
                        type: "get",
                        url: js_path + "/mfClassMv/modify?id=" + item.id,  //发送到后台的url
                        success: function (data) {
                            form.setInfo('#andOrUpdate', data.data);
                            element.init();
                            form.render();
                            queryHandler();
                        }
                    });
                },
                // 删除
                function (td, row) {
                    //询问框
                    layer.confirm('确定要删除？', {
                        btn: ['删除', '取消'] //按钮
                    }, function () {
                        var index = layer.load(0, {shade: false});
                        //点击删除按钮后单个删除
                        $.ajax({
                            type: "POST",
                            url: js_path + "/mfClassMv/delete",//发送到后台的url
                            data: {'id': td.id},
                            success: function (data) {
                                layer.msg(data.msg, {icon: 1});
                                layer.close(index);
                                // 刷新页面
                                queryHandler();
                                delspotAndModuleHandler('3');
                            }
                        });
                    }, function () {
                    });
                }
            ] //对应的button的回调函数
            //分页事件
            , pageHandler: function (page) {
                currentPage = page;
                queryHandler();
            }
            , batchChooseHandler: function (list) {
                var index = layer.load(0, {shade: false});
                var item = "";
                for (var i = 0; i < list.length; i++) {
                    //console.log(list[i].id);
                    item += list[i].id + ",";
                }
                // 批量删除
                $.ajax({
                    type: "POST",
                    url: js_path + "/mfClassMv/batchDelete",//发送到后台的url
                    data: {'listId': item},
                    success: function (data) {
                        layer.close(index);
                        layer.msg('已经成功删除', {icon: 1});
                        queryHandler();
                        delspotAndModuleHandler('3');
                    }
                });
            }
        };
        table.init(options);
        table.setPageSize(10)	//设置一页显示数量
        table.batchDelete('#btn-delete-all');

        // 为查询按钮注册事件
        $("#queryBtn").click(function () {
            queryHandler();
        });
        // 初始化查询方法  用来刷新页面
        function queryHandler() {
            $.ajax({
                type: "POST",
                url: js_path + '/mfClassMv/queryDynamic',//查询接口
                data: {'queryRestraintJson': getCurrentRestraintJson()},//查询参数,
                success: function (data) {
                    table.render(data);
                }
            });
        }
        $("#add").click(function () {

            form.clear('#andOrUpdate');
            layer.open({
                type: 1 //页面层
                , title: '添加视频信息'
                , area: ['800px', '400px']
                , shade: ['0.3', '#000']
                , maxmin: true
                , content: $('#andOrUpdate')
                , btn: ['确认添加', '全部关闭'] 	//只是为了演示
                , yes: function () {
                	 var messageValue = layedit.getContent(messageValueLayedit);
	                    $("#messagesAdd").val(messageValue);
                    var flag = form.verifyField('#andOrUpdate');
                    if (flag == false) {
                        return;
                    }
                    layer.closeAll();
                    var entity = form.getFormparam('#andOrUpdate');
                    var data={name:entity.name, address:entity.address, messages:entity.messages};
                    $.ajax({
                        type: "POST",
                        url: js_path + "/mfClassMv/add",//发送到后台的url
                        data: data,
                        success: function (data) {
                            layer.msg(data.msg, {icon: 1});
                            // 刷新页面
                            queryHandler();
                            spotAndModuleHandler('3');
                        }
                    });

                },
            success: function(layero) {
            	
            	 messageValueLayedit = layedit.build('messagesAdd', {
                     height: 180,
                     //设置编辑器高度
                     width: 400,
                     /* uploadImage: {
                         url: js_path + '/file/pictureUpload',
                         type: 'post'
                     } */
                 });

                element.init();
                form.render();
                layer.setTop(layero); //重点2
            }
                
            });
        });

        $("#goback").click(function () {
            history.back();
        });
        // 例:获取ids
        $(document).on('click', '#btn-delete-all', function () {
            // getIds(table对象,获取input为id的属性)
            var j = getIds($('#dateTable'), 'data-id');
            if (list == null || list == '') {
                layer.msg('未选择');
            } else {
                layer.msg(list);
            }
        });

        // 获取全选的值
        function getIds(o, str) {
            var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
            var list = '';
            obj.each(function (index, elem) {
                list += $(elem).attr(str) + ',';
            });
            list = list.substr(0, (list.length - 1));
            return list;
        }
    });



    //获取当前约束条件  包括分页信息，查询条件信息等
    //对象转JSON
    //翻页、点击查询会触发此方法
    function getCurrentRestraintJson() {
        //此处仅供测试，实际需要读取标签值
    	   //此处仅供测试，实际需要读取标签值
        var mfClassMv = new Object();
        //MfClassMv.name = $("#status3").find("option:selected").val();
        //MfClassMv.flage=$("#imgFlageQuery").find("option:selected").val();

        var MfClassMvDynamicQueryVo = new Object();
        MfClassMvDynamicQueryVo.page = currentPage - 1;
        MfClassMvDynamicQueryVo.pageSize = 10;
        MfClassMvDynamicQueryVo.mfClassMv = mfClassMv;
        return JSON.stringify(MfClassMvDynamicQueryVo);
    }


    $("#upload").click(function () {
        $("#file").trigger("click");

    });

    function upperCase(obj) {
    	
    	var zhezhao=  layer.open({
              type: 3
              , maxmin: true
              ,id:'zhezhao'
              , shade: ['0.3', '#000'] 		//不显示遮罩
    	      ,zIndex: layer.zIndex
         
          });

        $.ajaxFileUpload
        (
            {
                url: js_path + '/mfClassMv/indexImgUpLoad',//发送到后台的url
                secureuri: false,           //一般设置为false
                fileElementId: 'file', //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   
                //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
                dataType: 'json',//返回值类型 一般设置为json
                complete: function () {//只要完成即执行，最后执行
                },
                success: function (data)  //服务器成功响应处理函数
                {
                	 layer.msg(data.msg);
                	 layer.close(zhezhao);
                     if (data.code == 200) {
                         $("#address").val(data.data);
                     }


                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                	 layer.close(zhezhao);
                	 layer.msg("数据异常，请稍后....");
                }

            }
        )


    }

    /**
    *课堂模块热点提示
	*moduleId 模块id
	*/
	function spotAndModuleHandler(moduleId,userId){
		
		$.ajax({
			type:"POST",
	        dataType: 'json',
	        url:js_path+'/spotpush/queryModule',
		    data:{'json':JSON.stringify({'moduleId':moduleId,'userId':userId})},		
		    success: function(data) {
		    	
		    	//存在就更新
		    	if(data.data&&data.data.length>0){
		    		var item = data.data[0];
		    		var timesOfBrowsing = parseInt(item.timesOfBrowsing);
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/updateModule',
    				    data:{'json':JSON.stringify({'id':item.id,'timesOfBrowsing':++timesOfBrowsing,'moduleId':moduleId,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    		
		    	}else{//不存在就创建
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/addModule',
    				    data:{'json':JSON.stringify({'timesOfBrowsing':'1','moduleId':moduleId,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    	}
		    	
		    }
		});
	}
	
	/**
	*减少对应的热点提示
	*moduleId 模块id
	*/
	function delspotAndModuleHandler(moduleId,userId){
		return;//此功能展示屏蔽
		$.ajax({
			type:"POST",
	        dataType: 'json',
	        url:js_path+'/spotpush/queryModule',
		    data:{'json':JSON.stringify({'moduleId':moduleId,'userId':userId})},		
		    success: function(data) {
		    	
		    	//存在就更新
		    	if(data.data&&data.data.length>0){
		    		var item = data.data[0];
		    		var timesOfBrowsing = parseInt(item.timesOfBrowsing);
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/updateModule',
    				    data:{'json':JSON.stringify({'id':item.id,'timesOfBrowsing':--timesOfBrowsing,'moduleId':moduleId,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    		
		    	}else{//不存在就创建
		    		$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/spotpush/addModule',
    				    data:{'json':JSON.stringify({'timesOfBrowsing':'0','moduleId':moduleId,'userId':userId})},		
    				    success: function(data) {
    				    	
    				    }
    				});
		    	}
		    	
		    }
		});
	}

</script>
</body>
</html>

