<template>
  <div class="bg-light">
    <br>
    <b-container class="mb-4">
      <b-row>
        <p class="ml-3">
          <b-button v-for="item in tags" :key="item.tagName" class="mr-3"  squared :variant=getRandomVariant()
            @click="searchTag(item.tagName)">#{{ item.tagName }}
          </b-button>
        </p>
      </b-row>
      <b-row>
        <b-nav class="custom-nav">
          <b-nav-item @click="setActiveTab('most_viewed')" :active="activeTab === 'most_viewed'"><strong>most
              viewed</strong></b-nav-item>
          <b-nav-item @click="setActiveTab('most_liked')" :active="activeTab === 'most_liked'"><strong>most
              liked</strong></b-nav-item>
          <b-nav-item @click="setActiveTab('recent')"
            :active="activeTab === 'recent'"><strong>recent</strong></b-nav-item>
        </b-nav>
      </b-row>
    </b-container>

    <HomeCards :posts="posts" />
    <b-container>
      <b-row class="justify-content-center" style="height: 10vh;">
        <h4 @click="loadPosts" class="load-more"><strong>load more<b-icon icon="chevron-down"></b-icon></strong></h4>
      </b-row>
    </b-container>

  </div>
</template>

<script>
import HomeCards from '../../components/home/HomeCards.vue';
import postsMockData from '@/mocks/posts.js'

export default {
  components: {
    HomeCards

  },
  data() {
    return {
      tags: [],
      posts: postsMockData,
      page: 0,
      activeTab: 'most_viewed'  // default active tab
    };
  },
  mounted() {
    this.loadTags();
    this.posts = [];
    this.loadPosts();
  },
  methods: {
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
    setActiveTab(tabName) {
      this.activeTab = tabName;
      this.page = 0; // Reset page
      this.posts = []; // Reset posts
      this.loadPosts();
    },
    loadPosts() {
      var self = this;
      self.page += 1;
      // Adjust the endpoint based on the activeTab
      let endpoint = '/posts/' + this.activeTab;

      this.axios.get(endpoint, {
        params: {
          page: self.page // Add the updated page number as a parameter
        }
      })
        .then(response => {
          if (response.data.code === 1) {
            if (response.data.data.rows.length === 0) {
              this.$notify('info', 'No more posts', 'info');
              return;
            }
            self.posts.push(...response.data.data.rows);
          } else {
            this.$notify('error', response.data.msg, 'danger');
            console.log(response.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
          this.$notify('error', error, 'danger');
        });
    },
    loadTags() {
      var self = this;
      this.axios.get('/posts/tags')
        .then(response => {
          if (response.data.code === 1) {
            self.tags = response.data.data.rows;
          } else {
            this.$notify('error', response.data.msg, 'danger');
            console.log(response.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    searchTag(tagName) {
      this.$router.push({
                path: '/search',
                query: {
                    category: 'tag',
                    query: tagName
                }
            })
    }
  }

}
</script>


<style>
.custom-nav .nav-link {
  font-size: 1.5rem;
  color: #6c757d;
}

.custom-nav .nav-link:hover {
  opacity: 0.7;
}

.custom-nav .nav-link.active {
  color: #0aac8e;
}

.load-more {
  color: #0aac8e;
}

.load-more:hover {
  opacity: 0.7;
  cursor: pointer;
  /* change the cursor to a hand pointer when hovering */
}
</style>