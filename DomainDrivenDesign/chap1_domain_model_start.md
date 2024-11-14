# CHAPTER 1. 도메인 모델 시작

> [DDD Start! 도메인 주도 설계 구현과 핵심 개념 익히기 - 최범균] 학습 후 기록

> 1. 도메인 모델<br>
  > 1-1. 도메인<br>
  > 1-2. 도메인 모델<br>
  > 1-3. 도메인 모델 패턴<br>
  > 1-4. 도메인 모델 도출<br>
> 2. 엔티티와 벨류<br>
  > 2-1. 엔티티와 밸류<br>
  > 2-2. 엔티티<br>
  > 2-3. 엔티티의 식별자 생성<br>
  > 2-4. 밸류 타입<br>
  > 2-5. 엔티티 식별자와 밸류 타입<br>
  > 2-6. 도메인 모델에 set 메서드 넣지 않기<br>
> 3. 도메인 용어

## 1. 도메인 모델

### 🏛️ 도메인

&nbsp;저는 저자분과 같이 책을 구매할 때 온라인 서점을 자주 이용한다. 
개발자 입장에서 온라인 서점은 구현해야 할 소프트웨어의 대상이 된다. 온라인 서점 소프트웨어는 온라인으로 책을 판매하는 데 필요한 구매, 결제, 배송추적 등의 기능을 제공해야 한다.
이 때, 온라인 서점은 소프트웨어로 해결하고자 하는 문제 영역, 즉 <ins>도메인(domain)</ins>에 해당한다.

한 도메인은 다시 하위 도메인으로 나눌 수 있다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/domain1.png" width="300" height="300"/>

&nbsp;예를 들어, 혜택 하위 도메인은 고객에게 제공할 쿠폰 목록을 제공하고 특별할인에 대한 정보를 제공한다. 
한 하위 도메인은 다른 하위 도메인과 연동하여 완전한 기능을 제공한다. 예를 들어, 고객이 물건을 구매하면 주문, 결제, 배송, 혜택 하위 도메인의 기능이 엮이게 된다.

&nbsp;특정 도메인을 위한 소프트웨어라고 해서 도메인이 제공해야 하는 모든 기능을 구현하는 것은 아니다. 
많은 온라인 쇼핑몰이 자체적으로 배송 시스템을 구축하기보다 외부 배송 업체의 시스템을 사용하고 배송추적에 필요한 기능만 일부 연동한다. 
결제도 마찬가지로 직접 구현하기보다 결제 대행 업체를 이용해서 처리한다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/domain2.png" width="400" height="300"/>

&nbsp;<ins>도메인 마다 고정된 하위 도메인이 존재하는 것은 아니다.</ins> 
모든 온라인 쇼핑몰이 위의 그림과 같이 고객에게 특별할인 혜택을 제공하는 것은 아닌 것과 같다. 
즉, 하위 도메인을 어떻게 구성할지 여부는 상황에 따라 달라진다.

---

### 🏛️ 도메인 모델

&nbsp;<ins>도메인 모델</ins>에는 다양한 정의가 존재하지만 <ins>기본적으로 특정 도메인을 개념적으로 표현한 것</ins>이다.

&nbsp;책에서는 객체 기반 주문 도메인 모델을 클래스 다이어그램을 통해서 주문(Order)모델을 객체 모델로 보여준다.

&nbsp;이 객체 기반 주문 도메인 모델을 통해서 즉, 도메인 모델을 사용하면 여러 관계자들이 동일한 모습으로 도메인을 이해하고 도메인 지식을 공유하는데 도움이 된다.

&nbsp;<ins>도메인을 이해하려면 도메인이 제공하는 기능과 도메인의 주요 데이터 구성을 파악해야 하는데, 이런 면에서 기능과 데이터를 함께 보여주는 객체 모델은 도메인을 모델링하기에 적합하다.</ins>

&nbsp;또한 상태 다이어그램을 이용해서 도메인 모델을 모델링 할 수 있다.

&nbsp;도메인 모델을 표현할 때 클래스 다이어그램이나 상태 다이어그램과 같은 UML 표기법만을 사용해야 하는 것은 아니다. 
관계가 중요한 도메인이라면 그래프를 이용해서 도메인을 모델링 할 수 있다. 도메인을 이해하는데 도움이 된다면 표현 방식이 무엇인지는 중요하지 않다.

