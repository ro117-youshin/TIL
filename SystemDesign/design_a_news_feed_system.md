# DESIGN A NEWS FEED SYSTEM (뉴스 피드 시스템 설계)
> 가상 면접으로 배우는 대규모 시스템 설계 기초(System Design Interview - An Insider's Guide: Alex Xu 번역본) <br>

---

### 🪜 STEP 1 - Understand the problem and establish design scope(문제 이해 및 설계 범위 확정)

&nbsp;면접관의 의도를 파악하는 질문과 답변 사례는 아래와 같다.

🙋🏻Candidate: 모바일 앱을 위한 시스템인가요? 아니면 웹? 둘 다 지원해야 하나요?<br>
🧑🏻‍💻Interviewer : 둘 다 지원해야 합니다.<br>

🙋🏻Candidate: 중요한 기능으로는 어떤 것이 있을까요?<br>
🧑🏻‍💻Interviewer : 사용자는 뉴스 피드 페이지에 새로운 스토리를 올릴 수 있어야 하고, 친구들이 올리는 스토리를 볼 수도 있어야 합니다.<br>

🙋🏻Candidate: 뉴스 피드에는 어떤 순서로 스토리가 표시되어야 하나요? 최신 포스트 순 또는 토픽 점수(topic score) 같은 기준이 있을까요?<br>
🧑🏻‍💻Interviewer : 단순 시간 흐름 역순(reverse chronological order)으로 표시된다고 가정합시다.<br>

🙋🏻Candidate: 한 명의 사용자는 최대 몇 명의 친구를 가질 수 있을까요?<br>
🧑🏻‍💻Interviewer : 5,000명 입니다.<br>

🙋🏻Candidate: 트래픽 규모는 어느 정도 입니까?<br>
🧑🏻‍💻Interviewer : 매일 천만 명이 방문한다고 가정합시다.<br>

🙋🏻Candidate: 피드에 이미지나 비디오 스토리도 올라올 수 있습니까?<br>
🧑🏻‍💻Interviewer : 스토리에 이미지나 비디오 등의 미디어 파일이 포함될 수 있습니다.<br>

---

### 🪜 STEP 2 - Propose high-level design and get buy-in(개략적 설계안 제시 및 동의 구하기)

&nbsp;파악한 요구사항을 토대로 시스템 설계를 시작할텐데, 아래의 두 가지 부분으로 설계안을 생각해보자.

(1) 피드 발행(feed publishing): 사용자가 스토리를 포스팅하면 해당 데이터를 캐시와 데이터베이스에 기록한다. 새 포스팅은 친구의 뉴스피드에도 전송된다.<br>
(2) 뉴스 피드 생성(news feed building): 지면 관계상 뉴스 피드는 모든 친구의 포스팅을 시간 흐름 역순으로 모아서 만든다고 가정한다.

#### 💡 뉴스 피드 API
&nbsp;뉴스 피드 API는 클라이언트가 서버와 통신하기 위해 사용하는 수단이다. 
HTTP 프로토콜 기반이고, 상태 정보를 업데이트하거나, 뉴스 피드를 가져오거나, 친구를 추가하는 등의 다양한 작업을 수행하는데 사용한다.
그 중에서 가장 중요한 두 가지 API인 <ins>피드 발행 API</ins>와 <ins>피드 읽기 API</ins>를 살펴보자.

📌 _피드 발행 API_
&nbsp;새 스토리를 포스팅하기 위한 API이다. HTTP POST형태로 요청을 보내면 된다.

_POST/v1/me/feed_

인자: 
* body: 포스트 내용에 해당한다.
* Authorization 헤더: API 호출을 인증하기 위해 사용한다.

📌 _피드 읽기 API_
&nbsp;뉴스 피드를 가져오는 API다.

_GET/v1/me/feed_

인자:
* Authorization 헤더: API 호출을 인증하기 위해 사용한다.

#### 💡 피드 발행
&nbsp;피드 발행 시스템의 개략적 형태

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/feed_publishing_system.png"/>

* User: 모바일 앱이나 브라우저에서 새 포스팅을 올리는 주체. _POST_  _/v1/me/feed_ API를 사용한다.
* Load balancer: 트래픽을 웹 서버들로 분산한다.
* Web servers: HTTP 요청을 내부 서비스로 중계하는 역할을 담당한다.
* Post service(포스트 저장 서비스): 새 포스팅을 데이터베이스와 캐시에 저장한다.
* Fanout service(포스팅 전송 서비스): 새 포스팅을 친구의 뉴스 피드에 푸시(push)한다. 뉴스 피드 데이터는 캐시에 보관하여 빠르게 읽어갈 수 있도록 한다.
* Notification service(알림 서비스): 친구들에게 새 포스팅이 올라왔음을 알리거나, 푸시 알림을 보내는 역할을 담당한다.

#### 💡 뉴스 피드 생성
&nbsp;뉴스 피드가 만들어지는 개략적인 설계

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/news_feed_building.png"/>

* User: 뉴스 피드를 읽는 주체다. _GET_ _/v1/me/feed_ API를 이용한다.
* Load balancer: 트래픽을 웹 서버들로 분산한다.
* Web servers: 트래픽을 뉴스 피드 서비스로 보낸다.
* News feed service: 캐시에서 뉴스 피드를 가져오는 서비스다.
* News feed cache: 뉴스 피드를 렌더링할 때 필요한 feed ID를 보관한다.

---

### 🪜 STEP 3 - Design deep dive(상세 설계)
&nbsp; STEP 2의 두 가지 흐름이 포함된 개략적 설계안을 상세히 살펴보자.

#### 💡 피드 발행 흐름 상세 설계
&nbsp;아래 그림에서 대부분의 컴포넌트는 STEP 2에서 다룬 정도로 충분할 것이라 보고, 웹 서버와 fanout service(포스팅 전송 서비스)에 초점을 맞추어 보자.

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/feed_publishing_deep_dive.png"/>

📌 _웹 서버_ 

&nbsp; 웹 서버는 클라이언트와 통신할 뿐 아니라 인증이나 처리율 제한 등의 기능도 수행한다. 
올바른 인증 토큰을 Authorization 헤더에 넣고 API를 호출하는 사용자만 포스팅을 할 수 있어야 한다.
또한, 스팸을 막고 유해한 콘텐츠가 자주 올라오는 것을 방지하기 위해 특정 기간 동안 한 사용자가 올릴 수 있는 포스팅의 수에 제한을 두어야 한다.

📌 _fanout service(포스팅 전송 서비스)_

&nbsp; 포스팅 전송, 즉 fanout은 어떤 사용자의 새 포스팅을 그 사용자와 친구 관계에 있는 모든 사용자에게 전달하는 과정이다. 
fanout에는 두 가지 모델이 있다. 하나는 쓰기 시점에 fanout-on-write하는 모델(push 모델이라고도 함)이고, 다른 하나는 읽기 시점에 fanout-on-read하는 모델(pull 모델이라고도 함)이다.
이 각각의 동작 원리를 알아보고 어떤 것이 이 시스템에 적합한 모델인지 알아보자.

📌 _fanout-on-write (쓰기 시점에 fanout하는 모델)_

&nbsp; 이 접근법의 경우 새로운 포스팅을 기록하는 시점에 뉴스 피드를 갱신하게 된다. 
포스팅이 완료되면 바로 해당 사용자의 캐시에 해당 포스팅을 기록하는 것이다.

**장점**
* 뉴스 피드가 실시간으로 갱신되며 친구 목록에 있는 사용자에게 즉시 전송된다.
* 새 포스팅이 기록되는 순간에 뉴스 피드가 갱신되므로(pre-computed) 뉴스 피드를 읽는데 드는 시간이 짧아진다.

**단점**
* 친구가 많은 사용자의 경우 친구 목록을 가져오고 그 목록에 있는 사용자 모두의 뉴스 피드를 갱신하는데 많은 시간이 소요될 수 있다. 이것을 `hotkey problem`이라고 부른다.
* 서비스를 자주 이용하지 않는 사용자의 피드까지 갱신해야 하므로 컴퓨터 자원이 낭비된다.

📌 _fanout-on-read (읽기 시점에 fanout하는 모델)_

&nbsp;피드를 읽어야 하는 시점에 뉴스 피드를 갱신한다. 따라서 요청 기반(on-demand) 모델이다.
사용자가 본인 홈페이지나 타임라인을 로딩하는 시점에 새로운 포스터를 가져오게 된다.

**장점**
* 비활성화된 사용자, 또는 서비스에 거의 로그인하지 않는 사용자의 경우에는 이 모델이 유리하다. 로그인하기까지는 어떤 컴퓨팅 자원도 소모하지 않아서다.
* 데이터를 친구 각각에 푸시하는 작업이 필요 없으므로 `hotkey problem`도 생기지 않는다.

**단점**
* 뉴스 피드를 읽는데 많은 시간이 소요될 수 있다.

&nbsp;이번 설계안의 경우 위 두 가지 방법의 장점을 취하고 단점을 버리는 전략으로 취하도록 하겠다.
* 뉴스 피드를 빠르게 가져올 수 있도록 하는 것이 중요하므로 대부분의 사용자에 대해서는 푸시 모델을 사용한다.
* 친구나 팔로어가 아주 많은 사용자의 경우에는 팔로어로 하여금 해당 사용자의 포스팅을 필요할 때 가져가도록 하는 풀 모델을 사용하여 시스템 과부하를 방지할 것이다.
* 아울러 안정 해시(consistent hashing)를 통해 요청과 데이터를 보다 고르게 분산하여 `hotkey problem`을 줄여볼 것이다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/fanout_service.png"/>

&nbsp;위 그림은 `💡 피드 발행 흐름 상세 설계`의 그림에서 `fanout service`에 관한 부분만 따로 떼어 옮겼다.
자세히 살펴보면,
1. `Graph DB`에서 친구 ID 목록을 가져온다. `Graph DB`는 친구 관계나 친구 추천을 관리하기 적합하다.
2. `User cache(사용자 캐시)`에서 친구들의 정보를 가져온다. 그런 후에 사용자 설정에 따라 친구 가운데 일부를 걸러낸다. 
예를 들어, 우리가 친구 중 누군가의 피드 업데이트를 무시하기로 설정했다면(mute) 친구 관계는 유지되지만 해당 사용자의 새 스토리는 우리의 뉴스 피드에 보이지 않아야 한다. 
새로 포스팅된 스토리가 일부 사용자에게만 공유되도록 설정된 경우에도 비슷한 일이 벌어질 것이다.
3. 친구 목록과 새 스토리의 포스팅 ID를 `Message Queue`에 넣는다.
4. `Fanout Workers(팬아웃 작업 서버)`가 `Message Queue`에서 데이터를 꺼내 뉴스 피드 데이터를 `News Feed Cache`에 넣는다. 
`News Feed Cache`는 <post_id, user_id>의 순서쌍을 보관하는 매핑 테이블이라고 볼 수 있다. 
따라서 새 포스팅이 만들어질 때마다 이 캐시에 `| post_id | user_id |` 레코드가 추가될 것이다. 
사용자 정보와 포스팅 정보를 전부 저장하지 않는 이유는, 그렇게 하면 메모리 요구량이 지나치게 늘어날 수 있기 때문이다. 
또한 메모리 크기를 적정 수준으로 유지하기 위해서, 이 캐시의 크기에 제한을 두며 해당 값은 조정이 가능하도록 한다.
어떤 사용자가 뉴스 피드에 올라온 수천 개의 스토리를 전부 훑어보는 일이 벌어질 확률은 지극히 낮다. 
대부분의 사용자가 보려 하는 것은 최신 스토리다. 따라서 캐시 미스(cache miss)가 일어날 확률은 낮다.

#### 💡 피드 읽기 흐름 상세 설계
&nbsp;뉴스 피드를 읽는 과정 전반의 상세 설계안은 아래와 같다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/news_feed_retrieval_deep_dive.png"/>

&nbsp;이미지나 비디오와 같은 미디어 콘텐츠는 `CDN`에 저장하여 빨리 읽어갈 수 있도록 하였다.

&nbsp;이제 클라이언트가 뉴스 피드를 어떻게 읽어가는지 단계별로 알아보자.
1. _/v1/me/feed_ 로 사용자가 뉴스 피드를 읽으려는 요청을 보낸다.
2. `Load balancer`가 요청을 `Web servers` 가운데 하나로 보낸다.
3. `Web servers`는 피드를 가져오기 우해 `News Feed Service`를 호출한다.
4. `News Feed Service`는 `News Feed Cache`에서 `post IDs` 목록을 가져온다.
5. 뉴스 피드에 표시할 사용자 이름, 사용자 사진, 포스팅 콘텐츠, 이미지 등을 `User Cache(사용자 캐시)`와 `Post Cache(포스팅 캐시)`에서 가져와 완전한 뉴스 피드를 만든다.
6. 생성된 뉴스 피드를 JSON 형태로 클라이언트에게 보낸다. 클라이언트는 해당 피드를 렌더링한다.

#### 💡 캐시 구조
&nbsp; 캐시는 뉴스 피드 시스템의 핵심 컴포넌트다. 이번 설계의 경우 아래와 같이 캐시를 다섯 계층으로 나눴다.

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/cache_architecture.png"/>

* 뉴스 피드: 뉴스 피드의 ID를 보관.
* 콘텐츠: 포스팅 데이터를 보관, 인기 콘텐츠는 따로 보관.
* 소셜 그래프: 사용자 간 관계 정보를 보관.
* 행동(action): 포스팅에 대한 사용자의 행위에 관한 정보를 보관. (포스팅에 대한 '좋아요', 답글 등이 이에 해당한다.)
* 횟수(counter): '좋아요' 횟수, 응답 수, 팔로어 수, 팔로잉 수 등의 정보를 보관한다.

---

#### _Reference_
[1] How News Feed Works:
https://www.facebook.com/help/327131014036297/ <br>
[2] Friend of Friend recommendations Neo4j and SQL Sever:
http://geekswithblogs.net/brendonpage/archive/2015/10/26/friend-of-friendrecommendations-
with-neo4j.aspx <br>
[3] 가상 면접으로 배우는 대규모 시스템 설계 기초(System Design Interview - An Insider's Guide: Alex Xu 번역본) - CHAPTER 11.
