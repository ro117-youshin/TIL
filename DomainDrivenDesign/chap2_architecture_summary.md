CHAPTER 2. 아키텍처 개요
=====================
> 1. 아키텍처<br>
  > 1-1. 네 개의 영역<br>
  > 1-2. 계층 구조 아키텍처
> 2. DIP<br>
  > 2-1. DIP 주의사항<br>
  > 2-2. DIP와 아키텍처
> 3. 도메인 영역의 주요 구성요소
> 4. 인프라스트럭처
> 5. 모듈

## 1. 아키텍처

### 🏛️ 네 개의 영역
아키텍처의 전형적인 영역, '표현', '응용', '도메인', '인프라스트럭처'의 네 영역이다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/archi_1.png" width="500" height="300"/>

웹 애플리케이션에서 표현 영역은 HTTP 요청을 응용 영역이 필요로 하는 형식으로 변환해서 전달하고, 응용 영역의 응답을 HTTP 응답으로 변환해서 전송한다.
예를 들어, 표현 영역은 웹 브라우저가 HTTP 요청 파라미터로 전송한 데이터를 응용 서비스가 요구하는 형식의 객체 타입으로 변환해서 전달하고, 응용 서비스가 리턴한 결과를 JSON 형식으로 변환해서 HTTP 응답으로 웹 브라우저에 전송한다.

표현 영역을 통해 사용자의 요청을 전달받는 응용 영역은 시스템이 사용자에게 제공해야 할 기능을 구현한다. 주문 취소 기능을 제공하는 응용 서비스를 예로 들면 다음과 같이 주문 도메인 모델을 사용해서 기능을 구현한다.
```java
public class CancelOrderService {
    
    @Transactional
    public void cancelOrder(String orderId) {
        Order order = findOrderById(orderId);
        if(order == null) throw new OrderNotFoundException(orderId);
        order.cancel();
    }
 ... 
}
```
응용 서비스는 로직을 직접 수행하기보다는 도메인 모델에 로직 수행을 위임한다. 위 코드도 주문 취소 로직을 직접 구현하지 않고 `Order`객체에 취소 처리를 위임하고 있다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/application_to_domain.png" width="300" height="300"/>

<ins>도메인 영역은 도메인 모델을 구현한다.</ins>
* chap1에서 봤던 `Order`, `OrderLine`, `ShippingInfo`와 같은 도메인 모델이 이 영역에 위치한다.
* 도메인 모델은 도메인의 핵심 로직을 구현한다.

<ins>인프라스트럭처 영역은 구현 기술에 대한 것을 다룬다.</ins>
* RDBMS 연동을 처리
* 메시징 큐에 메세지를 전송하거나 수신하는 기능을 구현
* 몽고DB나 HBase를 사용해서 데이터베이스 연동을 처리
* SMTP를 이용한 메일 발송 기능을 구현하거나 HTTP 클라이언트를 이용해서 REST API를 호출하는 것도 처리
  인프라스트럭처 영역은 논리적인 개념을 표현하기보다 실제 구현을 다룬다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/infrastructure.png" width="300" height="300"/>

도메인, 응용, 표현 영역은 구현 기술을 사용한 코드를 직접 만들지 않는다.
대신 인프라스트럭처 영역에서 제공하는 기능을 사용해서 필요한 기능을 개발한다.

ex)
* 응용 영역에 DB 보관 데이터가 필요하면 인프라스트럭처 영역의 DB 모듈을 사용해서 데이터를 읽어온다.
* 외부 메일 발송을 위해 인프라스트럭처가 제공하는 SMTP 연동 모듈을 이용

---

### 🏛️ 계층 구조 아키텍처

표현, 응용 영역은 도메인 영역을 사용하고, 도메인 영역은 인프라스트럭처 영역을 사용하므로 계층 구조를 적용하기에 적당해 보인다.
도메인의 복잡도에 따라 응용과 도메인을 분리하기도 하고 한 계층으로 합치기도 하지만 전체적인 아키텍처는 아래의 그림과 같다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/layered_architecture.png" width="300" height="300"/>

