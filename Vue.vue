<template>
  <div v-bind:attr="attr" v-if="x">Vue.vue</div>
  <div v-else-if="n">Vue.vue</div>
  <div v-else>Vue.vue</div>
  <input v-model="input">
  <span v-show="x">Vue.vue</span>
</template>

<script lang="ts" setup>
// pnpm create vue@latest
// pnpm add @tsconfig/node24 等 --> 单独安装 --> vscode ctrl shift p 搜索 Restart Vue and TS servers
// FPS: Frames Per Second 每秒帧数

</script>

<style lang="sass" scoped>
</style>
