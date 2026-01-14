import http from 'k6/http';
import {check, sleep} from 'k6';

// 1. 부하 설정 (Options)
export const options = {
    stages: [
        {duration: '30s', target: 20}, // 30초 동안 20명까지 증가
        {duration: '1m', target: 20},  // 1분 동안 20명 유지
        {duration: '10s', target: 0},  // 종료
    ],
    thresholds: {
        http_req_duration: ['p(95)<200'], // 95%의 응답이 200ms 이내여야 함 (로또 생성은 빨라야 함)
        http_req_failed: ['rate<0.01'],   // 에러율 1% 미만
    },
};

const BASE_URL = 'http://user:e6b2c5c6-2b8a-441e-b4aa-5435b53b0bdc@localhost:8080';

export default function () {
    const url = `${BASE_URL}/lotto/generate/random`;

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // 3. GET 요청 실행
    const res = http.get(url, params);

    // 4. 검증 (로또 번호가 6개 잘 오는지 등)
    check(res, {
        'status is 200': (r) => r.status === 200,
        'has 6 numbers': (r) => {
            const body = JSON.parse(r.body);
            // 앞서 설계한 LottoTicket 구조에 따라 numbers.values.length 체크
            return body.numbers.values.length === 6;
        },
        'method is RANDOM': (r) => JSON.parse(r.body).metadata.method === 'RANDOM',
    });

    sleep(1);
}
