<template>
  <div class="container">
    <app-button theme="outline" @click="$router.go(-1)">Cancel</app-button>
    <h2 class="view-title">Add New Author</h2>
    <author-form submitLabel="Add Author" @submit="handleAdd" />
  </div>
</template>

<script>
import AuthorForm from '@/components/AuthorForm.vue';
import AppButton from '@/components/AppButton.vue';
import axios from 'axios';

export default {
  name: "AuthorAddView",
  components: { AuthorForm, AppButton },
  methods: {
    async handleAdd(authorData) {
      if (authorData instanceof Event) {
        return;
      }
      try {
        await axios.post('http://localhost:8081/authors', authorData);
        this.$router.go(-1);
      } catch (e) {
        alert("Error occurred while adding the author.");
      }
    }
  }
};
</script>

<style scoped>
.container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
.view-title { margin-bottom: 20px; color: #2c3e50; }
</style>