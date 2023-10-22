package co.kr.demo.util.serializer;

import co.kr.demo.service.dto.domainDto.OptionDataADto;
import co.kr.demo.service.dto.domainDto.OptionDataDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.Null;
import java.io.IOException;


public class OptionDataDtoDeserializer extends JsonDeserializer<OptionDataDto> {
    @Override
    public OptionDataDto deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {

        JsonNode node = parser.getCodec().readTree(parser);

        if (node.has("anotherInfo")) {

            return OptionDataADto.builder()
                    .optionDataId(node.has("id") ? node.get("id").asLong() : null)
                    .optionData(node.has("optionData") ? node.get("optionData").asText() : null)
                    .optionDataName(node.has("optionDataName") ? node.get("optionDataName").asText() : null)
                    .anotherInfo(node.get("anotherInfo").asText())
                    .build();
        } else {
            return OptionDataDto.builder()
                    .optionDataId( node.has("id") ? node.get("id").asLong() : null)
                    .optionData(node.has("optionData") ? node.get("optionData").asText() : null)
                    .optionDataName(node.has("optionDataName") ? node.get("optionDataName").asText() : null)
                    .build();
        }
    }



}


