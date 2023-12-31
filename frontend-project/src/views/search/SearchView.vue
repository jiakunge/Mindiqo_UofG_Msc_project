<template >
    <b-container class="my-3" style="text-align: left">
        <h3>Search Results</h3>
        <p><strong>{{ total }}</strong>&nbsp;results found for&nbsp;<strong>{{ category }}</strong>&nbsp;with the
            term&nbsp;<strong>{{ query }}</strong>. </p>
        <hr>
        <HomeCards :posts="results" />

        <div v-if="total === 0" class="mt-3">
            <h1> No results found.</h1>
        </div>
        <hr>
        <b-row class="justify-content-center" style="height: 10vh;">
        <h4 @click="loadResults" class="load-more"><strong>load more<b-icon icon="chevron-down"></b-icon></strong></h4>
        </b-row>
    </b-container>
</template>
  
<script>
import HomeCards from '@/components/home/HomeCards.vue';
export default {
    components: {
        HomeCards
    },
    props: {
        category: {
            type: String,
            default: ''
        },
        query: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            page: 0,
            total: 0,
            results: []
        };
    },
    mounted() {
        this.results = [];
        this.loadResults();
    },
    methods: {
        loadResults() {
            var self = this;
            let endpoint = 'posts/search';
            self.page += 1;
            this.axios.get(endpoint, {
                params: {
                    page: self.page,
                    [self.category]: self.query,
                }
            })
                .then(response => {
                    if (response.data.code === 1) {
                        self.total = response.data.data.total;
                        if (response.data.data.rows.length === 0) {
                            this.$notify('info', 'No more posts', 'info');
                            return;
                        }
                        self.results.push(...response.data.data.rows);
                    } else {
                        this.$notify('error', response.data.message, 'danger');
                    }
                })
                .catch(error => {
                    this.$notify('error', error.message, 'danger');
                });
        }
    },
}
</script>

<style scoped></style>
