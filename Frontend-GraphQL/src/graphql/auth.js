import { gql } from "@apollo/client";

export const SIGNUP = gql`
  mutation SignUp($userInput: UserInput!) {
    signUp(userInput: $userInput) {
      id
      username
    }
  }
`;

export const SIGNIN = gql`
  mutation SignIn($username: String!, $password: String!) {
    signIn(username: $username, password: $password) {
      id
      username
    }
  }
`;
