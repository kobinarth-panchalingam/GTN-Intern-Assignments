import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope, faUnlockAlt } from "@fortawesome/free-solid-svg-icons";
import { Col, Row, Form, Card, Button, FormCheck, Container, InputGroup } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { Formik } from "formik";
import * as Yup from "yup";
import axios from "./../utils/axios";
import { toast, ToastContainer } from "react-toastify";

export default function SignUp() {
  const navigate = useNavigate();
  return (
    <main className="main">
      <section className="d-flex align-items-center my-5 mt-lg-6 mb-lg-5">
        <Container>
          <Row className="justify-content-center form-bg-image">
            <Col xs={12} md={12} lg={12} className="d-flex align-items-center justify-content-center">
              <div className="bg-white shadow-lg border rounded border-light p-4 p-lg-5 w-100">
                <div className="text-center text-md-center mb-4 mt-md-0">
                  <h3 className="mb-0">Create an account</h3>
                </div>
                <Formik
                  initialValues={{
                    firstName: "",
                    lastName: "",
                    email: "",
                    password: "",
                    confirmPassword: "",
                  }}
                  validationSchema={Yup.object({
                    firstName: Yup.string().required("Required"),
                    lastName: Yup.string().required("Required"),
                    email: Yup.string().email("Invalid email address").required("Required"),
                    password: Yup.string().min(8, "Must be 8 characters or more").required("Required"),
                    confirmPassword: Yup.string()
                      .oneOf([Yup.ref("password"), null], "Passwords must match")
                      .required("Required"),
                  })}
                  onSubmit={(values, { resetForm }) => {
                    axios
                      .post("/api/auth/signup", {
                        firstName: values.firstName,
                        lastName: values.lastName,
                        username: values.email,
                        password: values.password,
                      })
                      .then((response) => {
                        console.log(response);
                        if (response.status === 201) {
                          toast.success("Signup successful");
                          navigate("/");
                        } else {
                          toast.error("Invalid signup");
                        }
                      })
                      .catch((error) => {
                        console.log(error.response);
                        toast.error(error.response.data);
                      });
                  }}
                >
                  {({ handleSubmit, handleChange, errors, touched }) => (
                    <Form className="mt-4" onSubmit={handleSubmit}>
                      <Row>
                        <Col>
                          <Form.Group id="firstName" className="mb-4">
                            <Form.Label>Your First Name</Form.Label>
                            <InputGroup>
                              <Form.Control autoFocus required type="text" id="firstName" placeholder="First Name" onChange={handleChange} />
                            </InputGroup>
                          </Form.Group>
                        </Col>
                        <Col>
                          <Form.Group id="lastName" className="mb-4">
                            <Form.Label>Your Last Name</Form.Label>
                            <InputGroup>
                              <Form.Control required type="text" id="lastName" placeholder="Last Name" onChange={handleChange} />
                            </InputGroup>
                          </Form.Group>
                        </Col>
                      </Row>
                      <Form.Group id="email" className="mb-4">
                        <Form.Label>Your Email</Form.Label>
                        <InputGroup>
                          <InputGroup.Text>
                            <FontAwesomeIcon icon={faEnvelope} />
                          </InputGroup.Text>
                          <Form.Control autoFocus required type="email" id="email" placeholder="example@company.com" onChange={handleChange} />
                        </InputGroup>
                      </Form.Group>
                      <Form.Group id="password" className="mb-4">
                        <Form.Label>Your Password</Form.Label>
                        <InputGroup>
                          <InputGroup.Text>
                            <FontAwesomeIcon icon={faUnlockAlt} />
                          </InputGroup.Text>
                          <Form.Control required type="password" placeholder="Password" id="password" onChange={handleChange} />
                          {touched.password && errors.password && <div className="text-danger col-12">{errors.password}</div>}
                        </InputGroup>
                      </Form.Group>
                      <Form.Group id="confirmPassword" className="mb-4">
                        <Form.Label>Confirm Password</Form.Label>
                        <InputGroup>
                          <InputGroup.Text>
                            <FontAwesomeIcon icon={faUnlockAlt} />
                          </InputGroup.Text>
                          <Form.Control required type="password" placeholder="Confirm Password" id="confirmPassword" onChange={handleChange} />
                          {touched.confirmPassword && errors.confirmPassword && <div className="text-danger col-12  ">{errors.confirmPassword}</div>}
                        </InputGroup>
                      </Form.Group>
                      <FormCheck type="checkbox" className="d-flex mb-4">
                        <FormCheck.Input required id="terms" className="me-2" />
                        <FormCheck.Label htmlFor="terms">
                          I agree to the <Card.Link>terms and conditions</Card.Link>
                        </FormCheck.Label>
                      </FormCheck>

                      <Button variant="primary" type="submit" className="button w-100">
                        Sign up
                      </Button>
                    </Form>
                  )}
                </Formik>

                <div className="mt-3 mb-4 text-center">
                  <span className="fw-normal">or</span>
                </div>

                <div className="d-flex justify-content-center align-items-center mt-4">
                  <span className="fw-normal">
                    Already have an account?
                    <Card.Link as={Link} to={"/"} className="fw-bold">
                      &nbsp; Sign In here
                    </Card.Link>
                  </span>
                </div>
              </div>
            </Col>
          </Row>
        </Container>
      </section>
    </main>
  );
}
