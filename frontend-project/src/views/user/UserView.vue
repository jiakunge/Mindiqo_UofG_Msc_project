<template>
    <div class="user bg-light">
        <b-jumbotron fluid class="border-round-0 min-50vh" :style="{ backgroundImage: 'url(' + user.bgImg + ')' }">
        </b-jumbotron>
        <b-container class="mb-4">
            <b-img :src="user.avatar" class="user-avatar mb-4 rounded-circle bg-secondary" width="128" :alt="user.username"
                @click="editAvatar()"></b-img>
            <h1 class="font-weight-bold title">{{ user.username }}</h1>
            <p>
                {{ user.bio }} <b-button v-if="isMyself" class="ml-4" size="sm" variant="outline-dark" @click="editBio()">Edit</b-button>
            </p>
        </b-container>
        <UserCards :posts="posts" :isMyself="isMyself" @openDeleteDialog="handleOpenDeleteDialog"></UserCards>
        <UserDialog ref="userDialog" @changeBio="handleChangeBio" @changeAvatar="handleChangeAvatar"
        @deletePost="handleDeletePost"></UserDialog>
    </div>
</template>

<script>
import UserCards from '../../components/user/UserCards.vue';
import UserDialog from '../../components/user/UserDialog.vue';
export default {
    components: {
        UserCards,
        UserDialog,
    },
    data() {
        return {
            user: {
            },
            posts: [],
            isMyself: false,

        };
    },
    methods: {
        loadUser(username) {
            var self = this;
            this.axios.get('/user/' + username)
                .then(response => {
                    if (response.data.code === 1) {
                        self.user = response.data.data;
                        this.loadPosts(self.user.id, self);
                        this.checkIsMyself();
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
        loadPosts(userId, self) {
            this.axios.post('/user/post', {
                id: userId
            })
                .then(response => {
                    if (response.data.code === 1) {
                        self.posts = response.data.data;
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
        checkIsMyself() {
            if (this.$store.state.userData.id === this.user.id) {
                this.isMyself = true;
            }
        },

        //******************************************************/
        // Update User method
        updateUser(field, data) {

            let payload = {};
            payload[field] = data;

            this.axios.post('/user/update', payload)
                .then(response => {
                    if (response.data.code === 1) {
                        this.user[field] = data;
                        this.$notify('success', 'update success', 'success');
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
        // Edit Bio
        editBio() {
            this.$refs.userDialog.receiveData('changeBio', this.user.bio);
        },
        // Edit Avatar
        editAvatar() {
            if (!this.isMyself) {
                return;
            }
            this.$refs.userDialog.receiveData('changeAvatar', this.user.avatar);
        },
        handleChangeBio(bio) {
            this.updateUser('bio', bio);
        },
        handleChangeAvatar(avatar) {
            this.updateUser('avatar', avatar);
            this.$store.commit('SET_AVATAR', avatar);
        },
        handleOpenDeleteDialog(postId){
            this.$refs.userDialog.receiveData('deletePost', postId);
        },
        handleDeletePost(postId){
            console.log(postId);
            this.axios.delete('/posts/'+postId+'/delete')
                .then(response => {
                    if (response.data.code === 1) {
                        this.$notify('success', 'delete success', 'success');
                        //delete post from posts
                        for (let i = 0; i < this.posts.length; i++) {
                            if (this.posts[i].id === postId) {
                                this.posts.splice(i, 1);
                                break;
                            }
                        }
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




    },
    mounted() {
        if (this.$route.params.username) {
            this.loadUser(this.$route.params.username);
        }

    },

}
</script>

<style>
.user {
    text-align: left;
}

.user-avatar {
    margin-top: -100px;
}

.user-avatar:hover {
    filter: brightness(80%);
    cursor: pointer;
}
</style>