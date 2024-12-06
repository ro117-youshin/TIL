## CHAPTER 3. 애그리거트(_Aggregate_)

> [DDD Start! 도메인 주도 설계 구현과 핵심 개념 익히기 - 최범균] 학습 후 기록

> 1. 애그리거트<br>
> 2. 애그리거트 루트와 역할<br>
  > 2-1. 도메인 규칙과 일관성<br>
  > 2-2. 애그리거트 루트의 기능 구현<br>
  > 2-3. 트랜잭션 범위<br>
> 3. 애그리거트와 리포지터리<br>
> 4. ID를 이용한 애그리거트 참조<br>
  > 4-1. ID를 이용한 참조와 조회 성능


## 1. 애그리거트

&nbsp;백 개 이상의 테이블을 한 장의 ERD에 모두 표시하면 개별 테이블 간의 관계를 파악하느라 큰 틀에서 데이터 구조를 이해하는데 어려움을 겪게 되는 것처럼,
<ins>도메인 객체 모델이 복잡해지면 개별 구성요소 위주로 모델을 이해하게 되고 전반적인 구조나 큰 수준에서 도메인 간의 관계를 파악하기 어려워진다.</ins>

&nbsp;<ins>주요 도메인 개념 간의 관계를 파악하기 어렵다는 것은 곧 코드를 변경하고 확장하는 것이 어려워진다는 것을 의미한다.</ins>
상위 수준에서 모델이 어떻게 엮여 있는지 알아야 전체 모델을 망가뜨리지 않으면서 추가 요구사항을 모델에 반영할 수 있는데 세부적인 모델만 이해한 상태로 코드를 수정하기가 두렵기 때문에 코드 변경을 최대한 회피하는 쪽으로 요구사항을 협의하게 된다.

&nbsp;<ins>복잡한 도메인을 이해하고 관리하기 쉬운 단위로 만들려면 상위 수준에서 모델을 조망할 수 있는 방법이 필요한데,</ins> 그 방법이 바로 **_애그리거트_** 이다.

&nbsp;애그리거트는 관련된 객체를 하나의 군으로 묶어주는데, 수많은 객체를 애그리거트로 묶어서 바라보면 좀 더 상위 수준에서 도메인 모델 간의 관계를 파악할 수 있다.

<p align="center">
  <img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/models_at_individual_object_level.png" width="600" height="350"/>
</p>

&nbsp; 동일한 모델이지만 애그리거트를 사용함으로써 모델 간의 관계를 개별 모델 수준뿐만 아니라 상위 수준에서도 이해할 수 있게 된다.

<p align="center">
  <img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/models_at_aggregate_level.png" width="600" height="350"/>
</p>

&nbsp;애그리거트는 모델을 잘 이해하게 해줄 뿐만 아니라 <ins>일관성을 관리하는 기준이 된다.</ins>
<ins>일관성을 관리하기 때문에 복잡한 도메인을 단순한 구조로 만들어주고, 복잡도가 낮아지는 만큼 도메인 기능을 확장하고 변경하는데 필요한 노력(개발 시간)도 줄어든다.</ins>

&nbsp;<ins>한 애그리거트에 속한 객체는 유사하거나 동일한 라이프사이클을 갖는다.</ins> 
예를 들어, 주문 애그리거트를 만들려면 `Order`, `OrderLine`, `Orderer`와 같은 관련 객체를 함께 생성해야 한다.
`Order`를 생성했는데 `ShippingInfo`를 생성하지 않았거나 `ShippingInfo`를 생성했는데 `Orderer`를 생성하지 않는 경우는 없다.
도메인 규칙에 따라 최초 주문 시점에 일부 객체를 만들 필요가 없는 경우도 있지만 <ins>애그리거트에 속한 구성요소는 대부분 함께 생성하고 함께 제거한다.</ins>

&nbsp; 위 그림과 같이 <ins>애그리거트는 경계</ins>를 갖는다. 애그리거트는 독립된 객체 군이며, 각 애그리거트는 자기 자신을 관리할 뿐 다른 애그리거트는 관리하지 않는다.
예를 들어, 주문 애그리거트는 배송지를 변경하거나 주문 상품 개수를 변경하는 등 자기 자신을 관리하지만, 회원정보를 변경하거나 상품의 가격을 변경하지 않는다.

&nbsp;경계를 설정할 때 기본이 되는 것은 도메인 규칙과 요구사항이다. 도메인 규칙에 따라 함께 생성되는 구성요소는 한 애그리거트에 속할 가능성이 높다.
또한 함께 변경되는 빈도가 높은 객체는 한 애그리거트에 속할 가능성이 높다.

