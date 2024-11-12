<script setup>
import {ref} from "vue";
import {ArrowDown, ArrowRight, Avatar, Star, SwitchButton, User} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const userInfo = ref({
  user_name: String,
  email: String
})
const testUserName = "任飞扬"
</script>

<template>
  <el-dropdown class="top-right-container" trigger="hover">
    <el-avatar
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
    />
  <template #dropdown>
    <el-dropdown-menu>
      <!--TODO 具体逻辑-->
      <el-text class="mx-2" size="large" style="text-align: center;" tag="b">欢迎，{{testUserName}}</el-text>
      <div style="margin-bottom: 10px;"></div>
      <el-dropdown-item @click="()=>{ElMessage.success('success')}">
        <el-icon style="margin-left:15px"><User /></el-icon>
        <span style="margin-left:8px; margin-right: 100px">个人中心</span>
        <el-icon><ArrowRight /></el-icon>
      </el-dropdown-item>
      <el-dropdown-item @click="()=>{ElMessage.success('success')}">
        <el-icon style="margin-left:15px"><Star /></el-icon>
        <span style="margin-left:8px; margin-right: 100px">收藏商品</span>
        <el-icon><ArrowRight /></el-icon>
      </el-dropdown-item>
      <el-dropdown-item>
        <el-icon style="margin-left:15px"><SwitchButton /></el-icon>
        <a href="/" style="margin-left:8px; margin-right: 100px">退出登录</a>
      </el-dropdown-item>
    </el-dropdown-menu>
  </template>
  </el-dropdown>
</template>

<style scoped>
.top-right-container {
  position: fixed;
  top: 4vh; /* 根据需要调整 */
  right: 9vw; /* 根据需要调整 */
}
a {
  text-decoration: none;
  color: #606266; /* 替换为你希望的颜色 */
}

/* 移除悬停、点击和访问过状态下的下划线 */
a:hover,
a:active,
a:visited {
  text-decoration: none;
}
</style>