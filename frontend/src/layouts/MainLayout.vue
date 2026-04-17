<template>
  <div class="layout">
    <AppNavbar />

    <main class="content">
      <router-view />
    </main>
    
    <AppFooter />
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