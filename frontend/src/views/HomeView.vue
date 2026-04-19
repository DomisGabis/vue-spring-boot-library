<template>
  <div class="home-container">
    <header class="home-header">
      <h1>Library Manager</h1>
      <p>Welcome to the library management system!</p>
    </header>
    <section class="stats">
      <h2>Statistics</h2>
      <div v-if="loading">Loading...</div>

      <div v-else class="stats-grid">
        <div class="stat-card">
          <div class="icon">📚</div>
          <div class="info">
            <h3>Books</h3>
            <p class="number">{{ stats.books }}</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="icon">✍️</div>
          <div class="info">
            <h3>Authors</h3>
            <p class="number">{{ stats.authors }}</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="icon">👥</div>
          <div class="info">
            <h3>Readers</h3>
            <p class="number">{{ stats.readers }}</p>
          </div>
        </div>

        <div class="stat-card rentals">
          <div class="icon">⏳</div>
          <div class="info">
            <h3>Active Rentals</h3>
            <p class="number">{{ stats.rentals }}</p>
          </div>
        </div>
      </div>
    </section>
    <section class="quick-actions">
      <h2>Quick Actions</h2>
      <div class="actions-row">
        <AppButton to="/books/add">Add Book</AppButton>
        <AppButton to="/rentals/add">New Rental</AppButton>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "HomeView",
  data() {
    return {
      stats: {
        books: 0,
        authors: 0,
        readers: 0,
        rentals: 0,
      },
      loading: true,
      error: null,
    };
  },
  async mounted() {
    try {
      const [booksRes, authorsRes, readersRes, rentalsRes] = await Promise.all([
        axios.get("http://localhost:8081/books/count"),
        axios.get("http://localhost:8081/authors/count"),
        axios.get("http://localhost:8081/readers/count"),
        axios.get("http://localhost:8081/rentals/active-count"),
      ]);

      this.stats.books = booksRes.data;
      this.stats.authors = authorsRes.data;
      this.stats.readers = readersRes.data;
      this.stats.rentals = rentalsRes.data;
    } catch (err) {
      this.error = "Failed to fetch statistics";
      console.error(err);
    } finally {
      this.loading = false;
    }
  },
};
</script>

<style scoped>
.home-container {
  max-width: 1200px;
  margin: 0 auto;
  text-align: left;
}

.home-header {
  margin-bottom: 2rem;
  border-bottom: 2px solid #eee;
  padding-bottom: 1rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 3rem;
}

.stat-card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  transition: transform 0.2s;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.icon {
  font-size: 2.5rem;
  margin-right: 15px;
}

.info h3 {
  margin: 0;
  font-size: 0.9rem;
  color: #666;
  text-transform: uppercase;
}

.info .number {
  margin: 5px 0 0 0;
  font-size: 1.8rem;
  font-weight: bold;
  color: #2c3e50;
}

.rentals {
  /* border-left: 5px solid #e74c3c; */
}

.quick-actions h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.actions-row {
  display: flex;
  gap: 15px;
}
</style>