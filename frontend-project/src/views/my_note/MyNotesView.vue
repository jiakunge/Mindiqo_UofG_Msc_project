<template>
  <div>
    <NoteDialog ref="dialog" @rename="handleRename" @editAbs="handelEditAbs" @delete="handleDelete"
      @newNote="handleNewNote" @changeImg="handleChangeImg" />

    <b-card no-body>
      <b-tabs card>

        <b-tab :title-link-class="linkClass(-1)" class="bg-light">
          <template #title>
            <strong>All</strong>
          </template>
          <b-card-text>
            <NoteCards :notes="notes" @openDialog="handleDialog" />
          </b-card-text>
        </b-tab>

        <b-tab v-for=" (item, index) in tags" :key="index" :title-link-class="linkClass(index)"
          @click="tagLoadNote(index)" class="bg-light">
          <template #title>
            <strong>#{{ item.tagName }}</strong>
          </template>
          <b-card-text>
            <NoteCards :notes="tagToNotes[index]" @openDialog="handleDialog" />
          </b-card-text>
        </b-tab>
      </b-tabs>

    </b-card>
  </div>
</template>

<script>
import router from '@/router';
import NoteCards from '../../components/my_notes/NoteCards.vue';
import NoteDialog from '../../components/my_notes/NoteDialog.vue';
import tagMock from '@/mocks/notes.js'

export default {
  components: {
    NoteCards,
    NoteDialog,
  },
  data() {
    return {
      tags: tagMock,
      tagIndex: -1,
      notes: [],
      tagToNotes: [],

    };
  },
  mounted() {
    var self = this;
    // tags
    // notes
    this.axios.get('/notes')
      .then(response => {
        if (response.data.code === 1) {
          self.notes = response.data.data.notes;
          self.tags = response.data.data.tags;

        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      })
      .catch(error => {
        console.log(error);
      });


  },
  methods: {
    //V
    linkClass(idx) {
      if (this.tabIndex === idx) {
        return ['text-dark']
      } else {
        return ['text-secondary']
      }
    },
    //C
    handleDialog(noteData) {
      this.$refs.dialog.receiveData(noteData);
    },
    //M
    handleRename(noteData) {
      this.axios.post('/notes/' + noteData.id + '/rename', {
        title: noteData.title,
      }).then(response => {
        if (response.data.code === 1) {
          this.notes.forEach(note => {
            if (note.id === noteData.id) {
              note.title = noteData.title;
            }
          });
          this.$notify('success', 'rename success', 'success');
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      })

    },
    handelEditAbs(noteData) {
      this.axios.post('/notes/' + noteData.id + '/edit_abs', {
        abs: noteData.abs,
      }).then(response => {
        if (response.data.code === 1) {

          this.notes.forEach(note => {
            if (note.id === noteData.id) {
              note.abs = noteData.abs;
            }
          });
          this.$notify('success', 'edit abstract success', 'success');
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      })
    },
    handleChangeImg(noteData) {
      this.axios.post('/notes/' + noteData.id + '/change_img', {
        img: noteData.img,
      }).then(response => {
        if (response.data.code === 1) {
          this.notes.forEach(note => {
            if (note.id === noteData.id) {
              note.img = noteData.img;
            }
          });
          this.$notify('success', 'change image success', 'success');
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      })

    },
    handleDelete(noteData) {
      this.axios.delete('/notes/' + noteData.id + '/delete', {
      }).then(response => {
        if (response.data.code === 1) {
          this.notes.forEach(note => {
            if (note.id === noteData.id) {
              this.notes.splice(this.notes.indexOf(note), 1);
            }
          });
          this.$notify('success', 'delete success', 'success');
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      })
    },
    handleNewNote(noteData) {
      this.axios.post('/notes/add', {
        title: noteData.title,
        abs: noteData.abs,
        img: 'https://picsum.photos/400',
      }).then(response => {
        if (response.data.code === 1) {
          //get a note from server add to notes
          const newNote = response.data.data;
          this.notes.push(newNote);
          router.push('/my_notes/edit/' + newNote.id);
          this.$notify('success', 'create note success', 'success');
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      })
    },
    // tagToNotes
    tagLoadNote(index) {
      if (index >= 0 && index < this.tags.length) {
        const selectedTag = this.tags[index];
        const tagNotes = [];

        this.notes.forEach((note) => {
          // Check if any of the note's tags has the same id as the selectedTag
          if (note.tags.some((tag) => tag.id === selectedTag.id)) {
            tagNotes.push(note);
          }
        });
        this.$set(this.tagToNotes, index, tagNotes);
      }
    },
  },
}
</script>

<style></style>