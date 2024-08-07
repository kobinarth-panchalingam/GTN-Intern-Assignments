import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope, faUnlockAlt } from "@fortawesome/free-solid-svg-icons";
import { Col, Row, Form, Card, Button, Container, InputGroup, Spinner } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { Formik } from "formik";
import * as Yup from "yup";
import { toast } from "react-toastify";
import { useMutation } from "@apollo/client";
import { SIGNIN } from "../graphql/auth";

export default function SignIn() {
  const navigate = useNavigate();
  const [signIn, { loading }] = useMutation(SIGNIN);

  return (
    <main className="main">
      <section className="d-flex align-items-center my-5 mt-lg-6 mb-lg-5">
        <Container>
          <Row className="justify-content-center form-bg-image">
            <Col xs={12} md={6} lg={12} className="d-flex align-items-center justify-content-center">
              <div className="bg-white shadow-lg border rounded border-light p-4 p-lg-5 w-100">
                <div className="text-center text-md-center mb-4 mt-md-0">
                  <h3 className="mb-0">Sign in to our platform</h3>
                </div>
                <Formik
                  initialValues={{ email: "", password: "" }}
                  validationSchema={Yup.object({
                    email: Yup.string().email("Invalid email address").required("Email Required"),
                    password: Yup.string().required("Password Required"),
                  })}
                  onSubmit={(values) => {
                    signIn({
                      variables: {
                        username: values.email,
                        password: values.password,
                      },
                    })
                      .then((res) => {
                        toast.success("Login success");
                        localStorage.setItem("user", JSON.stringify(res.data.signIn));
                        navigate("/home");
                      })
                      .catch((err) => {
                        toast.error(err.message);
                      });
                  }}
                >
                  {({ handleSubmit, handleChange, touched, errors }) => (
                    <Form onSubmit={handleSubmit} className="mt-4">
                      <Form.Group id="email" className="mb-4">
                        <Form.Label>Your Email</Form.Label>
                        <InputGroup>
                          <InputGroup.Text>
                            <FontAwesomeIcon icon={faEnvelope} />
                          </InputGroup.Text>
                          <Form.Control autoFocus required type="email" id="email" placeholder="example@company.com" onChange={handleChange} />
                        </InputGroup>
                        {touched.email && errors.email && <div className="text-danger">{errors.email}</div>}
                      </Form.Group>
                      <Form.Group>
                        <Form.Group id="password" className="mb-4">
                          <Form.Label>Your Password</Form.Label>
                          <InputGroup>
                            <InputGroup.Text>
                              <FontAwesomeIcon icon={faUnlockAlt} />
                            </InputGroup.Text>
                            <Form.Control required type="password" id="password" placeholder="Password" onChange={handleChange} />
                          </InputGroup>
                          {touched.password && errors.password && <div className="text-danger">{errors.password}</div>}
                        </Form.Group>
                        <div className="d-flex justify-content-between align-items-center mb-4"></div>
                      </Form.Group>
                      {loading ? ( // Conditionally render loading icon or submit button
                        <Button variant="primary" type="submit" className="button w-100" disabled>
                          <Spinner animation="border" size="sm" /> Loading...
                        </Button>
                      ) : (
                        <Button variant="primary" type="submit" className="button w-100">
                          Sign in
                        </Button>
                      )}
                    </Form>
                  )}
                </Formik>

                <div className="d-flex justify-content-center align-items-center mt-4">
                  <span className="fw-normal">
                    Not registered?
                    <Card.Link as={Link} to={"/signup"} className="fw-bold">
                      Create account
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
