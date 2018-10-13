
package Dao;

import Database.Database;
import Domain.Vastaus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VastausDao implements Dao<Vastaus, Integer> {

    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Vastaus> findAllByKysymysId(Integer id) throws SQLException {
        List<Vastaus> vastaukset = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
//            if (!result.next()) {
//                return null;
//            }
            while (result.next()) {
                String oikein = "Oikein";
                if (!result.getBoolean("oikein")) {
                    oikein = "Väärin";
                }

                vastaukset.add(new Vastaus(result.getInt("id"),
                        result.getInt("kysymys_id"),
                        result.getString("vastausteksti"),
                        oikein));
            }
          

        }
        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus vastaus) throws SQLException {
        try (Connection conn = database.getConnection()) {

            Boolean onkoOikein = true;
            if (vastaus.getOikein().equals("Väärin")) {
                onkoOikein = false;
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (?, ?, ?)");
            stmt.setInt(1, vastaus.getKysymys_id());
            stmt.setString(2, vastaus.getVastausteksti());
            stmt.setBoolean(3, onkoOikein);
            stmt.executeUpdate();

            
        }

        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

      
    }
       public Vastaus findById(Integer id) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            
            String oikein = "Oikein";
                if (!result.getBoolean("oikein")) {
                    oikein = "Väärin";
                }
           
            return new Vastaus(result.getInt("id"),
                        result.getInt("kysymys_id"),
                        result.getString("vastausteksti"),
                        oikein);
        }
    }

}