계층 구조는 그 특성상 상위 계층에서 하위 계층으로의 의존만 존재하고 하위 계층은 상위 계층에 의존하지 않는다.<br>
ex) 응용 계층이 도메인 계층에 의존하지만, 인프라스트럭처 계층이 도메인에 의존하지 않는다.

계층 구조를 엄격하게 적용하면 상위 계층은 바로 아래의 계층에만 의존을 가져야 하지만 구현의 편리함을 위해 계층 구조를 유연하게 적용한다.<br>
ex) 응용 계층 아래 계층인 도메인 계층에 의존하지만 외부 시스템과의 연동을 위해 더 아래 계층인 인프라스트럭처 계층에 의존하기도 한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/layerd_architecture_dependency.png" width="500" height="300"/>

위 그림과 같이 응용 영역과 도메인 영역은 DB나 외부 시스템 연동을 위해 인프라스트럭처의 기능을 사용한다.
하지만 알아야 할 것은 <ins>표현, 응용, 도메인 계층이 상세한 구현 기술을 다루는 인프라스트럭처 계층에 종속</ins>된다는 점이다.

인프라스트럭처에 의존하면 '테스트의 어려움', '기능 확장의 어려움'이라는 두 가지 문제가 발생한다.

이에 대한 해답이 <ins>**DIP**</ins>이다.

---

## 2. DIP
> Dependency Inversion Principle, 의존 역전 원칙

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/high_level_module_and_low_level_module.png" width="500" height="300"/>

고수준 모듈이 제대로 동작하려면 저수준 모듈을 사용해야 한다. 그런데, 고수준 모듈이 저수준 모듈을 사용하면 앞서 계층 구조 아키텍처에서 언급했던 두 가지 문제(구현 변경과 테스트가 어려움)가 발생한다.

DIP는 이 문제를 해결하기 위해 <ins>저수준 모듈이 고수준 모듈에 의존</ins>하도록 바꾼다. 고수준 모듈을 구현하려면 저수준 모듈을 사용해야 하는데, 반대로 저수준 모듈이 고수준 모듈에 의존하도록 하려면 <ins>추상화한 인터페이스</ins>를 사용해야 한다.

`CalculateDiscountService` 입장에서 룰 적용을 Drools로 구현했는지, Java로 직접 구현했는지는 중요하지 않다.
단지, '고객 정보와 구매 정보에 룰을 적용해서 할인 금액을 구한다'는 것이 중요할 뿐.
```java
public interface RuleDiscounter {
    public Money applyRules(Customer customer, List<OrderLine> orderLines);
}
```
`CalculateDiscountService`가 `RuleDiscounter`를 이용하도록 바꿔보면
```java
public class CalculateDiscountService {
    private RuleDiscounter ruleDiscounter;
    
    public CalculateDiscountService(RuleDiscounter ruleDiscounter) {
        this.ruleDiscounter = ruleDiscounter;
    }
    
    public Money calculateDiscout(List<OrderLine> orderLines, String customerId) {
        Customer customer = findCustomer(customerId);
        return ruleDiscounter.applyRules(customer, orderLines);
    }
    ...
}
```
`CalculateDiscountService`는 Drools에 의존하는 코드를 갖고 있지 않다. 단지, `RuleDiscounter`가 룰을 적용한다는 것만 알 뿐.
실제 `RuleDiscounter`의 구현 객체는 생성자를 통해서 전달 받는다.

룰 적용을 구현한 클래스는 `RuleDiscounter`인터페이스를 상속받아 구현한다. (Drools 코드는 이해할 필요 없다.)
여기서 중요한 건 `RuleDiscounter`를 상속받아 구현 한다는 것.

```java
public class DroolsRuleDiscounter implements RuleDiscounter {
    private KieContainer kContainer;
    
    public DroolsRuleDiscounter() {
        KieServices ks = KieServices.Factory.get();
        kContainer = ks.getKieClasspathContainer();
    }
    
    @Override
    public Money applyRule(Customer customer, List<OrderLine> orderLines) {
        KieSession kSession = kContainer.newKieSession("discountSession");
        try {
          ... 코드 생략
          kSession.fireAllRules();        
        } finally {
            kSession.dispose();
        }
        return money.toImmutableMoney();
    }
}
```

