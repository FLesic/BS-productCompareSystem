<script setup>
import {computed, onMounted, onUnmounted, ref} from "vue";
import JD from "@/search/assets/JD.png";
import DD from "@/search/assets/DD.png";
import Amazon from "@/search/assets/Amazon.png"
import SN from "@/search/assets/SN.png"
import PriceHistory from "@/detail/components/PriceHistory.vue";
import ProductInDifPlat from "@/detail/components/ProductInDifPlat.vue";
import {useStore} from "vuex";
import axios from "axios";
import {ElMessage} from "element-plus";
const store = useStore();
const selectedProduct = ref(store.getters.selectProduct);
const nowUser = ref(store.getters.userID);
import bus from '../../eventbus/event.js';
let iconSrc = computed(() => {
  const platform = selectedProduct.value?.platform;
  switch (platform) {
    case '京东':
      return JD;
    case '当当':
      return DD;
    case '亚马逊':
      return Amazon;
    case '苏宁':
      return SN;
    default:
      return null;
  }
});
const historyPrice = ref([
  {
    data: String,
    price: 0.0
  }
])
const collectFlag = ref(false);
const lowPriceRemainder = ref(false);
const getCollectInfo = ()=>{
  console.log("触发一次")
  axios.get('/collect/get/',{
    params:{user_id: nowUser.value, product_id:selectedProduct.value.id}
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      // 有收藏信息
      if(Response.data.length > 0){
        collectFlag.value = true;
        lowPriceRemainder.value = (Response.data[0].isLowReminder === 1);
      }
      else {
        collectFlag.value = false;
        lowPriceRemainder.value = false;
      }
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}
onMounted(()=>{
  getCollectInfo();
  bus.on('collect-info-update', getCollectInfo);
})
onUnmounted(() => {
  bus.off('collect-info-update', getCollectInfo);
});
const collectProduct = ()=>{
  axios.post('/collect/set/', {
    user_id: nowUser.value,
    product_id: selectedProduct.value.id,
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      ElMessage.success("收藏成功");
      collectFlag.value = true;
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}
const cancelCollectProduct = ()=>{
  axios.post('/collect/cancel/', {
    user_id: nowUser.value,
    product_id: selectedProduct.value.id,
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      ElMessage.success("取消收藏成功");
      collectFlag.value = false;
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}
const setLowReminder = ()=>{
  axios.post('/collect/update/', {
    user_id: nowUser.value,
    product_id: selectedProduct.value.id,
    isLowReminder: 1,
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      ElMessage.success("降价提醒设置成功");
      lowPriceRemainder.value = true;
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}
const cancelSetLowReminder = ()=>{
  axios.post('/collect/update/', {
    user_id: nowUser.value,
    product_id: selectedProduct.value.id,
    isLowReminder: 0,
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      ElMessage.success("降价提醒取消成功");
      lowPriceRemainder.value = false;
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}

</script>

<template>
      <el-container>
        <el-header style="display: flex; justify-content: space-between; align-items: center;height: 300px">
          <!-- 左侧内容 -->
          <el-card style="max-width: 600px; max-height: 600px;">
            <div style="flex: 1; display: flex; align-items: center;">
                <div style="flex: 2;">
                  <img :src="selectedProduct.photoURL" alt="Example Image" style="width: 80%; height: auto;" />
                </div>
                <!-- 右侧部分：详细信息 -->
                <div style="flex: 2; padding-left: 10px;">
                  <p style="text-align: left;">
                    <img
                        :src="iconSrc"
                        style="width: 6%; margin-right: 8px"
                        alt="其他平台："
                    />
                    {{ selectedProduct.name }}
                  </p>
                  <p style="text-align: left;font-size: smaller; background-color: #f6f6f6;">
                    <span style="color: #999999">店铺来源：</span>
                    <span style="color: #666666">{{selectedProduct.shop}}</span>
                  </p>
                  <p style="text-align: left;color:#e23a3a;font-size: 25px">
                    ￥{{selectedProduct.price}}
                    <el-button v-if="!collectFlag" type="warning" style="margin-top: 10px; margin-bottom: 10px"
                               @click="collectProduct">
                      收藏商品
                    </el-button>
                    <el-button v-if="collectFlag && !lowPriceRemainder" type="primary" style="margin-top: 10px; margin-bottom: 10px"
                               @click="setLowReminder">
                      降价提醒
                    </el-button>
                    <el-button v-if="collectFlag && lowPriceRemainder" type="primary" style="margin-top: 10px; margin-bottom: 10px"
                               @click="cancelSetLowReminder">
                      取消提醒
                    </el-button>
                    <el-button v-if="collectFlag" type="warning" style="margin-top: 10px; margin-bottom: 10px"
                               @click="cancelCollectProduct">
                      取消收藏
                    </el-button>
                  </p>
                  <p style="text-align: left;font-size: smaller;">详细信息：{{selectedProduct.detail}}</p>
                  <el-divider></el-divider>
                  <el-button type="danger" style="margin-top: 10px;">
                    <a v-bind:href="selectedProduct.productURL">去看看</a>
                  </el-button>

                </div>
            </div>
          </el-card>
          <!-- 右侧内容 -->
          <div style="flex: 1; text-align: right;">
            <PriceHistory></PriceHistory>
          </div>
        </el-header>
        <el-main>
          <ProductInDifPlat></ProductInDifPlat>
        </el-main>
      </el-container>
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