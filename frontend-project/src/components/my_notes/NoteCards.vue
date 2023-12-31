<template>
  <div class="box-content box-1 bg-light">
    <b-container>
      <b-row>
        <b-col cols="12" md="4">
          <div class="box-item" >
            <b-img :src="img" fluid></b-img>
            <div class="content" style="text-align: center;">
              <h3> <b-icon icon='pencil-square'></b-icon>Capture Ideas Here!</h3>
              <b-button  variant="outline-dark" @click="newNote()">New Note</b-button>
            </div>
          </div>
        </b-col>
        <b-col cols="12" md="4" v-for="(item, index) in notes" :key="index">
          <div class="box-item">
            <div class="image-container">
              <b-img :src="item.img"></b-img>
            </div>
            <div class="content">
              <h3>{{ item.title }}</h3>
              <p class="auto-warp">{{ item.abs }}</p>
              <div class="no-warp d-flex justify-content-between align-items-center">
                <router-link :to="'/my_notes/edit/' + item.id" class="router-link-text">Read More...</router-link>
                <span>{{ $formatDateTime(item.updateTime) }} </span>
                <span>
                  <b-dropdown class="ml-auto" size="sm" variant="link" toggle-class="text-decoration-none" no-caret lazy>
                    <template #button-content>
                      <b-icon icon="justify-right" style="color: #0aac8e;">
                      </b-icon>
                    </template>
                    <b-dropdown-item-button @click="rename(item)">Rename</b-dropdown-item-button>
                    <b-dropdown-item-button @click="editAbs(item)">Edit Abstract</b-dropdown-item-button>
                    <b-dropdown-item-button @click="changeImg(item)">Change Image</b-dropdown-item-button>
                    <b-dropdown-divider></b-dropdown-divider>
                    <b-dropdown-item-button variant="danger" @click="deleteNote(item)">
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
import img from "@/assets/new_note.png";

export default {
  props: {
    notes: Array, // Define the 'notes' prop as an array
  },
  data() {
    return {
      img
    };
  },
  methods: {
    rename(note) {
      var noteData = {
        id: note.id,
        title: note.title,

      }
      this.$emit('openDialog', noteData);
    },
    editAbs(note) {
      var noteData = {
        id: note.id,
        abs: note.abs,
      }
      this.$emit('openDialog', noteData);
    },
    changeImg(note) {
      var noteData = {
        id: note.id,
        img: note.img,
      }
      this.$emit('openDialog', noteData);
    },
    deleteNote(note) {
      var noteData = {
        id: note.id,
      }
      this.$emit('openDialog', noteData);
    },
    newNote() {
      var noteData = {
      };
      this.$emit('openDialog', noteData);
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
  height: 100px;
  overflow: hidden;
  word-wrap: break-word;
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




