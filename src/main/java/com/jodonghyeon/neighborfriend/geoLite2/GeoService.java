package com.jodonghyeon.neighborfriend.geoLite2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jodonghyeon.neighborfriend.domain.model.Address;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Subdivision;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

@Service
public class GeoService {

    private final GeoReader geoReader;
    private final String kakaoKey;

    public GeoService(@Value("${secret}") String kakaoKey, GeoReader geoReader) {
        this.kakaoKey = kakaoKey;
        this.geoReader = geoReader;
    }

    private InetAddress getIpAddress() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip = req.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.isEmpty()) {
            ip = req.getRemoteAddr();
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getRemoteAddr();
        }
        if (ip == null || ip.isEmpty()) {
            throw new RuntimeException();
        }

        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ipAddress;
    }

    public Address findCity() throws UnknownHostException {
        InetAddress ipAddress = getIpAddress();
        CityResponse response = geoReader.city(ipAddress);

        Subdivision subdivision = response.getMostSpecificSubdivision();
        City city = response.getCity();
        Location location = response.getLocation();

        return loadLocation(location.getLatitude(), location.getLongitude());
    }


    public Address loadLocation(Double lat1, Double lon1) {
        URL obj;
        Double lat = lat1;
        Double lon = lon1;
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + lon + "&y=" + lat;
        ObjectMapper mapper = new ObjectMapper();

        try {
            String coordinatesystem = "WGS84";
            obj = new URL(url + "&input_coord=" + coordinatesystem);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + kakaoKey);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            JsonNode root = mapper.readTree(response.toString());
            JsonNode documents = root.get("documents");

            for (JsonNode document : documents) {
                JsonNode address = document.get("address");
                String region_3depth_name = address.get("region_3depth_name").asText();

                return Address.builder()
                        .address(region_3depth_name)
                        .lon(lon)
                        .lat(lat)
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
