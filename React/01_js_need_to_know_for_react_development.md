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
#### undefined
* undefined는 선언한 후 값을 할당하지 않은 변수 또는 값이 주어지지 않은 인수에 자동으로 할당되는 값이다.
```javascript
let dog

typeof dog === 'undefined'    // true

fuction cat(hello) {
    return hello
}

typeof cat() === 'undefined'  // true
```
#### null
* 아직 값이 없거나 비어 있는 값을 표현할 때 사용한다.
```javascript
typeof null === 'object'  // true?
```
* null의 특별한 점, 다른 원시값과 다르게 typeof로 null을 확인했을 때 해당 타입이 아닌 'object'라는 결과가 반환된다는 것이다. 이는 초창기 자바스크립트가 값을 표현하는 방식 때문에 발생한 문제로, 이후에 typeof null 을 진짜 'null'로 표현하고자 하는 시도가 있었으나 이전 코드에서 작동할 수 없는 호환성이 깨지는 변경 사항(breaking change)이어서 받아들여지지 않았다.

💡 undefined는 '선언됐지만 할당되지 않은 값'이고, null은 '명시적으로 비어 있음을 나타내는 값'으로 사용하는 것이 일반적이다.

#### boolean


