<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<jsp:include page="piece/header.jsp" />

    <c:out value="${id}"/>
<div class="row">
    <div class="card" style="width: 100%">
        <h2>Upload image</h2>
    <form action="<c:url value='/photo-upload'/>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="idUser" value="<c:out value='${idUser}'/>" >
        <div class="custom-file">
            <input type="file"name="file" class="custom-file-input" id="customFile">
            <label class="custom-file-label"  for="customFile">Choose file</label>
        </div>
<%--            <input type="file" class="form-control"  />--%>
<%--            <input type="file" name="file">--%>

        <button type="submit" class="btn btn-primary" id="submit-button">Submit</button>

    </form>
    </div>
</div>

    <script>
        $(document).ready(function() {
            $('#submit-button').click(function() {
                if (!validateFile()) {
                    return false;
                }
                return true;
            });

            function validateFile() {
                if ($('input[name=file]').val() !== ""){
                    return true;
                }
                alert("Выбирите файл для отправки!");
                return false;
            }

        });
    </script>

<jsp:include page="piece/footer.jsp" />
