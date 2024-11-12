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

const registerForm = ref({
  user_name: '',
  email: '',
  password: '',
  confirmPassword: '',
});
const formRef = ref(null);
const rules = {
  user_name: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 6, message: '用户名不少于6个字符', trigger: ['blur', 'change'] },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] },
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
const handleRegister = (param) => {
  const { user_name,email, password } = registerForm.value;
  console.log('用户名:', user_name,'邮箱：', email, '密码：', password);
  formRef.value.validate((valid) => {
    if (valid) {
      // 表单验证通过，执行提交操作
      // TODO 提交到后端账户数据
      ElMessage.success("注册成功")
      console.log('success');
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
