package es.andaluces.ourCode.persistences.repositories;

import es.andaluces.ourCode.persistences.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment as c JOIN User as u ON c.user = u.nick WHERE u.nick=:nick")
    Collection<Comment> findAllByNick(String nick);

    @Query("SELECT c FROM Comment as c JOIN Book as b ON c.book = b WHERE c.id=:idComment AND b.id=:idBook")
    Optional<Comment> findByIdAndIdBook(Long idComment, Long idBook);
}
