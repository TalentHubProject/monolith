<script lang="ts" setup>
const jobs = [
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 2, title: 'Designer UX/UI', company: 'Entreprise B', location: 'Lyon', salary: '40k - 50k', description: 'Nous sommes à la recherche d\'un designer UX/UI talentueux pour créer des interfaces utilisateur intuitives...',},
  {id: 3, title: 'Développeur Full-stack', company: 'Entreprise C', location: 'Toulouse', salary: '60k - 70k', description: 'Nous recherchons un développeur Full-stack pour rejoindre notre équipe et travailler sur des projets passionnants...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',}, {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
  {id: 1, title: 'Développeur Front-end', company: 'Entreprise A', location: 'Paris', salary: '50k - 60k', description: 'Nous recherchons un développeur Front-end expérimenté pour rejoindre notre équipe...',},
]

const visibleJobs = ref([]);
const loading = ref(false);
const allJobsLoaded = ref(false);
const jobsPerPage = 6;

function loadMoreJobs() {
  loading.value = true;
  const start = visibleJobs.value.length;
  const end = start + jobsPerPage;
  visibleJobs.value.push(...jobs.slice(start, end));
  loading.value = false;
  if (visibleJobs.value.length === jobs.length) {
    allJobsLoaded.value = true;
  }
}

onMounted(() => {
  loadMoreJobs();
});
</script>

<template>
  <section class="min-h-screen flex flex-col justify-center items-center">
    <header class="mb-12">
      <h1 class="text-4xl font-bold mb-8 text-center text-gray-800">Missions</h1>
      <p class="text-gray-700 text-center mb-8">
        Il est temps de trouver votre prochaine mission. Découvrez les offres d'emploi ci-dessous et postulez dès
        aujourd'hui en contactant directement l'employeur.
      </p>
    </header>

    <div v-if="jobs.length === 0" class="text-gray-700 text-center">Il n'y a aucune offre pour le moment.</div>

    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
      <div
          v-for="job in visibleJobs"
          :key="job.id"
          class="bg-white shadow-md rounded-lg p-6 transition duration-300 hover:shadow-lg"
      >
        <h2 class="text-xl font-bold text-gray-800">{{ job.title }}</h2>
        <p class="text-gray-600 text-sm mb-4">{{ job.company }} - {{ job.location }}</p>
        <p class="text-gray-700 mb-4">{{ job.description }}</p>
        <p class="text-gray-700 font-bold">{{ job.salary }}</p>
      </div>
    </div>

    <div v-if="loading" class="mt-8">
      <div class="flex justify-center items-center">
        <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
      </div>
    </div>

    <div v-if="!loading && !allJobsLoaded" class="mt-8">
      <button
          class="bg-gray-800 text-white px-6 py-3 rounded-md hover:bg-gray-700 transition duration-300"
          @click="loadMoreJobs"
      >
        Charger plus d'offres
      </button>
    </div>
  </section>
</template>
