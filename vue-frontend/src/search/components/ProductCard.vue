<script setup>
import {computed, ref} from "vue";
import JD from '../assets/JD.png'; // 使用 ES6 模块导入图片
import TB from '../assets/TB.png';
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
    case '淘宝':
      return TB;
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
  <el-card style="margin-left:20px; max-width: 250px">
    <template #header>
      <div class="card-header">
        <el-text class="w-250px mb-2" truncated size="large" tag="b">
          {{data.name}}
        </el-text>
      </div>
    </template>
    <img
        :src="data.photoURL"
        style="width: 100%;"
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
      <img
          :src="iconSrc"
          style="width: 9%; margin-right: 8px"
          alt="其他平台："
      />
      <el-text class="w-250px mb-2" truncated size="large">
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
</style>