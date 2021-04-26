package ru.job4j.dream.servlet;

import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class PhotoDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        for (File name : new File("C:\\projects\\images\\").listFiles()) {
            if (id.equals(FilenameUtils.removeExtension(name.getName()))) {
                name.delete();
            }

        }

        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}