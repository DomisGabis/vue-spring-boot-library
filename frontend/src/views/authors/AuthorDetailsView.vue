<template>
  <div class="details-container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Back</AppButton>

    <div v-if="loading" class="loader">Loading author data...</div>

    <div v-else-if="author" class="author-wrapper">
      <div class="author-card">
        <div class="author-header">
          <div class="author-icon">✍️</div>
          <div class="title-section">
            <h1>{{ author.firstName }} {{ author.lastName }}</h1>
            <p class="subtitle">Author ID: #{{ author.id }}</p>
          </div>
        </div>

        <div class="author-content">
          <div class="info-group">
            <label>Full Name</label>
            <p>{{ author.firstName }} {{ author.lastName }}</p>
          </div>
          <div class="info-group">
            <label>Total Books in System</label>
            <p>{{ author.bookCount || 0 }}</p>
          </div>
        </div>

        <div class="author-actions">
          <AppButton theme="primary" :to="`/authors/${author.id}/edit`">Edit data</AppButton>
          <AppButton theme="danger" @click="deleteAuthor">Delete Author</AppButton>
        </div>
      </div>

      <div class="books-section">
        <h2>Author's Bibliography</h2>
        
        <div v-if="books.length === 0" class="no-books">
          This author doesn't have any books assigned yet.
        </div>

        <div v-else>
          <table class="books-mini-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Pages</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="book in books" :key="book.id">
                <td class="clickable" @click="$router.push(`/books/${book.id}`)">{{ book.title }}</td>
                <td>{{ book.pages }}</td>
                <td>
                  
                  {{ book.rented ? 'Rented' : 'Available' }}
                </td>
              </tr>
            </tbody>
          </table>

          <div class="pagination-mini">
            <button :disabled="currentPage === 0" @click="changePage(currentPage - 1)">‹</button>
            <span>{{ currentPage + 1 }} / {{ totalPages }}</span>
            <button :disabled="currentPage >= totalPages - 1" @click="changePage(currentPage + 1)">›</button>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="error-msg">Author not found.</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AuthorDetailsView",
  data() {
    return {
      author: null,
      books: [],
      loading: true,
      currentPage: 0,
      pageSize: 5,
      totalPages: 0
    };
  },
  async mounted() {
    await this.fetchAuthorData();
  },
  methods: {
    async fetchAuthorData() {
      const authorId = this.$route.params.id;
      this.loading = true;
      try {
        const authorRes = await axios.get(`http://localhost:8081/authors/${authorId}`);
        this.author = authorRes.data;

        await this.fetchAuthorBooks();
      } catch (error) {
        console.error("Error fetching author details:", error);
      } finally {
        this.loading = false;
      }
    },
    async fetchAuthorBooks() {
      try {
        const response = await axios.get(`http://localhost:8081/books/author/${this.author.id}`, {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        });
        this.books = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Error fetching author books:", error);
      }
    },
    changePage(newPage) {
      this.currentPage = newPage;
      this.fetchAuthorBooks();
    },
    async deleteAuthor() {
      if (confirm("Are you sure? This will delete the author and potentially cause issues with their books!")) {
        try {
          await axios.delete(`http://localhost:8081/authors/${this.author.id}`);
          this.$router.push("/authors");
        } catch (error) {
          alert(error.response?.data?.message || "Error deleting author");
        }
      }
    }
  }
};
</script>

<style scoped>
/* Wykorzystujemy te same style co w BookDetails dla spójności */
.details-container { max-width: 800px; margin: 0 auto; padding: 20px; }
.author-card { background: white; border-radius: 12px; box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1); padding: 30px; margin-bottom: 30px; }
.author-header { display: flex; align-items: center; gap: 20px; border-bottom: 2px solid #f0f0f0; padding-bottom: 20px; margin-bottom: 20px; }
.author-icon { font-size: 4rem; }
.title-section h1 { margin: 0; color: #2c3e50; font-size: 2rem; }
.subtitle { color: #95a5a6; margin: 5px 0 0 0; }
.author-content { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
.info-group label { display: block; font-size: 0.8rem; text-transform: uppercase; color: #7f8c8d; margin-bottom: 5px; }
.info-group p { font-size: 1.2rem; font-weight: 500; margin: 0; color: #34495e; }
.author-actions { margin-top: 40px; display: flex; gap: 15px; justify-content: flex-end; }

.books-section { background: white; border-radius: 12px; padding: 30px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05); }
.books-section h2 { margin-top: 0; color: #2c3e50; font-size: 1.5rem; margin-bottom: 20px; }

.books-mini-table { width: 100%; border-collapse: collapse; margin-bottom: 20px; table-layout: fixed;}
.books-mini-table th { text-align: left; padding: 10px; border-bottom: 2px solid #eee; color: #7f8c8d; font-size: 0.9rem; }
.books-mini-table td { padding: 12px 10px; border-bottom: 1px solid #f9f9f9; }

.clickable { cursor: pointer; color: #3498db; font-weight: 500; }
.clickable:hover { text-decoration: underline; }

.status-dot { display: inline-block; width: 10px; height: 10px; border-radius: 50%; margin-right: 5px; }
.available { background: #38a169; }
.rented { background: #e53e3e; }

.view-link { color: #3498db; text-decoration: none; font-size: 0.9rem; }
.pagination-mini { display: flex; justify-content: center; align-items: center; gap: 15px; }
.pagination-mini button { padding: 5px 10px; cursor: pointer; border: 1px solid #ddd; background: white; border-radius: 4px; }
.no-books { color: #95a5a6; font-style: italic; }
.mb-4 { margin-bottom: 20px; }
</style>