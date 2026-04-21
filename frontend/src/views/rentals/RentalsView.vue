<template>
  <div class="rentals-container">
    <div class="header">
      <h1>Rentals List</h1>
      <app-button theme="primary" :to="'/rentals/add'">Add New Rental</app-button>
    </div>

    <div class="filter-tabs">
      <button
        :class="['tab-btn', { active: showOnlyActive }]"
        @click="setFilter(true)"
      >
        Active Rentals
      </button>
      <button
        :class="['tab-btn', { active: !showOnlyActive }]"
        @click="setFilter(false)"
      >
        All Rentals
      </button>
    </div>

    <div v-if="loading" class="loader">Loading rentals...</div>

    <div v-else>
      <table class="rentals-table">
        <thead>
          <tr>
            <th class="col-book">Book Title</th>
            <th class="col-user">Reader</th>
            <th class="col-date">Rental Date</th>
            <th class="col-status">Status</th>
            <th class="col-actions actions-header">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rental in rentals" :key="rental.id" class="rental-row">
            <td>
              <router-link :to="`/books/${rental.bookId}`">{{
                rental.bookTitle
              }}</router-link>
            </td>
            <td>
              <router-link :to="`/readers/${rental.readerId}`"
                >{{ rental.readerFirstName }}
                {{ rental.readerLastName }}</router-link
              >
            </td>
            <td>{{ formatDate(rental.rentalDate) }}</td>
            <td>
              <span
                :class="[
                  'status-badge',
                  rental.returned ? 'returned-rent' : 'active-rent',
                ]"
              >
                {{
                  rental.returned
                    ? "Returned: " + formatDate(rental.returnDate)
                    : "Active"
                }}
              </span>
            </td>

            <td class="actions">
              <app-button theme="none" @click.stop="deleteRental(rental.id)" > 🗑️ </app-button>
              <app-button v-if="rental.returned === false" theme="none" @click="returnBook(rental.id)" > ↩️ </app-button>
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
import AppButton from "@/components/AppButton.vue";

export default {
  name: "RentalsView",
  components: { AppButton },
  data() {
    return {
      rentals: [],
      loading: true,
      currentPage: 0,
      pageSize: 5,
      totalPages: 0,
      showOnlyActive: true,
      sortBy: "rentalDate",
      sortDir: "desc",
    };
  },
  methods: {
    async fetchRentals() {
      this.loading = true;
      try {
        const response = await axios.get(`http://localhost:8081/rentals`, {
          params: {
            page: this.currentPage,
            size: this.pageSize,
            sort: `${this.sortBy},${this.sortDir}`,
            active: this.showOnlyActive,
          },
        });

        this.rentals = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Error fetching rentals:", error);
      } finally {
        this.loading = false;
      }
    },
    setFilter(activeOnly) {
      this.showOnlyActive = activeOnly;
      this.currentPage = 0;
      this.fetchRentals();
    },
    changePage(newPage) {
      this.currentPage = newPage;
      this.fetchRentals();
    },
    formatDate(dateString) {
      if (!dateString) return "-";
      return new Date(dateString).toLocaleDateString();
    },
    async returnBook(id) {
      if (confirm("Mark this book as returned?")) {
        try {
          const rentalToUpdate = this.rentals.find((r) => r.id === id);
          const updatedData = {
            ...rentalToUpdate,
            returnDate: new Date().toISOString(),
          };
          await axios.put(`http://localhost:8081/rentals/${id}`, updatedData);
          this.fetchRentals();
        } catch (error) {
          alert("Error during return process");
        }
      }
    },
    async deleteRental(id) {
      if (confirm("Delete this rental record permanently?")) {
        try {
          await axios.delete(`http://localhost:8081/rentals/${id}`);
          this.fetchRentals();
        } catch (error) {
          alert("Error deleting record");
        }
      }
    },
  },
  mounted() {
    this.fetchRentals();
  },
};
</script>

<style scoped>
.rentals-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.filter-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 2rem;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.tab-btn {
  padding: 8px 16px;
  border: none;
  background: none;
  cursor: pointer;
  font-weight: 600;
  color: #7f8c8d;
  transition: all 0.3s;
  border-radius: 4px;
}

.tab-btn.active {
  background-color: #f0f8f3;
  color: #42b983;
}

/* Tabela */
.rentals-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
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

.col-book {
  width: 30%;
}
.col-user {
  width: 25%;
}
.col-date {
  width: 20%;
}
.col-status {
  width: 15%;
}
.col-actions {
  width: 10%;
}

/* Status Badges */
.status-badge {
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: bold;
}

.active-rent {
  background: #fff5f5;
  color: #e53e3e;
}
.returned-rent {
  background: #e6fffa;
  color: #38a169;
}

/* Actions */
.actions {
  display: flex;
  gap: 12px;
  opacity: 0;
  transition: opacity 0.2s;
}

.actions button {
  border: none;
  background: none;
  cursor: pointer;
  transition: transform 0.2s;
}

.rental-row:hover .actions {
  opacity: 1;
}


/* Paginacja */
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