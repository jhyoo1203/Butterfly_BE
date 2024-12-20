package com.codenear.butterfly.kakaoPay.presentation.singlepay;

import com.codenear.butterfly.global.dto.ResponseDTO;
import com.codenear.butterfly.global.exception.ErrorCode;
import com.codenear.butterfly.global.util.ResponseUtil;
import com.codenear.butterfly.kakaoPay.application.OrderDetailsService;
import com.codenear.butterfly.kakaoPay.application.SinglePaymentService;
import com.codenear.butterfly.kakaoPay.domain.dto.request.DeliveryPaymentRequestDTO;
import com.codenear.butterfly.kakaoPay.domain.dto.request.PickupPaymentRequestDTO;
import com.codenear.butterfly.kakaoPay.exception.KakaoPayException;
import com.codenear.butterfly.member.domain.dto.MemberDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class SinglePayController implements SinglePayControllerSwagger {

    private final SinglePaymentService singlePaymentService;
    private final OrderDetailsService orderDetailsService;

    @PostMapping("/ready/pickup")
    public ResponseEntity<ResponseDTO> pickupPaymentRequest(@RequestBody PickupPaymentRequestDTO paymentRequestDTO,
                                                            @AuthenticationPrincipal MemberDTO memberDTO) {
        return ResponseUtil.createSuccessResponse(singlePaymentService.kakaoPayReady(paymentRequestDTO, memberDTO.getId(), "pickup"));
    }

    @PostMapping("/ready/delivery")
    public ResponseEntity<ResponseDTO> deliveryPaymentRequest(@RequestBody DeliveryPaymentRequestDTO paymentRequestDTO,
                                                              @AuthenticationPrincipal MemberDTO memberDTO) {
        return ResponseUtil.createSuccessResponse(singlePaymentService.kakaoPayReady(paymentRequestDTO, memberDTO.getId(), "deliver"));
    }

    @GetMapping("/success")
    public void successPaymentRequest(@RequestParam("pg_token") String pgToken,
                                                             @RequestParam("memberId") Long memberId,
                                                             HttpServletResponse response) throws IOException {
        singlePaymentService.approveResponse(pgToken, memberId);
        response.sendRedirect("butterfly://kakaopay/success");
    }

    @GetMapping("/cancel")
    public void cancelPaymentRequest(@RequestParam("memberId") Long memberId,
                                                            HttpServletResponse response) throws IOException {
        singlePaymentService.cancelPayment(memberId);
        response.sendRedirect("butterfly://kakaopay/cancel");
    }

    @GetMapping("/fail")
    public void failPaymentRequest(@RequestParam("memberId") Long memberId,
                                   HttpServletResponse response) throws IOException {
        singlePaymentService.failPayment(memberId);
        response.sendRedirect("butterfly://kakaopay/fail");
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseDTO> checkPaymentStatus(@AuthenticationPrincipal MemberDTO memberDTO) {
        String status = singlePaymentService.checkPaymentStatus(memberDTO.getId());
        singlePaymentService.updatePaymentStatus(memberDTO.getId());
        return ResponseUtil.createSuccessResponse(HttpStatus.OK, "결제 상태 조회 성공", status);
    }

    @GetMapping("/order-details")
    public ResponseEntity<ResponseDTO> getAllOrderDetails(@AuthenticationPrincipal MemberDTO memberDTO) {
        return ResponseUtil.createSuccessResponse(
                HttpStatus.OK,
                "주문 내역 조회 성공",
                orderDetailsService.getAllOrderDetails(memberDTO.getId()));
    }
}