&nbsp;흔히 'A가 B를 갖는다'로 설계할 수 있는 요구사항이 있다면 A와 B를 한 애그리거트에 묶어서 생각하기 쉽다. 
주문의 경우 `Order`가 `ShippingInfo`와 `Orderer`를 가지므로 이는 어느 정도 타당해 보인다. 
하지만, 'A가 B를 갖는다'로 해석되는 요구사항이라도 반드시 A와 B가 한 애그리거트에 속한다는 의미는 아니다.

&nbsp;좋은 예가 상품과 리뷰이다. 상품 상세 페이지에 보면 상품 상세 정보와 리뷰 내용을 보여줘야 한다. 
이 요구사항이라면 `Product` 엔티티와 `Review` 엔티티가 한 애그리거트에 속한다고 생각할 수 있다. 
하지만 `Product`와 `Review`는 함께 생성 및 변경되지 않는다. 그리고 `Product`를 변경하는 주체는 상품 담당자라면 `Review`는 고객이 생성 및 변경하는 주체다.
`Review`의 변경과 `Product`의 변경이 서로에게 영향을 주지 않기 때문에 서로 다른 애그리거트에 속한다.

---

## 2. 애그리거트 루트와 역할

&nbsp; 애그리거트는 여러 객체로 구성되기 때문에 한 객체만 상태가 정상이어서는 안된다. 
<ins>도메인 규칙을 지키려면 애그리거트에 속한 모든 객체가 정상 상태를 가져야 한다.</ins>

&nbsp; <ins>애그리거트에 속한 모든 객체가 일관된 상태를 유지하려면 애그리거트 전체를 관리할 주체가 필요한데 이 책임을 지는 것이 바로 **_애그리거트의 루트 엔티티_** 이다.</ins>
애그리거트 루트 엔티티는 애그리거트의 대표 엔티티로 애그리거트에 속한 객체는 애그리거트 루트 엔티티에 직접 또는 간접적으로 속한다.

<p align="center">
  <img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/the_root_of_order_aggregate_is_order.png" width="500" height="350"/>
</p>

&nbsp; 주문 애그리거트에서 루트 역할을 하는 엔티티는 `Order` 이다. `OrderLine`, `ShippingInfo`, `Orderer` 등 주문 애그리거트에 속한 모델은 `Order`에 직접 또는 간접적으로 속한다.

### 💡 도메인 규칙과 일관성

&nbsp; <ins>애그리거트의 핵심 역할</ins>은 <ins>애그리거트의 일관성이 깨지지 않도록 하는 것이다.</ins> 
이를 위해 <ins>애그리거트 루트는 애그리거트가 제공해야 할 도메인 기능을 구현한다.</ins>
예를 들어, 주문 애그리거트는 배송지 변경, 상품 변경과 같은 기능을 제공하는데 애그리거트 루트인 `Order`가 이 기능을 구현한 메서드를 제공한다.

&nbsp; <ins>애그리거트 루트가 제공하는 메서드는 도메인 규칙에 따라 애그리거트에 속한 객체의 일관성이 깨지지 않도록 구현해야 한다.</ins>
예를 들어, 배송이 시작되기 전까지만 배송지 정보를 변경할 수 있다는 규칙이 있다면, 애그리거트 루트인 `Order`의 `changeShippingInfo()`메서드는 이 규칙에 따라 배송 시작 여부를 확인하고 변경이 가능한 경우에만 배송지 정보를 변경해야 한다.
```java
public class Order {
    
    // 애그리거트 루트는 도메인 규칙을 구현한 기능을 제공한다.
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
    }
    
    private void verifyNotYetShipped() {
        if(state != OrderState.PAYMENT_WAITING && state != OrderState.WAITING)
            throw new IllegalStateException("already shipped");
    }
    
    ...
}
```

&nbsp; <ins>애그리거트 루트가 아닌 다른 객체가 애그리거트에 속한 객체를 직접 변경하면 안된다.</ins> 
이는 애그리거트 루트가 강제하는 규칙을 적용할 수 없어 모델의 일관성을 깨는 원인이 된다.

```java
ShippingInfo si = order.getShippingInfo();
si.setAddress(newAddress);
```

