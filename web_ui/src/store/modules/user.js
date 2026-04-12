import { queryCurrentUser } from "@/api/admin_request/WebRequest"
import axios from 'axios'
import { defineStore } from 'pinia'
import { ref } from 'vue'

const silentRequest = axios.create({
    baseURL: import.meta.env.VUE_APP_BASEURL,
    timeout: 30000,
})

export const useUserStore = defineStore('user', () => {
    const userInfo = ref({})
    const frontUserInfo = ref({})

    async function fetchCurrentUser() {
        const res = await queryCurrentUser()
        userInfo.value = res.data
        return res
    }

    async function fetchCurrentFrontUserInfo() {
        try {
            const response = await silentRequest.get('/user/current')
            const res = response.data
            if (res.code === 200) {
                frontUserInfo.value = res.data
            }
            return res
        } catch {
            return null
        }
    }

    function $reset() {
        userInfo.value = {}
        frontUserInfo.value = {}
    }

    return { userInfo, frontUserInfo, fetchCurrentUser, fetchCurrentFrontUserInfo, $reset }
})
