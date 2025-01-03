# CHAPTER 2. 아키텍처 개요

> [DDD Start! 도메인 주도 설계 구현과 핵심 개념 익히기 - 최범균] 학습 후 기록

> 1. 아키텍처<br>
  > 1-1. 네 개의 영역<br>
  > 1-2. 계층 구조 아키텍처
> 2. DIP<br>
  > 2-1. DIP 주의사항<br>
  > 2-2. DIP와 아키텍처
> 3. 도메인 영역의 주요 구성요소<br>
  > 3-1. 엔티티와 밸류<br>
  > 3-2. 애그리거트<br>
  > 3-3. 리포지터리<br>
  > 3-4. 요청 처리 흐름
> 4. 인프라스트럭처
> 5. 모듈

## 1. 아키텍처

### 🏛️ 네 개의 영역
&nbsp;아키텍처의 전형적인 영역, '표현', '응용', '도메인', '인프라스트럭처'의 네 영역이다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/archi_1.png" width="500" height="300"/>

&nbsp;웹 애플리케이션에서 표현 영역은 HTTP 요청을 응용 영역이 필요로 하는 형식으로 변환해서 전달하고, 응용 영역의 응답을 HTTP 응답으로 변환해서 전송한다.
예를 들어, 표현 영역은 웹 브라우저가 HTTP 요청 파라미터로 전송한 데이터를 응용 서비스가 요구하는 형식의 객체 타입으로 변환해서 전달하고, 응용 서비스가 리턴한 결과를 JSON 형식으로 변환해서 HTTP 응답으로 웹 브라우저에 전송한다.

&nbsp;표현 영역을 통해 사용자의 요청을 전달받는 응용 영역은 시스템이 사용자에게 제공해야 할 기능을 구현한다. 주문 취소 기능을 제공하는 응용 서비스를 예로 들면 다음과 같이 주문 도메인 모델을 사용해서 기능을 구현한다.
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
&nbsp;응용 서비스는 로직을 직접 수행하기보다는 도메인 모델에 로직 수행을 위임한다. 위 코드도 주문 취소 로직을 직접 구현하지 않고 `Order`객체에 취소 처리를 위임하고 있다.

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

&nbsp;도메인, 응용, 표현 영역은 구현 기술을 사용한 코드를 직접 만들지 않는다.
대신 인프라스트럭처 영역에서 제공하는 기능을 사용해서 필요한 기능을 개발한다.

ex)
* 응용 영역에 DB 보관 데이터가 필요하면 인프라스트럭처 영역의 DB 모듈을 사용해서 데이터를 읽어온다.
* 외부 메일 발송을 위해 인프라스트럭처가 제공하는 SMTP 연동 모듈을 이용

---

### 🏛️ 계층 구조 아키텍처

&nbsp;표현, 응용 영역은 도메인 영역을 사용하고, 도메인 영역은 인프라스트럭처 영역을 사용하므로 계층 구조를 적용하기에 적당해 보인다.
도메인의 복잡도에 따라 응용과 도메인을 분리하기도 하고 한 계층으로 합치기도 하지만 전체적인 아키텍처는 아래의 그림과 같다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/layered_architecture.png" width="300" height="300"/>

&nbsp;계층 구조는 그 특성상 상위 계층에서 하위 계층으로의 의존만 존재하고 하위 계층은 상위 계층에 의존하지 않는다.<br>
ex) 응용 계층이 도메인 계층에 의존하지만, 인프라스트럭처 계층이 도메인에 의존하지 않는다.

&nbsp;계층 구조를 엄격하게 적용하면 상위 계층은 바로 아래의 계층에만 의존을 가져야 하지만 구현의 편리함을 위해 계층 구조를 유연하게 적용한다.<br>
ex) 응용 계층 아래 계층인 도메인 계층에 의존하지만 외부 시스템과의 연동을 위해 더 아래 계층인 인프라스트럭처 계층에 의존하기도 한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/layerd_architecture_dependency.png" width="500" height="300"/>

