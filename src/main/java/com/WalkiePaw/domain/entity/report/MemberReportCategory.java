package com.WalkiePaw.domain.entity.report;

/**
 * ABUSIVE = 욕설을 사용한 경우
 * HARASSMENT = 불쾌한 대화를 시도한 경우
 * NO_SHOW = 약속 장소에 나타나지 않은 경우
 * SCAMMER = 사기 사용자인 경우
 * DISPUTE = 거래/환불 분쟁이 있는 경우
 */
public enum MemberReportCategory {
    ABUSIVE,
    HARASSMENT,
    NO_SHOW,
    SCAMMER,
    DISPUTE
}