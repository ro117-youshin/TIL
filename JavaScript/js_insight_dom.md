# DOM(The Document Object Model) - 문서 객체 모델
> 문서 객체 모델 DOM과 자바스크립트 JavaScript | 생성 방식 및 접근 방법 - 코드

### DOM(문서 객체 모델)이란?
* 문서 객체 모델, 즉 DOM은 웹페이지(HTML이나 XML 문서)의 콘텐츠 및 구조, 그리그 스타일 요소를 구조화 시켜 표현하여 프로그래밍 언어가 해당 문서에 접근하여 읽고 조작할 수 있도록 API를 제공하는 일종의 인터페이스이다.
* 즉 JavaScript 같은 스크립트 언어가 쉽게 웹 페이지에 접근하여 조작할 수 있게끔 연결시켜주는 역할을 담당한다.

### DOM은 어떻게 생성되고 어떻게 보여질까?
* DOM은 웹 페이지, 즉 HTML 문서를 계층적 구조와 정보로 표현하며, 이를 제어할 수 있는 프로퍼티와 메서드를 제공하는 트리 자료구조이기도 하다. 따라서 HTML DOM, 혹은 HTML DOM Tree로 부르기도 한다.
* 트리 자료구조로 구축이 되기 때문에, HTML 문서는 최종적으로 하나의 최상위 노드(root 노드)에서 시작해 자식 노드들을 가지며, 아래로만 뻗어나가는 구조로 만들어지게 된다.

*트리 자료구조는 노드들의 계층 구조로 이루어져 있다. 계층 구조로 이루어져 있기 때문에 부모-자식 관계, 형제관계를 표현하는 비선형 자료구조를 나타낸다.*

