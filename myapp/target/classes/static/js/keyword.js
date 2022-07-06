function visual_keyword(word_count,key_arr,word_avg,total,avg_length) {
    var chartDom2 = document.getElementById('ech2');
    var myChart2 = echarts.init(chartDom2, 'dark');
    var option2;

    option2 = {
        title: {
            text: '当日搜索关键词量',
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

                        value: word_count,
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
            text: 'Textrank关键词提取',
        },
        tooltip: {
            show: true,
        },
        series: [{
            type: 'wordCloud',
            gridSize: 1,
            sizeRange: [17, 60],
            rotationRange: [-45, 0, 45, 90],
            // maskImage: maskImage,
            textStyle: {
                fontFamily: '微软雅黑',
                color: function () {
                    return 'rgb(' + [
                        Math.round(Math.random() * 250),
                        Math.round(Math.random() * 250),
                        Math.round(Math.random() * 250)
                    ].join(',') + ')';
                }
            },

            left: 'center',
            top: 'center',
            right: null,
            bottom: null,
            data: key_arr,
        }]
    };
    option3 && myChart3.setOption(option3);


    var chartDom4 = document.getElementById('ech4');
    var myChart4 = echarts.init(chartDom4, 'dark');
    var option4;

    var colors = ['#aa55ff', '#55aaff', '#6495ed', '#4f76f5', '#19dc19', '#F5AD1D', '#00ff7f'];

    option4 = {
        title: {
            text: '单个词搜索量',
        },
        legend: {},

        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            data: ['最小值', '平均值']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: [1, word_avg],
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                },

                itemStyle: {
                    color: function (params) {
                        //通过返回值的下标一一对应将颜色赋给柱子上
                        return colors[params.dataIndex];
                    },
                }

            },

            //   {
            //       data: [1,word_avg],
            //       type: 'line',

            //     }
        ]
    };

    option4 && myChart4.setOption(option4);


    var chartDom5 = document.getElementById('ech5');
    var myChart5 = echarts.init(chartDom5, 'dark');
    var option5;

    var option5 = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        color: ["#27D9C8", "#D8D8D8"],
        title: {
            text: ((1 - word_count / total) * 100).toFixed(2),
            left: "center",
            top: "50%",
            textStyle: {
                color: "#27D9C8",
                fontSize: 36,
                align: "center"
            },
        },
        graphic: {
            type: "text",
            left: "center",
            top: "40%",
            style: {
                text: "重复搜索占比",
                textAlign: "center",
                fill: "#aaaaff",
                fontSize: 20,
                fontWeight: 700
            },
        },
        series: [
            {
                name: '重复搜索占比',
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
                    {value: total - word_count, name: '重复搜索词'},
                    {value: word_count, name: '首次搜索词'},


                ]
            }
        ]
    };

    option5 && myChart5.setOption(option5);


    var chartDom6 = document.getElementById('ech6');
    var myChart6 = echarts.init(chartDom6, 'dark');
    var option6;

    //初始化数据

    var option6 = {
        title: {
            text: '当日搜索字符平均长度',
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            }
        },
        yAxis: {
            type: 'category',
            data: ['长度'],
            splitLine: {show: false},
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            offset: 10,
            nameTextStyle: {
                fontSize: 15
            }
        },
        series: [
            {
                name: '数量',
                type: 'bar',
                data: [avg_length],
                barWidth: 14,
                barGap: 10,
                smooth: true,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        offset: [5, -2],
                        textStyle: {
                            color: '#F68300',
                            fontSize: 13
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                        barBorderRadius: 7
                    },
                    normal: {
                        barBorderRadius: 7,
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 1, 0,
                            [
                                {offset: 0, color: '#3977E6'},
                                {offset: 1, color: '#37BBF8'}

                            ]
                        )
                    }
                }
            }
        ]
    };
    myChart6.setOption(option6);
}