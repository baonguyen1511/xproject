import http from "../http-common";
const API_URL = "http://localhost:8080/api/rbaa/";
const getAll = () => {
  return http.get(API_URL + "tutorials");
};

const get = (id) => {
  return http.get(API_URL + `tutorials/${id}`);
};

const create = (data) => {
  return http.post(API_URL + `tutorials`, data);
};

const update = (id, data) => {
  return http.put(API_URL + `tutorials/${id}`, data);
};

const remove = (id) => {
  return http.delete(API_URL + `tutorials/${id}`);
};

const removeAll = () => {
  return http.delete(API_URL + `tutorials`);
};

const findByTitle = (title) => {
  return http.get(API_URL + `tutorials?title=${title}`);
};

export default {
  getAll,
  get,
  create,
  update,
  remove,
  removeAll,
  findByTitle,
};
