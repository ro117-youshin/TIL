# CHAPTER 1. 도메인 모델 시작

> [DDD Start! 도메인 주도 설계 구현과 핵심 개념 익히기 - 최범균] 학습 후 기록
>
> 1. 도메인 모델<br>
  > 1-1. 도메인<br>
  > 1-2. 도메인 모델<br>
  > 1-3. 도메인 모델 패턴<br>
  > 1-4. 도메인 모델 도출<br>
> 2. 엔티티와 벨류
> 3. 도메인 용어

## 1. 도메인 모델

### 🏛️ 도메인

저는 저자분과 같이 책을 구매할 때 온라인 서점을 자주 이용한다. 개발자 입장에서 온라인 서점은 구현해야 할 소프트웨어의 대상이 된다. 온라인 서점 소프트웨어는 온라인으로 책을 판매하는 데 필요한 구매, 결제, 배송추적 등의 기능을 제공해야 한다.
이 때, 온라인 서점은 소프트웨어로 해결하고자 하는 문제 영역, 즉 <ins>도메인(domain)</ins>에 해당한다.

한 도메인은 다시 하위 도메인으로 나눌 수 있다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/domain1.png" width="300" height="300"/>

예를 들어, 혜택 하위 도메인은 고객에게 제공할 쿠폰 목록을 제공하고 특별할인에 대한 정보를 제공한다. 한 하위 도메인은 다른 하위 도메인과 연동하여 완전한 기능을 제공한다. 예를 들어, 고객이 물건을 구매하면 주문, 결제, 배송, 혜택 하위 도메인의 기능이 엮이게 된다.

특정 도메인을 위한 소프트웨어라고 해서 도메인이 제공해야 하는 모든 기능을 구현하는 것은 아니다. 많은 온라인 쇼핑몰이 자체적으로 배송 시스템을 구축하기보다 외부 배송 업체의 시스템을 사용하고 배송추적에 필요한 기능만 일부 연동한다. 결제도 마찬가지로 직접 구현하기보다 결제 대행 업체를 이용해서 처리한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/domain2.png" width="400" height="300"/>

<ins>도메인 마다 고정된 하위 도메인이 존재하는 것은 아니다.</ins> 모든 온라인 쇼핑몰이 위의 그림과 같이 고객에게 특별할인 혜택을 제공하는 것은 아닌 것과 같다. 즉, 하위 도메인을 어떻게 구성할지 여부는 상황에 따라 달라진다.

---

### 🏛️ 도메인 모델

<ins>도메인 모델</ins>에는 다양한 정의가 존재하지만 <ins>기본적으로 특정 도메인을 개념적으로 표현한 것</ins>이다.

책에서는 객체 기반 주문 도메인 모델을 클래스 다이어그램을 통해서 주문(Order)모델을 객체 모델로 보여준다.

이 객체 기반 주문 도메인 모델을 통해서 즉, 도메인 모델을 사용하면 여러 관계자들이 동일한 모습으로 도메인을 이해하고 도메인 지식을 공유하는데 도움이 된다.

<ins>도메인을 이해하려면 도메인이 제공하는 기능과 도메인의 주요 데이터 구성을 파악해야 하는데, 이런 면에서 기능과 데이터를 함께 보여주는 객체 모델은 도메인을 모델링하기에 적합하다.</ins>

또한 상태 다이어그램을 이용해서 도메인 모델을 모델링 할 수 있다.

도메인 모델을 표현할 때 클래스 다이어그램이나 상태 다이어그램과 같은 UML 표기법만을 사용해야 하는 것은 아니다. 관계가 중요한 도메인이라면 그래프를 이용해서 도메인을 모델링 할 수 있다. 도메인을 이해하는데 도움이 된다면 표현 방식이 무엇인지는 중요하지 않다.

<ins>도메인 모델은 기본적으로 도메인 자체를 이해하기 위한 개념 모델이다.</ins> 개념 모델을 이용해서 바로 코드를 작성할 수 있는 것은 아니기에 구현 기술에 맞는 구현 모델이 따로 필요하다. 개념 모델과 구현 모델은 서로 다른 것이지만 구현 모델이 개념 모델을 최대한 따르도록 할 수는 있다. 예를 들어, 객체 기반 모델을 이용해서 도메인을 표현했다면 객체 지향 언어를 이용해서 개념 모델에 가깝게 구현할 수 있다.

