## 00. 자바스크립트의 핵심 개념
> 벨로퍼트와 함께하는 모던 자바스크립트


### 📌 연산자, 연산 순서
* 순서는 NOT -> AND -> OR 이다.

#### ex)
```JavaScript
const value = !((true && false) || (true && false) || !false);
```
괄호 안에 있는 코드부터 처리를 시작. 우선 NOT을 처리.
```JavaScript
!((true && false) || (true && false) || true);
```
다음으로 AND를 처리.
```javaScript
!(false || false || true);
```
OR 처리.
```JavaScript
!true;
```
---

### 📌 Truthy and Faisy
[01장 리액트 개발을 위해 꼭 알아야 할 자바스크립트 - 자바스크립트의 데이터 타입 boolean](https://github.com/ro117-youshin/TIL/blob/master/React/01_js_need_to_know_for_react_development.md#-boolean)

---

### 📌 단축 평가(short-circuit evaluation) 논리 계산법
* 논리연산자를 사용할 때 무조건 boolean 값 (true, false)를 사용해야 하는 것은 아니다. 문자열이나 숫자, 객체를 사용할 수도 있고, 해당 값이 Truthy 하냐 Falsy 하냐에 따라 달라진다.

#### ex)
```JavaScript
const dog = {
  name: '오트'
};

function getName(animal) {
  return animal.name;
}

const name = getName(dog);
console.log(name); // 오트
```
위 코드에서 만약, getName()의 파라미터에 객체가 주어지지 않는다면?
```JavaScript
const dog = {
  name: '오트'
};

function getName(animal) {
  return animal.name;
}

const name = getName();
console.log(name); 
```
animal 객체가 undefined 이기 때문에, undefined 에서 name 을 조회 할 수 없어 에러가 발생.
```JavaScript
const dog = {
  name: '오트'
};

function getName(animal) {
  if (animal) {
      return animal.name;
    }
    return undefined;
}

const name = getName();
console.log(name); 
```
위 코드와 같이 하면 animal 값이 주어지지 않아도 에러가 발생하지 않는다. 더 코드를 단축시켜보자.

#### 💡 && 연산자로 코드 단축시키기
* A && B 연산자를 사용하게 될 때에는 A 가 Truthy 한 값이라면, B 가 결과값이 된다.
* 반면, A 가 Falsy 한 값이라면 결과는 A가 된다.

```JavaScript
const dog = {
  name: '오트'
};

function getName(animal) {
  return animal && animal.name;
}

const name = getName();
console.log(name); // undefined
```
```JavaScript
const dog = {
  name: '오트'
};

function getName(animal) {
  return animal && animal.name;
}

const name = getName(dog);
console.log(name); // 오트
```
```JavaScript
console.log(true && "hello"); // hello
console.log(false && "hello"); // false
console.log("hello" && "bye"); // bye
console.log(null && "hello"); // null
console.log(undefined && "hello"); // undefined
console.log("" && "hello"); // ''
console.log(0 && "hello"); // 0
console.log(1 && "hello"); // hello
```

#### 💡 || 연산자로 코드 단축시키기
* A || B 는 만약 A 가 Truthy 할 경우 결과는 A 가 된다.
* 반면, A 가 Falsy 하다면 결과는 B가 된다.

#### ex)
```JavaScript
const namelessDog = {
  name: ''
};

function getName(animal) {
  const name = animal && animal.name;
  if (!name) {
    return '이름이 없는 동물입니다';
  }
  return name;
}

const name = getName(namelessDog);
console.log(name); // 이름이 없는 동물입니다.
```
위 코드를 || 연산자를 사용하여 단축시키면,
```JavaScript
const namelessDog = {
  name: ''
};

function getName(animal) {
  const name = animal && animal.name;
  return name || '이름이 없는 동물입니다.';
}

const name = getName(namelessDog);
console.log(name); // 이름이 없는 동물입니다.
```
```JavaScript
console.log(trueValue || "hello") // true
console.log(falseValue || "hello") // hello
console.log("hello" || "bye") // hello
console.log(null || "hello") // hello
console.log(undefined || "hello") // hello
console.log("" || "hello") // hello
console.log(0 || "hello") // hello
console.log(1 || "hello") // 1
```

---
