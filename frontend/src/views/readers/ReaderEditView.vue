<template>
  <div class="container">
    <app-button theme="outline" @click="$router.go(-1)">Cancel</app-button>
    <h2 class="view-title">Edit Reader</h2>
    <reader-form 
      v-if="reader" 
      :initial-data="reader" 
      submit-label="Update Reader" 
      @submit="handleUpdate" 
    />
    <div v-else class="loading">Loading reader data...</div>
  </div>
</template>

<script>
import axios from "axios";
import ReaderForm from "@/components/ReaderForm.vue";
import AppButton from "@/components/AppButton.vue";

export default {
  components: { ReaderForm, AppButton },
  data() {
    return {
      reader: null,
    };
  },
  async mounted() {
    await this.fetchReader();
  },
  methods: {
    async fetchReader() {
      try {
        const id = this.$route.params.id;
        const res = await axios.get(`http://localhost:8081/readers/${id}`);
        this.reader = res.data;
      } catch (error) {
        alert("Could not load reader data");
        this.$router.push("/readers");
      }
    },
    async handleUpdate(updatedData) {
      if (updatedData instanceof Event) {
        return;
      }
      try {
        const id = this.$route.params.id;
        await axios.put(`http://localhost:8081/readers/${id}`, updatedData);
        this.$router.go(-1);
      } catch (error) {
        alert("Error updating reader");
      }
    },
  },
};
</script>

<style scoped>
.container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
.view-title { margin-bottom: 20px; color: #2c3e50; }
.mb-4 { margin-bottom: 20px; }
</style>