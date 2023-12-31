<template>
    <div>
        <NavBar ref="navbar" :userInfo="userData" :isSidebarOpen="isSidebarOpen" @toggleSidebar="toggleSidebar" @logout="logout" />
        <SideBar :navList="navList" :isSidebarOpen="isSidebarOpen" />
        <MainContent :isSidebarOpen="isSidebarOpen">
            <router-view></router-view>
        </MainContent>
    </div>
</template>
  
<script>
import NavBar from '../components/common/NavBar.vue';
import SideBar from '../components/common/SideBar.vue';
import MainContent from '../components/common/MainContent.vue';


export default {
    name: 'IndexView',
    components: {
        NavBar,
        SideBar,
        MainContent
    },
    data() {
        return {
            navList: [
                {
                    name: 'Home',
                    path: '/home',
                    icon: 'house-door'
                },
                {
                    name: 'My Notes',
                    path: '/my_notes',
                    icon: 'pencil-square'
                },
                {
                    name: 'User Center',
                    path: '/user',
                    icon: 'person'
                },
            ],
            isSidebarOpen: true, // control sidebar
            marginTop: '0px', // control main content margin-top
        };
    },
    mounted() {
        this.adjustMarginTop();
        window.addEventListener('resize', this.adjustMarginTop);
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.adjustMarginTop);
    },
    computed: {
        userData() {
            return this.$store.state.userData;
        }
    },

    methods: {
        toggleSidebar() {
            this.isSidebarOpen = !this.isSidebarOpen;
        },
        logout() {
            localStorage.removeItem('userToken');
            this.$store.dispatch('logout');
            window.location.href = '/';

        },
        adjustMarginTop() {
            if (this.$refs.navbar) {
                let navbarHeight = this.$refs.navbar.$el.offsetHeight;
                this.marginTop = `${navbarHeight}px`;
            }
        },

    },
}
</script>
