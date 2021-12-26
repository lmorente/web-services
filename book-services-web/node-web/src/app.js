import express from 'express';
import bookRouter from './router/bookRouter.js';
import userRouter from './router/userRouter.js';

const app = express();

//Convert json bodies to JavaScript object
app.use(express.json());

app.use(bookRouter);
app.use(userRouter);

app.listen(3001, () => {
    console.log('book-api-rest app listening on port 3000!');
});
