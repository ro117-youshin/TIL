Section 2. 아키텍처 개요
=====================
> 1. 아키텍처
> 1-1. 네 개의 영역
> 1-2. 계층 구조 아키텍처
> 2. DIP
> 3. 도메인 영역의 주요 구성요소
> 4. 인프라스트럭처
> 5. 모듈

## 1. 아키텍처

### 🏛️ 네 개의 영역
 아키텍처의 전형적인 영역, '표현', '응용', '도메인', '인프라스트럭처'의 네 영역이다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/archi_1.png" width="400" height="300"/>

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

<img src="https://github.com/ro117-youshin/TIL/blob/main/DomainDrivenDesign/img/application_to_domain.png" width="400" height="300"/>

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













