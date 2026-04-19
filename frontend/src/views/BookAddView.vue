<template>
  <div class="add-container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Cancel</AppButton>

    <div class="add-card">
      <h2>Add Book</h2>
      
      <form @submit.prevent="saveBook">
        <div class="form-group">
          <label>Title</label>
          <input v-model="book.title" type="text" required placeholder="Enter title...">
        </div>

        <div class="form-group author-search-container">
          <label>Author</label>
          <div class="search-wrapper">
            <input 
              v-model="searchQuery" 
              type="text" 
              placeholder="Start typing surname..."
              @focus="showDropdown = true"
            >
            
            <div v-if="showDropdown && filteredAuthors.length" class="dropdown-list">
              <div 
                v-for="author in filteredAuthors" 
                :key="author.id" 
                class="dropdown-item"
                @click="selectAuthor(author)"
              >
                {{ author.firstName }} {{ author.lastName }}
              </div>
            </div>

            <div v-if="showDropdown && searchQuery && !filteredAuthors.length" class="no-results">
              <button type="button" class="btn-add-fast" @click="openQuickAdd">
                + Add Author: {{ searchQuery }}
              </button>
            </div>
          </div>
          <div v-if="selectedAuthorName" class="selected-badge">
            Selected: <strong>{{ selectedAuthorName }}</strong> 
            <span @click="clearAuthor" class="clear-btn">✕</span>
          </div>
        </div>

        <div class="form-group">
          <label>Number of Pages</label>
          <input v-model.number="book.pages" type="number" required placeholder="0">
        </div>

        <div class="actions">
          <AppButton type="submit" theme="primary" :disabled="!book.authorId">
            Add to System
          </AppButton>
        </div>
      </form>
    </div>

    </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      book: { title: '', authorId: null, pages: null },
      authors: [],
      searchQuery: '',
      showDropdown: false,
      selectedAuthorName: '',
      showQuickAdd: false,
      newAuthor: { firstName: '', lastName: '' }
    };
  },
  computed: {
    filteredAuthors() {
      if (!this.searchQuery) return this.authors;
      const query = this.searchQuery.toLowerCase();
      return this.authors.filter(a => 
        (a.firstName + ' ' + a.lastName).toLowerCase().includes(query)
      );
    }
  },
  async mounted() {
    this.fetchAuthors();
  },
  methods: {
    async fetchAuthors() {
      const res = await axios.get('http://localhost:8081/authors');
      this.authors = res.data;
    },
    selectAuthor(author) {
      this.book.authorId = author.id;
      this.selectedAuthorName = `${author.firstName} ${author.lastName}`;
      this.searchQuery = '';
      this.showDropdown = false;
    },
    clearAuthor() {
      this.book.authorId = null;
      this.selectedAuthorName = '';
    },
    openQuickAdd() {
      const parts = this.searchQuery.split(' ');
      this.newAuthor.firstName = parts[0] || '';
      this.newAuthor.lastName = parts.slice(1).join(' ') || '';
      this.showQuickAdd = true;
      this.showDropdown = false;
    },
    async addNewAuthor() {
      try {
        const res = await axios.post('http://localhost:8081/authors', this.newAuthor);
        const createdAuthor = res.data;
        await this.fetchAuthors();
        this.selectAuthor(createdAuthor);
        this.showQuickAdd = false;
      } catch (e) {
        alert("Error adding author");
      }
    },
    async saveBook() {
      try {
        await axios.post('http://localhost:8081/books', this.book);
        this.$router.push('/books');
      } catch (e) { alert("Error saving book"); }
    }
  }
};
</script>

<style scoped>
.add-container {
  max-width: 600px;
  margin: 40px auto;
  padding: 0 20px;
}

.add-card {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.08);
  text-align: left; /* Ważne, żeby tekst nie był wycentrowany */
}

h2 {
  margin-bottom: 25px;
  color: #2c3e50;
}

.form-group {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #7f8c8d;
  font-size: 0.9rem;
}

input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  box-sizing: border-box;
  transition: border-color 0.2s;
}

input:focus {
  border-color: #3498db;
  outline: none;
}

.search-wrapper {
  position: relative;
}

.dropdown-list {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  z-index: 100;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.dropdown-item {
  padding: 10px 15px;
  cursor: pointer;
}

.dropdown-item:hover {
  background: #f8f9fa;
}

.selected-badge {
  margin-top: 10px;
  background: #e1f5fe;
  padding: 6px 12px;
  border-radius: 20px;
  display: inline-flex;
  align-items: center;
  font-size: 0.9rem;
}

.clear-btn {
  margin-left: 8px;
  color: #e74c3c;
  cursor: pointer;
  font-weight: bold;
}

.actions {
  margin-top: 30px;
  display: flex;
  justify-content: flex-end;
}
</style>