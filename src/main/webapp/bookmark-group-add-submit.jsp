<%@ page import="com.example.seoulpublicwifi.dao.BookmarkGroupDao" %>
<%@ page import="com.example.seoulpublicwifi.dto.BookmarkGroupDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    String seq = request.getParameter("seq");

    BookmarkGroupDto bookmarkGroupDto = new BookmarkGroupDto();
    bookmarkGroupDto.setName(name);
    bookmarkGroupDto.setSeq(Integer.parseInt(seq));

    BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
    int affected = bookmarkGroupDao.insert(bookmarkGroupDto);
%>

<script>
    <%
        String text = affected > 0 ? "북마크 그룹 데이터를 추가하였습니다." : "북마크 그룹 데이터를 추가하지 못했습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmark-group.jsp";
</script>
</body>
</html>
