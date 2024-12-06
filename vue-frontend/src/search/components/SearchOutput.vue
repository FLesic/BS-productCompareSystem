<script setup>
import ProductCard from "@/search/components/ProductCard.vue";
import {useStore} from "vuex";
import {computed} from "vue";
const store = useStore()
const products = computed(() => store.getters.productList);
// id:1,
// name:"科尔沁 手撕风干牛肉干 原味618g量贩装 健身代餐高蛋白解馋休闲零食",
// price:99.0,
// platform:"京东",
// shop:"科尔钦京东自营店",
// photoURL:"https://img14.360buyimg.com/n0/jfs/t1/161423/27/49783/117759/6732eefcFd217e8dc/df11b028d4a11365.jpg.avif",
// productURL:"https://item.jd.com/100108593346.html#crumb-wrap",
// detail:"none"
</script>

<template>
  <div style="display: flex;flex-wrap: wrap; justify-content: start;">
    <ProductCard v-for="product in products" :key="product.product_id" :data="product"></ProductCard>
  </div>
</template>

<style scoped>

</style>