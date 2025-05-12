package cleancode;

import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("선택한 이용권에 대한 타입/기간/할인율 테스트")
public class StudyCafeSeatPassTest {

    private StudyCafeSeatPass fixedPass; // 고정석 이용권
    private StudyCafeSeatPass weeklyPass; // 고정석 이용권
    private StudyCafeLockerPass lockerPass; // 사물함 이용

    @BeforeEach
    void setUp() {
        fixedPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 500000, 0.15);
        weeklyPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 6, 200000, 0.1);
        lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 10000);
    }

    // 6. 사용자가 좌석을 선택했을 때, 좌석의 타입, 기간, 할인율 등을 확인
    @DisplayName("고정권 + 사물함 사용에 대한 정보 확인")
    @Test
    void totalPrice_FixedPassWithLocker() {
        // given
        StudyCafePassOrder order = StudyCafePassOrder.of(fixedPass, lockerPass); // 최종 이용 내역

        // when
        boolean result = fixedPass.cannotUseLocker();
        int discountPrice = order.getDiscountPrice();
        int totalPrice = order.getTotalPrice();

        // then
        assertThat(result).isFalse();   // 고정석은 사물함 사용 가능
        assertThat(discountPrice).isEqualTo(75000);      // 15% 할인 금액 = 75000
        assertThat(totalPrice).isEqualTo(435000);        // 최종 금액 500000 + 10000 - 75000
    }

    @DisplayName("주간권 + 사물함 사용에 대한 정보 확인")
    @Test
    void totalPrice_WeeklyPassWithoutLocker() {
        // given
        StudyCafePassOrder order = StudyCafePassOrder.of(weeklyPass, null); // 최종 이용 내역

        // when
        boolean result = weeklyPass.cannotUseLocker();
        int discountPrice = order.getDiscountPrice();
        int totalPrice = order.getTotalPrice();

        // then
        assertThat(result).isTrue();   // 주간권은 사물함 사용 불가능
        assertThat(discountPrice).isEqualTo(20000);      // 10% 할인 금액 = 20000
        assertThat(totalPrice).isEqualTo(180000);         // 200000 - 20000 = 180000
    }
}
