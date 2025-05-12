package cleancode;

import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("이용권 금액 테스트")
public class StudyCafePassOrderTest {

    // 1, 2 할인 가격과 전체 금액 - 경계값 기준으로 1주권 / 2주권 / 12주권
    @DisplayName("1주권을 선택하면 할인이 적용되지 않는다.")
    @Test
    void oneWeek_Discount() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 1, 60000, 0.0); // 1주권
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // when
        int discountPrice = order.getDiscountPrice();
        int totalPrice = order.getTotalPrice();

        // then
        assertThat(discountPrice).isEqualTo(0); // 할인 X
        assertThat(totalPrice).isEqualTo(60000);
    }

    @DisplayName("2주권은 10% 할인이 적용된다.")
    @Test
    void twoWeek_Discount() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 100000, 0.10);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // when
        int discountPrice = order.getDiscountPrice();
        int totalPrice = order.getTotalPrice();

        // then
        assertThat(discountPrice).isEqualTo(10000); // 10% 할인
        assertThat(totalPrice).isEqualTo(90000);    // 총 90000원
    }

    @DisplayName("12주권은 15% 할인이 적용된다.")
    @Test
    void twelveWeek_Discount() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 12, 500000, 0.15);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // when
        int discountPrice = order.getDiscountPrice();
        int totalPrice = order.getTotalPrice();

        // then
        assertThat(discountPrice).isEqualTo(75000); // 15% 할인
        assertThat(totalPrice).isEqualTo(425000);   // 총 425000원
    }
}
