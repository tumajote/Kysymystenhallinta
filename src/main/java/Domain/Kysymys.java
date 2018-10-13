
package Domain;

import java.util.ArrayList;


public class Kysymys {
    private Integer id;
    private Integer aihe_id;
    private String kysymysTeksti;
    private ArrayList<Vastaus> vastaukset;

    public Kysymys(Integer id, Integer aihe_id,  String kysymysTeksti) {
        this.id = id;
        this.aihe_id = aihe_id;
        this.kysymysTeksti = kysymysTeksti;
        this.vastaukset= new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getAihe_id() {
        return aihe_id;
    }

    public String getKysymysTeksti() {
        return kysymysTeksti;
    }

    public ArrayList<Vastaus> getVastaukset() {
        return vastaukset;
    }


    
}
