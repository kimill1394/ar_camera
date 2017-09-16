package com.jina.camera.arcameraexample;

import android.location.Location;
// * 계산을 위해 각 함수가 좌표값을 가지는 location 객체를 받아 사용함

/**
 * Created by bon on 2017-05-09.
 */


public class LocationHelper {
    // * GPS coordinate to Navigation coordinate
    // * GPS 값을 네비게이션 값으로 변경해주는 클래스
    private final static double WGS84_A = 6378137.0;                  // WGS 84 semi-major axis constant in meters
    private final static double WGS84_E2 = 0.00669437999014;          // square of WGS 84 eccentricity

    public static float[] WSG84toECEF(Location location) {
        // * Convert GPS coordinate to ECEF coordinate (Earth-centered Earth-fixed coordinate)
        // * 얻은 location객체의 wsg84 좌표계의 값을 ecef좌표계로 변경
        double radLat = Math.toRadians(location.getLatitude());
        double radLon = Math.toRadians(location.getLongitude());

        float clat = (float) Math.cos(radLat);
        float slat = (float) Math.sin(radLat);
        float clon = (float) Math.cos(radLon);
        float slon = (float) Math.sin(radLon);

        float N = (float) (WGS84_A / Math.sqrt(1.0 - WGS84_E2 * slat * slat));

        float x = (float) ((N + location.getAltitude()) * clat * clon);
        float y = (float) ((N + location.getAltitude()) * clat * slon);
        float z = (float) ((N * (1.0 - WGS84_E2) + location.getAltitude()) * slat);

        return new float[] {x , y, z};
    }

    public static float[] ECEFtoENU(Location currentLocation, float[] ecefCurrentLocation, float[] ecefPOI) {
        // * Convert ECEF coordinate to Navigation coordinate
        // * exec로 변경한 좌표값(추측)을 담은 location객체의 좌표값을 이용해 네비게이션 좌표를 추출
        // * 매개변수 행렬 두개 뭔지 모르겠음
        double radLat = Math.toRadians(currentLocation.getLatitude());
        double radLon = Math.toRadians(currentLocation.getLongitude());

        float clat = (float)Math.cos(radLat);
        float slat = (float)Math.sin(radLat);
        float clon = (float)Math.cos(radLon);
        float slon = (float)Math.sin(radLon);

        float dx = ecefCurrentLocation[0] - ecefPOI[0];
        float dy = ecefCurrentLocation[1] - ecefPOI[1];
        float dz = ecefCurrentLocation[2] - ecefPOI[2];

        float east = -slon*dx + clon*dy;

        float north = -slat*clon*dx - slat*slon*dy + clat*dz;

        float up = clat*clon*dx + clat*slon*dy + slat*dz;

        return new float[] {east , north, up, 1};
    }
}
