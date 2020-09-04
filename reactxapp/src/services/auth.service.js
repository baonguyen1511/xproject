import http from "../http-common";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  async login(username, password) {
    const response = await http
      .post(API_URL + "signin", {
        username,
        password,
      });
    if (response.data.accessToken) {
      localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(username, email, password) {
    return http.post(API_URL + "signup", {
      username,
      email,
      password,
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }
}

export default new AuthService();
