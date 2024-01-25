## 02장 리액트 핵심 요소 깊게 살펴보기
> '모던 리액트 Deep Dive - 김용찬 02장' 학습
>
> 2.1 JSX란?
>
> 2.2 가상 DOM과 리액트 파이버
> 
> 2.3 클래스형 컴포넌트와 함수형 컴포넌트
>
> 2.4 렌더링은 어떻게 일어나는가?

---

### 2.1 JSX란?
###### JSX는 자바스크립트 표준 코드가 아닌 페이스북이 임의로 만든 새로운 문법이기 때문에 JSX는 반드시 트랜스파일러를 거쳐야 비로소 자바스크립트 런타임이 이해할 수 있는 의미 있는 자바스크립트 코드로 변환된다. 
* JSX는 HTML이나 XML을 자바스크립트 내부에 표현하는 것이 유일한 목적은 아니다.
  * 다양한 트랜스파일러에서 다양한 속성을 가진 트리 구조를 토큰화해 ECMAScript로 변환하는 데 초점을 두고 있다.
  * 쉽게 이야기 하면 JSX 내부에 트리 구조로 표현하고 싶은 다양한 것들을 작성해두고, 이 JSX를 트랜스파일이라는 과정을 거쳐 자바스크립트(ECMAScript)가 이해할 수 있는 코드로 변경하는 것이 목표라고 볼 수 있다.
* 정리하자면 JSX는 자바스크립트 내부에서 표현하기 까다로웠던 XML 스타일의 트리 구문을 작성하는 데 많은 도움을 주는 새로운 문법.

#### 2.1.1 JSX 정의와 형태
기본적으로 JSXElement, JSXAttributes, JSXChildren, JSXStrings라는 4가지 컴포넌트를 기반으로 구성돼 있다.

#### *JSXElement*
: JSX를 구성하는 가장 기본 요소, HTML의 요소(element)와 비슷한 역할.
* JSXOpeningElement: 일반적으로 볼 수 있는 요소. JSXOpeningElement로 시작했다면 JSXClosingElement가 동일한 요소로 같은 단계에서 선언돼 있어야 올바른 문법으로 간주된다.
```JSX
<JSXElement JSXAttributes(optional)>
```
* JSXClosingElement: JSXOpeningElement가 종료됐음을 알리는 요소, 반드시 JSXOpeningElement와 쌍으로 사용돼야 한다. 
```JSX
<JSXElement />
```
* JSXSelfClosingElement: 요소가 시작되고, 스스로 종료되는 형태. <sript/>와 동일한 모습. 이는 내부적으로 자식을 포함할 수 없는 형태를 의미한다.
```JSX
<JSXElement JSXAttributes(optional) />
```
* JSXFragment: 아무런 요소가 없는 형태로, JSXSelfClosingElement 형태를 띨 수는 없다. </>는 불가능. <></>는 가능.
```JSX
<>JSXChildren(optional)</>
```

#### *JSXElementName*
: JSXElement의 요소 이름으로 쓸 수 있는 것을 의미.

* JSXIdentifier: JSX 내부에서 사용할 수 있는 식별자. (자바스크립트 식별자 규칙과 동일) 숫자로 시작하거나 $와 _ 외의 다른 특수문자로 시작할 수 없다.
```JSX
function Valid1() {
    return <$><$/>
}

function Valid2() {
    return <_><_/>
}

// 불가능
function Invalid() {
    return <1><1/>
}
```
* JSXNamespacedName: <JSXIdentifier:JSXIdentifier> 의 조합, 즉 :을 통해 서로 다른 식별자를 이어주는 것도 하나의 식별자로 취급.
```JSX
function valid() {
    return <loan:amt><loan:amt/>
}

// 불가능
function invalid() {
    return <loan:amt:usd><loan:amt:usd/>
}
```
* JSXMemberExpression: <JSXIdentifier.JSXIdentifier>의 조합, 즉 .을 통해 서로 다른 식별자를 이어주는 것도 하나의 식별자로 취급. JSXNamespacedName과 다르게 여러 개 이어서 하는 것도 가능. (JSXNamespacedName과 이어서 사용은 불가)
```JSX
function valid1() {
    return <loan.amt><loan.amt/>
}

function valid2() {
    return <loan.amt.usd><loan.amt.usd/>
}

// 불가능
function valid2() {
    return <loan:amt.usd><loan:amt.usd/>
}
```

