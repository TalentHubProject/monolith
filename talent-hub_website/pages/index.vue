<template>

    <Hero />
    <WhatIsTalentHub />
    <Poles />
    <Faq/>
    <JoinJourneyBanner />
</template>

<script setup lang="ts">
import WhatIsTalentHub from "~/components/Index/WhatIsTalentHub.vue";
import Faq from "~/components/Index/Faq.vue";
import {useFetch} from "nuxt/app";
import Hero from "~/components/Index/Hero.vue";
import Poles from "~/components/Index/Poles.vue";
import JoinJourneyBanner from "~/components/Index/JoinJourneyBanner.vue";


let isMouseOver = false;

</script>

<style scoped>
.red-fill {
    background: linear-gradient(to right, #df8f90, #e05880);
    color: #fff;
}

.container {
    margin: 0 auto;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.polygon {
    clip-path: polygon(0 28%, 100% 7%, 100% 79%, 0 100%);
    background: linear-gradient(
            90deg,
            rgba(193, 65, 104, 1) 13%,
            rgba(223, 143, 144, 1) 100%
    );
}

.Polos {
    background-color: rgb(241 245 249 / 39%);
    border: 1px solid #f1f5f94d;
}

@media screen and (max-width: 426px) {
    /* Apply your polygon shape to the element */
    .polygon {
        position: relative;
        clip-path: polygon(0 7%, 100% 0, 100% 92%, 0 100%);
        background: linear-gradient(
                to top,
                rgba(193, 65, 104, 1) 13%,
                rgba(223, 143, 144, 1) 100%
        );
    }
}
</style>
