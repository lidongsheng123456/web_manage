<template>
  <div class="container">
    <div style="background-color: white; width: 400px; padding: 30px; border-radius: 10px">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #000000">欢迎使用东神后台</div>
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" prefix-icon="Lock"></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <div style="display: flex">
            <el-input v-model="form.code" placeholder="验证码" prefix-icon="Postcard"
                      @keyup.enter="logIn"></el-input>
            <div class="login-code">
              <img id="verificationCodeImg" :src="codeUrl" alt="点击一下试试" title="看不清？换一张"
                   @click="getCaptcha"/>
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
              v-no-more-click
              :loading="loading"
              style="width: 100%; background: pink; border-color: #ff7b7b; color: white"
              @click="logIn"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
            还没有账号？请<a href="/register">注册</a>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {captcha, login} from "@/api/request/WebRequest";
import router from "@/router";
import {ElMessage} from "element-plus";
import axios from "axios";

const uploadUrl = process.env.VUE_APP_BASEURL
const codeUrl = ref('');
const loading = ref(false);
const formRef = ref(null);
const form = ref({
  username: 'lidongsheng',
  password: '123456',
  code: ''
});

const rules = {
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
  ],
  code: [
    {required: true, message: '请输入验证码', trigger: 'change'},
  ],
};

const logIn = () => {
  formRef.value.validate((valid) => {
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
      codeUrl.value = imageUrl;
    }
  }).catch(error => {
    console.log(error)
  });
};

const getNotice = () => {
  axios.get(uploadUrl + '/admin/notice').then(res => {
    if (res.data.code === 200) {
      router.push('/Manage');
    }
  }).catch(error => {
    console.log(error)
  });
}

onMounted(() => {
  getCaptcha();
  getNotice()
});
</script>

<style scoped>
a {
  text-decoration: none;
}

.container {
  background: url("@/assets/img/background.png") no-repeat center;
  background-size: cover;
  height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #858585;
}

.login-code {
  width: 150px;
  text-align: center;
  height: 41px;
  margin-left: 10px;
}

.login-code img {
  cursor: pointer;
}

.login-code-img {
  height: 100%;
}
</style>
