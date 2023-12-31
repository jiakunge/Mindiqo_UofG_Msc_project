<template>
    <div>
        <b-form @submit.prevent="onSubmit">
            <b-form-group class="mb-0">
                <b-input-group class="no-rounded">

                    <!-- Category Dropdown -->
                    <b-input-group-prepend class="narrow-prepend">
                        <b-form-select class="rounded-0" v-model="selectedCategory" :options="categories"
                            placeholder="Category"></b-form-select>
                    </b-input-group-prepend>

                    <!-- Search Input -->
                    <b-form-input class="rounded-0" v-model="searchQuery" placeholder="Input to Search"></b-form-input>

                    <!-- Search Button -->
                    <b-input-group-append>
                        <b-button class="custom-btn" squared type="submit">
                            <b-icon icon="search"></b-icon>
                        </b-button>
                    </b-input-group-append>

                </b-input-group>
            </b-form-group>
        </b-form>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            selectedCategory: 'title',
            searchQuery: '',
            categories: [
                'title',
                'tag',
                'content',
            ]
        };
    },
    methods: {
        onSubmit() {
            this.searchQuery = this.searchQuery.trim();
            this.$router.push({
                path: '/search',
                query: {
                    category: this.selectedCategory,
                    query: this.searchQuery
                }
            }).catch(err => {
                if (err.name === 'NavigationDuplicated') {
                    window.location.reload();
                } else {
                    console.error(err);
                }
            });
            this.searchQuery = '';
        }
    }
};
</script>
<style scoped>
.no-rounded .input-group-text,
.no-rounded .form-control {
    border-radius: 0 !important;
}

.narrow-prepend {
    width: 100px;
}

.custom-btn {
    background-color: #0aac8e;
    color: #ffffff;
    border: none;
}
</style>