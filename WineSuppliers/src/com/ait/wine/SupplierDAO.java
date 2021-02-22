package com.ait.wine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
	
	public List<Supplier> findAll() {
        List<Supplier> list = new ArrayList<>();
        Connection c = null;
    	String sql = "SELECT * FROM supplier ORDER BY name";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
	
    public Supplier findById(int id) {
    	String sql = "SELECT * FROM supplier WHERE id = ? ";
        Supplier supplier = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	supplier = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return supplier;
    }
	
    protected Supplier processRow(ResultSet rs) throws SQLException {
    	Supplier supplier = new Supplier();
    	supplier.setId(rs.getInt("id"));
    	supplier.setName(rs.getString("name"));
    	supplier.setCountry(rs.getString("country"));
        return supplier;
    }

}
