<template>
  <div>
    <el-form ref="formRef" :model="form" :rules="rules">
      <el-form-item label="原来密码" prop="formerPassword">
        <el-input v-model="form.formerPassword" placeholder="请输入原密码" prefix-icon="User"
                  style="width: 350px;height: 40px"></el-input>
      </el-form-item>
      <el-form-item label="新的密码" prop="newPassword">
        <el-input v-model="form.newPassword" placeholder="请输入新密码" prefix-icon="Lock"
                  show-password style="width: 350px;height: 40px"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" placeholder="请确认新密码" prefix-icon="Lock"
                  show-password style="width: 350px;height: 40px"></el-input>
      </el-form-item>
    </el-form>
    <el-button v-no-more-click @click="handleReset">取消</el-button>
    <el-button v-no-more-click type="primary" @click="submitForm">
      确认
    </el-button>
  </div>
</template>

<script setup>
import {ref} from "vue";
import router from "@/router";
import {logout, updatePerson, validateFormerPassword} from "@/api/request/WebRequest";
import {ElMessage} from "element-plus";

const formRef = ref(null);
const form = ref({
  formerPassword: null,
  newPassword: null,
  confirmPassword: null
});

const formerPassword = (rule, confirmPwd, callback) => {
  validateFormerPassword(confirmPwd).then(res => {
    if (res.code !== 200) {
      callback(new Error("原密码错误"));
      return
    }
    callback();
  }).catch(error => {
    console.log(error)
  });
};

const validatePassword = (rule, confirmPwd, callback) => {
  if (confirmPwd !== form.value.newPassword) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
};

const rules = {
  formerPassword: [
    {required: true, message: '请输入原密码', trigger: 'blur'},
    {validator: formerPassword, trigger: 'blur'}
  ],
  newPassword: [
    {required: true, message: '请输入新密码', trigger: 'blur'}
  ],
  confirmPassword: [
    {required: true, message: '请确认新密码', trigger: 'blur'},
    {validator: validatePassword, trigger: 'blur'}
  ]
};
// 提交表单
const submitForm = () => {
  formRef.value.validate(valid => {
    if (valid) {
      updatePerson({password: form.value.confirmPassword}).then(res => {
        if (res.code !== 200) {
          ElMessage.error(res.msg);
          return;
        }
        ElMessage.success('修改成功');
        logout().then(res => {
          if (res.code !== 200) {
            ElMessage.error(res.msg);
            return
          }
          router.push('/');
          window.location.reload(true);
        }).catch(error => {
          console.log(error)
        });
      }).catch(error => {
        console.log(error)
      });
    }
  });
};
// 控制重置表单
const handleReset = () => {
  router.push('/manage')
}
</script>

<style scoped>

</style>
