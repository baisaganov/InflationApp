package kz.inflation.InflationApp.models.abstractClasses;

import java.util.List;

public abstract class ItemCategoryAbstract {
    private Long id;
    private String name;
    private List<ItemAbstract> item;


    protected ItemCategoryAbstract() {
    }

    protected ItemCategoryAbstract(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemAbstract> getItem() {
        return item;
    }

    public void setItem(List<ItemAbstract> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "ItemCategoryAbstract{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", item=" + item +
                '}';
    }
}
