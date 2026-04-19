<template>
  <div class="edit-container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Cancel</AppButton>
    
    <div v-if="loading" class="loader">
      <p>Loading book data...</p>
    </div>

    <div v-else>
      <h2 class="view-title">Edit Book: {{ originalTitle }}</h2>
      
      <BookForm 
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

export default {
  name: 'BookEditView',
  components: { BookForm },
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
      // Pobieramy dane książki, aby przekazać je do formularza
      const response = await axios.get(`http://localhost:8081/books/${bookId}`);
      
      // Mapujemy dane z API na strukturę oczekiwaną przez BookForm
      this.book = {
        title: response.data.title,
        authorId: response.data.authorId,
        pages: response.data.pages
      };
      this.originalTitle = response.data.title;
    } catch (error) {
      console.error("Błąd podczas pobierania danych:", error);
      alert("Nie udało się załadować danych książki.");
      this.$router.push('/books');
    } finally {
      this.loading = false;
    }
  },
  methods: {
    async handleUpdate(updatedBookData) {
      const bookId = this.$route.params.id;
      try {
        await axios.put(`http://localhost:8081/books/${bookId}`, updatedBookData);
        alert("Zmiany zostały pomyślnie zapisane!");
        this.$router.push(`/books/${bookId}`);
      } catch (error) {
        console.error("Błąd podczas aktualizacji:", error);
        alert("Wystąpił błąd podczas zapisywania zmian.");
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

.mb-4 {
  margin-bottom: 20px;
}
</style>