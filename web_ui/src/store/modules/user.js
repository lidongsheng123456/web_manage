import {queryCurrentUser} from "@/api/admin_request/WebRequest";
import axios from "axios";

const uploadUrl = process.env.VUE_APP_BASEURL

const state = {
    userInfo: {},
    frontUserInfo: {}
};

const mutations = {
    SET_USERINFO(state, userInfo) {
        state.userInfo = userInfo;
    },
    SET_FRONT_USERINFO(state, userInfo) {
        state.frontUserInfo = userInfo;
    }
};

const actions = {
    queryCurrentUser({commit}) {
        return new Promise((resolve, reject) => {
            queryCurrentUser()
                .then(res => {
                    commit("SET_USERINFO", res.data);
                    resolve(res);
                })
                .catch(error => {
                    console.log(error);
                    reject(error);
                });
        });
    },
    queryCurrentFrontUserInfo({commit}) {
        return new Promise((resolve, reject) => {
            const request = axios.create({
                withCredentials: true
            })
            request.get(uploadUrl + '/user/current')
                .then(res => {
                    if (res.data.code === 200) {
                        commit("SET_FRONT_USERINFO", res.data.data);
                        resolve(res);
                    }
                }).catch(error => {
                    console.log(error);
                    reject(error);
                });
        });
    },
};

const getters = {
    userInfo: state => state.userInfo,
    frontUserInfo: state => state.frontUserInfo
};

export default {
    namespaced: true,
    state,
    mutations,
    actions,
    getters
};
