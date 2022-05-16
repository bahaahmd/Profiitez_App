package com.example.project2;

import java.util.ArrayList;

public class Province {
    String province_name;
    private static ArrayList<Province> provinces_list;

    public Province(String province_name) {
        this.province_name = province_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }



    public static ArrayList<Province> getProvinces_list() {
        return provinces_list;
    }


}
