function datetime() {
            //创建日期时间对象
    var datetime = new Date();

            //获取组件
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var day = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    var second = datetime.getSeconds();
    var week = datetime.getDay();

    //转换星期格式
    switch (week) {
        case 0:
            var week = '星期日';
            break;
        case 1:
            var week = '星期一';
            break;
        case 2:
            var week = '星期二';
			break;
        case 3:
            var week = '星期三';
            break;
        case 4:
            var week = '星期四';
            break;
        case 5:
            var week = '星期五';
           break;
        case 6:
            var week = '星期六';
            break;
     }

            //小时,分钟,秒如果小于10加上前导零
    if (hour < 10) {
        var hour = 0 + "" + hour;
        }

    if (minute < 10) {
        var minute = 0 + "" + minute;
        }

    if (second < 10) {
        var second = 0 + "" + second;
        }

    //完整时间
    var day =  year + "年" + month + "月" + day + "日" + " " + week;
	var nowtime = hour + ":" + minute + ":" + second 
    //更新内容
    $("#day").text(day);//这里是往p标签中添加
	$("#nowtime").text(nowtime);
}

setInterval(datetime, 1000)

var MyMarhq = '';
clearInterval(MyMarhq);
$('.tbl-body tbody').empty();
$('.tbl-header tbody').empty();
var str = '';
var Items = [{"rank":1,"userid":"dasdasd",count:23},
{"rank":2,"userid":"dasdasd",count:23},
		{"rank":3,"userid":"dasdasd",count:23},
		{"rank":4,"userid":"dasdasd",count:23},
		{"rank":1,"userid":"dasdasd",count:23},
		{"rank":1,"userid":"dasdasd",count:23}];

$.each(Items,function (i, item) {
	str = '<tr>'+
		'<td>'+item.rank+'</td>'+
		'<td>'+item.userid+'</td>'+
		'<td>'+item.count+'</td>'+
		'</tr>'
		
	$('.tbl-body tbody').append(str);
	$('.tbl-header tbody').append(str);
});
		
if(Items.length > 5){
	$('.tbl-body tbody').html($('.tbl-body tbody').html()+$('.tbl-body tbody').html());
	$('.tbl-body').css('top', '0');
	var tblTop = 0;
	var speedhq = 30; // 数值越大越慢
	var outerHeight = $('.tbl-body tbody').find("tr").outerHeight();
	function Marqueehq(){
	if(tblTop <= -outerHeight*Items.length){
		tblTop = 0;
	} else {
		tblTop -= 1;
	}
	$('.tbl-body').css('top', tblTop+'px');
}
		
	MyMarhq = setInterval(Marqueehq,speedhq);
			
	// 鼠标移上去取消事件
	$(".tbl-header tbody").hover(function (){
		clearInterval(MyMarhq);
	},function (){
		clearInterval(MyMarhq);
		MyMarhq = setInterval(Marqueehq,speedhq);
		})
			
}