&nbsp;위 코드는 애그리거트 루트 `Order`에서 `ShippingInfo`를 가져와 직접 정보를 변경하고 있다.
주문 상태에 상관없이 배송지 주소를 변경할 수 있는데, 이는 업무 규칙을 무시하고 DB 테이블에서 직접 데이터를 수정하는 것과 같은 결과를 만든다. 
즉, 논리적인 데이터 일관성이 깨지게 되는 것이다. 일관성을 지키기 위해 아래와 같이 상태 확인 로직을 응용 서비스에 구현할 수도 있지만, 
이렇게 되면 동일한 검사 로직을 여러 응용 서비스에서 중복해서 구현할 가능성이 높아져 상황을 더 악화시킬 수 있다.

```java
ShippingInfo si = order.getShippingInfo();

// 주요 도메인 로직이 중복되는 문제, 여러 곳에 산재할 수 있는 코드...
if(state != OrderState.PAYMENT_WAITING && state != OrderState.WAITING){
    throw new IllegalArgumentException();    
}
    
si.setAddress(newAddress);
```

&nbsp; <ins>불필요한 중복을 피하고 애그리거트 루트를 통해서만 도메인 로직을 구현</ins>하게 만들려면 도메인 모델에 대해 다음의 두 가지 습관을 적용해야 한다.
* 단순히 필드를 변경하는 `set`메서드를 공개(public)범위로 만들지 않는다.
* 벨류 타입은 불변으로 구현한다.

```java
// 도메인 모델에서 공개 set 메서드는 가급적 피해야 한다.
public void setName(String name) {
    this.name = name;
}
```

&nbsp;공개 `set`메서드는 중요 도메인의 의미나 의도를 표현하지 못하고 도메인 로직이 도메인 객체가 아닌 응용 영역이나 표현 영역으로 분산되게 만드는 원인이 된다.
도메인 로직이 한곳에 응집되어 있지 않게 되므로 코드를 유지보수할 때에도 분석하고 수정하는데 더 많은 시간을 들이게 된다.

&nbsp; 공개 `set`메서드를 만들지 않는 것의 연장으로 <ins>벨류는 '불변 타입'으로 구현한다.</ins>
벨류 객체의 값을 변경할 수 없으면 애그리거트 루트에서 벨류 객체를 구해도 값을 변경할 수 없기 때문에 애그리거트 외부에서 벨류 객체의 상태를 변경할 수 없게 된다.

```java
ShippingInfo si = order.getShippingInfo();

si.setAddress(newAddress); // ShippingInfo 벨류 객체가 불변이면, 이 코드는 컴파일 에러!
```

&nbsp; 애그리거트 외부에서 내부 상태를 함부로 바꾸지 못하므로 애그리거트의 일관성이 깨질 가능성이 줄어든다.
<ins>벨류 객체가 불변이면 벨류 객체의 값을 변경하는 방법은 새로운 벨류 객체를 할당하는 것 뿐이다.</ins>

```java
public class Order {
    
    private ShippingInfo shippingInfo;
    
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
    }
    // set 메서드의 접근 허용 범위는 private
    private void setShippingInfo(ShippingInfo newShippingInfo) {
        // 벨류가 불변이면, 새로운 객체를 할당해서 값을 변경해야 한다.
        // 불변이므로 아래와 같은 코드는 사용할 수 없다.
        // this.shippingInfo.setAddress(newShippingInfo.getAddress()) 
        this.shippingInfo = newShippingInfo;
    }
}
```

### 💡 애그리거트 루트의 기능 구현
&nbsp; **애그리거트 루트**는 <ins>애그리거트 내부의 다른 객체를 조합해서 기능을 완성한다.</ins>
예를 들어, `Order`는 총 주문 금액을 구하기 위해 `OrderLine` 목록을 사용한다.

```java
public class Order {
    private Money totalAmounts;
    private List<OrderLine> orderLines;
    
    private void calculateTotalAmounts() {
        int sum = orderLines.stream()
                 .mapToInt(ol -> ol.getPrice() * ol.quantity())
                 .sum();
        this.totalAmounts = new Money(sum);
    }
}
```

&nbsp; 또 다른 예로 회원을 표현하는 `Member` 애그리거트 루트는 암호를 변경하기 위해 `Password`객체에 암호가 일치하는지 여부를 확인할 것이다.
```java
public class Member {
    private Password password;
    
    public void changePassword(String currentPassword, String newPassword) {
        if(!password.match(currentPassword)) {
            throw new PasswordNotMatchException();
        }
        this.password = new Password(newPassword);
    }
}
```

