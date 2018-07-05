$(function(){
	 $.ajax({
		type: "POST",
		url: js_path+'/airport/Allairport',//发送到后台的url
		success: function(data) {
			for (var i = 0; i < data.length; i++) {
				 
				$("#startCity1").append("<option id=option value='"+data[i].codeIata+"'>"+data[i].indexName+"</option>");
				$("#endCity1").append("<option id=option value='"+data[i].codeIata+"'>"+data[i].indexName+"</option>");
				$("#startCity2").append("<option id=option value='"+data[i].codeIata+"'>"+data[i].indexName+"</option>");
				$("#endCity2").append("<option id=option value='"+data[i].codeIata+"'>"+data[i].indexName+"</option>");
							}
			form.render();
		}
	});	
});
