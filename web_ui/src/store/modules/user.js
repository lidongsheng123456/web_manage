import axios from 'axios'
import { defineStore } from 'pinia'
import { ref } from 'vue'

const silentRequest = axios.create({
    baseURL: import.meta.env.VUE_APP_BASEURL,
    timeout: 30000,
})

export const useUserStore = defineStore('user', () => {
    const adminUserInfo = ref({})
    const frontUserInfo = ref({})

    async function fetchCurrentUser() {
        try {
            const response = await silentRequest.get('/admin/current')
            const res = response.data
            if (res.code === 200) {
                adminUserInfo.value = res.data
            }
            return res
        } catch {
            return null
        }
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
        adminUserInfo.value = {}
        frontUserInfo.value = {}
    }

    return { adminUserInfo, frontUserInfo, fetchCurrentUser, fetchCurrentFrontUserInfo, $reset }
})