구조를 보면 `CalculateDiscounterService`는 더 이상 구현 기술인 Drools에 의존하지 않는다. '룰을 이용한 할인 금액 계산'을 추상화한 `RuleDiscounter` 인터페이스에 의존할 뿐이다.
'룰을 이용한 할인 금액 계산'은 고수준 모듈의 개념이므로 `RuleDiscounter`인터페이스는 고수준 모듈에 속한다. `DroolsRuleDiscounter`는 고수준의 하위 기능인 `RuleDiscounter`를 구현한 것이므로 저수준 모듈에 속한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_structure.png" width="500" height="300"/>

#### 💡 그래서 DIP란?

DIP를 적용하면 위 그림과 같이 저수준 모듈이 고수준 모듈에 의존하게 된다. 고수준 모듈이 저수준 모듈을 사용하려면 고수준 모듈이 저수준 모듈에 의존해야 하는데,
반대로 <ins>저수준 모듈이 고수준 모듈에 의존</ins>한다고 해서 이를 <ins>**DIP(Dependency Inversion Principle, 의존 역전 원칙)**</ins> 라고 부른다.

또한 DIP를 적용하면 앞서 다른 영역이 인프라스트럭처 영역에 의존할 때 발생했던 두 가지 문제인 구현 교체가 어렵다는 문제와 테스트가 어려운 문제를 해소할 수 있다.

먼저 <ins>구현 기술 교체 문제</ins>를 보면, 고수준 모듈은 더 이상 저수준 모듈을 의존하지 않고 인터페이스에 의존한다.
실제 사용할 저수준 구현 객체는 아래 코드처럼 의존 주입을 이용해서 전달 받을 수 있다.

```java
// 사용할 저수준 객체 생성
RuleDiscounter ruleDiscounter = new DroolsRuleDiscounter();

// 생성자 방식으로 주입
CalculateDiscountService disService = new CalculateDiscountService(ruleDiscounter);
```
테스트에 대해 언급하기 전, `CalculateDiscountService`가 제대로 동작하려면 Customer를 찾는 기능도 구현해야 한다.
이를 위한 고수준 인터페이스 `CustomerRepository`를 만들었다.
`CalculateDiscountService`는 `CustomerRepository`와 `RuleDiscounter`를 사용해서 기능을 구현한다.

```java
public class CalculateDiscountService {
    private CustomerRepository customerRepository;
    private RuleDiscounter ruleDiscounter;
    
    public CalculateDiscountService(CustomerRepository customerRepository, RuleDiscounter ruleDiscounter) {
        this.customerRepository = customerRepository;
        this.ruleDiscounter = ruleDiscounter;
    }
    
    public Money calculateDiscount(OrderLine orderLines, String customerId) {
        Customer customer = findCustomer(customerId);
        return ruleDiscounter.applyRules(customer, orderLines);
    }
    
    private Customer findCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId);
        if(customer == null) throw new NoCustomerException();
        return customer;
    }
    ...
}
```
`CalculateDiscountService`를 테스트하려면 `CustomerRepository`와 `RuleDiscounter`를 구현한 객체가 필요하다.
만약 `CalculateDiscountService`가 저수준 모듈에 직접 의존했다면 저수준 모듈이 만들어지기까지 테스트를 할 수가 없었겠지만 `CustomerRepository`와 `RuleDiscounter`는 인터페이스이므로 대용 객체를 사용해서 테스트를 진행할 수 있다.

