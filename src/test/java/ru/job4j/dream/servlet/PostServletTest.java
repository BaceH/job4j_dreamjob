package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}