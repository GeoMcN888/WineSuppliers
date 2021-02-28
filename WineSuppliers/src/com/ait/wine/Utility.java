package com.ait.wine;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Utility {

    public Wine processWineRow(ResultSet rs) throws SQLException {
        Wine wine = new Wine();
        wine.setId(rs.getInt("id"));
        wine.setName(rs.getString("name"));
        wine.setGrapes(rs.getString("grapes"));
        wine.setCountry(rs.getString("country"));
        wine.setRegion(rs.getString("region"));
        wine.setYear(rs.getString("year"));
        wine.setPicture(rs.getString("picture"));
        wine.setDescription(rs.getString("description"));
        return wine;
    }
    
    public Supplier processSupplierRow(ResultSet rs) throws SQLException {
    	Supplier supplier = new Supplier();
    	supplier.setId(rs.getInt("id"));
    	supplier.setName(rs.getString("name"));
    	supplier.setCountry(rs.getString("country"));
        return supplier;
    }
}
