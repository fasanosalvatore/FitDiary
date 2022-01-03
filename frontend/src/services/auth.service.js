import axios from "axios";
import config from "../config.json";


const urlLogin = `${config.SERVER_URL}/utenti/login`;
const urlProfile = `${config.SERVER_URL}/utenti/profilo`;

class AuthService {
    async login(username, password) {
        const formData = new FormData();
        formData.append("email", username);
        formData.append("password", password);
        const respAuth = await axios({
            url: urlLogin,
            method: "POST",
            headers: {
                "Content-Type": "multipart/form-data"
            },
            data: formData
        })
        console.log(respAuth.data.access_token)
        const respUser = await axios({
            url: urlProfile,
            method: "GET",
            headers: {
                'Authorization': "Bearer "+respAuth.data.access_token,
            }
        })
        console.log(respUser)
        const completeUser ={
            utente: respUser.data.data.utente,
            access_token: respAuth.data.access_token,
            refresh_token: respAuth.data.refresh_token,
        }
        console.log(completeUser)
        localStorage.setItem('user', JSON.stringify(completeUser));
        return respAuth.data;
    }

    logout() {
        localStorage.removeItem("user");
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();