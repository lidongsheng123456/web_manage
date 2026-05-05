<template>
  <div class="common-layout">
    <el-container class="container">
      <div class="sidebar-overlay" :class="{ visible: mobileSidebarOpen }" @click="mobileSidebarOpen = false" />
      <el-aside :width="settingsStore.sidebarCollapse ? '64px' : '200px'" :class="{ 'mobile-open': mobileSidebarOpen }"
        style="transition: width 0.3s, transform 0.3s">
        <router-link class="aside-logo" to="/Manage/ManageDataView">
          <img alt="Logo" class="aside-logo-img" src="@/assets/img/logo.svg">
          <transition name="logo-fade">
            <h1 v-show="!settingsStore.sidebarCollapse" class="aside-logo-title">
              {{ settingsStore.siteName }}
            </h1>
          </transition>
        </router-link>
        <el-menu :default-openeds="['4']" :router="true" :collapse="settingsStore.sidebarCollapse"
          :active-text-color="settingsStore.themeColor" class="el-menu-vertical-demo" default-active="1">
          <el-menu-item index="/Manage/ManageDataView">
            <el-icon>
              <Promotion />
            </el-icon>
            <span>数据中心</span>
          </el-menu-item>
          <el-sub-menu v-if="shouldShowSystemMenu" index="2">
            <template #title>
              <el-icon>
                <Tools />
              </el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item v-permission="'admin:notice:query'" index="/Manage/ManageNoticeView">
              <el-icon>
                <Bell />
              </el-icon>
              通知管理
            </el-menu-item>
            <el-menu-item v-permission="'admin:user:query'" index="/Manage/ManageUserView">
              <el-icon>
                <User />
              </el-icon>
              用户管理
            </el-menu-item>
            <el-menu-item v-permission="'admin:operLog:query'" index="/Manage/ManageLogView">
              <el-icon>
                <Comment />
              </el-icon>
              日志管理
            </el-menu-item>
            <el-menu-item v-permission="'admin:dict:query'" index="/Manage/ManageDictView">
              <el-icon>
                <Collection />
              </el-icon>
              字典管理
            </el-menu-item>
            <el-sub-menu v-permission="'admin:permission:query'" index="2-1" style="background-color: #212d3d">
              <template #title>
                <el-icon>
                  <Avatar />
                </el-icon>
                权限管理
              </template>
              <el-menu-item index="/Manage/ManageRoleView">
                <el-icon>
                  <Stamp />
                </el-icon>
                角色管理
              </el-menu-item>
              <el-menu-item index="/Manage/ManagePermissionView">
                <el-icon>
                  <Unlock />
                </el-icon>
                权限管理
              </el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu v-if="shouldShowToolsMenu" index="3">
            <template #title>
              <el-icon>
                <Avatar />
              </el-icon>
              <span>系统工具</span>
            </template>
            <el-menu-item v-permission="'admin:docs:query'" index="/Manage/DocsView">
              <el-icon>
                <List />
              </el-icon>
              接口文档
            </el-menu-item>
            <el-menu-item v-permission="'admin:com-query:query'" index="/Manage/ManageComQueryView">
              <el-icon>
                <Search />
              </el-icon>
              通用查询
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu v-if="shouldShowFrontMenu" index="4">
            <template #title>
              <el-icon>
                <HomeFilled />
              </el-icon>
              <span>前台管理</span>
            </template>
            <el-menu-item v-permission="'admin:front-user:query'" index="/Manage/ManageFrontUserView">
              <el-icon>
                <UserFilled />
              </el-icon>
              用户管理
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/Manage/ManageSettingsView">
            <el-icon>
              <Setting />
            </el-icon>
            <span>系统设置</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="el-header-left">
            <div class="mobile-hamburger" @click="mobileSidebarOpen = !mobileSidebarOpen">
              <el-icon :size="24">
                <Fold />
              </el-icon>
            </div>
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
                <el-image :src="adminUserInfo.imgUrl || noImage" style="width: 50px; height: 50px;border-radius: 50%">
                  <template #error>
                    <img :src="noImage" style="width: 50px; height: 50px;border-radius: 50%" alt="" />
                  </template>
                </el-image>
                <el-icon class="el-icon--right">
                  <arrow-down />
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
          <el-divider />
          <div v-show="settingsStore.showTagsView" class="el-header-right">
            <el-tabs v-model="editableTabsValue" class="demo-tabs" editable type="card" @edit="handleTabsEdit"
              @tab-click="handleTabClick">
              <el-tab-pane v-for="item in editableTabs" :key="item.name" :closable="item.closable" :label="item.title"
                :name="item.name">
              </el-tab-pane>
            </el-tabs>
            <!-- 标签右键菜单 -->
            <div v-if="ctxMenuVisible" class="tab-ctx-menu" :style="{ left: ctxMenuX + 'px', top: ctxMenuY + 'px' }">
              <div class="ctx-item" @click="ctxCloseCurrent">关闭当前</div>
              <div class="ctx-item" @click="ctxCloseOthers">关闭其他</div>
              <div class="ctx-item" @click="ctxCloseLeft">关闭左侧</div>
              <div class="ctx-item" @click="ctxCloseRight">关闭右侧</div>
              <div class="ctx-item ctx-danger" @click="ctxCloseAll">关闭全部</div>
            </div>
          </div>
        </el-header>
        <el-main>
          <router-view v-slot="{ Component }">
            <transition v-if="settingsStore.showTransition" :duration="{ enter: 300, leave: 150 }" mode="out-in"
              name="slide-fade">
                <component :is="Component" :key="routeKey" />
            </transition>
              <component v-else :is="Component" :key="routeKey" />
          </router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { logout } from "@/api/admin_request/WebRequest";
