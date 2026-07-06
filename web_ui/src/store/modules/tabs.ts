import router from "@/router";
import type { TabPaneName, TabsPaneContext } from "element-plus";
import { defineStore } from "pinia";
import { ref, watch } from "vue";
import { useRoute } from "vue-router";

export interface TabItem {
  title: string
  name: string
  content: string
  closable?: boolean
}

const HOME_TAB = '/Manage/ManageDataView';

export const useTabsStore = defineStore('tabs', () => {
  const route = useRoute();
  const editableTabsValue = ref(route.path);
  const editableTabs = ref<TabItem[]>([
    { title: '首页', name: HOME_TAB, content: '数据中心', closable: false }
  ]);

  const navigateTo = (path: string) => {
    editableTabsValue.value = path;
    router.push(path);
  };

  const handleTabsEdit = (targetName: TabPaneName | undefined, action: 'add' | 'remove') => {
    if (action === 'remove') {
      if (targetName === HOME_TAB) return;
      const tabs = editableTabs.value;
      let activeName = editableTabsValue.value;

      if (activeName === targetName) {
        const idx = tabs.findIndex(tab => tab.name === targetName);
        const nextTab = tabs[idx + 1] || tabs[idx - 1];
        if (nextTab) {
          activeName = nextTab.name;
        }
      }

      editableTabs.value = tabs.filter(tab => tab.name !== targetName);
      navigateTo(activeName);
    }
  };

  const handleTabClick = (tab: TabsPaneContext) => {
    navigateTo(tab.paneName as string);
  };

  // 右键菜单状态
  const ctxMenuVisible = ref(false);
  const ctxMenuX = ref(0);
  const ctxMenuY = ref(0);
  const ctxTargetTab = ref('');

  const openCtxMenu = (e: MouseEvent) => {
    const tabEl = (e.target as HTMLElement)?.closest('.el-tabs__item');
    if (!tabEl) return;
    e.preventDefault();
    const tabId = tabEl.id;
    ctxTargetTab.value = tabId.replace(/^tab-/, '');
    ctxMenuX.value = e.clientX;
    ctxMenuY.value = e.clientY;
    ctxMenuVisible.value = true;
  };

  const closeCtxMenu = () => { ctxMenuVisible.value = false; };

  const ctxCloseCurrent = () => {
    closeCtxMenu();
    if (ctxTargetTab.value === HOME_TAB) return;
    handleTabsEdit(ctxTargetTab.value, 'remove');
  };

  const ctxCloseOthers = () => {
    closeCtxMenu();
    editableTabs.value = editableTabs.value.filter(
      t => t.name === HOME_TAB || t.name === ctxTargetTab.value
    );
    navigateTo(ctxTargetTab.value);
  };

  const ctxCloseLeft = () => {
    closeCtxMenu();
    const idx = editableTabs.value.findIndex(t => t.name === ctxTargetTab.value);
    editableTabs.value = editableTabs.value.filter(
      (t, i) => t.name === HOME_TAB || i >= idx
    );
    if (!editableTabs.value.find(t => t.name === editableTabsValue.value)) {
      navigateTo(ctxTargetTab.value);
    }
  };

  const ctxCloseRight = () => {
    closeCtxMenu();
    const idx = editableTabs.value.findIndex(t => t.name === ctxTargetTab.value);
    editableTabs.value = editableTabs.value.filter(
      (t, i) => t.name === HOME_TAB || i <= idx
    );
    if (!editableTabs.value.find(t => t.name === editableTabsValue.value)) {
      navigateTo(ctxTargetTab.value);
    }
  };

  const ctxCloseAll = () => {
    closeCtxMenu();
    editableTabs.value = editableTabs.value.filter(t => t.name === HOME_TAB);
    navigateTo(HOME_TAB);
  };

  // 路由变化时自动添加标签
  watch(() => route.path, (newPath) => {
    const tabExists = editableTabs.value.some(tab => tab.name === newPath);
    if (!tabExists) {
      editableTabs.value.push({
        title: (route.meta.name as string) || newPath,
        name: newPath,
        content: `${route.meta.name} 内容`,
        closable: true,
      });
    }
    editableTabsValue.value = newPath;
  }, { immediate: true });

  return {
    editableTabs,
    editableTabsValue,
    handleTabsEdit,
    handleTabClick,
    ctxMenuVisible,
    ctxMenuX,
    ctxMenuY,
    openCtxMenu,
    closeCtxMenu,
    ctxCloseCurrent,
    ctxCloseOthers,
    ctxCloseLeft,
    ctxCloseRight,
    ctxCloseAll,
  };
});
