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

#### _Reference_
[1] How News Feed Works:
https://www.facebook.com/help/327131014036297/ <br>
[2] Friend of Friend recommendations Neo4j and SQL Sever:
http://geekswithblogs.net/brendonpage/archive/2015/10/26/friend-of-friendrecommendations-
with-neo4j.aspx <br>
[3] 가상 면접으로 배우는 대규모 시스템 설계 기초(System Design Interview - An Insider's Guide: Alex Xu 번역본) - CHAPTER 11.