import noImageUrl from '@/assets/img/no_image.png';
const noImage = noImageUrl;
import router from "@/router";
import { useSettingsStore } from "@/store/modules/settings";
import { useUserStore } from "@/store/modules/user";
import {
  ArrowDown,
  Avatar,
  Bell,
  Collection,
  Comment,
  Fold,
  HomeFilled,
  List,
  Promotion,
  Search,
  Setting,
  Stamp,
  Tools,
  Unlock,
  User,
  UserFilled
} from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { computed, nextTick, onBeforeUnmount, onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const userStore = useUserStore();
const settingsStore = useSettingsStore();
const mobileSidebarOpen = ref(false);
let adminUserInfo = ref({});
let tabIndex = 2;
const editableTabsValue = ref(route.path);
const editableTabs = ref([
  {
    title: '首页',
    name: '/Manage/ManageDataView',
    content: '数据中心',
    closable: true
  }
]);

// 嵌套子路由使用父路由路径作为 key，避免子路由切换导致父组件重新挂载
const routeKey = computed(() => {
  const segments = route.path.split('/').filter(Boolean);
  if (segments.length > 2) {
    return '/' + segments.slice(0, 2).join('/');
  }
  return route.path;
});


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

// ==================== 标签右键菜单 ====================
const ctxMenuVisible = ref(false);
const ctxMenuX = ref(0);
const ctxMenuY = ref(0);
const ctxTargetTab = ref('');

const openCtxMenu = (e) => {
  // 查找被右键的 tab 元素
  const tabEl = e.target.closest('.el-tabs__item');
  if (!tabEl) return;
  e.preventDefault();
  const tabId = tabEl.id;
  // el-tabs 生成的 id 格式为 tab-<name>
  const tabName = tabId.replace(/^tab-/, '');
  ctxTargetTab.value = tabName;
  ctxMenuX.value = e.clientX;
  ctxMenuY.value = e.clientY;
  ctxMenuVisible.value = true;
};

const closeCtxMenu = () => { ctxMenuVisible.value = false; };

const ctxCloseCurrent = () => {
  closeCtxMenu();
  if (ctxTargetTab.value === '/Manage/ManageDataView') return; // 首页不关闭
  handleTabsEdit(ctxTargetTab.value, 'remove');
};

const ctxCloseOthers = () => {
  closeCtxMenu();
  editableTabs.value = editableTabs.value.filter(
    t => t.name === '/Manage/ManageDataView' || t.name === ctxTargetTab.value
  );
  editableTabsValue.value = ctxTargetTab.value;
  router.push(ctxTargetTab.value);
};

const ctxCloseLeft = () => {
  closeCtxMenu();
  const idx = editableTabs.value.findIndex(t => t.name === ctxTargetTab.value);
  editableTabs.value = editableTabs.value.filter(
    (t, i) => t.name === '/Manage/ManageDataView' || i >= idx
  );
  if (!editableTabs.value.find(t => t.name === editableTabsValue.value)) {
    editableTabsValue.value = ctxTargetTab.value;
    router.push(ctxTargetTab.value);
  }
};

const ctxCloseRight = () => {
  closeCtxMenu();
  const idx = editableTabs.value.findIndex(t => t.name === ctxTargetTab.value);
  editableTabs.value = editableTabs.value.filter(
    (t, i) => t.name === '/Manage/ManageDataView' || i <= idx
  );
  if (!editableTabs.value.find(t => t.name === editableTabsValue.value)) {
    editableTabsValue.value = ctxTargetTab.value;
    router.push(ctxTargetTab.value);
  }
};

const ctxCloseAll = () => {
  closeCtxMenu();
  editableTabs.value = editableTabs.value.filter(t => t.name === '/Manage/ManageDataView');
  editableTabsValue.value = '/Manage/ManageDataView';
  router.push('/Manage/ManageDataView');
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
      setTimeout(() => {
        window.location.reload(true);
      }, 500)
    }).catch(error => {
      console.log(error)
    });
  }).catch(error => {
  })
};