&nbsp;<ins>도메인 모델은 기본적으로 도메인 자체를 이해하기 위한 개념 모델이다.</ins> 
개념 모델을 이용해서 바로 코드를 작성할 수 있는 것은 아니기에 구현 기술에 맞는 구현 모델이 따로 필요하다. 
개념 모델과 구현 모델은 서로 다른 것이지만 구현 모델이 개념 모델을 최대한 따르도록 할 수는 있다. 
예를 들어, 객체 기반 모델을 이용해서 도메인을 표현했다면 객체 지향 언어를 이용해서 개념 모델에 가깝게 구현할 수 있다.

### 💡 하위 도메인과 모델

> &nbsp;도메인은 다수의 하위 도메인으로 구성된다. 각 하위 도메인이 다루는 영역은 서로 다르기 때문에 같은 용어라도 하위 도메인마다 의미가 달라질 수 있다.
예를 들어, 카탈로그 도메인의 상품이 상품 가격, 상세 내용을 담고 있는 정보를 의미한다면 배송 도메인의 상품은 고객에게 실제 배송되는 물리적인 상품을 의미한다.
도메인에 따라 용어의 의미가 결정되므로, 여러 하위 도메인을 하나의 다이어그램에 모델링하면 안 된다.
만약 도메인 모델을 구분하지 않고 하나의 다이어그램에 함께 표시한다고 가정한다면 '상품'은 카탈로그의 상품과 배송의 상품의 의미를 함께 제공하기에,
카탈로그 도메인에서의 상품을 제대로 이해하는데 방해가 된다.
모델의 각 구성요소는 특정 도메인을 한정할 때 비로소 의미가 완전해지기 때문에, 각 하위 도메인마다 별도로 모델을 만들어야 한다.
이는 카탈로그 하위 도메인 모델과 배송 하위 도메인 모델을 따로 만들어야 한다는 것을 뜻한다.

---

### 🏛️ 도메인 모델 패턴

일반적인 애플리케이션의 아키텍처는 아래의 그림과 같다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/applicationArchitecture.png" width="400" height="400"/>

각 계층의 역할은 다음과 같다.

- **사용자인터페이스(UI)** 또는 **표현(Presentation)** - 사용자의 요청을 처리하고 사용자에게 정보를 보여준다. 여기서 사용자는 소프트웨어를 사용하는 사람 뿐만 아니라 외부 시스템도 사용자가 될 수 있다.
- **응용(Aplication)** - 사용자가 요청한 기능을 실행한다. 업무 로직을 직접 구현하지 않으며 도메인 계층을 조합해서 기능을 실행한다.
- **도메인** - 시스템이 제공할 도메인의 규칙을 구현한다.
- **인프라스트럭처(Infrastructure)** - 데이터베이스나 메시징 시스템과 같은 외부 시스템과의 연동을 처리한다.

&nbsp;앞서 살펴본 도메인 모델이 도메인 자체를 이해하는데 필요한 개념 모델을 의미한다면, 지금 살펴볼 도메인 모델은 마틴 파울러가 쓴 [엔터프라이즈 애플리케이션 아키텍처 패턴(PEAA)] 책의 도메인 모델 패턴을 의미한다. **도메인 모델**은 <ins>아키텍처상의 도메인 계층을 객체 지향 기법으로 구현하는 패턴</ins>을 말한다.

&nbsp;도메인 계층은 도메인의 핵심 규칙을 구현한다. 주문 도메인의 경우 '출고 전에 배송지를 변경할 수 있다'는 규칙과 '주문 취소 배송전에만 할 수 있다'는 규칙을 구현한 코드가 도메인 계층에 위치하게 된다.

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

&nbsp;배송지 변경이 가능한지 여부를 판단할 규칙이 주문 상태와 다른 정보를 함께 사용한다면 배송지 변경 가능 여부 판단을 `OrderState`만으로 할 수 없으므로 구현을 `Order`에서 해야 할 것이다.
배송지 변경 가능 여부를 판단하는 기능이 `Order`에 있든, `OrderState`에 있든 중요한 점은 주문과 관련된 중요 업무 규칙을 주문 도메인 모델인 `Order`나 `OrderState`에서 구현한다는 점이다. 핵심 규칙을 구현한 코드는 도메인 모델에만 위치하기 때문에 규칙이 바뀌거나 규칙을 확장해야 할 때 다른 코드에 영향을 덜 주고 변경 내역을 모델에 반영할 수 있게 된다.

