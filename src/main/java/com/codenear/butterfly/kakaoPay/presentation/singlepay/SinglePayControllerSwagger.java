package com.codenear.butterfly.kakaoPay.presentation.singlepay;

import com.codenear.butterfly.global.dto.ResponseDTO;
import com.codenear.butterfly.kakaoPay.domain.dto.DeliveryPaymentRequestDTO;
import com.codenear.butterfly.kakaoPay.domain.dto.PickupPaymentRequestDTO;
import com.codenear.butterfly.member.domain.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "SinglePay", description = "**카카오페이 단건결제 API**")
public interface SinglePayControllerSwagger {

    @Operation(summary = "직거래 결제 준비", description = "직거래 결제 준비 API")
    ResponseEntity<ResponseDTO> pickupPaymentRequest(@RequestBody PickupPaymentRequestDTO paymentRequestDTO,
                                                     @AuthenticationPrincipal MemberDTO memberDTO);

    @Operation(summary = "배달 결제 준비", description = "배달 결제 준비 API")
    ResponseEntity<ResponseDTO> deliveryPaymentRequest(@RequestBody DeliveryPaymentRequestDTO paymentRequestDTO,
                                                       @AuthenticationPrincipal MemberDTO memberDTO);

    @Operation(summary = "결제 성공", description = "결제 성공 API", hidden = true)
    ResponseEntity<ResponseDTO> successPaymentRequest(@RequestParam("pg_token") String pgToken,
                                                      @RequestParam("memberId") Long memberId);

    @Operation(summary = "결제 취소", description = "결제 취소 API", hidden = true)
    @ApiResponse(responseCode = "200", description = "결제 취소")
    ResponseEntity<ResponseDTO> cancelPaymentRequest(@RequestParam("memberId") Long memberId);

    @Operation(summary = "결제 실패", description = "결제 실패 API", hidden = true)
    @ApiResponse(responseCode = "402", description = "결제 실패")
    void failPaymentRequest(@RequestParam("memberId") Long memberId);

    @Operation(summary = "결제 상태 조회", description = "결제 상태 조회 API")
    @ApiResponse(responseCode = "200", description = "결제 상태 조회 성공")
    ResponseEntity<ResponseDTO> checkPaymentStatus(@AuthenticationPrincipal MemberDTO memberDTO);

    @Operation(summary = "주문 내역 조회", description = "주문 내역 조회 API")
    @ApiResponse(responseCode = "200", description = "주문 내역 조회 성공")
    ResponseEntity<ResponseDTO> getAllOrderDetails(@AuthenticationPrincipal MemberDTO memberDTO);
}