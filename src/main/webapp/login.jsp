<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:include page="piece/header.jsp" />

    <a class="nav-link" href="<%=request.getContextPath()%>/reg.jsp">Регистрация</a>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Авторизация
            </div>
            <c:if test="${not empty error}">
                <div class="p-3 mb-2 bg-danger text-white">
                    <c:out value="${error}"/>
                </div>
            </c:if>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/auth.do" method="post">
                    <div class="form-group">
                        <label>Почта</label>
                        <input type="text" class="form-control" name="email" required>
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="password" class="form-control" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary" id="submit-button">Войти</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            $('#submit-button').click(function() {
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
                alert("Проверьте поле почта, почта введена некоректно.");
                return false;
            }
            function validatePassword() {
                if ($('input[name=password]').val().length > 1){
                    return true;
                }
                alert("Проверьте поле пароль, поле не должно быть пустым!");
                return false;

            }

        });
    </script>

<jsp:include page="piece/footer.jsp" />