### 💡 도메인 모델이란

> &nbsp;'도메인 모델'이란 용어는 도메인 자체를 표현하는 개념적인 모델을 의미하지만, 도메인 계층을 구현할 때 사용하는 객체 모델을 언급할 때에도 '도메인 모델'이란 용어를 사용한다. 여기서는 도메인 계층의 객체 모델을 표현할 때 도메인 모델이라고 표현하고 있다.


### 💡 개념 모델과 구현 모델

> &nbsp;개념 모델은 순수하게 문제를 분석한 결과물이다. 개념 모델은 데이터 베이스, 트랜잭션 처리, 성능, 구현 기술과 같은 것들을 고려하고 있지 않기 때문에 실제 코드를 작성할 때 개념 모델을 있는 그대로 사용할 수 없다. 그래서 개념 모델을 구현 가능한 형태의 모델로 전환하는 과정을 거치게 된다.
개념 모델을 만들 때 처음부터 완벽하게 도메인을 표현하는 모델을 만드는 시도를 할 수 있지만 실제로 이는 불가능에 가깝다. 소프트웨어를 개발하는 동안 개발자와 관계자들은 해당 도메인을 더 잘 이해하게 된다. 프로젝트 초기에 이해한 도메인 지식이 시간이 지나 새로운 통찰을 얻으면서 완전히 다른 의미로 해석되는 경우도 있다. 프로젝트 초기에 완벽한 도메인 모델을 만들더라도 결국 도메인에 대한 새로운 지식이 쌓이면서 모델을 보완하거나 수정하는 일이 발생한다.
따라서, 처음부터 완벽한 개념 모델을 만들기보다는 전반적인 개요를 알 수 있는 수준으로 개념 모델을 작성해야 한다. 프로젝트 초기에는 개요 수준의 개념 모델로 도메인에 대한 전체 윤곽을 이해하는데 집중하고, 구현하는 과정에서 개념 모델을 구현 모델로 점진적으로 발전시켜 나가야 한다.


---

### 🏛️ 도메인 모델 도출

&nbsp;도메인에 대한 이해 없이 코딩을 시작할 수 없다. 요구사항과 관련자와의 대화를 통해 도메인을 이해하고 이를 바탕으로 도메인 모델 초안을 만들어야 비로소 코드를 작성할 수 있다. 도메인 모델링 할 때, 기본이 되는 작업은 모델을 구성하는 핵심 구성요소, 규칙, 기능을 찾는 것이다. 이 과정은 요구사항에서 출발한다. 주문 도메인과 관련된 몇 가지 요구사항을 보자.

- 최소 한 종류 이상의 상품을 주문해야 한다.
- 한 상품을 한 개 이상 주문할 수 있다.
- 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.
- 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.
- 주문할 때 배송지 정보를 반드시 지정해야 한다.
- 배송지 정보는 받는 사람 이름, 전화번호, 주소로 구성된다.
- 출고를 하면 배송지 정보를 변경할 수 없다.
- 출고 전에 주문을 취소 할 수 있다.
- 고객이 결제를 완료하기 전에는 상품을 준비하지 않는다.

&nbsp;위 요구사항에서 알 수 있는 것은 주문은 아래의 네 기능을 제공한다는 것.

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

&nbsp;다음 요구사항은 주문 항목이 어떤 데이터로 구성되는지 알려준다.

- 한 상품을 한 개 이상 주문할 수 있다.
- 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.

&nbsp;그렇다면 주문 항목을 표현하는 `OrderLine`은 적어도 주문할 상품, 상품의 가격, 구매 개수, 각 구매항목의 구매 가격을 포함하고 있어야 한다.

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

&nbsp;다음 요구사항은 `Order`와 `OrderLine`과의 관계를 알려준다.

- 최소 한 종류 이상의 상품을 주문해야 한다.
- 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.

