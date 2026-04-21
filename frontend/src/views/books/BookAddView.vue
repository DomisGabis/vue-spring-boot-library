<template>
  <div class="add-container">
    <app-button theme="outline" @click="$router.go(-1)">Cancel</app-button >
    <h2 class="view-title">Add Book</h2>
    <book-form submitLabel="Add New Book" @submit="handleAdd" />
  </div>
</template>

<script>
import BookForm from "@/components/BookForm.vue";
import AppButton from "@/components/AppButton.vue";
import axios from "axios";

export default {
  name: "BookAddView",
  components: { BookForm, AppButton },
  methods: {
    async handleAdd(bookData) {
      if (bookData instanceof Event) {
        return;
      }
      try {
        await axios.post("http://localhost:8081/books", bookData);
        this.$router.go(-1);
      } catch (e) {
        alert("Error occurred while adding the book.");
      }
    },
  },
};
</script>

<style scoped>
.add-container {
  max-width: 600px;
  margin: 40px auto;
  padding: 0 20px;
}
</style>