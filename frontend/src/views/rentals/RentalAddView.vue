<template>
  <div class="form-container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Back</AppButton>
    
    <div class="form-card">
      <div class="form-header">
        <span class="icon">🔍</span>
        <h2>Add Rental</h2>
      </div>

      <div v-if="loadingData" class="loader">Loading lists...</div>

      <form v-else @submit.prevent="handleSubmit">
        
        <div class="form-group">
          <label>Search Book</label>
          <div class="search-wrapper">
            <input 
              type="text" 
              v-model="bookSearch" 
              placeholder="Enter book title..."
              @focus="showBookResults = true"
            />
            <ul v-if="showBookResults && filteredBooks.length" class="results-list">
              <li 
                v-for="book in filteredBooks" 
                :key="book.id" 
                @click="selectBook(book)"
              >
                {{ book.title }} <small>(ID: #{{ book.id }})</small>
              </li>
            </ul>
          </div>
          <div v-if="selectedBook" class="selection-badge">
            Selected: <strong>{{ selectedBook.title }}</strong>
            <span class="clear-selection" @click="selectedBook = null">✕</span>
          </div>
        </div>

        <div class="form-group">
          <label>Search Reader</label>
          <div class="search-wrapper">
            <input 
              type="text" 
              v-model="userSearch" 
              placeholder="First name or last name..."
              @focus="showUserResults = true"
            />
            <ul v-if="showUserResults && filteredUsers.length" class="results-list">
              <li 
                v-for="user in filteredUsers" 
                :key="user.id" 
                @click="selectUser(user)"
              >
                {{ user.firstName }} {{ user.lastName }}
              </li>
            </ul>
          </div>
          <div v-if="selectedUser" class="selection-badge">
            For: <strong>{{ selectedUser.firstName }} {{ selectedUser.lastName }}</strong>
            <span class="clear-selection" @click="selectedUser = null">✕</span>
          </div>
        </div>

        <div class="form-group">
          <label>Rental Date</label>
          <input v-model="rentalDate" type="date" required />
        </div>

        <div class="actions">
          <AppButton 
            type="submit" 
            theme="primary" 
            :disabled="!selectedBook || !selectedUser || submitting"
          >
            {{ submitting ? "Saving..." : "Confirm" }}
          </AppButton>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      allBooks: [],
      allUsers: [],
      bookSearch: "",
      userSearch: "",
      selectedBook: null,
      selectedUser: null,
      rentalDate: new Date().toISOString().substr(0, 10),
      
      showBookResults: false,
      showUserResults: false,
      loadingData: true,
      submitting: false
    };
  },
  computed: {
    filteredBooks() {
      if (!this.bookSearch) return [];
      return this.allBooks.filter(b => 
        b.title.toLowerCase().includes(this.bookSearch.toLowerCase()) && !b.rented
      ).slice(0, 5);
    },
    filteredUsers() {
      if (!this.userSearch) return [];
      const search = this.userSearch.toLowerCase();
      return this.allUsers.filter(u => 
        u.firstName.toLowerCase().includes(search) || 
        u.lastName.toLowerCase().includes(search)
      ).slice(0, 5);
    }
  },
  async mounted() {
    try {
      const [booksRes, usersRes] = await Promise.all([
        axios.get("http://localhost:8081/books?size=1000"),
        axios.get("http://localhost:8081/readers?size=1000")
      ]);
      this.allBooks = booksRes.data.content || booksRes.data;
      this.allUsers = usersRes.data.content || usersRes.data;
    } catch (e) {
      console.error("Fetch error", e);
    } finally {
      this.loadingData = false;
    }
  },
  methods: {
    selectBook(book) {
      this.selectedBook = book;
      this.bookSearch = book.title;
      this.showBookResults = false;
    },
    selectUser(user) {
      this.selectedUser = user;
      this.userSearch = `${user.firstName} ${user.lastName}`;
      this.showUserResults = false;
    },
    async handleSubmit() {
      this.submitting = true;
      try {
        await axios.post("http://localhost:8081/rentals", {
          bookId: this.selectedBook.id,
          readerId: this.selectedUser.id,
          rentalDate: new Date(this.rentalDate).toISOString()
        });
        this.$router.push("/rentals");
      } catch (e) {
        alert("Error saving.");
      } finally {
        this.submitting = false;
      }
    }
  }
};
</script>

<style scoped>
.form-container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
.form-card { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }

.search-wrapper { position: relative; width: 100%; }

.results-list {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #ddd;
  border-top: none;
  border-radius: 0 0 8px 8px;
  list-style: none;
  padding: 0;
  margin: 0;
  z-index: 10;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

.results-list li {
  padding: 12px;
  cursor: pointer;
  border-bottom: 1px solid #eee;
}

.results-list li:hover { background: #f0f4f8; }

.selection-badge {
  margin-top: 10px;
  padding: 8px 12px;
  background: #e1f5fe;
  color: #0288d1;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
}

.clear-selection { cursor: pointer; font-weight: bold; margin-left: 10px; }

.form-group { margin-bottom: 20px; display: flex; flex-direction: column; align-items: flex-start; position: relative; }
label { font-weight: 600; margin-bottom: 8px; color: #7f8c8d; }
input { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; }

.mb-4 { margin-bottom: 20px; }
.actions { display: flex; justify-content: flex-end; margin-top: 20px; }
</style>