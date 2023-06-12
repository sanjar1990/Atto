package org.example.repository;

import org.example.dto.TerminalDto;
import org.example.enums.TerminalStatus;
import org.example.util.DBConnection;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Repository
public class TerminalRepo {
    public TerminalDto checkTerminalByNum(int terminalNum) {
        Connection connection= DBConnection.getConnection();
        String sql="select * from terminal where code=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setInt(1,terminalNum);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                TerminalDto terminalDto= new TerminalDto();
                terminalDto.setTerminalCode(rs.getInt("code"));
                terminalDto.setAddress(rs.getString("address"));
                terminalDto.setTerminalStatus(TerminalStatus.valueOf(rs.getString("status")));
                terminalDto.setCreated_date(rs.getTimestamp("created_date").toLocalDateTime());
                return terminalDto;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public boolean createTerminal(TerminalDto terminalDto) {
        Connection connection= DBConnection.getConnection();
        String sql="insert into terminal(code,address,status,created_date) values(?,?,?,?)";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setInt(1,terminalDto.getTerminalCode());
            pr.setString(2,terminalDto.getAddress());
            pr.setString(3,terminalDto.getTerminalStatus().name());
            pr.setTimestamp(4,Timestamp.valueOf(terminalDto.getCreated_date()));
            return pr.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<TerminalDto> getAllTerminal() {
        Connection connection= DBConnection.getConnection();
        List<TerminalDto> terminalDtoList= new LinkedList<>();
        String sql="select * from terminal";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                TerminalDto terminalDto= new TerminalDto();
                terminalDto.setTerminalCode(rs.getInt("code"));
                terminalDto.setAddress(rs.getString("address"));
                terminalDto.setTerminalStatus(TerminalStatus.valueOf(rs.getString("status")));
                terminalDto.setCreated_date(rs.getTimestamp("created_date").toLocalDateTime());
                terminalDtoList.add(terminalDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return terminalDtoList;
    }

    public boolean updateTerminal(int oldCode, int newCode, String newAddress) {
        Connection connection= DBConnection.getConnection();
        String sql="update terminal set code=?, address=? where code=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setInt(1,newCode);
            pr.setString(2,newAddress);
            pr.setInt(3,oldCode);
            return pr.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean updateTerminalStatus(int terminalCode, String newStatus) {
        Connection connection= DBConnection.getConnection();
        String sql="update terminal set status=? where code=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,newStatus);
            pr.setInt(2,terminalCode);
            return pr.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