#### *JSXAttributes*
: JSXElement에 부여할 수 있는 속성. 단순히 속성이기 때문에 optional.
* JSXSpreadAttributes: 자바스크립트의 전개 연산자와 동일한 역할을 한다고 볼 수 있음. 
* JSXAttributes: 속성을 나타내는 키와 값으로 짝을 이루어서 표현. 키는 JSXAttributeName, 값은 JSXAttributeValue.
  
#### *JSXChildren*
: JSXElement의 자식 값을 나타냄. JSX는 속성을 가진 트리 구조를 나타내기 위해 만들어졌기 때문에 JSX로 부모와 자식관계를 나타낼 수 있으며, 그 자식을 JSXChildren이라고 함.
* JSXText: {, <, >, } 을 제외한 문자열, 이는 다른 JSX 문법과 혼동을 줄 수 있기 때문. 만약 표현하고 싶다면 문자열로 표시.
```JSX
function valid() {
    return <> {'{} <>'} </>
}
```
* JSXElement: 값으로 다른 JSX 요소가 들어갈 수 있음.
* JSXFragment: 값으로 빈 JSX 요소인 <></>가 들어갈 수 있음.
* { JSXChildExpression (optional) }: 이 JSXChildExpression은 자바스크립트의 AssignmentExpression을 의미한다.
```JSX
// 이러한 JSX 표현식도 있다.
// 이 함수를 리액트에서 렌더링하면 "LOAN"이라는 문자열을 출력.
export default function App() {
    return <>{(() => 'LOAN')()}</>
}
``` 

---
### 2.2 가상 DOM과 리액트 파이버
###### 추후작성

---
### 2.3 클래스형 컴포넌트와 함수형 컴포넌트
#### 2.3.1 클래스형 컴포넌트

###### 추후작성

#### 2.3.2 함수형 컴포넌트
###### *함수형 컴포넌트는 리액트 16.8 버전 이전에는 단순히 무상태 컴포넌트를 구현하기 위한 하나의 수단에 불과했지만 16.8에서 함수형 컴포넌트에서 사용 가능한 Hook이 등장하면서 리액트 개발자들에게 각광받고 있다.*

#### ex) 아주 간단한 함수형 컴포넌트 예제
```javascript
import { useState } from 'react'

type SampleProps = {
    required?: boolean
    text: string
}

exoprt function SampleComponent({ required, text }: SampleProps) {
    const [count, setCount] = useState<number>(0)
    const [isLimited, setIsLimited] = useState<boolean>(false)

    fuction handleClick() {
        const newValue = count + 1
        setCount(newValue)
        setIsLimited(newValue >= 10)
    }

    return (
        <h2>
          Sample Component
          <div>{required ? '필수': '필수 아님'}</div>
          <div>문자: {text}</div>
          <div>count: {count}</div>
          <button onClick={handleClick} disabled={isLimited}>
            증가
          </button>
        </h2>
    )
}
```
* 클래스 컴포넌트로 동일한 결과물을 구현했을 때보다 여러모로 간결해진 코드
  * render 내부에서 필요한 함수를 선언할 때 this 바인딩을 조심할 필요가 없다.
  * state는 객체가 아닌 각각의 원시값으로 관리되어 훨씬 사용하기가 편해졌다. 물론 state는 객체도 관리할 수 있다.
  * 렌더링하는 코드인 return에서도 굳이 this를 사용하지 않더라도 props와 state에 접근할 수 있게 됐다.

#### 2.3.3 함수형 컴포넌트 vs. 클래스형 컴포넌트

