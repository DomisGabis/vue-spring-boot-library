<template>
  <div class="form-card">
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Book Title</label>
        <input
          v-model="localBook.title"
          type="text"
          required
          placeholder="Enter book title..."
        />
      </div>

      <div class="form-group author-search-container">
        <label>Author</label>
        <div class="search-wrapper">
          <input
            v-model="searchQuery"
            type="text"
            :placeholder="
              selectedAuthorName
                ? 'Change author...'
                : 'Start typing surname...'
            "
            @focus="showDropdown = true"
          />

          <div v-if="showDropdown" class="dropdown-list">
            <div
              v-for="author in filteredAuthors"
              :key="author.id"
              class="dropdown-item"
              @click="selectAuthor(author)"
            >
              {{ author.firstName }} {{ author.lastName }}
            </div>

            <div
              v-if="searchQuery && filteredAuthors.length === 0"
              class="no-results"
            >
              <button type="button" class="btn-add-fast" @click="openQuickAdd">
                + No results for: "{{ searchQuery }}". Click to add author.
              </button>
            </div>
          </div>
        </div>

        <div v-if="selectedAuthorName" class="selected-badge">
          Selected: <strong>{{ selectedAuthorName }}</strong>
          <span @click="clearAuthor" class="clear-btn" title="Clear selection"
            >✕</span
          >
        </div>
      </div>

      <div class="form-group">
        <label>Number of Pages</label>
        <input
          v-model.number="localBook.pages"
          type="number"
          required
          min="1"
          placeholder="0"
        />
      </div>

      <div class="actions">
        <AppButton type="submit" theme="primary" :disabled="!localBook.title">
          {{ submitLabel }}
        </AppButton>
      </div>
    </form>

    <div
      v-if="showQuickAdd"
      class="modal-overlay"
      @click.self="showQuickAdd = false"
    >
      <div class="modal-content">
        <h3>Add New Author</h3>
        <div class="form-group">
          <label>First Name</label>
          <input v-model="newAuthor.firstName" placeholder="First Name" />
        </div>
        <div class="form-group">
          <label>Last Name</label>
          <input v-model="newAuthor.lastName" placeholder="Last Name" />
        </div>
        <div class="modal-actions">
          <AppButton type="button" theme="outline" @click="showQuickAdd = false"
            >Cancel</AppButton
          >
          <AppButton type="button" theme="primary" @click="addNewAuthor"
            >Save and Select</AppButton
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BookForm",
  props: {
    initialData: {
      type: Object,
      default: () => ({ title: "", authorId: null, pages: null }),
    },
    submitLabel: {
      type: String,
      default: "Save Book",
    },
  },
  data() {
    return {
      localBook: { ...this.initialData },
      authors: { content: [] },
      searchQuery: "",
      showDropdown: false,
      selectedAuthorName: "",
      showQuickAdd: false,
      newAuthor: { firstName: "", lastName: "" },
    };
  },
  computed: {
    filteredAuthors() {
      const q = this.searchQuery.toLowerCase();
      return this.authors.content.filter((a) =>
        `${a.firstName} ${a.lastName}`.toLowerCase().includes(q)
      );
    },
  },
  watch: {
    initialData: {
      handler(newVal) {
        this.localBook = { ...newVal };
        this.updateSelectedAuthorName();
      },
      deep: true,
    },
  },
  async mounted() {
    await this.fetchAuthors();
    this.updateSelectedAuthorName();

    document.addEventListener("click", this.handleClickOutside);
  },
  unmounted() {
    document.removeEventListener("click", this.handleClickOutside);
  },
  methods: {
    async fetchAuthors() {
      try {
        const res = await axios.get("http://localhost:8081/authors", {
          params: { page: 0, size: 1000 },
        });
        this.authors = res.data;
      } catch (e) {
        console.error("Error fetching authors");
      }
    },
    updateSelectedAuthorName() {
      const author = this.authors.content.find(
        (a) => a.id === this.localBook.authorId
      );
      this.selectedAuthorName = author
        ? `${author.firstName} ${author.lastName}`
        : "";
    },
    selectAuthor(author) {
      this.localBook.authorId = author.id;
      this.selectedAuthorName = `${author.firstName} ${author.lastName}`;
      this.searchQuery = "";
      this.showDropdown = false;
    },
    clearAuthor() {
      this.localBook.authorId = null;
      this.selectedAuthorName = "";
    },
    openQuickAdd() {
      const parts = this.searchQuery.split(" ");
      this.newAuthor.firstName = parts[0] || "";
      this.newAuthor.lastName = parts.slice(1).join(" ") || "";
      this.showQuickAdd = true;
      this.showDropdown = false;
    },
    async addNewAuthor() {
      try {
        const res = await axios.post(
          "http://localhost:8081/authors",
          this.newAuthor
        );

        const savedAuthor = res.data;

        this.authors.content = [...this.authors.content, savedAuthor];

        this.selectAuthor(savedAuthor);

        this.showQuickAdd = false;
        this.newAuthor = { firstName: "", lastName: "" };
      } catch (e) {
        console.error(e);
        alert(
          "Error adding author: " +
            (e.response?.data?.message || "Unknown error")
        );
      }
    },
    handleSubmit() {
      const payload = {
        title: this.localBook.title,
        pages: this.localBook.pages,
        authorId: this.localBook.authorId,
      };
      this.$emit("submit", payload);
    },
    handleClickOutside(e) {
      if (!this.$el.contains(e.target)) {
        this.showDropdown = false;
      }
    },
  },
};
</script>

<style scoped>
.form-card {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  text-align: left;
}

.form-group {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
}

.form-group label {
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
  box-sizing: border-box; /* Kluczowe dla image_a44279.png! */
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
  max-height: 200px;
  overflow-y: auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.dropdown-item {
  padding: 10px 15px;
  cursor: pointer;
}

.dropdown-item:hover {
  background: #f0f7ff;
}

.btn-add-fast {
  width: 100%;
  padding: 12px;
  background: #fdf2f2;
  border: none;
  color: #d32f2f;
  cursor: pointer;
  text-align: left;
  font-weight: 600;
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
  margin-left: 10px;
  color: #e74c3c;
  cursor: pointer;
  font-weight: bold;
}

.actions {
  margin-top: 30px;
  display: flex;
  justify-content: flex-end;
}

/* Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 30px;
  border-radius: 12px;
  width: 100%;
  max-width: 400px;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.btn-save {
  background: #3498db;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-cancel {
  background: #eee;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
}
</style>