&nbsp;한 종류 이상의 상품을 주문할 수 있으므로 `Order`는 최소 한 개 이상의 `OrderLine`을 포함해야 한다. 또한, `OrderLine`으로부터 총 주문 금액을 구할 수 있다.

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

&nbsp;`Order`는 한 개 이상의 `OrderLine`을 가질 수 있으므로 `Order`를 생성할 때 `OrderLine`목록을 `List`로 전달한다. 생성자에서 호출하는 `setOrderLines()`메서드는 요구사항에 정의한 제약 조건을 검사한다. `verifyAtLeastOneOrMoreOrderLines()`메서드는 `OrderLine`이 한 개 이상 존재하는지 검사, `calculateTotalAmounts()`메서드는 총 주문 금액을 계산.

&nbsp;배송지 정보는 이름, 전화번호, 주소 데이터를 가지므로 `ShippingInfo`클래스를 아래와 같이 정의할 수 있다.

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

&nbsp;앞서 요구사항 중에 '주문할 때 배송지 정보를 반드시 지정해야 한다' 는 내용이 있다. 이는 `Order`를 생성할 때 `OrderLine`의 목록뿐만 아니라 `ShippingInfo`도 함께 전달해야 함을 의미한다. 이를 생성자에 반영한다.

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

&nbsp;생성자에서 호출하는 `setShippingInfo()`메서드는 `ShippingInfo`가 `null`이면 `Exception`이 발생하는데, 이렇게 함으로써 '배송지 정보 필수' 라는 도메인 규칙을 구현한다.

&nbsp;배송지 변경이나 주문 취소 기능은 출고 전에만 가능하다는 제약 규칙이 있으므로 이 규칙을 적용하기 위해 `chageShippingInfo()`와 `cancel()`은 `verifyNotYetShipped()`메서드를 먼저 실행한다.

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

---

## 2. 엔티티와 밸류 타입

### 🏛️ 엔티티와 밸류
&nbsp;도출한 모델은 크게 엔티티(Entity)와 밸류(value)로 구분할 수 있다. 
엔티티와 밸류를 제대로 구분해야 도메인을 올바르게 설계하고 구현할 수 있다. 
그래서 이 둘의 차이를 명확하게 이해하는 것이 도메인을 구현하는데 있어 중요하다.

### 🏛️ 엔티티
&nbsp;<ins>엔티티의 가장 큰 특징은 식별자를 갖는다는 것.</ins> 
식별자는 엔티티 객체마다 고유해서 <ins>각 엔티티는 서로 다른 식별자를 갖는다.</ins>

&nbsp;엔티티의 식별자는 바뀌지 않는다. 엔티티를 생성하고 엔티티의 속성을 바꾸고 엔티티를 삭제할 때까지 식별자는 유지된다.

&nbsp;엔티티의 식별자는 바뀌지 않고 고유하기 때문에 두 엔티티 객체의 식별자가 같으면 두 엔티티는 같다고 판단할 수 있다. 
엔티티를 구현한 클래스는 다음과 같이 식별자를 이용해서 `equals()` 메서드와 `hashCode()` 메서드를 구현할 수 있다.

```java
public class Order {
    private String orderNumber;
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(obj.getClass() != Order.class) return false;
        Order order = (Order)obj;
        if(this.orderNumber == null) return false;
        return this.orderNumber.equals(other.orderNumber);
    }
    
    @Override
    public int haschCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result * ((orderNumber == null) ? 0 : orderNumber.hashCode());
        return result;
    }
    ...
}
```

### 🏛 엔티티의 식별자 생성

&nbsp;엔티티의 식별자를 생성하는 시점은 도메인의 특징과 사용하는 기술에 따라 달라진다. 흔히 식별자는 다음 중 한 가지 방식으로 생성한다.
* 특정 규칙에 따라 생성
* UUID 사용
* 값을 직접 입력
* 일련번호 사용(시퀀스나 DB의 자동 증가 컬럼 사용)

&nbsp;주문번호, 운송장번호 같은 식별자는 특정 규칙에 따라 생성한다. 이 규칙은 도메인에 따라 다르고, 같은 주문번호라도 회사마다 다르다.<br>
&nbsp;흔히 사용하는 규칙은 현재 시간과 다른 값을 함께 조합하는 것.<br> 
ex) '20241025205534000001' -> '20241025205534' 는 시간<br>
&nbsp;시간을 이용해서 식별자를 생성할 때 주의할 점은 같은 시간에 동시에 식별자를 생성할 때 같은 식별자가 만들어지면 안 된다는 것.

