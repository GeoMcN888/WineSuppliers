package com.ait.wine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
	
	Utility utility = new Utility();
	
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

    public List<Supplier> findByName(String name) {
        List<Supplier> list = new ArrayList<Supplier>();
        Connection c = null;
    	String sql = "SELECT * FROM supplier as e " +
			"WHERE UPPER(name) LIKE ? " +	
			"ORDER BY name";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + name.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
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
    
    public List<Wine> findWinesBySupplierId(int id) {
    	String sql = "SELECT * FROM wine "
    			+ "JOIN supplier_wines on (wine.id=supplier_wines.wine_id) "
    			+ "WHERE supplier_id = ?";
        List<Wine> wines = new ArrayList<>();
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                wines.add(utility.processRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return wines;
    }
       
    public Supplier create(Supplier resource) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO supplier (name, country) VALUES (?, ?)",
                new String[] { "ID" });
            ps.setString(1, resource.getName());
            ps.setString(2, resource.getCountry());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            resource.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return resource;
    }
    
 
    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM supplier WHERE id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
    }
    
}
