<template>
    <main class="flex flex-col">
        <Navbar/>
        <slot/>
        <SpeedInsights/>
        <Toaster />
        <Footer/>
    </main>
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
    },
    setup() {
        useSeoMeta({
            title: 'Talent Hub - Serveur Discord d\'entraide pour artistes, développeurs, musiciens et entrepreneurs',
            description: 'Rejoignez Talent Hub, la communauté Discord pour l\'entraide et la collaboration dans les domaines de l\'art, du développement, de la musique, de l\'investissement et de l\'audiovisuel. Trouvez des offres d\'emploi, des missions freelance et lancez vos projets avec d\'autres passionnés.',
            image: 'https://talent-hub.fr/assets/images/og.png',
            url: 'https://talent-hub.fr',
            author: 'Talent Hub',
            robots: 'index, follow',
            viewport: 'width=device-width, initial-scale=1',
            ogTitle: 'Talent Hub - Serveur Discord d\'entraide pour artistes, développeurs, musiciens et entrepreneurs',
            ogDescription: 'Rejoignez Talent Hub, la communauté Discord pour l\'entraide et la collaboration dans les domaines de l\'art, du développement, de la musique, de l\'investissement et de l\'audiovisuel. Trouvez des offres d\'emploi, des missions freelance et lancez vos projets avec d\'autres passionnés.',
            ogImage: 'https://talent-hub.fr/assets/images/og.png',
            ogUrl: 'https://talent-hub.fr',
            ogType: 'website',
            twitterTitle: 'Talent Hub - Serveur Discord d\'entraide pour artistes, développeurs, musiciens et entrepreneurs',
            twitterDescription: 'Rejoignez Talent Hub, la communauté Discord pour l\'entraide et la collaboration dans les domaines de l\'art, du développement, de la musique, de l\'investissement et de l\'audiovisuel. Trouvez des offres d\'emploi, des missions freelance et lancez vos projets avec d\'autres passionnés.',
            twitterImage: 'https://talent-hub.fr/assets/images/og.png',
            twitterCard: 'summary_large_image',
            keywords: "discord, communauté, entraide, artistes, développeurs, musiciens, entrepreneurs, investissement, audiovisuel, emploi, freelance, projets, passionnés, serveur discord, bot discord, discord web, pixel art, pixel art facile, art, street art, jobs"
        })
    }
};
</script>
