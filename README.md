# Order Service

## 기술 스펙

* Spring Boot
* Spring Data JPA
* H2 DataBase
* Spring MVC
* thymeleaf Template Engine

## 요구 사항 분석

* 로그인, 회원가입
* 상품 등록, 상품 목록
* 상품 주문, 주문 내역

## 기능 목록

* 회원 기능
    * 회원 등록
    * 회원 조회
* 상품 기능
    * 상품 등록
    * 상품 수정
    * 상품 조회
* 주문 기능
    * 상품 주문
    * 주문 내역 조회
    * 주문 취소
    * 주문 검색

## API 개발 고급

> 인프런 JPA API 개발 성능 최적화 강좌 학습 및 정리한 내용 입니다.

### 지연로딩(Lazy Loading) 과 조회 성능 최적화

* 지연로딩으로 설정된 엔티티를 API 응답으로 내려줄 때, Jackson이 데이터를 변환하다가 아래와 같은 에러가 발생한다. Order -> Member, Order -> Address 지연 로딩 (Lazy
  Loading) 으로 실제 엔티티 대신 proxy 존재 함.

  ```shell
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
      No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer(to avoid exception,disable SerializationFeature.FAIL_ON_EMPTY_BEANS)(through reference chain:java.util.ArrayList[0]->com.hyeonah.hellojpa.domain.Order["member"]->com.hyeonah.hellojpa.domain.Member$HibernateProxy$RVgm0EHQ["hibernateLazyInitializer"])
  ```

    Hibernate5Module 을 Spring Bean 으로 등록하면 해결 가능하다.
    
    * build.gradle jackson-datatype-hibernate5 dependency 추가
    
      ```groovy
      implementation 'com.fasterxml.jackson.datatype:jackson-datatype-hibernate5'
      ```
    
    * Bean 등록
    
      ```java
        @Bean
        Hibernate5Module hibernate5Module(){
            final Hibernate5Module hibernate5Module=new Hibernate5Module();
            // json 생성 시점에 강제로 지연로딩 해서 엔티티 정보를 가져오게 설정
            hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,true);
            return hibernate5Module;
          }
      ```
    
  * 엔티티를 직접 노출할 때 xToOne (OneToOne, ManyToOne) 양방향 연관관계에서 무한 루프에 빠지는 문제가 발생한다.
  
    양방향 연관관계에서 한 곳에서 @JsonIgnore 설정으 관계를 끊어주어야 한다.
  
      ```java 
      @JsonIgnore
      @OneToMany(mappedBy = "member")
      private final List<Order> orders = new ArrayList<>();
      ```

but, 엔티티를 API 응답으로 외부에 노출하는 것은 좋지 않다.
`API 스펙과 상관없는 데이터 노출 가능성`, `API 스펙 추가 노출문제`,  `성능 상 문제` 등 발생한다. 그렇기 때문에 `Hibernate5Module` 를 사용하는 것보다는 DTO 로 변환해서 반환하는
것이 좋다.

🔑 중요

- 응답 값으로 엔티티를 외부에 직접 노출 하게 된다면 문제점은?
    - Entity에 Presentation 계층을 위한 로직이 추가 된다.
        - 기본적으로 엔티티의 값들이 모두 노출 된다. (API 스펙 상 필요하지 않는 것들도..)
        - Response Spec 을 맞추기 위해서 로직이 추가 된다. (@JsonIgnore, 별도의 노출(뷰) 로직 등..)
        - 각 용도에 따른 API 들을 위해서 Entity 내에 Presentation Response 로직을 담기는 어려움이 있다.
        - Entity 가 변경되면 API Spec 도 변경된다.
        - (+) Collection을 직접 반환하면 추후에 API Spec 변경에 어려움이 따른다. => 별도의 Response or Result 클래스를 생성한다.
- 따라서, `각 API Spec에 따라 별도의 DTO로 변환하여 반환`한다.

- 지연 로딩 (Lazy loading) 을 피하기 위해서 즉시 로딩 (Eager)로 설정하게 되면?
    - 즉시 로딩은 연관관계가 필요 없는 경우에도 데이터를 항상 조회, 성능 문제가 발생할 수 있다.
    - 성능 튜닝이 매우 어려워지기 때문에 `지연 로딩`을 기본으로 하고, 성능 최적화가 필요한 경우에는 페치 조인(fetch join)을 사용한다. (V3)

* N + 1 문제: 쿼리가 총 1 + N 
    * order 조회 1번 (order 조회 결과 수가 N)
    * order > member 지연로딩 조회 N번
    * order > delivery 지연로딩 조회 N번
    * ex) order의 결과가 2개면 최악의 경우 1+2+2 번 실행된다.
        * 지연로딩은 영속성 컨텍스트에서 조회하므로, 이미 조회된 경우 쿼리를 생략한다. 

* 컬렉션 조회 최적화
* 페이징과 한계
* OSIV(Open Session In View)와 성능 최적화 