import express from 'express';
import url from 'url';
import bookService from '../service/bookService.js';

bookService.initBook();

function isValid(book) {
    return typeof book.title == 'string'
        && typeof book.summary == 'string';
}

function fullUrl(req) {
    const fullUrl = url.format({
        protocol: req.protocol,
        host: req.get('host'),
        pathname: req.originalUrl
    });

    return fullUrl + (fullUrl.endsWith('/') ? '' : '/');
}

const router = express.Router();

router.get('/books', (req, res) => {
    res.json(bookService.getAllBooks());
});

router.post('/books', (req, res) => {
    if (!isValid(req.body)) {
        res.sendStatus(400);
    } else {
        //TODO:
        const book = {
            id: null,
            title: req.body.title,
            summary: req.body.summary
        };
        const save = bookService.saveBook(book);
        res.location(fullUrl(req) + save.id);
        res.json(save);
    }
});

router.get('/books/:id', (req, res) => {
    const id = req.params.id;
    const book = bookService.getBook(id);
    if (!book) {
        res.sendStatus(404);
    } else {
        res.json(book);
    }
});

router.delete('/books/:id', (req, res) => {
    const id = req.params.id;
    const book = bookService.getBook(id);
    if (!book) {
        res.sendStatus(404);
    } else {
        bookService.deleteBook(id);
        res.sendStatus(200);
    }
});

router.put('/books/:id', (req, res) => {
    const id = req.params.id;
    if (!isValid(req.body)) {
        res.sendStatus(400);
    } else {
        const book = bookService.getBook(id);
        if (!book) {
            res.sendStatus(404);
        } else {
            //TODO:
            const book = {
                id: req.body.id,
                title: req.body.title,
                summary: req.body.summary,
                author: req.body.author,
                publisher: req.body.publisher,
                published: req.body.published
            };
            const update = bookService.updateBook(id, book);
            res.json(update);
        }
    }
});
export default router;


