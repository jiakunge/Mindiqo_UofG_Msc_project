<template>
  <div class="ml-3 mr-3">
    <div class="head hv-10" style="border: 1px solid #ebeef5;">
  <b-form inline class="d-flex align-items-center"> <!-- Add d-flex and align-items-center to make items stretch across the entire line and center-aligned vertically -->
    <!-- Input - 40% width -->
    <b-form-input class="input-no-border flex-grow-1" style="flex: 0 0 30%; font-size: 1.5em; font-weight: bold;" autocomplete="off" v-model="title" @blur="updateTitle()" @keyup.enter="updateTitle()"></b-form-input>
    <!-- TagBar - 30% width -->
    <TagBar :allTags="allTags" :thisNoteTags="note.tags" :noteId="note.id" style="flex: 0 0 40%;"></TagBar>
    <!-- Actions Span - 20% width -->
    <span style="flex: 0 0 20%; white-space: nowrap;"> <!-- Adding white-space: nowrap; to prevent wrapping within the span -->
      <small class="text-muted mr-3">Created at: {{ $formatDateTime(note.createTime) }}</small>
      <b-button class="mr-3" variant="warning" @click="saveNote()">Save</b-button>
      <b-button class="mr-3" variant="secondary" @click="postNote()">Post</b-button>
    </span>
  </b-form>
</div>

    <EditorComponent :content="note.content" @update:content="updateNoteContent"></EditorComponent>
  </div>
</template>

<script>
import EditorComponent from '../../../components/my_notes/EditorComponent.vue';
import TagBar from '../../../components/my_notes/TagBar.vue';

export default {
  components: {
    EditorComponent,
    TagBar
  },
  data() {
    return {
      note: {
        id: '-1',
        title: '',
        content: '',
        updateTime: '',
        createTime: '',
        tags: [],
      },
      allTags: [],
      title: 'Untitled',
      imgSrc: [],
    };
  },
  mounted() {
    if (this.$route.params.id) {
      this.loadNote(this.$route.params.id);
    }
    this.axios.get('/notes/tags')
      .then(response => {
        if (response.data.code === 1) {
          this.allTags = response.data.data;
        } else {
          console.log(response.data.msg);
        }
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    loadNote(id) {
      var self = this;
      this.axios.get('/notes/' + id)
        .then(response => {
          if (response.data.code === 1) {
            self.note = response.data.data;
            self.title = self.note.title;
          } else {
            console.log(response.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    updateNoteContent(newContent) {
      this.note.content = newContent;
    },
    updateTitle() {
      if (this.title !== this.note.title) {
        this.note.title = this.title;
        this.axios.post('/notes/' + this.note.id + '/rename', {
          title: this.note.title,
        }).then(response => {
          if (response.data.code === 1) {
            this.$notify('success', 'rename success', 'success');
          } else {
            this.$notify('error', response.data.msg, 'danger');
            console.log(response.data.msg);
          }
        })
      }
    },
    saveNote() {
      this.axios.post('/notes/' + this.note.id + '/save', {
        content: this.note.content,
      }).then(response => {
        if (response.data.code === 1) {
          this.$notify('success', 'save success', 'success');
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      });
    },
    postNote() {
      this.axios.post('/notes/' + this.note.id + '/save', {
        content: this.note.content,
      }).then(response => {
        if (response.data.code === 1) {
          this.$notify('success', 'save success', 'success');
          this.axios.post('/notes/' + this.note.id + '/public', {
            content: this.note.content,
          }).then(response => {
            if (response.data.code === 1) {
              this.$notify('success', 'public success', 'success');
            } else {
              this.$notify('error', response.data.msg, 'danger');
              console.log(response.data.msg);
            }
          });
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      });

    }


  },

}
</script>

<style scoped>
.head {
  width: 100%;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #000;
}

.input-no-border {
  border: none !important;
  height: 50px;
  max-width: 30vw;
}
</style>