#### 💡 생명주기 메서드의 부재
* 가장 눈에 띄는 차이점이자 많은 개발자들이 적응하지 못하는 부분, 클래스형 컴포넌트의 생명주기 메서드가 함수형 컴포넌트에 존재하는 않는다는 것.
  * 그 이유는 함수형 컴포넌트는 props를 받아 단순히 리액트 요소만 반환하는 함수인 반면, 클래스형 컴포넌트는 render 메서드가 있는 React.Component를 상속받아 구현하는 자바스크립트 클래스이기 때문.
  * 즉, 생명주기 메서드는 React.Component에서 오는 것이기 때문에 클래스형 컴포넌트가 아닌 이상 생명주기 메서드는 더는 사용할 수 없다는 뜻.

#### 💡 함수형 컴포넌트와 렌더링된 값
###### 추후작성

---
### 2.4 렌더링은 어떻게 일어나는가?
###### *리액트에서 렌더링이 무엇을 의미하는지 명확히 정의할 필요가 있다. 렌더링은 브라우저에서도 사용되는 용어이므로 두 가지를 혼동해서 사용해서는 안 된다.*
#### 2.4.1 리액트의 렌더링이란?
* 리액트 애플리케이션 트리 안에 있는 모든 컴포넌트들이 현재 자신들이 가지고 있는 props와 state의 값을 기반으로 어떻게 UI를 구성하고 이를 바탕으로 어떤 DOM 결과를 브라우저에 제공할 것인지 계산하는 일련의 과정을 의미.
* 만약 컴포넌트가 props와 state와 같은 상태값을 가지고 있지 않다면 오직 해당 컴포넌트가 반환하는 JSX 값에 기반해 렌더링이 일어나게 된다. 

#### 2.4.2 리액트의 렌더링이 일어나는 이유
###### *렌더링 과정을 이해하는 것도 중요하지만 이보다 더 중요한 것은 렌더링이 언제 발생하느냐다.*
#### 렌더링 발생 시나리오
1. 최초 렌더링: 사용자가 처음 애플리케이션에 진입하면 당연히 렌더링해야 할 결과물이 필요하다. 리액트는 브라우저에 이 정보를 제공하기 위해 최초 렌더링을 수행.
2. 리렌더링: 리렌더링은 처음 애플리케이션에 진입했을 때 최초 렌더링이 발생한 이후로 발생하는 모든 렌더링을 의미.
* 클래스 컴포넌트의 setState가 실행되는 경우:
  * state의 변화는 컴포넌트 상태의 변화를 의미. 클래스형 컴포넌트에서는 state의 변화를 setState 호출을 통해 수행하므로 리렌더링이 발생.
* 클래스형 컴포넌트의 forceUpdate가 실행되는 경우:
  * 클래스형 컴포넌트에서 렌더링을 수행하는 것은 인스턴스메서드인 render다. 만약 이 render가 state나 props가 아닌 다른 값에 의존하고 있어 리렌더링을 자동으로 실행할 수 없을 경우 forceUpdate를 실행해 리렌더링을 일으킬 수 있다.
* 함수형 컴포넌트의 useState()의 두 번째 배열 요소인 setter가 실행되는 경우:
  * useState가 반환하는 배열의 두 번째 인수는 클래스형 컴포넌트의 setState와 마찬가지로 state를 업데이트하는 함수다. 이 함수가 실행되면 렌더링이 일어남.
* 함수형 컴포넌트의 useReducer()의 두 번째 배열 요소인 dispatch가 실행되는 경우:
  * useReducer도 useState와 마찬가지로 상태와 이 상태를 업데이트하는 함수를 배열로 제공한다. 이 두 번째 요소를 실행하면 컴포넌트의 렌더링이 일어남.
* 컴포넌트의 key props가 변경되는 경우:
  * 리액트에서 key는 명시적으로 선언돼 있지 않더라도 모든 컴포넌트에서 사용할 수 있는 특수한 props다. 
