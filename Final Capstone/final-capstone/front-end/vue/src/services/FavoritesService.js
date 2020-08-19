import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:8080"
});

export default {
    addFavorite(userId, photoId) {
        return http.post(`/favorites/${userId}/add`, { userId, photoId });
    },
    deleteFavorite(userId, photoId) {
        return http.delete(`/favorites/${userId}/delete`, { data: { userId, photoId } }); // tell axios to allow us to pass body data
    },
    getFavorites(userId) {
        return http.get(`/favorites/${userId}`)
    }
}