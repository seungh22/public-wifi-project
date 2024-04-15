<%@ page import="com.example.seoulpublicwifi.dao.HistoryDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<%
    String id = request.getParameter("id");

    HistoryDao historyDao = new HistoryDao();
    int affected = historyDao.delete(Integer.valueOf(id));
%>

<script>
    <%
        String text = affected > 0 ? "위치 히스토리 데이터를 삭제하였습니다." : "위치 히스토리 데이터를 삭제하지 못했습니다.";
    %>
    alert("<%= text %>");
    location.href = "history.jsp";
</script>
</body>
</html>
