package com.jodonghyeon.neighborfriend.geoLite2;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class GeoReader {
    private static final String DATABASE_CITY_PATH = "/home/ec2-user/app/deploy/src/main/resources/GeoLite2-City.mmdb";
    private DatabaseReader reader;

    public GeoReader() throws IOException {
        File dbFile = new File(DATABASE_CITY_PATH);
        reader = new DatabaseReader.Builder(dbFile).build();
    }

    public CityResponse city(InetAddress ipAddress) {
        CityResponse response = null;

        try {
            response = reader.city(ipAddress);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
