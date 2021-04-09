package ru.job4j.dream.servlet;

import org.apache.log4j.Logger;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User userAut = PsqlStore.instOf().findUserByEmail(email);
        if (userAut != null && userAut.getPassword().equals(password)){
            logger.info("User " + email + " logged in");
            HttpSession sc = req.getSession();
            sc.setAttribute("user", userAut);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            logger.warn("Hacking attempt!!! User " + email );
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}