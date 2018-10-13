
package Domain;

import java.util.ArrayList;
import java.util.Objects;


public class Kurssi{
    
    private Integer id;
    private String nimi;
    private ArrayList<Aihe> aiheet;

    public Kurssi(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.aiheet = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public ArrayList<Aihe> getAiheet() {
        return aiheet;
    }

    public void setAiheet(ArrayList<Aihe> aiheet) {
        this.aiheet = aiheet;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.nimi);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kurssi other = (Kurssi) obj;
        if (!Objects.equals(this.nimi, other.nimi)) {
            return false;
        }
        return true;
    }

   

 
}
