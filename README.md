# 자바 ORM 표준 JPA 프로그래밍

<br/>
<br/>

# [3장] 영속성 관리


## 3.1 엔티티 매니저 팩토리와 엔티티 매니저
엔티티 매니저 팩토리 비용 많이 발생, 여러 스레드가 동시에 접근해도 안전함
엔티티 매니저 비용 별로 발생 않하고 스레드 간에 공유 절대 안된다고
커넥션풀은 엔티티 매니저 팩토리 말들 때 같이 만든다고
엔티티 매니저는 필요할 때 커넥션풀에서 커넥션을 획득한다.


## 3.2 영속성 컨텍스트란?
영속성 컨텍스트 - '엔티티를 영구 저장하는 환경' (논리적인 개념에 가깝다고)
영속성 컨텍스트는 엔티티 매니저를 생성할 때 만들어지는데
우선은 하나의 엔티티 매니저당 하나의 영속성 컨텍스트가 만들어진다고 생각하자
여러 엔티티 매니저가 같은 영속성 컨텍스트에 접근할 수 있다고


## 3.3 엔티티의 생명주기
비영속(new/transient) : 영속성 컨텍스트와 전혀 관계가 없는 상태
순수한 객체 상태를 비영속 상태라고 한단다.

영속(managed) : 영속성 컨텍스트에 저장된 상태
영속 상태란 영속성 컨텍스트에 의해 관리가 된다는 뜻

준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
detach(), clear(), close() 등 호출, 영속성 컨텍스트가 관리하지 않는 상태가 준영속 상태

삭제(removed) : 삭제된 상태


## 3.4 영속성 컨텍스트의 특징
영속성 컨텍스트가 왜 필요하고 어떤 이점이 있는지 엔티티를 CRUD 하면서 그 이유를 하나씩 알아보자.

### 3.4.1 엔티티 조회
영속 컨텍스트 - 1차 캐시 (map 형태로 되어있다고, @Id - Entity)
em.find() - 1차 캐시(영속 컨텍스트)에서 먼저 찾고 없으면 디비를 조회한다.
영속성 컨텍스트는 성능상 이점과 엔티티의 동일성을 보장한다고 한다.

동일선(identity) : 실제 인스턴스가 같다.
동등성(equality) : 실제 인스턴스는 다를 수 있지만 인스턴스가 가지고 있는 값이 같다.

### 3.4.2 엔티티 등록
트랜잭션을 커밋하기 전에는 디비에 엔티티를 저장하지 않고 쓰기 지연 SQL 저장소에
쿼리를 쌓아두고 있다가 커밋할 때 한번에 날린다.
그리고 엔티티는 1차 캐시에 넣는다.

커밋을 하면 영속성 매니저는 우선 영속성 컨텍스트를 플러시한다.
ㄴ 플러시란 영속성 컨텍스트의 변경 내용을 디비에 동기화하는 작업
ㄴ 쓰기 지연 SQL 저장소에 모인 쿼리를 데이터베이스에 

### 3.4.3 엔티티 수정
em.update(member) 따로 안해줘도 엔티티의 변경사항을 디비에 자동으로 반영하는 기능을 변경 감지(dirty checking)라고 한다.
ㄴ 변경 감지는 영속성 컨텍스트가 관리하는 영속 상태의 엔티티만 적용된다.
ㄴ JPA는 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장해두는데 이것을 스냅샷이라 한다.
ㄴ 그리고 플러시 시점에 스냅샷과 엔티티를 비교해서 변경된 엔티티를 찾는다.
ㄴ 그냥 항상 전체 필드 업데이트하는 문을 보낸다고 (항상 같은게 들어가니깐 미리 생성할 수도 있고 재사용도 가능하다고 한다.)

### 3.4.4 엔티티 삭제


## 3.5 플러시

## 3.6 준영속

## 3.7 정리

<br/>
<br/>


# [6장] 다양한 연관관계 매핑

---

## 6.1 다대일
### 6.1.1 다대일 단방향 [N:1]

* 항상 **다(Many)**가 외래키를 가진다. (**생각해보면 이게 맞다!**)

<br/>
* 한쪽에서만 상대를 참조할 수 있는 필드를 가진다.

> (@JoinColumn, DB에 테이블도 @JoinColumn이 있는곳에 생긴다.)
<br/>
<br/>
<br/>
(예제 OrderItem, Item [실습])

---

## 6.1 다대일
### 6.1.2 다대일 양방향 [N:1, 1:N]

* 외래키는 누가 가지고 있을까? (항상 **다(Many)**가 가진다고!)

* 양방향이기 때문에 양쪽다 Class에는 필드가 있다.

> (@ManyToOne - @JoinColumn, @OneToMany(**mappedBy = **"team"))

> (mappedby는 Member 클래스에 team이라는 변수와 매핑되어 있음.)

