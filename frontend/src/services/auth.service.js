import axios from "axios";

const urlLogin = process.env.REACT_APP_SERVER_URL + '/utenti/login';
const urlProfile = process.env.REACT_APP_SERVER_URL + '/utenti/profilo';

/*
* example:
* {"accessToken":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiQ2xpZW50ZSJdLCJpc3MiOiIvYXBpL3YxL3V0ZW50aS9sb2dpbiIsImV4cCI6MTY0MTQzMjg0OSwiZW1haWwiOiJjbGllbnRlQGZpdGRpYXJ5Lml0In0.At98wr7R9vKYPq6WWkOwnbn34ztGKkxJgPpT-F107To","expiresAt":1641432849842},"refreshToken":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiQ2xpZW50ZSJdLCJpc3MiOiIvYXBpL3YxL3V0ZW50aS9sb2dpbiIsImV4cCI6MTY0MTQzNDA0OSwiZW1haWwiOiJjbGllbnRlQGZpdGRpYXJ5Lml0In0.iiH3qi378-LSmu9nzwxveSiG_NfwAs29V7uqY4KKAXA","expiresAt":1641434049842},"userInfo":{"trainerId":5,"surname":"Melmosa","email":"cliente@fitdiary.it","name":"Tiziana","gender":"F","roles":["Cliente"]}}
* */
class AuthService {

    async login(username, password) {
        const formData = new FormData();
        formData.append("email", username);
        formData.append("password", password);

        return axios.post(urlLogin, formData, {
            headers: {"Content-Type": "multipart/form-data"},
        }).then(value => {
            localStorage.setItem('user', JSON.stringify(value.data.data));
        })
    }

    logout() {
        localStorage.removeItem("user");
    }

    async getProfile() {
        return axios.get(urlProfile, {
            headers: {"Authorization": "Bearer " + this.getAccessToken()}
        });
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

    getAccessToken() {
        return this.getCurrentUser()?.accessToken.token;
    }
}

export default new AuthService();