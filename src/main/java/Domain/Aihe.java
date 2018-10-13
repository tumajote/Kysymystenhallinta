package Domain;

import java.util.ArrayList;

public class Aihe {

    private Integer id;
    private Integer kurssi_id;
    private String nimi;
    private ArrayList<Kysymys> kysymykset ;

    public Aihe(Integer id, Integer kurssi_id, String nimi) {
        this.id = id;
        this.kurssi_id = kurssi_id;
        this.nimi = nimi;
        this.kysymykset = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getKurssi_id() {
        return kurssi_id;
    }

    public String getNimi() {
        return nimi;
    }

    public ArrayList<Kysymys> getKysymykset() {
        return kysymykset;
    }

    public void setKysymykset(ArrayList<Kysymys> kysymykset) {
        this.kysymykset = kysymykset;
    }

}
