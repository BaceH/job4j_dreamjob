package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void savePost(Post post);

    void saveCandidate(Candidate post);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    Collection<User> findAllUser();
    void saveUser(User user);
    User findUserById(int id);
    User findUserByEmail(String email);
}
