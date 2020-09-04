import React, { useState, useEffect } from "react";
import userService from "../services/user.service";
import { Table } from "react-bootstrap";

const UserList = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    retrieveUsers();
  }, []);

  const retrieveUsers = () => {
    userService
      .getUsers()
      .then((response) => {
        setUsers(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  return (
    <Table striped borderless hover responsive>
      <thead>
        <tr>
          <th>#</th>
          <th>USERNAME</th>
          <th>EMAIL</th>
          <th>CREATED_ON</th>
          <th>UPDATED_ON</th>
          <th>ROLES</th>
        </tr>
      </thead>
      <tbody>
        {users &&
          users.map((user, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{new Date(user.created_on).toUTCString()}</td>
              <td>{new Date(user.updated_on).toUTCString()}</td>
              <td>
                <code>
                  <ul className="list-unstyled">
                    {user.roles.map((role, index) => (
                      <li key={role.id}>{role.name}</li>
                    ))}
                  </ul>
                </code>
              </td>
            </tr>
          ))}
      </tbody>
    </Table>
  );
};

export default UserList;