### 💡 하위 도메인과 모델

> 도메인은 다수의 하위 도메인으로 구성된다. 각 하위 도메인이 다루는 영역은 서로 다르기 때문에 같은 용어라도 하위 도메인마다 의미가 달라질 수 있다.
예를 들어, 카탈로그 도메인의 상품이 상품 가격, 상세 내용을 담고 있는 정보를 의미한다면 배송 도메인의 상품은 고객에게 실제 배송되는 물리적인 상품을 의미한다.
도메인에 따라 용어의 의미가 결정되므로, 여러 하위 도메인을 하나의 다이어그램에 모델링하면 안 된다.
만약 도메인 모델을 구분하지 않고 하나의 다이어그램에 함께 표시한다고 가정한다면 '상품'은 카탈로그의 상품과 배송의 상품의 의미를 함께 제공하기에,
카탈로그 도메인에서의 상품을 제대로 이해하는데 방해가 된다.
모델의 각 구성요소는 특정 도메인을 한정할 때 비로소 의미가 완전해지기 때문에, 각 하위 도메인마다 별도로 모델을 만들어야 한다.
이는 카탈로그 하위 도메인 모델과 배송 하위 도메인 모델을 따로 만들어야 한다는 것을 뜻한다.
>

---

### 🏛️ 도메인 모델 패턴

일반적인 애플리케이션의 아키텍처는 아래의 그림과 같다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/applicationArchitecture.png" width="400" height="400"/>

각 계층의 역할은 다음과 같다.

- **사용자인터페이스(UI)** 또는 **표현(Presentation)** - 사용자의 요청을 처리하고 사용자에게 정보를 보여준다. 여기서 사용자는 소프트웨어를 사용하는 사람 뿐만 아니라 외부 시스템도 사용자가 될 수 있다.
- **응용(Aplication)** - 사용자가 요청한 기능을 실행한다. 업무 로직을 직접 구현하지 않으며 도메인 계층을 조합해서 기능을 실행한다.
- **도메인** - 시스템이 제공할 도메인의 규칙을 구현한다.
- **인프라스트럭처(Infrastructure)** - 데이터베이스나 메시징 시스템과 같은 외부 시스템과의 연동을 처리한다.

앞서 살펴본 도메인 모델이 도메인 자체를 이해하는데 필요한 개념 모델을 의미한다면, 지금 살펴볼 도메인 모델은 마틴 파울러가 쓴 [엔터프라이즈 애플리케이션 아키텍처 패턴(PEAA)] 책의 도메인 모델 패턴을 의미한다. **도메인 모델**은 <ins>아키텍처상의 도메인 계층을 객체 지향 기법으로 구현하는 패턴</ins>을 말한다.

도메인 계층은 도메인의 핵심 규칙을 구현한다. 주문 도메인의 경우 '출고 전에 배송지를 변경할 수 있다'는 규칙과 '주문 취소 배송전에만 할 수 있다'는 규칙을 구현한 코드가 도메인 계층에 위치하게 된다.

```java
public class Order {
    private OrderState state;
    private ShippingInfo shippingInfo;

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if(!isShippingChangeable()) {
            throw new IllegalStateException("can't change shipping in " + state);
        }
        this.shippingInfo = newShippingInfo;
    }
    private boolean isShippingChangeable() {
        return state == OrderState.PAYMENT_WATING ||
            state == OrderState.PREPARING;
    }
    ...
}

public enum OrderState {
    PAYMENT_WATING, PREPARING, SHIPPED, DELIVERING, DELIVERY_COMPLETED;
}

```

