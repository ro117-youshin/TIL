## 01장 리액트 개발을 위해 꼭 알아야 할 자바스크립트
> '모던 리액트 Deep Dive - 김용찬 01장' 학습
>
> 1.1 자바스크립트의 동등 비교
> 
> 1.2 함수
> 
> 1.3 클래스

### 1.1 자바스크립트의 동등 비교
#### 1.1.1 자바스크립트의 데이터 타입
```text
원시타입(primitive type)
* boolean
* null
* undefined
* number
* string
* symbol
* bigint

객체타입(object/reference type)
* object
```
#### > *undefined*
* undefined는 선언한 후 값을 할당하지 않은 변수 또는 값이 주어지지 않은 인수에 자동으로 할당되는 값이다.
```javascript
let dog

typeof dog === 'undefined'    // true

fuction cat(hello) {
    return hello
}

typeof cat() === 'undefined'  // true
```
#### > *null*
* 아직 값이 없거나 비어 있는 값을 표현할 때 사용한다.
```javascript
typeof null === 'object'  // true?
```
* null의 특별한 점, 다른 원시값과 다르게 typeof로 null을 확인했을 때 해당 타입이 아닌 'object'라는 결과가 반환된다는 것이다. 이는 초창기 자바스크립트가 값을 표현하는 방식 때문에 발생한 문제로, 이후에 typeof null 을 진짜 'null'로 표현하고자 하는 시도가 있었으나 이전 코드에서 작동할 수 없는 호환성이 깨지는 변경 사항(breaking change)이어서 받아들여지지 않았다.

💡 undefined는 '선언됐지만 할당되지 않은 값'이고, null은 '명시적으로 비어 있음을 나타내는 값'으로 사용하는 것이 일반적이다.

#### > *boolean*
* true와 false만을 가질 수 있는 데이터 타입이다.
* 이외에도 조건문에서 마치 true와 false처럼 취급되는 truthy, falsy 값이 존재한다.

💡 falsy: 조건문 내부에서 false로 취급되는 값
|값|타입|설명|
|-----|-----|-----|
|false|Boolean|false는 대표적인 falsy한 값|
|0, -0, 0n, 0x0n|Number, BigInt|0은 부호나 소수점 유무에 상관없이 falsy한 값|
|NaN|Number|Number가 아니라는 것을 뜻하는 NaN(Not a Number)은 falsy한 값|
|'',"",``|String|문자열이 falsy하기 위해서는 반드시 공백이 없는 빈 문자열이어야 함|
|null|null|null은 falsy한 값|
|undefined|undefined|undefined는 falsy한 값|

💡 truthy: 조건문 내부에서 true로 취급되는 값. 앞에서 언급된 falsy로 취급되는 값 이외에는 모두 true로 취급된다. 한 가지 유념할 점은 객체와 배열은 내부에 값이 존재하는지 여부와 상관없이 truthy로 취급된다는 것이다. 즉, {}, [] 모두 truthy한 값이다.

```javascript
if(1) {
    // true
}

if (0) {
    // false
}

if (NaN) {
    // false
}

// 조건문 외에도 truthy와 falsyfmf Boolean()을 통해서 확인할 수 있다.
Boolean(1) // true
Boolean(0) // false
```

###### 우선순위를 두어 다음절부터 기록

---
### 1.2 함수
* JavaScript에서 함수란 작업을 수행하거나 값을 계산하는 등의 과정을 표현하고, 이를 하나의 블록으로 감싸서 실행 단위로 만들어 놓는 것을 의미.
```javascript
function sum(a, b) {
    return a + b
}

sum(11, 7)    // 18
```
* 리액트에서 컴포넌트를 만드는 함수도 이러한 기초적인 형태를 따르는 것을 알 수 있다.
```javascript
fuction Component(props){
    return <div>{props.hello}</div>
}
```
* Component라는 함수를 선언하고 매개변수로는 일반적으로 props라고 부르는 단일 객체를 받으며 return문으로 JSX를 반환한다.
* 일반적으로 함수와 차이점은, JavaScript에서는 Component(props) 형태로 호출하지만, 리액트에서의 함수형 컴포넌트는 <Component hello={props.hello}... />와 같이 JSX 문법으로 단일 props별로 받거나, <Component {...props} /> 같은 형태로 모든 props를 전개 연산자로 받는다는 차이가 있다.
* 이러한 JSX 형태 외에도 일반적인 JavaScript 문법으로 함수형 컴포넌트를 호출하는 것도 가능하다.