아래 코드는 대용 객체를 사용해서 Customer가 존재하지 않는 경우 Exception이 발생하는지 검증하는 테스트 코드이다.
```java
public class CalculateDiscountServiceTest {
    
    @Test(expected = NoCustomerException.class);
    public void noCustomer_thenExceptionShouldBeThrown() {
        // 테스트 목적의 대용 객체
        CustomerRepository stubRepo = mock(CustomerRepository.class);
        when(stubRepo.findById("noCustId")).thenReturn(null);
        
        RuleDiscounter stubRule = (cust, lines) -> null;
        
        // 대용 객체를 주입받아 테스트 진행
        CalculateDiscountService calDisSvc = new CalculateDiscountService(stubRepo, stubRule);
        calDisSvc.calculateDiscount(someLines, "noCustId");
    }
  
}
```
* `stubRepo` : `CustomerRepository` 대용 객체 (Mockito라는 Mock 프레임워크를 이용)
* `stubRule` : `RuleDiscounter` 대용 객체 (메서드가 하 나여서 람다식으로 객체 생성)

그래서 위 테스트 코드는 `CustomerRepository`와 `RuleDiscounter`의 실제 구현 클래스가 없어도 `CalculateDiscountService`를 테스트 할 수 있다.

결과적으로, DIP를 이용해서 고수준 모듈이 저수준 모듈에 의존하지 않도록 했기에 대용 객체를 사용해서 거의 모든 상황을 테스트 할 수 있다.

### 🏛️ DIP 주의사항
DIP를 잘못 생각하면 단순히 인터페이스와 구현 클래스를 분리하는 정도로 받아들일 수 있다. 
**DIP의 핵심**은 <ins>고수준 모듈이 저수준 모듈에 의존하지 않도록 하기 위함</ins>인데 DIP를 적용한 결과 구조만 보고 아래 그림과 같이 저수준 모듈에서 인터페이스를 추출하는 경우가 있다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/incorrect_dip.png" width="500" height="300"/>

위 그림은 잘못된 구조인데, 도메인 영역이 구현 기술을 다루는 인프라스트럭처 영역에 의존하고 있다. 즉, 여전히 고수준 모듈이 저수준 모듈에 의존하고 있는 것이다.
`RuleEngine` 인터페이스는 고수준 모듈인 도메인 관점이 아니라 룰 엔진이라는 저수준 모듈 관점에서 도출한 것이다.

<ins>DIP를 적용할 때 하위 기능을 추상화한 인터페이스는 고수준 모듈 관점에서 도출한다.</ins>
`CalculateDiscountService` 입장에서 봤을 때 할인 금액을 구하기 위해 룰 엔진을 사용하는지, 직접 연산하는지 여부는 중요하지 않다.
단지 규칙에 따라 할인 금액을 계산한다는 것이 중요할 뿐.
즉, '할인 금액 계산'을 추상화한 인터페이스는 저수준 모듈이 아닌 고수준 모듈에 위치한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/correct_dip.png" width="500" height="300"/>

### 🏛️ DIP와 아키텍처
인프라스트럭처 영역은 구현 기술을 다루는 저수준 모듈이고 응용 영역과 도메인 영역은 고수준 모듈이다.
인프라스트럭처 계층의 가장 하단에 위치하는 계층형 구조와 달리 아키텍처에 DIP를 적용하면 아래 그림과 같이 인프라스트럭처 영역이 응용 영역과 도메인 영역에 의존(상속)하는 구조가 된다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_architecture.png" width="300" height="400"/>

인프라스트럭처에 위치한 클래스가 도메인이나 응용 영역에 정의한 인터페이스를 상속받아 구현하는 구조가 되므로 도메인과 응용 영역에 대한 영향을 주지 않거나 최소화하면서 구현 기술을 변경 가능하다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_structure_final.png" width="500" height="300"/>

인프라스트럭처 영역의 `EmailNotifier`클래스는 응용 영역의 `Notifier`인터페이스를 상속받고 있다.
주문 시 통지 방식에 SMS를 추가해야 한다는 요구사항이 들어왔을 경우 응용 영역의 `OrderService`는 변경할 필요가 없다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_structure_modify_impl.png" width="500" height="300"/>

요구사항이 들어왔을 때 두 통지 방식을 함께 제공하는 `Notifier` 구현 클래스를 인프라스트럭처 영역에 추가하면 된다.
비슷하게 Mybatis 대신 JPA를 구현 기술로 사용하고 싶다면 JPA 이용한 `OrderRepository` 구현 클래스를 인프라스트럭처 영역에 추가하면 된다.
















