<template>
    <div class="relative">

        <DropdownMenu v-if="loggedIn">
            <DropdownMenuTrigger>
                <Avatar>
                    <AvatarImage :src="user.avatar" :alt="user.username" />
                    <AvatarFallback>{{user.username}}</AvatarFallback>
                </Avatar>
            </DropdownMenuTrigger>
            <DropdownMenuContent>
                <DropdownMenuLabel>
                    <span class="flex flex-col">
                        <span class="font-bold">{{user.username}}</span>
                        <span class="text-gray-400 font-thin">{{user.email}}</span>
                    </span>
                </DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem>
                    <NuxtLink to="/profile" class="flex items-center gap-x-2 text-gray-500 hover:text-gray-600">
                        <span>Profile</span>
                    </NuxtLink>
                </DropdownMenuItem>
                <DropdownMenuItem>
                    <button @click="clear" class="flex items-center gap-x-2 text-red-500 hover:text-red-600">
                        <span>Se déconnecter</span>
                    </button>
                </DropdownMenuItem>
            </DropdownMenuContent>
        </DropdownMenu>
        <a v-else
           class="flex items-center text-sm font-normal ml-2 hover:text-red-500 gap-x-2" href="api/auth/discord"
           @mouseout="handleMouseOut" @mouseover="handleMouseOver">
            <svg class="w-6 h-6" fill="none"
                 viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" @mouseout="handleMouseOut"
                 @mouseover="handleMouseOver">
                <g clip-path="url(#clip0)">
                    <path :stroke="strokeColor"
                          d="M2 12C2 6.5 6.5 2 12 2C13.5525 2 15.0836 2.36145 16.4721 3.05573C17.8607 3.75001 19.0685 4.75804 20 6"
                          stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor" d="M5 19.5C5.5 18 6 15 6 12C6 11.3 6.12 10.63 6.34 10"
                          stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor" d="M17.29 21.02C17.41 20.42 17.72 18.72 17.79 18" stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor"
                          d="M12 10C11.4696 10 10.9609 10.2107 10.5858 10.5858C10.2107 10.9609 9.99999 11.4696 9.99999 12C9.99999 13.02 9.89999 14.51 9.73999 16"
                          stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor" d="M8.65002 22C8.86002 21.34 9.10003 20.68 9.22003 20"
                          stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor" d="M14 13.1201C14 15.5001 14 19.5001 13 22.0001" stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor" d="M2 16H2.01" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor" d="M21.8 16C22 14 21.931 10.646 21.8 10" stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"/>
                    <path :stroke="strokeColor"
                          d="M9 6.79994C9.9124 6.27317 10.9474 5.99593 12.001 5.99609C13.0545 5.99626 14.0894 6.27384 15.0017 6.8009C15.9139 7.32797 16.6713 8.08594 17.1976 8.99859C17.7239 9.91124 18.0007 10.9464 18 11.9999C18 12.4699 18 13.1699 17.98 13.9999"
                          stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                </g>
                <defs>
                    <clipPath id="clip0">
                        <rect fill="white" height="24" width="24"/>
                    </clipPath>
                </defs>
            </svg>

            Se connecter
        </a>
    </div>
</template>

<script lang="ts" setup>
import {ref} from 'vue'
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'

const {loggedIn, user, session, clear, logout} = useUserSession()
const isMouseOver = ref(false)

const refElement = ref(null)

const handleMouseOver = () => isMouseOver.value = true
const handleMouseOut = () => isMouseOver.value = false
const strokeColor = computed(() => isMouseOver.value ? '#E05880' : 'black')
</script>
