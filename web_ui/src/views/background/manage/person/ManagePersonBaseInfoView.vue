<template>
  <div>
    <el-form ref="formRef" :model="form" :rules="rules" label-width="65px">
      <el-form-item label="用户名" prop="username">
        <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            style="width: 350px;height: 40px"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
            v-model="form.name"
            placeholder="请输入姓名"
            style="width: 350px;height: 40px"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
            v-model="form.phone"
            placeholder="请输入手机号"
            style="width: 350px;height: 40px"
        />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
            v-model="form.email"
            placeholder="请输入邮箱"
            style="width: 350px;height: 40px"
        />
      </el-form-item>
    </el-form>
    <el-button v-no-more-click @click="handleReset">取消</el-button>
    <el-button v-no-more-click type="primary" @click="submitForm">
      确认
    </el-button>
  </div>
</template>

<script setup>
// 表单
import {defineProps, ref, watch} from "vue";
import router from "@/router";
import {updatePerson} from "@/api/admin_request/WebRequest";
import {ElMessage} from "element-plus";

// 获取父组件自定义事件
const emit = defineEmits(['updateUserInfo'])
// 表单元素
const formRef = ref(null)
// 表单
const form = ref({
  createTime: null,
  email: null,
  imgUrl: null,
  roles: [{}],
  name: null,
  permissions: [{}],
  phone: null,
  updateTime: null,
  username: null
});
const props = defineProps({
  userInfo: {
    type: Object,
    required: true
  }
});
// 自定义手机号验证函数
const validatePhone = (rule, value, callback) => {
  const phoneRegex = /^1[3-9]\d{9}$/;
  if (!value) {
    callback(new Error('手机号不能为空'));
  } else if (!phoneRegex.test(value)) {
    callback(new Error('请输入有效的手机号'));
  } else {
    callback();
  }
};
// 自定义邮箱验证函数
const validateEmail = (rule, value, callback) => {
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
  if (!value) {
    callback(new Error('邮箱不能为空'));
  } else if (!emailRegex.test(value)) {
    callback(new Error('请输入有效的邮箱地址'));
  } else {
    callback();
  }
};
// 验证规则
const rules = {
  username: [
    {required: true, message: "用户名不能为空", trigger: "blur"}
  ],
  name: [
    {required: true, message: "姓名不能为空", trigger: "blur"}
  ],
  phone: [
    {required: true, message: '手机号不能为空', trigger: 'blur'},
    {validator: validatePhone, trigger: 'blur'}
  ],
  email: [
    {required: true, message: '邮箱不能为空', trigger: 'blur'},
    {validator: validateEmail, trigger: 'blur'}
  ]
};
// 提交表单
const submitForm = () => {
  formRef.value.validate(valid => {
    if (valid) {
      updatePerson(form.value).then(res => {
        ElMessage.success('修改成功');
        emit('updateUserInfo');
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
// 监听父组件异步数据
watch(() => props.userInfo, (newValue, oldValue) => {
  // 深拷贝
  form.value = JSON.parse(JSON.stringify(newValue))
}, {immediate: true}, {deep: true})
</script>

<style scoped>

</style>
