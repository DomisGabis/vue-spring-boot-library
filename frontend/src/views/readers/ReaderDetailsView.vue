<template>
  <div class="details-container">
    <app-button theme="outline" @click="$router.go(-1)"
      >Back</app-button
    >

    <div v-if="loading" class="loader">Loading reader data...</div>

    <div v-else-if="reader" class="reader-wrapper">
      <div class="reader-card">
        <div class="reader-header">
          <div class="reader-icon">👤</div>
          <div class="title-section">
            <h1>{{ reader.firstName }} {{ reader.lastName }}</h1>
            <p class="subtitle">Reader ID: #{{ reader.id }}</p>
          </div>
        </div>

        <div class="reader-content">
          <div class="info-group">
            <label>Email Address</label>
            <p>{{ reader.email || "No email provided" }}</p>
          </div>
          <div class="info-group">
            <label>Member Since</label>
            <p>{{ this.firstRental ? formatDate(this.firstRental) : "N/A" }}</p>
          </div>
        </div>

        <div class="reader-actions">
          <app-button theme="primary" :to="`/readers/${reader.id}/edit`"
            >Edit data</app-button
          >
          <app-button theme="danger" @click="deleteReader"
            >Delete Reader</app-button
          >
        </div>
      </div>

      <div class="history-section">
        <h2>Rental History</h2>

        <div v-if="rentals.length === 0" class="no-data">
          This reader hasn't rented any books yet.
        </div>

        <div v-else>
          <table class="history-mini-table">
            <thead>
              <tr>
                <th>Book Title</th>
                <th>Rental Date</th>
                <th>Return Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="rental in rentals" :key="rental.id">
                <td
                  class="clickable"
                  @click="$router.push(`/books/${rental.bookId}`)"
                >
                  {{ rental.bookTitle }}
                </td>
                <td>{{ formatDate(rental.rentalDate) }}</td>
                <td>
                  {{ rental.returnDate ? formatDate(rental.returnDate) : "—" }}
                </td>
                <td>
                  <span
                    :class="[
                      'status-badge',
                      rental.returnDate ? 'returned' : 'active',
                    ]"
                  >
                    {{ rental.returnDate ? "Returned" : "Active" }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>

          <div class="pagination-mini">
            <button
              :disabled="currentPage === 0"
              @click="changePage(currentPage - 1)"
            >
              ‹
            </button>
            <span>{{ currentPage + 1 }} / {{ totalPages }}</span>
            <button
              :disabled="currentPage >= totalPages - 1"
              @click="changePage(currentPage + 1)"
            >
              ›
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="error-msg">Reader not found.</div>
  </div>
</template>

<script>
import axios from "axios";
import AppButton from "@/components/AppButton.vue";

export default {
  name: "ReaderDetailsView",
  components: { AppButton },
  data() {
    return {
      reader: null,
      rentals: [],
      loading: true,
      currentPage: 0,
      pageSize: 5,
      totalPages: 0,
      firstRental: null,
    };
  },
  async mounted() {
    await this.fetchReaderData();
  },
  methods: {
    async fetchReaderData() {
      const readerId = this.$route.params.id;
      this.loading = true;
      try {
        const readerRes = await axios.get(
          `http://localhost:8081/readers/${readerId}`
        );
        this.reader = readerRes.data;

        await this.fetchReaderRentals();
      } catch (error) {
        console.error("Error fetching reader details:", error);
      } finally {
        this.loading = false;
      }
    },
    async fetchReaderRentals() {
      try {
        const response = await axios.get(
          `http://localhost:8081/rentals/reader/${this.reader.id}`,
          {
            params: {
              page: this.currentPage,
              size: this.pageSize,
            },
          }
        );

        this.rentals = response.data.content || [];
        this.totalPages = response.data.totalPages || 0;
        this.firstRental = this.rentals[0]?.rentalDate || null;
      } catch (error) {
        console.error("Error fetching reader rentals:", error);
      }
    },
    changePage(newPage) {
      this.currentPage = newPage;
      this.fetchReaderRentals();
    },
    async deleteReader() {
      if (
        confirm(
          "Are you sure? This will delete the reader and their rental history!"
        )
      ) {
        try {
          await axios.delete(`http://localhost:8081/readers/${this.reader.id}`);
          this.$router.push("/readers");
        } catch (error) {
          alert(error.response?.data?.message || "Error deleting reader");
        }
      }
    },
    formatDate(dateString) {
      if (!dateString) return "";
      return new Date(dateString).toLocaleDateString();
    },
  },
};
</script>

<style scoped>
/* Style spójne z AuthorDetailsView */
.details-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
.reader-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  padding: 30px;
  margin-bottom: 30px;
}
.reader-header {
  display: flex;
  align-items: center;
  gap: 20px;
  border-bottom: 2px solid #f0f0f0;
  padding-bottom: 20px;
  margin-bottom: 20px;
}
.reader-icon {
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
.reader-content {
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
.reader-actions {
  margin-top: 40px;
  display: flex;
  gap: 15px;
  justify-content: flex-end;
}

.history-section {
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}
.history-section h2 {
  margin-top: 0;
  color: #2c3e50;
  font-size: 1.5rem;
  margin-bottom: 20px;
}

.history-mini-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}
.history-mini-table th {
  text-align: left;
  padding: 10px;
  border-bottom: 2px solid #eee;
  color: #7f8c8d;
  font-size: 0.9rem;
}
.history-mini-table td {
  padding: 12px 10px;
  border-bottom: 1px solid #f9f9f9;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: bold;
  text-transform: uppercase;
}
.status-badge.active {
  background: #fff5f5;
  color: #e53e3e;
}
.status-badge.returned {
  background: #f0fff4;
  color: #38a169;
}

.clickable {
  cursor: pointer;
  color: #3498db;
  font-weight: 500;
}
.clickable:hover {
  text-decoration: underline;
}

.pagination-mini {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}
.pagination-mini button {
  padding: 5px 10px;
  cursor: pointer;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
}
.no-data {
  color: #95a5a6;
  font-style: italic;
}
</style>