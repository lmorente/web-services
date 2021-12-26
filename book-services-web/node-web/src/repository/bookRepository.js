const books = new Map();
let nextId = 1;

function addBooks(book) {
    let id = nextId++;
    book.id = id;
    books.set(book.id, book);
}

function getById(id) {
    return books.get(id);
}

function getAll() {
    return [...books.values()];
}

function saveNew(book) {
    let id = nextId++;
    book.id = id;
    books.set(id, book);
    return book;
}

function save(book) {
    books.set(book.id, book);
    return book;
}

function deleteById(id) {
    books.delete(id);
}

export default {addBooks, getAll, getById, saveNew, save, deleteById};