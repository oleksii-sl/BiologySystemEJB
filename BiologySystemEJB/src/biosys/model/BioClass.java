package biosys.model;

/**
 * Class mapped to a table "classification" in the database.
 * It's simple Java Bean
 * @author Алексей
 */
public class BioClass {

    private String name;
    private Integer parentId;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + id + " " + name + " " + parentId;
    }
}
