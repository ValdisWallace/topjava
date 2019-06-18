<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        a {font-family:Arial, sans-serif;font-size:16px}
        .tg  {border-collapse:collapse;border-spacing:0;border-width:1px;border-style:solid;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#ccc;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#ccc;background-color:#f0f0f0;}
        .tg .tg-x5q1{font-size:16px;text-align:left;vertical-align:top}
        .tg .tg-uusf{font-weight:bold;font-size:16px;text-align:left}
        .tg .tg-vox4{font-weight:bold;font-size:16px;text-align:left;vertical-align:top}
        .tg .normal{color: green}
        .tg .excess{color: red}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=create">Add Meals</a>
<br><br>
<table class="tg" style="undefined;table-layout: fixed; width: 397px">
    <colgroup>
        <col style="width: 173px">
        <col style="width: 112px">
        <col style="width: 90px">
        <col style="width: 80px">
        <col style="width: 80px">
    </colgroup>
    <tr>
        <th class="tg-uusf"><span style="font-weight:700">Дата/Время</span></th>
        <th class="tg-vox4">Описание</th>
        <th class="tg-vox4">Калории</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo" />
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td class="tg-x5q1">
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" var="date" />
                    ${date}
            </td>
            <td class="tg-x5q1">${meal.description}</td>
            <td class="tg-x5q1">${meal.calories}</td>
            <td class="tg-x5q1"><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td class="tg-x5q1"><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