&nbsp;위 그림과 같이 응용 영역과 도메인 영역은 DB나 외부 시스템 연동을 위해 인프라스트럭처의 기능을 사용한다.
하지만 알아야 할 것은 <ins>표현, 응용, 도메인 계층이 상세한 구현 기술을 다루는 인프라스트럭처 계층에 종속</ins>된다는 점이다.

&nbsp;인프라스트럭처에 의존하면 '테스트의 어려움', '기능 확장의 어려움'이라는 두 가지 문제가 발생한다.

이에 대한 해답이 <ins>**DIP**</ins>이다.

---

## 2. DIP
> Dependency Inversion Principle, 의존 역전 원칙

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/high_level_module_and_low_level_module.png" width="500" height="300"/>

&nbsp;고수준 모듈이 제대로 동작하려면 저수준 모듈을 사용해야 한다. 그런데, 고수준 모듈이 저수준 모듈을 사용하면 앞서 계층 구조 아키텍처에서 언급했던 두 가지 문제(구현 변경과 테스트가 어려움)가 발생한다.

&nbsp;DIP는 이 문제를 해결하기 위해 <ins>저수준 모듈이 고수준 모듈에 의존</ins>하도록 바꾼다. 고수준 모듈을 구현하려면 저수준 모듈을 사용해야 하는데, 반대로 저수준 모듈이 고수준 모듈에 의존하도록 하려면 <ins>추상화한 인터페이스</ins>를 사용해야 한다.

&nbsp;`CalculateDiscountService` 입장에서 룰 적용을 Drools로 구현했는지, Java로 직접 구현했는지는 중요하지 않다.
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
&nbsp;`CalculateDiscountService`는 Drools에 의존하는 코드를 갖고 있지 않다. 단지, `RuleDiscounter`가 룰을 적용한다는 것만 알 뿐.
실제 `RuleDiscounter`의 구현 객체는 생성자를 통해서 전달 받는다.

&nbsp;룰 적용을 구현한 클래스는 `RuleDiscounter`인터페이스를 상속받아 구현한다. (Drools 코드는 이해할 필요 없다.)
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

&nbsp;구조를 보면 `CalculateDiscounterService`는 더 이상 구현 기술인 Drools에 의존하지 않는다. '룰을 이용한 할인 금액 계산'을 추상화한 `RuleDiscounter` 인터페이스에 의존할 뿐이다.
'룰을 이용한 할인 금액 계산'은 고수준 모듈의 개념이므로 `RuleDiscounter`인터페이스는 고수준 모듈에 속한다. `DroolsRuleDiscounter`는 고수준의 하위 기능인 `RuleDiscounter`를 구현한 것이므로 저수준 모듈에 속한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_structure.png" width="500" height="300"/>

#### 💡 그래서 DIP란?

&nbsp;DIP를 적용하면 위 그림과 같이 저수준 모듈이 고수준 모듈에 의존하게 된다. 고수준 모듈이 저수준 모듈을 사용하려면 고수준 모듈이 저수준 모듈에 의존해야 하는데,
반대로 <ins>저수준 모듈이 고수준 모듈에 의존</ins>한다고 해서 이를 <ins>**DIP(Dependency Inversion Principle, 의존 역전 원칙)**</ins> 라고 부른다.

&nbsp;또한 DIP를 적용하면 앞서 다른 영역이 인프라스트럭처 영역에 의존할 때 발생했던 두 가지 문제인 구현 교체가 어렵다는 문제와 테스트가 어려운 문제를 해소할 수 있다.

&nbsp;먼저 <ins>구현 기술 교체 문제</ins>를 보면, 고수준 모듈은 더 이상 저수준 모듈을 의존하지 않고 인터페이스에 의존한다.
실제 사용할 저수준 구현 객체는 아래 코드처럼 의존 주입을 이용해서 전달 받을 수 있다.

