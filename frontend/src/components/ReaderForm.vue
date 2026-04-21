<template>
  <div class="form-card">
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>First Name</label>
        <input 
          v-model="localReader.firstName" 
          type="text" 
          required 
          placeholder="Enter first name..."
        >
      </div>

      <div class="form-group">
        <label>Last Name</label>
        <input 
          v-model="localReader.lastName" 
          type="text" 
          required 
          placeholder="Enter last name..."
        >
      </div>

      <div class="form-group">
        <label>Email Address</label>
        <input 
          v-model="localReader.email" 
          type="email" 
          required 
          placeholder="email@example.com"
        >
      </div>

      <div class="actions">
        <app-button type="submit" theme="primary">
          {{ submitLabel }}
        </app-button>
      </div>
    </form>
  </div>
</template>

<script>
import AppButton from './AppButton.vue';
export default {
  name: 'reader-form',
  components: { AppButton },
  props: {
    initialData: {
      type: Object,
      default: () => ({ firstName: '', lastName: '', email: '' })
    },
    submitLabel: {
      type: String,
      default: 'Save Reader'
    }
  },
  data() {
    return {
      localReader: { ...this.initialData }
    };
  },
  watch: {
    initialData: {
      handler(newVal) {
        this.localReader = { ...newVal };
      },
      deep: true
    }
  },
  methods: {
    handleSubmit() {
      this.$emit('submit', this.localReader);
    }
  }
};
</script>

<style scoped>
.form-card {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.08);
}

.form-group {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.form-group label {
  margin-bottom: 8px;
  font-weight: 600;
  color: #7f8c8d;
}

input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-sizing: border-box;
}

.actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>