<template>
    <div>
        <b-modal id="modal-change-bio" title="Change Bio" centered @hidden="resetModal()" @ok="changeBio()"
            :ok-disabled="!(inputField.length <= 150 && inputField.length >= 1)">
            <div class="mb-3">
                <b-form>
                    <b-form-textarea label="Bio" label-for="input-field" placeholder="Enter a bio" id="bio-input" rows="3"
                        v-model="inputField" :state="inputField.length <= 150 && inputField.length >= 1"
                        required></b-form-textarea>
                    <p style="font-size: 12px; color: grey;">Character count must be between 1 and 150.</p>
                </b-form>
            </div>
        </b-modal>

        <b-modal id="modal-change-avatar" title="Choose an avatar" centered @hidden="resetModal()" @ok="changeAvatar()">
            <div class="mb-3">
                <b-img :src="avatar" thumbnail width="100"  height="100" class="rounded-circle"></b-img>
            </div>
            <b-form-group label="Select an avatar">
                <b-container fluid>
                    <b-row >
                        <b-col v-for="option in options" :key="option.url" md="3" class="text-center">
                            <b-img :src="option.url" :alt="option.alt" width="80"  height="80" class="avatar rounded-circle mb-4"
                                @click="updateAvatar(option.url)"></b-img>
                        </b-col>
                    </b-row >
                </b-container>
            </b-form-group>
        </b-modal>

        <b-modal id="modal-delete" title="Warning" centered ok-title="Delete" ok-variant="danger" @ok="deletePost()">
            <p class="my-4">Are you sure you want to delete this post?
                <br>This action cannot be undone.
            </p>
        </b-modal>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            inputField: '',
            avatar: '',
            postId: null,
            options: [
                {
                    url: 'https://en.gravatar.com/userimage/237955910/20c803c18091d52d49f9d212b1143133.jpeg?size=256',
                    alt: 'Image 1'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/08c79381033bbb46eb186de08e6fc5b9.jpeg?size=256',
                    alt: 'Image 2'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/95921a02ea95270dc18bdda1ba04e7c1.jpeg?size=256',
                    alt: 'Image 3'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/a0fd95e268465f23b6b5a7be9bc85e38.jpeg?size=256',
                    alt: 'Image 4'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/060845077512c84c29434575e19b374b.jpeg?size=256',
                    alt: 'Image 5'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/194f10b6017401995fac4d37d9f506ab.jpeg?size=256',
                    alt: 'Image 6'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/f9005be33705bce6635fe83a4dc633f8.jpeg?size=256',
                    alt: 'Image 7'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/34aebc5e71cdf25418f64e90f883dd67.jpeg?size=256',
                    alt: 'Image 8'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/cef1fb3a03579c8c3644ebb7f80ef4d5.jpeg?size=256',
                    alt: 'Image 9'
                },
                {
                    url: 'https://en.gravatar.com/userimage/237955910/747425267e626fd0baf231672317ca66.jpeg?size=256',
                    alt: 'Image 10'
                },

            ]
        }
    },
    computed: {
        isInputValid() {
            return this.inputField.length >= 1 && this.inputField.length <= 150;
        }
    },
    methods: {
        validateInputField() {
            this.isInputValid = this.inputField.length >= 1 && this.inputField.length <= 150;
        },
        resetModal() {
            this.inputField = ''
            this.avatar = ''
        },
        deleteNote() {
            this.$emit('delete', this.noteData)
            this.$bvModal.hide('modal-delete');
        },
        receiveData(msg, data) {
            if (msg === 'changeBio') {
                this.inputField = data;
                this.$bvModal.show('modal-change-bio');
            } else if (msg === 'changeAvatar') {
                this.avatar = data;
                this.$bvModal.show('modal-change-avatar');
            }else if (msg === 'deletePost') {
                this.postId = data;
                this.$bvModal.show('modal-delete');
            }else {
                this.$notify('error', 'msg not recognized', 'danger');
                console.log('Error: msg not recognized');
            }
        },
        changeBio() {
            this.$emit('changeBio', this.inputField)
            this.$bvModal.hide('modal-change-bio');
        },
        changeAvatar() {
            this.$emit('changeAvatar', this.avatar)
            this.$bvModal.hide('modal-change-avatar');
        },
        updateAvatar(url) {
            this.avatar = url;
        },
        deletePost() {
            this.$emit('deletePost', this.postId)
            this.$bvModal.hide('modal-delete');
        }
    },
}
</script>
<style>
.avatar:hover {
    filter: brightness(80%); 
    cursor: pointer; 
}
</style>
