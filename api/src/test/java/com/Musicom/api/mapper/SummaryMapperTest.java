package com.Musicom.api.mapper;

import com.Musicom.data.model.Band;
import com.Musicom.web_api_contract.SummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SummaryMapperTest {
    private SummaryMapper<Band> bandSummaryMapper;

    @BeforeEach
    public void setUp() {
        bandSummaryMapper = new SummaryMapper<>();
    }

    @Test
    public void shouldReturnNull() {
        assertNull(bandSummaryMapper.mapDto(new SummaryDto(1, "name")));
    }
    @Test
    public void shouldReturnSummary() {
        Band band = new Band();
        band.setId(23L);
        band.setName("name23");
        band.setUrl("http://example.com/");
        assertEquals(new SummaryDto(23L, "name23"), bandSummaryMapper.mapEntity(band));
    }
    @Test
    public void shouldReturnSeveralSummaries() {
        Band band1 = new Band();
        band1.setId(23L);
        band1.setName("name23");
        band1.setUrl("http://example.com/");
        Band band2 = new Band();
        band2.setId(29L);
        band2.setName("Mateusz Kapibarowicz");
        band2.setUrl("http://mateusz.kapibarowicz/");
        assertThat(
                bandSummaryMapper.mapAllEntities(Set.of(band1, band2)),
                containsInAnyOrder(
                        new SummaryDto(23L, "name23"),
                        new SummaryDto(29L, "Mateusz Kapibarowicz")
                )
        );
    }
}
