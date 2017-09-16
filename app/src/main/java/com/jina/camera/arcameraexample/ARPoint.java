package com.jina.camera.arcameraexample;

import android.location.Location;

/**
 * Created by bon on 2017-05-09.
 */

public class ARPoint {  //	- 로케이션 헬퍼에서 사용하는 location을 만드는 클래스인 듯 함
    Location location;
    String name;

    public ARPoint(String name, double lat, double lon, double altitude) {
        this.name = name;
        location = new Location("ARPoint");
        location.setLatitude(lat);
        location.setLongitude(lon);
        location.setAltitude(altitude);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