```java
// 사용할 저수준 객체 생성
RuleDiscounter ruleDiscounter = new DroolsRuleDiscounter();

// 생성자 방식으로 주입
CalculateDiscountService disService = new CalculateDiscountService(ruleDiscounter);
```
&nbsp;테스트에 대해 언급하기 전, `CalculateDiscountService`가 제대로 동작하려면 Customer를 찾는 기능도 구현해야 한다.
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
&nbsp;`CalculateDiscountService`를 테스트하려면 `CustomerRepository`와 `RuleDiscounter`를 구현한 객체가 필요하다.
만약 `CalculateDiscountService`가 저수준 모듈에 직접 의존했다면 저수준 모듈이 만들어지기까지 테스트를 할 수가 없었겠지만 `CustomerRepository`와 `RuleDiscounter`는 인터페이스이므로 대용 객체를 사용해서 테스트를 진행할 수 있다.

&nbsp;아래 코드는 대용 객체를 사용해서 Customer가 존재하지 않는 경우 Exception이 발생하는지 검증하는 테스트 코드이다.
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

&nbsp;그래서 위 테스트 코드는 `CustomerRepository`와 `RuleDiscounter`의 실제 구현 클래스가 없어도 `CalculateDiscountService`를 테스트 할 수 있다.

&nbsp;결과적으로, DIP를 이용해서 고수준 모듈이 저수준 모듈에 의존하지 않도록 했기에 대용 객체를 사용해서 거의 모든 상황을 테스트 할 수 있다.

### 🏛️ DIP 주의사항
&nbsp;DIP를 잘못 생각하면 단순히 인터페이스와 구현 클래스를 분리하는 정도로 받아들일 수 있다. 
**DIP의 핵심**은 <ins>고수준 모듈이 저수준 모듈에 의존하지 않도록 하기 위함</ins>인데 DIP를 적용한 결과 구조만 보고 아래 그림과 같이 저수준 모듈에서 인터페이스를 추출하는 경우가 있다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/incorrect_dip.png" width="500" height="300"/>

&nbsp;위 그림은 잘못된 구조인데, 도메인 영역이 구현 기술을 다루는 인프라스트럭처 영역에 의존하고 있다. 즉, 여전히 고수준 모듈이 저수준 모듈에 의존하고 있는 것이다.
`RuleEngine` 인터페이스는 고수준 모듈인 도메인 관점이 아니라 룰 엔진이라는 저수준 모듈 관점에서 도출한 것이다.

<ins>DIP를 적용할 때 하위 기능을 추상화한 인터페이스는 고수준 모듈 관점에서 도출한다.</ins>
`CalculateDiscountService` 입장에서 봤을 때 할인 금액을 구하기 위해 룰 엔진을 사용하는지, 직접 연산하는지 여부는 중요하지 않다.
단지 규칙에 따라 할인 금액을 계산한다는 것이 중요할 뿐.
즉, '할인 금액 계산'을 추상화한 인터페이스는 저수준 모듈이 아닌 고수준 모듈에 위치한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/correct_dip.png" width="500" height="300"/>

### 🏛️ DIP와 아키텍처
&nbsp;인프라스트럭처 영역은 구현 기술을 다루는 저수준 모듈이고 응용 영역과 도메인 영역은 고수준 모듈이다.
인프라스트럭처 계층의 가장 하단에 위치하는 계층형 구조와 달리 아키텍처에 DIP를 적용하면 아래 그림과 같이 인프라스트럭처 영역이 응용 영역과 도메인 영역에 의존(상속)하는 구조가 된다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_architecture.png" width="300" height="400"/>

&nbsp;인프라스트럭처에 위치한 클래스가 도메인이나 응용 영역에 정의한 인터페이스를 상속받아 구현하는 구조가 되므로 도메인과 응용 영역에 대한 영향을 주지 않거나 최소화하면서 구현 기술을 변경 가능하다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_structure_final.jpg" width="500" height="300"/>

