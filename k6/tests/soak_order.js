import http from 'k6/http';
import { check } from 'k6';

/**
 * 1m / 50 TPS    → warm-up
 * 8m / 4000 TPS  → max pressure
 * 4m / 2000 TPS  → recovery
 * 1m / 50 TPS    → cool-down
 */
export let options = {
    scenarios: {
        soak_500: {
            executor: 'constant-arrival-rate',
            rate: 500,
            timeUnit: '1s',
            duration: '30m',
            preAllocatedVUs: 800,
            maxVUs: 1200,
            startTime: '0s',
        },
        spike_1m: {
            executor: 'constant-arrival-rate',
            rate: 2000,
            timeUnit: '1s',
            duration: '20s',
            preAllocatedVUs: 2000,
            maxVUs: 3000,
            startTime: '1m',
        },
        spike_5m: {
            executor: 'constant-arrival-rate',
            rate: 2000,
            timeUnit: '1s',
            duration: '20s',
            preAllocatedVUs: 2000,
            maxVUs: 3000,
            startTime: '5m',
        },
    },
    noConnectionReuse: true,
};

export default function () {
    const res = http.post(
        'http://localhost:8080/api/orders',
        JSON.stringify({ productId: 1, quantity: 1 }),
        { headers: { 'Content-Type': 'application/json' } }
    );

    check(res, {
        '주문 성공 (201)': (r) => r.status === 201 || r.status === 200,
        '품절 처리됨 (409/410)': (r) => r.status === 409 || r.status === 410,
    });
}
