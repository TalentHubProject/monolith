<script lang="ts" setup>
import {toast} from "vue-sonner";


const visibleJobs = ref([]);
const loading = ref(false);
const allJobsLoaded = ref(false);
const searchQuery = ref('');
const selectedDomain = ref('');

const config = useRuntimeConfig();
const apiBaseUrl = config.public.apiBaseUrl;

async function loadJobs() {
  loading.value = true;
  console.log("apiBaseUrl", apiBaseUrl)
  fetch(`${apiBaseUrl}/missions`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  }).then(response => response.json()).then(data => {
    console.log("data", data)
    visibleJobs.value = data;
    allJobsLoaded.value = true;
  }).catch(error => {
    console.error("no missions", error);
    toast.error('Une erreur est survenue lors du chargement des offres');
    visibleJobs.value = [];
  });

  loading.value = false;
}

function filterJobs() {
  visibleJobs.value = visibleJobs.value.filter(job => {
    return job.title.toLowerCase().includes(searchQuery.value.toLowerCase()) &&
        (selectedDomain.value === '' || job.domain === selectedDomain.value);
  });
}

watch([searchQuery, selectedDomain], filterJobs);
const {loggedIn, user} = useUserSession();

const offerName = ref('');
const offerDescription = ref('');
const offerBudget = ref(0);
const offerDomain = ref('dev');

async function handleCreateOffer() {

  if (!loggedIn.value) {
    toast.error('Vous devez être connecté pour créer une offre');
    return;
  }

  const offerData = {
    name: offerName.value,
    description: offerDescription.value,
    budget: offerBudget.value,
    deadline: "2024-06-30",
    status: "Ouvert",
    employerSnowflake: user.value.id,
    employerName: user.value.username,
    domain: offerDomain.value
  };

  try {
    const response = await fetch(`${apiBaseUrl}/missions`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(offerData)
    });

    if (response.ok) {
      toast.success('L\'offre a été créée avec succès');
      offerName.value = '';
      offerDescription.value = '';
      offerBudget.value = 0;
      offerDomain.value = 'dev';
      await loadJobs();
    } else {
      toast.error('Vous devez patienter 3h avant de créer une nouvelle offre');
    }
  } catch (error) {
    console.error("Error creating offer", error);
    toast.error('Une erreur est survenue lors de la création de l\'offre');
  }
}

onMounted(() => {
  loadJobs();
});

useSeoMeta({
  title: 'Talent Hub - Offres',
  description: 'Retrouvez toutes les offres disponibles sur Talent Hub',
});

</script>

<template>
  <section class="min-h-screen flex flex-col justify-center items-center px-4 md:px-8 lg:px-16 md:mt-12">
    <header class="mb-12 flex flex-col items-center gap-y-10 mb-12">
      <div class="px-4 py-1 rounded bg-gray-100">
        <LucideActivity class="float-left pt-1 pr-2"/>
        <p class="float-left tex-sm">Offres</p></div>
      <h1 class="text-5xl font-bold mb-8 text-center text-gray-800">Les offres du moment actuellement sur Talent
        Hub</h1>
      <div class="w-full flex flex-col gap-x-10 gap-y-5 md:flex-row md:gap-x-5 md:gap-y-0">
        <input
            v-model="searchQuery"
            class="w-full px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:border-gray-500 transition duration-300"
            placeholder="Rechercher une offre..."
            type="text"/>
        <select
            v-model="selectedDomain"
            class="w-full px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:border-gray-500 transition duration-300">
          <option value="">Tous les domaines</option>
          <option value="dev">Développement</option>
          <option value="art">Arts (Illustration, Logo, etc.)</option>
          <option value="audiovisual">Audiovisuels (Photographie, Montage, etc.)</option>
        </select>
      </div>
    </header>
    <div class="w-full flex justify-start md:justify-end my-5">
      <Drawer>

        <DrawerTrigger>
          <button
              class="bg-gray-800 text-white px-6 py-3 rounded-md hover:bg-gray-700 transition duration-300"
              :disabled="!loggedIn"
              >
            Créer une offre
          </button>
        </DrawerTrigger>
        <DrawerContent class="bg-white">
          <DrawerHeader>
            <DrawerTitle>Créer une offre</DrawerTitle>
            <DrawerDescription>Créez une offre pour trouver le talent qu'il vous faut</DrawerDescription>
          </DrawerHeader>
          <form class="p-6">
            <div class="mb-4"><label class="block text-gray-700 text-sm font-bold mb-2" for="name">Nom de
              l'offre</label> <input id="name" v-model="offerName" class="w-full px-3 py-2 border rounded-md border-gray-300 focus:outline-none focus:border-gray-500 transition duration-300"
                                     placeholder="Nom de l'offre"
                                     type="text"/></div>
            <div class="mb-4"><label class="block text-gray-700 text-sm font-bold mb-2"
                                     for="description">Description</label> <textarea
                id="description" v-model="offerDescription"
                class="w-full px-3 py-2 border rounded-md border-gray-300 focus:outline-none focus:border-gray-500 transition duration-300"
                placeholder="Description de l'offre"></textarea></div>
            <div class="mb-4"><label class="block text-gray-700 text-sm font-bold mb-2" for="budget">Budget</label>
              <input id="budget" v-model="offerBudget" class="w-full px-3 py-2 border rounded-md border-gray-300 focus:outline-none focus:border-gray-500 transition duration-300"
                     placeholder="Budget"
                     type="number"/></div>
            <div class="mb-4"><label class="block text-gray-700 text-sm font-bold mb-2" for="domain">Domaine</label>
              <select id="domain" v-model="offerDomain"
                      class="w-full px-3 py-2 border rounded-md border-gray-300 focus:outline-none focus:border-gray-500 transition duration-300">
                <option value="dev">Développement</option>
                <option value="art">Arts (Illustration, Logo, etc.)</option>
                <option value="audiovisual">Audiovisuels (Photographie, Montage, etc.)</option>
              </select></div>
          </form>
          <DrawerFooter>
            <Button @click="handleCreateOffer">Créer</Button>
            <DrawerClose>
              <Button variant="outline">
                Annuler
              </Button>
            </DrawerClose>
          </DrawerFooter>
        </DrawerContent>
      </Drawer>
    </div>

    <div v-if="loading" class="mt-8">
      <div class="flex justify-center items-center">
        <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
      </div>
    </div>

    <div v-else>
      <div v-if="visibleJobs.length === 0" class="text-gray-700 text-center">
        Il n'y a pas d'offres pour le moment
      </div>
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div
            v-for="job in visibleJobs"
            :key="job?.id"
            class="bg-white shadow-md rounded-lg p-6 transition duration-300 hover:shadow-lg"
        >
          <h2 class="text-xl font-bold text-gray-800">{{ job?.name }}</h2>
          <p class="text-gray-600 text-sm mb-4">{{ job?.employerName }}</p>
          <p class="text-gray-700 mb-4">{{ job?.description }}</p>
          <p class="text-gray-700 font-bold">{{ job?.budget }}</p>
        </div>
      </div>
    </div>
  </section>
</template>