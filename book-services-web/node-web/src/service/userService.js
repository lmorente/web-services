import userRepository from '.././repository/userRepository.js';


function getUser(nick){
    return userRepository.getByNick(nick);
}

function createUser(user){
    return userRepository.create(user);
}

function updateUser(user){
    return userRepository.update(user)
}

export default {getUser, createUser, updateUser}
