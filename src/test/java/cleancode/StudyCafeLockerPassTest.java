package cleancode;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("사물함 이용 테스트")
public class StudyCafeLockerPassTest {

    // 3. 사물함 사용 시 이용권 타입과 기간 확인
    @DisplayName("이용권 타입이 같지만 기간이 다르면 사물함을 사용할 수 없다.")
    @Test
    void isSameDurationType_differentDuration() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 100000, 0.10);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 2, 30000);

        // when
        boolean result = seatPass.isSameDurationType(lockerPass);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("이용권 기간이 같지만 타입이 다르면 사물함을 사용할 수 없다.")
    @Test
    void isSameDurationType_differentType() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 100000, 0.10);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.WEEKLY, 4, 100000);

        // when
        boolean result = seatPass.isSameDurationType(lockerPass);

        // then
        assertThat(result).isFalse();
    }

    // 4. 이용권에 따른 사물함 사용 권한
    @DisplayName("사용자가 주간 이용권을 선택했을 때, 사물함을 이용할 수 없다.")
    @Test
    void weeklyCannotUseLocker() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 12, 500000, 0.15);

        // when
        boolean result = seatPass.cannotUseLocker();    // FIXED가 아니면 사용 불가

        // then
        assertThat(result).isTrue();    // WEEKLY는 사용 못하니까 true로 반환되어야 함
    }

    @DisplayName("사용자가 시간 이용권을 선택했을 때, 사물함을 이용할 수 없다.")
    @Test
    void hourlyCannotUseLocker() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 4, 80000, 0.0);

        // when
        boolean result = seatPass.cannotUseLocker();    // FIXED가 아니면 사용 불가

        // then
        assertThat(result).isTrue();    // HOURLY는 사용 못하니까 true로 반환되어야 함
    }
}
