<template>
  <div class="container">
    <AppButton theme="outline" @click="$router.go(-1)" class="mb-4">Cancel</AppButton>
    <h2>Add New Reader</h2>
    <ReaderForm 
      submit-label="Add Reader" 
      @submit="handleCreate" 
    />
  </div>
</template>

<script>
import axios from "axios";
import ReaderForm from "@/components/ReaderForm.vue";

export default {
  components: { ReaderForm },
  methods: {
    async handleCreate(readerData) {
      try {
        await axios.post("http://localhost:8081/readers", readerData);
        alert("Reader added successfully!");
        this.$router.push("/readers");
      } catch (error) {
        alert("Error adding reader: " + (error.response?.data?.message || "Server error"));
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