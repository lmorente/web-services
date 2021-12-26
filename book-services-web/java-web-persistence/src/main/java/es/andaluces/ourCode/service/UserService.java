package es.andaluces.ourCode.service;

import es.andaluces.ourCode.exceptions.DeleteUserException;
import es.andaluces.ourCode.models.MailIDto;
import es.andaluces.ourCode.models.UserDto;

public interface UserService {

    UserDto save(UserDto user);

    UserDto getUserByNick(String nick);

    UserDto modifyMail(String nick, MailIDto mail);

    void deleteUser(String nick) throws DeleteUserException;
}
