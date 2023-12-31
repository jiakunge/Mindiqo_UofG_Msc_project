<template>
  <b-navbar toggleable="sm" type="dark" variant="dark" sticky>
    <b-button pill size="sm" variant="outline-light" @click="$emit('toggleSidebar')" class="ml-3 mr-3">
      <b-icon v-if="isSidebarOpen" icon="chevron-bar-left"></b-icon>
      <b-icon v-else icon="chevron-bar-right"></b-icon>
    </b-button>
    <b-navbar-brand>
      <img src="@/assets/logo_white.png" style="max-height: 30px; width: auto;" class="d-inline-block align-top"
        alt="mindiqo">
    </b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class='ml-auto'>
        <SearchBar />
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <NotificationComponent class="mr-2"/>
        <b-nav-item-dropdown right v-if="userInfo">
          <template #button-content>
            <b-avatar :src="userInfo.avatar" size="2em"></b-avatar>
          </template>
          <b-dropdown-header>{{ userInfo.username }}</b-dropdown-header>
          <b-dropdown-item :to="'/user/'+userInfo.username">Profile</b-dropdown-item>
          <b-dropdown-divider></b-dropdown-divider>
          <b-dropdown-item href="#" @click="$emit('logout')">Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item v-else>
          <b-button pill size="sm" variant="outline-light" to="/login">
            Login
          </b-button>
        </b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>
  
<script>
import SearchBar from './SearchBar.vue';
import NotificationComponent from './NotificationComponent.vue';
export default {
  components: {
    SearchBar,
    NotificationComponent,
  },
  props: ['userInfo', 'isSidebarOpen'],
  data() {
    return {
      searchContent: '',
    };
  },
}
</script>
  