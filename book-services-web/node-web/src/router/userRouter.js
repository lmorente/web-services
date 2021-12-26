import express from 'express';
import url from 'url';
import userService from '../service/userService.js';

function isValid(user) {
    return typeof user.nick == 'string'
        && typeof user.mail == 'string';
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

router.post('/users', (req, res) => {
    if (!isValid(req.body)) {
        res.sendStatus(400);
    } else {
        //TODO:
        const user = {
            nick: req.body.nick,
            mail: req.body.mail
        };
        const save = userService.createUser(user);
        if(save) {
            res.location(fullUrl(req) + save.id);
            res.json(save);
        }
        res.json("This user already exists");
        res.sendStatus(400)
    }
});

router.get('/users/:nick', (req, res) => {
    const nick = req.params.nick;
    const user = userService.getUser(nick);
    if (!user) {
        res.sendStatus(404);
    } else {
        res.json(user);
    }
});

router.put('/users/:nick', (req, res) => {
    const nick = req.params.nick;
    if (!isValid(req.body)) {
        res.sendStatus(400);
    } else {
        const user = userService.getUser(nick);
        if (!user) {
            res.sendStatus(404);
        } else {
            //TODO:
            const user = {
                nick: req.body.nick,
                mail: req.body.mail
            };
            if(user.nick != nick){
                res.sendStatus(403);
            } else {
                const update = userService.updateUser(user);
                res.json(update);
            }
        }
    }
});

//TODO: validando que no tenga comentarios antes para poder borrar
/*router.delete('/books/:id', (req, res) => {
    const id = req.params.id;
    const book = bookService.getBook(id);
    if (!book) {
        res.sendStatus(404);
    } else {
        bookService.deleteBook(id);
        res.sendStatus(200);
    }
});*/

//TODO: listado de comentarios por usuario (cada comentario con el id del libro)

export default router;


