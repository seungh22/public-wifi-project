<%@ page import="com.example.seoulpublicwifi.dao.HistoryDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.seoulpublicwifi.dto.HistoryDto" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>

    <style>
        #link-list {
            margin-bottom: 20px;
        }

        #table-list {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #table-list td, #table-list th {
            border: 1px solid #ddd;
            text-align: center;
            font-size: 14px;
            padding: 8px;
        }

        #table-list tr:nth-child(odd) {
            background-color: #f2f2f2;
        }

        #table-list tr:hover {
            background-color: #ddd;
        }

        #table-list th {
            padding-top: 12px;
            padding-bottom: 12px;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<h1>위치 히스토리 목록</h1>

<div id="link-list">
    <a href="index.jsp">홈</a>
    &#124;
    <a href="history.jsp">위치 히스토리 목록</a>
    &#124;
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
    &#124;
    <a href="bookmark-list.jsp">즐겨찾기 보기</a>
    &#124;
    <a href="bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<table id="table-list">
    <thead>
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <%
        HistoryDao historyDao = new HistoryDao();
        if (historyDao.count() == 0) {
    %>
    <tr>
        <td colspan="5">
            데이터가 존재하지 않습니다.
        </td>
    </tr>
    <%
    } else {
        List<HistoryDto> historyDtoList = historyDao.selectList();
        for (HistoryDto item : historyDtoList) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(item.getSrcDttm());
    %>
    <tr>
        <td>
            <%= item.getId() %>
        </td>
        <td>
            <%= item.getLat() %>
        </td>
        <td>
            <%= item.getLnt() %>
        </td>
        <td>
            <%= strDate %>
        </td>
        <td>
            <button onclick="deleteId(<%= item.getId() %>);">삭제</button>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<script>
    function deleteId(id) {
        location.href = "history-delete.jsp?id=" + id;
    }
</script>
</body>
</html>
