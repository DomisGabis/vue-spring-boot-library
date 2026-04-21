<template>
  <div class="edit-container">
    <app-button theme="outline" @click="$router.go(-1)">Cancel</app-button>
    <div v-if="loading" class="loader">
      <p>Loading book data...</p>
    </div>

    <div v-else>
      <h2 class="view-title">Edit Book: {{ originalTitle }}</h2>
      
      <book-form 
        :initialData="book" 
        submitLabel="Save Changes" 
        @submit="handleUpdate" 
      />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import BookForm from '@/components/BookForm.vue';
import AppButton from '@/components/AppButton.vue';

export default {
  name: 'BookEditView',
  components: { BookForm, AppButton },
  data() {
    return {
      book: null,
      originalTitle: '',
      loading: true
    };
  },
  async mounted() {
    const bookId = this.$route.params.id;
    try {
      const response = await axios.get(`http://localhost:8081/books/${bookId}`);
      
      this.book = {
        title: response.data.title,
        authorId: response.data.authorId,
        pages: response.data.pages
      };
      this.originalTitle = response.data.title;
    } catch (error) {
      console.error("Error occurred while loading book data:", error);
      alert("Error occurred while loading book data.");
      this.$router.push('/books');
    } finally {
      this.loading = false;
    }
  },
  methods: {
    async handleUpdate(updatedBookData) {
      if (updatedBookData instanceof Event) {
        return;
      }
      const bookId = this.$route.params.id;
      try {
        await axios.put(`http://localhost:8081/books/${bookId}`, updatedBookData);
        this.$router.go(-1);
      } catch (error) {
        console.error("Error occurred while updating the book:", error);
        alert("Error occurred while updating the book.");
      }
    }
  }
};
</script>

<style scoped>
.edit-container {
  max-width: 600px;
  margin: 40px auto;
  padding: 0 20px;
}

.view-title {
  margin-bottom: 20px;
  color: #2c3e50;
  font-size: 1.5rem;
  text-align: left;
}

.loader {
  text-align: center;
  padding: 50px;
  color: #7f8c8d;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}
</style>