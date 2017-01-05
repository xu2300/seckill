<%--
  Created by IntelliJ IDEA.
  User: junweixu
  Date: 16/12/27
  Time: PM9:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- introduce jstl -->
<%@include file="common/tag.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title>Seckill List Page</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
<!-- page display  -->
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>Seckill List</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Storage</th>
                    <th>Start time</th>
                    <th>End time</th>
                    <th>Created time</th>
                    <th>Detail Page</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sk" items="${list}">
                    <tr>
                        <td>${sk.name}</td>
                        <td>${sk.number}</td>
                        <td>
                            <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank">link</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


</body>
<!-- jQuery file -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!--  Bootstrap core, JavaScript file -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</html>