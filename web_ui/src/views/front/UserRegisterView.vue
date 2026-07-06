<template>
  <div class="container">
    <div style="background-color: white; width: 400px; padding: 30px; border-radius: 10px">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #000000">欢迎使用东神脚手架</div>
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" prefix-icon="Lock" show-password></el-input>
        </el-form-item>
        <el-form-item prop="confirmPwd">
          <el-input v-model="form.confirmPwd" placeholder="请确认密码" prefix-icon="Lock" show-password></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <div style="display: flex">
            <el-input v-model="form.code" placeholder="验证码" prefix-icon="Postcard" @keyup.enter="registe"></el-input>
            <div class="login-code">
              <img id="verificationCodeImg" :src="codeUrl" alt="点击一下试试" title="看不清？换一张" @click="getCaptcha" />
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button v-no-more-click style="width: 100%; background: pink; border-color: #ff7b7b; color: white"
            @click="registe">注 册
          </el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
            已有账号？请<a href="/UserLogin">登录</a>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { captcha, register } from "@/api/front_request/WebRequest";
import router from "@/router";
import type { FormInstance } from 'element-plus';
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';

const formRef = ref<FormInstance>();
const codeUrl = ref('');
interface RegisterForm {
  username: string
  password: string
  confirmPwd: string
  code: string
}
const form = ref<RegisterForm>({
  username: '',
  password: '',
  confirmPwd: '',
  code: ''
});

// 确认密码校验：两次密码一致性
const validatePassword = (rule: unknown, confirmPwd: string, callback: (error?: Error) => void) => {
  if (confirmPwd === '') {
    callback(new Error("请确认密码"));
  } else if (confirmPwd !== form.value.password) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
};

// 密码格式校验：6位以上纯数字或字母
const validatePasswordFormat = (rule: unknown, value: string, callback: (error?: Error) => void) => {
  if (!value) {
    callback(new Error("请输入密码"));
  } else if (value.length < 6) {
    callback(new Error("密码长度不能少于6位"));
  } else if (!/^[a-zA-Z0-9]+$/.test(value)) {
    callback(new Error("密码只能包含数字和字母"));
  } else {
    callback();
  }
};

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, validator: validatePasswordFormat, trigger: 'blur' }
  ],
  confirmPwd: [
    { validator: validatePassword, trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'change' },
  ],
};

// 提交注册
const registe = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      register(form.value).then(res => {
        router.push('/UserLogin');
        ElMessage.success('注册成功');
      }).catch(error => {
        console.log(error);
      }).finally(() => {
        getCaptcha();
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

onMounted(() => {
  getCaptcha();
});

</script>

<style lang="scss" scoped>
@use "@/assets/css/front/user-auth";
</style>
