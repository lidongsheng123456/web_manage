import {ElMessage} from "element-plus";
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
                    if (res.code !== 200) {
                        ElMessage.error(res.msg);
                        reject(new Error(res.msg));
                        return
                    }
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
