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
            font-size: 14px;
            padding: 8px;
        }

        #table-list tr:nth-child(odd) {
            background-color: #f2f2f2;
        }

        #table-list th {
            padding-top: 12px;
            padding-bottom: 12px;
            background-color: #04AA6D;
            text-align: center;
            color: white;
            width: 20%;
        }
    </style>
</head>
<body>
<h1>즐겨찾기 그룹 관리</h1>

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

<form method="post" action="bookmark-group-add-submit.jsp">
    <table id="table-list">
        <tr>
            <th>북마크 이름</th>
            <td>
                <input type="text" name="name">
            </td>
        </tr>
        <tr>
            <th>순서</th>
            <td>
                <input type="text" name="seq">
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <input type="submit" value="추가">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