```java
        // Member Class
        @OneToMany(mappedBy = "member")
        private List<Order> orders = new ArrayList<Order>();
```

> **문제 : ** 위 예제의 Member 테이블에 Order 관련 필드가 생긴다 안생긴다?
<br/>
<br/>
<br/>
(예제 Member, Order(ORDERS))

---

## 6.1 다대일
### 6.1.2 다대일 양방향 [N:1, 1:N]

* JPA는 외래 키를 관리할 때 연관관계의 주인만 사용한다.

> (주인이 아니면 조회를 위한 JPQL이나 객체 그래프를 탐색 할 때 사용한다?)

* 양방향 연관관계는 한 쪽에서만 참조하면 관계가 성립하지 않는다.

> (항상 서로가 참조할 수 있게 편의 메소드를 만들어주자.)

> (한 쪽에만 만들어도 되고 양쪽 다 만들어도 된다고. (이 때 무한루프 조심)

```java
        public void setMember(Member member) {
            //기존 관계 제거
            if (this.member != null) {
                this.member.getOrders().remove(this);
            }
            this.member = member;
            member.getOrders().add(this);
        }
```
---

## 6.2 일대다
### 6.2.1 일대다 단방향 [1:N]

* 일대다 단방향은 @JoinColumn 안때려주면 테이블이 생긴다.

> **문제 : ** @OneToMany를 가지는 Team 테이블에 해당 필드가 있다 없다?

> (기억하자 @OneToMany도 똑같다. FK는 **다(Many)**가 가진다!!!)

<br/>
* 다른 테이블에 외래 키가 있어서 UPDATE SQL이 추가로 실행된다. (실습)

> (OrderItemRepositoryTest 보면 다대일은 update 안날라간다.)

<br/>
<br/>
<br/>
(예제 OToM_Team, OToM_Member [실습])


---

## 6.2 일대다
### 6.2.2 일대다 양방향 [1:N, N:1]

* 양방향 처럼 보이도록 일대다 단방향 매핑 반대편에 <br/>다대일 단방향 매핑을 읽기 전용으로

> (일대다 단방향 매핑이 가지는 단점을 그대로 가진다고)

```java
        @ManyToOne
        @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
        private Team team;
```

---

## 6.3 일대일 [1:1]
### 6.3.1 주 테이블에 외래 키

* 객체지향 개발자들이 선호한다고 한다.

> 장점 : 주 테이블이 외래 키를 가지므로 주 테이블만 봐도 연관관계를 확인 할 수 있다.
<br/>

* 단방향
> 주 테이블이 왜래 키를 가진다. 클래스의 필드도 한쪽에만
<br/>

* 양방향
> 양쪽 클래스에 필드가 생기고 한쪽에 mappedBy 해줘야한다.<br/> (왜래 키는 한쪽에서만 가진다.)
<br/>
<br/>
<br/>
(예제 Order(ORDERS), Delivery)

---

## 6.3 일대일 [1:1]
### 6.3.2 대상 테이블에 외래 키

* 전통적인 데이터베이스 개발자들은 대상 테이블에 외래 키를 두는 걸 선호한다고 한다.

> 장점 : 테이블 관계를 일대일에서 일대다로 변경할 때 테이블 구조가 그대로 유지된다.

> (왜래키를 가지고 있는 일이 다가 되는건 큰 문제가 없지 않겠니?)
<br/>

* 단방향
> 단방향으로는 안된다. 양방향으로 만들고 대상이 되는 쪽을 연관관계의 주인으로 설정해야한다.
<br/>

* 양방향
> 대상 테이블과 주 테이블을 기준이 뭘까? 양방향에서 mappedBy를 해주면 주 테이블이 바뀌는거 아닌가?
<br/>
<br/>
<br/>
(예제 Order(ORDERS), Delivery)

---

## 6.4 다대다 [M:N]
### 6.4.1 다대다: 단방향
### 6.4.2 다대다: 양방향

* @ManyToMany 중간에 테이블을 만들어서 일대다, 다대일로 풀어서 표현한다.

```java
        // 반대편에는 mappedBy로 처리한다.
        @ManyToMany
        @JoinTable(name = "CATEGORY_ITEM",
                joinColumns = @JoinColumn(name = "CATEGORY_ID"),
                inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
        private List<Item> items = new ArrayList<Item>();
```
<br/>
<br/>
<br/>
(예제 Item, Category [실습])

---

## 6.4 다대다 [M:N]
### 6.4.3 다대다: 매핑의 한계와 극복, 연결 엔티티 사용
### 6.4.4 다대다: 새로운 기본 키 사용

* 자동으로 생기는 연결 테이블에 뭔가 정보를 더 주고 싶다면?

* @IdClass(MemberProductId.class) 복합키 방식

* 기본키가 @GeneratedValue 되도록
