## 00. 자바스크립트의 핵심 개념
> [벨로퍼트와 함께하는 모던 자바스크립트](https://learnjs.vlpt.us/)

> 알고 있으면 유용한 자바스크립트 문법
>> 연산자, 연산 순서
>> 
>> Truthy and Faisy
>> 
>> 단축 평가(short-circuit evaluation) 논리 계산법
>> 
>> 조건문 더 스마트하게 쓰기
>>
>> 비구조화 할당 (구조분해)
>>
>> spread와 rest
>>
> 자바스크립트에서 비동기 처리 다루기
>> Promise
>>
>> async/await

## 알고 있으면 유용한 자바스크립트 문법

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

---

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

#### 💡 rest
* 객체, 배열, 함수의 파라미터에서 사용 가능.
* 객체와 배열에서 사용 할 때는 비구조화 할당 문법과 함께 사용된다.
* 주로 rest 라는 키워드를 사용하게 되는데, 추출한 값의 이름이 꼭 rest 일 필요는 없다.

#### ex) 객체에서의 rest
```JavaScript
const whiteCuteDog = {
  name: "oat",
  attribute: "cute",
  color: "white",
};

const { color, ...rest } = whiteCuteDog;

console.log(color); // white
console.log(rest);  // { name: "oat", attribute: "cute" }
```
attribute 를 없앤 새로운 객체를 만들고 싶다면
```JavaScript
const { attribute, ...rest } = whiteCuteDog;

console.log(attribute); // cute
console.log(rest);  // { name: "oat", color: "white" }
```

#### ex) 배열에서의 rest
* 파라미터로 넣어준 모든 값들을 합해주는 함수를 만든다.
* 아래의 sum 함수는 7개의 파라미터를 받아오는데, 실제 사용할 때는 6개만 넣어줬다. 그러면, g 값이 undefined 가 되기 때문에 더하는 과정에서 += undefined 를 하게 되면 결과는 NaN 이 되버린다.
* 함수의 파라미터가 몇개가 될 지 모르는 상황에서 rest 파라미터를 사용하면 매우 유용하다.

```JavaScript
function sum(a, b, c, d, e, f, g) {
  let sum = 0;
  if (a) sum += a;
  if (b) sum += b;
  if (c) sum += c;
  if (d) sum += d;
  if (e) sum += e;
  if (f) sum += f;
  if (g) sum += g;
  return sum;
}

const result = sum(1, 2, 3, 4, 5, 6);
console.log(result);
```

```JavaScript
function sum(...rest) {
  return rest;
}

const result = sum(1, 2, 3, 4, 5, 6);
console.log(result);  // [1, 2, 3, 4, 5, 6]
```
ex) reduce 함수 사용.
```JavaScript
function sum(...rest) {
  return rest.reduce((acc, current) => acc + current, 0);
}

const result = sum(1, 2, 3, 4, 5, 6);
console.log(result); // 21
```

#### 💡 함수 인자와 spread
* 위의 함수파라미터와 rest 를 사용한 것과 비슷한데, 반대의 역할.

#### ex) 배열 안에 있는 원소들을 모두 파라미터로 넣어주고 싶을 때
```JavaScript
function sum(...rest) {
  return rest.reduce((acc, current) => acc + current, 0);
}

const numbers = [1, 2, 3, 4, 5, 6];
const result = sum(
  numbers[0],
  numbers[1],
  numbers[2],
  numbers[3],
  numbers[4],
  numbers[5]
);
console.log(result);
```
sum 함수를 사용 할 때 인자 부분에 spread 를 사용하면
```JavaScript
function sum(...rest) {
  return rest.reduce((acc, current) => acc + current, 0);
}

const numbers = [1, 2, 3, 4, 5, 6];
const result = sum(...numbers);
console.log(result);
```

---

