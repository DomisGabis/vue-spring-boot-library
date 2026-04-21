<template>
  <div class="layout">
    <app-navbar />

    <main class="content">
      <router-view />
    </main>
    
    <app-footer />
  </div>
</template>

<script>
import AppNavbar from '@/components/AppNavbar.vue';
import AppFooter from '@/components/AppFooter.vue';
export default {  
  name: 'MainLayout',
  components: {
    AppNavbar,
    AppFooter
  }
}
</script>


<style scoped>
.content {
  padding: 20px;
  min-height: 80vh;
}
</style>