&nbsp;인프라스트럭처 영역의 `EmailNotifier`클래스는 응용 영역의 `Notifier`인터페이스를 상속받고 있다.
주문 시 통지 방식에 SMS를 추가해야 한다는 요구사항이 들어왔을 경우 응용 영역의 `OrderService`는 변경할 필요가 없다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/dip_structure_modify_impl.jpg" width="500" height="300"/>

&nbsp;요구사항이 들어왔을 때 두 통지 방식을 함께 제공하는 `Notifier` 구현 클래스를 인프라스트럭처 영역에 추가하면 된다.
비슷하게 Mybatis 대신 JPA를 구현 기술로 사용하고 싶다면 JPA 이용한 `OrderRepository` 구현 클래스를 인프라스트럭처 영역에 추가하면 된다.

---

## 3. 도메인 영역의 주요 구성요소

도메인 영역의 모델은 도메인의 주요 개념을 표현하며 핵심이 되는 로직을 구현한다. 
도메인 영역의 주요 구성요소는 **엔티티**와 **밸류 타입**이다.

- **엔티티 ENTITY** : <ins>고유의 식별자를 갖는 객체로 자신의 라이프사이클을 갖는다.</ins> 주문(Order), 회원(Member), 상품(Product)과 같이 도메인의 고유한 개념을 표현한다. 도메인 모델의 데이터를 포함하며 해당 데이터와 관련된 기능을 함께 제공한다.
- **밸류 VALUE** : <ins>고유의 식별자를 갖지 않는 객체로 주로 개념적으로 하나인 도매인 객체의 속성을 표현할 때 사용한다.</ins> 엔티티의 속성으로 사용될 뿐만 아니라 다른 밸류 타입의 속성으로도 사용될 수 있다.
- **애그리거트 AGGREGATE** : <ins>애그리거트는 관련된 엔티티와 밸류 객체를 개념적으로 하나로 묶은 것이다.</ins>
- **리포지터리 REPOSITORY** : <ins>도메인 모델의 영속성을 처리한다.</ins>
- **도메인 서비스 DOMAIN SERVICE** : <ins>특정 엔티티에 속하지 않은 도메인 로직을 제공한다.</ins> '할인 금액 계산'은 상품, 쿠폰, 회원 등급 등 다양한 조건을 이용해서 구현하게 되는데, 이렇게 도메인 로직이 여러 엔티티와 밸류를 필요로 할 경우 도메인 서비스에서 로직을 구현한다.

### 🏛️ 엔티티와 밸류

&nbsp;저자분이 신입 시절 처음 도메인 모델을 만들 때, DB 테이블의 엔티티와 도메인 모델의 엔티티를 구분하지 못해 거의 동일하게 만들곤 했다고 한다.
그러나 경력을 더할수록 도메인 모델에 대한 이해가 쌓이면서 <ins>실제 도메인 모델의 엔티티와 DB 관계형 모델의 엔티티는 같은 것이 아님을 갈게 되었다고 한다.</ins>

&nbsp;이 두 모델의 가장 큰 차이점은 <ins>도메인 모델의 엔티티는 데이터와 도메인 기능을 함께 제공한다는 점이다.</ins>
예를 들어, 주문을 나타내는 엔티티는 주문과 관련된 데이터와 배송지 주소 변경을 위한 기능을 함께 제공한다.

```java
public class Order {
    // 주문 도메인 모델의 데이터
    private OrderNo number;
    private Orderer orderer;
    private ShippingInfo shippingInfo;
    ...
    
    // 도메인 모델 엔티티는 도메인 기능도 함께 제공
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        ...
    }
}
```
&nbsp;***도메인 모델의 엔티티***는 단순히 데이터를 담고 있는 데이터 구조가 아니라 <ins>데이터와 함께 기능을 제공하는 객체</ins>이다.
도메인 관점에서 기능을 구현하고 기능 구현을 캡슐화해서 데이터가 임의로 변경되는 것을 막는다. 

