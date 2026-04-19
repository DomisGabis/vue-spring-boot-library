<template>
  <nav class="navbar">
    <h1><router-link to="/">Library Manager</router-link></h1>
    <ul>
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
  align-content: center;
  align-items: center;
  justify-content: space-between;
  padding: 0.5rem;
  padding-left: 3rem;
  padding-right: 3rem;
  background: #2c3e50;
  color: white;
}
.navbar ul {
  display: flex;
  align-content: center;
  align-items: center;
  list-style: none;
  gap: 20px;
}

.navbar a {
  color: white;
  text-decoration: none;
  position: relative; 
  padding: 5px 0;
}

.navbar a::after {
  content: '';
  position: absolute;
  width: 100%;
  height: 2px;
  bottom: 0;
  left: 0;
  background-color: #36ca88;
  
  transform: scaleX(0);
  transform-origin: center;
  transition: transform 0.3s ease-in-out;
}

.navbar a:hover::after {
  transform: scaleX(1);
}

</style>