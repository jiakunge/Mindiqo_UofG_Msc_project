<template>
    <div class="main-content" :style="{ 'margin-left': isSidebarOpen ? '180px' : '64px' }">
      <slot></slot>
    </div>
  </template>
  
  <script>
  export default {
    props: ['isSidebarOpen',],
  }
  </script>
  
  <style scoped>
  .main-content {
    transition: margin-left .5s ease;
  }
  </style>
  