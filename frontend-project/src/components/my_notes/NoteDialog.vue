<template>
    <div>
        <b-modal id="modal-rename" title="Rename" centered @hidden="resetModal()" @ok="handleRename()">
            <form ref="form" @submit.stop.prevent="handleSubmit('modal-rename', 'title', 'rename')">
                <b-form-group label="New Title" label-for="input-field" invalid-feedback="Field is required"
                    :state="fieldState">
                    <b-form-textarea id="input-field" v-model="inputField" :state="fieldState" required
                        autosize></b-form-textarea>
                </b-form-group>
            </form>
        </b-modal>

        <b-modal id="modal-edit_abs" title="Edit abstract" centered @hidden="resetModal()" @ok="handleEditAbs()">
            <form ref="form" @submit.stop.prevent="handleSubmit('modal-edit_abs', 'abs', 'editAbs')">
                <b-form-group label="Abstract" label-for="input-field" invalid-feedback="Field is required"
                    :state="fieldState">
                    <b-form-textarea id="input-field" v-model="inputField" :state="fieldState" required
                        autosize></b-form-textarea>
                </b-form-group>
            </form>
        </b-modal>

        <b-modal id="modal-change-img" title="Change image" centered @hidden="resetModal()" @ok="changeImg()">
            <div class="mb-3">
                <b-form-file :disabled="Boolean(imgUrl)"  accept=".jpg, .png"
                v-model="imgFile"  placeholder="upload an image or drop it here..."
                    drop-placeholder="Drop file here..."></b-form-file>
            </div>
            <div>
                <b-form-input :disabled="Boolean(imgFile)" v-model="imgUrl" placeholder="or enter an image url"></b-form-input>
            </div>
            <div>
                <b-img :src="imgUrl" fluid></b-img>
            </div>
        </b-modal>

        <b-modal id="modal-delete" title="Warning" centered ok-title="Delete" ok-variant="danger" @ok="deleteNote()">
            <p class="my-4">Are you sure you want to delete this note?
                <br>This action cannot be undone.
            </p>
        </b-modal>

        <b-modal id="modal-new-note" title="New Note" centered @hidden="resetModal()" @ok="newNote()">
            <form>
                <label for="new-note-title">Title:</label>
                <b-form-input class="mb-3" placeholder="Enter a title" id="new-note-title"></b-form-input>
                <label for="new-note-abs">Abstract:</label>
                <b-form-textarea label="Abstract" label-for="input-field" placeholder="Enter an abstract" id="new-note-abs"
                    rows="3"></b-form-textarea>
            </form>

        </b-modal>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            noteData: {
                id: 0,
                title: 'mock',
                abs: null,
                img: null,
            },
            inputField: '',
            fieldState: null,
            imgFile: null,
            imgUrl: null,
        }
    },
    methods: {
        checkFormValidity() {
            const valid = this.$refs.form.checkValidity()
            this.fieldState = valid
            return valid
        },
        resetModal() {
            this.inputField = ''
            this.fieldState = null
            this.imgFile = null
            this.imgUrl = null
        },
        handleRename() {
            this.handleSubmit('modal-rename', 'title', 'rename')
        },
        handleEditAbs() {
            this.handleSubmit('modal-edit_abs', 'abs', 'editAbs')
        },
        handleSubmit(modalId, field, event) {
            if (!this.checkFormValidity()) {
                return
            }
            this.noteData[field] = this.inputField
            this.$emit(event, this.noteData)
            this.$bvModal.hide(modalId);
        },
        changeImg() {
            if (this.imgFile) {
                const reader = new FileReader();
                reader.readAsDataURL(this.imgFile);

                reader.onload = () => {
                    this.noteData.img = reader.result;
                    this.$emit('changeImg', this.noteData)
                    this.$bvModal.hide('modal-change-img');
                };

                reader.onerror = error => {
                    console.error('Error converting image to Base64:', error);
                    this.$notify({
                        title: 'Error',
                        text: 'Error converting image to Base64',
                        type: 'danger'
                    });
                };
            } else if (this.imgUrl) {
                this.noteData.img = this.imgUrl
                this.$emit('changeImg', this.noteData)
                this.$bvModal.hide('modal-change-img');
            }

        },
        deleteNote() {
            this.$emit('delete', this.noteData)
            this.$bvModal.hide('modal-delete');
        },
        newNote() {
            this.noteData.title = document.getElementById("new-note-title").value;
            this.noteData.abs = document.getElementById("new-note-abs").value;
            this.$emit('newNote', this.noteData)
        },
        receiveData(noteData) {
            this.noteData = noteData;
            if (noteData.title) {
                this.inputField = noteData.title;
                this.$bvModal.show('modal-rename');
            } else if (noteData.abs) {
                this.inputField = noteData.abs;
                this.$bvModal.show('modal-edit_abs');
            } else if (noteData.img) {
                this.$bvModal.show('modal-change-img');
            } else if (noteData.id) {
                this.$bvModal.show("modal-delete");
            } else {
                this.$bvModal.show('modal-new-note');
            }
        },
    },
}
</script>
