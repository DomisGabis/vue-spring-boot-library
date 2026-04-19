<template>
  <router-link v-if="to" :to="to" class="btn link" :class="'btn-' + theme">
    <slot></slot>
  </router-link>

  <button 
    v-else 
    class="btn" 
    :class="'btn-' + theme" 
    :type="type" 
    @click="(e) => $emit('click', e)"
  >
    <slot></slot>
  </button>
</template>

<script>
export default {
  name: 'AppButton',
  inheritAttrs: false,
  props: {
    theme: { type: String, default: 'primary' },
    type: { type: String, default: 'button' },
    to: { type: String, default: null }
  }
}
</script>

<style scoped>
.btn {
  padding: 10px 20px;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  border: 1px solid transparent;
  transition: all 0.2s ease;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-family: inherit;
  font-size: 14px;
}

.btn-primary {
  background-color: #36ca88;
  color: white;
}
.btn-primary:hover { background-color: #29a26c; }

.btn-danger {
  background-color: #e74c3c;
  color: white;
}
.btn-danger:hover { background-color: #c0392b; }

.btn-none {
  
}

.btn-outline {
  background-color: transparent;
  border: 1px solid #ddd;
  color: #7f8c8d;
}
.btn-outline:hover { 
  background-color: #f8f9fa; 
  border-color: #bdc3c7;
  color: #2c3e50;
}
.link {
  text-decoration: none;
}
</style>