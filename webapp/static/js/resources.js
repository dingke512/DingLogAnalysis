var chartDom = document.getElementById('ch1');
var myChart = echarts.init(chartDom);
var option;

option = {
	title: {
	     text: '内存使用率',
		  x:'center',
	},
	tooltip: {
		 trigger: 'axis'
	},
	legend: {},
  xAxis: {
    type: 'category',
    data: [new Date().toLocaleTimeString()]
  },
  yAxis: {
    type: 'value',
	max : 100,
  },
  series: [
    {
      data: [0],
      type: 'line',
    }
  ]
};

option && myChart.setOption(option);


var chartDom1 = document.getElementById('ch2');
var myChart1 = echarts.init(chartDom1);
var option1;

option1 = {
	title: {
	     text: 'CPU使用率',
		 x:'center',
	},
	tooltip: {
		 trigger: 'axis'
	},
	legend: {},
  xAxis: {
    type: 'category',
    data: [new Date().toLocaleTimeString()]
  },
  yAxis: {
    type: 'value',
	max : 100,
  },
  series: [
    {
      data: [0],
      type: 'line',
    }
  ]
};

option1 && myChart1.setOption(option1);


var chartDom2 = document.getElementById('ch3');
var myChart2 = echarts.init(chartDom2);
var option2;

option2 = {
	title: {
	     text: '内存可使用大小',
		 x:'center',
	},
	legend: {},
	tooltip: {
		 trigger: 'axis'
	},
  series: [
	  
	{
      type: 'gauge',
	  max: 16,
	  splitNumber: 8,
      progress: {
        show: true,
        width: 18
      },
      axisLine: {
        lineStyle: {
          width: 18
        }
      },
      axisTick: {
        show: false
      },
      splitLine: {
        length: 15,
        lineStyle: {
          width: 2,
          color: '#999'
        }
      },
      axisLabel: {
        distance: 25,
        color: '#999',
        fontSize: 20
      },
      anchor: {
        show: true,
        showAbove: true,
        size: 25,
        itemStyle: {
          borderWidth: 10
        }
      },
      title: {
        show: false
      },
      detail: {
        valueAnimation: true,
        fontSize: 30,
        offsetCenter: [0, '70%']
      },
      data: [
        {
          value: 0
        }
      ]
    }
  ]
};

option2 && myChart2.setOption(option2);


function refresh(){
	$.ajax({
		url: '/resource/data',
		type: 'get',
		dataType: 'json',
		success: (res) =>{
			console.log(res.memory_use_rate)
			var data_y = option.series[0].data;
			var data_x = option.xAxis.data;	
			
			var data_y1 = option1.series[0].data;
			var data_x1 = option1.xAxis.data;	
			
			if (data_y.length > 20){
				data_y.shift();
				data_x.shift();
				
				data_y1.shift();
				data_x1.shift();
				
			}
					
			data_y.push(res.memory_use_rate);
			data_x.push(new Date().toLocaleTimeString());
			
			data_y1.push(res.cpu_use_rate);
			data_x1.push(new Date().toLocaleTimeString());
			
			option2.series[0].data[0].value = res.memory_free
			option && myChart.setOption(option);
			option1 && myChart1.setOption(option1);
			option2 && myChart2.setOption(option2);
		}
	})
};

setInterval(refresh, 3000);