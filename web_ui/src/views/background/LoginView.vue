<template>
  <div class="container">
    <div class="login-card">
      <div class="login-title">欢迎使用东神脚手架后台</div>
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" prefix-icon="Lock" show-password></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <div style="display: flex">
            <el-input v-model="form.code" placeholder="验证码" prefix-icon="Postcard" @keyup.enter="logIn"></el-input>
            <div class="login-code">
              <img id="verificationCodeImg" :src="codeUrl" alt="点击一下试试" title="看不清？换一张" @click="getCaptcha" />
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button v-no-more-click :loading="loading" type="primary" style="width: 100%" @click="logIn">
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { captcha, login } from "@/api/admin_request/WebRequest";
import router from "@/router";
import { useUserStore } from "@/store/modules/user";
import type { UserDto } from "@/types";
import type { FormInstance } from "element-plus";
import { ElMessage } from "element-plus";
import { onMounted, onUnmounted, ref } from 'vue';

const userStore = useUserStore()
const codeUrl = ref<string>('');
const loading = ref<boolean>(false);
const formRef = ref<FormInstance>();
const form = ref<UserDto>({
  username: 'admin',
  password: '123456',
  code: ''
});

const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'change' },
  ],
};

const logIn = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      loading.value = true;
      login(form.value).then(res => {
        ElMessage.success('登录成功');
        router.push('/Manage');
      }).catch(error => {
        console.log(error);
      }).finally(() => {
        getCaptcha();
        loading.value = false;
      })
    }
  });
};

const getCaptcha = () => {
  captcha().then(imageUrl => {
    if (imageUrl) {
      // 释放旧的 Blob URL 防止内存泄漏
      if (codeUrl.value) {
        URL.revokeObjectURL(codeUrl.value);
      }
      codeUrl.value = imageUrl;
    }
  }).catch(error => {
    console.log(error)
  });
};

const checkSession = () => {
  userStore.fetchCurrentUser().then((res) => {
    if (res.code === 200) {
      router.push('/Manage');
    }
  })
}

onMounted(() => {
  getCaptcha();
  checkSession();
});

onUnmounted(() => {
  if (codeUrl.value) {
    URL.revokeObjectURL(codeUrl.value);
  }
});
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/login";
</style>
