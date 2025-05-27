<template>
  <div>
    <div>
      <div class="safe-area">
        <el-card style="max-width: 480px;margin: 0 auto">
          <template #header>
            <div class="card-header">
              <span>个人信息</span>
            </div>
          </template>
          <el-form ref="formRef" :inline="false" :model="form" :rules="rules" label-width="70px">
            <el-form-item label="头像"
                          style="display: flex;margin-left: 70px;align-items: center;justify-content: space-around">
              <el-upload
                  :action="uploadUrl + '/common/files/upload'"
                  :before-upload="beforeAvatarUpload"
                  :on-success="handleAvatarSuccess"
                  :show-file-list="false"
                  class="avatar-uploader"
              >
                <img style="width: 178px;height: 178px;margin: 0 auto" v-if="form.imgUrl" :src="form.imgUrl || noImage"
                     alt="" class="avatar"/>
                <el-icon v-else class="avatar-uploader-icon">
                  <Plus/>
                </el-icon>
              </el-upload>
            </el-form-item>
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
          <template #footer>
            <el-button @click="dialogOverflowVisible = true" type="warning" plain>
              修改密码
            </el-button>
            <el-button @click="submit" type="primary" plain>
              提交
            </el-button>
          </template>
        </el-card>

        <el-dialog
            v-model="dialogOverflowVisible"
            title="修改密码"
            draggable
            overflow
            width="500"
        >
          <el-form ref="pwdFormRef" :model="pwdForm" :rules="rules">
            <el-form-item label="原来密码" prop="oldPassword">
              <el-input v-model="pwdForm.oldPassword" placeholder="请输入原密码" prefix-icon="User"
                        style="width: 350px;height: 40px"></el-input>
            </el-form-item>
            <el-form-item label="新的密码" prop="newPassword">
              <el-input v-model="pwdForm.newPassword" placeholder="请输入新密码" prefix-icon="Lock"
                        show-password style="width: 350px;height: 40px"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="conPassword">
              <el-input v-model="pwdForm.conPassword" placeholder="请确认新密码" prefix-icon="Lock"
                        show-password style="width: 350px;height: 40px"></el-input>
            </el-form-item>
          </el-form>
          <el-button v-no-more-click @click="handleReset">取消</el-button>
          <el-button v-no-more-click type="primary" @click="submitForm">
            确认
          </el-button>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script setup>
// 上传的ip和端口号
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {Plus} from "@element-plus/icons-vue";
import {logout, queryCurrentFrontUserInfo, updatePerson, validateFormerPassword} from "@/api/front_request/WebRequest";
import router from "@/router";
// 无头像时展示
const noImage = require('@/assets/img/no_image.png')
const uploadUrl = process.env.VUE_APP_BASEURL
const formRef = ref(null)
const pwdFormRef = ref(null)
// 对话框显示
const dialogOverflowVisible = ref(false)

const form = ref({
  username: null,
  name: null,
  phone: null,
  email: null,
  imgUrl: null
})

const pwdForm = ref({
  oldPassword: '',
  newPassword: '',
  conPassword: '',
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
const formerPassword = (rule, confirmPwd, callback) => {
  validateFormerPassword(confirmPwd).then(res => {
    callback();
  }).catch(error => {
    callback(new Error('原密码错误'));
  });
};

const validatePassword = (rule, confirmPwd, callback) => {
  if (confirmPwd !== pwdForm.value.newPassword) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
};
// 验证规则
const rules = {
  username: [
    {required: true, message: "用户名不能为空", trigger: "blur"},
  ],
  name: [
    {required: true, message: "姓名不能为空", trigger: "blur"},
  ],
  email: [
    {required: true, message: "邮箱不能为空", trigger: "blur"},
    {validator: validateEmail, trigger: 'blur'}
  ],
  phone: [
    {required: true, message: "手机号不能为空", trigger: "blur"},
    {validator: validatePhone, trigger: 'blur'}
  ],
  oldPassword: [
    {required: true, message: '请输入原密码', trigger: 'blur'},
    {validator: formerPassword, trigger: 'blur'}
  ],
  newPassword: [
    {required: true, message: '请输入新密码', trigger: 'blur'}
  ],
  conPassword: [
    {required: true, message: '请确认新密码', trigger: 'blur'},
    {validator: validatePassword, trigger: 'blur'}
  ]
};
// 控制上传成功
const handleAvatarSuccess = (response, uploadFile) => {
  if (response.code !== 200) {
    ElMessage.error(response.msg)
    return
  }
  form.value.imgUrl = response.data;
};

// 上传之前验证文件
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('头像图片必须是 JPG 或 PNG 格式!');
    return false;
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('头像图片大小不能超过 10MB!');
    return false;
  }
  return true;
};

const getPersonInfo = () => {
  queryCurrentFrontUserInfo().then(res => {
    form.value = res.data
  })
}

const submit = () => {
  formRef.value.validate(valid => {
    if (valid) {
      updatePerson(form.value).then(res => {
        ElMessage.success('修改成功')
      }).catch(() => {
      })
    }
  });
}

// 提交表单
const submitForm = () => {
  pwdFormRef.value.validate(valid => {
    if (valid) {
      updatePerson({password: pwdForm.value.conPassword}).then(res => {
        ElMessage.success('修改成功');
        logout().then(res => {
          router.push('/Front');
          setTimeout(() => {
            window.location.reload(true);
          }, 500)
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
  dialogOverflowVisible.value = false;
}

onMounted(() => {
  getPersonInfo()
})
</script>

<style scoped>
</style>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  border-radius: 50%;
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
