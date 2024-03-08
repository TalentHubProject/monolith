<template>
  <section class="min-h-screen flex justify-center align-middle">
    <div class="container mx-auto flex">
      <h1 class="text-4xl font-bold text-center">Missions</h1>
    </div>
  </section>
</template>

<script lang="ts" setup>
definePageMeta({
  middleware: 'auth'
})

const {user} = useUserSession()
</script>