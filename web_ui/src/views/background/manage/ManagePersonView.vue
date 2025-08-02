<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <el-divider/>
          <div class="text item">
            <el-upload
                :action="uploadUrl + '/common/files/upload'"
                :before-upload="beforeAvatarUpload"
                :on-success="handleAvatarSuccess"
                :show-file-list="false"
                class="avatar-uploader"
                style="text-align: center"
            >
              <img v-if="userInfo.imgUrl" :src="userInfo.imgUrl" alt="" class="avatar"/>
              <el-icon v-else class="avatar-uploader-icon">
                <Plus/>
              </el-icon>
            </el-upload>
            <el-divider/>
            <div style="display: flex;justify-content: space-between">
              <div style="display: flex; align-items: center;">
                <el-icon>
                  <UserFilled/>
                </el-icon>
                <span>用户名称</span>
              </div>
              <div>
                {{ userInfo.username }}
              </div>
            </div>
            <el-divider/>
            <div style="display: flex;justify-content: space-between">
              <div style="display: flex; align-items: center;">
                <el-icon>
                  <PhoneFilled/>
                </el-icon>
                <span>手机号码</span>
              </div>
              <div>
                {{ userInfo.phone }}
              </div>
            </div>
            <el-divider/>
            <div style="display: flex;justify-content: space-between">
              <div style="display: flex; align-items: center;">
                <el-icon>
                  <Message/>
                </el-icon>
                <span>邮箱</span>
              </div>
              <div>
                {{ userInfo.email }}
              </div>
            </div>
            <el-divider/>
            <div style="display: flex;justify-content: space-between">
              <div style="display: flex; align-items: center;">
                <el-icon>
                  <Avatar/>
                </el-icon>
                <span>角色</span>
              </div>
              <div>
                {{ roleCodes }}
              </div>
            </div>
            <el-divider/>
            <div style="display: flex;justify-content: space-between">
              <div style="display: flex; align-items: center;">
                <el-icon>
                  <Watch/>
                </el-icon>
                <span>创建时间</span>
              </div>
              <div>
                {{ userInfo.createTime }}
              </div>
            </div>
            <el-divider/>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-divider/>
          <div class="text item">
            <el-row>
              <el-col :span="4">
                <router-link to="/Manage/ManagePersonView/ManagePersonBaseInfoView">基本资料</router-link>
              </el-col>
              <el-col :span="4">
                <router-link to="/Manage/ManagePersonView/ManagePersonPasswordView">修改密码</router-link>
              </el-col>
            </el-row>
            <el-divider/>
            <router-view :userInfo="userInfo" @updateUserInfo="getUserInfo"/>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import store from "@/store";
import {Avatar, Message, PhoneFilled, Plus, UserFilled, Watch} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {updatePerson} from "@/api/admin_request/WebRequest";
import router from "@/router";

// 上传的ip和端口号
const uploadUrl = import.meta.env.VUE_APP_BASEURL
// 用户信息
const userInfo = ref({
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
// 获取父组件自定义事件
const emit = defineEmits(['updateUserInfo'])

// 获取用户信息
const getUserInfo = () => {
  store.dispatch('user/queryCurrentUser').then(() => {
    userInfo.value = store.getters["user/userInfo"]
    router.push('/Manage/ManagePersonView/ManagePersonBaseInfoView')
  })
}

// 控制上传成功
const handleAvatarSuccess = (response, uploadFile) => {
  if (response.code !== 200) {
    ElMessage.error(response.msg)
    return
  }
  userInfo.value.imgUrl = response.data;
  updatePerson({id: userInfo.value.id, imgUrl: userInfo.value.imgUrl}).then(response => {
    getUserInfo()
    emit('updateUserInfo');
  }).catch(error => {
    console.log(error)
  });
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

// 计算角色属性
const roleCodes = computed(() => {
  return userInfo.value.roles.map(role => role.role_code).join(', ');
});

onMounted(() => {
  getUserInfo()
});
</script>

<style scoped>
a {
  padding: 5px 0;
  z-index: 9;
}

a:hover {
  color: #4f9bfe;
  border-bottom: 5px solid #4f9bfe;
  border-radius: 1px;
}
</style>
<style>
.el-divider {
  margin: 10px 0;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

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
