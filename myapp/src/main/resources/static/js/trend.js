
function trend_visual(trend,sum_total,data_x,data_y) {
  var chartDom = document.getElementById('trend');
  var myChart = echarts.init(chartDom, 'dark');
  var option;

  option = {
    title: {
      text: '用户访问整体趋势',
      textStyle: {
        fontSize: 20,
      },
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {},
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    toolbox: {
      feature: {
        saveAsImage: {}
      }
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: trend.date
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '搜索总量',
        type: 'line',
        // stack: 'Total',
        data: trend.total,
      },
      {
        name: '搜索用户数',
        type: 'line',
        // stack: 'Total',
        data: trend.uv,
      },
      {
        name: '搜索页面数',
        type: 'line',
        // stack: 'Total',
        data: trend.url_count,
      },
      {
        name: '搜索关键字总量',
        type: 'line',
        // stack: 'Total',
        data: trend.word_count,
      }
    ]
  };

  option && myChart.setOption(option);

  var chartDom2 = document.getElementById('ech1');
  var myChart2 = echarts.init(chartDom2, 'dark');
  var option2;

  option2 = {
    title: {
      text: '总流量PV',
    },
    legend: {},
    series: [
      {
        type: 'gauge',
        radius: '90%',//仪表盘半径
        min: 0,
        max: 10000,
        splitNumber: 10,


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
          fontSize: 25,
          // offsetCenter: [0, '70%']
          formatter: '{value} 次',
        },
        data: [
          {

            value: sum_total,
          }
        ]
      }
    ]
  };

  option2 && myChart2.setOption(option2);

  var chartDom3 = document.getElementById('ech3');
  var myChart3 = echarts.init(chartDom3, 'dark');
  var option3;
  option3 = {
    title: {
      text: '每次平均搜索字符长度',
    },
    legend: {},
    tooltip: {
      trigger: 'axis'
    },

    xAxis: {
      type: 'category',
      data: data_x
    },
    yAxis: {
      type: 'value',
    },
    series: [{
      data: data_y,
      type: 'bar',
    }]
  };
  option3 && myChart3.setOption(option3);

}