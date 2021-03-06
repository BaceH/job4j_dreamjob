package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post ORDER BY id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (SQLException e) {
            logger.error("'SELECT * FROM post' ended in error", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate ORDER BY id")
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    candidates.add(new Candidate(rs.getInt("id"), rs.getString("name"), rs.getInt("city_id")));
                }
            }
        } catch (SQLException e) {
            logger.error("'SELECT * FROM candidate' ended in error", e);
        }
        return candidates;
    }

    @Override
    public Collection<City> findAllCity() {
        List<City> city = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city ORDER BY id")
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    city.add(new City(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            logger.error("'SELECT * FROM city' ended in error", e);
        }
        return city;
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("'INSERT INTO post' ended in error", e);

        }
        return post;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE post SET name=(?) WHERE id=(?)")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();

        } catch (SQLException e) {
            logger.error("'UPDATE post' ended in error", e);
        }
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name, city_id) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.executeUpdate();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("'INSERT INTO candidate' ended in error", e);
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE candidate SET name=(?), city_id=(?) WHERE id=(?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setInt(3, candidate.getId());
            ps.execute();

        } catch (SQLException e) {
            logger.error("'UPDATE candidate' ended in error", e);
        }
    }

    @Override
    public Post findByIdPost(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id=(?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Post(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            logger.error("'SELECT * FROM post WHERE id=' ended in error", e);
        }
        return null;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id=(?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Candidate(rs.getInt("id"), rs.getString("name"), rs.getInt("city_id"));
                }
            }
        } catch (SQLException e) {
            logger.error("'SELECT * FROM candidate WHERE id=' ended in error", e);
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM userjob WHERE email=(?)")
        ) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("'SELECT * FROM userjob WHERE email=' ended in error", e);
        }
        return null;
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO userjob (name, email, password) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("'INSERT INTO userjob' ended in error", e);

        }
        return user;
    }

    private void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE userjob SET name=(?), email=(?), password=(?)  WHERE id=(?)")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (SQLException e) {
            logger.error("'UPDATE userjob SET' ended in error", e);
        }
    }

}