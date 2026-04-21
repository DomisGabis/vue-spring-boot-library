<template>
  <div class="container">
    <app-button theme="outline" @click="$router.go(-1)">Cancel</app-button>
    
    <div v-if="loading">Loading author data...</div>
    <div v-else>
      <h2 class="view-title">Edit Author: {{ originalName }}</h2>
      <author-form 
        :initialData="author" 
        submitLabel="Save Changes" 
        @submit="handleUpdate" 
      />
    </div>
  </div>
</template>

<script>
import AuthorForm from '@/components/AuthorForm.vue';
import AppButton from '@/components/AppButton.vue';

import axios from 'axios';

export default {
  name: "AuthorEditView",
  components: { AuthorForm, AppButton },
  data() {
    return {
      author: null,
      originalName: '',
      loading: true
    };
  },
  async mounted() {
    const authorId = this.$route.params.id;
    try {
      const res = await axios.get(`http://localhost:8081/authors/${authorId}`);
      this.author = {
        firstName: res.data.firstName,
        lastName: res.data.lastName
      };
      this.originalName = `${res.data.firstName} ${res.data.lastName}`;
    } catch (e) {
      alert("Error occurred while fetching author data.");
      this.$router.push('/authors');
    } finally {
      this.loading = false;
    }
  },
  methods: {
    async handleUpdate(updatedData) {
      if (updatedData instanceof Event) {
        return;
      }

      const authorId = this.$route.params.id;
      try {
        await axios.put(`http://localhost:8081/authors/${authorId}`, updatedData);
        this.$router.go(-1);
      } catch (e) {
        alert("Error occurred while updating author data.");
      }
    }
  }
};
</script>

<style scoped>
.container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
.view-title { margin-bottom: 20px; color: #2c3e50; }
</style>