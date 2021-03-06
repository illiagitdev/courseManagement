<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Courses</title>
    <style>
        <%@include file="/view/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<c:if test="${not empty courses}">
<table class="zui-table">
    <thead>
    <tr>
        <th>Title</th>
        <th>Course Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${courses}" var="course">
    <tr>
        <td>
            <c:out value="${course.title}"/>
        </td>
        <td>
            <c:out value="${course.courseStatus}"/>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/course/get?id=${course.id}#" class="button" role="button" tabindex="0">Show details</a>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</c:if>
</body>
</html>
