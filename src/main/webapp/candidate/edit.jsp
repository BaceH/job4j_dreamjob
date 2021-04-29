<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:include page="../piece/header.jsp" />

    <%
        String id = request.getParameter("id");
        Candidate candidate = new Candidate(0, "");
        if (id != null) {
            candidate = PsqlStore.instOf().findByIdCandidate(Integer.parseInt(id));
        }
    %>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>

            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name"  value="<%=candidate.getName()%>">
                    </div>
                    <div class="form-group">
                        <label>Город</label>
                        <select name="cityId" id="city-list" class="browser-default custom-select">
                            <option value="" selected>выберите город</option>
                        </select>

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
            if (!validateCity()) {
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
        function validateCity() {
            if ($('option:selected').val() !== ""){
                return true;
            }
            alert("Выбирите город!");
            return false;
        }

        getCity();
        function getCity() {

            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/dreamjob/city',
                data: "{text : 'cityList'}",
                dataType: 'json'
            }).done(function(data) {
                Object.entries(data).forEach(([key, value]) => {
                    let option;
                    if ('<%=candidate.getCityId()%>' === key) {
                        option = "<option value='" + key + "' selected>" + value + "</option>";
                    } else {
                        option = "<option value='" + key + "'>" + value + "</option>";
                    }
                    $('#city-list').find('option:last').after(option);
                });
                // $('#').before('<h1>' + data.textRes + '</h1>');
                // $('#exampleInputEmail1').val('');
            }).fail(function(err){
                alert("error \n" + err );
            });
        }

    });
</script>

<jsp:include page="../piece/footer.jsp" />