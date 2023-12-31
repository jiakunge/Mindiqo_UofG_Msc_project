<template>
    <b-container class="mt-4" style="text-align:left;">
        <h1 class="font-weight-bold mb-3">Notification Center</h1>
        <b-row class="justify-content-md-center">
            <b-col cols="12" md="9" sm="8">
                <!-- Notification Recent -->
                <b-card no-body class="shadow-sm mb-3">
                    <b-card-header class="p-3">
                        <h6 class="m-0">Recent</h6>
                    </b-card-header>
                    <b-card-body class="p-0">
                        <b-list-group flush>
                            <b-list-group-item v-for="item in recentNotifications" :key="item.id"
                                class="d-flex align-items-center">
                                <b-avatar :src=item.sender.avatar class="mr-3"
                                :to="'/user/'+item.sender.username"
                                ></b-avatar>
                                <div @click="viewNotification(item)" class="font-weight-bold mr-3 hover-effect">
                                    <div class="text-truncate" >{{ item.sender.username }}</div>
                                    <div class="small">{{item.content}}</div>
                                </div>
                                <div class="ml-auto mb-auto">
                                    <b-button @click="deleteNotification(item)" size="sm" variant="outline-danger">
                                        <b-icon icon="trash"></b-icon>
                                    </b-button>
                                    <b-button @click="checkNotification(item)" class="ml-2" size="sm"
                                        variant="outline-success">
                                        <b-icon icon="check-lg"></b-icon>
                                    </b-button>
                                    <div class="text-right text-muted pt-1">{{ $formatDateTime(item.createTime) }}</div>
                                </div>
                            </b-list-group-item>
                        </b-list-group>
                    </b-card-body>
                </b-card>
                            
                <!-- Notification Earlier -->

                <b-card no-body class="shadow-sm mb-3">
                    <b-card-header class="p-3">
                        <h6 class="m-0">Earlier</h6>
                    </b-card-header>
                    <b-card-body class="p-0">
                        <b-list-group flush>
                            <b-list-group-item v-for="item in notifications" :key="item.id"
                                class="d-flex align-items-center">
                                <b-avatar :src=item.sender.avatar class="mr-3"
                                :to="'/user/'+item.sender.username"
                                ></b-avatar>
                                <div @click="viewRelatedPost(item)" class="font-weight-bold mr-3 hover-effect">
                                    <div class="text-truncate" >{{ item.sender.username }}</div>
                                    <div class="small">{{item.content}}</div>
                                </div>
                                <div class="ml-auto mb-auto">
                                    <b-button @click="deleteNotification(item)" size="sm" variant="outline-danger">
                                        <b-icon icon="trash"></b-icon>
                                    </b-button>
                                    <div class="text-right text-muted pt-1">{{ $formatDateTime(item.createTime) }}</div>
                                </div>
                            </b-list-group-item>
                        </b-list-group>
                    </b-card-body>
                </b-card>
            </b-col>
        </b-row>
    </b-container>
</template>
  
<script>
export default {
    data() {
        return {

            recentNotifications: [],
            notifications: []
        };
    },
    mounted() {
        this.load();
    },
    methods: {
        load(){
            this.loadRecentNotifications();
            this.loadNotifications();
            this.$store.dispatch('loadUnreadCount');
        },
        loadRecentNotifications() {
            var self = this;
            this.axios.get('/notifications/unread')
                .then(response => {
                    if (response.data.code === 1) {
                        self.recentNotifications = response.data.data;
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    this.$notify('error', error, 'danger');
                    console.log(error);
                });
        },
        loadNotifications() {
            var self = this;
            this.axios.get('/notifications/read')
                .then(response => {
                    if (response.data.code === 1) {
                        self.notifications = response.data.data;
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    this.$notify('error', error, 'danger');
                    console.log(error);
                });
        },
        viewRelatedPost(item){
            this.$router.push('/home/post/' + item.id);
        },
        viewNotification(item) {
            this.checkNotification(item);
            this.viewRelatedPost(item);
            
        },
        deleteNotification(item) {
            var self = this;
            this.axios.delete('/notifications/' + item.id)
                .then(response => {
                    if (response.data.code === 1) {
                        self.load();
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    this.$notify('error', error, 'danger');
                    console.log(error);
                });
            
        },
        checkNotification(item) {
            var self = this;
            this.axios.post('/notifications/read',{id:item.id})
                .then(response => {
                    if (response.data.code === 1) {
                        self.load();
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    this.$notify('error', error, 'danger');
                    console.log(error);
                });
        }
    }
};
</script>
  
<style scoped>
.hover-effect:hover {
    opacity: 0.7;
    cursor: pointer;
}
</style>
