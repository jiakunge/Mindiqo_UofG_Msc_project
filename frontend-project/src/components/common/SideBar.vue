<template>
  <div>
    <div class="lg-sidebar">
      <transition name="slide">
        <div class="sidebar bg-dark" v-if="isSidebarOpen">
          <b-nav vertical class="w-100 mt-2">
            <b-nav-item class="custom-nav-item" active-class="bg-secondary" v-for="(item, i) in navList" :to="item.path"
              :key="i">
              <b-icon variant="light" :icon="item.icon"></b-icon>
              <span class="text-light">&nbsp;&nbsp;&nbsp;{{ item.name }}</span>
            </b-nav-item>
          </b-nav>
        </div>
      </transition>
      <transition name="fade">
        <div class="small-sidebar bg-dark" v-if="!isSidebarOpen">
          <b-nav vertical class="w-100 mt-2">
            <b-nav-item active-class="bg-secondary" v-for="(item, i) in navList" :to="item.path" :key="i">
              <b-icon variant="light" :icon="item.icon"></b-icon>
            </b-nav-item>
          </b-nav>
        </div>
      </transition>
    </div>
  </div>
</template>
  
<script>
export default {
  props: ['navList', 'isSidebarOpen'],
}
</script>
  
<style scoped>
.slide-enter-active,
.slide-leave-active {
  transition: all .5s ease-out;
}

.slide-enter,
.slide-leave-to {
  transform: translateX(-100%);
  width: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity .5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.sidebar {
  width: 180px;
  height: 100vh;
  position: fixed;
  left: 0;
}

.small-sidebar {
  width: 64px;
  height: 100vh;
  position: fixed;
  left: 0;
}

.custom-nav-item {
  width: 100%;
  text-align: left;
}
</style>
  