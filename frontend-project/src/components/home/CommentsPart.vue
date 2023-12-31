<template>
  <div>
    <ul>
      <b-media tag="li" v-for="item in comments" :key="item.id">
        <template #aside>
          <div>
            <b-avatar :to="'/user/' + item.user.username" :src="item.user.avatar"></b-avatar>
          </div>
        </template>
        <div class="d-flex justify-content-between mt-0 mb-1">
          <span><strong>{{ item.user.username }}</strong></span>
          <span>{{ $formatDateTime(item.createTime) }} </span>
        </div>

        <p class="mb-0">
          {{ item.content }}
        </p>
        <hr>
      </b-media>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    comments: Array, // Define the 'comments' prop as an array
  },

  data() {
    return {
    };
  },

}
</script>

<style></style>