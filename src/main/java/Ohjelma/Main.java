package Ohjelma;

import Dao.AiheDao;
import Dao.KurssiDao;
import Dao.KysymysDao;
import Dao.VastausDao;
import Database.Database;
import Domain.Aihe;
import Domain.Kurssi;
import Domain.Kysymys;
import Domain.Vastaus;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Throwable {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }


        Database database = new Database("jdbc:sqlite:kysymykset.db");


        KurssiDao kurssiDao = new KurssiDao(database);
        KysymysDao kysymysDao = new KysymysDao(database);
        AiheDao aiheDao = new AiheDao(database);
        VastausDao vastausDao = new VastausDao(database);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("teksti", "Kysymykset");
            List<Kurssi> kurssit = kurssiDao.getAll();
            map.put("kurssit", kurssit);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaaKysymys", (req, res) -> {
            String kurssiNimi = req.queryParams("kurssi");
            String aiheenNimi = req.queryParams("aihe");
            String kysymyksenTeksti = req.queryParams("kysymysteksti");
            Kurssi kurssi = new Kurssi(-1, kurssiNimi);
            kurssiDao.saveOrUpdate(kurssi);
            Integer id = kurssiDao.findOneWithNimi(kurssiNimi).getId();
            Aihe aihe = new Aihe(-1, id, aiheenNimi);
            aiheDao.saveOrUpdate(aihe);
            id = aiheDao.findOneWithNimi(aiheenNimi).getId();
            Kysymys kysymys = new Kysymys(-1, id, kysymyksenTeksti);
            kysymysDao.saveOrUpdate(kysymys);
            System.out.println("Vastaanotettiin ");
            res.redirect("/");
            return "";
        });

        Spark.get("/kysymykset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer kysymysId = Integer.parseInt(req.params(":id"));
            Kysymys kysymys = kysymysDao.findById(kysymysId);
            Aihe aihe = aiheDao.findAiheByKysymys(kysymys);
            Kurssi kurssi = kurssiDao.findKurssiByAihe(aihe);
            map.put("kurssi", kurssi);
            map.put("aihe", aihe);
            map.put("kysymys", kysymys);
            map.put("vastaukset", vastausDao.findAllByKysymysId(kysymysId));

            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kysymykset/:id", (req, res) -> {
            String vastausteksti = req.queryParams("vastausteksti");
            Integer kysymys_Id = Integer.parseInt(req.params(":id"));
            String oikein = req.queryParams("Oikein");
            System.out.println(oikein);

            vastausDao.saveOrUpdate(new Vastaus(-1, kysymys_Id, vastausteksti, oikein));
            res.redirect("/kysymykset/" + Integer.parseInt(req.params(":id")));
            return "";

        });

        Spark.post("/kysymykset/poista/:id", (req, res) -> {
            Integer kysymys_Id = Integer.parseInt(req.params(":id"));
            kysymysDao.delete(kysymys_Id);
            res.redirect("/");
            return "";

        });

        Spark.post("/kysymykset/poistavastaus/:id", (req, res) -> {
            Integer vastaus_Id = Integer.parseInt(req.params(":id"));
            Integer kysymys_id = vastausDao.findById(vastaus_Id).getKysymys_id();
            vastausDao.delete(vastaus_Id);
            res.redirect("/kysymykset/" + kysymys_id);
            return "";

        });
    }

    
}
