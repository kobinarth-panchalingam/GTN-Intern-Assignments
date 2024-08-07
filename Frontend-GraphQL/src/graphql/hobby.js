import { gql } from "@apollo/client";

export const GET_HOBBIES = gql`
  query GetHobbiesByUserId($userId: ID!) {
    getHobbiesByUserId(userId: $userId) {
      id
      hobbyName
    }
  }
`;
