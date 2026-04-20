<template>
  <div class="add-container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Cancel</AppButton>
    <h2 class="view-title">Add Book</h2>
    <BookForm submitLabel="Add New Book" @submit="handleAdd" />
  </div>
</template>

<script>
import BookForm from '@/components/BookForm.vue';
import axios from 'axios';

export default {
  components: { BookForm },
  methods: {
    async handleAdd(bookData) {
      try {
        await axios.post('http://localhost:8081/books', bookData);
        this.$router.go(-1);
      } catch (e) { alert("Error occurred while adding the book."); }
    }
  }
};
</script>

<style scoped>
.add-container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
.mb-4 { margin-bottom: 20px; }
</style>