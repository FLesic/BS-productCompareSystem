import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
const store = createStore({
    state: {
        user_id: 1,
        productList: [],
        // 每个字段包含: id, name, price, platform, shop, photoURL, productURL, detail
        selectProduct: {
            id: '',
            name: '',
            price: 0.0,
            platform: '',
            shop: '',
            photoURL: '',
            productURL: '',
            detail: '',
        },
    },
    mutations: {
        setUserId(state, userID) {
            state.user_id = userID;
        },
        setProductList(state, newProductList) {
            state.productList = newProductList;
        },
        setSelectProduct(state, product) {
            state.selectProduct = product;
        },
        clearState(state) {
            state.user_id = 0;
            state.productList = [];
            state.selectProduct = {
                id: '',
                name: '',
                price: 0.0,
                platform: '',
                shop: '',
                photoURL: '',
                productURL: '',
                detail: '',
            };
        },
        clearProductList(state){
            state.productList = [];
        }
    },
    actions: {
        setUserID({commit}, userID) {
            commit('setUserId', userID);
        },
        setProductList({commit}, newProductList) {
            commit('setProductList', newProductList);
        },
        setSelectProduct({commit}, product) {
            commit('setSelectProduct', product);
        },
        clearState({commit}) {
            commit('clearState');
        },
        clearProductList({commit}){
            commit('clearProductList');
        }
    },
    getters: {
        productList: (state) => state.productList,
        userID: (state) => state.user_id,
        selectProduct: (state) => state.selectProduct,
    },
    plugins: [
        createPersistedState({
            // 配置选项
            key: 'my-app', // 存储数据时使用的键名，默认为 'vuex'
            storage: window.localStorage, // 存储位置，默认为 localStorage，也可以设置为 sessionStorage
            paths: ['productList', 'user_id', 'selectProduct'], // 指定需要持久化的 state 属性路径数组
        })
    ]
});

export default store;
