<script setup>
import { defineProps, inject } from 'vue'

const props = defineProps({
  title: String
})

const selectedTitle = inject('selectedTitle')
</script>

<template>
  <div class="tabs__content" v-show="selectedTitle === title">
    <slot />
  </div>
</template>

<style scoped>
.tabs__content {
  border-radius: 0px 30px 30px 30px;
}
</style>
