<%@ page import="com.example.seoulpublicwifi.dao.BookmarkGroupDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");

    BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
    int affected = bookmarkGroupDao.delete(Integer.parseInt(id));
%>

<script>
    <%
        String text = affected > 0 ? "북마크 그룹 데이터를 삭제하였습니다." : "북마크 그룹 데이터를 삭제하지 못했습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmark-group.jsp";
</script>
</body>
</html>
