<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:include page="../piece/header.jsp" />

<%
    String id = request.getParameter("id");
    Post post = new Post(0, "");
    if (id != null) {
        post = PsqlStore.instOf().findByIdPost(Integer.parseInt(id));
    }
%>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                    Новая вакансия.
                <% } else { %>
                    Редактирование вакансии.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/posts.do?id=<%=post.getId()%>" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" value="<%=post.getName()%>">
                    </div>
                    <button type="submit" class="btn btn-primary" id="submit-button">Сохранить</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            $('#submit-button').click(function() {
                if (!validateName()) {
                    return false;
                }
                return true;
            });

            function validateName() {
                if ($('input[name=name]').val() !== ""){
                    return true;
                }
                alert("Введите коректное имя!");
                return false;
            }

        });
    </script>

<jsp:include page="../piece/footer.jsp" />