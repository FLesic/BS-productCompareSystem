<script setup>
import {computed, onMounted, ref} from "vue";
import JD from "@/search/assets/JD.png";
import TB from "@/search/assets/TB.png";
import PriceHistory from "@/detail/components/PriceHistory.vue";
import ProductInDifPlat from "@/detail/components/ProductInDifPlat.vue";
import {useStore} from "vuex";
import axios from "axios";
import {ElMessage} from "element-plus";
const store = useStore();
const selectedProduct = ref(store.getters.selectProduct);
const nowUser = ref(store.getters.userID);
let iconSrc = computed(() => {
  const platform = selectedProduct.value?.platform;
  switch (platform) {
    case '京东':
      return JD;
    case '淘宝':
      return TB;
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
onMounted(()=>{
  axios.get('/product/get-collect/',{
    params:{user_id: nowUser.value, product_id:selectedProduct.value.id}
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      // 有收藏信息
      if(Response.data.length > 0){
        collectFlag.value = true;
        lowPriceRemainder.value = (Response.data.isLowReminder === 1);
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
})
const collectProduct = ()=>{
  axios.post('/product/collect/', {
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
  axios.post('/product/cancel-collect/', {
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
  axios.post('/product/update-collect/', {
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
  axios.post('/product/update-collect/', {
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
                  <img :src="selectedProduct.photoURL" alt="Example Image" style="width: 100%; height: auto;" />
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