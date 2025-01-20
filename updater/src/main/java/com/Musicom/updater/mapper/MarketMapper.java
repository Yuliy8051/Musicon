package com.Musicom.updater.mapper;

import com.Musicom.data.model.Market;
import org.springframework.stereotype.Component;

@Component
public class MarketMapper implements IMap<String, Market> {
    @Override
    public Market map(String name) {
        Market market = new Market();
        market.setName(name);
        return market;
    }
}
