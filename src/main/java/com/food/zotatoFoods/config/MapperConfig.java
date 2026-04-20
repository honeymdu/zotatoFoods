package com.food.zotatoFoods.config;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.food.zotatoFoods.dto.PointDto;
import com.food.zotatoFoods.utils.GeomatryUtil;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new Converter<String, Long>() {
            @Override
            public Long convert(MappingContext<String, Long> context) {
                try {
                    return Long.parseLong(context.getSource());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });

        modelMapper.addConverter(new Converter<PointDto, Point>() {
            @Override
            public Point convert(MappingContext<PointDto, Point> context) {
                return GeomatryUtil.CreatePoint(context.getSource());
            }
        });

        modelMapper.addConverter(new Converter<Point, PointDto>() {
            @Override
            public PointDto convert(MappingContext<Point, PointDto> context) {
                Point point = context.getSource();
                if (point == null) return null;
                return new PointDto(new double[]{point.getX(), point.getY()});
            }
        });

        return modelMapper;

    }

}
