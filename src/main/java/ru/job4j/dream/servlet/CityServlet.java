package ru.job4j.dream.servlet;

import org.json.JSONObject;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CityServlet extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/plain");
//        resp.setCharacterEncoding("UTF-8");
//
//        String email = req.getParameter("email");
//        System.out.println("Nice to meet you, " + email);
//        PrintWriter writer = new PrintWriter(resp.getOutputStream());
//        writer.println("Nice to meet you, " + email);
//        writer.flush();
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CityServlet");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<City> cityList = new ArrayList<>(PsqlStore.instOf().findAllCity());
        JSONObject cityJson = new JSONObject();

        for (City c : cityList) {
            cityJson.put(String.valueOf(c.getId()), c.getName());
        }

        PrintWriter writer = new PrintWriter(resp.getOutputStream());

        System.out.println(cityJson.toString());

        writer.println(cityJson.toString());
        writer.flush();
    }
}
