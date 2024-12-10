<script setup>
import {computed, ref} from "vue";
import JD from '../assets/JD.png'; // 使用 ES6 模块导入图片
import Amazon from '../assets/Amazon.png';
import DD from '../assets/DD.png';
import SN from '../assets/SN.png';
import axios from "axios";
import {useStore} from "vuex";
const props = defineProps({
  data: Object
});
const iconSrc = computed(() => {
  const platform = props.data.platform;
  switch (platform) {
    case '京东':
      return JD;
    case '亚马逊':
      return Amazon;
    case '当当':
      return DD;
    case '苏宁':
      return SN;
    default:
      return null;
  }
})
const store = useStore()
const handleCheckDetail = () =>{
  store.dispatch('setSelectProduct', props.data);
  window.location.href = '/detail';
}
</script>

<template>
  <el-card style="margin-left:22px; margin-bottom:10px; max-width: 270px">
    <template #header>
      <div class="card-header">
        <el-text class="w-250px mb-2" truncated size="large" tag="b">
          {{data.name}}
        </el-text>
      </div>
    </template>
    <img
        :src="data.photoURL"
        class="product-image"
    />
    <p></p>
    <div>
      <span style="font-size:25px;color: #e23a3a;">￥{{data.price}}</span>
      <el-button type="danger" size="default" style="margin-left:30px;margin-bottom: 5px"
                 @click="handleCheckDetail">
        商品详情
      </el-button>
    </div>
    <template #footer>
      <div style="text-align: left;">
      <el-text class="w-250px mb-2" truncated size="large">
        <img
            :src="iconSrc"
            style="width: 7%; margin-right: 8px;"
            alt="其他平台："
        />
        {{data.shop}}
      </el-text>
      </div>
    </template>
  </el-card>
</template>

<style scoped>
a {
  text-decoration: none;
  color: white; /* 替换为你希望的颜色 */
}

/* 移除悬停、点击和访问过状态下的下划线 */
a:hover,
a:active,
a:visited {
  text-decoration: none;
}
.product-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
}
</style>