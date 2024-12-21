## CHAPTER 6. 응용 서비스와 표현 영역

> [DDD Start! 도메인 주도 설계 구현과 핵심 개념 익히기 - 최범균] 학습 후 기록

> 0. Intro
> 1. 응용 서비스 구현
> 2. 표현 영역의 역할
> 3. 값 검증과 권한 검사

## 0. Intro

```text
사용자 -> 표현 영역 -> 응용 영역 -> 도메인 영역
(사용자에게 기능을 제공하려면 도메인 영역과 사용자를 연결해 줄 표현 영역과 응용 영역이 필요)
```

### 표현 영역과 응용 영역
&nbsp; 표현 영역은 사용자의 요청을 해석. 
요청을 받은 표현 영역은 URL, 요청 파라미터, 쿠키, 헤더 등을 이용해서 사용자가 어떤 기능을 실행하고 싶어 하는지 판별하고 그 기능을 제공하는 응용서비스를 실행한다.

&nbsp;실제 사용자가 원하는 기능을 제공하는 것은 응용 영역에 위치한 서비스다. 
사용자가 회원가입을 요청했다면 실제 그 기능을 제공하는 주체는 응용 서비스에 위치한다.
응용 서비스는 기능을 실행하는 데 필요한 입력값을 메서드 파라미터로 전달받고 실행 결과를 리턴한다.

&nbsp;응용 서비스의 메서드가 요구하는 파라미터와 사용자로부터 전달받은 데이터는 형식이 다르기 때문에 표현 영역은 사용자 요청을 응용 서비스가 요구하는 형식으로 변환한다.

&nbsp;<ins>사용자와의 상호작용은 표현 영역이 처리하기 때문에 응용 서비스는 표현 영역에 의존하지 않는다.</ins> 
응용 영역은 사용자가 웹 브라우저를 사용하는지, REST API를 호출하는지, TCP 소켓을 사용하는지 여부를 알 필요가 없다.
단지, <ins>응용 영역은 실행에 필요한 입력값을 전달받고 실행 결과만 리턴하면 될 뿐이다.</ins>

### 응용 서비스의 역할

&nbsp;응용 서비스는 사용자(client)가 요청한 기능을 실행. 사용자의 요청을 처리하기 위해 리포지터리로부터 도메인 객체를 구하고, 도메인 객체를 사용한다.

&nbsp;주요 역할은 <ins>도메인 객체를 사용해서 사용자의 요청을 처리하는 것</ins>, 표현(사용자) 영역 입장에서 보았을 때 응용 서비스는 도메인 영역과 표현 영역을 연결해주는 창구인 파사드(facade) 역할을 한다.

###### 응용 서비스의 단순한 형태
```java
public Result doSomeFunc(SomeReq req) { 
    // 1. 리포지터리에서 애그리거트를 구한다.
    SomeAgg agg = someAggRepository.findById(req.getId());
    checkNull(agg);
    
    // 2. 애그리거트의 도메인 기능을 실행
    agg.doFunc(req.getValue());
    
    // 3. 결과를 리턴
    return createSuccessResult(agg);
}
```

###### 새로운 애그리거트를 생성하는 응용 서비스
```java
public Result doSomeCreation(CreateSomeReq req) { 
    // 1. 데이터 중복 등 데이터가 유효한지 검사.
    checkValid(req);
    
    // 2. 애그리거트 생성.
    SomeAgg newAgg = createSome(req);
    
    // 3. 리포지터리에 애그리거트를 저장.
    someAggRepository.save(newAgg);
    
    // 4. 결과를 리턴.
    return createSuccessResult(newAgg);
}
```

&nbsp;응용 서비스가 이것보다 복잡하다면 도메인 로직의 일부를 구현하고 있을 가능성이 높다.

&nbsp;응용 서비스의 주된 역할은 도메인 객체 간의 실행 흐름을 제어하는 것과 트랜잭션 처리이다.
응용 서비스는 도메인의 상태 변경을 트랜잭션으로 처리해야 한다. 한 번에 다수 회원을 차단 상태로 변경하는 응용 서비스를 생각해보자.
이 서비스는 차단 대상이 되는 `Member` 애그리거트 목록을 구하고 차례대로 차단 기능을 실행할 것이다.
```java
public void blockMembers(String[] blockingIds) {
    if(blockingIds == null || blockingIds.length == 0) return;
    List<Member> members = memberRepository.findByIds(blockingIds);
    for (Member mem : members) {
        mem.block;
    }
}
```
&nbsp;`blockMembers()`메서드가 트랜잭션 범위에서 실행되지 않는다고 가정한다면 `Member`객체의 `block()` 메서드의 변경 상태를 DB에 반영하는 도중 문제가 발생하면 일부 `Member`만 차단 상태가 되어 데이터 일관성이 깨진다.
이런 상황이 발생하지 않으려면 트랜잭션 범위에서 응용 서비스를 실행해야 한다.