## 자바스크립트에서 비동기 처리 다루기 
### 📌 자바스크립트의 동기적 처리와 비동기 처리
![img_1](https://github.com/ro117-youshin/TIL/blob/master/JavaScript/img/sync_async_img.png)

* 만약 작업을 동기적으로 처리한다면 작업이 끝날 때까지 기다리는 동안 중지 상태가 되기 때문에 다른 작업을 할 수 없다.
* 작업이 끝나야 비로소 다음 예정된 작업을 할 수 있다.
* 하지만 비동기적으로 처리를 한다면 흐름이 멈추지 않기 때문에 동시에 여러 가지 작업을 처리할 수도 있고, 기다리는 과정에서 다른 함수도 호출할 수 있다.
* 아래의 코드를 통해 이해해보자.

#### ex) 연산량 많은 작업을 처리하는 함수 ```work()``` 
```javascript
function work() {
  const start = Date.now();
  for (let i = 0; i < 1000000000; i++) {}
  const end = Date.now();
  console.log(end - start + "ms");
}

work();
console.log("다음 작업");
```
![img_2](https://github.com/ro117-youshin/TIL/blob/master/JavaScript/img/work_01.jpeg)
* 위 코드는 ```work()``` 함수가 호출되면, for문이 돌아가는 동안 다른 작업은 처리하지 않고 온전히 for문만 실행한다.
* 만약 이 작업이 진행되는 동안 다른 작업도 하고 싶다면 함수를 비동기 형태로 전환을 해주어야 한다. 그렇게 하기 위해서 ```setTimeout``` 이라는 함수를 사용해 주어야 한다.

#### ex) ```setTimeout``` 함수 사용
```javascript
function work() {
  setTimeout(() => {
    const start = Date.now();
    for (let i = 0; i < 1000000000; i++) {}
    const end = Date.now();
    console.log(end - start + 'ms');
  }, 0);
}

console.log('작업 시작!');
work();
console.log('다음 작업');
```
* ```setTimeout``` 함수는 첫 번째 파라미터에 넣는 함수를 두 번째 파라미터에 넣은 시간(ms 단위)이 흐른 후 호출해준다.
* 위 ```work()``` 함수는 바로 실행이 되는데 실제로는 4ms 이후에 실행된다. [setTimeout() - 딜레이가 지정한 값보다 더 긴 이유](https://developer.mozilla.org/ko/docs/Web/API/setTimeout#%EB%94%9C%EB%A0%88%EC%9D%B4%EA%B0%80_%EC%A7%80%EC%A0%95%ED%95%9C_%EA%B0%92%EB%B3%B4%EB%8B%A4_%EB%8D%94_%EA%B8%B4_%EC%9D%B4%EC%9C%A0)
* 이렇게 ```setTimeout``` 을 이용하면 개발자가 정한 작업이 백그라운드에서 수행되기 때문에 기존의 코드 흐름을 막지 않고 동시에 다른 작업들을 진행할 수 있다.

![img_03](https://github.com/ro117-youshin/TIL/blob/master/JavaScript/img/work_02.jpeg)
* 결과를 보면 작업이 시작되고 나서 for 문이 돌아가는 동안 다음 작업도 실행되고, for 문이 끝나고 몇 ms 가 걸렸는지 나타나고 있다.
* 만약에 ```work()``` 함수가 끝난 다음에 어떤 작업을 처리하고 싶다면 콜백 함수를 파라미터로 전달해주면 된다.
* 콜백 함수란, 함수 타입의 값을 파라미터로 넘겨줘서 파라미터로 받은 함수를 특정 작업이 끝나고 호출을 해주는 것을 의미한다.

#### ex) 동기 처리
```javascript
function work(callback) {
  setTimeout(() => {
    const start = Date.now();
    for (let i = 0; i < 1000000000; i++) {}
    const end = Date.now();
    console.log(end - start + 'ms');
    callback();
  }, 0);
}

console.log('작업 시작!');
work(() => {
  console.log('작업이 끝났어요!')
});
console.log('다음 작업');
```
![img_4](https://github.com/ro117-youshin/TIL/blob/master/JavaScript/img/work_03.jpeg)

#### 💡 비동기적으로 처리하는 작업의 예시
* Ajax Web API 요청: 만약 서버쪽에서 데이터를 받아와야 할 때에는 요청을 하고 서버에서 응답을 할 때까지 대기를 해야 되기 때문에 작업을 비동기로 처리한다.
* 파일 읽기: 주로 서버 쪽에서 파일을 읽어야 하는 상황에는 비동기로 처리한다.
* 암호화/복호화: 암/복호화를 할 때에도 바로 처리가 되지 않고, 시간이 어느정도 걸리는 경우가 있기 때문에 비동기로 처리한다.
* 작업 예약: 단순히 어떤 작업을 몇 초 후에 스케줄링 해야 하는 상황에는 ```setTimeout``` 을 사용하여 비동기로 처리한다.

### 📌 Promise
* 비동기 작업을 조금 더 편하게 처리 할 수 있도록 ES6 에 도입된 기능이다.
* 이전에는 비동기 작업을 처리 할 때에는 콜백 함수로 처리를 해야 했었다. 콜백 함수로 처리를 하게 되면 비동기 작업이 많아질 경우 코드가 난잡해지게 된다.

#### ex) Callback Hell
###### 한 번 숫자 n을 파라미터로 받아와서 다섯번에 걸쳐 1초마다 1씩 더해 출력하는 작업을 setTimeout으로 구현.
```javascript
function increaseAndPrint(n, callback) {
  setTimeout(() => {
    const increased = n + 1;
    console.log(increased);
    if (callback) {
      callback(increased);
    }
  }, 1000);
}

increaseAndPrint(0, n => {
  increaseAndPrint(n, n => {
    increaseAndPrint(n, n => {
      increaseAndPrint(n, n => {
        increaseAndPrint(n, n => {
          console.log('끝!');
        });
      });
    });
  });
});
```
* 비동기적으로 처리해야 하는 일이 많아질수록, 코드의 깊이가 계속 깊어지는 현상.
* Promise를 사용하면 위와 같이 코드의 깊이가 깊어지는 현상을 방지할 수 있다.

#### Promise 만들기
```javascript
const myPromise = new Promise((resolve, reject) => {
  // 구현...
});
```

* Promise는 성공할 수도, 실패할 수도 있다.
  * 성공할 때에는 resolve 를 호출하고, 실패할 때에는 reject 를 호출한다.

#### ex) 1초 뒤에 성공시키는 상황을 구현.
```javascript
const myPromise = new Promise((resolve, reject) => {
  setTimeout(() => {
    resolve(1);
  }, 1000);
});

myPromise.then(n => {
  console.log(n);
});
```
* resolve 를 호출할 때 특정값을 파라미터로 넣어주면, 이 값을 작업이 끝나고 나서 사용할 수 있다.
* 작업이 끝나고 나서 또 다른 작업을 해야할 때에는 Promise 뒤에 *.ther(...)* 을 붙여 사용한다.

#### ex) 1초 뒤에 실패시키는 상황을 구현.
```javascript
const myPromise = new Promise((resolve, reject) => {
  setTimeout(() => {
    reject(new Error());
  }, 1000);
});

myPromise
  .then(n => {
    console.log(n);
  })
  .catch(error => {
    console.log(error);
  });
```
* 실패하는 상황에서는 reject 를 사용하고, *.catch* 를 통하여 실패했을시 수행할 작업을 설정할 수 있다.

#### ex) Promise 를 만드는 함수 작성.
```javascript
function increaseAndPrint(n) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const value = n + 1;
      if (value === 5) {
        const error = new Error();
        error.name = 'ValueIsFiveError';
        reject(error);
        return;
      }
      console.log(value);
      resolve(value);
    }, 1000);
  });
}

increaseAndPrint(0).then((n) => {
  console.log('result: ', n);
})
```
```javascript
1
result: 1
```
* 결국 함수를 전달하는 것으로 뭐가 다른가?
* 하지만, Promise 의 속성 중에는, 만약 then 내부에 넣은 함수에서 또 Promise 를 리턴하게 된다면, 연달아서 사용할 수 있다.
* 아래의 코드로 살펴보자.

```javascript
function increaseAndPrint(n) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const value = n + 1;
      if (value === 5) {
        const error = new Error();
        error.name = 'ValueIsFiveError';
        reject(error);
        return;
      }
      console.log(value);
      resolve(value);
    }, 1000);
  });
}

increaseAndPrint(0)
  .then(n => {
    return increaseAndPrint(n);
  })
  .then(n => {
    return increaseAndPrint(n);
  })
  .then(n => {
    return increaseAndPrint(n);
  })
  .then(n => {
    return increaseAndPrint(n);
  })
  .then(n => {
    return increaseAndPrint(n);
  })
  .catch(e => {
    console.error(e);
  });
```

#### 위 코드를 다시 정리하면.
* .then 에서 새로 함수를 선언하지 않고 increaseAndPrint 를 넣어주면 앞서 호출한 increaseAndPrint 의 결과값을 다시 increaseAndPrint 의 인자로 넣어서 호출하게 된다.

```javascript
function increaseAndPrint(n) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const value = n + 1;
      if (value === 5) {
        const error = new Error();
        error.name = 'ValueIsFiveError';
        reject(error);
        return;
      }
      console.log(value);
      resolve(value);
    }, 1000);
  });
}

increaseAndPrint(0)
  .then(increaseAndPrint)
  .then(increaseAndPrint)
  .then(increaseAndPrint)
  .then(increaseAndPrint)
  .then(increaseAndPrint)
  .catch(e => {
    console.error(e);
  });
```

* 하지만 이것도 불편한 점이 있다. 에러를 잡을 때 몇 번째에서 발생했는지 알아내기도 어렵고 특정 조건에 따라 분기를 나누는 작업도 어렵고 특정 값을 공유해가면서 작업을 처리하기도 까다롭다.
* async/await 을 사용하면 위와 같은 문제점을 깔끔하게 해결할 수 있다.

### 📌 async/await
* async/await 문법은 ES8에 해당하는 문법으로서, Promise 를 더욱 쉽게 사용 할 수 있게 해준다.

#### 기본적인 사용법
```javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function process() {
  console.log('안녕하세요!');
  await sleep(1000); // 1초쉬고
  console.log('반갑습니다!');
}

process();
```
* Promise ```sleep``` 의 앞부분에 ```await``` 을 넣어주면 해당 Promise가 끝날 때까지 기다렸다가 다음 작업을 수행할 수 있다.
* 위 코드에서는 ```sleep``` 함수를 만들어서 파라미터로 넣어준 시간 만큼 기다리는 Promise 를 만들고, 이를 ```process``` 함수에서 사용해주었다.
* 함수 ```async``` 를 사용하면, 해당 함수는 결과값으로 Promise 를 반환한다. (아래 코드에서 확인.)

#### ex) ```async``` 사용으로 Promise 결과값 반환
```javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function process() {
  console.log('안녕하세요!');
  await sleep(1000); // 1초쉬고
  console.log('반갑습니다!');
}

process().then(() => {
  console.log('작업이 끝났어요!');
});
```

#### ex) ```async``` 에서 ```throw``` 와 ```try/catch``` 사용
* ```async``` 함수에서 에러를 발생 시킬 때에는 ```throw```
* 에러를 잡아낼 때에는 ```try/catch```

```javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function makeError() {
  await sleep(1000);
  const error = new Error();
  throw error;
}

async function process() {
  try {
    await makeError();
  } catch (e) {
    console.error(e);
  }
}

process();
```

#### ex) 비동기 함수 만들어보기

```javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

const getDog = async () => {
  await sleep(1000);
  return '멍멍이';
};

const getRabbit = async () => {
  await sleep(500);
  return '토끼';
};
const getTurtle = async () => {
  await sleep(3000);
  return '거북이';
};

async function process() {
  const dog = await getDog();
  console.log(dog);
  const rabbit = await getRabbit();
  console.log(rabbit);
  const turtle = await getTurtle();
  console.log(turtle);
}

process();
```
* 현재 위 코드에서는 ```getDog``` 는 1초, ```getRabbit``` 은 0.5초, ```getTurtle``` 은 3초가 걸리고 있다.
* 이 함수들을 ```process``` 함수에서 연달아서 사용하게 되면서, ```process``` 함수가 실행되는 총 시간은 4.5초가 된다.
* 지금은 ```getDog``` -> ```getRabbit``` -> ```getTurtle``` 순서대로 실행이 되고 있다. 하나가 끝나야 다음 작업이 시작하고 있는데, 동시에 작업을 시작하고 싶다면 아래와 같이 ```Promise.all``` 을 사용해야 한다.

#### 💡 ```Promise.all``` 사용
```javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

const getDog = async () => {
  await sleep(1000);
  return '멍멍이';
};

const getRabbit = async () => {
  await sleep(500);
  return '토끼';
};
const getTurtle = async () => {
  await sleep(3000);
  return '거북이';
};

async function process() {
  const results = await Promise.all([getDog(), getRabbit(), getTurtle()]);
  console.log(results);
}

process();
```

#### ex) 배열 비구조화 할당 문법을 사용하여 각각의 결과값을 추출하기
```javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

const getDog = async () => {
  await sleep(1000);
  return '멍멍이';
};

const getRabbit = async () => {
  await sleep(500);
  return '토끼';
};
const getTurtle = async () => {
  await sleep(3000);
  return '거북이';
};

async function process() {
  const [dog, rabbit, turtle] = await Promise.all([
    getDog(),
    getRabbit(),
    getTurtle()
  ]);
  console.log(dog);
  console.log(rabbit);
  console.log(turtle);
}

process();
```
* ```Promise.all``` 을 사용 할 때는, 등록한 프로미스 중 하나라도 실패하면 모든게 실패 한 것으로 간주한다.

#### 💡 ```Promise.race``` 사용
* ```Promise.all``` 과 다르게 여러 개의 프로미스를 등록해서 실행했을 때 가장 빨리 끝난 것 하나만의 결과값을 가져온다.

```javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

const getDog = async () => {
  await sleep(1000);
  return '멍멍이';
};

const getRabbit = async () => {
  await sleep(500);
  return '토끼';
};
const getTurtle = async () => {
  await sleep(3000);
  return '거북이';
};

async function process() {
  const first = await Promise.race([
    getDog(),
    getRabbit(),
    getTurtle()
  ]);
  console.log(first);
}

process();
```
* ```Promise.race``` 의 경우엔 가장 다른 Promise 가 먼저 성공하기 전에 가장 먼저 끝난 Promise 가 실패하면 이를 실패로 간주한다.
* 위 코드에서 ```getRabbit``` 에서 에러를 발생시킨다면 에러를 잡아낼 수 있지만, ```getTurtle``` 이나 ```getDog``` 에서 발생한 에러는 무시된다.







