package co.kr.demo.service.dto.businessDto;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class SearchConditionDto {


    public final static String START_DATE = "START";
    public final static String END_DATE = "END";
    private  final static String START_OF_DATE="1900-01-01";
    private final static  String END_OF_DATE="2999-12-31";
    private Instant startDate;
    private Instant endDate;


    public static Instant toParseInstant(String dateString, String standard) {


        /*
        * refactoring 필요
        * */

        try {
            LocalDateTime localDate;
            // LocalDate를 Instant로 변환하고 기본 시간대(예: UTC)로 설정
            if(standard.equals(START_DATE)){
                localDate = LocalDateTime.parse(dateString+"T00:00:00");
            }
            else{
                localDate = LocalDateTime.parse(dateString+"T23:59:59");
            }

            return localDate.atZone(ZoneId.of("Asia/Seoul")).toInstant();
        } catch (Exception e) {
            log.info("여기들어옴");

            if (standard.equals(START_DATE)) {
                LocalDate localDate = LocalDate.parse(START_OF_DATE);
                return localDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant();
            } else {
                LocalDate localDate = LocalDate.parse(END_OF_DATE);
                return localDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant();
            }
        }
    }

}
