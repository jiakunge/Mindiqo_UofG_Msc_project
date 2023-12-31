<template>
    <div>
        <br>
        <b-container class="post-details">
            <b-row>
                <b-col cols="12" md="9">
                    <h1>{{ post.title }}</h1>
                    <p class="post-header">
                        <span class="mr-3">Posted at {{ $formatDateTime(post.createTime) }}</span>
                        <span class="mr-3">Author: <span class="mr-2">{{ post.creator.username }}</span>
                            <b-avatar :to="'/user/' + post.creator.username" variant="primary"
                                :src="post.creator.avatar"></b-avatar></span>
                        <span class="mr-3"><b-icon class="mr-1" icon="hand-thumbs-up"></b-icon>{{ post.likeCount }}</span>
                        <span class="mr-3"><b-icon class="mr-1" icon="chat-left-text"></b-icon> {{ post.commentCount
                        }}</span>
                        <span><b-icon class="mr-1" icon="eye"></b-icon>{{ post.viewCount }}</span>
                    </p>

                    <p>
                        <b-button v-for="item in post.tags" :key="item.tagName" class="mr-2 mb-2" squared
                            :variant=getRandomVariant() size="sm">#{{ item.tagName }}
                        </b-button>
                    </p>
                    <hr>
                    <div class="el-tiptap-editor__content bg-light" v-html="post.content"></div>
                </b-col>

                <b-col cols="12" md="3">
                    <h4><b>Notes published by this author</b></h4>
                    <ul>
                        <li class="list-group-item" v-for="item in creatorPosts.slice(0, 6)" :key=item.id>
                            <router-link :to="'/home/post' + item.id">{{ item.title }}</router-link>
                        </li>
                    </ul>
                </b-col>
            </b-row>
            <b-row>
                <b-col clos="12" md="8">
                    <br>
                    <b-button variant="light" class="mr-3" @click="like()">
                        <b-icon class="mr-1" :icon="likeIcon()"></b-icon>
                        {{ post.likeCount }}
                    </b-button>
                    <b-button variant="light" class="mr-3" @click="comment()">
                        <b-icon class="mr-1" icon="chat-left-text"></b-icon>
                        {{ post.commentCount }}
                    </b-button>
                </b-col>
            </b-row>

            <b-row>
                <b-col clos="12" md="8">
                    <hr>
                    <CommentsPart :comments="this.comments"></CommentsPart>
                </b-col>
            </b-row>

            <b-row id='comment-bottom'>
                <b-col clos="12" md="8">
                    <hr>
                    <b-form @submit.prevent="onSubmit">
                        <b-form-group id="comment-form-group" label="Comment" label-for="comment">
                            <b-form-textarea id="comment" v-model="commentInput" placeholder="Enter your comment"
                                rows="3"></b-form-textarea>
                        </b-form-group>
                        <b-button type="submit" variant="secondary" @click="submitComment()">Submit</b-button>
                    </b-form>
                    <br>
                </b-col>
            </b-row>
        </b-container>


    </div>
</template>

<script>
import CommentsPart from '../../../components/home/CommentsPart.vue';
import { PostViewMock } from '@/mocks/posts';

export default {
    components: {
        CommentsPart,
    },
    data() {
        return {
            post: PostViewMock.post,
            comments: PostViewMock.comments,
            creatorPosts: [],
            commentInput: null,
            hasLiked: false,

        };
    },
    methods: {
        loadPost(id) {
            var self = this;
            this.axios.get('/posts/' + id)
                .then(response => {
                    if (response.data.code === 1) {
                        self.post = response.data.data;
                        this.loadCreatorPosts(self.post.creator);
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        check(id) {
            var self = this;
            this.axios.get('/posts/' + id + '/is_liked')
                .then(response => {
                    if (response.data.code === 1) {
                        self.hasLiked = response.data.data;
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        loadComments(id) {
            var self = this;
            this.axios.get('/posts/' + id + '/comments')
                .then(response => {
                    if (response.data.code === 1) {
                        self.comments = response.data.data;
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    console.log(error);
                });

        },
        loadCreatorPosts(creator) {
            var self = this;
            this.axios.post('/user/post', {
                id: creator.id,
            })
                .then(response => {
                    if (response.data.code === 1) {
                        self.creatorPosts = response.data.data;
                    } else {
                        this.$notify('error', response.data.msg, 'danger');
                        console.log(response.data.msg);
                    }
                })
                .catch(error => {
                    console.log(error);
                });

        },
        comment() {
            const commentBottomElement = document.getElementById('comment-bottom');
            if (commentBottomElement) {
                commentBottomElement.scrollIntoView({ behavior: 'smooth' });
            }
        },
        getRandomVariant() {
            const variants = [
                'outline-primary',
                'outline-secondary',
                'outline-success',
                'outline-danger',
                'outline-warning',
                'outline-info',
                'outline-dark'
            ];

            return variants[Math.floor(Math.random() * variants.length)];

        },
        likeIcon() {
            return this.hasLiked ? 'hand-thumbs-up-fill' : 'hand-thumbs-up';
        },
        like() {
            if (this.hasLiked) {
                // If the user has already liked the post, decrement the like count and update the state to "not liked"
                this.axios.delete('/posts/' + this.post.id + '/unlike', {
                })
                    .then(response => {
                        if (response.data.code === 1) {
                            this.post.likeCount--;
                            this.hasLiked = false;
                            this.$notify('info', 'cancel like.', 'info');
                        } else {
                            this.$notify('error', response.data.msg, 'danger');
                            console.log(response.data.msg);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            } else {
                // If the user hasn't liked the post, increment the like count and update the state to "liked"
                this.axios.post('/posts/' + this.post.id + '/like', {
                })
                    .then(response => {
                        if (response.data.code === 1) {
                            this.post.likeCount++;
                            this.hasLiked = true;
                            this.$notify('info', 'liked.', 'info');
                        } else {
                            this.$notify('error', response.data.msg, 'danger');
                            console.log(response.data.msg);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        },
        submitComment() {
            if (this.commentInput) {
                this.axios.post('/posts/' + this.post.id + '/comment', {
                    postId: this.post.id,
                    content: this.commentInput
                })
                    .then(response => {
                        if (response.data.code === 1) {
                            this.$notify('success', 'Add a comment', 'success');
                            this.loadComments(this.$route.params.id);
                            this.commentInput = null;
                        } else {
                            this.$notify('error', response.data.msg, 'danger');
                            console.log(response.data.msg);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                        this.$notify('error', error, 'danger');
                    });
            }
        },
    },
    mounted() {
        if (this.$route.params.id) {
            this.loadPost(this.$route.params.id);//load post
            this.loadComments(this.$route.params.id);//load comments
            if (localStorage.getItem('userToken')) {
                this.check(this.$route.params.id)//check if the user has liked the post
            }
        }

    },
    watch: {

    }
}
</script>

<style>
.list-group-item a {
    color: #0aac8e;
}

.post-header {
    color: gray;
}

.post-details {
    text-align: left;
}
</style>