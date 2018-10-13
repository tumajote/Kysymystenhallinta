package Domain;

public class Vastaus {

    private Integer id;
    private Integer kysymys_id;
    private String vastausteksti;
    private String oikein;

    public Vastaus(Integer id, Integer kysymys_id, String vastausteksti, String oikein) {
        this.id = id;
        this.kysymys_id = kysymys_id;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKysymys_id() {
        return kysymys_id;
    }

    public String getVastausteksti() {
        return vastausteksti;
    }

    public String getOikein() {
        return oikein;
    }
    
}
