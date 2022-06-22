var chartDom = document.getElementById('ech3');
var myChart = echarts.init(chartDom,'dark');
var option;

var colors = ['#aa55ff','#55aaff','#6495ed','#4f76f5','#19dc19','#F5AD1D','#00ff7f'];

option = {
	title: {
	     text: '用户平均访问',
	},
	legend: {},

	tooltip: {
	     trigger: 'axis'
	},
  xAxis: {
    type: 'category',
    data: ['用户搜索最小', '平均用户搜索']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [1, user_avg],
      type: 'bar',
      showBackground: true,
      backgroundStyle: {
        color: 'rgba(180, 180, 180, 0.2)'
      },
	  
	  itemStyle: {
	    color: function(params) {
	    //通过返回值的下标一一对应将颜色赋给柱子上
	     return colors[params.dataIndex];
	      },
		}

    },

  ]
};

option && myChart.setOption(option);


var chartDom2 = document.getElementById('ech2');
var myChart2 = echarts.init(chartDom2,'dark');
var option2;

option2 = {
	title: {
	     text: '当日用户访问量(UV)',
	},
		legend: {},
  series: [
    {
      type: 'gauge', 
	  radius: '80%',//仪表盘半径
	  min: 0,
	  max: 1000000,
	  splitNumber: 5,
	  

	  
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
        fontSize: 13
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
        fontSize: 20,
        // offsetCenter: [0, '70%']
      },
      data: [
        {
		
          value: user_count,
        }
      ]
    }
  ]
};

option2 && myChart2.setOption(option2);


var colors2 = ['#aa55ff','#5555ff','#6495ed','#4f76f5','#19dc19','#F5AD1D'];
var chartDom4 = document.getElementById('ech4');
var myChart4 = echarts.init(chartDom4,'dark');
var option4;

option4 = {
  title: [
    {
      text: '用户-总量对比'
    }
  ],
  
    legend: {
      data: [ '搜索用户数','搜索总量']
    },
  polar: {
    radius: [30, '80%']
  },
  angleAxis: {
    // max: 10000,
    startAngle: 75
  },
  radiusAxis: {
    type: 'category',
    data: [ '搜索用户数','搜索总量']
  },
  tooltip: {},
  series: {
    type: 'bar',
    data: [user_count,total],
    coordinateSystem: 'polar',
	barWidth : 38,
	itemStyle: {
	    color: function(params) {
	    //通过返回值的下标一一对应将颜色赋给柱子上
	     return colors2[params.dataIndex];
	      },
		}		
  }
};

option4 && myChart4.setOption(option4);



var chartDom5 = document.getElementById('ech5');
var myChart5 = echarts.init(chartDom5,'dark');
var option5;

 var option5 = {
    tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    color:["#27D9C8","#D8D8D8"],
    title:{
        text: ((1 - user_count / total)*100).toFixed(2),
        left:"center",
        top:"50%",
        textStyle:{
			color:"#27D9C8",
			fontSize:36,
			align:"center"
         },
    },
     graphic:{
        type:"text",
        left:"center",
        top:"40%",
        style:{
            text:"重复搜索用户占比",
            textAlign:"center",
            fill:"#aaaaff",
            fontSize:20,
            fontWeight:700
            },
     },
    series: [
        {
        name: '重复搜索用户占比',
        type: 'pie',
        radius: ['65%', '70%'],
        avoidLabelOverlap: false,
        label: {
			normal: {
			show: false,
			position: 'center'
			},
         },
                    
        data: [
			{ value: total-user_count, name: '重复搜索用户' },
            { value: user_count, name: '单次搜索用户' },

                       
            ]}
 ]};

option5 && myChart5.setOption(option5);