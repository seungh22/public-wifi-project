<%@ page import="com.example.seoulpublicwifi.dto.WifiDto" %>
<%@ page import="com.example.seoulpublicwifi.dao.WifiDao" %>
<%@ page import="com.example.seoulpublicwifi.dao.BookmarkGroupDao" %>
<%@ page import="com.example.seoulpublicwifi.dto.BookmarkGroupDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>

    <style>
        #link-list {
            margin-bottom: 20px;
        }

        #bookmark-list {
            margin-bottom: 5px;
        }

        #bookmark-list select {
            float: left;
            margin-right: 5px;
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
<h1>와이파이 정보 구하기</h1>

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

<%
    String mgrNo = request.getParameter("mgrNo");
    String dist = request.getParameter("dist");

    WifiDao wifiDao = new WifiDao();
    WifiDto wifiDto = wifiDao.select(mgrNo);

    BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
    List<BookmarkGroupDto> bookmarkGroupDtoList = bookmarkGroupDao.selectList();
%>

<div id="bookmark-list">
    <form action="bookmark-add-submit.jsp" method="post" id="bookmark-form">
        <select name="gid">
            <option value="none" selected>북마크 그룹 이름 선택</option>
            <%
                for (BookmarkGroupDto item : bookmarkGroupDtoList) {
            %>
            <option value="<%= item.getId() %>">
                <%= item.getName() %>
            </option>
            <%
                }
            %>
        </select>

        <input type="submit" value="즐겨찾기 추가하기">
        <input type="hidden" name="mgrNo" value="<%= mgrNo %>">
    </form>
</div>

<table id="table-list">
    <tr>
        <th>거리(Km)</th>
        <td><%= dist %>
        </td>
    </tr>
    <tr>
        <th>관리번호</th>
        <td><%= wifiDto.getMgrNo() %>
        </td>
    </tr>
    <tr>
        <th>자치구</th>
        <td><%= wifiDto.getWrdofc() %>
        </td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td><%= wifiDto.getMainNm() %>
        </td>
    </tr>
    <tr>
        <th>도로명주소</th>
        <td><%= wifiDto.getAdres1() %>
        </td>
    </tr>
    <tr>
        <th>상세주소</th>
        <td><%= wifiDto.getAdres2() %>
        </td>
    </tr>
    <tr>
        <th>설치위치(층)</th>
        <td><%= wifiDto.getInstlFloor() %>
        </td>
    </tr>
    <tr>
        <th>설치유형</th>
        <td><%= wifiDto.getInstlTy() %>
        </td>
    </tr>
    <tr>
        <th>설치기관</th>
        <td><%= wifiDto.getInstlMby() %>
        </td>
    </tr>
    <tr>
        <th>서비스구분</th>
        <td><%= wifiDto.getSvcSe() %>
        </td>
    </tr>
    <tr>
        <th>망종류</th>
        <td><%= wifiDto.getCmcwr() %>
        </td>
    </tr>
    <tr>
        <th>설치년도</th>
        <td><%= wifiDto.getCnstcYear() %>
        </td>
    </tr>
    <tr>
        <th>실내외구분</th>
        <td><%= wifiDto.getInoutDoor() %>
        </td>
    </tr>
    <tr>
        <th>WIFI접속환경</th>
        <td><%= wifiDto.getRemars3() %>
        </td>
    </tr>
    <tr>
        <th>X좌표</th>
        <td><%= wifiDto.getLnt() %>
        </td>
    </tr>
    <tr>
        <th>Y좌표</th>
        <td><%= wifiDto.getLat() %>
        </td>
    </tr>
    <tr>
        <th>작업일자</th>
        <td><%= wifiDto.getWorkDttm() %>
        </td>
    </tr>
</table>
</body>
</html>