<template>
  <div class="authors-container">
    <div class="header">
      <h1>Authors List</h1>
      <app-button theme="primary" :to="'/authors/add'">Add New Author</app-button>
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
              <app-button theme="none" :to="`/authors/${author.id}/edit`"> ✏️ </app-button>
              <app-button theme="none" @click.stop="deleteAuthor(author.id)" > 🗑️ </app-button>
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
import AppButton from '@/components/AppButton.vue';

export default {
  name: "AuthorsView",
  components: { AppButton },
  data() {
    return {
      authors: [],
      loading: true,
      currentPage: 0,
      pageSize: 5,
      totalPages: 0,
      sortBy: "lastName",
      sortDir: "asc",
    };
  },
  methods: {
    async fetchAuthors() {
      this.loading = true;
      try {
        const response = await axios.get(`http://localhost:8081/authors`, {
          params: {
            page: this.currentPage,
            size: this.pageSize,
            sort: `${this.sortBy},${this.sortDir}`,
          },
        });

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
.authors-container { padding: 20px; }

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

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
  transition: transform 0.2s;
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