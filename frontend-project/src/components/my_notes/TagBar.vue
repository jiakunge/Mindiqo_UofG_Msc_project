<template>
  <div>
    <b-form-tags v-model="value" no-outer-focus class="no-border">
      <template v-slot="{ tags, disabled, addTag, removeTag }">
      <span class="tag-list">
        <ul v-if="tags.length > 0" class="tag-list d-inline mb-2">
          <li v-for="tag in tags" :key="tag" class="list-inline-item">
            <b-form-tag @remove="handleRemove(tag, removeTag)" :title="tag" :disabled="disabled" variant="danger">{{ tag }}</b-form-tag>
          </li>
        </ul>
      </span>
      <span>
        <b-dropdown size="sm" variant="outline-secondary">
          <template #button-content>
            <b-icon icon="tag-fill"></b-icon>
          </template>
          <b-dropdown-form @submit.stop.prevent="onSubmit">
            <b-form-group class="mt-0 mb-0 ml-0 mr-0" :description="searchDesc" :disabled="disabled">
              <b-form-input v-model="search" id="tag-search-input" type="search" size="sm"
                autocomplete="off"></b-form-input>
            </b-form-group>
          </b-dropdown-form>
          <b-dropdown-divider></b-dropdown-divider>
          <b-dropdown-item-button v-for="option in availableOptions" :key="option"
            @click="onOptionClick({ option, addTag })">
            {{ option }}
          </b-dropdown-item-button>
          <b-dropdown-text v-if="availableOptions.length === 0">
            No matching tag
          </b-dropdown-text>
        </b-dropdown>
      </span>
      </template>
    </b-form-tags>
  </div>
</template>

<script>
export default {
  props: {
    allTags: {
      type: Array,
      default: () => []
    },
    thisNoteTags: {
      type: Array,
      default: () => []
    }, noteId: {
      type: [Number,String]
    }

  },
  data() {
    return {
      options: ['Apple', 'Orange', 'Banana', 'Lime', 'Peach', 'Chocolate', 'Strawberry'],
      search: '',
      value: []
    }
  },
  mounted() {
    this.options = this.allTags.map(tag => tag.tagName);
    this.value = this.thisNoteTags.map(tag => tag.tagName);
  },
  computed: {
    criteria() {
      return this.search.trim().toLowerCase()
    },
    availableOptions() {
      const criteria = this.criteria
      const options = this.options.filter(opt => this.value.indexOf(opt) === -1)
      if (criteria) {
        return options.filter(opt => opt.toLowerCase().indexOf(criteria) > -1);
      }
      return options
    },
    searchDesc() {
      if (this.criteria && this.availableOptions.length === 0) {
        return 'There are no tags matching your search criteria'
      }
      return ''
    }
  },
  methods: {
    onOptionClick({ option, addTag }) {
      this.axios.post('/notes/' + this.noteId + '/add_tag', {
        tagName: option
      })
        .then(response => {
          if (response.data.code === 1) {
            addTag(option)
            this.$notify('success', 'add tag success', 'success');
          } else {
            this.$notify('error', response.data.msg, 'danger');
            console.log(response.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
          this.$notify('error', error, 'danger');
        });
      this.search = ''
    },
    onSubmit() {
      const search = this.search.trim()
      if (this.search.length > 20) {
        alert('Tag length should not exceed 20 characters.'); 
        return;
      }
      if (search && !this.options.includes(search)) {
        this.axios.post('/notes/tags/add', {
          tagName: search
        })
          .then(response => {
            if (response.data.code === 1) {
              this.$notify('success', 'create tag success', 'success');
              this.axios.post('/notes/' + this.noteId + '/add_tag', {
                tagName: search
              })
                .then(response => {
                  if (response.data.code === 1) {
                    this.options.push(search)
                    this.value.push(search)

                    this.$notify('success', 'add tag success', 'success');
                  } else {
                    this.$notify('error', response.data.msg, 'danger');
                    console.log(response.data.msg);
                  }
                })
                .catch(error => {
                  console.log(error);
                  this.$notify('error', error, 'danger');
                });

            } else {
              this.$notify('error', response.data.msg, 'danger');
              console.log(response.data.msg);
            }
          })
          .catch(error => {
            console.log(error);
            this.$notify('error', error, 'danger');
          });
        this.search = ''
      }
    },
    handleRemove(tag, removeTag) {  
      this.axios.post('/notes/' + this.noteId + '/remove_tag', {
        tagName: tag
      })
        .then(response => {
          if (response.data.code === 1) {
            removeTag(tag)
            this.$notify('success', 'remove tag success', 'success');
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
  watch: {
    allTags(newVal) {
      this.options = newVal.map(tag => tag.tagName);
    },
    thisNoteTags(newVal) {
      this.value = newVal.map(tag => tag.tagName);
    },
  }
}
</script>

<style >
.no-border {
  border: none !important;
}

.tag-list {
    max-width: 200px;
    white-space: nowrap; overflow-x: auto;
}
.list-inline-item {
    flex: 0 0 auto;  /* do not allow the tag to grow, shrink, and it's auto based on content */
}
</style>
