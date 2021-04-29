<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:include page="piece/header.jsp" />

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Кандидаты
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col" id="wertty">ID</th>
                        <th scope="col">Имя</th>
                        <th scope="col">Город</th>
                        <th>Редактировать</th>
                        <th>Фото</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${candidates}" var="can">
                        <tr>
                            <td><c:out value="${can.id}"/></td>
                            <td><c:out value="${can.name}"/></td>
                            <td class="city_<c:out value='${can.cityId}'/>">city</td>
                            <td>
                                <a href='<c:url value="/candidate/edit.jsp?id=${can.id}"/>'>
                                    <i class="fa fa-edit mr-3"></i>
                                </a>
                            </td>
                            <td>
                                <img src="<c:url value='/download?name=${can.id}'/>" width="100px" height="100px"/>
                                <BR/>
                                <a href="<c:url value='/photo-upload?id=${can.id}'/>" class="btn btn-primary btn-lg " role="button" aria-disabled="true">добавить</a>
                                <a href="<c:url value='/photo-delete?id=${can.id}'/>" class="btn btn-secondary btn-lg" role="button" aria-disabled="true">удалить</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<script>
    $(document).ready(function() {
        getCity();
        function getCity() {

            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/dreamjob/city',
                data: "{text : 'cityList'}",
                dataType: 'json'
            }).done(function(data) {
                Object.entries(data).forEach(([key, value]) => {
                    $('.city_' + key).each(function () { $(this).text(value) });
                });
            }).fail(function(err){
                alert("error \n" + err );
            });
        }

    });
</script>

<jsp:include page="piece/footer.jsp" />