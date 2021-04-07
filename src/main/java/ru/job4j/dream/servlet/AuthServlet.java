package ru.job4j.dream.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if ("root@local".equals(email) && "root".equals(password)) {
            logger.info("User " + email + " logged in");
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            logger.warn("Hacking attempt!!! User " + email );
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}