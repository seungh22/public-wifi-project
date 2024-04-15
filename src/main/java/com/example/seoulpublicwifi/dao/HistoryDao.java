package com.example.seoulpublicwifi.dao;

import com.example.seoulpublicwifi.common.Db;
import com.example.seoulpublicwifi.common.JDBCTemplate;
import com.example.seoulpublicwifi.dto.HistoryDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao extends JDBCTemplate {
    public int insert(HistoryDto historyDto) {
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

            String sql = "INSERT INTO HISTORY (LAT, LNT, SEARCH_DTTM) VALUES (?, ?, datetime('now', 'localtime'));";

            ps = conn.prepareStatement(sql);
            ps.setDouble(1, historyDto.getLat());
            ps.setDouble(2, historyDto.getLnt());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("히스토리 데이터 삽입 성공");
            } else {
                System.out.println("히스토리 데이터 삽입 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public int delete(int id) {
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

            String sql = "DELETE FROM HISTORY WHERE ID = ?;";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("히스토리 데이터 삭제 성공");
            } else {
                System.out.println("히스토리 데이터 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public int count() {
        int count = 0;

        try {
            Class.forName(Db.CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(Db.URL);

            String sql = "SELECT COUNT(*) FROM HISTORY;";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return count;
    }

    public List<HistoryDto> selectList() {
        List<HistoryDto> historyDtoList = new ArrayList<>();

        try {
            Class.forName(Db.CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(Db.URL);

            String sql = "SELECT * FROM HISTORY ORDER BY ID DESC";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                double lat = rs.getDouble("LAT");
                double lnt = rs.getDouble("LNT");
                Date srcDttm = rs.getDate("SEARCH_DTTM");

                HistoryDto historyDto = new HistoryDto();
                historyDto.setId(id);
                historyDto.setLat(lat);
                historyDto.setLnt(lnt);
                historyDto.setSrcDttm(srcDttm);

                historyDtoList.add(historyDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return historyDtoList;
    }
}
