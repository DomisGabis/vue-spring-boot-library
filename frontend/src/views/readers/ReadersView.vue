<template>
  <div class="readers-container">
    <div class="header">
      <h1>Readers List</h1>
      <router-link to="/readers/add" class="add-btn">
        <span class="icon">+</span> Add New Reader
      </router-link>
    </div>

    <div v-if="loading" class="loader">Loading readers...</div>

    <div v-else>
      <table class="readers-table">
        <thead>
          <tr>
            <th class="col-name">First Name</th>
            <th class="col-name">Last Name</th>
            <th class="col-email">Email</th>
            <th class="col-actions actions-header">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="reader in readers" :key="reader.id" class="reader-row">
            <td @click="goToDetails(reader.id)" class="clickable">
              {{ reader.firstName }}
            </td>
            <td @click="goToDetails(reader.id)" class="clickable">
              {{ reader.lastName }}
            </td>
            <td @click="goToDetails(reader.id)" class="clickable">
              {{ reader.email || 'N/A' }}
            </td>

            <td class="actions">
              <AppButton theme="none" class="edit-icon" :to="`/readers/${reader.id}/edit`"> ✏️ </AppButton>
              <button
                @click.stop="deleteReader(reader.id)"
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
  name: "ReadersView",
  data() {
    return {
      readers: [],
      loading: true,
      currentPage: 0,
      pageSize: 5,
      totalPages: 0,
      sortBy: "lastName",
      sortDir: "asc",
    };
  },
  methods: {
    async fetchReaders() {
      this.loading = true;
      try {
        // Zmieniony endpoint na /readers zgodnie z Twoim API
        const response = await axios.get(`http://localhost:8081/readers`, {
          params: {
            page: this.currentPage,
            size: this.pageSize,
            sort: `${this.sortBy},${this.sortDir}`,
          },
        });

        this.readers = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Error while fetching readers:", error);
      } finally {
        this.loading = false;
      }
    },
    changePage(newPage) {
      this.currentPage = newPage;
      this.fetchReaders();
    },
    goToDetails(id) {
      this.$router.push(`/readers/${id}`);
    },
    async deleteReader(id) {
      if (confirm("Are you sure you want to delete this reader?")) {
        try {
          await axios.delete(`http://localhost:8081/readers/${id}`);
          this.fetchReaders();
        } catch (error) {
          const message =
            error.response?.data?.message ||
            "Error while deleting reader (he might have active rentals)";
          alert(message);
        }
      }
    },
  },
  mounted() {
    this.fetchReaders();
  },
};
</script>

<style scoped>
.readers-container { padding: 20px; }

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

.readers-table {
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

.col-name { width: 30%; }
.col-email { width: 30%; }
.col-actions { width: 10%; }

td {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.reader-row:hover { background-color: #f9f9f9; }
.clickable { cursor: pointer; }

.actions {
  opacity: 0;
  transition: opacity 0.2s;
  display: flex;
  gap: 10px;
}

.reader-row:hover .actions { opacity: 1; }

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

.loader { text-align: center; padding: 20px; }
</style>