import { buildSchema } from 'graphql';
import { getAllPlants, createPlant, deletePlant } from './controller/plantController.js';

//SCHEMA
export const schema = buildSchema(`
  type Query {
    plants: [Plant!]!
  }
  type Mutation {
    createPlant(city: String!): Plant
    deletePlant(id: ID!): Plant
  }
  type Plant {
    id: ID!
    city: String!
    planning: String!
  }`);

//ROOT: the root provides a resolver function for each API endpoint
export const root = {
    plants: getAllPlants,
    createPlant: createPlant,
    deletePlant: deletePlant,
};