<script setup>
import {ref} from "vue";
import ProductCard from "@/search/components/ProductCard.vue";

const productsData = {
  'JD': [
    {
      product_id:1,
      product_name:"科尔沁 手撕风干牛肉干 原味618g量贩装 健身代餐高蛋白解馋休闲零食",
      price:99.0,
      platform:"京东",
      shop:"科尔钦京东自营店",
      photo_url:"https://img14.360buyimg.com/n0/jfs/t1/161423/27/49783/117759/6732eefcFd217e8dc/df11b028d4a11365.jpg.avif",
      product_url:"https://item.jd.com/100108593346.html#crumb-wrap",
      detail:"none"
    },
    {
      product_id:2,
      product_name:"科尔沁 手撕风干牛肉干 原味618g量贩装 健身代餐高蛋白解馋休闲零食",
      price:199.0,
      platform:"京东",
      shop:"科尔钦京东自营店",
      photo_url:"https://img14.360buyimg.com/n0/jfs/t1/161423/27/49783/117759/6732eefcFd217e8dc/df11b028d4a11365.jpg.avif",
      product_url:"https://item.jd.com/100108593346.html#crumb-wrap",
      detail:"none"
    },
    {
      product_id:2,
      product_name:"科尔沁 手撕风干牛肉干 原味618g量贩装 健身代餐高蛋白解馋休闲零食",
      price:199.0,
      platform:"京东",
      shop:"科尔钦京东自营店",
      photo_url:"https://img14.360buyimg.com/n0/jfs/t1/161423/27/49783/117759/6732eefcFd217e8dc/df11b028d4a11365.jpg.avif",
      product_url:"https://item.jd.com/100108593346.html#crumb-wrap",
      detail:"none"
    },
  ],
  'TB': [
    {
      product_id:3,
      product_name:"科尔沁 酱牛肉五香味618g 量贩装 0添加防腐剂 熟食菜肴速食解馋下酒菜",
      price:119.0,
      platform:"淘宝",
      shop:"科尔钦京东自营店",
      photo_url:"https://img14.360buyimg.com/n0/jfs/t1/175969/24/51540/102980/6732ecc1F269a7b39/c19695e86051e7c2.jpg.avif",
      product_url:"https://item.jd.com/100122413511.html#crumb-wrap",
      detail:"none"
    },
  ],
};
const currentPlatForm = ref('JD');
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
    </el-menu>
    <div v-for="product in productsData[currentPlatForm]" :key="product.product_id" >
      <div class="product_item">
        <div style="flex: 1;">
          <img :src="product.photo_url"  style="width: 50%; height: auto;" />
        </div>
        <div style="flex: 3; padding-left: 10px;">
          <p style="text-align: left;">
            <el-link href="./detail" style="color: #181818;font-size:large" >{{ product.product_name }}</el-link>
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
                  v-if="currentPlatForm === 'TB'"
                  src="../assets/TB.png"
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
</style>