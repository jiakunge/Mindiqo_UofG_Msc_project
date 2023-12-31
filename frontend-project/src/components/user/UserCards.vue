<template>
    <div class="box-content box-1 bg-light">
        <b-container>
            <b-row>

                <b-col v-for="item in posts" :key="item.id" cols="12" md="4">
                    <div class="box-item">
                        <div class="image-container">
                            <b-img :src="item.img"></b-img>
                        </div>
                        <div class="content">
                            <h3>{{ item.title }}</h3>
                            <p class="auto-wrap">{{ item.abs }}</p>
                            <div class="no-warp d-flex justify-content-between align-items-center">
                                <router-link :to="'/home/post/' + item.id">Read More...</router-link>
                                <span>
                                    <span class="mr-2">{{ $formatDateTime(item.createTime) }} </span>
                                </span>
                                <span v-if="isMyself">
                                    <b-dropdown class="ml-auto" size="sm" variant="link" toggle-class="text-decoration-none"
                                        no-caret lazy>
                                        <template #button-content>
                                            <b-icon icon="justify-right" style="color: #0aac8e;">
                                            </b-icon>
                                        </template>
                                        <b-dropdown-item-button @click="toThisNote(item.noteId)">To this
                                            Note</b-dropdown-item-button>
                                        <b-dropdown-divider></b-dropdown-divider>
                                        <b-dropdown-item-button variant="danger" @click="deletePost(item)">
                                            <b-icon icon="trash-fill" aria-hidden="true"></b-icon>
                                            Delete
                                        </b-dropdown-item-button>
                                    </b-dropdown>
                                </span>

                            </div>
                        </div>
                    </div>
                </b-col>

            </b-row>
        </b-container>
    </div>
</template>

<script>

export default {
    props: {
        posts: Array, // Define the 'posts' prop as an array
        isMyself: Boolean,
    },

    data() {
        return {
        };
    },
    methods: {
        toThisNote(noteId) {
            this.$router.push('/my_notes/edit/' + noteId);
        },
        deletePost(post) {
            this.$emit('openDeleteDialog', post.id)
        },

    },
};
</script>

<style >
.image-container {
    position: relative;
    width: 100%;        
    padding-top: 61.8%;   
    overflow: hidden;       
}

.image-container img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;     
}

.auto-wrap {
    word-wrap: break-word;
}

.no-wrap {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.box-content.box-1 .box-item {
    background-color: #fff;
    margin: 0 auto 30px;
    max-width: 500px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
    text-align: left;
}

.box-content.box-1 .box-item:hover img {
    opacity: 0.9;
}

.box-content.box-1 .box-item .content {
    padding: 10px 30px 30px;
}

.box-content.box-1 .box-item img {
    width: 100%;
}

.box-content.box-1 .box-item a {
    color: #0aac8e;
    font-weight: bold;
}

.box-content.box-1 .box-item .content h3 {
    font-weight: 500;
    color: #333;
    font-size: 24px;
    margin-bottom: 20px;
}

.box-content.box-1 .box-item .content span {
    color: #bbb;
    font-size: 15px;
}
</style>




