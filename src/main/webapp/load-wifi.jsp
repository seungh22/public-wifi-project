<%@ page import="com.example.seoulpublicwifi.service.WifiService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<style>
    * {
        text-align: center;
    }
</style>
<body>
<%
    WifiService wifiService = new WifiService();
    wifiService.init();
//    wifiService.save();
    wifiService.saveBatch();
%>

<h1><%= wifiService.total() %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
<a href="index.jsp">홈으로 가기</a>
</body>
</html>