배송지 변경이 가능한지 여부를 판단할 규칙이 주문 상태와 다른 정보를 함께 사용한다면 배송지 변경 가능 여부 판단을 `OrderState`만으로 할 수 없으므로 구현을 `Order`에서 해야 할 것이다.
배송지 변경 가능 여부를 판단하는 기능이 `Order`에 있든, `OrderState`에 있든 중요한 점은 주문과 관련된 중요 업무 규칙을 주문 도메인 모델인 `Order`나 `OrderState`에서 구현한다는 점이다. 핵심 규칙을 구현한 코드는 도메인 모델에만 위치하기 때문에 규칙이 바뀌거나 규칙을 확장해야 할 때 다른 코드에 영향을 덜 주고 변경 내역을 모델에 반영할 수 있게 된다.

### 💡 도메인 모델이란

> '도메인 모델'이란 용어는 도메인 자체를 표현하는 개념적인 모델을 의미하지만, 도메인 계층을 구현할 때 사용하는 객체 모델을 언급할 때에도 '도메인 모델'이란 용어를 사용한다. 여기서는 도메인 계층의 객체 모델을 표현할 때 도메인 모델이라고 표현하고 있다.
>

### 💡 개념 모델과 구현 모델

> 개념 모델은 순수하게 문제를 분석한 결과물이다. 개념 모델은 데이터 베이스, 트랜잭션 처리, 성능, 구현 기술과 같은 것들을 고려하고 있지 않기 때문에 실제 코드를 작성할 때 개념 모델을 있는 그대로 사용할 수 없다. 그래서 개념 모델을 구현 가능한 형태의 모델로 전환하는 과정을 거치게 된다.
개념 모델을 만들 때 처음부터 완벽하게 도메인을 표현하는 모델을 만드는 시도를 할 수 있지만 실제로 이는 불가능에 가깝다. 소프트웨어를 개발하는 동안 개발자와 관계자들은 해당 도메인을 더 잘 이해하게 된다. 프로젝트 초기에 이해한 도메인 지식이 시간이 지나 새로운 통찰을 얻으면서 완전히 다른 의미로 해석되는 경우도 있다. 프로젝트 초기에 완벽한 도메인 모델을 만들더라도 결국 도메인에 대한 새로운 지식이 쌓이면서 모델을 보완하거나 수정하는 일이 발생한다.
따라서, 처음부터 완벽한 개념 모델을 만들기보다는 전반적인 개요를 알 수 있는 수준으로 개념 모델을 작성해야 한다. 프로젝트 초기에는 개요 수준의 개념 모델로 도메인에 대한 전체 윤곽을 이해하는데 집중하고, 구현하는 과정에서 개념 모델을 구현 모델로 점진적으로 발전시켜 나가야 한다.
>

---

### 🏛️ 도메인 모델 도출

도메인에 대한 이해 없이 코딩을 시작할 수 없다. 요구사항과 관련자와의 대화를 통해 도메인을 이해하고 이를 바탕으로 도메인 모델 초안을 만들어야 비로소 코드를 작성할 수 있다. 도메인 모델링 할 때, 기본이 되는 작업은 모델을 구성하는 핵심 구성요소, 규칙, 기능을 찾는 것이다. 이 과정은 요구사항에서 출발한다. 주문 도메인과 관련된 몇 가지 요구사항을 보자.

- 최소 한 종류 이상의 상품을 주문해야 한다.
- 한 상품을 한 개 이상 주문할 수 있다.
- 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.
- 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.
- 주문할 때 배송지 정보를 반드시 지정해야 한다.
- 배송지 정보는 받는 사람 이름, 전화번호, 주소로 구성된다.
- 출고를 하면 배송지 정보를 변경할 수 없다.
- 출고 전에 주문을 취소 할 수 있다.
- 고객이 결제를 완료하기 전에는 상품을 준비하지 않는다.

위 요구사항에서 알 수 있는 것은 주문은 아래의 네 기능을 제공한다는 것.

```java
public class Order {
    // 출고 상태로 변경하기
    public void changeShipped() { ... }
    // 배송지 정보 변경하기
    public void changeShippingInfo(ShippingInfo newShipping) { ... }
    // 주문 취소하기
    public void cancel() { ... }
    // 결제 완료로 변경하기
    public void completePayment() { ... }
}

```

다음 요구사항은 주문 항목이 어떤 데이터로 구성되는지 알려준다.

- 한 상품을 한 개 이상 주문할 수 있다.
- 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.

