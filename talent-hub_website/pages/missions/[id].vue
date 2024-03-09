<template>
  <section class="min-h-screen flex flex-col justify-center items-center">
    <header class="mb-12">
      <h1 class="text-4xl font-bold mb-8 text-center text-gray-800">
        Missions id : {{ id }}
      </h1>
      <p class="text-gray-700 text-center mb-8">
        Missions via Id
      </p>
    </header>
  </section>
</template>

<script lang="ts" setup>
const route = useRoute()
const id = route.params.id
</script>