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
        rush_order: {
            executor: 'ramping-arrival-rate',
            stages: [
                { duration: '1m', target: 20000 },
                { duration: '3m', target: 20000 },
            ],
            preAllocatedVUs: 500,
            maxVUs: 2000,
        }

    },
    // noConnectionReuse: true,
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