그렇다면 주문 항목을 표현하는 `OrderLine`은 적어도 주문할 상품, 상품의 가격, 구매 개수, 각 구매항목의 구매 가격을 포함하고 있어야 한다.

```java
public class OrderLine {
    private Product product;
    private int price;
    private int quantity;
    private int amounts;

    public OrderLine(Product product, int price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }
    private int calculateAmounts() {
        return price * quantity;
    }

    public int getAmounts() { ... }
    ...
}

```

다음 요구사항은 `Order`와 `OrderLine`과의 관계를 알려준다.

- 최소 한 종류 이상의 상품을 주문해야 한다.
- 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.

한 종류 이상의 상품을 주문할 수 있으므로 `Order`는 최소 한 개 이상의 `OrderLine`을 포함해야 한다. 또한, `OrderLine`으로부터 총 주문 금액을 구할 수 있다.

```java
public class Order {
    private List<OrderLine> orderLines;
    private int totalAmounts;

    public Order(List<OrderLine> orderLines) {
        setOrderLines(orderLines);
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if(orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void calculateTotalAmounts() {
        this.totalAmounts = new Money(orderLines.stream()
                .mapToInt(x -> x.getAmounts().getValue().sum();
    }

    ...
}

```

`Order`는 한 개 이상의 `OrderLine`을 가질 수 있으므로 `Order`를 생성할 때 `OrderLine`목록을 `List`로 전달한다. 생성자에서 호출하는 `setOrderLines()`메서드는 요구사항에 정의한 제약 조건을 검사한다. `verifyAtLeastOneOrMoreOrderLines()`메서드는 `OrderLine`이 한 개 이상 존재하는지 검사, `calculateTotalAmounts()`메서드는 총 주문 금액을 계산.

배송지 정보는 이름, 전화번호, 주소 데이터를 가지므로 `ShippingInfo`클래스를 아래와 같이 정의할 수 있다.

```java
public class ShippingInfo {
    private String receiverName;
    private String receiverPhoneNumber;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingZipcode;

    ... 생성자, getter
}

```

앞서 요구사항 중에 '주문할 때 배송지 정보를 반드시 지정해야 한다' 는 내용이 있다. 이는 `Order`를 생성할 때 `OrderLine`의 목록뿐만 아니라 `ShippingInfo`도 함께 전달해야 함을 의미한다. 이를 생성자에 반영한다.

```java
public class Order {
    private List<OrderLine> orderLines;
    private int totalAmounts;
    private ShippingInfo shippingInfo;

    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if(shippingInfo == null)
            throw new IllegalArgumentException("no ShippingInfo");

        this.shippingInfo = shippingInfo;
    }
    ...
}

```

생성자에서 호출하는 `setShippingInfo()`메서드는 `ShippingInfo`가 `null`이면 `Exception`이 발생하는데, 이렇게 함으로써 '배송지 정보 필수' 라는 도메인 규칙을 구현한다.

배송지 변경이나 주문 취소 기능은 출고 전에만 가능하다는 제약 규칙이 있으므로 이 규칙을 적용하기 위해 `chageShippingInfo()`와 `cancel()`은 `verifyNotYetShipped()`메서드를 먼저 실행한다.

```java
public class Order {
    private OrderState state;

    public void chageShippingInfo(ShippingInfo newShippingInfo) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
    }

    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }

    private void verifyNotYetShipped() {
        if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING)
            throw new IllegalStateException("already shipped");
    }

    ...
}

```

> 앞서 도메인 모델 패턴을 설명할 때에는 제약 조건 검사를 isShippingChangeable, 지금은 verifyNotYetShipped라는 이름으로 변경하였다. 이유는 그 사이에 도메인을 더 잘 알게 되었기 때문이다. 최초에는 배송지 정보 변경에 대한 제약 조건만 파악했기 때문에 '배송지 정보 변경 가능 여부 확인'을 의미하는 isShippingChangeable 이름을 사용했다. 그런데, 요구사항을 분석하면서 배송지 정보 변경과 주문 취소가 둘 다 '출고 전에 가능'하다는 제약 조건을 알게 되었고 이를 반영하여 메서드 이름을 verifyNotYetShipped로 변경했다.
>