package es.andaluces.ourCode.service.impl;

import es.andaluces.ourCode.exceptions.DeleteUserException;
import es.andaluces.ourCode.models.MailIDto;
import es.andaluces.ourCode.models.UserDto;
import es.andaluces.ourCode.persistences.entities.User;
import es.andaluces.ourCode.persistences.repositories.UserRepository;
import es.andaluces.ourCode.service.CommentService;
import es.andaluces.ourCode.service.UserService;
import es.andaluces.ourCode.transformer.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private CommentService commentService;

    @Override
    public UserDto save(UserDto user) {
        if(this.repository.findByNick(user.getNick()).isPresent()){
            throw new EntityExistsException();
        }
        final User save = this.repository.save(this.mapper.toMO(user));
        return this.mapper.toDTO(save);
    }

    @Override
    public UserDto getUserByNick(String nick) {
        return this.mapper.toDTO(this.repository.findByNick(nick).orElseThrow());
    }

    @Override
    public UserDto modifyMail(String nick, MailIDto mail) {
        final User user = this.repository.findByNick(nick).orElseThrow();
        user.setEmail(mail.getEmail());
        return this.mapper.toDTO(this.repository.save(user));
    }

    @Override
    public void deleteUser(String nick) throws DeleteUserException {
        if(!this.commentService.getAllCommentsByUser(nick).isEmpty()){
            throw new DeleteUserException();
        }
        final User user = this.repository.findByNick(nick).orElseThrow();
        this.repository.delete(user);
    }
}
