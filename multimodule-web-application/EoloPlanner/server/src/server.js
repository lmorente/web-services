import express from 'express';
import cors from 'cors';
import { graphqlHTTP } from 'express-graphql';
import { schema, root } from './graphqlConfiguration.js';
import { __dirname } from './dirname.js'
import {loadSync} from "@grpc/proto-loader";

//EXPRESS SERVER
const app = express();
app.use(cors());
app.use(express.static(__dirname + "/client"));
app.use('/graphql', graphqlHTTP({
    schema: schema,
    rootValue: root,
    graphiql: true,
}));

//URL-PORT
app.listen(3000, ()=>{
    console.log('Running GraphQL API server at http://localhost:3000/graphql');
});