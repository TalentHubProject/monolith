<template>
    <Collapsible
            class="space-y-2 text-center"
    >
        <div class="flex items-center justify-between space-x-4 px-4">
            <CollapsibleTrigger as-child>
                <Button class="flex items-center justify-between w-full space-x-2">
                    <h4 class="text-2xl font-bold mr-2 text-left">
                        {{ props.title }}
                    </h4>
                    <LucideChevronsUpDown class="h-4 w-4"/>
                    <span class="sr-only">Toggle</span>
                </Button>
            </CollapsibleTrigger>
        </div>
        <CollapsibleContent class="space-y-2">
            <p class="text-sm px-4 py-2 text-gray-400 text-justify">
                {{ props.content }}
            </p>
        </CollapsibleContent>
    </Collapsible>
</template>

<script lang="ts" setup>
import {defineProps} from 'vue'

const props = defineProps({
    title: String,
    content: String,
})

</script>