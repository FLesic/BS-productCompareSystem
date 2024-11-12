<script setup>
import {ref} from "vue";
import {Search, ShoppingCart} from "@element-plus/icons-vue";

const product_name = ref(null);
</script>

<template>
  <el-input v-model="product_name" style="max-width: 50vw" placeholder="请输入商品">
    <template #append>
      <el-button :icon="Search"/>
    </template>
  </el-input>
</template>

<style scoped>

</style>