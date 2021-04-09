package ru.job4j.dream.servlet;

import org.apache.log4j.Logger;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!req.getParameter("password").equals(req.getParameter("passwordagain"))){
            logger.warn("Парали должни совпадать!" );
            req.setAttribute("error", "Парали должни совпадать!");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
        User userAut = PsqlStore.instOf().findUserByEmail(req.getParameter("email"));
        if (userAut == null) {
            PsqlStore.instOf().saveUser(
                    new User(
                            0,
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("password")
                    )
            );
        } else {
            req.setAttribute("error", "Пользователь с таким email зарегистрирован.");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }




        logger.info("Зарегистрирован новый пользователь: " + req.getParameter("email"));
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}