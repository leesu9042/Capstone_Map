
//poi 데이터 클래스

package com.example.capstone_map;

public class Poi {
    private String name;
    private String tel;
    private String upperAddrName;
    private String middleAddrName;
    private String lowerAddrName;
    private double lat;
    private double lon;
    private String desc;

    public Poi(String name, String tel, String upperAddrName, String middleAddrName, String lowerAddrName, double lat, double lon, String desc) {
        this.name = name;
        this.tel = tel;
        this.upperAddrName = upperAddrName;
        this.middleAddrName = middleAddrName;
        this.lowerAddrName = lowerAddrName;
        this.lat = lat;
        this.lon = lon;
        this.desc = desc;
    }

    // getter들 추가!

    public String getName() { return name; }
    public String getTel() { return tel; }
    public String getUpperAddrName() { return upperAddrName; }
    public String getMiddleAddrName() { return middleAddrName; }
    public String getLowerAddrName() { return lowerAddrName; }
    public double getLatitude() { return lat; }
    public double getLongitude() { return lon; }
}
