package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) { //Model? -> data
        model.addAttribute( "memberForm", new MemberForm() );
        return "members/createMemberForm";
    }

    //MemberForm이 파라미터로 넘어가게 된다.
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form) {
        Address address = new Address( form.getCity(), form.getStreet(), form.getZipcode() );
        Member member = new Member();
        member.setName( form.getName() );
        member.setAddress( address );
        memberService.join( member );
        return "redirect:/";

    }
}
