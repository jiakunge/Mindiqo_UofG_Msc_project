<template>
    <div class="notification d-flex align-items-center">
        <b-icon @click="toNotification()" icon="bell" variant="light" font-scale="1.5"></b-icon>
        <b-badge v-show="unreadCount !== 0" variant="danger" class="notification-badge">{{ unreadCount }}</b-badge>
    </div>
</template>

<script>
export default {
    data() {
        return {
        }
    },
    computed: {
        unreadCount() {
            return this.$store.state.unreadCount;
        }
    },
    mounted() {
        this.$store.dispatch('loadUnreadCount');
    },
    methods: {
        toNotification() {
            this.$router.push('/notification');
        },
    }
}
</script>

<style scoped>
.notification {
    width: 2em;
}

.notification:hover {
    opacity: 0.7;
    cursor: pointer;
}

.notification-badge {
    margin-top: -10px;
}
</style>