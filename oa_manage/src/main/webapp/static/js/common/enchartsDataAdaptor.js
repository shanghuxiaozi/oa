//将map形式数据转换为[{key:xx,value:xx},{key:xx,value:xx}],以适配encharts饼图所需数据
function mapToObjectList(map){
	var list=new Array();
	var index=0;
	$.each(map,function(name,value){
//		console.log(name+":"+value);
		var o=new Object();
		o.name=name;
		o.value=value;
		list[index]=o;
		index++;
	});
	return list;
}

//将map形式数据转换为[key1,key2..},{value1,value1...}],以适配encharts柱状所需数据
//返回结果 一个对象里面装有两个数组 keys and values
function mapTo2List(map){
	var result =new Object();
	var keys=new Array();
	var values=new Array();
	var index=0;
	$.each(map,function(name,value){
//		console.log(name+":"+value);
		keys[index]=name;
		values[index]=value;
		index++;
	});
	result.keys=keys;
	result.values=values;
	return result;
}


//地图数据适配器
//原始数据："offCarPlaceDist":[{"lng":"102.646491","name":"NI-430221199006018118","value":1,"lat":"25.041101"},...
function mapList2mapDataAndCoordMap(placeDist){
	var result=new Object();
	var mapData=new Array();
	var coordMap=new Object();
	result.mapData=mapData;
	result.coordMap=coordMap;
	var index=0;
	$.each(placeDist,function(index,item){
		console.log("lng:"+item.lng);
		mapData[index]=new Object();
		mapData[index].name=item.name;
		mapData[index].value=item.value;
		
		coordMap[item.name]=[item.lng,item.lat];
		
	});
	return result;
}



//function test(){
//	var map=new Object();
//	map.key1="value1";
//	map.key2="value2";
//	var list=mapToObjectList(map);
//	console.log(JSON.stringify(list));
//}
//test();




