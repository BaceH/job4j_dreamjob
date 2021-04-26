package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void whenAddPostThenStoreIt() throws ServletException, IOException {
        MemStore memStore = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);

        Mockito.when(PsqlStore.instOf()).thenReturn(memStore);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn("4");
        when(req.getParameter("name")).thenReturn("New Java work!!!");

        new PostServlet().doPost(req, resp);
        assertThat(memStore.findByIdPost(4).getName(), is("New Java work!!!"));
    }

    @Test
    public void doGetTestResponse() throws IOException, ServletException {
        PostServlet postServlet = new PostServlet();

        MemStore memStore = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(memStore);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        session.setAttribute("user", new User(0, "test", "email@local", "test"));
        when(request.getSession()).thenReturn(session);

        ServletContext context = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(context);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("posts.jsp")).thenReturn(dispatcher);

        HttpServletResponse response = mock(HttpServletResponse.class);
        postServlet.doGet(request, response);

        verify(request).getRequestDispatcher("posts.jsp");
        verify(request).getSession();
        verify(dispatcher).forward(request, response);
    }
}