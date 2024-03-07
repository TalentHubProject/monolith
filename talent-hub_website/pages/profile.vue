<template>
  <div class="flex flex-col items-center justify-center min-h-screen py-2">
    <div class="bg-white shadow-xl rounded-lg overflow-hidden">
      <div :style="{ backgroundColor: user.banner_color }" class="bg-cover bg-center h-32 w-full"></div>
      <div class="flex justify-center -mt-8">
        <img :src="user.avatar" alt="Avatar" class="h-24 w-24 bg-white p-2 rounded-full">
      </div>
      <div class="p-4">
        <p class="text-center text-gray-400">@{{ user.username }}</p>
        <div class="text-center mt-2">
          <button class="text-sm bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded-full">
            Follow
          </button>
        </div>
        <div class="mt-4 p-4 border-t border-gray-300">
          <div class="flex items-center">
            <p class="text-gray-600">Email: {{ user.email }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  middleware: 'auth'
})

const {user} = useUserSession()
</script>