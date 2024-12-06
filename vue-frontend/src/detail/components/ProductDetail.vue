<script setup>
import {computed, onMounted, ref} from "vue";
import JD from "@/search/assets/JD.png";
import TB from "@/search/assets/TB.png";
import PriceHistory from "@/detail/components/PriceHistory.vue";
import ProductInDifPlat from "@/detail/components/ProductInDifPlat.vue";
import {useStore} from "vuex";
const store = useStore();
const selectedProduct = ref(store.getters.selectProduct);
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
const testProduct = ref({
  product_id:1,
  product_name:"科尔沁 手撕风干牛肉干 原味618g量贩装 健身代餐高蛋白解馋休闲零食",
  price:99.0,
  platform:"京东",
  shop:"科尔钦京东自营店",
  photo_url:"https://img14.360buyimg.com/n0/jfs/t1/161423/27/49783/117759/6732eefcFd217e8dc/df11b028d4a11365.jpg.avif",
  product_url:"https://item.jd.com/100108593346.html#crumb-wrap",
  detail:"这牛肉干是真的好吃啊"
});
const historyPrice = ref([
  {
    data: String,
    price: 0.0
  }
])
const collectFlag = ref(false);
const lowPriceRemainder = ref(false);
const collectProduct = ()=>{
  collectFlag.value = true;
}
const cancelCollectProduct = ()=>{
  collectFlag.value = false;
}
const setLowReminder = ()=>{
  lowPriceRemainder.value = true;
}
const cancelSetLowReminder = ()=>{
  lowPriceRemainder.value = false;
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