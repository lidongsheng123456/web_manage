<template>
  <div>
    前台
    <router-view/>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import store from "@/store";

let userInfo = ref({});

const getUserInfo = () => {
  store.dispatch('user/queryCurrentFrontUserInfo').then(() => {
    userInfo.value = store.getters["user/frontUserInfo"]
  })
}

onMounted(() => {
  getUserInfo()
});
</script>

<style scoped>

</style>
