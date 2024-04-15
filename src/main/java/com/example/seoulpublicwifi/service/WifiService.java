package com.example.seoulpublicwifi.service;

import com.example.seoulpublicwifi.common.Db;
import com.example.seoulpublicwifi.dao.WifiDao;
import com.example.seoulpublicwifi.dto.WifiDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WifiService {
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_KEY = "4a43664b5873756534327744695647";
    private static final String DATA_TYPE = "json";
    private static final String SERVICE_NAME = "TbPublicWifiInfo";

    // 서울 공공 와이파이 총 개수
    public int total() throws IOException {
        String urlBuilder = BASE_URL + "/" +
                URLEncoder.encode(API_KEY, "UTF-8") + "/" +
                URLEncoder.encode(DATA_TYPE, "UTF-8") + "/" +
                URLEncoder.encode(SERVICE_NAME, "UTF-8") + "/1/1";

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
        int listTotalCount = jsonObject.getAsJsonObject(SERVICE_NAME).get("list_total_count").getAsInt();
        return listTotalCount;
    }

    // 서울 공공 와이파이 저장
    public void save() throws IOException {
        int data = 1;
        for (int i = 0; i < total() / 1000 + 1; i++) {
            String urlBuilder = BASE_URL + "/" +
                    URLEncoder.encode(API_KEY, "UTF-8") + "/" +
                    URLEncoder.encode(DATA_TYPE, "UTF-8") + "/" +
                    URLEncoder.encode(SERVICE_NAME, "UTF-8") + "/" +
                    URLEncoder.encode(String.valueOf(data), "UTF-8") + "/" +
                    URLEncoder.encode(String.valueOf(data + 999), "UTF-8");

            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
            JsonArray rowArray = jsonObject.getAsJsonObject(SERVICE_NAME).getAsJsonArray("row");

            for (int j = 0; j < rowArray.size(); j++) {
                JsonObject rowObject = rowArray.get(j).getAsJsonObject();

                WifiDto wifiDto = new WifiDto();
                wifiDto.setMgrNo(rowObject.get("X_SWIFI_MGR_NO").getAsString());
                wifiDto.setWrdofc(rowObject.get("X_SWIFI_WRDOFC").getAsString());
                wifiDto.setMainNm(rowObject.get("X_SWIFI_MAIN_NM").getAsString());
                wifiDto.setAdres1(rowObject.get("X_SWIFI_ADRES1").getAsString());
                wifiDto.setAdres2(rowObject.get("X_SWIFI_ADRES2").getAsString());
                wifiDto.setInstlFloor(rowObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                wifiDto.setInstlTy(rowObject.get("X_SWIFI_INSTL_TY").getAsString());
                wifiDto.setInstlMby(rowObject.get("X_SWIFI_INSTL_MBY").getAsString());
                wifiDto.setSvcSe(rowObject.get("X_SWIFI_SVC_SE").getAsString());
                wifiDto.setCmcwr(rowObject.get("X_SWIFI_CMCWR").getAsString());
                wifiDto.setCnstcYear(rowObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                wifiDto.setInoutDoor(rowObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                wifiDto.setRemars3(rowObject.get("X_SWIFI_REMARS3").getAsString());
                wifiDto.setLat(rowObject.get("LAT").getAsDouble());
                wifiDto.setLnt(rowObject.get("LNT").getAsDouble());
                wifiDto.setWorkDttm(rowObject.get("WORK_DTTM").getAsString());

                WifiDao wifiDao = new WifiDao();
                wifiDao.insert(wifiDto);
            }
            data += 1000;
        }
    }

    public void saveBatch() throws IOException {
        try {
            Class.forName(Db.CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DriverManager.getConnection(Db.URL);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO WIFI VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);

            int data = 1;
            for (int i = 0; i < total() / 1000 + 1; i++) {
                String urlBuilder = BASE_URL + "/" +
                        URLEncoder.encode(API_KEY, "UTF-8") + "/" +
                        URLEncoder.encode(DATA_TYPE, "UTF-8") + "/" +
                        URLEncoder.encode(SERVICE_NAME, "UTF-8") + "/" +
                        URLEncoder.encode(String.valueOf(data), "UTF-8") + "/" +
                        URLEncoder.encode(String.valueOf(data + 999), "UTF-8");

                URL url = new URL(urlBuilder);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                conn.disconnect();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
                JsonArray rowArray = jsonObject.getAsJsonObject(SERVICE_NAME).getAsJsonArray("row");

                for (int j = 0; j < rowArray.size(); j++) {
                    JsonObject rowObject = rowArray.get(j).getAsJsonObject();

                    ps.setString(1, rowObject.get("X_SWIFI_MGR_NO").getAsString());
                    ps.setString(2, rowObject.get("X_SWIFI_WRDOFC").getAsString());
                    ps.setString(3, rowObject.get("X_SWIFI_MAIN_NM").getAsString());
                    ps.setString(4, rowObject.get("X_SWIFI_ADRES1").getAsString());
                    ps.setString(5, rowObject.get("X_SWIFI_ADRES2").getAsString());
                    ps.setString(6, rowObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    ps.setString(7, rowObject.get("X_SWIFI_INSTL_TY").getAsString());
                    ps.setString(8, rowObject.get("X_SWIFI_INSTL_MBY").getAsString());
                    ps.setString(9, rowObject.get("X_SWIFI_SVC_SE").getAsString());
                    ps.setString(10, rowObject.get("X_SWIFI_CMCWR").getAsString());
                    ps.setInt(11, rowObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                    ps.setString(12, rowObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                    ps.setString(13, rowObject.get("X_SWIFI_REMARS3").getAsString());
                    ps.setDouble(14, rowObject.get("LAT").getAsDouble());
                    ps.setDouble(15, rowObject.get("LNT").getAsDouble());
                    ps.setString(16, rowObject.get("WORK_DTTM").getAsString());
                    ps.addBatch();
                }
                ps.executeBatch();
                ps.clearBatch();
                connection.commit();

                data += 1000;
            }
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 서울 공공 와이파이 초기화
    public void init() {
        WifiDao wifiDao = new WifiDao();
        wifiDao.deleteAll();
    }
}
