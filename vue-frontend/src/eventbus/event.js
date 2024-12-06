// eventBus.js
import { reactive, readonly } from 'vue';

const state = reactive(new Map());

const bus = {
    emit(event, payload) {
        state.get(event)?.forEach((callback) => callback(payload));
    },
    on(event, callback) {
        const callbacks = state.get(event) || [];
        callbacks.push(callback);
        state.set(event, callbacks);
    },
    off(event, callback) {
        const callbacks = state.get(event) || [];
        const index = callbacks.indexOf(callback);
        if (index > -1) {
            callbacks.splice(index, 1);
            state.set(event, callbacks);
        }
    },
};

export default bus;