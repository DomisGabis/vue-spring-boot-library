<template>
  <nav class="navbar">
    <h1>Library Manager</h1>
    <ul>
      <li><router-link to="/">Home</router-link></li>
      <li><router-link to="/books">Books</router-link></li>
      <li><router-link to="/authors">Authors</router-link></li>
      <li><router-link to="/readers">Readers</router-link></li>
      <li><router-link to="/rentals">Rentals</router-link></li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "AppNavbar",
};
</script>
<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  padding: 1rem;
  background: #2c3e50;
  color: white;
}
.navbar ul {
  display: flex;
  list-style: none;
  gap: 20px;
}
.navbar a {
  color: white;
  text-decoration: none;
}
</style>