&nbsp;애그리거트 루트가 구성요소의 상태만 참조하는 것은 아니다. <ins>기능 실행을 위임하기도 한다.</ins>
예를 들어, 구현 기술의 제약이나 내부 모델링 규칙 때문에 `OrderLine` 목록을 별도 클래스로 분리했다고 해보자.

```java
public class OrderLines {
    private List<OrderLine> lines;
    
    public Money getTotalAmounts() { ... }
    public void changeOrderLines(List<OrderLine> newLines) {
        this.lines = newLines;
    }
}
```

&nbsp 이 경우 `Order`의 `changeOrderLines()` 메서드는 아래 코드와 같이 내부의 `orderLines`필드에 상태 변경을 위임하는 방식으로 기능을 구현한다.

```java
public class Order {
    private OrderLines orderLines;
    
    public void changeOrderLines(List<OrderLine> newLines) {
        orderLines.changeOrderLines(newLines);
        this.totalAmounts = orderLines.getTotalAmounts();
    }
}
```

&nbsp; `OrderLines`는 `changeOrderLines()`와 `getTotalAmounts()` 같은 기능을 제공하고 있다.
만약 `Order`가 `getOrderLines()`와 같이 `OrderLines`를 구할 수 있는 메서드를 제공하면 애그리거트 외부에서 `OrderLines`의 기능을 실행할 수 있게 된다.

```java
OrderLines lines = order.getOrderLines();

// 외부에서 애그리거트 내부 상태 변경!
// order의 totalAmounts가 값이 OrderLines가 일치하지 않게 됨
lines.changeOrderLines(newOrderLines);
```

&nbsp; 위 코드는 주문의 `OrderLine` 목록이 바뀌는데 총합은 계산하지 않는 버그를 만든다.
이런 버그가 생기지 않도록 하려면 애초에 애그리거트 외부에서 `OrderLine` 목록을 변경할 수 없도록 `OrderLines`를 불변으로 구현하면 된다.

&nbsp; 팀 표준이나 구현 기술 제약으로 `OrderLines`를 불변으로 구현할 수 없다면 `OrderLines`의 변경 기능을 패키지나 `protected` 범위로 한정해서 외부에서 실행할 수 없도록 제한하는 방법이 있다.

### 💡 트랜잭션 범위

&nbsp; <ins>트랜잭션 범위는 작을수록 좋다.</ins> 
DB 테이블을 기준으로 한 트랜잭션이 한 개 테이블을 수정하는 것과 세 개의 테이블을 수정하는 것은 성능에서 차이가 발생한다.
한 개 테이블을 수정할 때에는 트랜잭션 충돌을 막기 위해 잠그는 대상이 한 개의 테이블의 한 행으로 한정되지만, 세 개의 테이블을 수정하면 잠금 대상이 더 많아진다.
<ins>잠금 대상이 많아진다는 것은 그만큼 동시에 처리할 수 있는 트랜잭션 개수가 줄어든다는 것을 뜻하고 이는 전체적인 성능(처리량)을 떨어뜨린다.

&nbsp;<ins>동일하게 한 트랜잭션에는 한 개의 애그리거트만 수정해야 한다. 
한 트랜잭션에서 두 개 이상의 애그리거트를 수정하면 트랜잭션 충돌이 발생할 가능성이 더 높아지기 때문에 한 번에 수정하는 애그리거트 개수가 많아질수록 전체 처리량이 떨어지게 된다.</ins>

&nbsp; <ins>한 트랜잭션에서 한 애그리거트만 수정한다는 것</ins>은 <ins>애그리거트에서 다른 애그리거트를 변경하지 않는다는 것을 뜻한다.</ins>
즉, <ins>두 개의 애그리거트를 한 트랜잭션에서 수정하게 되는데 한 애그리거트 내부에서 다른 애그리거트의 상태를 변경하는 기능을 실행하면 안된다.(MSA)</ins>
예를 들어, 배송지 정보를 변경하면서 동시에 배송지 정보를 회원의 주소로 설정하는 기능이 있다고 해보자. 
이 경우 주문 애그리거트는 아래 코드와 같이 회원 애그리거트의 정보를 변경하면 안된다.

```java
public class Order {
    private Orderer orderer;
    
    public void shipTo(ShippingInfo newShippingInfo, 
                       boolean useNewShippingAddrAsMemberAddr) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
        if(useNewShippingAddrAsMemberAddr) {
            orderer.getCustomer().changeAddress(newShippingInfo.getAddress());
        }
    }
    ...
}
```

