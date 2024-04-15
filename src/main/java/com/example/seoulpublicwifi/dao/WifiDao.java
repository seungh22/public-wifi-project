package com.example.seoulpublicwifi.dao;

import com.example.seoulpublicwifi.common.Db;
import com.example.seoulpublicwifi.common.JDBCTemplate;
import com.example.seoulpublicwifi.dto.WifiDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiDao extends JDBCTemplate {
    public int insert(WifiDto wifiDto) {
        try {
            Class.forName(Db.CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        int affected = 0;

        try {
            conn = DriverManager.getConnection(Db.URL);

            String sql = "INSERT INTO WIFI VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            ps = conn.prepareStatement(sql);
            ps.setString(1, wifiDto.getMgrNo());
            ps.setString(2, wifiDto.getWrdofc());
            ps.setString(3, wifiDto.getMainNm());
            ps.setString(4, wifiDto.getAdres1());
            ps.setString(5, wifiDto.getAdres2());
            ps.setString(6, wifiDto.getInstlFloor());
            ps.setString(7, wifiDto.getInstlTy());
            ps.setString(8, wifiDto.getInstlMby());
            ps.setString(9, wifiDto.getSvcSe());
            ps.setString(10, wifiDto.getCmcwr());
            ps.setInt(11, wifiDto.getCnstcYear());
            ps.setString(12, wifiDto.getInoutDoor());
            ps.setString(13, wifiDto.getRemars3());
            ps.setDouble(14, wifiDto.getLat());
            ps.setDouble(15, wifiDto.getLnt());
            ps.setString(16, wifiDto.getWorkDttm());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("와이파이 데이터 삽입 성공");
            } else {
                System.out.println("와이파이 데이터 삽입 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public WifiDto select(String mgrNo) {
        WifiDto wifiDto = new WifiDto();

        try {
            Class.forName(Db.CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(Db.URL);

            String sql = "SELECT * FROM WIFI WHERE X_SWIFI_MGR_NO = ?;";

            ps = conn.prepareStatement(sql);
            ps.setString(1, mgrNo);

            rs = ps.executeQuery();
            while (rs.next()) {
                String mgrNo2 = rs.getString("X_SWIFI_MGR_NO");
                String wrdofc = rs.getString("X_SWIFI_WRDOFC");
                String mainNm = rs.getString("X_SWIFI_MAIN_NM");
                String adres1 = rs.getString("X_SWIFI_ADRES1");
                String adres2 = rs.getString("X_SWIFI_ADRES2");
                String instlFloor = rs.getString("X_SWIFI_INSTL_FLOOR");
                String instlTy = rs.getString("X_SWIFI_INSTL_TY");
                String instlMby = rs.getString("X_SWIFI_INSTL_MBY");
                String svcSe = rs.getString("X_SWIFI_SVC_SE");
                String cmcwr = rs.getString("X_SWIFI_CMCWR");
                int cnstcYear = rs.getInt("X_SWIFI_CNSTC_YEAR");
                String inoutDoor = rs.getString("X_SWIFI_INOUT_DOOR");
                String remars3 = rs.getString("X_SWIFI_REMARS3");
                double lat = rs.getDouble("LAT");
                double lnt = rs.getDouble("LNT");
                String workDttm = rs.getString("WORK_DTTM");

                wifiDto.setMgrNo(mgrNo2);
                wifiDto.setWrdofc(wrdofc);
                wifiDto.setMainNm(mainNm);
                wifiDto.setAdres1(adres1);
                wifiDto.setAdres2(adres2);
                wifiDto.setInstlFloor(instlFloor);
                wifiDto.setInstlTy(instlTy);
                wifiDto.setInstlMby(instlMby);
                wifiDto.setSvcSe(svcSe);
                wifiDto.setCmcwr(cmcwr);
                wifiDto.setCnstcYear(cnstcYear);
                wifiDto.setInoutDoor(inoutDoor);
                wifiDto.setRemars3(remars3);
                wifiDto.setLat(lat);
                wifiDto.setLnt(lnt);
                wifiDto.setWorkDttm(workDttm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return wifiDto;
    }

    public void deleteAll() {
        try {
            Class.forName(Db.CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(Db.URL);

            String sql = "DELETE FROM WIFI";
            ps = conn.prepareStatement(sql);

            int affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("모든 와이파이 데이터 제거 성공");
            } else {
                System.out.println("모든 와이파이 데이터 제거 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
    }

    public List<WifiDto> selectList(Double lat, Double lnt) {
        List<WifiDto> wifiDtoList = new ArrayList<>();

        try {
            Class.forName(Db.CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(Db.URL);

            String sql = "SELECT *, " +
                    "round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT)" +
                    "-radians(?))+sin(radians(?))*sin(radians(LAT))), 4) " +
                    "AS DISTANCE " +
                    "FROM WIFI " +
                    "ORDER BY DISTANCE " +
                    "LIMIT 20;";

            ps = conn.prepareStatement(sql);
            ps.setDouble(1, lat);
            ps.setDouble(2, lnt);
            ps.setDouble(3, lat);

            rs = ps.executeQuery();
            while (rs.next()) {
                double dist = rs.getDouble("DISTANCE");
                String mgrNo = rs.getString("X_SWIFI_MGR_NO");
                String wrdofc = rs.getString("X_SWIFI_WRDOFC");
                String mainNm = rs.getString("X_SWIFI_MAIN_NM");
                String adres1 = rs.getString("X_SWIFI_ADRES1");
                String adres2 = rs.getString("X_SWIFI_ADRES2");
                String instlFloor = rs.getString("X_SWIFI_INSTL_FLOOR");
                String instlTy = rs.getString("X_SWIFI_INSTL_TY");
                String instlMby = rs.getString("X_SWIFI_INSTL_MBY");
                String svcSe = rs.getString("X_SWIFI_SVC_SE");
                String cmcwr = rs.getString("X_SWIFI_CMCWR");
                int cnstcYear = rs.getInt("X_SWIFI_CNSTC_YEAR");
                String inoutDoor = rs.getString("X_SWIFI_INOUT_DOOR");
                String remars3 = rs.getString("X_SWIFI_REMARS3");
                double lat2 = rs.getDouble("LAT");
                double lnt2 = rs.getDouble("LNT");
                String workDttm = rs.getString("WORK_DTTM");

                WifiDto wifiDto = new WifiDto();
                wifiDto.setDist(dist);
                wifiDto.setMgrNo(mgrNo);
                wifiDto.setWrdofc(wrdofc);
                wifiDto.setMainNm(mainNm);
                wifiDto.setAdres1(adres1);
                wifiDto.setAdres2(adres2);
                wifiDto.setInstlFloor(instlFloor);
                wifiDto.setInstlTy(instlTy);
                wifiDto.setInstlMby(instlMby);
                wifiDto.setSvcSe(svcSe);
                wifiDto.setCmcwr(cmcwr);
                wifiDto.setCnstcYear(cnstcYear);
                wifiDto.setInoutDoor(inoutDoor);
                wifiDto.setRemars3(remars3);
                wifiDto.setLat(lat2);
                wifiDto.setLnt(lnt2);
                wifiDto.setWorkDttm(workDttm);

                wifiDtoList.add(wifiDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return wifiDtoList;
    }
}
