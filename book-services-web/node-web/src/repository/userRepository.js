const users = new Map();

function create(user) {
    if(!users.get(user.nick)) {
        users.set(user.nick, user);
        return user;
    }
    return null;
}

function getByNick(nick) {
    return users.get(nick);
}

function update(user) {
    users.set(user.nick, user);
    return user;
}
export default {create, getByNick, update}