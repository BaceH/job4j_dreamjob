<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:include page="piece/header.jsp" />

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Регистрация
            </div>
            <c:if test="${not empty error}">
                <div class="p-3 mb-2 bg-danger text-white">
                    <c:out value="${error}"/>
                </div>
            </c:if>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg.do" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" required>
                    </div>
                    <div class="form-group">
                        <label>Почта</label>
                        <input type="text" class="form-control" name="email"  required>
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="password" class="form-control" name="password" required>
                    </div>
                    <div class="form-group">
                        <label>Повторите пароль</label>
                        <input type="password" class="form-control" name="passwordagain" required>
                    </div>
                    <button type="submit" class="btn btn-primary" id="submit-button">Зарегистрироваться</button>
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
                if (!validateEmail()) {
                    return false;
                }
                if (!validatePassword()) {
                    return false;
                }
                return true;
            });

            function validateEmail () {

                if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test($('input[name=email]').val()))
                {
                    return true;
                }
                alert("Проверьте поле Почта, почта введена некоректно.");
                return false;
            }
            function validatePassword() {
                if ($('input[name=password]').val() === $('input[name=passwordagain]').val()){
                    return true;
                }
                alert("Пароли должны совпадать!");
                return false;
            }
            function validateName() {
                if ($('input[name=name]').val().length > 1){
                    return true;
                }
                alert("Проверьте поле Имя, поле не должно быть пустым!");
                return false;
            }
        });
    </script>

<jsp:include page="piece/footer.jsp" />