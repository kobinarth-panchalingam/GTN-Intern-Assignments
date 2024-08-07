import React, { useState, useEffect } from "react";
import { Table, Form, Button } from "react-bootstrap";
import { Formik, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import axios from "./../utils/axios";

const Home = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  const [hobbies, setHobbies] = useState([]);

  useEffect(() => {
    // Fetch hobbies when the component mounts
    fetchHobbies();
  }, []);

  const fetchHobbies = () => {
    axios
      .get(`api/hobby/${user.id}`)
      .then((response) => {
        setHobbies(response.data);
      })
      .catch((error) => {
        console.error("Error fetching hobbies:", error);
      });
  };

  const handleAddHobby = (values, { resetForm }) => {
    axios
      .post("/api/hobby/add", { hobbyName: values.newHobby, user: { id: user.id } })
      .then(() => {
        resetForm();
        fetchHobbies(); // Fetch updated list of hobbies after adding a new one
      })
      .catch((error) => {
        console.error("Error adding hobby:", error);
      });
  };

  const handleDeleteHobby = (id) => {
    axios
      .delete(`/api/hobby/delete/${id}`)
      .then(() => {
        fetchHobbies(); // Fetch updated list of hobbies after deleting one
      })
      .catch((error) => {
        console.error("Error deleting hobby:", error);
      });
  };

  return (
    <div>
      <Formik
        initialValues={{ newHobby: "" }}
        validationSchema={Yup.object({
          newHobby: Yup.string().required("Required"),
        })}
        onSubmit={handleAddHobby}
      >
        {(formik) => (
          <Form onSubmit={formik.handleSubmit}>
            <Form.Group controlId="newHobby">
              <Form.Label>Add New Hobby</Form.Label>
              <Field
                type="text"
                name="newHobby"
                placeholder="Enter hobby name"
                as={Form.Control}
                isInvalid={formik.touched.newHobby && formik.errors.newHobby}
              />
              <ErrorMessage name="newHobby" component="div" className="text-danger" />
            </Form.Group>
            <Button variant="primary" type="submit">
              Add Hobby
            </Button>
          </Form>
        )}
      </Formik>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Hobby Name</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {hobbies.map((hobby) => (
            <tr key={hobby.id}>
              <td>{hobby.id}</td>
              <td>{hobby.hobbyName}</td>
              <td>
                <Button variant="danger" onClick={() => handleDeleteHobby(hobby.id)}>
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default Home;