&nbsp;<ins>UUID(universally unique identifier)를 사용</ins>해서 식별자를 생성할 수 있다. 다수의 개발언어가 UUID 생성기를 제공하고 있으므로 마땅한 규칙이 없다면 사용해도 된다. ex) 자바의 경우 java.util.UUID 클래스를 사용하면 생성할 수 있다.

```java
UUID uuid = UUID.randomUUID();

// 615f2ab9-c123-4h20-9420-2341234af123 과 같은 형식 문자열
String strUuid = uuid.toString();
```

&nbsp;식별자로 일련번호를 사용하기도 한다. 이 방식은 주로 데이터베이스가 제공하는 자동 증가 기능을 사용한다. ex) 오라클은 시퀀스를 이용해서 자동 증가 식별자를 구하고, MySQL을 사용한다면 자동 증가 컬럼(auto_increment)을 이용해서 일련번호 식별자를 생성.

&nbsp;자동 증가 컬럼을 제외한 다른 방식은 다음과 같이 식별자를 먼저 만들고 엔티티 객체를 생성할 때 식별자를 전달할 수 있다.

```java
// 엔티티를 생성하기 전에 식별자 생성
String orderNumber = orderRepository.generate();

Order order = new Order(orderNumber, ....);
orderRepository.save(order);
```

&nbsp;자동 증가 컬럼은 DB 테이블에 데이터를 삽입해야 비로소 값을 알 수 있기 때문에 테이블에 데이터를 추가하기 전에는 식별자를 알 수 없다. 이는 엔티티 객체 생성을 할 때 식별자를 전달할 수 없음을 뜻한다.

```java
Article article = new Article(author title, ...);
articleRepository.save(article); // DB에 저장한 뒤 구한 식별자를 엔티티에 반영
Long savedArticleId = article.getId(); // DB에 저장한 후 식별자 참조 가능
```

### 🏛️ 밸류 타입

&nbsp;밸류 타입은 개념적으로 완전한 하나를 표현할 때 사용한다. 

```java
public class Receiver {
    private String name;
    private String phoneNumber;
    
    public receiver(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
```

&nbsp;`Receiver`는 '받는 사람' 이라는 도메인 개념을 표현한다. 앞서 `ShippingInfo`의 `receiverName` 필드와 `receiverPhoneNumber` 필드가 필드이름을 통해서 받는 사람을 위한 데이터라는 것을 유추한다면, `Receiver`는 그 자체로 받는 사람을 뜻한다.

&nbsp;밸류 타입을 사용함으로써 개념적으로 완전한 하나를 잘 표현할 수 있는 것이다.

&nbsp;`ShippingInfo`의 주소 관련 데이터도 다음의 `Address` 밸류 타입을 사용해서 보다 명확하게 표현할 수 있다.

```java
public class Address {
    private String address1;
    private String address2;
    private String zipcode;
    
    public Address(String address1, String Address2, String zipcode) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipcode = zipcode;
    }
    
    // get 메서드
}
```

&nbsp;밸류 타입을 이용해서 `ShippingInfo` 클래스를 다시 구현해보자. 배송정보가 받는 사람과 주소로 구성된다는 것을 쉽게 알 수 있다.

```java
public class ShippingInfo {
    private Receiver receiver;
    private Address address;
    
    ... 생성자, get 메서드
}
```

&nbsp;<ins>밸류 타입이 꼭 두 개 이상의 데이터를 가져야 하는 것은 아니다. 의미를 명확하게 표현하기 위해 밸류 타입을 사용하는 경우도 있다.</ins>

&nbsp;이를 위한 좋은 예가 `OrderLine` 이다.

```java
public class OrderLine {
    private Product product;
    private int price;
    private int quantity;
    private int amounts;
    ...
}
```

&nbsp; `OrderLine` 의 `price` 와 `amounts` 는 `int` 타입의 숫자를 사용하지만 '돈'을 의미한다. 따라서, '돈'을 의미하는 `Money` 타입을 만들어서 사용하면 코드를 이해하는데 도움이 된다.

