<template>
  <div class="container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Cancel</AppButton>
    <h2 class="view-title">Add New Author</h2>
    <AuthorForm submitLabel="Add Author" @submit="handleAdd" />
  </div>
</template>

<script>
import AuthorForm from '@/components/AuthorForm.vue';
import axios from 'axios';

export default {
  components: { AuthorForm },
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
.mb-4 { margin-bottom: 20px; }
</style>