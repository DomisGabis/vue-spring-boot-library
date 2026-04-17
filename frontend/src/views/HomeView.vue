<template>
  <div class="home-container">
    <header class="home-header">
      <h1>Library Manager</h1>
      <p>Welcome to the library management system!</p>
    </header>

    <div class="stats-grid">
      <div class="stat-card">
        <div class="icon">📚</div>
        <div class="info">
          <h3>Books</h3>
          <p class="number">128</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="icon">✍️</div>
        <div class="info">
          <h3>Authors</h3>
          <p class="number">45</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="icon">👥</div>
        <div class="info">
          <h3>Readers</h3>
          <p class="number">312</p>
        </div>
      </div>

      <div class="stat-card active-rentals">
        <div class="icon">⏳</div>
        <div class="info">
          <h3>Active Rentals</h3>
          <p class="number">12</p>
        </div>
      </div>
    </div>

    <section class="quick-actions">
      <h2>Quick Actions</h2>
      <div class="actions-row">
        <router-link to="/books" class="action-btn">Add Book</router-link>
        <router-link to="/rentals" class="action-btn">New Rental</router-link>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "HomeView"
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
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
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

/* Kolor wyróżniający dla wypożyczeń */
.active-rentals {
  border-left: 5px solid #e74c3c;
}

.quick-actions h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.actions-row {
  display: flex;
  gap: 15px;
}

.action-btn {
  padding: 12px 24px;
  background-color: #3498db;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-weight: bold;
  transition: background 0.3s;
}

.action-btn:hover {
  background-color: #2980b9;
}
</style>