// 监听路由变化
watch(() => route.path, (newPath) => {
  mobileSidebarOpen.value = false;
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
  { immediate: true } // 立即执行一次
);

// 检查用户是否有指定权限
const hasPermission = (permission) => {
  const user = userStore.adminUserInfo || {};
  const filterUser = user.permissions ? user.permissions.map(item => item.permission_code) : [];
  return filterUser && filterUser.includes(permission);
};

// 检查系统管理子菜单是否应该显示
const shouldShowSystemMenu = computed(() => {
  const user = userStore.adminUserInfo;
  if (!user || !user.permissions) return false;

  return hasPermission('admin:notice:query') ||
    hasPermission('admin:user:query') ||
    hasPermission('admin:operLog:query') ||
    hasPermission('admin:dict:query') ||
    hasPermission('admin:permission:query');
});

// 检查系统工具子菜单是否应该显示
const shouldShowToolsMenu = computed(() => {
  const user = userStore.adminUserInfo;
  if (!user || !user.permissions) return false;

  return hasPermission('admin:docs:query') ||
    hasPermission('admin:com-query:query');
});

// 检查前台管理子菜单是否应该显示
const shouldShowFrontMenu = computed(() => {
  const user = userStore.adminUserInfo;
  if (!user || !user.permissions) return false;

  return hasPermission('admin:front-user:query');
});

const getUserInfo = () => {
  userStore.fetchCurrentUser().then(() => {
    adminUserInfo.value = userStore.adminUserInfo
  })
}

onMounted(() => {
  getUserInfo();
  settingsStore.applyTheme();
  // 绑定右键菜单事件
  nextTick(() => {
    const tabNav = document.querySelector('.el-header-right .el-tabs__nav-wrap');
    if (tabNav) tabNav.addEventListener('contextmenu', openCtxMenu);
  });
  document.addEventListener('click', closeCtxMenu);
});

onBeforeUnmount(() => {
  const tabNav = document.querySelector('.el-header-right .el-tabs__nav-wrap');
  if (tabNav) tabNav.removeEventListener('contextmenu', openCtxMenu);
  document.removeEventListener('click', closeCtxMenu);
});
</script>

<style lang="scss">
@use "@/assets/css/admin/layout";
</style>