```java
public class Money {
    private int value;
    
    public Money(int value) {
        this.money = money;
    }
    
    public int getValue() {
        return this.value;
    }
}
```

&nbsp;다음은 `Money` 사용하도록 `OrderLine` 을 변경한 코드이다. `Money` 타입 덕에 `price`, `amounts` 가 금액을 의미한다는 것을 쉽게 알 수 있다.

```java
public class OrderLine {
    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;
    ...
}
```

&nbsp;밸류 타입을 사용할 때의 또 다른 장점은 <ins>밸류 타입을 위한 기능 추가를 할 수 있다.</ins> 
ex) `Money` 타입은 돈 계산을 위한 기능을 추가할 수 있다.

```java
public class Money {
    private int value;
    
    ... 생성자, getValue()
    
    public Money add(Money money) {
        return new Money(this.value + money.value);
    }
    public Money multiply(int multiplier) {
        return new Money(value * multiplier);
    }
}
```

&nbsp;`Money`를 사용하는 코드는 이제 '정수타입 연산'이 아니라 '돈 계산' 이라는 의미로 코드를 작성할 수 있게 된다. <ins>코드의 가독성 향상!!</ins>

```java
public class OrderLine {
    private Product product;
    private int price;
    private int quantity;
    private int amounts;

    public OrderLine(Product product, int price, int quantity) {
        this.product = product;
        this.price = price;
    }

    private int calculateAmounts() {
        return price*quantity;
    }

    public int getAmounts() {...}
    ...
}
```
⬇️ 밸류 타입 적용 ⬇️    
```java
public class OrderLine {
    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;

    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        this.price = price;
    }

    private Money calculateAmounts() {
        return price.multiply(quantity);
    }

    public int getAmounts() {...}
    ...
}
```

&nbsp;<ins>밸류 객체의 데이터를 변경할 때</ins>는 기존 데이터를 변경하기보다는 <ins>변경한 데이터를 갖는 새로운 밸류 객체를 생성하는 방식을 선호</ins>한다.
ex) `Money` 클래스의 `add()` 메서드를 보면 `Money`를 새로 생성하고 있다.

```java
public class Money {
    private int value;
    
    public Money add(Money money) {
        return new Money(this.value + money.value);
    }
    
    // value를 변경할 수 있는 메서드 없음
}
```

&nbsp;`Money`처럼 데이터 변경 기능을 제공하지 않는 타입을 불변(immutable)이라고 한다. 
밸류 타입을 불변으로 구현하는 이유는 여러 가지가 있는데 가장 중요한 이유는 불변 타입을 사용하면 보다 안전한 코드를 작성할 수 있다.

ex) `OrderLine` 클래스를 생성하려면 다음 코드처럼 `Money` 객체를 전달해야 한다.

```java
Money price = ...;
OrderLine line = new OrderLine(product, price, quantity);
// 만약 price.setValue(0)으로 값을 변경할 수 있다면?
```

&nbsp;그런데, `Money`가 `setValue()`와 같은 메서드를 제공하여 값을 변경할 수 있다면? 아래와 같이 `OrderLine`의 `price`값이 잘못 반영되는 상황이 발생한다.

```java
Money price = new Money(1000);
OrderLine line = new OrderLine(product, price, 2); // -> [price=1000, quatity=2, amounts=2000]
price.setValue(2000); // -> [price=2000, quatity=2, amounts=2000]
```
(참조 투명성과 관련된 문제)

&nbsp;이런 문제가 발생하지 않도록 하려면 `OrderLine` 생성자는 다음과 같이 새로운 `Money` 객체를 생성하도록 코드를 작성해야 한다.

```java
public class OrderLine {
    ...
    private Money price;
    
    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        // Money가 불변 객체가 아니라면,
        // price 파라미터가 변경될 때 발생하는 문제를 방지하기 위해
        // 데이터를 복사한 새로운 객체를 생성해야 한다.
        this.price = new Money(price.getValue());
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }
}
```

&nbsp;`Money` 가 불변이면 이런 코드를 작성할 필요가 없다. `Money`의 데이터를 바꿀 수 없기 때문에 파라미터로 전달받은 `price`를 안전하게 사용할 수 있다.

