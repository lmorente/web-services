import bookRepository from '.././repository/bookRepository.js';


function initBook() {
    bookRepository.addBooks({
        id: null,
        title: "Manolito Gafotas",
        summary: "La historia de un niño en carabanchel",
        author: "Perez Reverte",
        publisher: "Editorial Salamandra",
        published: 2018
    });
    bookRepository.addBooks({
        id: null,
        title: "Señor de los anillos",
        summary: "Tercera Edad del Sol de la Tierra Media",
        author: "J. R. R. Tolkie",
        publisher: "Tirant Lo Blanch",
        published: 1954
    });
}

function getAllBooks(){
    return bookRepository.getAll();
}

function getBook(id){
    return bookRepository.getById(id);
}

function saveBook(book){
    return bookRepository.saveNew(book);
}

function deleteBook(id){
    return bookRepository.deleteById(id);
}

function updateBook(id, book){
    book.id = id;
    return bookRepository.save(book)
}


export default {initBook, getAllBooks, getBook, saveBook, deleteBook, updateBook}
