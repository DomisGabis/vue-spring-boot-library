<template>
  <div class="container">
    <app-button theme="outline" @click="$router.go(-1)">Cancel</app-button>
    <h2>Add New Reader</h2>
    <reader-form 
      submit-label="Add Reader" 
      @submit="handleCreate" 
    />
  </div>
</template>

<script>
import axios from "axios";
import ReaderForm from "@/components/ReaderForm.vue";
import AppButton from "@/components/AppButton.vue";

export default {
  name: "ReaderAddView",
  components: { ReaderForm, AppButton },
  methods: {
    async handleCreate(readerData) {
      if (readerData instanceof Event) {
        return;
      }
      try {
        await axios.post("http://localhost:8081/readers", readerData);
        this.$router.go(-1);
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
</style>