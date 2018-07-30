# 자바 ORM 표준 JPA 프로그래밍
# [6장] 다양한 연관관계 매핑


## 6.1 다대일
### 6.1.1 다대일 단방향 [N:1] ###

다대일 단방향 쉽다.
항상 다가 외래키를 가진다. (**생각해보면 이게 맞다!**)

한쪽에서만 상대를 참조할 수 있는 필드를 가진다.
(@JoinColumn, DB에 테이블도 @JoinColumn이 있는곳에 생긴다.)
<br/>
(예제 OrderItem, Item)

### 6.1.2 다대일 양방향 [N:1, 1:N] ###

    외래키는 누가 가지고 있을까? (항상 다가 가진다고)
    (@ManyToOne에는 @JoinColumn, @OneToMany(mappedBy = "team"))
    (나는 Member 클래스에 team이라는 변수와 매핑되어 있어요.)

    @JoinColumn, mappedBy 이제 헤까리지 말자.

    (예제 Member, Order)