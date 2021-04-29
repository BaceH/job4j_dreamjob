<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:include page="piece/header.jsp" />

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Вакансии
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th scope="col">Названия</th>
                        <th>Редактировать</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${posts}" var="post">
                    <tr>
                        <td><c:out value="${post.id}"/></td>
                        <td>
                            <c:out value="${post.name}"/>
                        </td>
                        <td>
                            <a href='<c:url value="/post/edit.jsp?id=${post.id}"/>'>
                                <i class="fa fa-edit mr-3"></i>
                            </a>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<jsp:include page="piece/footer.jsp" />