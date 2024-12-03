import { createStore } from 'vuex';

const store = createStore({
    state: {
        user_id: 1,
        teqTermList: [], // 每一个元素：{teqTerm_id: , teqTerm_name: ''}
    },
    mutations: {
        setUserId(state, userID) {
            state.user_id = userID;
        },
        setTeqTermList(state, newTeqTermList) {
            state.teqTermList = newTeqTermList;
        },
        addTeqTerm(state, newTeqTerm) {
            state.teqTermList.push(newTeqTerm);
        }
    },
    actions: {
        setUserID({commit}, userID) {
            commit('setUserId', userID);
        },
        setTeqTermList({commit}, newTeqTermList) {
            commit('setTeqTermList', newTeqTermList);
        },
        addTeqTerm({commit}, newTeqTerm) {
            commit('addTeqTerm', newTeqTerm);
        }
    },
    getters: {
        teqTermList: (state) => state.teqTermList,
        userID: (state) => state.user_id
    },
});

export default store;
