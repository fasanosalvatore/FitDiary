import axios from "axios";
import config from "../config.json";


const urlLogin = `${config.SERVER_URL}/utenti/login`;

class AuthService {
    login(username, password) {
        const formData = new FormData();
        formData.append("email", username);
        formData.append("password", password);
        return axios({
            url: urlLogin,
            method: "POST",
            headers: {
                "Content-Type": "multipart/form-data"
            },
            data: formData
        })
            .then(response => {
                console.log(response);
                if (response.access_token) {
                    localStorage.setItem("user", JSON.stringify(response));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));

    }
}

export default new AuthService();