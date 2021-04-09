package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
//        store.savePost(new Post(0, "Java Job 2"));
//        Post postEdit = store.findByIdPost(1);
//        postEdit.setName(postEdit.getName() + " edited");
//        store.savePost(postEdit);
//        for (Post post : store.findAllPosts()) {
//            System.out.println(post.getId() + " " + post.getName());
//        }
//
//        store.saveCandidate(new Candidate(0, "Java dev 1"));
//        store.saveCandidate(new Candidate(0, "Java dev 2"));
//        Candidate candidateEdit = store.findByIdCandidate(1);
//        candidateEdit.setName(candidateEdit.getName() + " edited");
//        store.saveCandidate(candidateEdit);
//        for (Candidate candidate : store.findAllCandidates()) {
//            System.out.println(candidate.getId() + " " + candidate.getName());
//        }

        store.saveUser(new User(0, "name", "email@local","1212121"));
        store.saveUser(new User(0, "2name", "2email@local","232323"));
        for (User u : store.findAllUser()){
            System.out.println(u.toString());
        }
        User user2 = store.findUserById(2);
        User user1 = store.findUserByEmail("email@local");
        user2.setEmail("emailemail@local");
        store.saveUser(user2);
        user1.setName("NNName");
        store.saveUser(user1);
        for (User u : store.findAllUser()){
            System.out.println(u.toString());
        }
    }
}