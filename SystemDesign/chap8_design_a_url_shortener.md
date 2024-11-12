# CHAPTER 8. DESIGN A URL SHORTENER(URL 단축기 설계)
> 가상 면접으로 배우는 대규모 시스템 설계 기초(System Design Interview - An Insider's Guide: Alex Xu 번역본)

&nbsp;고전적인 시스템 설계 문제 가운데 하나인, [TinyURL](https://tinyurl.com/) 같은 URL 단축기를 설계하는 문제를 풀어보는 것을 정리.

### 🪜 STEP 1 - 문제 이해 및 설계 범위 확정
#### 💡 개략적 추정
* 쓰기 연산: 매일 1억 개의 단축 URL 생성
* 초당 쓰기 연산: 1억(100million) / 24 / 3600 = 1160
* 읽기 연산: 읽기 연산과 쓰기 연산 비율은 10:1이라고 하자. 그 경우 읽기 연산은 초당 11,600회 발생한다.(1160 * 10 = 11,600)
* URL 단축 서비스를 10년간 운영한다고 가정하면 1억(100million) * 365 * 10 = 3650억(365billion)개의 레코드를 보관해야 한다.
* 축약 전 URL의 평균 길이는 100이라고 하자.
* 따라서 10년 동안 필요한 저장 용량은 3650억(365billion) * 100byte = 36.5TB 이다.

### 🪜 STEP 2 - 개략적 설계안 제시 및 동의 구하기
&nbsp;API 엔드포인트(endpoint), URL 리디렉션, URL 단축 플로에 대해 살펴보자.

#### 💡 API 엔드포인트
&nbsp;클라이언트는 서버가 제공하는 API 엔드포인트를 통해 서버와 통신한다. 
이 엔드포인트를 REST 스타일로 설계할 것이고, URL 단축기는 기본적으로 두 개의 엔드포인트를 필요로 한다.
1. URL 단축용 엔드포인트: 새 단축 URL을 생성하고자 하는 클라이언트는 이 엔드포인트에 단축할 URL을 인자로 실어서 POST 요청을 보내야 한다. 이 엔드포인트는 아래와 같은 형태를 띈다. <br><br> _POST/api/v1/data/shorten_ 
    * 인자: {longURL: longURLstring}
    * 반환: 단축 URL
2. URL 리디렉션용 엔드포인트: 단축 URL에 대해서 HTTP 요청이 오면 원래 URL로 보내주기 위한 용도의 엔드포인트, 아래와 같은 형태를 띈다. <br><br> _GET/api/v1/shortUrl_
    * 반환: HTTP 리디렉션 목적지가 될 원래 URL  

#### 💡 URL 리디렉션
&nbsp;아래의 이미지를 통해 단축 URL을 입력하면 어떤 일이 발생하는지 알 수 있다. 

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/input_a_short_url.jpg" width="500" height="300"/>

&nbsp;단축 URL을 받은 서버는 그 URL을 원래 URL로 바꾸어서 301 응답의 Location 헤더에 넣어 반환한다.

&nbsp;클라이언트와 서버 사이의 통신 절차를 좀 더 자세히 보면,

<img src="https://github.com/ro117-youshin/TIL/blob/main/SystemDesign/img/the_communication_between_client_and_server.jpg" width="500" height="500"/>

여기서 유의할 것은 <ins>301 응답</ins>과 <ins>302 응답</ins>의 차이이다. 둘 다 리디렉션 응답이긴 하지만 차이가 있다.

* **301 Permanently Moved**: 이 응답은 <ins>해당 URL에 대한 HTTP 요청의 처리 책임이 영구적으로 Location 헤더에 반환된 URL로 이전되었다는 응답</ins>이다. 영구적으로 이전되었으므로, 브라우저는 이 응답을 캐시(cache)한다. 따라서 추후 같은 단축 URL에 요청을 보낼 필요가 있을 때 브라우저는 캐시된 원래 URL로 요청을 보내게 된다.
<br>&rarr; 장점: 첫 번째 요청만 단축 URL서버로 전송될 것이기 때문에, 서버 부하를 줄이는 것이 중요할 때 사용한다.
* **302 Found**: 이 응답은 <ins>주어진 URL로의 요청이 '일시적으로' Location 헤더가 지정하는 URL에 의해 처리되어야 한다는 응답</ins>이다. 따라서 클라이언트의 요청은 언제나 단축 URL 서버에 먼저 보내진 후에 원래 URL로 리디렉션 되어야 한다.
  <br>&rarr; 장점: 트래픽 분석(analytics)이 중요할 때, 클릭 발생률이나 발생 위치를 추적하는데 좀 더 유리하다.

&nbsp;URL 리디렉션을 구현하는 가장 직관적인 방법은 해시 테이블을 사용하는 것이다. 해시 테이블에 <단축 URL, 원래 URL>의 쌍을 저장한다고 가정한다면, URL 리디렉션은 아래와 같이 구현될 수 있다.
* 원래 URL = hashTable.get(단축 URL)
* 301 또는 302 응답 Location 헤더에 원래 URL을 넣은 후 전송

#### 💡 URL 단축

### 🪜 STEP 3 - 상세 설계

#### 💡 데이터 모델
#### 💡 해시 함수
#### 💡 URL 단축기 상세 설계
#### 💡 URL 리디렉션 상세 설계

### 🪜 STEP 4 - 마무리


#### _Reference_
[1] A RESTful Tutorial: _https://www.restapitutorial.com/index.html_ <br>
[2] Bloom filter: _https://en.wikipedia.org/wiki/Bloom_filter_