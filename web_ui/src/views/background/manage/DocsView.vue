<template>
  <div v-loading="loading" :style="'height:'+ height">
    <iframe ref="myIframe"
            :src="src"
            frameborder="no"
            scrolling="auto"
            style="width: 100%;height: 100%"
            @load="handleIframeLoad"/>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {getBaseUrl} from "@/utils/env";

const src = ref(getBaseUrl() + "/doc.html")
const height = ref(document.documentElement.clientHeight - 94.5 + "px;")
let loading = ref(true)

const init = () => {
  const that = this;
  window.onresize = function temp() {
    that.height = document.documentElement.clientHeight - 94.5 + "px;";
  };
}

const handleIframeLoad = () => {
  loading.value = false;
}

onMounted(() => {
  init()
})
</script>
