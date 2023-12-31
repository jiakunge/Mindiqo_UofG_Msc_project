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
                            <h3 :title="item.title">{{ item.title }}</h3>
                            <p class="auto-wrap">{{ item.abs }}</p>
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <router-link :to="'/home/post/' + item.id">Read More...</router-link>
                                <span>{{ $formatDateTime(item.createTime) }} </span>
                            </div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span>
                                    <span class="mr-2"><b-icon class="mr-1" icon="hand-thumbs-up"></b-icon>
                                        {{ item.likeCount }}
                                    </span>
                                    <span class="mr-2"><b-icon class="mr-1" icon="chat-left-text"></b-icon>
                                        {{ item.commentCount }}
                                    </span>
                                    <span class="mr-2"><b-icon class="mr-1" icon="eye"></b-icon>
                                        {{ item.viewCount }}
                                    </span>
                                </span>
                                <span class="username-container">
                                    <b-avatar class="mr-2" :to="'/user/' + item.creator.username" variant="primary"
                                        :src="item.creator.avatar"></b-avatar>
                                    <span class="overflow-ellipsis">{{ item.creator.username }}</span>
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
    },

    data() {
        return {
        };
    },
    methods: {
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
    height: 100px;
    overflow: hidden;
    word-wrap: break-word;
}

.username-container {
    display: flex;
    align-items: center;
    max-width: 50%;
}

.overflow-ellipsis {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: calc(100% - 40px);
}


.box-content.box-1 .box-item .content h3 {
    max-height: 1.2em;
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




