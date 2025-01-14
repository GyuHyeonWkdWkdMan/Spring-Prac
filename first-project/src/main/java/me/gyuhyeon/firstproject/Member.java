package me.gyuhyeon.firstproject;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity // JPA가 관리하는 엔티티로 지정 - Member 클래스와 실제 데이터베이스의 테이블을 매핑(이름 지정 안하면 클래스 이름과 동일한 테이블과 매핑)
// 이름 지정 예시
// @Entity(name = "member_list")
// public class Article{...}
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 - protected
@AllArgsConstructor
public class Member {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false) // 변수와 컬럼의 이름이 다르면 @Column(name = "differentid") nullable, unique, columnDefinition 등등 있음
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    public void changeName(String name) {
        this.name = name;
    }
}
