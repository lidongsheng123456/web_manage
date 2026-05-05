<template>
  <div class="wscn-http404-container">
    <div class="wscn-http404">
      <div class="pic-404">
        <img alt="404" class="pic-404__parent" src="../../assets/404_images/404.png">
        <img alt="404" class="pic-404__child left" src="../../assets/404_images/404_cloud.png">
        <img alt="404" class="pic-404__child mid" src="../../assets/404_images/404_cloud.png">
        <img alt="404" class="pic-404__child right" src="../../assets/404_images/404_cloud.png">
      </div>
      <div class="bullshit">
        <div class="bullshit__oops">
          404错误!
        </div>
        <div class="bullshit__headline">
          {{ '找不到网页！' }}
        </div>
        <div class="bullshit__info">
          对不起，您正在寻找的页面不存在。尝试检查URL的错误，然后按浏览器上的刷新按钮或尝试在我们的应用程序中找到其他内容。
        </div>
        <router-link class="bullshit__return-home" to="/">
          返回首页
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/not-found";
</style>
