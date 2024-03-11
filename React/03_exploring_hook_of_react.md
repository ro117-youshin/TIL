## 03장 리액트 훅 깊게 살펴보기
> '모던 리액트 Deep Dive - 김용찬 03장' 학습
>
> 3.1 리액트의 모든 훅 파헤치기
>
>> 3.1.1 useState

### 3.1 리액트의 모든 훅 파헤치기
* React 함수형 컴포넌트에서 가장 중요한 개념.
* 클래스형 컴포넌트에서만 가능했던 state, ref 등 React의 핵심적인 기능을 함수에서도 가능하게 만들었다.

### 3.1.1 useState
* useState는 함수형 컴포넌트 내부에서 상태를 정의하고, 이 상태를 관리할 수 있게 해주는 Hook.

#### useState 기본 사용법
```javascript
import { useState } from 'React';

const [state, setState] = useState(initialState)
```
* useState의 인수로는 사용할 state의 초깃값을 넘겨준다. (아무런 값도 넘겨주지 않는다면 ```undefined```)
* useState Hook의 반환 값은 배열, 배열의 첫 번째 원소로 state 값 자체를 사용할 수 있고, 두 번째 원소인 setState 함수를 사용해 해당 state 값을 변경할 수 있다.

#### 💡 게으른 초기화(lazy initiallization)
* 일반적으로 useState에서 기본값을 선언하기 위해 useState() 인수로 원시값을 넣는 경우가 대부분.
* 그러나 인수로 특정한 값을 넘기는 함수를 인수로 넣어줄 수도 있음.
* useState에 <ins>변수 대신 함수를 넘기는 것</ins>을 게으른 초기화라 한다.

###### code로 살펴보기
```javascript
// 일반적인 useState 사용
// 바로 값을 넣는다.
const [count, seCount] = useState(
    Number.parseInt(window.localStorage.getItem(cacheKey)),
)

// 게으른 초기화
// 위 코드와의 차이점, 함수를 실행해 값을 반환한다.
const [count, setCount] = useState(() =>
    Number.parseInt(window.localStorage.getItem(cacheKey)),
)
```
* React 공식 문서에서 게으른 초기화는 useState의 초깃값이 복잡하거나 무거운 연산을 포함하고 있을 때 사용하라고 돼 있다.
* 이 게으른 초기화 함수는 오로지 state가 처음 만들어질 때만 사용된다.
* 만약 리렌더링이 발생된다면 이 함수의 실행은 무시된다.

###### ex) 게으른 초기화 예제
```javascript
import { useState } from 'react';

export default function App() {
    const [state, setState] = useState(() => {
        console.log('복잡한 연산...') // App 컴포넌트가 처음 구동될 때만 실행되고, 이후 리렌더링 시에는 실행되지 않는다.

        return 0
    })

    function handleClick() {
        setState((prev) => prev + 1)
    }

    return (
        <div>
            <h1>{state}</h1>
            <button onClick={handleClick}>+</button>
        </div>
    )
}
```

* 게으른 초기화는 언제 사용하는 것이 좋을까?
  * React에서는 무거운 연산이 요구될 때 사용하라고 한다.
  * 즉, ```localStorage``` 나 ```sessionStorage``` 에 대한 접근, ```map``` , ```filter``` , ```find``` 같은 배열에 대한 접근, 혹은 초깃값 계산을 위해 함수 호출이 필요할 때와 같이 무거운 연산을 포함해 실행 비용이 많이 드는 경우에 사용하는 것이 좋다.
