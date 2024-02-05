## 00. 자바스크립트의 핵심 개념
> [벨로퍼트와 함께하는 모던 자바스크립트](https://learnjs.vlpt.us/)

> 연산자, 연산 순서
> 
> Truthy and Faisy
> 
> 단축 평가(short-circuit evaluation) 논리 계산법
> 
> 조건문 더 스마트하게 쓰기
>
> 비구조화 할당 (구조분해)
>
> spread와 rest

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

#### ex)
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

### 📌 조건문 더 스마트하게 쓰기
#### 💡 includes 함수: 특정 값이 여러 값중 하나인지 확인해야 할 때
#### ex)
```JavaScript
function isAnimal(text) {
  return (
    text === '고양이' || text === '개' || text === '거북이' || text === '너구리'
  );
}

console.log(isAnimal('개')); // true
console.log(isAnimal('노트북')); // false
```
배열의 includes 함수를 사용.
```JavaScript
function isAnimal(name) {
  const animals = ['고양이', '개', '거북이', '너구리'];
  return animals.includes(name);
}

console.log(isAnimal('개')); // true
console.log(isAnimal('노트북')); // false
```
animals 배열 선언 생략, 화살표 함수 사용.
```JavaScript
const isAnimal = name => ['고양이', '개', '거북이', '너구리'].includes(name);

console.log(isAnimal('개')); // true
console.log(isAnimal('노트북')); // false
```
#### 💡 값에 따라 다른 결과물을 반환 해야 할 때
#### ex)
```JavaScript
function getSound(animal) {
  if (animal === '개') return '멍멍!';
  if (animal === '고양이') return '야옹~';
  if (animal === '참새') return '짹짹';
  if (animal === '비둘기') return '구구 구 구';
  return '...?';
}

console.log(getSound('개')); // 멍멍!
console.log(getSound('비둘기')); // 구구 구 구
```
위 코드를 switch case문으로
```JavaScript
function getSound(animal) {
  switch (animal) {
    case '개':
      return '멍멍!';
    case '고양이':
      return '야옹~';
    case '참새':
      return '짹짹';
    case '비둘기':
      return '구구 구 구';
    default:
      return '...?';
  }
}

console.log(getSound('개')); // 멍멍!
console.log(getSound('비둘기')); // 구구 구 구
```
객체를 활용
```JavaScript
function getSound(animal) {
  const sounds = {
    개: '멍멍!',
    고양이: '야옹~',
    참새: '짹짹',
    비둘기: '구구 구 구'
  };
  return sounds[animal] || '...?';
}

console.log(getSound('개')); // 멍멍!
console.log(getSound('비둘기')); // 구구 구 구
```
반면, 값에 따라 실행해야 하는 코드 구문이 다를 때는 객체에 함수를 넣으면 된다.
```JavaScript
function makeSound(animal) {
  const tasks = {
    개() {
      console.log('멍멍');
    },
    고양이() {
      console.log('고양이');
    },
    비둘기() {
      console.log('구구 구 구');
    }
  };
  if (!tasks[animal]) {
    console.log('...?');
    return;
  }
  tasks[animal]();
}

makeSound("개");  // 멍멍
makeSound("비둘기"); // 구구 구 구
makeSound("하이");  // ...?
```

---

### 📌 비구조화 할당 (구조분해) 

#### 💡 객체 비구조화 할당 (객체 구조 분해)
```JavaScript
const ironMan = {
  name: '토니 스타크',
  actor: '로버트 다우니 주니어',
  alias: '아이언맨'
};

const captainAmerica = {
  name: '스티븐 로저스',
  actor: '크리스 에반스',
  alias: '캡틴 아메리카'
};

function print(hero) {
  const text = `${hero.alias}(${hero.name}) 역할을 맡은 배우는 ${
    hero.actor
  } 입니다.`;
  console.log(text);
}

print(ironMan);
print(captainAmerica);
```
객체에서 값들을 추출해서 새로운 상수로 선언.
```JavaScript
function print(hero) {
  const { alias, name, actor } = hero;
  const text = `${alias}(${name}) 역할을 맡은 배우는 ${actor} 입니다.`;
  console.log(text);
}
```
파라미터 단계에서 객체 비구조화 할당.
```JavaScript
function print({ alias, name, actor }) {
  const text = `${alias}(${name}) 역할을 맡은 배우는 ${actor} 입니다.`;
  console.log(text);
}
```

#### 💡 비구조화 할당시 기본값 설정
파라미터에서.
```JavaScript
const object = { a: 1 };

function print({ a, b = 2 }) {
    console.log(a);
    console.log(b);
}

print(object);
// 1
// 2
```
상수 선언할 때.
```JavaScript
const object = { a: 1 };

const { a, b = 2 } = object;

console.log(a); // 1
console.log(b); // 2
```

#### 💡 비구조화 할당시 이름 바꾸기
: 문자를 사용하여 이름 바꾸기.
```JavaScript
const animal = {
    name: 'oat',
    type: 'dog'  
};

const nickname = animal.name;

console.log(nickname); // oat
```
아래의 코드는 'animal 객체 안에 있는 name 을 nickname 이라고 선언하겠다.' 라는 의미.
```JavaScript
const { name: nickname } = animal
```

#### 💡 배열 비구조화 할당
배열 안에 있는 원소를 다른 이름으로 새로 선언해주고 싶을 때.
```JavaScript
const array = [1, 2];
const [one, two] = array;

console.log(one);
console.log(two);
```
객체 비구조화 할당과 마찬가지로, 기본값 지정이 가능.
```JavaScript
const array = [1];
const [one, two = 2] = array;

console.log(one);
console.log(two);
```

#### 💡 깊은 값 비구조화 할당
첫 번째, 비구조화 할당 문법을 두 번 사용.
```JavaScript
const deepObject = {
  state: {
    information: {
      name: "youshin",
      languages: ["korean", "english", "chinese"],
    },
  },
  value: 117,
};

// 비구조화 할당 두 번...
const { name, languages } = deepObject.state.information;
const { value } = deepObject;

const extracted = {
  name,
  languages,
  value
};

console.log(extracted); 
```
두 번째, 한 번에 다 추출하기.
```JavaScript
// 한번에 추출하기...
const {
  state: {
    information: { name, languages }
  },
  value
} = deepObject;
```

### 📌 spread와 rest
#### 💡 Spread
* 의미: '펼치다', '퍼뜨리다'
* 객체 혹은 배열을 펼칠 수 있다.

#### ex) 객체 펼치기.
아래 코드에서 핵심은, 기존의 것은 건들이지 않고, 새로운 객체를 만드는 것이다. 이러한 상황에서 유용한 문법이 spread 이다.

```JavaScript
const dog = {
  name: "oat",
};

const cuteDog = {
  name: "oat",
  attribute: "cute",
};

const whiteCuteDog = {
  name: "oat",
  attribute: "cute",
  color: "white",
};
```
```JavaScript
const dog = {
  name: "oat",
};

const cuteDog = {
  ...dog
  attribute: "cute",
};

const whiteCuteDog = {
  ...cuteDog
  color: "white",
};
```
#### ex) 배열 펼치기.
```JavaScript
const color = ["red", "green", "blue"];
const anotherColor = [...color, "yellow"];

console.log(anotherColor); // (4) ['red', 'green', 'blue', 'yellow']
```
#### ex) spread 연산자 여러번 사용 가능
```JavaScript
const spreadColor = [...color, "yellow", ...color];

console.log(anotherColor); // (7) ['red', 'green', 'blue', 'yellow', 'red', 'green', 'blue']
```

