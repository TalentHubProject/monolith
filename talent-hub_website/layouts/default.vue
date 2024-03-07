
<template>
  <div >

   <Navbar/>
       <slot />
      <SpeedInsights />
    <Footer/>
  </div>
</template>
<script>

import './assets/css/main.css'
import { SpeedInsights } from "@vercel/speed-insights/nuxt"

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
  },
  setup() {
    useSeoMeta({
      title: 'Talent Hub',
      description: 'Réunissant passionnés d\'art, de développement, de musique et bien plus encore',
      image: 'https://talenthub.fr/assets/images/og.png',
      url: 'https://talenthub.fr'
    })
  }
};
</script>
<style lang="scss" scoped></style>
