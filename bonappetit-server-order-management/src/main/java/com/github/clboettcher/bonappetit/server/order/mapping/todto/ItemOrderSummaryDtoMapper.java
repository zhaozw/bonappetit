package com.github.clboettcher.bonappetit.server.order.mapping.todto;

import com.gihub.clboettcher.price_calculation.api.PriceCalculator;
import com.github.clboettcher.bonappetit.server.order.api.dto.read.ItemOrderSummaryDto;
import com.github.clboettcher.bonappetit.server.order.api.dto.read.SummaryDto;
import com.github.clboettcher.bonappetit.server.order.api.dto.read.SummaryEntryDto;
import com.github.clboettcher.bonappetit.server.order.entity.ItemOrderEntity;
import org.joda.time.DateTime;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ItemOrderSummaryDtoMapper {

    @Autowired
    private PriceCalculator priceCalculator;

    @Autowired
    private ItemOrderDtoMapper itemOrderDtoMapper;

    @Autowired
    private OptionOrderSummaryDtoMapper optionOrderSummaryDtoMapper;


    public ItemOrderSummaryDto mapToItemOrderSummaryDto(ItemOrderEntity itemOrder) {
        if (itemOrder == null) {
            return null;
        }

        return ItemOrderSummaryDto.builder()
                .itemTitle(itemOrder.getItemTitle())
                .totalPrice(this.priceCalculator.calculateTotalPrice(itemOrderDtoMapper.mapToItemOrderDto(itemOrder)))
                .optionOrders(optionOrderSummaryDtoMapper.mapToOptionOrderSummaryDtos(itemOrder.getAllOptionOrders()))
                .build();
    }

    public SummaryDto mapToSummaryDto(List<ItemOrderEntity> itemOrderEntities) {
        if (itemOrderEntities == null) {
            return null;
        }

        SummaryDto result = new SummaryDto();
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<ItemOrderSummaryDto> orderSummaryDtos = new ArrayList<>();
        // Map to item order dtos and sum up the total price
        // Look for oldest and newest orders.
        DateTime oldestOrderTime = null;
        DateTime newestOrderTime = null;
        for (ItemOrderEntity itemOrderEntity : itemOrderEntities) {
            ItemOrderSummaryDto orderSummaryDto = mapToItemOrderSummaryDto(itemOrderEntity);
            
            totalPrice = totalPrice.add(orderSummaryDto.getTotalPrice());
            orderSummaryDtos.add(orderSummaryDto);

            DateTime currentOrderTime = new DateTime(itemOrderEntity.getOrderTime());
            if (oldestOrderTime == null || currentOrderTime.isBefore(oldestOrderTime)) {
                oldestOrderTime = currentOrderTime;
            }
            if (newestOrderTime == null || currentOrderTime.isAfter(newestOrderTime)) {
                newestOrderTime = currentOrderTime;
            }
        }

        Map<ItemOrderSummaryDto, Long> orderSummaryToCount = orderSummaryDtos.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        result.setTotalPrice(totalPrice);
        result.setOrderSummaries(this.createOrderSummaries(orderSummaryToCount));
        result.setOldestOrderTime(oldestOrderTime);
        result.setNewestOrderTime(newestOrderTime);

        return result;
    }

    private List<SummaryEntryDto> createOrderSummaries(Map<ItemOrderSummaryDto, Long> orderSummaryToCount) {
        return orderSummaryToCount.entrySet()
                .stream()
                .map(entry -> SummaryEntryDto.builder()
                        .count(entry.getValue())
                        .orderSummary(entry.getKey())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
