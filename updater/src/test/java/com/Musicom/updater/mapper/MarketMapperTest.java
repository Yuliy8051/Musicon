package com.Musicom.updater.mapper;

import com.Musicom.data.model.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketMapperTest {
    private MarketMapper marketMapper;

    @BeforeEach
    public void setUp() {
        marketMapper = new MarketMapper();
    }

    @Test
    public void shouldReturnedMappedMarket() {
        String name = "US";
        Market result = marketMapper.map(name);
        assertEquals(name, result.getName());
    }
}
