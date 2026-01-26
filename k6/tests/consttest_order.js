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
        warmup_100: {
            executor: 'constant-arrival-rate',
            rate: 100,
            timeUnit: '1s',
            duration: '1m',
            preAllocatedVUs: 200,
            maxVUs: 500,
            startTime: '0s',
        },

        peak_4000: {
            executor: 'constant-arrival-rate',
            rate: 4000,
            timeUnit: '1s',
            duration: '4m',
            preAllocatedVUs: 2000,
            maxVUs: 4000,
            startTime: '1m',
        },

        recovery_2000: {
            executor: 'constant-arrival-rate',
            rate: 2000,
            timeUnit: '1s',
            duration: '2m',
            preAllocatedVUs: 1500,
            maxVUs: 3000,
            startTime: '5m',
        },
        cooldown_100: {
            executor: 'constant-arrival-rate',
            rate: 100,
            timeUnit: '1s',
            duration: '1m',
            preAllocatedVUs: 200,
            maxVUs: 500,
            startTime: '7m',
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
        '주문 성공 (201)': (r) => r.status === 201,
        '품절 처리됨 (409/410)': (r) => r.status === 409 || r.status === 410,
    });
}
