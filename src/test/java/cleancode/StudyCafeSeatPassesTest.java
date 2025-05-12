package cleancode;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("특정 타입의 이용권 필터링")
public class StudyCafeSeatPassesTest {

    // 5. 특정 타입에 대해서만 필터링
    @DisplayName("FIXED 타입만 필터링한다.")
    @Test
    void findPassBy_PassType(){
        // given
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 4, 60000, 0.1);
        StudyCafeSeatPass weeklyPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 200000, 0.1);
        StudyCafeSeatPass fixedPass1 = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 500000, 0.15);
        StudyCafeSeatPass fixedPass2 = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 100000, 0.1);

        StudyCafeSeatPasses passList = StudyCafeSeatPasses.of(List.of(hourlyPass, weeklyPass, fixedPass1, fixedPass2));

        // when
        List<StudyCafeSeatPass> fixedPasses = passList.findPassBy(StudyCafePassType.FIXED); // 필터링할 타입 지정

        // then
        assertThat(fixedPasses).hasSize(2);
        assertThat(fixedPasses).allMatch(pass -> pass.getPassType() == StudyCafePassType.FIXED);
    }
}
