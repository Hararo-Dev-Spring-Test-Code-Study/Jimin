package cleancode;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("이용권과 타입/기간이 일치하는 사물함만 반환한다")
public class StudyCafeLockerPassesTest {

    // 7. 이용권/사물함의 타임/기간이 같은 것 반환
    @DisplayName("기간은 같지만 타입이 다르면 사물함을 반환하지 않는다")
    @Test
    void findLockerPassBy_differentType() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 500000, 0.15);

        StudyCafeLockerPass lockerPass1 = StudyCafeLockerPass.of(StudyCafePassType.WEEKLY, 12, 10000);
        StudyCafeLockerPass lockerPass2 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);
        StudyCafeLockerPass lockerPass3 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 15000);

        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass1, lockerPass2, lockerPass3));

        // when
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass3); // 첫 번째 일치 항목만 반환됨
    }
}
