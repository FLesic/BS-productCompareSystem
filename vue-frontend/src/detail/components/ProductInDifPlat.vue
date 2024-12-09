<script setup>
import {onMounted, ref} from "vue";
import {useStore} from "vuex";
import axios from "axios";
import {ElMessage} from "element-plus";

const productsData = ref({
  'JD': [
  ],
  'DD': [
  ],
  'Amazon': [

  ],
});
const currentPlatForm = ref('JD');
const store = useStore();
const selectProduct = ref(store.getters.selectProduct);
const handleCheckDetail = (product)=>{
  store.dispatch('setSelectProduct',product);
  window.location.href = '/detail';
}
const pickProductToDif = (product)=> {
  switch(product.platform)
  {
    case "京东":
      productsData.value['JD'].push(product);
      break;
    case "当当":
      productsData.value['DD'].push(product);
      break;
    case "亚马逊":
      productsData.value['Amazon'].push(product);
      break;
    default:
      break;
  }
}
onMounted(()=>{
  axios.get('/product/compare-platform/', {
    params: {product_id: selectProduct.value.id}
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      productsData.value['JD'] = [];
      productsData.value['DD'] = [];
      productsData.value['Amazon'] = [];
      for(let i = 0; i < Response.data.length; i++){
        pickProductToDif(Response.data[i]);
      }
    }else{
      // 处理后端返回的错误
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    // 处理请求错误，如网络错误或服务器错误
    ElMessage.error(error.response.data.error);
  })
})
</script>

<template>
  <el-card>
    <el-menu
        ellipsis
        class="el-menu-popper-demo"
        mode="horizontal"
        :popper-offset="16"
        style="max-width: 1200px;position: relative;"
        default-active="1"
    >
      <el-text size="large" tag="b">不同平台比价：</el-text>
      <el-menu-item index="1" @click="()=>{currentPlatForm = 'JD'}">京东</el-menu-item>
      <el-menu-item index="2" @click="()=>{currentPlatForm = 'TB'}">淘宝</el-menu-item>
      <el-menu-item index="3" @click="()=>{currentPlatForm = 'Amazon'}">亚马逊</el-menu-item>
      <el-menu-item index="4" @click="()=>{currentPlatForm = 'DD'}">当当</el-menu-item>
    </el-menu>
    <div v-for="product in productsData[currentPlatForm]" :key="product.id" >
      <div class="product_item">
        <div style="flex: 1;">
          <img :src="product.photoURL"  style="width: 45%; height: auto;" />
        </div>
        <div style="flex: 3; padding-left: 10px;">
          <p style="text-align: left;">
            <el-link style="color: #181818;font-size:large" @click="handleCheckDetail(product)">
              {{ product.name }}
            </el-link>
          </p>

          <div style="display:flex; justify-content: start;text-align: left;font-size: smaller; margin-top:50px">
            <div style="flex: 1;">
              <span style="text-align: left;color:#e23a3a;font-size: 25px; margin-right:20px">
                ￥{{product.price}}
              </span>
              <img
                  v-if="currentPlatForm === 'JD'"
                  src="../assets/JD.png"
                  style="width: 2%; margin-right: 8px"
                  alt="其他平台："
              />
              <img
                  v-if="currentPlatForm === 'DD'"
                  src="../assets/DD.png"
                  style="width: 2%; margin-right: 8px"
                  alt="其他平台："
              />
              <img
                  v-if="currentPlatForm === 'Amazon'"
                  src="../assets/Amazon.png"
                  style="width: 2%; margin-right: 8px"
                  alt="其他平台："
              />
              <span style="color: #666666; font-size: 15px">{{product.shop}}</span>
            </div>
            <el-button type="danger" style="display:flex; justify-content: flex-end; margin-left: auto">
                <a v-bind:href="product.product_url">去看看</a>
            </el-button>
          </div>
        </div>
      </div>
      <el-divider></el-divider>
    </div>
  </el-card>
</template>

<style scoped>
.product_item {
  display: flex;
  align-items: center;
}
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