#### Ex) H2 태그가 들어있는 웹페이지
![image](https://github.com/ro117-youshin/TIL/assets/86038910/f0b1e190-3ee6-4a7e-bbb6-c1adace0c6f0)

###### code
![image](https://github.com/ro117-youshin/TIL/assets/86038910/26f1e025-e626-47a1-ae9f-8293046a62e2)

###### 해당 문서를 트리 구조로 표현한다면
![image](https://github.com/ro117-youshin/TIL/assets/86038910/7a62012d-4037-4260-b8d1-edb47a3e45ec)
* document 노드가 최상위 노드가 되고, 밑으로 element 노드가 오며, 이어 text 노드와 attribute 노드가 오는 계층적인 구조임을 알 수 있다.
* 이러한 노드 타입에는 총 12개가 있는데 가장 중요한 것은 위에서도 명시가 되어 있듯 총 4가지의 노드가 있다.

#### 1. document node (문서 노드)
* DOM Tree에서 최상위 루트 노드를 나타내며, document 객체를 가리킨다. HTML 문서 전체를 나타내는 노드이기도 하다.
* window 객체의 document 프로퍼티로 바인딩(연결)이 되어 있어 window.document, document로 참조해 사용할 수 있다.
* HTML 문서에 이 문서 노드는 오로지 1개만 존재한다.

#### 2. element node (요소 노드)
* 모든 HTML 요소 (body, h2, div 등)는 이 요소 노드이다.
* 속성 노드를 가질 수 있는 유일한 노드로서, 부모-자식 관계를 가지게 되기 때문에 계층적 구조를 이룰 수 있게 된다.

#### 3. attribute node (속성 노드)
* 모든 HTML 요소의 속성은 이 속성 노드이다.
* 요소 노드에 대한 정보를 가지고 있다. 그렇기 때문에 부모 노드가 아닌 해당 노드와 연결(바인딩)이 되어 있다.

#### 4. text node (텍스트 노드)
* HTML 문서의 모든 텍스트는 이 텍스트 노드라 해도 과언이 아니다.
* 텍스트 노드는 정보를 표현하며, 가장 마지막에 위치하는 자식 노드이기 때문에 잎사귀를 닮았다 해 리프 노드라고 불리기도 한다.

### DOM의 정적 생성과 동적 생성
* 지금 현재 브라우저에 내장되어 있는 언어는 자바스크립트이고, 자바스크립트는 가장 간편하고 빠르게 DOM으로 구조화된 웹 문서에 접근하여 노드(웹 컨텐츠를 이루는 기본 요소)들을 조작할 수 있다.
* 자바스크립트를 이용해 **HTML 문서에 없는 노드를 만들어 이어 붙여 웹 페이지에 렌더링되게 만드는 모든 과정**이 동적으로 구현하는 것이라 볼 수 있다. 또는 **자바스크립트를 이용해 있던 노드에 없는 노드를 만들어 이어 붙이는 것**도 동적으로 구현한다고 볼 수 있다.
* 정적으로 생성되는 과정은 **이미 HTML 파일에 적혀 있는 코드를 위에서부터 아래로 읽어내려가며 생성하는 과정**만을 뜻한다. 즉 **HTML 문서에 직접 태그로 작성하는 것**만을 정적으로 생성한다고 보기 때문에, 이런 부분에서 차이가 날 수밖에 없다.

![image](https://github.com/ro117-youshin/TIL/assets/86038910/260e4ad3-d54d-42c6-aa7e-1f897f983e6a)
###### *위의 코드를 읽어내려 웹페이지의 콘텐츠를 띄우는 과정은 정적 생성 과정이다. 여기에는 자바스크립트가 접근하여 DOM을 조작한 흔적이 없다.*
![image](https://github.com/ro117-youshin/TIL/assets/86038910/9ae79f76-0cbd-4417-b5e8-68544c68e05f)
###### *위의 스크립트 태그를 달아 외부의 자바스크립트 파일을 연결하고, h2 태그에 id를 달아 일련의 작업(예를 들어 h2 태그를 클릭하면 밑에 사진이 나타나는 등의 동작)을 하게 된다면 이것은 동적으로 노드를 생성한다고 보는 것이다.*

### DOM의 데이터타입(Datatype)
#### DOM 객체의 구성 요소
* **프로퍼티(property)** : DOM 객체의 멤버 변수이다. HTML 태그의 속성을 반영한다.
* **메소드(Method)** : DOM 객체의 멤버 함수이다. HTML 태그를 제어한다.
* **컬렉션(collection)** : 정보를 집합적으로 표현하는 일종의 배열이다. 예를 들어 children 컬랙션은 DOM 객체의 모든 자식 DOM 객체에 대한 주소를 가진다.
* **이벤트 리스너(event listener)** : HTML 태그에 작성된 이벤트 리스너(onclick, onchange 등)들을 그대로 가진다.
* **스타일(style)** : 이 프로퍼티를 통해 HTML 태그에 적용된 CSS 스타일 시트에 접근 가능하다.

#### DOM의 중요한 데이터 타입들
##### 데이터 타입에는 여러 개가 있는데, 데이터타입은 객체이기 때문에 각각 프로퍼티와 메소드를 가지고 있다.

#### document
```text
member(프로퍼티 혹은 메서드)가 document 타입의 object를 리턴할 때, 이 object는 root document object 자체이다.
예를 들어 element의 ownerDocument property(ex. document.getElementById("myP").ownerDocument)는 해당 프로퍼티가 속해 있는 document를 return 한다.
```
#### element
```text
element는 DOM API의 member에 의해 return된 element 또는 element 타입의 노드를 의미한다.
document.createElement() method가 node를 참조하는 object를 리턴한다고 말하는 대신,
이 method가 DOM 안에서 생성되는 element를 리턴한다고 좀 더 단순하게 말할 수 있다.
element 객체들은 DOM Element 인터페이스와 함께, 좀 더 기본적인 Node 인터페이스를 구현한 것이기 때문에
이 참조(reference)에는 두 가지가 모두 포함되었다고 생각하면 된다.
```
[DOM Element Reference](https://developer.mozilla.org/en-US/docs/Web/API/Element)

#### nodeList
```text
nodeList 는 element의 배열이다.
(document.getElementsByTagName() method에 의해 return 된 것 같다.)
nodeList의 Items는 index를 통해 접근 가능하며, 다음과 같이 두 가지 방식이 있다.

1. list.item(1)
2. list[1]

위의 방식들은 동일하다.
1번의 item() method는 nodeList object의 단일 method.
2은 list에서 두 번째 item을 가져오는(fetch) 전형적인 array 문법.
```
[DOM NodeList Reference](https://developer.mozilla.org/en-US/docs/Web/API/NodeList)

#### namedNodeMap
```text
namedNodeMap는 배열과 유사하지만 안의 요소에 접근할 때 name 또는 index로 접근한다.
리스트는 특별한 정렬이 적용되지 않았기 때문에 열거(enumeration)할 때 index를 주로 사용한다.
namedNodeMap은 이를 위해 item() method가 있으며, namedNodeMap에 item을 추가하거나 삭제할 수 있다.
```
[DOM NamedNodeMap Reference](https://developer.mozilla.org/en-US/docs/Web/API/NamedNodeMap)





