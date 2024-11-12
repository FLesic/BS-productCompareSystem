<script setup>
import {computed, ref} from "vue";
import JD from '../assets/JD.png'; // 使用 ES6 模块导入图片
import TB from '../assets/TB.png';
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
</script>

<template>
  <el-card style="margin-left:20px; max-width: 250px">
    <template #header>
      <div class="card-header">
        <el-text class="w-250px mb-2" truncated size="large" tag="b">
          {{data.product_name}}
        </el-text>
      </div>
    </template>
    <img
        :src="data.photo_url"
        style="width: 100%;"
    />
    <p></p>
    <div>
      <span style="font-size:25px">{{data.price}}￥</span>
      <el-button type="danger" size="large" style="margin-left:40px;">
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

</style>