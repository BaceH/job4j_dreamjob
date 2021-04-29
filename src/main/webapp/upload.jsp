<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<jsp:include page="piece/header.jsp" />

<div class="row">
    <table class="table">
        <thead>
        <tr>
            <th>URL</th>
            <th>View</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${images}" var="image" varStatus="status">
            <tr valign="top">
                <td><a href="<c:url value='/download?name=${image}'/>">Download</a></td>
                <td>
                    <img src="<c:url value='/download?name=${image}'/>" width="100px" height="100px"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h2>Upload image</h2>
    <form action="<c:url value='/upload'/>" method="post" enctype="multipart/form-data">
        <div class="checkbox">
            <input type="file" name="file">
        </div>
        <button type="submit" class="btn btn-default" id="submit-button">Submit</button>
    </form>
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