import {queryCurrentUser} from "@/api/request/UserRequest";

const state = {
    userInfo: {}
};

const mutations = {
    SET_USERINFO(state, userInfo) {
        state.userInfo = userInfo;
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
    }
};

const getters = {
    userInfo: state => state.userInfo
};

export default {
    namespaced: true,
    state,
    mutations,
    actions,
    getters
};
