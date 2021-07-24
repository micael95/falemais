package br.com.uaubox.falemais.factory;

import org.modelmapper.ModelMapper;

public class FactoryManager {
    protected ModelMapper modelMapper = new ModelMapper();

    protected <S,T> T getObjectFromRequest(S source, Class<T> target) {
        return modelMapper.map(source, target);
    }

    protected <S,T> T getResponseFromObject(S source, Class<T> target) {
        return modelMapper.map(source, target);
    }
}
