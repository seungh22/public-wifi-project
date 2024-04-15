package com.example.seoulpublicwifi.dao;

import com.example.seoulpublicwifi.common.Db;
import com.example.seoulpublicwifi.common.JDBCTemplate;
import com.example.seoulpublicwifi.dto.BookmarkGroupDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupDao extends JDBCTemplate {
    public int insert(BookmarkGroupDto bookmarkGroupDto) {
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

            String sql = "INSERT INTO BOOKMARK_GROUP (NAME, SEQUENCE, REGISTER_DTTM) VALUES (?, ?, datetime('now', 'localtime'));";

            ps = conn.prepareStatement(sql);
            ps.setString(1, bookmarkGroupDto.getName());
            ps.setInt(2, bookmarkGroupDto.getSeq());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("북마크 그룹 데이터 삽입 성공");
            } else {
                System.out.println("북마크 그룹 데이터 삽입 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public BookmarkGroupDto select(int id) {
        BookmarkGroupDto bookmarkGroupDto = new BookmarkGroupDto();

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

            String sql = "SELECT * FROM BOOKMARK_GROUP WHERE ID = ?;";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id2 = rs.getInt("ID");
                String name = rs.getString("NAME");
                int seq = rs.getInt("SEQUENCE");
                Date regDttm = rs.getDate("REGISTER_DTTM");
                Date uptDttm = rs.getDate("UPDATE_DTTM");

                bookmarkGroupDto.setId(id2);
                bookmarkGroupDto.setName(name);
                bookmarkGroupDto.setSeq(seq);
                bookmarkGroupDto.setRegDttm(regDttm);
                bookmarkGroupDto.setUptDttm(uptDttm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return bookmarkGroupDto;
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

            ps = conn.prepareStatement("PRAGMA foreign_keys = ON;");
            ps.executeUpdate();

            String sql = "DELETE FROM BOOKMARK_GROUP WHERE ID = ?;";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("북마크 그룹 데이터 삭제 성공");
            } else {
                System.out.println("북마크 그룹 데이터 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public int update(BookmarkGroupDto bookmarkGroupDto) {
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

            String sql = "UPDATE BOOKMARK_GROUP SET NAME = ?, SEQUENCE = ?, UPDATE_DTTM = datetime('now', 'localtime') WHERE ID = ?;";

            ps = conn.prepareStatement(sql);
            ps.setString(1, bookmarkGroupDto.getName());
            ps.setInt(2, bookmarkGroupDto.getSeq());
            ps.setInt(3, bookmarkGroupDto.getId());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("북마크 그룹 데이터 업데이트 성공");
            } else {
                System.out.println("북마크 그룹 데이터 업데이트 실패");
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
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(Db.URL);

            String sql = "SELECT COUNT(*) FROM BOOKMARK_GROUP;";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return count;
    }

    public List<BookmarkGroupDto> selectList() {
        List<BookmarkGroupDto> bookmarkGroupDtoList = new ArrayList<>();

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

            String sql = "SELECT * FROM BOOKMARK_GROUP ORDER BY ID";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                int seq = rs.getInt("SEQUENCE");
                Date regDttm = rs.getDate("REGISTER_DTTM");
                Date uptDttm = rs.getDate("UPDATE_DTTM");

                BookmarkGroupDto bookmarkGroupDto = new BookmarkGroupDto();
                bookmarkGroupDto.setId(id);
                bookmarkGroupDto.setName(name);
                bookmarkGroupDto.setSeq(seq);
                bookmarkGroupDto.setRegDttm(regDttm);
                bookmarkGroupDto.setUptDttm(uptDttm);

                bookmarkGroupDtoList.add(bookmarkGroupDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return bookmarkGroupDtoList;
    }
}
