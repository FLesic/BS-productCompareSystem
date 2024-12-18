<script setup>
import {ref} from "vue";
import UserInfo from "@/search/components/UserInfo.vue";
import ProductDetail from "@/detail/components/ProductDetail.vue";
</script>

<template>

  <div class="common-layout">
    <el-container >
      <!-- 自定义样式的头部 -->
      <el-header height=100px class = "common-header">
        <h2 class = "cool-font" data-shadow='PriceCompare'>PriceCompare</h2>
        <UserInfo></UserInfo>
      </el-header>
      <!-- 主要内容区域 -->
      <el-main class="main-content">
        <ProductDetail></ProductDetail>
      </el-main>
    </el-container>
  </div>

</template>

<style>
.common-layout {
  height: 100vh; /* 容器高度为视口高度 */
  display: flex;
  flex-direction: column;
}

.common-header {
  //background-color: #409eff; /* Element UI 主题色 */
  color: white;
  text-align: center;
  line-height: 70px; /* 与Element UI el-header的高度保持一致 */
  font-size: 50px;
  font-weight: bold;

}

.main-content {
  //flex: 1; /* 主要内容区域占据剩余空间 */
  //padding: 20px;
}

*, *:before, *:after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  position: relative;
}
body {
  text-align: center;
  background-color: hsla(230,40%,50%,1);
}
body:before {
  content: '';
  display: inline-block;
  vertical-align: middle;
  font-size: 0;
  height: 100%;
}
@font-face {
  font-family: 'Righteous';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/righteous/v17/1cXxaUPXBpj2rGoU7C9WhnGFucE.woff2) format('woff2');
  unicode-range: U+0100-02BA, U+02BD-02C5, U+02C7-02CC, U+02CE-02D7, U+02DD-02FF, U+0304, U+0308, U+0329, U+1D00-1DBF, U+1E00-1E9F, U+1EF2-1EFF, U+2020, U+20A0-20AB, U+20AD-20C0, U+2113, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: 'Righteous';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/righteous/v17/1cXxaUPXBpj2rGoU7C9WiHGF.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+0304, U+0308, U+0329, U+2000-206F, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}

.cool-font {
  display: inline-block;
  color: white;
  font-family: 'Righteous', serif;
  text-shadow: .03em .03em 0 hsla(230,40%,50%,1);
}
.cool-font:after {
  content: attr(data-shadow);
  position: absolute;
  top: .06em; left: .06em;
  z-index: -1;
  text-shadow: none;
  background-image:
      linear-gradient(
          45deg,
          transparent 45%,
          hsla(48,20%,90%,1) 45%,
          hsla(48,20%,90%,1) 55%,
          transparent 0
      );
  background-size: .05em .05em;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;

  animation: shad-anim 15s linear infinite;
}

@keyframes shad-anim {
  0% {background-position: 0 0}
  0% {background-position: 100% -100%}
}
@media (max-width: 500px) {
  .common-header {
    //background-color: #409eff; /* Element UI 主题色 */
    color: white;
    text-align: center;
    line-height: 60px; /* 与Element UI el-header的高度保持一致 */
    font-size: 60px;
    font-weight: bold;
  }
  .cool-font {
    display: inline-block;
    font-size: 50px;
    color: white;
    font-family: 'Righteous', serif;
    text-shadow: .03em .03em 0 hsla(230,40%,50%,1);
  }
}
</style>