또 다른 차이점은 <ins>도메인 모델의 엔티티는 두 개 이상의 데이터가 개념적으로 하나인 경우 밸류 타입을 이용해서 표현할 수 있다는 것이다.</ins>
위 코드의 `Orderer`는 주문자를 표현하는 객체이며 밸류 타입으로 주문자의 이름과 이메일 데이터를 포함할 수 있다.
```java
public class Orderer {
    private String name;
    private String email;
    ...
}
```

#### 💡 밸류는 불변으로 구현하는 것을 권장한다.
&nbsp;이는 엔티티의 밸류 타입 데이터를 변경할 때 객체 자체를 완전히 교체한다는 것을 의미한다.
예를 들어, 배송지 정보를 변경하는 코드는 기존 객체의 값을 변경하지 않고 다음과 같이 새로운 객체를 필드에 할당한다.

```java
public class Order {
    private ShippingInfo shippingInfo;
    ...
  
    // 도메인 모델 엔티티는 도메인 기능도 함께 제공
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        checkShippingInfoChangeable();
        setShippingInfo(newShippingInfo);
    }
    
    private void setShippingInfo(ShippingInfo newShippingInfo) {
        if(newShippingInfo == null) throw new IllegalArgumentException();
        // 밸류 타입의 데이터를 변경할 때는 새로운 객체로 교체한다.
        this.shippingInfo = newShippingInfo;
    }
}
```

### 🏛 애그리거트

&nbsp;도메인이 커질수록 개발할 도메인 모델도 커지면서 많은 엔티티와 밸류가 출현한다. 이에 따라 엔티티와 밸류 개수가 많아지면서 모델은 더 복잡해진다.
️
&nbsp;도메인 모델이 복잡해지면 개발자가 전체 구조가 아닌 한 개 엔티티와 밸류에만 집중하게 되는 경우가 발생한다.
이때 상위 모델을 관리하기보다 개별 요소에만 초점을 맞추다 보면 큰 수준에서 모델을 이해하지 못해 큰 틀에서 모델을 관리할 수 없는 상황에 빠질 수 있다.

&nbsp;도메인 모델을 볼 때, <ins>개별 객체뿐만 아니라 상위 수준에서 모델을 볼 수 있어야 전체 모델의 관계와 개별 모델을 이해하는데 도움이 된다.
도메인 모델에서 전체 구조를 이해하는데 도움이 되는 것이 바로 **애그리거트(AGGREGATE)** 이다.</ins>

&nbsp;애그리거트는 관련 객체를 하나로 묶은 군집이다. 
애그리거트를 사용하면 개별 객체가 아닌 관련 객체를 묶어서 객체 군집 단위로 모델을 바라볼 수 있게 되는데, 개별 객체 간의 관계가 아닌 애그리거트 간의 관계로 도메인 모델을 이해하고 구현할 수 있게 된다.

&nbsp;애그리거트는 군집에 속한 객체들을 관리하는 <ins>**루트 엔티티**</ins>를 갖는다.
**루트엔티티**는 <ins>애그리거트에 속해 있는 엔티티와 밸류 객체를 이용해서 애그리거트가 구현해야 할 기능을 제공한다.</ins>

&nbsp;애그리거트를 사용하는 코드는 애그리거트 루트가 제공하는 기능을 실행하고 애그리거트 루트를 통해서 간접적으로 애그리거트 내의 다른 엔티티나 밸류 객체에 접근하게 된다.
이는 애그리거트의 내부 구현을 숨겨서 애그리거트 단위로 구현을 캡슐화할 수 있도록 돕는다.

대표적인 예로 주문 애그리거트를 알아보면,

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/order_aggregate.jpg" width="500" height="400"/>

&nbsp;애그리거트 루트인 `Order`는 주문 도메인 로직에 맞게 애그리거트의 상태를 관리한다. 
예를 들어, `Order`의 배송지 정보 변경 기능은 배송지를 변경할 수 있는지 확인한 뒤에 배송지 정보를 변경한다.

