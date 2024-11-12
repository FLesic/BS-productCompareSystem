<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">用户登录</h2>
      <el-divider border-style="dashed"></el-divider>
      <el-form :model="loginForm" ref="formRef" :rules="rules" label-width="100px">
        <el-form-item label="账户" prop="user_nameOrEmail">
          <el-input v-model="loginForm.user_nameOrEmail" placeholder="请输入邮箱/用户名"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
          <el-link type="primary" style="margin-left:2vw;" @click="goToRegister">首次使用？点我注册！</el-link>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

const formRef = ref(null);
const loginForm = ref({
  user_nameOrEmail: '',
  password: '',
});

const rules = {
  user_nameOrEmail: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
  ],
  password: [{ required: true, message: '请输入密码 ',trigger: 'blur' }]
};

const router = useRouter();

const handleLogin = () => {
  const { user_nameOrEmail, password } = loginForm.value;
  console.log('邮箱/用户名：', user_nameOrEmail, '密码：', password);
  formRef.value.validate((valid) => {
    if (valid) {
      // 表单验证通过，执行提交操作
      // TODO 提交到后端账户数据
      ElMessage.success("登录成功")
      console.log('success');
    } else {
      // 表单验证失败，阻止提交
      console.log('error');
    }
  });
};

const goToRegister = () => {
  router.push('/register');
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh;
}

.login-card {
  width: 400px;
}

</style>
