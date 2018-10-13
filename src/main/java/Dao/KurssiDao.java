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

public class KurssiDao implements Dao<Kurssi, Integer> {

    private Database database;

    public KurssiDao(Database database) {
        this.database = database;
    }

    @Override
    public Kurssi findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Kurssi findOneWithNimi(String nimi) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi WHERE kurssi = ? ");
            stmt.setString(1, nimi);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Kurssi(result.getInt("id"), result.getString("kurssi"));
        }
    }

    @Override
    public List<Kurssi> findAll() throws SQLException {
        List<Kurssi> kurssit = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM Kurssi").executeQuery()) {

            while (result.next()) {
                kurssit.add(new Kurssi(result.getInt("id"), result.getString("kurssi")));

            }

        } catch (Exception ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kurssit;
    }

    public ArrayList<Kurssi> getAll() throws SQLException, Exception {
        ArrayList<Kurssi> kurssit = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet resultSet1 = conn.prepareStatement("SELECT * FROM Kurssi").executeQuery()) {

            while (resultSet1.next()) {
                ArrayList<Aihe> aiheet = new ArrayList<>();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE kurssi_id = ?");
                stmt.setInt(1, resultSet1.getInt("id"));
                ResultSet resultSet2 = stmt.executeQuery();

                while (resultSet2.next()) {
                    ArrayList<Kysymys> kysymykset = new ArrayList<>();
                    PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM Kysymys WHERE aihe_id = ?");
                    stmt2.setInt(1, resultSet2.getInt("id"));
                    ResultSet resultSet3 = stmt2.executeQuery();

                    while (resultSet3.next()) {
                        kysymykset.add(new Kysymys(resultSet3.getInt("id"), resultSet3.getInt("Aihe_id"), resultSet3.getString("Kysymysteksti")));
                    }
                    if (kysymykset.size() > 0) {
                        Aihe aihe = new Aihe(resultSet2.getInt("id"), resultSet2.getInt("kurssi_id"), resultSet2.getString("aihe"));
                        aihe.setKysymykset(kysymykset);
                        aiheet.add(aihe);
                    }
                }
                if (aiheet.size() > 0) {
                    Kurssi kurssi = new Kurssi(resultSet1.getInt("id"), resultSet1.getString("kurssi"));
                    kurssi.setAiheet(aiheet);
                    kurssit.add(kurssi);
                }

            }

        }
        return kurssit;
    }

    @Override
    public Kurssi saveOrUpdate(Kurssi kurssi) throws SQLException {
        try (Connection conn = database.getConnection()) {

            Kurssi nimella = findOneWithNimi(kurssi.getNimi());

            if (nimella != null) {
                return nimella;
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Kurssi (kurssi) VALUES (?)");
            stmt.setString(1, kurssi.getNimi());
            stmt.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Kurssi findKurssiByAihe(Aihe aihe) throws SQLException, Exception{
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi WHERE id = ?");
            stmt.setInt(1, aihe.getKurssi_id());

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Kurssi(result.getInt("id"), result.getString("kurssi"));
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
