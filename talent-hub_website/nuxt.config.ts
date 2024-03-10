// https://nuxt.com/docs/api/configuration/nuxt-config
import {process} from "std-env";

export default defineNuxtConfig({
  devtools: { enabled: true },
  css: [
    "/assets/css/main.css"
  ],
  modules: [
      '@nuxtjs/sitemap',
    'nuxt-swiper',
    '@nuxtjs/tailwindcss',
    ['@nuxtjs/google-fonts', {
      families: {
        // 'Poppins': true,
        Poppins: [300, 400, 500, 600, 700, 800],
        download: true,
        inject: true
      }
    }],
    'shadcn-nuxt',
    'nuxt-lucide-icons',
    'nuxt-auth-utils'
  ],
  runtimeConfig: {
    public: {
        apiBaseUrl: process.env.API_BASE_URL
    }
  },
  shadcn: {
    prefix: '',
    componentDir: './components/ui'
  },
  tailwindcss: {
    configPath: '/tailwind.config.js',
    cssPath: '/assets/css/main.css',
  },
  build: {
    transpile: [
        'defu'
    ]
  }
})