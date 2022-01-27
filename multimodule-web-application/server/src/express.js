import express from 'express';
import { __dirname } from './dirname.js';
import { graphql } from './graphql.js';

export const server = express();

server.use(express.json());

server.use('/graphql', graphql);

server.use(express.static(__dirname + '/../client'));

// viewed at http://localhost:3000
server.get('/', function(req, res) {
    res.sendFile(path.join(__dirname + '/../client', 'index.html'));
});