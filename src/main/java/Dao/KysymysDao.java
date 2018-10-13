
package Dao;

import Database.Database;
import Domain.Aihe;
import Domain.Kysymys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KysymysDao implements Dao<Kysymys, Integer> {

    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        List<Kysymys> kysymykset = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM Kysymys").executeQuery()) {

            while (result.next()) {
                kysymykset.add(new Kysymys(result.getInt("id"), result.getInt("aihe_id"), result.getString("kysymysteksti")));

            }
            
        } catch (Exception ex) {
            Logger.getLogger(KysymysDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kysymykset;
    }

    public List<Kysymys> findAllWithAiheiId(Aihe aihe) throws SQLException, Exception {
        List<Kysymys> kysymykset = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE aihe_id = ?");
            stmt.setInt(1, aihe.getId());

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            while (result.next()) {
                kysymykset.add(new Kysymys(result.getInt("id"), result.getInt("aihe_id"), result.getString("kysymysteksti")));
            }
            
        }
        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys kysymys) throws SQLException {
        try (Connection conn = database.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Kysymys (aihe_id, kysymysteksti) VALUES (?, ?)");
            stmt.setInt(1, kysymys.getAihe_id());
            stmt.setString(2, kysymys.getKysymysTeksti());
            stmt.executeUpdate();

            
        } catch (Exception ex) {
            Logger.getLogger(KysymysDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
            
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(KysymysDao.class.getName()).log(Level.SEVERE, null, ex);
        }

      
    }

    public Kysymys findById(Integer id) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            
            return new Kysymys(result.getInt("id"), result.getInt("aihe_id"), result.getString("kysymysteksti"));
        }
    }

}
