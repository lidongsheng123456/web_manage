<template>
  <div class="common-layout">
    <el-container class="container">
      <el-aside width="200px">
        <div class="aside-logo">
          <img alt="管理" src="@/assets/img/管理.png">
          <h1>
            毕设脚手架
          </h1>
        </div>
        <el-menu
            :default-openeds="['2','3']"
            :router="true"
            class="el-menu-vertical-demo"
            default-active="1"
        >
          <el-menu-item index="/Manage/ManageDataView">
            <el-icon>
              <Promotion/>
            </el-icon>
            <span>数据中心</span>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <Tools/>
              </el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item v-permission="'admin:notice:query'" index="/Manage/ManageNoticeView">
              <el-icon>
                <Bell/>
              </el-icon>
              通知管理
            </el-menu-item>
            <el-menu-item v-permission="'admin:user:query'" index="/Manage/ManageUserView">
              <el-icon>
                <User/>
              </el-icon>
              用户管理
            </el-menu-item>
            <el-menu-item v-permission="'admin:operLog:query'" index="/Manage/ManageLogView">
              <el-icon>
                <Comment/>
              </el-icon>
              日志管理
            </el-menu-item>
            <el-menu-item v-permission="'admin:dict:query'" index="/Manage/ManageDictView">
              <el-icon>
                <Collection/>
              </el-icon>
              字典管理
            </el-menu-item>
            <el-sub-menu v-permission="'admin:permission:query'" index="2-1" style="background-color: #212d3d">
              <template #title>
                <el-icon>
                  <Avatar/>
                </el-icon>
                权限管理
              </template>
              <el-menu-item index="/Manage/ManageRoleView">
                <el-icon>
                  <Stamp/>
                </el-icon>
                角色管理
              </el-menu-item>
              <el-menu-item index="/Manage/ManagePermissionView">
                <el-icon>
                  <Unlock/>
                </el-icon>
                权限管理
              </el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <Avatar/>
              </el-icon>
              系统工具
            </template>
            <el-menu-item v-permission="'admin:docs:query'" index="/Manage/DocsView">
              <el-icon>
                <List/>
              </el-icon>
              接口文档
            </el-menu-item>
            <el-menu-item v-permission="'admin:com-query:query'" index="/Manage/ManageComQueryView">
              <el-icon>
                <Search/>
              </el-icon>
              通用查询
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="el-header-left">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item to="/Manage/ManageDataView">
                首页
              </el-breadcrumb-item>
              <el-breadcrumb-item v-for="(item, index) in breadcrumbItems" :key="index" :to="item.to">
                {{ item.title }}
              </el-breadcrumb-item>
            </el-breadcrumb>
            <el-dropdown>
              <span class="el-dropdown-link">
                <el-image :src="userInfo.imgUrl" style="width: 50px; height: 50px;border-radius: 50%"/>
                <el-icon class="el-icon--right">
                  <arrow-down/>
                </el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <router-link to="/Manage/ManagePersonView">个人中心</router-link>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <router-link to="#" @click="logoutLogin">退出登录</router-link>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
          <el-divider/>
          <div class="el-header-right">
            <el-tabs
                v-model="editableTabsValue"
                class="demo-tabs"
                editable
                type="card"
                @edit="handleTabsEdit"
                @tab-click="handleTabClick"
            >
              <el-tab-pane
                  v-for="item in editableTabs"
                  :key="item.name"
                  :closable="item.closable"
                  :label="item.title"
                  :name="item.name"
              >
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-header>
        <el-main>
          <router-view v-slot="{ Component }">
            <keep-alive>
              <transition :duration="{ enter: 500, leave: 200 }" mode="out-in" name="slide-fade">
                <component :is="Component"/>
              </transition>
            </keep-alive>
          </router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {
  ArrowDown,
  Avatar,
  Bell,
  Collection,
  Comment,
  List,
  Promotion,
  Stamp,
  Tools,
  Unlock,
  User
} from "@element-plus/icons-vue";
import {computed, onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";
import router from "@/router";
import {logout} from "@/api/admin_request/WebRequest";
import {ElMessage, ElMessageBox} from "element-plus";
import store from "@/store";

const route = useRoute();
let userInfo = ref({});
let tabIndex = 2;
const editableTabsValue = ref(route.path);
// 上传的ip和端口号
const uploadUrl = process.env.VUE_APP_BASEURL
const editableTabs = ref([
  {
    title: '首页',
    name: '/Manage/ManageDataView',
    content: '数据中心',
    closable: true
  }
]);

const breadcrumbItems = computed(() => {
  const matchedRoutes = route.matched.filter(route => route.meta && route.meta.name);
  return matchedRoutes.map(route => ({
    title: route.meta.name,
    to: route.path
  }));
});

const handleTabsEdit = (targetName, action) => {
  if (action === 'add') {
    const newTabName = `${++tabIndex}`;
    editableTabs.value.push({
      title: '新标签',
      name: newTabName,
      content: '新标签内容',
    });
    editableTabsValue.value = newTabName;
  } else if (action === 'remove') {
    const tabs = editableTabs.value;
    let activeName = editableTabsValue.value;
    if (activeName === targetName) {
      tabs.forEach((tab, index) => {
        if (tab.name === targetName) {
          const nextTab = tabs[index + 1] || tabs[index - 1];
          if (nextTab) {
            activeName = nextTab.name;
          }
        }
      });
    }

    editableTabsValue.value = activeName;
    editableTabs.value = tabs.filter(tab => tab.name !== targetName);
  }
};

const handleTabClick = (tab) => {
  router.push(tab.paneName);
};

const logoutLogin = () => {
  ElMessageBox.confirm('确认退出?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    logout().then(res => {
      ElMessage.success('退出成功');
      router.push('/Login');
      window.location.reload(true);
    }).catch(error => {
      console.log(error)
    });
  }).catch(error => {
  })
};

// 监听路由变化
watch(() => route.path, (newPath) => {
      const tabExists = editableTabs.value.some(tab => tab.name === newPath);
      if (!tabExists) {
        const routeMeta = route.meta;
        editableTabs.value.push({
          title: routeMeta.name || newPath,
          name: newPath,
          content: `${routeMeta.name} 内容`, // 这里可以根据需要动态生成内容
        });
      }
      editableTabsValue.value = newPath;
    },
    {immediate: true} // 立即执行一次
);

const getUserInfo = () => {
  store.dispatch('user/queryCurrentUser').then(() => {
    userInfo.value = store.getters["user/userInfo"]
  })
}

onMounted(() => {
  getUserInfo()
});
</script>

<style lang="scss">
@use "@/assets/css/manager";
</style>
