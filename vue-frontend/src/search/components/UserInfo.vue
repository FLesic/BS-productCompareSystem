<template>
  <el-dropdown class="top-right-container" trigger="hover">
    <el-avatar
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
    />
    <template #dropdown>
      <el-dropdown-menu>
        <!--TODO 具体逻辑-->
        <el-text class="mx-2" size="large" style="text-align: center;" tag="b">欢迎，{{ userInfo.user_name }}</el-text>
        <div style="margin-bottom: 10px;"></div>
        <el-dropdown-item @click="UserInfoVisible = true">
          <el-icon style="margin-left:15px">
            <User/>
          </el-icon>
          <span style="margin-left:8px; margin-right: 100px">个人中心</span>
          <el-icon>
            <ArrowRight/>
          </el-icon>
        </el-dropdown-item>
        <el-dropdown-item @click="openCollectInfo">
          <el-icon style="margin-left:15px"><Star /></el-icon>
          <span style="margin-left:8px; margin-right: 100px">收藏商品</span>
          <el-icon><ArrowRight /></el-icon>
        </el-dropdown-item>
        <el-dropdown-item>
          <el-icon style="margin-left:15px">
            <SwitchButton/>
          </el-icon>
          <a href="/" style="margin-left:8px; margin-right: 100px">退出登录</a>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
  <el-dialog v-model="UserInfoVisible" width="50%" align-center :show-close="false">
    <div style="font-size: 25px; font-weight: bold; color: #181818">用户ID： {{ userInfo.user_id }}</div>
    <div v-if="UserInfoVisible" style="text-align: left">
      <el-divider/>
      <p style="margin-left: 1vw; font-size: 1rem; margin-top: 5px; color: #666666">
        名称：
        <span style="font-weight: bold; color: #181818">{{ userInfo.user_name }}</span>
      </p>
      <p style="margin-left: 1vw; font-size: 1rem; margin-top: 5px; color: #666666">
        邮箱：
        <span style="font-weight: bold; color: #181818">{{ userInfo.email }}</span>
      </p>
      <p style="margin-left: 1vw; font-size: 1rem; margin-top: 5px; color: #666666">
        电话号码：
        <span style="font-weight: bold; color: #181818">{{ userInfo.phone }}</span>
      </p>
    </div>
    <template #footer>
      <el-button type="danger" @click="ShowUpdateUserInfo">
        编辑
      </el-button>
      <el-button type="primary" @click="UserInfoVisible=false">
        确认
      </el-button>
    </template>
  </el-dialog>
  <el-dialog v-model="UserInfoUpdateVisible" width="50%" align-center :show-close="false">
    <div style="font-size: 25px; font-weight: bold; color: #181818">用户ID： {{ userInfo.user_id }}</div>
    <div v-if="UserInfoUpdateVisible" style="text-align: left">
      <el-divider/>
      <el-form :model="newUserInfo" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="密码" prop="oldPassword">
          <el-input type="password" v-model="newUserInfo.oldPassword" placeholder="请输入原始密码确认身份"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="user_name">
          <el-input v-model="newUserInfo.user_name" maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="newUserInfo.email"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="phone">
          <el-input v-model="newUserInfo.phone"></el-input>
        </el-form-item>
        <el-form-item label="修改密码" prop="newPassword">
          <el-input type="password" v-model="newUserInfo.newPassword" placeholder="请修改密码（如有需要）"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <el-button type="danger" @click="UserInfoUpdateVisible=false">
        取消
      </el-button>
      <el-button type="primary" @click="handleUpdateUserInfo">
        确认
      </el-button>
    </template>
  </el-dialog>
  <el-dialog v-model="CollectInfoVisible" width = "80%" :show-close="false">
    <div style="font-size: 25px; font-weight: bold; color: #181818">收藏商品</div>
    <div v-for="product in collectProducts" :key="product.count" >
      <div v-if="product.collectFlag === 1" class="product_item">
        <div style="flex: 1;">
          <img :src="product.photoURL"  class="product-image"/>
        </div>
        <div style="flex: 3; padding-left: 10px;">
          <p style="text-align: left;">
            <el-link href="./detail" style="color: #181818;font-size:larger" @click="handleLink(product)">{{ product.name }}</el-link>
          </p>

          <div style="display:flex; justify-content: start;text-align: left;font-size: smaller; margin-top:10px">
            <div style="flex: 1;">
              <span style="text-align: left;color:#e23a3a;font-size: 20px; margin-right:20px">
                ￥{{product.price}}
              </span>
              <img
                  v-if="product.platform === '京东'"
                  src="../assets/JD.png"
                  style="width: 3.5%; margin-right: 8px"
                  alt="其他平台："
              />
              <img
                  v-if="product.platform === '亚马逊'"
                  src="../assets/Amazon.png"
                  style="width: 3.5%; margin-right: 8px"
                  alt="其他平台："
              />
              <img
                  v-if="product.platform === '当当'"
                  src="../assets/DD.png"
                  style="width: 3.5%; margin-right: 8px"
                  alt="其他平台："
              />
              <img
                  v-if="product.platform === '苏宁'"
                  src="../assets/SN.png"
                  style="width: 3.5%; margin-right: 8px"
                  alt="其他平台："
              />
              <span style="color: #666666; font-size: 13px">{{product.shop}}</span>
            </div>
            <el-button v-if="product.lowReminderFlag === 0" type="primary" plain style="display:flex; justify-content: flex-end; margin-left: auto"
                       @click="setLowReminder(product.count)">
              降价提醒
            </el-button>
            <el-button v-if="product.lowReminderFlag === 1" type="primary" plain style="display:flex; justify-content: flex-end; margin-left: 10px"
                       @click="cancelSetLowReminder(product.count)">
              取消提醒
            </el-button>
            <el-button v-if="product.collectFlag === 1" type="warning" plain style="display:flex; justify-content: flex-end; margin-left: 10px"
                       @click="cancelCollectProduct(product.count)">
              取消收藏
            </el-button>
            <el-button type="danger" plain style="display:flex; justify-content: flex-end; margin-left: 10px">
              <a class="custom-link" v-bind:href="product.productURL">去看看</a>
            </el-button>
          </div>
        </div>
      </div>
    </div>
    <template #footer>
      <el-button type="primary" @click="CollectInfoVisible=false">
        确认
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref} from "vue";
import {onMounted} from 'vue';
import {ArrowDown, ArrowRight, Avatar, Star, SwitchButton, User} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import axios from "axios";
import {useStore} from "vuex";

