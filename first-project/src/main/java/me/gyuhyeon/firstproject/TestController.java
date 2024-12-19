package me.gyuhyeon.firstproject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "테스트", description = "테스트예용")   //api의 그룹 태그 설정
public class TestController {
    @Autowired
    TestService testService;
    @Operation(summary = "테스트 컨트롤러", description = "API 개별 명세 테스트")
    @GetMapping("/test")
    public List<Member> getAllMembers() {
        List<Member> members = testService.getAllMembers();
        return members;
    }
}