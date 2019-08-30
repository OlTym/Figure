package com.task11;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static Storage inst;

    @SerializedName("SettingShape")
    private List<SettingShape> list = new ArrayList<>();

    public static synchronized Storage getInstance() {
        if (inst == null) {
            inst = new Storage();

        }
        return inst;
    }


    public void add(SettingShape settingShape) {

        list.add(settingShape);
    }

    public void delete() {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelect()) {
                list.remove(i);
            }

        }
    }

    public void deleteGroup() {

        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).isGroup()) {
                list.remove(i);
            }

        }
    }


    public List<SettingShape> getSettingShapeList() {
        return list;
    }


    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("SaveShape{");

        sb.append("list=").append(list);
        sb.append('}');
        return sb.toString();
    }

}
