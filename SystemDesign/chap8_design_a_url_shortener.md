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
API 엔드포인트(endpoint), URL 리디렉션, URL 단축 플로에 대해 살펴보자.

#### 💡 API 엔드포인트
#### 💡 URL 리디렉션
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