package co.kr.demo.util.serializer;

import co.kr.demo.service.dto.domainDto.OptionDataDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;


public class OptionDataDtoDeserializer extends JsonDeserializer<OptionDataDto> {
    @Override
    public OptionDataDto deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        return null;

    }


}