> 불변 객체는 참조 투명성과 스레드에 안전한 특징을 갖고 있다. 불변 객체에 대해 더 많은 내용을 알고 싶다면 https://goo.gl/2Lo4pU 문서를 참고하자.

&nbsp;엔티티 타입의 두 객체가 같은지 비교할 때 주로 식별자를 사용한다면 <ins>두 밸류 객체가 같은지 비교할 때는 모든 속성이 같은지 비교해야 한다.</ins>

```java
public class Receiver{
    private String name;
    private String phoneNumber;
    
    public boolean equals(Object other) {
        if(other == null) return false;
        if(this == other) return true;
        if(!(other instanceof Receiver)) return false;
        Receiver that = (Receiver)other;
        return this.name.equals(that.name) && 
              this.phoneNumber.equals(that.phoneNumber);
    }
    ...
}
```

---

## 3. 도메인 용어

&nbsp;코드를 작성할 때 도메인에서 사용하는 용어는 아주 중요하다. 도메인에서 사용하는 용어를 코드에 반영하지 않는다면 그 코드는 개발자에게 코드의 의미를 해석해야 하는 부담을 준다.

ex) `OrderState`를 다음과 같이 구현했다고 가정해보자.

```java
public OrderState {
    STEP1, STEP2, STEP3, STEP4, STEP5, STEP6
}
```

실제주문상태는 '결제 대기중', '상품 준비중', '출고 완료됨', '배송중', '배송완료', '주문 취소' 인데, 이 코드는 개발자가 전체 상태를 6단계로 보고 코드로 표현한 것이다. 
이 개발자는 `Order` 코드를 아래와 같이 작성할 가능성이 높다.

```java
public class Order {
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        verifyStep1OrStep2();
        setShippingInfo(newShippingInfo);
    }
    private void verifyStep1OrStep2() {
        if (state != OrderState.STEP1 && state != OrderState.STEP2)
            throw new IllegalStateException("already shipped");
    }
    ...
}
```

&nbsp;배송지 변경은 '출고 전'에 가능한데 이 코드의 `verifyStep1OrStep2`라는 이름은 도메인의 중요 규칙이 드러나지 않는다. 
각각 STEP의 상태를 파악해야 한다. 즉, 기획자나 도메인 전문가가 개발자와의 업무 회의에서 '출고 전' 이라는 단어를 사용하면 개발자는 머릿속으로 '출고 전은 STEP1과 STEP2' 라고 도메인 지식을 코드로 해석해야 한다.

&nbsp;아래의 코드처럼 도메인 용어를 사용해서 `OrderState`를 구현하면 불필요한 변환을 거치지 않아도 된다.

```java
public enum OrderState {
    PAYMENT_WAITING, PREPARING, SHIPPED, DELIVERING, DELIVERY_COMPLETED;
}
```

&nbsp;위와 같이 도메인에서 사용하는 용어를 최대한 코드에 반영하면 코드를 보고 바로 도메인을 이해할 수 있다. 
코드를 도메인 용어로 해석하거나 도메인 용어를 코드로 해석하는 과정이 줄어든다. 
이는 코드의 가독성을 높여서 코드를 분석하고 이해하는 시간을 절약한다. 
도메인 용어를 사용해서 최대한 도메인 규칙을 코드로 작성하게 되므로 (의미를 변환하는 과정에서 발생하는) 버그도 줄어들게 된다.

+\
&nbsp;도메인 용어는 좋은 코드를 만드는 데 아주 중요한 요소임에 틀림없지만 국내 개발자는 이 점에 있어 불리한 면이 있다. 
바로 영어 때문이다. 즉 도메인 용어를 영어로 해석하는 노력이 필요함을 뜻한다. 
알맞은 영어 단어를 찾는 것은 쉽지 않은 일이지만 시간을 들여 찾는 노력을 해야 한다. 
사전을 사용해서 적당한 단어를 찾는 노력을 하지 않고 도메인에 어울리지 않는 단어를 사용하면 코드는 도메인과 점점 멀어지게 된다.

&nbsp;**도메인 용어에 알맞은 단어를 찾는 시간을 아까워하지 말자.**






