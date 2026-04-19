<template>
  <div class="details-container">
    <AppButton theme="outline" @click="$router.go(-1)">Back</AppButton>

    <div v-if="loading" class="loader">Loading book data...</div>

    <div v-else-if="book" class="book-card">
      <div class="book-header">
        <div class="book-icon">📚</div>
        <div class="title-section">
          <h1>{{ book.title }}</h1>
          <p class="subtitle">Resource ID: #{{ book.id }}</p>
        </div>
      </div>

      <div class="book-content">
        <div class="info-group">
          <label>Author</label>
          <router-link class="author-link" :to="`/authors/${book.authorId}`">
            {{ book.authorFirstName }} {{ book.authorLastName }}
          </router-link>
        </div>

        <div class="info-group">
          <label>Number of Pages</label>
          <p>{{ book.pages }}</p>
        </div>

        <div class="info-group">
          <label>Status</label>
          <span :class="['status-badge', book.rented ? 'rented' : 'available']">
            {{ book.rented ? "Rented" : "Available" }}
          </span>
        </div>
      </div>

      <div class="book-actions">
        <AppButton theme="primary" :to="`/books/${book.id}/edit`">Edit data</AppButton>
        <AppButton theme="danger" @click="deleteBook">Delete Book</AppButton>
      </div>
    </div>

    <div v-else class="error-msg">Book not found.</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BookDetailsView",
  data() {
    return {
      book: null,
      loading: true,
    };
  },
  async mounted() {
    const bookId = this.$route.params.id;
    try {
      const response = await axios.get(`http://localhost:8081/books/${bookId}`);
      this.book = response.data;
    } catch (error) {
      console.error("Error fetching book details:", error);
    } finally {
      this.loading = false;
    }
  },
  methods: {
    async deleteBook() {
      if (confirm("Are you sure you want to delete this book?")) {
        try {
          await axios.delete(`http://localhost:8081/books/${this.book.id}`);
          this.$router.push("/books");
        } catch (error) {
          const message =
            error.response?.data?.message ||
            "Error while deleting book";
          console.error("Error details:", error.response);
          alert(message);
        }
      }
    },
  },
};
</script>

<style scoped>
.details-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.back-link {
  background: none;
  border: none;
  color: #3498db;
  cursor: pointer;
  font-size: 1rem;
  margin-bottom: 20px;
  padding: 0;
}

.back-link:hover {
  text-decoration: underline;
}

.book-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  padding: 30px;
}

.book-header {
  display: flex;
  align-items: center;
  gap: 20px;
  border-bottom: 2px solid #f0f0f0;
  padding-bottom: 20px;
  margin-bottom: 20px;
}

.book-icon {
  font-size: 4rem;
}

.title-section h1 {
  margin: 0;
  color: #2c3e50;
  font-size: 2rem;
}

.subtitle {
  color: #95a5a6;
  margin: 5px 0 0 0;
}

.book-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.info-group label {
  display: block;
  font-size: 0.8rem;
  text-transform: uppercase;
  color: #7f8c8d;
  margin-bottom: 5px;
}

.info-group p {
  font-size: 1.2rem;
  font-weight: 500;
  margin: 0;
  color: #34495e;
}

.status-badge {
  display: inline-block;
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: bold;
}

.available {
  background: #e6fffa;
  color: #38a169;
}
.rented {
  background: #fff5f5;
  color: #e53e3e;
}

.book-actions {
  margin-top: 40px;
  display: flex;
  gap: 15px;
  justify-content: flex-end;
}
</style>