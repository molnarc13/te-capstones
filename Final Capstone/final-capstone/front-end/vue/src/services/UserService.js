import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:8080"
});

export default {
    updateUsername(id, username) {
        return http.put(`/username/${id}`, username);
    },
}