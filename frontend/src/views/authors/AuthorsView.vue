<template>
  <div class="authors-container">
    <div class="header">
      <h1>Authors List</h1>
      <router-link to="/authors/add" class="add-btn">
        <span class="icon">+</span> Add New Author
      </router-link>
    </div>

    <div v-if="loading" class="loader">Loading authors...</div>

    <div v-else>
      <table class="authors-table">
        <thead>
          <tr>
            <th class="col-name">First Name</th>
            <th class="col-name">Last Name</th>
            <th class="col-books">Book Count</th>
            <th class="col-actions actions-header">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="author in authors" :key="author.id" class="author-row">
            <td @click="goToDetails(author.id)" class="clickable">
              {{ author.firstName }}
            </td>
            <td @click="goToDetails(author.id)" class="clickable">
              {{ author.lastName }}
            </td>
            <td @click="goToDetails(author.id)" class="clickable">
              {{ author.bookCount || 0 }}
            </td>

            <td class="actions">
              <AppButton theme="none" class="edit-icon" :to="`/authors/${author.id}/edit`"> ✏️ </AppButton>
              <button
                @click.stop="deleteAuthor(author.id)"
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
  name: "AuthorsView",
  data() {
    return {
      authors: [],
      loading: true,
      currentPage: 0,
      pageSize: 10,
      totalPages: 0,
      sortBy: "lastName",
      sortDir: "asc",
    };
  },
  methods: {
    async fetchAuthors() {
      this.loading = true;
      try {
        // Endpoint dopasowany do standardu Twojego API
        const response = await axios.get(`http://localhost:8081/authors`, {
          params: {
            page: this.currentPage,
            size: this.pageSize,
            sort: `${this.sortBy},${this.sortDir}`,
          },
        });

        // Spring Page zwraca content, totalPages itp.
        this.authors = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Error while fetching authors:", error);
      } finally {
        this.loading = false;
      }
    },
    changePage(newPage) {
      this.currentPage = newPage;
      this.fetchAuthors();
    },
    goToDetails(id) {
      this.$router.push(`/authors/${id}`);
    },
    async deleteAuthor(id) {
      if (confirm("Are you sure you want to delete this author? This might affect their books!")) {
        try {
          await axios.delete(`http://localhost:8081/authors/${id}`);
          this.fetchAuthors();
        } catch (error) {
          const message =
            error.response?.data?.message ||
            "Error while deleting author (he might have assigned books)";
          alert(message);
        }
      }
    },
  },
  mounted() {
    this.fetchAuthors();
  },
};
</script>

<style scoped>
/* Style są identyczne jak w BooksView, aby zachować spójność UI */
.authors-container { padding: 20px; }

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

.add-btn:hover { background-color: #38a169; }

.authors-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
  table-layout: fixed;
}

th, td {
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

/* Dopasowanie szerokości kolumn */
.col-name { width: 40%; }
.col-books { width: 10%; }
.col-actions { width: 10%; }

td {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.author-row:hover { background-color: #f9f9f9; }

.clickable { cursor: pointer; }

.actions {
  opacity: 0;
  transition: opacity 0.2s;
  display: flex;
  gap: 10px;
}

.author-row:hover .actions { opacity: 1; }

.actions button {
  border: none;
  background: none;
  cursor: pointer;
  font-size: 1.2rem;
  transition: transform 0.2s;
}

.actions button:hover { transform: scale(1.2); }

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