```java
public class Order {
    ...
    public void changeShippingInfo(ShippingInfo newInfo) {
        checkShippingInfoChangeable(); // 배송지 변경 가능 여부 확인
        this.shippingInfo = newInfo;
    }
    
    private void checkShippingInfoChangeable() {
        ... 배송지 정보를 변경할 수 있는지 여부를 확인하는 도메인 규칙 구현 
    }
}
```

&nbsp;주문 애그리거트는 `Order`를 통하지 않고 `ShippingInfo`를 변경할 수 있는 방법을 제공하지 않는다. 
즉, 배송지를 변경하려면 루트 엔티티인 `Order`를 사용해야 하므로 배송지 정보를 변경할 때에는 `Order`가 구현한 도메인 로직을 항상 따르게 된다.

&nbsp;애그리거트를 구현할 때는 고려할 것이 많다. 
애그리거트를 어떻게 구성했느냐에 따라 구현이 복잡해지기도 하고 트랜잭션 범위가 달라지기도 한다.
또한 선택한 구현 기술에 따라 애그리거트 구현에 제약이 생기기도 한다.

### 🏛️ 리포지터리

&nbsp;도메인 객체를 지속적으로 사용하려면 RDBMS, NoSQL, 로컬 파일과 같은 물리적인 저장소에 도매인 객체를 보관해야 하는데, 이를 위한 도메인 모델이 리포지터리(repository)이다.
엔티티나 밸류가 요구사항에서 도출되는 도메인 모델이라면 리포지터리는 구현을 위한 도메인 모델이다.

&nbsp;리포지터리는 애그리거트 단위로 도메인 객체를 저장하고 조회하는 기능을 정의한다.

```java
public interface OrderRepository {
    public Order findByNumber(OrderNumber number);
    // 애그리거트 단위로 저장/삭제
    public void save(Order order);
    public void delete(Order order);
}
```
&nbsp;`OrderRepository`의 메서드를 보면 대상을 찾고 저장하는 단위가 애그리거트 루트인 `Order`이다. 
`Order`는 애그리거트에 속한 모든 객체를 포함하고 있으므로 결과적으로 애그리거트 단위로 저장하고 조회한다.

&nbsp;도메인 모델을 사용해야 하는 코드는 리포지터리를 통해서 도메인 객체를 구한 뒤에 도메인 객체의 기능을 실행하게 된다.
예를 들어, 주문 취소 기능을 제공하는 응용 서비스는 아래 코드처럼 `OrderRepository`를 이용해서 `Order`객체를 구하고 해당 기능을 실행한다.

```java
public class CancelOrderService {
    private OrderRepository orderRepository;
    
    public void cancel(OrderNumber number) {
        Order order = orderRepository.findByNumber(number);
        if(order == null) throw new NoOrderException(number);
        order.cancel();
    }
    
    ... DI 등의 방식으로 OrderRepository 객체 전달
}
```

&nbsp;도메인 모델 관점에서 `OrderRepository`는 도메인 객체를 영속화하는데 필요한 기능을 추상화한 것으로 고수준 모듈에 속한다. 
기반 기술을 이용해서 `OrderRepository`를 구현한 클래스는 저수준 모듈로 인프라스트럭처 영역에 속한다.

&nbsp; 전체 모듈 구조를 그려보면, 

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/overall_module_structure.png" width="400" height="500"/>

&nbsp;응용 서비스는 의존 주입과 같은 방식을 사용해서 실제 리포지터리 구현 객체에 접근한다.
스프링 프레임워크를 사용한다면 아래 코드와 비슷한 방식으로 리포지터리 구현 객체를 주입할 것이다.

