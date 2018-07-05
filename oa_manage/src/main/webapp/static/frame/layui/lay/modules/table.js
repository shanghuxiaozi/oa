/**

 @Name：layui.table 表格控制器
 @Author：宋琪
 @License：MIT
    
 */
 /**扩展IE*不支持indexOf**/
extendTheIEArray();
layui.define(['layer','laypage', 'form'], function(exports){
	var $ = layui.jquery
	  ,layer = layui.layer
	  ,form = layui.form(),laypage = layui.laypage;
	//var isControll = true;//控制是否重新渲染页码控件
	//var pageSize = 10;
	//var dataList = [];//保存每页额渲染数据
	var Table = function(){
	    this.options;
	 };
	// var buttonClass = ['','layui-btn-normal','layui-btn-danger'];
	// var trList ;//console.log("trList=",trList);
	// var pageHandler;
	// var total = 0;//总页数
	// var currentID = 0;//当前表格id
	// var currentPage = 0;
//	 var pageConfig = {
//			    cont: 'pagingComponent'
//				   
//			
//				    ,skip: true
//				    ,jump: function(obj, first){
//				        if(!first){
//				          
//				          if( typeof pageHandler === 'function' ){
//				        	  isControll = false;
//				        	  currentPage = obj.curr;
//				        	  pageHandler(obj.curr);
//				          }
//				        }
//				      }
//				  };
	//初始化渲染
	  Table.prototype.init = function(options){
	    var that = this ;
	    that.options = options;
	    var pageSize =  that.pageSize = 10;
	    var isControll = that.isControll = true;//控制是否重新渲染页码控件
	    var dataList = that.dataList =  [];//保存每页额渲染数据
	    var buttonClass = that.buttonClass = ['','layui-btn-normal','layui-btn-danger'];
	    var trList  = that.trList;
	    var pageHandler = that.pageHandler = options.pageHandler;
	    var total = that.total = 0;//总页数
	    var currentID = that.currentID = 0;//当前表格id
	    var currentPage = that.currentPage =  0;
	    var pageConfig = that.pageConfig =  {
			    cont: 'pagingComponent'
				   
			
				    ,skip: true
				    ,jump: function(obj, first){
				        if(!first){
				          
				          if( typeof that.pageHandler === 'function' ){
				        	  isControll =  that.isControll = false;
				        	  that.currentPage = obj.curr;
				        	  that.pageHandler(obj.curr);
				          }
				        }
				      }
				  };
	    
	    pageHandler = options.pageHandler;
	    
	    if(!options.id){
	    	// console.error("未传入id值");
	    	 return;
	    }
	    //console.log("----------表格初始化成功--------------");
	    options.id = "#"+options.id;
	   trList = $(options.id+' thead tr').find('th');//console.log("trList=",trList);
	  //查询的函数
        if(options.url)
        {
        	var circle = layer.load(0, {shade: false}); 
        	//动态查询
        	$.ajax({
        		type: "POST",
        		url: Config.IP + options.url,
        		data:options.data,
        		success: function(data) {
        			layer.close(circle);
        			if(data.code == 200){
        				
        				if( data.data.length > 0 ){
        					
        						
        						that.pageConfig.pages = Math.ceil(data.total/that.pageSize);
        						laypage(that.pageConfig);
        						if(options.firstLoadSuccess){//首次成功加载数据
        							options.firstLoadSuccess(data);
        							
        						}else{
        							that.render(data);
        						}
        					
        				}else{
        					layer.msg('无记录', {icon: 3});
        				}
        			}else{
        				layer.msg('无记录', {icon: 3});
        			}
        		},
        		error:function(e){
        			layer.close(circle);
        		}
        	});
        }
	   
	 };
	 
	 /**更新分页数据**/
	 Table.prototype.upDatePage = function(data){
		 var that = this;
		 var pageSize =  that.pageSize;
		 that.pageConfig.pages = Math.ceil(data.total/pageSize);
		laypage(that.pageConfig);
	 }
	 
	 /**渲染表格数据**/
	 Table.prototype.render = function(data){
		 
		 var that = this;
		 
		 var pageSize =  that.pageSize;
		  var isControll = that.isControll ;//控制是否重新渲染页码控件
		  var buttonClass = that.buttonClass
		  var trList  = that.trList;
		  var currentPage = that.currentPage
		  
		 var options = that.options;
		 
		 that.currentID = options.id;
		 if($(options.id+' thead').find("tr").length>1){
			 trList = $(options.id+' thead').find(".trContent").find('th');
		 }else
			 trList = $(options.id+' thead tr').find('th');
		 var dataList = that.dataList = data.data;//console.log("dataList=",dataList);
		 var trW = 0,trWList = new Object();
		 var QRcodeList = new Object();//二维码td
		 for(var i=0,m=trList.length;i<m;i++){
			 var tr = $(trList[i]);
			 if(tr.attr('laya-type')=='serial'){
				 trW+=50;
				 
			 }else if(tr.attr('laya-type')=='radio'){
				 trW+=50;
				 
			 }if(tr.attr('laya-type')=='status'){
				 
				 trWList[tr.attr('name')] = tr.html().length*22;
			 }else if(tr.attr('name') == 'checked'){
				 trW+=50;
				 
			 }else if(tr.attr('name') == 'operButton'){
				 for(var z=0,n=options.buttons.length;z<n;z++){
					 trW+=options.buttons[z].length*22;
				 }
			 }else if( tr.attr('laya-type')=='image' ){
				 trW+=120;
			 }else{
				// trW+=tr.html().length*22;
				 trWList[tr.attr('name')] = tr.html().length*22;
			 }
			 
		 }
		 
		 $(options.id+' tbody').empty();
			for( var i = 0, m = data.data.length; i < m ; i++ ){
				var item = data.data[i];
				var row = "";
				var tipsList = new Object();
				var trBg;
				for( var j = 0;j < trList.length; j++ ){
					var tr = $(trList[j])
//					console.log("trList[j]=",tr.attr('name'));
					
					if(tr.attr('laya-type')=='status'){
//						row+='<td> <div class="layui-input-inline"> <select name="quiz"> <option value="0">请选择</option> <option value="1">已处理</option><option value="2">未处理</option> </select> </div></td>';
						//var select = '<div class="layui-input-inline"> <select name="quiz">';
						var key = tr.attr('kh-key').split(',');
						var vaule = tr.attr('kh-value').split(',');
						var content = "";
						for( var aa = 0;aa<key.length;aa++ ){
							if(item[tr.attr('name')]==key[aa]){
								content = vaule[aa];
							}
								
						}
						
						var conditions = [] , bg;
						 if(tr.attr('laya-color-conditions'))//laya-color-conditions='[{"field":{"key":"orderStatus","value":2},"color":"#dac4c4"}]'
							 conditions = JSON.parse(tr.attr('laya-color-conditions'));
						 for(var cg=0;cg<conditions.length;cg++){
							 var cg_v = conditions[cg];
							 if( parseInt(item[cg_v.field.key]) == parseInt(cg_v.field.value) ){
								 trBg = bg = cg_v.color;
								 break;
							 }
						 }
//						if(bg)
//							row+='<td style="background-color:'+bg+'"> '+content+' </td>';
//						else
							row+='<td > '+content+' </td>';
						if(tr.html().length<content.length){
							trWList[tr.attr('name')] = content.length*22;
						}
						
						
					 
					 
					}else if( tr.attr('laya-type')=='date' ){//如果是日期
						var date = new Date(item[tr.attr('name')]);
						row+='<td> '+date.getFullYear()+'-'+(date.getMonth()+1)+'-'+(date.getDate())+' </td>';
					}else if( tr.attr('laya-type')=='dateFormat' ){//<th name="date" laya-type="dateFormat" laya-format="yyyy-mm-dd " style="width:150px;">日期</th>
						var msg = item[tr.attr('name')];
						var format = tr.attr('laya-format');
						var content = "";
						if(msg){
							content = msg.substr(0,format.length);
						}
						row+='<td> '+content+' </td>';
					}else if( tr.attr('laya-type')=='image' ){//如果是日期
						var src = item[tr.attr('name')]||"";
						row+='<td> <img style="width:100px;height:100px" src="'+src+'"> </td>';
					}else if( tr.attr('laya-type')=='split' ){//如果是日期
						var a = item[tr.attr('name')];
						if(a==null||a=='' || a=="null"){
							row+='<td></td>';
						}else{
							var backgroundColor = '#ffffff';//'#65c7be';
							if(a.length > 0 && a.indexOf(',')!=-1){
								var list = a.split(',');
								var content = "";
								for(var ii=0,mm=list.length;ii<mm;ii++){
									if(list[ii] == '' || list[ii] == 'null')list[ii]=''
									if(ii == mm-1 &&(list[ii] == '' || list[ii] == 'null'))continue;
//									if(ii%2==0)
//										backgroundColor = '#65c7be';
//									else
//										backgroundColor = '#66ace0';
									
									content+='<p style=" margin-bottom: 3px;vertical-align: middle;'+
									    'height: 20px;'+
								    'line-height: 20px;'+
								    'padding: 0 18px;'+
								    //'background-color: '+backgroundColor+
								    ';color: #000;'+
								    'white-space: nowrap;'+
								    'text-align: center;'+
								    'font-size: 14px;'+
								    ''+
								    '">'
								    +list[ii]+'</p>';
									if(tr.html().length<list[ii].trim().length){
										if(list[ii].trim().length>32)
											trWList[tr.attr('name')] = list[ii].length*7;
										else
										 trWList[tr.attr('name')] = list[ii].length*22;
									}
								}
								row+='<td> '+content+'</td>';
								
							}else{
								row+='<td style="text-align: center;"> '+a+'</td>';
								if(tr.html().length<a.length){
									if(a.length>32)
										trWList[tr.attr('name')] = a.length*7;
									else
										trWList[tr.attr('name')] = a.length*22;
								}
							}
						}
						
						
					}else if(tr.attr('laya-type')=='serial'){
						row+='<td > '+(i+1+((currentPage==0?1:currentPage)-1)*pageSize)+' </td>';
					}else if(tr.attr('laya-type')=='radio'){//复选框
						var content = "";
							content = ' <input type="radio" name="'+tr.attr('name')+'" >';
						row+='<td> '+content+' </td>';	
					}else if(tr.attr('laya-type')=='QRcode'){//二维码
						var a = item[tr.attr('name')];
						if(a==null||a=='' || a=="null"){
							row+='<td></td>';
						}else{
							var random =  Math.random();
							if(a.length > 0 && a.indexOf(',')!=-1){
								var list = a.split(',');
								var content = "";
								for(var ii=0,mm=list.length;ii<mm;ii++){
									content += "<div id='td_QRcode_"+i+"-"+ii+random+"' style='margin-bottom: 3px;margin-left: 10px;'><div  class='orcode'></div><div style='display: none;' class='codeDiv'>";
									content+=list[ii];
									content+="</div></div>";
									QRcodeList['td_QRcode_'+i+"-"+ii+random] = 0;
								}
								
								row+='<td   style="text-align: center;"> '+content+' </td>';
								
							}else{
								var content = "<div class='orcode'></div><div div style='display: none;' class='codeDiv'> ";
								content+=a;
								content+="</div>";
								row+='<td  id="td_QRcode_'+i+random+'" style="text-align: center;"> '+content+' </td>';
								QRcodeList['td_QRcode_'+i+random] = 0;
							}
						}
					}else if(tr.attr('name')!='operButton' && tr.attr('name')!='checked'){
						var msg,content = "";
						if(item[tr.attr('name')]!=0)//注意这里tr.attr('name')]!=0和tr.attr('name')]!=""等价
							msg = item[tr.attr('name')]||"";
						else
							msg = item[tr.attr('name')]
						msg+="";
//						if(msg.length > 32 )
//							msg = msg.substr(0,32)+"<br/>"+msg.substr(32);
						if(msg.trim().length > 32 && msg.indexOf('<img')==-1){
							content = msg.substr(0,32)+'... ...';
							content = content.replace("<","&lt;");
							content = content.replace(">","&gt;");
							var random =  Math.random();
							if(tr.css('display')=='none'){
								row+='<td style="display:none"  id="td_'+i+random+'" >'+content+'</td>';
							}else
							row+='<td   id="td_'+i+random+'" >'+content+'</td>';
							tipsList['td_'+i+random] = msg;
							trWList[tr.attr('name')] = 33*7;
						}else{
							if(tr.css('display')=='none'){
								row+='<td style="display:none">'+msg+'</td>';
							}else
								row+='<td>'+msg+'</td>';
							if(tr.html().length<msg.length){
								trWList[tr.attr('name')] = msg.length*7;
							}
						}
							
					}else if(tr.attr('name') == 'operButton'){
						var button = "";
						var conditions = [];
						var perm = [];
						var isPerm = false;
						if(tr.attr('laya-perm')){
							perm=tr.attr('laya-perm').split(',');
//							perm = JSON.parse(tr.attr('laya-perm'));
//							for(var pp=0;pp<perm.length;pp++){
//								if(perm[pp] === 0){
//									continue;
//								}else{
//									if(!perm[pp])
//									perm[pp] = "-";
//								}
//								
//								
//							}
							isPerm = true;
						}
						
						if(tr.attr('laya-conditions'))
						conditions = JSON.parse(tr.attr('laya-conditions'));//laya-conditions='[{"field":{"key":"orderStatus","value":2},"button":1}]'
						
						for(var z=0,n=options.buttons.length;z<n;z++){
							var isDisabled = false;
							for(var gg=0,gm=conditions.length;gg<gm;gg++){
								var gc = conditions[gg];
								if( z==gc.button && parseInt(item[gc.field.key]) == parseInt(gc.field.value)){
									isDisabled = true;
									break;
								}
							}
							if(isPerm){
								var isVisible = false;
								for(var pp=0;pp<perm.length;pp++){
									
//									if(!perm[pp]&& perm[pp]!=0){
										if(parseInt(perm[pp]) === z){
											isVisible = true;
											break;
										}
//									}
									
								}
								if(isVisible){
									if(isDisabled){
										button+='<button type="button" class="layui-btn layui-btn-small layui-btn-disabled '+buttonClass[z]+' '+options.buttons[z]+'" name="'+options.buttons[z]+'" disabled>'+options.buttonsName[z]+'</button>';
									}else{
										button+='<button type="button" class="layui-btn layui-btn-small '+buttonClass[z]+' '+options.buttons[z]+'" name="'+options.buttons[z]+'">'+options.buttonsName[z]+'</button>';
									}
								}else{
									button+='';
								}
							}else{
								if(isDisabled){
									button+='<button type="button" class="layui-btn layui-btn-small layui-btn-disabled '+buttonClass[z]+' '+options.buttons[z]+'" name="'+options.buttons[z]+'" disabled>'+options.buttonsName[z]+'</button>';
								}else{
									button+='<button type="button" class="layui-btn layui-btn-small '+buttonClass[z]+' '+options.buttons[z]+'" name="'+options.buttons[z]+'">'+options.buttonsName[z]+'</button>';
								}
							}
							
							
							
						}
						
						
						
						
						row+='<td> '+button+' </td>';
					}else if(tr.attr('name') == 'checked'){
						row+='<td><div class="table-checkbox"><input type="checkbox" lay-skin="primary" class="my-checkbox" data-id="'+i+'" lay-filter="singleChoose"/></div></td>';
						
					}
				}
				
				if(trBg){
					$(options.id+' tbody').append('<tr id="tr_'+i+'"  style="background-color:'+trBg+'">'+row+'</tr>');
				}else{
					$(options.id+' tbody').append('<tr id="tr_'+i+'"  >'+row+'</tr>');
				}
				
				
				var kh_tips = $(options.id).attr('kh-tips');
				if(kh_tips ){
					kh_tips = JSON.parse(kh_tips);
					if( kh_tips.length > 0){
						var tips_content = "";
						
						for(var ti = 0,tm = kh_tips.length;ti<tm;ti++){
							var value = kh_tips[ ti ];
							tips_content += '<p>'+value.name+":"+item[value.key]+'</p>';
						}
						
						form.addTips('#tr_'+i,tips_content , 1 ,'#9ebddc' , 1000 , 500000);
					}
					
					
				}
				
				for(var key in tipsList){
					
					//
					var td=document.getElementById(key);
					if( tipsList[key].length/32<5 )
						form.addTips(td,tipsList[key],1,'#9ebddc',410,500000);
					else
						form.addTips(td,tipsList[key],1,'#9ebddc',750,500000);
				}
				
				//鼠标移动显示二维码
				for( var key in QRcodeList ){
					var td=document.getElementById(key);
					form.addTipsORcode(td,$(td).find('.codeDiv').html(),1,'#9ebddc',410,500000);
				}
//				$(options.id + ' .layui-form-checkbox ').css({    'height': '20px','line-height': '20px',    'margin-right': '0px',	    'padding-right': '15px',	    'padding-left': '5px'});
			}
			//$(options.id+' tbody').append('<div id="translate"></div>');
//			for(var key in trWList){
//				trW+=trWList[key];
//			}
//			$(options.id).css('width',trW+'px');
			
			initTable(options,data);
			 function initTable(options,data){
			    	
				    
				    // 初始化表格
			       /* $(options.id).DataTable({
			            "dom": '<"top">rt<"bottom"flp><"clear">',
			            "autoWidth": false,                     // 自适应宽度
			            "stateSave": true,                      // 刷新后保存页数
			            "order": options.order,               // 排序
			            "searching": false,                     // 本地搜索
			            "info": true,                           // 控制是否显示表格左下角的信息
			            "stripeClasses": ["odd", "even"],       // 为奇偶行加上样式，兼容不支持CSS伪类的场合
			            "aoColumnDefs": [{                      // 指定列不参与排序
			                "orderable": false,
			                "aTargets": [0,trList.length-1]              // 对应你的表格的列数
			            }],
			            "pagingType":  options.pagingType,         // 分页样式 simple,simple_numbers,full,full_numbers
			            "language": {                           // 国际化
			                "url":'language.json'
			            }/*,
			            "ajax": {
			                "url":  Config.IP + options.url,
			                "data":options.data,
			                "type": "POST"
			            },
			            "columns": [
			                { "data": "templateId" },
			                { "data": "templateName" },
			                { "data": "templateType" },
			                { "data": "isDeleted" },
			                { "data": "createTime" },
			                { "data": "standBy" }
			            ]*/
			            
			       /* });*/
			        
			        
			        
			        
			        var handlers ;
			        if(options.handlers && options.handlers.length > 0){
			        	handlers = [handlers];
			        }
			        if(options.buttons && options.buttons.length > 0){
			        	for(var i=0,m=options.buttons.length;i<m;i++){
			        		if(!options.buttons[i])continue;
			        		
			        		 $(options.id+' .'+ options.buttons[i]).click(function (e) {
			        	           
			        	            var trSeq = $(this).parent().parent().parent().find("tr").index($(this).parent().parent());
			        	            //获取鼠标点击某一列的值
//			        	            var td = $(this).parent().parent().parent().find("tr").eq(trSeq).find("td");
			        	            
			        	            //console.log( td.eq(3).html());
			        	           // alert("第" + (trSeq) + "行" );
			        	            //----------------------------------------------------
			        	            var handler ;
			        	            var index = options.buttons._indexOf(e.target.name);
			                		
			        	            if(options.handlers && options.handlers.length > 0){
			        	            	if( typeof options.handlers[index] === 'function' ){
			        	            		handler = options.handlers[index];
			        	            		handler(dataList[trSeq],trSeq);
			        	            		//checkboxAll();
			        	            		
			        	            	}
			        	            	
			        	            }
			        	 
			        	            
			        	            //---------------------------------------------------
			        	        })
			        	}
			        }
			        
			        
			        
			        
			        
			        
			        
			        that.pageConfig.pages= Math.ceil(data.total/pageSize);
			        if(isControll){
			        	laypage(that.pageConfig);
			        	
			        }
			        
			        
			        isControll = true;
			        
			        
			      
			        checkboxAll();
//			        $(options.id+' tbody .layui-form-checkbox').css({'height': '25px','padding-right': '15px','padding-left': '10px'});
//			        $(options.id+' tbody .layui-form-checkbox i').css({'font-size': '15px'});
			        
			}
			 
			 //全选
			 function checkboxAll(){
				 
				 form.render('checkbox');
				 
				//全选
			        form.on('checkbox(allChoose)', function(data){
			          var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
			          
			          child.each(function(index, item){
			            item.checked = data.elem.checked;
			          });
			          form.render('checkbox');
			          if(options.allChooseHandler){
			        	  if(data.elem.checked)
			        		  options.allChooseHandler(dataList,data.elem.checked);
			        	  else
			        		  options.allChooseHandler([],data.elem.checked);
			          }
//			          $(currentID+' tbody .layui-form-checkbox').css({'height': '25px','padding-right': '15px','padding-left': '10px'});
//				      $(currentID+' tbody .layui-form-checkbox i').css({'font-size': '15px'});
			        });
			        
			        
			        //
			      //单选
			        form.on('checkbox(singleChoose)', function(data){//console.log();
			          var child = $(data.elem);
			          var trSeq = child.parent().parent().parent().parent().find("tr").index($(this).parent().parent().parent());
			          //console.log(dataList[trSeq],trSeq);
			          if(options.singleChooseHandler){
			        	  options.singleChooseHandler(dataList[trSeq],trSeq,data.elem.checked);
			          }
			        });
			 }
			
	 }
	 
	
	 
	 
	 /**获取dataList**/
	 Table.prototype.getDataList = function(){
		return this.dataList;
		 
	 }
	 
	 /**设置页码**/
	 Table.prototype.setPageSize = function(size){
		 
		 this.pageSize = size;
		 
	 }
	 
	 /**单条修改**/
	 Table.prototype.singleEdit = function(id){
		 var that = this;
		 var options = that.options;
		 $(document).on('click',id, function(){
	         // getIds(table对象,获取input为id的属性)
	         var list = getIds($(options.id),'data-id');
	         if(list == null || list == ''){
	             layer.msg('未选择');
	         }else if(list.length!=1){
	        	 layer.msg('请选择一条数据进行修改！');
	         }else{
	        	 if( typeof options.singleChooseEditHandler === 'function' ){
		        	  options.singleChooseEditHandler(list[0]);
		          }
	         }
	     });
	     function getIds(o,str){
	    	 	//var that = this;
			    var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
			    var list = [];
			    obj.each(function(index,elem){
//			        list += $(elem).attr(str)+',';
			    	for(var i = 0 , m = that.dataList.length;i<m;i++){
			    		if(i ==  $(elem).attr(str)){
			    			list.push(that.dataList[i]);
			    		}
			    	}
			    });
			    return list;
			}
	
	 }
	 
	 /**批量删除**/
	 Table.prototype.batchDelete = function(id){
		 var that = this;
		 var options = that.options;
		 $(document).on('click',id, function(){
	         // getIds(table对象,获取input为id的属性)
	         var list = getIds($(options.id),'data-id');
	         if(list == null || list == ''){
	             layer.msg('未选择');
	         }else{
	        	 if( typeof options.batchChooseHandler === 'function' ){
		        	  options.batchChooseHandler(list);
		          }
	         }
	     });
	     function getIds(o,str){
			    var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
			    var list = [];
			    obj.each(function(index,elem){
//			        list += $(elem).attr(str)+',';
			    	for(var i = 0 , m = that.dataList.length;i<m;i++){
			    		if(i ==  $(elem).attr(str)){
			    			list.push(that.dataList[i]);
			    		}
			    	}
			    });
			    return list;
			}
	
	 }
	 
	 /**批量选择**/
	 Table.prototype.getChooseList = function(){
		 var that = this;
		 var options = that.options;
		 var o = $(options.id);
		 var str = 'data-id';
		 var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
		    var list = [];
		    obj.each(function(index,elem){
		    	for(var i = 0 , m = that.dataList.length;i<m;i++){
		    		if(i ==  $(elem).attr(str)){
		    			list.push(that.dataList[i]);
		    		}
		    	}
		    });
		    return list;
	 }
	 
	 
	 /**批量选择**/
	 Table.prototype.batchChooseList = function(handler){
		 var that = this;
		 var options = that.options;
		 var o = $(options.id);
		 var str = 'data-id';
		 var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
		  //  var list = [];
		    obj.each(function(index,elem){
		    	for(var i = 0 , m = that.dataList.length;i<m;i++){
		    		if(i ==  $(elem).attr(str)){
		    			//list.push(dataList[i]);
		    			if(handler)
		    			handler(i,that.dataList[i]);
		    		}
		    	}
		    });
		    
	 }
	 
	 //得到一个新的实例
	 Table.prototype.getNewTable = function(){
		 return new Table();
	 }
	 var table = new Table();
	//暴露接口
	  exports('table', table);
});


/**扩展IE*不支持indexOf**/
function extendTheIEArray(){
	if(!Array.prototype._indexOf){   
		Array.prototype._indexOf=function(n){                                                                                                                                         
	    if("indexOf" in this){ 
	        return this["indexOf"](n); 
	    }   
	    for(var i=0;i<this.length;i++){ 
	        if(n===this[i]){ 
	            return i; 
	        } 
	    } 
	    return -1; 
	}; 
	}; 
}
//清除两边的空格 
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ''); 
}; 