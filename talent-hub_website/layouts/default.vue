
<template>
  <div >

   <Navbar/>
       <slot />
    <Footer/>
  </div>
</template>
<script>

import './assets/css/main.css'

export default {
  data() {
    return {
      isMenuHidden: true
    };
  },
  methods: {
    toggleMenu() {
      this.isMenuHidden = !this.isMenuHidden;
    }
  }
};
</script>
<style lang="scss" scoped></style>
