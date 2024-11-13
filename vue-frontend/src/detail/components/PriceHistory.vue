<template>
  <div ref="chartRef" style="max-width: 800px; height: 400px;"></div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';

const chartRef = ref(null);
const testPriceHistory = ref({
  date:['11月1日', '11月2日', '11月3日', '11月4日', '11月5日'],
  price:[120, 132, 101, 134, 90],
})
onMounted(() => {
  const myChart = echarts.init(chartRef.value);
  const option = {
    title: {
      text: '商品历史价格趋势图',
      color:'#FFFFFF'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['价格']
    },
    xAxis: {
      type: 'category',
      data: testPriceHistory.value.date,// 这里填入日期数据
      // 设置 X 轴线的颜色
      axisLine: {
        lineStyle: {
          color: '#FFFFFF' // 轴线颜色
        }
      },
      // 设置x轴标签颜色
      axisLabel: {
        color: '#FFFFFF' // 标签颜色
      }
    },
    yAxis: {
      type: 'value',
      scale: true,
      color: '#FFFFFF',
      // Y轴标签颜色
      axisLabel: {
        color: '#FFFFFF',
        formatter: '￥{value}'
      }
    },
    series: [{
      name: '价格',
      type: 'line',
      data: testPriceHistory.value.price, // 这里填入价格数据
      lineStyle: {
        color: '#FFFFFF',
      },
      markPoint: {
        data: [
          { type: 'max', name: '最大值' },
          { type: 'min', name: '最小值' }
        ],
        color: '#FFFFFF'
      },
      markLine: {
        data: [
          { type: 'average', name: '平均值' }
        ],
        itemStyle: {
          color: '#FFFFFF' // 标记点的颜色
        }
      }
    }]
  };
  myChart.setOption(option);
});
</script>
<style scoped>

</style>