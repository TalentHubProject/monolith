<template>
  <div>
    <nav class="w-full p-6 bg-white">
      <div class="flex items-center justify-between sm:justify-around">

        <div class="ml-0 lg:ml-24">
          <Logo/>
        </div>

        <div class="hidden md:block">
          <ul class="flex space-x-2 text-sm bg-slate-100 px-1 py-2 rounded">
            <li>
              <NuxtLink active-class="exactActiveClass" class="px-2 hoverClass" to="/">Accueil
              </NuxtLink>
            </li>
            <li>
              <NuxtLink active-class="exactActiveClass " class="px-2 hoverClass" to="/missions">Offres</NuxtLink>
            </li>
            <li>
              <NuxtLink active-class="exactActiveClass " class="px-2 hoverClass" to="/events">Événements (à venir)
              </NuxtLink>
            </li>
            <li>
              <NuxtLink active-class="exactActiveClass" class="px-2 hoverClass" to="/starboard">Starboard (à venir)
              </NuxtLink>
            </li>
          </ul>
        </div>
        <div class="hidden md:block mr-24">
          <ul>
            <li>
              <Connecter/>
            </li>
          </ul>
        </div>

        <div class="md:hidden">
          <button @click="drawer">
            <svg fill="none" height="9" viewBox="0 0 27 9" width="27" xmlns="http://www.w3.org/2000/svg">
              <rect fill="black" height="2" width="27"/>
              <rect fill="black" height="2" width="23" x="4" y="7"/>
            </svg>
          </button>
        </div>

        <aside
            :class="isOpen ? 'translate-x-0' : '-translate-x-full'"
            class="p-5 pt-0 transform top-0 left-0 w-screen bg-white fixed h-full overflow-auto ease-in-out transition-all duration-300 z-30">
          <div class="close">
            <button class="absolute top-0 right-0 mt-6 mr-4" @click="isOpen = false">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                   stroke-width="2" viewBox="0 0 24 24">
                <path d="M6 18L18 6M6 6l12 12"></path>
              </svg>
            </button>
          </div>

          <span class="flex w-full items-center justify-between p-4 pl-0 border-b" @click="isOpen = false">
    <Logo/>
    <Connecter/>
    <span class=""></span>
  </span>

          <ul class="">
            <li>
              <NuxtLink class="my-8 inline-block text-xl font-medium" to="/" @click="isOpen = false">Accueil</NuxtLink>
            </li>
            <li>
              <NuxtLink class="my-8 inline-block text-xl font-medium" to="/missions" @click="isOpen = false">Offres
              </NuxtLink>
            </li>
            <li>
              <NuxtLink class="my-8 inline-block text-xl font-medium" to="/events" @click="isOpen = false">Événements
              </NuxtLink>
            </li>
            <li>
              <NuxtLink class="my-8 inline-block text-xl font-medium" to="/starboard" @click="isOpen = false">
                Starboard
              </NuxtLink>
            </li>
          </ul>
          <div class="border-t"></div>
          <ul class="text-gray-500 dark:text-gray-400 font-medium mb-8">
            <li class="mb-8 sm:mb-4">
              <a class="hover:underline " href="https://www.instagram.com/talenthubfr/" target="_blank">Instagram</a>
            </li>
            <li class="mb-8 sm:mb-4">
              <a class="hover:underline" href="https://www.linkedin.com/company/93809895" target="_blank">LinkedIn</a>
            </li>
            <li>
              <a class="hover:underline" href="https://twitter.com/talenthubfr" target="_blank">Twitter</a>
            </li>
          </ul>
          <JoinButton text="Rejoindre l’aventure"/>
        </aside>

      </div>
    </nav>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isOpen: false,
    };
  },
  methods: {
    drawer() {
      this.isOpen = !this.isOpen;
    },
  },
  watch: {
    isOpen: {
      immediate: true,
      handler(isOpen) {
        if (process.client) {
          if (isOpen) document.body.style.setProperty("overflow", "hidden");
          else document.body.style.removeProperty("overflow");
        }
      },
    },
  },
  mounted() {
    document.addEventListener("keydown", (e) => {
      if (e.keyCode == 27 && this.isOpen) this.isOpen = false;
    });
  },
};
</script>
<style>
.exactActiveClass {
  font-weight: 500;
  background-color: #fff;
  padding-top: 0.25rem;
  padding-bottom: 0.25rem;
}

.hoverClass:hover {
  font-weight: 500;
  background-color: #fff;
  padding-top: 0.25rem;
  padding-bottom: 0.25rem;
}

.exactActiveClass:hover {
  font-weight: 500;
  background-color: #ffffff9c;
  padding-top: 0.25rem;
  padding-bottom: 0.25rem;
}
</style>
