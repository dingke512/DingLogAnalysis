
var chartDom1 = document.getElementById('cent1');
var myChart1 = echarts.init(chartDom1,'dark');
var option1;

option1={
			
	title: {
		text: '当前访问用户数',
			textStyle: {
			// fontWeight: 'normal',              //标题颜色
			color: '#ffffff',
			fontSize : 23,
				
		},
			x:"center"  
	},
	tooltip: {
		trigger: 'axis'
	},			
	xAxis: {
		type: 'category',
		data: [new Date().toLocaleTimeString()],
		max: 10,
		},

	yAxis: {
		type: 'value',
	},
	series: [{
			name: 'a',
			data: [0],
			type: 'line'
		},
		],
	textStyle: {
		color: '#ffffff',
	}
	
};
		
myChart1.showLoading();


var chartDom2 = document.getElementById('cent2');
var myChart2 = echarts.init(chartDom2,'dark');
var option2;

option2={
			
	title: {
		text: '当前访问页面数',
			textStyle: {
			// fontWeight: 'normal',              //标题颜色
			color: '#ffffff',
			fontSize : 23,
				
		},
			x:"center"  
	},
	tooltip: {
		trigger: 'axis'
	},			
	xAxis: {
		type: 'category',
		data: [new Date().toLocaleTimeString()],
		max: 10,
		},

	yAxis: {
		type: 'value',
	},
	series: [{
			name: 'a',
			data: [0],
			type: 'line'
		},
		{
			name: 'b',
			data: [0],
			type: 'line'
		}],
	textStyle: {
		color: '#ffffff',
	}
	
};
		

myChart2.showLoading();



var chartDom3 = document.getElementById('ech2');
var myChart3 = echarts.init(chartDom3,'dark');
var option3 ={
				title: {
				     text: '当前搜索总量',
					 textStyle: {
					 	// fontWeight: 'normal',              //标题颜色
					 	color: '#ffffff',
					 	fontSize : 18,
					 		
					 },
				},
				
			  series: [
			    {
				  name: 'total',
			      type: 'gauge',
				  radius:'93%',
				  min: 0,
				  max: 10000,
			      axisLine: {
			        lineStyle: {
			          width: 20,
			          color: [
			            [0.3, '#67e0e3'],
			            [0.7, '#37a2da'],
			            [1, '#fd666d']
			          ]
			        }
			      },
			      pointer: {
			        itemStyle: {
			          color: 'auto'
			        }
			      },
			      axisTick: {
			        distance: -20,
			        length: 5,
			        lineStyle: {
			          color: '#fff',
			          width: 1
			        }
			      },
			      splitLine: {
			        distance: -20,
			        length: 20,
			        lineStyle: {
			          color: '#fff',
			          width: 4
			        }
			      },
			      axisLabel: {
			        color: 'auto',
			        distance: 25,
			        fontSize: 15
			      },
			      detail: {
			        valueAnimation: true,
			        formatter: '{value}',
			        color: 'auto'
			      },
			      data: [
			        {
			          value: 0,
			        }
			      ]
			    },
		]};

myChart3.showLoading();

var colors = ['#da0d3d','#aa55ff','#fcfc17','#E87C25','#26e3ae',
              '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
              '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'];


var chartDom4 = document.getElementById('box4');
var myChart4 = echarts.init(chartDom4,'dark');
var option4 ={
				title: {
					 text: '累计数据统计',
				},
				legend: {},

				tooltip: {
					 trigger: 'axis'
				},
			  xAxis: {
				type: 'category',
				data: ['累计使用用户','累计搜索页面']
			  },
			  yAxis: {
				type: 'value'
			  },
			  series: [
				{
				  data: [0,0],
				  type: 'bar',
				  barWidth : 38,
				  showBackground: true,
				  backgroundStyle: {
					color: 'rgba(180, 180, 180, 0.2)'
				  },
				  
				  itemStyle: {
					color: function(params) {
					//通过返回值的下标一一对应将颜色赋给柱子上
					 return colors[params.dataIndex]
					  },
					}
				},
			  ]
	};

myChart4.showLoading();


var chartDom5 = document.getElementById('box5');
var myChart5 = echarts.init(chartDom5,'dark');
var option5;


option5 = {
	title: {
	     text: '当前搜索内容数',
	},
	legend: {},

	tooltip: {
	     trigger: 'axis'
	},
  xAxis: {
    type: 'category',
    data: [new Date().toLocaleTimeString()],
	max: 10,
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [0],
      type: 'bar',
      showBackground: true,
      backgroundStyle: {
        color: 'rgba(180, 180, 180, 0.2)'
      },
	  
	  itemStyle: {
	    color: function(params) {
	    //通过返回值的下标一一对应将颜色赋给柱子上
	     return colors[params.dataIndex]
	      },
		}
    },
  ]
};


myChart5.showLoading();

function refresh(){
	$.ajax({
	    url: '/streaming/data',
	    type: 'get',
	    dataType: 'json',
	    success: (res) =>{
			console.log(res.user_count)
			var data1 = option1.series[0].data;
			var date1 = option1.xAxis.data;
			
			var data2 = option2.series[0].data;
			var date2 = option2.xAxis.data;
			
			var data5 = option5.series[0].data;
			var date5 = option5.xAxis.data;
					
			if (data1.length > 10){
				data1.shift();
				date1.shift();
				
				data2.shift();
				date2.shift();
				
				data5.shift();
				date5.shift();
			}
					
			data1.push(res.user_count);
			date1.push(new Date().toLocaleTimeString());
			
			data2.push(res.page_count);
			date2.push(new Date().toLocaleTimeString());
			
			
			data5.push(res.word_count);
			date5.push(new Date().toLocaleTimeString());
			
			
			myChart1.hideLoading();
			myChart2.hideLoading();
			myChart3.hideLoading();
			myChart4.hideLoading();
			myChart5.hideLoading();
			
			myChart1.setOption(option1);
			myChart2.setOption(option2);
			myChart5.setOption(option5);

			option3.series[0].data[0].value = res.flow_count
			option3 && myChart3.setOption(option3);
			
			option4.series[0].data[0] = res.user_total
			option4.series[0].data[1] = res.page_total
			option4 && myChart4.setOption(option4);
			
			retable3(res.arr_user);
			retable6(res.arr_page);
						
		}

})};

function retable3(Items){
	$('#tb3-body tbody').empty();
	$('#tb3-header tbody').empty();
	$.each(Items,function (i, item) {
	    str = '<tr>'+
	        '<td>'+item.rank+'</td>'+
	        '<td>'+item.key+'</td>'+
	        '<td>'+item.count+'</td>'+
	        '</tr>'
	
	    $('#tb3-body tbody').append(str);
	    $('#tb6-header tbody').append(str);
	});
}

function retable6(Items){
	$('#tb6-body tbody').empty();
	$('#tb6-header tbody').empty();
	$.each(Items,function (i, item) {
	    str = '<tr>'+
	        '<td>'+item.rank+'</td>'+
	        '<td>'+item.key+'</td>'+
	        '<td>'+item.count+'</td>'+
	        '</tr>'
	
	    $('#tb6-body tbody').append(str);
	    $('#tb6-header tbody').append(str);
	});
}


setInterval(refresh, 3000);