import http from "../http-common";

const API_URL = "http://localhost:8080/api/rbaa/";

class UserService {
  getPublicContent() {
    return http.get(API_URL + "all");
  }

  getUserBoard() {
    return http.get(API_URL + "user");
  }

  getModeratorBoard() {
    return http.get(API_URL + "mod");
  }

  getAdminBoard() {
    return http.get(API_URL + "admin");
  }
}

export default new UserService();
