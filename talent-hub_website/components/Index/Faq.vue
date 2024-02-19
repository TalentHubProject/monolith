<template>
    <section class="w-full">
        <div class="mt-32 sm:mt-16 pb-32 px-4 mx-auto max-w-screen-xl text-center lg:pt-20">
            <div class="flex items-center justify-center">
                <div class="bg-slate-100 px-4 py-1 rounded">
                    <img class="float-left pt-1 pr-2" src="~/assets/images/faq.png"/>
                    <p class="text-slate-700 float-left">FAQ</p>
                </div>
            </div>
            <h1 class="text-2xl sm:text-3xl pt-6 font-bold mx-12 text-black xl:px-64">
                Questions Fréquemment Demandées
            </h1>
            <div class="px-0 lg:px-16 pt-24 grid grid-cols-1 gap-12 md:grid-cols-2 md:gap-24 text-center">
                <FaqItem title="Qu'est-ce que Talent Hub ?" content="Talent Hub est une communauté sur Discord réunissant des individus talentueux dans des domaines variés comme l'art, le développement, la musique et l'investissement. Notre but est de fournir un espace pour la créativité, le partage d'idées et les projets collaboratifs." />
                <FaqItem title="Comment puis-je rejoindre Talent Hub ?" content="Pour rejoindre Talent Hub, vous devez cliquer sur le bouton Rejoindre l'aventure. Une fois que vous avez rejoint notre serveur Discord, vous pouvez vous présenter dans le salon #présentation et commencer à explorer les différents salons." />
                <FaqItem title="Les projets sur Talent Hub sont-ils open source ?" content="Oui, la majorité de nos projets sont open source et disponibles sur GitHub. Nous encourageons la collaboration et l'innovation ouverte." />
                <FaqItem title="Puis-je contribuer à un projet si je suis débutant ?" content="Absolument ! Talent Hub encourage la participation de membres de tous niveaux. C'est une excellente opportunité d'apprendre et de progresser dans votre domaine." />
                <FaqItem title="Comment fonctionne le système de pôles ?" content="Nos pôles organisent les communautés par domaine d'intérêt, permettant une collaboration et un échange d'idées fluides. Les membres peuvent naviguer entre les pôles pour explorer et collaborer." />
                <FaqItem title="Comment puis-je sponsoriser ou contribuer à Talent Hub ?" content="Pour sponsoriser ou contribuer, visitez notre page GitHub ou contactez-nous via notre site. Nous sommes ouverts à toutes formes de collaboration." />
            </div>
        </div>
    </section>
</template>

<script setup>

</script>
