package kz.inflation.InflationApp.models.abstractClasses;

import java.time.LocalDate;
import java.util.Objects;

public abstract class ItemAbstract {
    private Long id;
    private Long articul;
    private String name;
    private int price;
    private LocalDate updatedTime;
    private ItemCategoryAbstract category;
}
