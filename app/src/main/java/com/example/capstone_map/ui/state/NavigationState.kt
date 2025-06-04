package com.example.capstone_map.ui.state

sealed interface NavigationState {

    // 1단계: 목적지 입력
    object AwaitingDestinationInput : NavigationState // 목적지 입력 대기 중
    object ListeningForDestination : NavigationState  // 목적지 음성 인식 중

    object ConfirmingDestination : NavigationState    // 목적지 확인 중
    object FetchingCandidates : NavigationState       // 목적지 후보지 검색 중
    object InputError : NavigationState               // 입력 오류 발생

    // 2단계: 후보지 탐색
    data class ShowingCandidate(val index: Int, val name: String) : NavigationState // 후보지 표시 중 (현재 인덱스, 이름)
    object NoCandidatesFound : NavigationState        // 후보지를 찾을 수 없음
    object FetchingAdditionalCandidates : NavigationState // 추가 후보지 검색 중

    // 3단계: 길안내 진행
    object StartNavigationPreparation : NavigationState // 길안내 시작 준비 중
    data class AligningDirection(val degreeOffset: Float) : NavigationState // 방향 정렬 안내 중 (현재 각도 오프셋)
    object AnnounceRouteSummary : NavigationState     // 경로 요약 안내 중
    object GuidingNavigation : NavigationState        // 길안내 진행 중
    object RouteRecalculationInProgress : NavigationState // 경로 재탐색 진행 중
    object RouteRecalculationSuccess : NavigationState // 경로 재탐색 성공
    object RouteRecalculationFailed : NavigationState // 경로 재탐색 실패

    // 4단계: 도착 및 종료
    object ArrivedAtDestination : NavigationState     // 목적지 도착
    object ReturningToMainScreen : NavigationState    // 메인 화면으로 복귀 중

    // 예외 및 이벤트성 상태
    object DirectionCorrectionRequired : NavigationState // 방향 수정 필요
    object ObstacleDetectionActive : NavigationState  // 장애물 감지 활성화
    object ObstacleDetectionCancelled : NavigationState // 장애물 감지 취소
    object NavigationCancelled : NavigationState      // 길안내 취소됨
}