<script setup>

import ProductCard from "@/search/components/ProductCard.vue";
const products = [
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
    product_name:"科尔沁 酱牛肉五香味618g 量贩装 0添加防腐剂 熟食菜肴速食解馋下酒菜",
    price:119.0,
    platform:"京东",
    shop:"科尔钦京东自营店",
    photo_url:"https://img14.360buyimg.com/n0/jfs/t1/175969/24/51540/102980/6732ecc1F269a7b39/c19695e86051e7c2.jpg.avif",
    product_url:"https://item.jd.com/100122413511.html#crumb-wrap",
    detail:"none"
  },
  {
    product_id:3,
    product_name:"科尔沁 酱牛肉五香味618g 量贩装 0添加防腐剂 熟食菜肴速食解馋下酒菜",
    price:119.0,
    platform:"淘宝",
    shop:"科尔钦京东自营店",
    photo_url:"https://img14.360buyimg.com/n0/jfs/t1/175969/24/51540/102980/6732ecc1F269a7b39/c19695e86051e7c2.jpg.avif",
    product_url:"https://item.jd.com/100122413511.html#crumb-wrap",
    detail:"none"
  }
];
</script>

<template>
  <div style="display: flex;flex-wrap: wrap; justify-content: start;">
    <ProductCard v-for="product in products" :key="product.product_id" :data="product"></ProductCard>
  </div>
</template>

<style scoped>

</style>