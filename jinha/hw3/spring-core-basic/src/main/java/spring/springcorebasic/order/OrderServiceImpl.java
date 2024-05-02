package spring.springcorebasic.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberRepository;

@Component
@RequiredArgsConstructor
// 이 어노테이션과 생성자 둘 다 사용할 수는 없다.
// @RequiredArgsConstructor 어노테이션이 생성자를 만들어주기 때문이다.
public class OrderServiceImpl implements OrderService{

    /*
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPoicy();
    // 위의 방식은 DIP, OCP를 위반한다. 구체에 의존하지 않고 추상화에 의존해야 한다.
    */

    //아래는 DIP를 충족하기 위한 변경된 코드이다.
    private final DiscountPolicy discountPolicy;

    // 회원의 등급을 확인하기 위함 => 할인정책 적용을 위해
    private final MemberRepository memberRepository;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); // 사용자의 등급을 통해 할인정책을 달리 하기 위함
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice); // 주문을 생성하여 반환

    }
}