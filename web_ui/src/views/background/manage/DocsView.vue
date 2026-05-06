<template>
  <div v-loading="loading" :style="'height:' + height">
    <iframe ref="myIframe" :src="src" frameborder="no" scrolling="auto" style="width: 100%;height: 100%"
      @load="handleIframeLoad" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

const src = ref(import.meta.env.VUE_APP_API_TARGET + "/doc.html")
const height = ref(document.documentElement.clientHeight - 94.5 + "px;")
const loading = ref(true)

const init = () => {
  window.onresize = () => {
    height.value = document.documentElement.clientHeight - 94.5 + "px;";
  };
}

const handleIframeLoad = () => {
  loading.value = false;
}

onMounted(() => {
  init()
})
</script>
