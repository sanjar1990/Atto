package org.example.repository;

import org.example.dto.CardDto;
import org.example.dto.TerminalDto;
import org.example.dto.TransactionDto;
import org.example.enums.TransactionType;
import org.example.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepo {
    public boolean addTransactionInfo(TransactionDto transactionDto) {
        Connection connection= DBConnection.getConnection();
        String sql="insert into transaction(card_number,amount, type, created_date) values(?,?,?,?)";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setInt(1,transactionDto.getCardNumber());
            pr.setDouble(2,transactionDto.getAmount());
            pr.setString(3, transactionDto.getTransactionType().name());
            pr.setTimestamp(4,Timestamp.valueOf(transactionDto.getCreatedDate()));
            return  pr.executeUpdate()>0;
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
    public List<TransactionDto> getUserTransactionHistory(String phone) {
        Connection connection=DBConnection.getConnection();
        List<TransactionDto> transactionDtoList=new LinkedList<>();
        String sql="select * from transaction as t inner join card as c on t.card_number=c.card_number where c.phone=? order by id desc";
        try {
    PreparedStatement pr= connection.prepareStatement(sql);
    pr.setString(1,phone);
    ResultSet rs=pr.executeQuery();
    while (rs.next()){
        TransactionDto transactionDto= new TransactionDto();
        transactionDto.setId(rs.getInt("id"));
        transactionDto.setCardNumber(rs.getInt("card_number"));
        transactionDto.setAmount(rs.getDouble("amount"));
        transactionDto.setTransactionType(TransactionType.valueOf(rs.getString("type")));
        transactionDto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        transactionDtoList.add(transactionDto);
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
        return transactionDtoList;
    }

    public List<TransactionDto> getAllTransactionHistory() {
        Connection connection=DBConnection.getConnection();
        List<TransactionDto> transactionDtoList=new LinkedList<>();
        String sql="select * from transaction order by id desc";
        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                TransactionDto transactionDto= new TransactionDto();
                transactionDto.setId(rs.getInt("id"));
                transactionDto.setCardNumber(rs.getInt("card_number"));
                transactionDto.setAmount(rs.getDouble("amount"));
                transactionDto.setTransactionType(TransactionType.valueOf(rs.getString("type")));
                transactionDto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                transactionDtoList.add(transactionDto);
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
        return transactionDtoList;
    }
    public List<TransactionDto> getTodayTransaction(LocalDate today) {
        Connection connection=DBConnection.getConnection();
        List<TransactionDto> transactionDtoList=new LinkedList<>();
        String sql="select * from transaction where created_date >? order by id desc ";

        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            pr.setTimestamp(1,Timestamp.valueOf(today.atStartOfDay()));
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                TransactionDto transactionDto= new TransactionDto();
                transactionDto.setId(rs.getInt("id"));
                transactionDto.setCardNumber(rs.getInt("card_number"));
                transactionDto.setAmount(rs.getDouble("amount"));
                transactionDto.setTransactionType(TransactionType.valueOf(rs.getString("type")));
                transactionDto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                transactionDtoList.add(transactionDto);
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
        return transactionDtoList;
    }

    public List<TransactionDto> getTransactionBetween(LocalDate fromDate, LocalDate toDate) {
        Connection connection=DBConnection.getConnection();
        List<TransactionDto> transactionDtoList=new LinkedList<>();
        String sql="select * from transaction where created_date >? and created_date<? order by id desc ";

        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            pr.setTimestamp(1,Timestamp.valueOf(fromDate.atStartOfDay()));
            pr.setTimestamp(2,Timestamp.valueOf(toDate.atStartOfDay()));
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                TransactionDto transactionDto= new TransactionDto();
                transactionDto.setId(rs.getInt("id"));
                transactionDto.setCardNumber(rs.getInt("card_number"));
                transactionDto.setAmount(rs.getDouble("amount"));
                transactionDto.setTransactionType(TransactionType.valueOf(rs.getString("type")));
                transactionDto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                transactionDtoList.add(transactionDto);
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
        return transactionDtoList;
    }

    public List<TransactionDto> getTransactionByTerminal(TerminalDto terminalDto) {
        Connection connection=DBConnection.getConnection();
        List<TransactionDto> transactionDtoList=new LinkedList<>();
        String sql="select * from transaction where terminal_code=?";
        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            pr.setInt(1,terminalDto.getTerminalCode());
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                TransactionDto transactionDto= new TransactionDto();
                transactionDto.setId(rs.getInt("id"));
                transactionDto.setCardNumber(rs.getInt("card_number"));
                transactionDto.setAmount(rs.getDouble("amount"));
                transactionDto.setTransactionType(TransactionType.valueOf(rs.getString("type")));
                transactionDto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                transactionDtoList.add(transactionDto);
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
        return transactionDtoList;
    }

    public List<TransactionDto> getTransactionByCard(CardDto cardDto) {
        Connection connection=DBConnection.getConnection();
        List<TransactionDto> transactionDtoList=new LinkedList<>();
        String sql="select * from transaction where card_number=?" +
                "order by id desc";
        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            pr.setInt(1,cardDto.getCardNumber());
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                TransactionDto transactionDto= new TransactionDto();
                transactionDto.setId(rs.getInt("id"));
                transactionDto.setCardNumber(rs.getInt("card_number"));
                transactionDto.setAmount(rs.getDouble("amount"));
                transactionDto.setTransactionType(TransactionType.valueOf(rs.getString("type")));
                transactionDto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                transactionDtoList.add(transactionDto);
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
        return transactionDtoList;
    }
}

