package com.propertysystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PropertyService {

    private final Set<String> regions = Set.of("DLN_WROC_C", "DLN_WROC_PC", "DLN_POZA_WROC", "SL_POL", "SL_KATO", "SL_PN", "M_WAW_CE", "M_WAW_W", "M_WAW_Z", "LUBL", "LUBL_INNE");

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PropertyRepository propertyRepository;

    private static final String requestStringTemplate = "http://localhost:8000/api/real-estates/%s?page=%s";

    public void updateData() {
        for (String region : regions) {
            updateRegion(region);
        }
    }

    private void updateRegion(String region) {
        HttpResponse<String> response = sendUpdateRequest(region, 1);
        if (response != null && response.statusCode() == HttpStatus.OK.value()) {
            long totalPages = getTotalPages(response);
            List<Property> data = getData(response);
            updateEntities(data, region);
            if (totalPages > 1) {
                for (int i = 2; i < totalPages; i++) {
                    response = sendUpdateRequest(region, i);
                    data = getData(response);
                    updateEntities(data, region);
                }
            }
        }
    }

    private void updateEntities(List<Property> data, String region) {
        for (Property property : data) {
            PropertyEntity propertyEntity = mapToPropertyEntity(property, region);
            Optional<PropertyEntity> persistedProperty = propertyRepository.findById(property.getId());
            persistedProperty.ifPresent(entity -> propertyEntity.setDateAdded(entity.getDateAdded()));
            propertyRepository.save(propertyEntity);
        }
    }

    private PropertyEntity mapToPropertyEntity(Property property, String region) {
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId(property.getId());
        propertyEntity.setPrice(property.getPrice());
        propertyEntity.setType(property.getType());
        propertyEntity.setDateAdded(LocalDateTime.now().toLocalDate());
        propertyEntity.setAreaRange(mapAreaRangeFromArea(property.getArea()));
        propertyEntity.setRegion(region);
        return propertyEntity;
    }

    private String mapAreaRangeFromArea(Double area) {
        if (area >= 18.0 && area < 45.0) {
            return "S";
        } else if (area >= 45.0 && area < 80.0) {
            return "M";
        } else if (area >= 80.0 && area < 400.0) {
            return "L";
        } else return "";
    }

    private HttpResponse<String> sendUpdateRequest(String region, int page) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(String.format(requestStringTemplate, region, 1)))
                    .GET()
                    .build();
            return http.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }
    }

    private long getTotalPages(HttpResponse<String> response) {
        try {
            JsonNode jsonNodeRoot = objectMapper.readTree(response.body());
            JsonNode totalPagesJson = jsonNodeRoot.get("totalPages");
            return objectMapper.readValue(totalPagesJson.asText(), Long.class);
        } catch (Exception e) {
            return 0;
        }
    }

    private List<Property> getData(HttpResponse<String> response) {
        try {
            JsonNode jsonNodeRoot = objectMapper.readTree(response.body());
            ArrayNode dataJson = (ArrayNode) jsonNodeRoot.get("data");
            return objectMapper.readValue(dataJson.toString(), new TypeReference<>() {
            });
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public double getAverageValue(String region, String size, String types, String startDate, String endDate) {
        String[] typesArray = types.split(",");
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyMMdd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyMMdd"));
        List<PropertyEntity> byRegionSizeTypeAndDate = propertyRepository.findByRegionSizeTypeAndDate(region, size, typesArray, start, end);
        return byRegionSizeTypeAndDate.stream().mapToDouble(PropertyEntity::getPrice).average().orElse(Double.NaN);
    }
}
