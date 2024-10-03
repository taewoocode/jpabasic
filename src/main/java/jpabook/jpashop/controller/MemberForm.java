package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Notempty;

@Getter @Setter
public class MemberForm {

    @NotEmpty
    private String name;

    private String city;
    private String street;
    private String zipcode;


}
