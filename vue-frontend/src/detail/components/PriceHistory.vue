<template>
  <div ref="chartRef" style="max-width: 800px; height: 400px;"></div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import axios from "axios";
import {ElMessage} from "element-plus";
import {useStore} from "vuex";

const chartRef = ref(null);
const priceHistory = ref({
  date:[],
  price:[],
})
let myChart = null;
const initEchart = () =>{
  myChart = echarts.init(chartRef.value);
}
const store = useStore();
const selectProduct = ref(store.getters.selectProduct);
const getHistoryInfo = () =>{
  axios.get('/product/compare-history/', {
    params: {product_id: selectProduct.value.id}
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      priceHistory.value = Response.data;
      updateHistoryInfo();
    }else{
      // 处理后端返回的错误
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    // 处理请求错误，如网络错误或服务器错误
    ElMessage.error(error.response.data.error);
  })
}
const updateHistoryInfo = () =>{
  if(myChart){
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
        data: priceHistory.value.date,// 这里填入日期数据
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
        data: priceHistory.value.price, // 这里填入价格数据
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
  }
}
onMounted(() => {
  initEchart();
  getHistoryInfo();
});
</script>
<style scoped>

</style>