```java
@Configuration
public class OrderServiceConfig { // 응용 서비스 영역 설정
    @Autowired
    private OrderRepository orderRepository;
    
    @Bean
    public CancelOrderService cancelOrderService() {
        return new CancelOrderService(orderRepository);
    }
}
```
```java
@Configuration
public class RepositoryConfig { // 인프라스트럭처 영역 설정
    @Bean 
    public JpaOrderRepository orderRepository() {
        return new JpaOrderRepository();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean emf() {
        ...
    }
}
```

&nbsp;응용 서비스와 리포지터리는 밀접한 연관이 있는 이유는 아래와 같다.
* 응용 서비스는 필요한 도메인 객체를 구하거나 저장할 때 리포지터리를 사용한다.
* 응용 서비스는 트랜잭션을 관리하는데, 트랜잭션 처리는 리포지터리 구현 기술에 영향을 받는다.

&nbsp;리포지터리의 사용 주체가 응용 서비스이기 때문에 리포지터리는 응용 서비스가 필요로 하는 메서드를 제공한다.
가장 기본이 되는 메서드는 아래의 두 가지이다.
* 애그리거트를 저장하는 메서드
* 애그리거트 루트 식별자로 애그리거트를 조회하는 메서드

```java
public interface SomeRepository {
    void save(Some some);
    Some findById(SomeId id);
}
```

### 🏛️ 요청 처리 흐름

&nbsp;사용자가 애플리케이션에 기능 실행을 요청하면 그 요청을 처음 받는 영역은 **표현 영역**이다.
스프링 MVC를 사용해서 웹 애플리케이션을 구현했다면 <ins>컨트롤러가 사용자의 요청을 받아 처리</ins>하게 된다.

&nbsp;<ins>표현 영역은 사용자가 전송한 데이터 형식이 올바른지 검사하고 문제가 없다면 데이터를 이용해서 응용 서비스에 기능 실행을 위임한다.</ins>
이 때 표현 영역은 사용자가 전송한 데이터를 응용 서비스가 요구하는 형식으로 변환해서 전달한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/request_processing_flow.jpg" width="500" height="400"/>

&nbsp;**응용서비스**는 도메인 모델을 이용해서 기능을 구현한다. 기능 구현에 필요한 도메인 객체를 리포지터리에서 가져와 실행하거나 신규 도메인 객체를 생성해서 리포지터리에 저장한다.

&nbsp; 도메인의 상태를 변경하는 응용 서비스는 변경 상태가 물리 저장소에 반영되도록 트랜잭션을 관리해야 한다. 
아래는 스프링이 제공하는 트랜잭션 관리 기능을 이용해 트랜잭션 처리를 확인했다.

```java
public class CancelOrderService {
    private OrderRepository orderRepository;
    
    @Transaction    // 응용 서비스는 트랜잭션을 관리한다.
    public void cancel(OrderNumber number) {
        Order order = orderRepository.findByNumber(number);
        if(order == null) throw new NoOrderException(number);
        order.cancel();
    }
    ...
}
```

---

## 4. 인프라스트럭처

### 🏛️인프라스트럭처 개요

&nbsp;인프라스트럭처는 표현 영역, 응용 영역, 도메인 영역을 지원한다. 
도메인 객체의 영속성 처리, 트랜잭션, SMTP 클라이언트, REST 클라이언트 등 다른 영역에서 필요로 하는 프레임워크, 구현 기술, 보조 기능을 지원한다.

&nbsp;<ins>DIP에서 확인한 것처럼 도메인 영역과 응용 영역에서 인프라스트럭처의 기능을 직접 사용하는 것보다 이 두 영역에 정의한 인터페이스를 인프라스트럭처 영역에서 구현하는 것이 시스템을 더 유연하고 테스트하기 쉽게 만들어준다.</ins>

&nbsp;하지만, 무조건 인프라스트럭처에 대한 의존을 없애는 것이 좋은 것은 아니다.
예를 들어, 스프링의 경우 응용 서비스는 트랜잭션 처리를 위해 `@Transactional`을 사용하는 것이 편리하다. 
영속성 처리를 위해 JPA를 사용할 경우 `@Entity` 나 `@Table`과 같은 전용 어노테이션을 도메인 모델 클래스에 사용하는 것이 XML 매핑 설정을 이용하는 것보다 편리하다.