&nbsp; <ins>애그리거트에서 다른 애그리거트의 상태를 변경하지 말아야 한다.</ins> 애그리거트는 최대한 독립적이어야 한다. (= 의존도 낮은 -> 결합도가 낮은 (향후 수정 비용이 증가하지 않음))

&nbsp; 만약 부득이하게 한 트랜잭션으로 두 개 이상의 애그리거트를 수정해야 한다면 애그리거트에서 다른 애그리거트를 직접 수정하지 말고 <ins>응용 서비스에서 두 애그리거트를 수정하도록 구현해야 한다.</ins>

```java
public class ChangeOrderService {
    // 두 개 이상의 애그리거트를 변경해야 하면,
    // 응용 서비스에서 각 애그리거트의 상태를 변경.
    @Transactional
    public void chagneShippingInfo(OrderId id, 
                                   ShippingInfo newShippingInfo,
                                   boolean useNewShippingAddrAsMemberAddr) {
        
        Order order = orderRepository.findById(id);
        
        if(order == null) throw new OrderNotFoundException();
        order.shipTo(newShippingInfo);
        
        if(useNewShippingAddrAsMemberAddr) {
            order.getOrderer().
                    getCustomer().changeAddress(newShippingInfo.getAddress());
        }
    }
}
```

&nbsp;한 트랜잭션에서 한 개의 애그리거트를 변경하는 것을 권장하지만 아래 3 가지 경우 한 트랜잭션에서 두 개 이상의 애그리거트를 변경하는 것을 교려할 수 있다.
* 팀 표준: 팀에서 표준에 따라 사용자 유스케이스와 관련된 응용 서비스의 기능을 한 트랜잭션으로 실행해야 하는 경우가 있다. DB가 다른 경우 글로벌 트랜잭션을 반드시 사용하도록 규칙을 정하는 곳도 있다.
* 기술 제약: 한 트랜잭션에서 두 개 이상의 애그리거트를 수정하는 대신 도메인 이벤트와 비동기를 사용하는 방식을 사용하는데, 기술적으로 이벤트 방식을 도입할 수 없는 경우 한 트랜잭션에서 다수의 애그리거트를 수정해서 일관성을 처리해야 한다.
* UI 구현의 편리: 운영자의 편리함을 위해 주문 목록 화면에서 여러 주문의 상태를 한 번에 변경하고 싶을 것이다. 이 경우 한 트랜잭션에서 여러 주문 애그리거트의 상태를 변경할 수 있을 것이다.

---

## 3. 애그리거트와 리포지터리

&nbsp; **_애그리거트_** 는 <ins>개념상 완전한 한 개의 도메인 모델을 표현</ins>하므로 <ins>객체의 영속성을 처리하는</ins> **_리포지터리_** 는 <ins>애그리거트 단위로 존재</ins>한다.
`Order`와 `OrderLine`을 물리적으로 별도의 DB 테이블에 저장하지만 리포지터리를 각각 만들지 않는다. 
`Order`가 애그리거트 루트이고 `OrderLine`인 애그리거트에 속하는 구성요소이므로 `Order`을 위한 리포지터리만 존재한다.

&nbsp; 새로운 애그리거트를 만들면 저장소에 애그리거트를 영속화하고(save), 애그리거트를 사용하려면 저장소에서 애그리거트를 읽어야 하므로(select) 리포지터리는 최소한 아래 두 가지 메서드는 제공해야 한다.
* save: 애그리거트 저장
* findById: ID로 애그리거트를 구함

&nbsp;<ins>애그리거트는 개념적으로 하나이므로 리포지터리는 애그리거트 전체를 저장소에 영속화해야 한다.</ins> 
`Order` 애그리거트를 저장할 때 애그리거트 루트와 매핑되는 테이블 뿐만 아니라 애그리거트에 속한 모든 구성요소를 위한 테이블에 데이터를 저장해야 한다.

```java
// 리포지터리에 애그리거트를 저장하면 애그리거트 전체를 영속화해야 한다.
orderRepository.save(order);
```

&nbsp;<ins>동일하게 애그리거트를 구하는 리포지터리 메서드는 완전한 애그리거트를 제공해야 한다.</ins>
즉, 아래 코드를 실행하면 `order`애그리거트는 `OrderLine`, `Orderer` 등 모든 구성요소를 포함하고 있어야 한다.

```java
// 리포지터리는 완전한 order를 제공해야 한다.
Order order = orderRepository.findById(orderId);

// order가 온전한 애그리거트가 아니면 기능 실행 도중 NPE와 같은 문제가 발생.
order.cancel();
```

---

## 4. ID를 이용한 애그리거트 참조

