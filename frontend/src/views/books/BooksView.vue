<template>
  <div class="books-container">
    <div class="header">
      <h1>Book List</h1>
      <router-link to="/books/add" class="add-btn">
        <span class="icon">+</span> Add New Book
      </router-link>
    </div>

    <div v-if="loading" class="loader">Loading books...</div>

    <div v-else>
      <table class="books-table">
        <thead>
          <tr>
            <th class="col-title">Title</th>
            <th class="col-author">Author</th>
            <th class="col-pages">Pages</th>
            <th class="col-status">Status</th>
            <th class="col-actions actions-header">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="book in books" :key="book.id" class="book-row">
            <td @click="goToDetails(book.id)" class="clickable">
              {{ book.title }}
            </td>
            <td @click="goToDetails(book.id)" class="clickable">
              {{ book.authorFirstName }} {{ book.authorLastName }}
            </td>
            <td @click="goToDetails(book.id)" class="clickable">
              {{ book.pages }}
            </td>
            <td @click="goToDetails(book.id)" class="clickable">
              {{ book.rented ? "Rented" : "Available" }}
            </td>

            <td class="actions">
              <AppButton theme="none" class="edit-icon" :to="`/books/${book.id}/edit`"> ✏️ </AppButton>
              <button
                @click.stop="deleteBook(book.id)"
                class="delete-icon"
                title="Delete"
              >
                🗑️
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="pagination">
        <button
          :disabled="currentPage === 0"
          @click="changePage(currentPage - 1)"
        >
          Previous
        </button>
        <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
        <button
          :disabled="currentPage >= totalPages - 1"
          @click="changePage(currentPage + 1)"
        >
          Next
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BooksView",
  data() {
    return {
      books: [],
      loading: true,
      currentPage: 0,
      pageSize: 5,
      totalPages: 0,
      sortBy: "title",
      sortDir: "asc",
    };
  },
  methods: {
    async fetchBooks() {
      this.loading = true;
      try {
        const response = await axios.get(`http://localhost:8081/books`, {
          params: {
            page: this.currentPage,
            size: this.pageSize,
            sort: `${this.sortBy},${this.sortDir}`,
          },
        });

        this.books = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Error while fetching books:", error);
      } finally {
        this.loading = false;
      }
    },
    changePage(newPage) {
      this.currentPage = newPage;
      this.fetchBooks();
    },
    goToDetails(id) {
      this.$router.push(`/books/${id}`);
    },
    editBook(id) {
      this.$router.push(`/books/edit/${id}`);
    },
    async deleteBook(id) {
      if (confirm("Are you sure you want to delete this book?")) {
        try {
          await axios.delete(`http://localhost:8081/books/${id}`);
          this.fetchBooks();
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
  mounted() {
    this.fetchBooks();
  },
};
</script>

<style scoped>
.books-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.add-btn {
  background-color: #42b983;
  color: white;
  padding: 10px 20px;
  text-decoration: none;
  border-radius: 5px;
  font-weight: bold;
  transition: background 0.3s;
}

.add-btn:hover {
  background-color: #38a169;
}

.books-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
  table-layout: fixed;
}

th,
td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background-color: #f8f9fa;
  color: #2c3e50;
  text-transform: uppercase;
  font-size: 0.85rem;
}

.col-title {
  width: 35%;
}

.col-author {
  width: 35%;
}

.col-pages {
  width: 10%;
}

.col-status {
  width: 10%;
}

.col-actions {
  width: 10%;
}

td {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.book-row:hover {
  background-color: #f9f9f9;
}

.clickable {
  cursor: pointer;
}

.actions {
  opacity: 0;
  transition: opacity 0.2s;
  display: flex;
  gap: 10px;
}

.book-row:hover .actions {
  opacity: 1;
}

.actions button {
  border: none;
  background: none;
  cursor: pointer;
  font-size: 1.2rem;
  transition: transform 0.2s;
}

.actions button:hover {
  transform: scale(1.2);
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.pagination button {
  padding: 8px 16px;
  cursor: pointer;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
}

.pagination button:disabled {
  background: #eee;
  cursor: not-allowed;
}
</style>