const store = useStore();
const handleLink = (product)=>{
  let newProduct = {};
  newProduct.id = product.id;
  newProduct.name = product.name;
  newProduct.price = product.price;
  newProduct.platform = product.platform;
  newProduct.shop = product.shop;
  newProduct.photoURL = product.photoURL;
  newProduct.productURL = product.productURL;
  newProduct.detail = product.detail;
  store.dispatch("setSelectProduct", newProduct);
  window.location.href = "/detail";
}
onMounted(() => {
  initializeFun();
});
const initializeFun = () => {
  userInfo.value.user_id = store.getters.userID;

  // 获取用户初始信息并存储
  fetchUserInfo();

  // 获取用户所有收藏信息
  fetchUserCollectInfo();
}
const fetchUserInfo = () => {
  axios.get('/user/query/', {
    params: {userID: userInfo.value.user_id}
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      userInfo.value.user_name = Response.data.name;
      userInfo.value.email = Response.data.email;
      userInfo.value.phone = Response.data.phone;
      userInfo.value.password = Response.data.password;
    }else{
      // 处理后端返回的错误
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    // 处理请求错误，如网络错误或服务器错误
    ElMessage.error(error.response.data.error);
  })
}
const fetchUserCollectInfo = () =>{
  axios.get('/collect/getAll/', {
    params: {user_id: userInfo.value.user_id}
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      collectProducts.value = Response.data;
    }
    else{
      // 处理后端返回的错误
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    // 处理请求错误，如网络错误或服务器错误
    ElMessage.error(error.response.data.error);
  })
}
const openCollectInfo = ()=>{
  CollectInfoVisible.value = true;
  fetchUserCollectInfo();
}
const collectProducts = ref([])
const userInfo = ref({
  user_id: 0,
  user_name: "",
  email: "",
  phone: "",
  password: ""
})
const newUserInfo = ref({
  user_name: "",
  email: "",
  phone: "",
  oldPassword: "",
  newPassword: "",
})
const formRef = ref(null);
const UserInfoVisible = ref(false)
const UserInfoUpdateVisible = ref(false)
const CollectInfoVisible = ref(false)
const ShowUpdateUserInfo = () => {
  UserInfoUpdateVisible.value = true
  UserInfoVisible.value = false
  newUserInfo.value.newPassword = ""
  newUserInfo.value.oldPassword = ""
  newUserInfo.value.user_name = userInfo.value.user_name
  newUserInfo.value.email = userInfo.value.email
  newUserInfo.value.phone = userInfo.value.phone
}
const handleUpdateUserInfo = () => {
  let {user_name, email, phone, oldPassword, newPassword} = newUserInfo.value;
  // 处理用户没有修改密码的情况
  if (newPassword === '') {
    newPassword = oldPassword;
  }
  console.log('用户名:', user_name, '邮箱：', email, '密码：', newPassword, '电话: ', newPassword);
  formRef.value.validate((valid) => {
    if (valid) {
      axios.post('/user/update/', {
        id: userInfo.value.user_id,
        name: user_name,
        email: email,
        phone: phone,
        password: newPassword,
      }).then(response => {
        let Response = response.data;
        if(Response.success){
          ElMessage.success("编辑成功");
          userInfo.value.user_name = user_name;
          userInfo.value.email = email;
          userInfo.value.phone = phone;
          userInfo.value.password = newPassword;
          UserInfoUpdateVisible.value = false
        }
        else {
          ElMessage.error(Response.errorMsg);
        }
      }).catch(error => {
        ElMessage.error(error.response.data.error);
      })
    } else {
      // 表单验证失败，阻止提交
      console.log('error');
    }
  });
}
const cancelCollectProduct = (count)=>{
  axios.post('/collect/cancel/', {
    user_id: userInfo.value.user_id,
    product_id: collectProducts.value[count].id,
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      ElMessage.success("取消收藏成功");
      collectProducts.value[count].collectFlag= 0;
      notifyProductDetail(); // 告诉ProductDetail组件可能发生数据更新
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}
const setLowReminder = (count)=>{
  axios.post('/collect/update/', {
    user_id: userInfo.value.user_id,
    product_id: collectProducts.value[count].id,
    isLowReminder: 1,
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      ElMessage.success("降价提醒设置成功");
      collectProducts.value[count].lowReminderFlag= 1;
      notifyProductDetail(); // 告诉ProductDetail组件可能发生数据更新
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}
const cancelSetLowReminder = (count)=>{
  axios.post('/collect/update/', {
    user_id: userInfo.value.user_id,
    product_id: collectProducts.value[count].id,
    isLowReminder: 0,
  }).then(response => {
    let Response = response.data;
    if(Response.success){
      ElMessage.success("降价提醒取消成功");
      collectProducts.value[count].lowReminderFlag= 0;
      notifyProductDetail(); // 告诉ProductDetail组件可能发生数据更新
    }
    else {
      ElMessage.error(Response.errorMsg);
    }
  }).catch(error => {
    ElMessage.error(error.response.data.error);
  })
}
const rules = {
  user_name: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, message: '用户名不少于3个字符', trigger: ['blur', 'change']},
  ],
  email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'},
    {type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change']},
  ],
  phone: [
    {required: true, message: '请输入电话', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        const reg = /^1[3-9]\d{9}$/;
        if (reg.test(value)) {
          return callback();
        } else {
          return callback(new Error('请输入正确的11位手机号码'));
        }
      },
      trigger: 'blur',
    }
  ],
  oldPassword: [
    {required: true, message: '请输入原始密码确认身份', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        if (value !== userInfo.value.password) {
          callback(new Error('密码不正确'));
        } else {
          callback();
        }
      },
      trigger: 'blur',
    },
  ],
  newPassword: [
    {required: false, message: '请确认密码', trigger: 'blur'},
    {min: 6, message: '密码不少于6个字符', trigger: ['blur', 'change']},
    {
      validator: (rule, value, callback) => {
        if (value === userInfo.value.password) {
          callback(new Error('新旧密码一致'));
        } else {
          callback();
        }
      },
      trigger: 'blur',
    },
  ],
};
import bus from '../../eventbus/event.js';
const notifyProductDetail = () => {
  bus.emit('collect-info-update');
};
</script>

<style scoped>
.top-right-container {
  position: absolute;
  top: 4vh; /* 根据需要调整 */
  right: 9vw; /* 根据需要调整 */
}

.product_item {
  display: flex;
  align-items: center;
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

.custom-link {
  color: #f57777;
}

.custom-link:hover {
  color: white;
}
.product-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
}
</style>