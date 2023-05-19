package kz.inflation.InflationApp.enums;

public enum OfficialBidsEnumeration {
    MZP("МЗП", "MZP"),
    MRP("МРП", "MRP");

    private String nameRu;
    private String nameEn;

    OfficialBidsEnumeration(String nameRu, String nameEn) {
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }
}
