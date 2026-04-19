<template>
  <div class="edit-container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Cancel</AppButton>
    
    <div v-if="loading" class="loader">Loading...</div>

    <div v-else class="edit-card">
      <h2>Edit Book: {{ originalTitle }}</h2>
      
      <form @submit.prevent="updateBook">
        <div class="form-group">
          <label>Title</label>
          <input v-model="book.title" type="text" required>
        </div>

        <div class="form-group author-search-container">
          <label>Author</label>
          <div class="search-wrapper">
            <input 
              v-model="searchQuery" 
              type="text" 
              :placeholder="selectedAuthorName ? 'Change author...' : 'Start typing surname...'"
              @focus="showDropdown = true"
            >
            
            <div v-if="showDropdown" class="dropdown-list">
              <div 
                v-for="author in filteredAuthors" 
                :key="author.id" 
                class="dropdown-item"
                @click="selectAuthor(author)"
              >
                {{ author.firstName }} {{ author.lastName }}
              </div>

              <div v-if="searchQuery && filteredAuthors.length === 0" class="no-results">
                <button type="button" class="btn-add-fast" @click="openQuickAdd">
                  + Add Author: {{ searchQuery }}
                </button>
              </div>
            </div>
          </div>
          
          <div v-if="selectedAuthorName" class="selected-badge">
            Selected: <strong>{{ selectedAuthorName }}</strong> 
            <span @click="clearAuthor" class="clear-btn">✕</span>
          </div>
        </div>

        <div class="form-group">
          <label>Number of Pages</label>
          <input v-model.number="book.pages" type="number" required>
        </div>

        <div class="actions">
          <AppButton type="submit" theme="primary" :disabled="!book.authorId">
            Save Changes
          </AppButton>
        </div>
      </form>
    </div>

    <div v-if="showQuickAdd" class="modal-overlay">
      <div class="modal-content">
        <h3>Add New Author</h3>
        <div class="form-group">
          <input v-model="newAuthor.firstName" placeholder="First Name">
        </div>
        <div class="form-group">
          <input v-model="newAuthor.lastName" placeholder="Last Name">
        </div>
        <div class="modal-actions">
          <button type="button" @click="showQuickAdd = false">Cancel</button>
          <button type="button" class="save-btn" @click="addNewAuthor">Add and Select</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      book: { title: '', authorId: null, pages: 0 },
      originalTitle: '',
      authors: [],
      searchQuery: '',
      showDropdown: false,
      selectedAuthorName: '',
      loading: true,
      showQuickAdd: false,
      newAuthor: { firstName: '', lastName: '' }
    };
  },
  computed: {
    filteredAuthors() {
      const q = this.searchQuery.toLowerCase();
      return this.authors.filter(a => 
        `${a.firstName} ${a.lastName}`.toLowerCase().includes(q)
      );
    }
  },
  async mounted() {
    const id = this.$route.params.id;
    try {
      const [bookRes, authorsRes] = await Promise.all([
        axios.get(`http://localhost:8081/books/${id}`),
        axios.get('http://localhost:8081/authors')
      ]);

      this.authors = authorsRes.data;
      this.book = {
        title: bookRes.data.title,
        authorId: bookRes.data.authorId,
        pages: bookRes.data.pages
      };
      this.originalTitle = bookRes.data.title;
      
      const currentAuthor = this.authors.find(a => a.id === this.book.authorId);
      if (currentAuthor) {
        this.selectedAuthorName = `${currentAuthor.firstName} ${currentAuthor.lastName}`;
      }
    } catch (e) {
      alert("Error loading data.");
    } finally {
      this.loading = false;
    }
  },
  methods: {
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
        this.authors.push(res.data);
        this.selectAuthor(res.data);
        this.showQuickAdd = false;
      } catch (e) { alert("Error saving author"); }
    },
    async updateBook() {
      try {
        await axios.put(`http://localhost:8081/books/${this.$route.params.id}`, this.book);
        this.$router.push(`/books/${this.$route.params.id}`);
      } catch (e) { alert("Error updating book"); }
    }
  }
};
</script>

<style scoped>
.edit-container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
.edit-card { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); text-align: left; }

.form-group { margin-bottom: 20px; display: flex; flex-direction: column; }
.form-group label { margin-bottom: 8px; font-weight: 600; color: #7f8c8d; }

input { 
  width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; 
  box-sizing: border-box;
}

.search-wrapper { position: relative; }
.dropdown-list { 
  position: absolute; top: 100%; left: 0; right: 0; 
  background: white; border: 1px solid #ddd; z-index: 100;
  max-height: 200px; overflow-y: auto; border-radius: 8px;
}
.dropdown-item { padding: 10px; cursor: pointer; }
.dropdown-item:hover { background: #f0f7ff; }

.selected-badge { 
  margin-top: 10px; background: #e1f5fe; padding: 8px 12px; 
  border-radius: 20px; display: inline-flex; align-items: center;
}
.clear-btn { margin-left: 10px; color: #e74c3c; cursor: pointer; font-weight: bold; }

.modal-overlay { 
  position: fixed; top: 0; left: 0; right: 0; bottom: 0; 
  background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 1000;
}
.modal-content { background: white; padding: 30px; border-radius: 12px; width: 400px; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }

.btn-add-fast { width: 100%; padding: 10px; background: #fdf2f2; border: none; color: #d32f2f; cursor: pointer; }
</style>