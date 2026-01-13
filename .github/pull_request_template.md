<!--
1. Title: [#<issue>] <type>(<scope>): <subject>
   Examples:
     - "[#123] feat(operator): support xxx"
     - "[#233] fix: check null before access result in xxx"
     - "[MINOR] refactor: fix typo in variable name"
     - "[MINOR] docs: fix typo in README"
     - "[#255] test: fix flaky test NameOfTheTest"
   Reference: https://www.conventionalcommits.org/en/v1.0.0/
2. If the PR is unfinished, please mark this PR as draft.
-->

## 이 PR은 어떤 변경 사항을 제안하나요?

<!--
(변경 사항을 간략히 설명하고, 이 PR이 어떻게 관련 이슈를 해결하는지 명시해주세요.)
-->

## 변경이 필요한 이유는 무엇인가요?

<!--
(왜 이러한 변경이 필요한지 명확하게 설명해주세요. 예를 들어,
새로운 API를 제안하는 경우, 해당 API의 사용 사례를 명확히 설명해주세요.
버그를 수정하는 경우, 어떤 버그인지 설명해주세요.)
Fix: #(issue)
Close: #(issue)
Ref: #(issue)
-->

## 사용자에게 영향을 미치는 변경 사항이 있나요?

<!--
- [x] 속성의 변경이 있습니다.
- [x] API의 변경이 있습니다.
- [x] 관련 문서의 업데이트가 있습니다.
- [x] 테스트 코드의 업데이트가 있습니다. 
-->

## 이 패치를 어떻게 테스트했나요?

<!--
(변경 사항을 테스트하고, 다른 사람들이 재현할 수 있도록 테스트 방법을 설명해주세요:
새로운 기능을 추가하거나 버그를 수정한 경우, 변경 사항을 검증하는 테스트 코드를 추가해주세요.
불안정한(flaky) 테스트를 수정한 경우, 여러 번 반복하여 정상 동작을 확인해주세요.)
`#FooServiceTest.mockTest(var1, var2)` 메소드를 통한 테스트코드 작성
`#FooServiceTest.mockTest(var1, var2)` 메소드의 테스트코드 케이스 추가
-->

## 추가 정보