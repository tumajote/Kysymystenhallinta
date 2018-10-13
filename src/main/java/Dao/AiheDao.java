
package Dao;

import Database.Database;
import Domain.Aihe;
import Domain.Kurssi;
import Domain.Kysymys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AiheDao implements Dao<Aihe, Integer> {

    private Database database;

    public AiheDao(Database database) {
        this.database = database;
    }

    public Aihe findOneWithNimi(String nimi) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE aihe = ? ");
            stmt.setString(1, nimi);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Aihe(result.getInt("id"), result.getInt("kurssi_id"), result.getString("aihe"));
        }
    }

    public Aihe findAiheByKysymys(Kysymys kysymys) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE id = ?");
            stmt.setInt(1, kysymys.getAihe_id());

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Aihe(result.getInt("id"), result.getInt("kurssi_id"), result.getString("aihe"));
        }
    }

    public List<Aihe> findallWithKurssiId(Kurssi kurssi) throws SQLException,Exception {
        List<Aihe> aiheet = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE kurssi_id = ? ");
            stmt.setInt(1, kurssi.getId());
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            while (result.next()) {
                aiheet.add(new Aihe(result.getInt("id"), result.getInt("kurssi_id"), result.getString("aihe")));
            }
            return aiheet;

        }
    }

    @Override
    public Aihe saveOrUpdate(Aihe aihe) throws SQLException{
        try (Connection conn = database.getConnection()) {

            Aihe nimella = findOneWithNimi(aihe.getNimi());

            if (nimella != null) {
                return nimella;
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Aihe (aihe, kurssi_id) VALUES (?,?)");
            stmt.setString(1, aihe.getNimi());
            stmt.setInt(2, aihe.getKurssi_id());
            stmt.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(AiheDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Aihe findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Aihe> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
