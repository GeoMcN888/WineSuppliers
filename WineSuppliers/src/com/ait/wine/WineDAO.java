package com.ait.wine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class WineDAO {
	
	Utility utility = new Utility();

    public List<Wine> findAll() {
        List<Wine> list = new ArrayList<Wine>();
        Connection c = null;
    	String sql = "SELECT * FROM wine ORDER BY name";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(utility.processWineRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
    



    public Wine findById(int id) {
    	String sql = "SELECT * FROM wine WHERE id = ? ";
        Wine wine = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                wine = utility.processWineRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return wine;
    }
    

    public List<Wine> findByName(String name) {
        List<Wine> list = new ArrayList<Wine>();
        Connection c = null;
    	String sql = "SELECT * FROM wine as e " +
			"WHERE UPPER(name) LIKE ? " +	
			"ORDER BY name";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + name.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(utility.processWineRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
    


    public List<Wine> findByCountryAndGrapes(String country, String grapes) {
        List<Wine> list = new ArrayList<Wine>();
        Connection c = null;
    	String sql = "SELECT * FROM wine as e " +
			"WHERE UPPER(country) LIKE ? " +
    		"AND UPPER(grapes) LIKE ?"+
			"ORDER BY name";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + country.toUpperCase() + "%");
            ps.setString(2, "%" + grapes.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(utility.processWineRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
    
    public List<Supplier> findSuppliersByWineId(int id) {
    	String sql = "SELECT * FROM wine "
    			+ "JOIN supplier_wines on (wine.id=supplier_wines.wine_id) "
    			+ "WHERE supplier_id = ?";
        List<Supplier> supplier = new ArrayList<>();
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	supplier.add(utility.processSupplierRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return supplier;
    }


    public Wine save(Wine wine)
	{
		return wine.getId() > 0 ? update(wine) : create(wine);
	}    
    
    public Wine create(Wine wine) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO wine (name, grapes, country, region, year, picture, description) VALUES (?, ?, ?, ?, ?, ?, ?)",
                new String[] { "ID" });
            ps.setString(1, wine.getName());
            ps.setString(2, wine.getGrapes());
            ps.setString(3, wine.getCountry());
            ps.setString(4, wine.getRegion());
            ps.setString(5, wine.getYear());
            ps.setString(6, wine.getPicture());
            ps.setString(7, wine.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            wine.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return wine;
    }
    
    public Wine update(Wine wine) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE wine SET name=?, grapes=?, country=?, region=?, year=?, picture=?, description=? WHERE id=?");
            ps.setString(1, wine.getName());
            ps.setString(2, wine.getGrapes());
            ps.setString(3, wine.getCountry());
            ps.setString(4, wine.getRegion());
            ps.setString(5, wine.getYear());
            ps.setString(6, wine.getPicture());
            ps.setString(7, wine.getDescription());
            ps.setInt(8, wine.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return wine;
    }


    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM wine WHERE id=?");
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
