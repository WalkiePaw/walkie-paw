package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressesGetResponse {

  private String memberAddress;
  private String selectedAddrs;

  /**
   * Entity -> DTO
   */
  public static AddressesGetResponse from(Member member) {
    return AddressesGetResponse.builder()
            .memberAddress(member.getMemberAddress())
            .selectedAddrs(member.getSelectedAddresses())
            .build();
  }
}
