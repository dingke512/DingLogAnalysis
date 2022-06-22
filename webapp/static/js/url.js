var chartDom_u1 = document.getElementById('url_ech1');
var myChart_u1 = echarts.init(chartDom_u1,'dark');
var option_u1;

option_u1 = {
	title: {
	     text: '当日访问页面量',
		 textStyle: {
		 	// fontWeight: 'normal',              //标题颜色
		 	color: '#ffffff',
		 	fontSize : 18,
		 		
		 },
	},
	
	legend: {},
  series: [
    {
      type: 'gauge',
	  radius:'80%',
	  min: 0,
	  max: 1000000,
	  splitNumber: 8,
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
        fontSize: 16
      },
      detail: {
        valueAnimation: true,
        formatter: '{value} 次',
        color: 'auto'
      },
      data: [
        {
          value: url_count,
        }
      ]
    }
  ]
};

option_u1 && myChart_u1.setOption(option_u1);



var chartDom = document.getElementById('ech3');
var myChart = echarts.init(chartDom,'dark');
var option;

var colors = ['#aa55ff','#55aaff','#ff6277','#6495ed','#4f76f5','#19dc19','#F5AD1D'];

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
      data: [1, url_avg],
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


var colors2 = ['#a273ff','#4043ff'];
var chartDom4 = document.getElementById('ech4');
var myChart4 = echarts.init(chartDom4,'dark');
var option4;

option4 = {
  title: [
    {
      text: '页面点击量-总量对比'
    }
  ],
  
    legend: {
      data: [ '点击页面数','搜索总量']
    },
  polar: {
    radius: [30, '80%']
  },
  angleAxis: {
    startAngle: 75
  },
  radiusAxis: {
    type: 'category',
    data: [ '点击页面数','搜索总量']
  },
  tooltip: {},
  series: {
    type: 'bar',
    data: [url_count,total],
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
        text: ((1 - url_count / total)*100).toFixed(2),
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
            text:"重复点击页面占比",
            textAlign:"center",
            fill:"#aaaaff",
            fontSize:20,
            fontWeight:700
            },
     },
    series: [
        {
        name: '重复点击页面占比',
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
			{ value: total-url_count, name: '重复点击页面' },
            { value: url_count, name: '点击单词页面' },

                       
            ]}
 ]};

option5 && myChart5.setOption(option5);