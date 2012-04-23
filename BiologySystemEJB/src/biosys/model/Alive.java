package biosys.model;


/**
 * Class mapped to a table "living_entity" in the database.
 * It's simple Java Bean
 * @author �������
 */
public class Alive {

    private String name;
    private String nameLatin;
    private int lifespan;
    private double avgWeight;
    private String nativeRange;
    private long population;
    private int bioClass;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBioClass() {
        return bioClass;
    }

    public void setBioClass(int bioClass) {
        this.bioClass = bioClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLatin() {
        return nameLatin;
    }

    public void setNameLatin(String nameLatin) {
        this.nameLatin = nameLatin;
    }

    public String getNativeRange() {
        return nativeRange;
    }

    public void setNativeRange(String nativeRange) {
        this.nativeRange = nativeRange;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public double getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(double avgWeight) {
        this.avgWeight = avgWeight;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getSimpleName()).append(": ").append("id: " + id + "\n");
        s.append("name: " + name + " ").append("nameLatin: " + nameLatin + "\n");
        s.append("lifespan: " + lifespan + " ").append("avgWeight: " + avgWeight + "\n");
        s.append("nativeRange: " + nativeRange + " ").append("population: " + population + "\n");
        s.append("bioClass: " + bioClass + " ").append("\n");
        return s.toString();
    }
}
