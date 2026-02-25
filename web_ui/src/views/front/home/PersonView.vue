<template>
  <div class="person-page" ref='vantaRef'>
    <div class="person-container">
      <div class="glass-card">
        <!-- 卡片头部 -->
        <div class="card-header">
          <span class="card-title">个人信息</span>
          <div @click="goBack" class="back-btn">
            <el-icon>
              <ArrowLeft />
            </el-icon>
            返回
          </div>
        </div>

        <!-- 头像区域 -->
        <div class="avatar-section">
          <el-upload :action="uploadUrl + '/common/files/upload'" :before-upload="beforeAvatarUpload"
            :on-success="handleAvatarSuccess" :show-file-list="false" class="avatar-uploader">
            <img v-if="form.imgUrl" :src="form.imgUrl || noImage" alt="" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </div>

        <!-- 表单区域 -->
        <el-form ref="formRef" :inline="false" :model="form" :rules="rules" label-width="70px" class="person-form">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入姓名" />
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-form>

        <!-- 按钮区域 -->
        <div class="card-footer">
          <button class="btn btn-warning" @click="dialogOverflowVisible = true">修改密码</button>
          <button class="btn btn-primary" @click="submit">保存修改</button>
        </div>
      </div>

      <el-dialog v-model="dialogOverflowVisible" title="修改密码" draggable overflow width="500">
        <el-form ref="pwdFormRef" :model="pwdForm" :rules="rules">
          <el-form-item label="原来密码" prop="oldPassword">
            <el-input v-model="pwdForm.oldPassword" placeholder="请输入原密码" prefix-icon="User"
              style="height: 40px"></el-input>
          </el-form-item>
          <el-form-item label="新的密码" prop="newPassword">
            <el-input v-model="pwdForm.newPassword" placeholder="请输入新密码" prefix-icon="Lock" show-password
              style="height: 40px"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="conPassword">
            <el-input v-model="pwdForm.conPassword" placeholder="请确认新密码" prefix-icon="Lock" show-password
              style="height: 40px"></el-input>
          </el-form-item>
        </el-form>
        <el-button v-no-more-click @click="handleReset">取消</el-button>
        <el-button v-no-more-click type="primary" @click="submitForm">确认</el-button>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { logout, queryCurrentFrontUserInfo, updatePerson, validateFormerPassword } from "@/api/front_request/WebRequest";
import router from "@/router";
import { ArrowLeft, Plus } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import * as THREE from 'three';
import Net from "vanta/src/vanta.net";
import { onBeforeUnmount, onMounted, ref } from "vue";
// 无头像时展示
import noImageUrl from '@/assets/img/no_image.png';
const noImage = noImageUrl
const uploadUrl = import.meta.env.VUE_APP_BASEURL
const formRef = ref(null)
const pwdFormRef = ref(null)
// 对话框显示
const dialogOverflowVisible = ref(false)
// Vanta 相关
const vantaRef = ref(null)
let vantaEffect = null

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
    { required: true, message: "用户名不能为空", trigger: "blur" },
  ],
  name: [
    { required: true, message: "姓名不能为空", trigger: "blur" },
  ],
  email: [
    { required: true, message: "邮箱不能为空", trigger: "blur" },
    { validator: validateEmail, trigger: 'blur' }
  ],
  phone: [
    { required: true, message: "手机号不能为空", trigger: "blur" },
    { validator: validatePhone, trigger: 'blur' }
  ],
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' },
    { validator: formerPassword, trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' }
  ],
  conPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validatePassword, trigger: 'blur' }
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

// 加载vanta3D动画渲染
const loadVanta = () => {
  // 确保 DOM 元素已经渲染
  if (vantaRef.value) {
    vantaEffect = Net({
      el: vantaRef.value,
      THREE: THREE,
      mouseControls: true,
      touchControls: true,
      gyroControls: false,
      minHeight: 200.00,
      minWidth: 200.00,
      scale: 1.00,
      scaleMobile: 1.00,
      color: 0x97dffd,
      colorRange: [0x00ffff, 0xff00ff],
      backgroundColor: 0xffffff,
      // 性能优化配置
      maxDistance: 20.00,
      spacing: 15.00,
      showDots: false
    })
  }
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
      updatePerson({ password: pwdForm.value.conPassword }).then(res => {
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

// 返回上一页
const goBack = () => {
  router.go(-1);
};

onMounted(() => {
  getPersonInfo()
  loadVanta()
})

// 组件卸载时清理 Vanta 效果
onBeforeUnmount(() => {
  if (vantaEffect) {
    vantaEffect.destroy()
    vantaEffect = null
  }
})
</script>

<style scoped>
.person-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 80px 20px 40px;
}

.person-container {
  width: 100%;
  max-width: 520px;
  margin: 0 auto;
}

.glass-card {
  background: rgba(255, 255, 255, 0.35);
  backdrop-filter: blur(24px) saturate(1.8);
  -webkit-backdrop-filter: blur(24px) saturate(1.8);
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow:
    0 8px 32px rgba(31, 38, 135, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.7),
    inset 0 -1px 0 rgba(255, 255, 255, 0.15);
  border-radius: 24px;
  padding: 36px 32px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 28px;
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.4);
}

.card-title {
  font-size: 1.35rem;
  font-weight: 700;
  color: #1a202c;
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: #2563eb;
  cursor: pointer;
  padding: 6px 14px;
  border-radius: 20px;
  transition: all 0.3s;
  font-weight: 500;
}

.back-btn:hover {
  background: rgba(37, 99, 235, 0.1);
  color: #1d4ed8;
}

.avatar-section {
  display: flex;
  justify-content: center;
  margin-bottom: 28px;
}

.person-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #4a5568;
}

.person-form :deep(.el-input__wrapper) {
  background: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  border-radius: 12px;
  box-shadow: none;
  border: 1px solid rgba(255, 255, 255, 0.5);
  transition: all 0.3s;
}

.person-form :deep(.el-input__wrapper:hover) {
  border-color: rgba(37, 99, 235, 0.3);
}

.person-form :deep(.el-input__wrapper.is-focus) {
  border-color: #2563eb;
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

.card-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.4);
}

.btn {
  padding: 10px 24px;
  border-radius: 50px;
  font-weight: 600;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
  outline: none;
}

.btn-primary {
  background: linear-gradient(135deg, #2563eb 0%, #10b981 100%);
  color: white;
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
}

.btn-primary:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(37, 99, 235, 0.4);
}

.btn-warning {
  background: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  color: #4a5568;
  border: 1px solid rgba(255, 255, 255, 0.5);
}

.btn-warning:hover {
  background: rgba(255, 255, 255, 0.7);
  color: #2563eb;
  transform: translateY(-1px);
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 2px dashed rgba(37, 99, 235, 0.2);
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.3s;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
}

.avatar-uploader .el-upload:hover {
  border-color: #2563eb;
  box-shadow: 0 0 0 4px rgba(37, 99, 235, 0.08);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #a0aec0;
  width: 140px;
  height: 140px;
  text-align: center;
}

.avatar-uploader .avatar {
  width: 140px;
  height: 140px;
  display: block;
  object-fit: cover;
}

@media (max-width: 560px) {
  .person-page {
    padding: 70px 12px 24px;
  }

  .glass-card {
    padding: 24px 18px;
    border-radius: 20px;
  }

  .person-form :deep(.el-input__wrapper) {
    border-radius: 10px;
  }

  .card-footer {
    flex-direction: column;
  }

  .btn {
    width: 100%;
    text-align: center;
  }
}
</style>