&nbsp;<ins>구현의 편리함</ins>은 DIP가 주는 다른 장점(변경의 유연함, 테스트 쉬움)만큼 중요하기 때문에
<ins>DIP의 장점을 해치지 않는 범위에서 응용 영역과 도메인 영역에서 구현 기술에 대한 의존을 가져가는 것이 현명하다.</ins> 
응용 영역과 도메인 영역이 인프라스트럭처에 대한 의존을 완전히 갖지 않도록 시도하는 것은 자칫 구현을 더 복잡하고 어렵게 만들 수 있다.

&nbsp;좋은 예가 Spring의 `@Transactional` 어노테이션이다. `@Transactional`을 사용하면 한 줄로 트랙잭션 처리를 할 수 있는데 코드에서 Spring에 대한 의존을 없애려면 복잡한 Spring 설정을 사용해야 한다.
의존은 없앴지만 특별히 테스트를 더 쉽게 하거나 유연함을 증가시켜주지 못한다. 
단지 설정만 복잡해지고 개발 시간만 늘어날 뿐이다.

&nbsp;<ins>표현 영역은 항상 인프라스트럭처 영역과 쌍을 이룬다.</ins> Spring MVC를 사용해서 웹 요청을 처리하면 Spring이 제공하는 MVC 프레임워크에 맞게 표현 영역을 구현해야 하고, 
Vert.x를 사용해서 REST API 서버를 구축하려면 Vert.x에 맞게 웹 요청 처리 부분을 구현해야 한다.

### 🏛️ 모듈 구성

&nbsp; 아키텍처의 각 영역은 별도 패키지에 위치한다. 
패키지 구성 규칙에 한 개의 정답만 존재하는 것은 아니지만 아래와 같이 영역별로 모듈이 위치할 패키지를 구성할 수 있을 것이다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/module_structure_1.png" width="500" height="400"/>

&nbsp;도메인이 크면 아래와 같이 하위 도메인으로 나누고 각 하위 도메인마다 별도 패키지를 구성한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/module_structure_2.png" width="500" height="400"/>

&nbsp; domain 모듈은 도메인에 속한 애그리거트를 기준으로 다시 패키지를 구성한다.
예를 들어, catalog 하위 도메인을 위한 도메인은 product 애그리거트와 category 애그리거트로 구성된다고 할 경우 아래와 같이 domain을 두 개의 하위 패키지로 구성할 수 있다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/module_structure_3.png" width="500" height="400"/>

&nbsp;각 애그리거트와 모델과 리포지터리는 같은 패키지에 위치시킨다.
예를 들어, 주문과 관련된 `Order`, `OrderLine`, `Orderer`, `OrderRepository` 등은 `com.myshop.order.domain` 패키지에 위치시킨다.

&nbsp;도메인이 복잡하면 도메인 모델과 도메인 서비스를 다음과 같이 별도 패키지에 위치시킬 수도 있다.
* `com.myshop.order.domain.order`: 애그리거트 위치
* `com.myshop.order.domain.order`: 도메인 서비스 위치

&nbsp;응용 서비스도 다음과 같이 도메인 별로 패키지를 구분할 수 있다.
* `com.myshop.catalog.application.product`
* `com.myshop.catalog.application.category`

&nbsp;모듈 구조를 얼마나 세분화해야 하는지에 대해 정해진 규칙은 없다. 단지, 한 패키지에 너무 많은 타입이 몰려서 코드를 찾을 때 불편한 정도만 아니면 된다.
개인적으로(저자)는 한 패키지에 가능하면 10개 미만으로 타입 개수를 유지하려고 노력한다. 이 개수가 넘어가면 모듈을 분리하는 시도를 해본다.










