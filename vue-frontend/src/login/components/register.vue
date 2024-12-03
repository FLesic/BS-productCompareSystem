<template>
  <div class="register-container">
    <el-card class="register-card">
      <h2 class="register-title">用户注册</h2>
      <el-divider border-style="dashed"></el-divider>
      <el-form :model="registerForm" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户名" prop="user_name">
          <el-input v-model="registerForm.user_name" placeholder="请输入用户名" maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister(registerForm)">注册</el-button>
          <el-link type="primary" style="margin-left:2vw;" @click="goToLogin">已有账户？点我登录！</el-link>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import axios from "axios";
import {useStore} from "vuex";

const registerForm = ref({
  user_name: '',
  email: '',
  phone:'',
  password: '',
  confirmPassword: '',
});
const formRef = ref(null);
const rules = {
  user_name: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, message: '用户名不少于3个字符', trigger: ['blur', 'change'] },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] },
  ],
  phone:[
    { required: true, message: '请输入电话', trigger: 'blur' },
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
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码不少于6个字符', trigger: ['blur', 'change'] },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        if (value !== registerForm.value.password) {
          callback(new Error('两次密码不一致'));
        } else {
          callback();
        }
      },
      trigger: 'blur',
    },
  ],
};

const router = useRouter();
const clearRegisterForm = () =>{
  registerForm.value.user_name = '';
  registerForm.value.email = '';
  registerForm.value.phone = '';
  registerForm.value.confirmPassword = '';
  registerForm.value.password = '';
}
const store = useStore();
const handleRegister = (param) => {
  const { user_name,email, password, phone } = registerForm.value;
  console.log('用户名:', user_name,'邮箱：', email, '密码：', password, '电话: ', phone);
  formRef.value.validate((valid) => {
    if (valid) {
      axios.post('/user/register/',{
        name: user_name,
        email: email,
        phone: phone,
        password: password,
      }).then(response => {
        let Response = response.data;
        if(Response.success){
          ElMessage.success("注册成功");
          clearRegisterForm();
          store.dispatch('setUserID', Response.data);
          window.location.href = "/search";  // 函数内部进行超链接跳转
        }
        else {
          //
          ElMessage.error(Response.errorMsg);
          registerForm.value.password = "";
          registerForm.value.confirmPassword = "";
        }
      }).catch(error => {
        ElMessage.error(error.response.data.error);
        registerForm.value.password = "";
        registerForm.value.confirmPassword = "";
      })
    } else {
      // 表单验证失败，阻止提交
      console.log('error');
    }
  });
};

const goToLogin = () => {
  router.push('/');
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh;
}

.register-card {
  width: 400px;
}

</style>
